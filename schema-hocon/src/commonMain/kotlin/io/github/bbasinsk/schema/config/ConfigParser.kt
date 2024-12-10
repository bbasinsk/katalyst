package io.github.bbasinsk.schema.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigList
import com.typesafe.config.ConfigObject
import com.typesafe.config.ConfigValue
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Record
import io.github.bbasinsk.schema.Schema.Union
import io.github.bbasinsk.schema.config.ConfigParseError.FormatError
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.Validation.Companion.invalid
import io.github.bbasinsk.validation.Validation.Companion.valid
import io.github.bbasinsk.validation.andThen
import io.github.bbasinsk.validation.getOrElse
import io.github.bbasinsk.validation.mapValid
import io.github.bbasinsk.validation.orElse


object ConfigParser {

    data class KtorConfig(
        val config: Config?,
        val value: ConfigValue?,
    ) {
        fun atField(at: String): KtorConfig {
            return KtorConfig(
                config = runCatching { config?.getConfig(at) }.getOrNull(),
                value = runCatching { config?.getValue(at) }.getOrNull()
            )
        }

        fun getString(): String = value!!.unwrapped().toString()

        fun getList(): List<KtorConfig> =
            when (value) {
                is ConfigList -> value.map {
                    when (it) {
                        is ConfigObject -> KtorConfig(it.toConfig(), it)
                        else -> KtorConfig(null, it)
                    }
                }

                else -> error("Expected list, got $value")
            }

        fun getMap(): Map<String, KtorConfig> {
            return when(value) {
                is ConfigObject -> {
                    value.toMap().mapValues { (k, v) ->
                        atField(k)
                    }
                }
                else -> error("Expected object, got $value")
            }
        }
    }

    fun <A> Config.parseSchema(schema: Schema<A>): Validation<ConfigParseError, A> =
        parse(schema, emptyList(), KtorConfig(this, this.root()))

    fun <A> Config.parseSchemaOrThrow(schema: Schema<A>): A =
        parseSchema(schema).getOrElse {
            error("Failed to parse config: ${it.first()}")
        }

    @Suppress("UNCHECKED_CAST")
    private fun <A> parse(
        parser: Schema<A>,
        path: List<String>,
        ktorConfig: KtorConfig
    ): Validation<ConfigParseError, A> =
        when (parser) {
            is Schema.Empty -> valid(null)

            is Record<*> -> parseRecord(parser, path, ktorConfig)
            is Union<*> -> parseUnion(parser, path, ktorConfig)
            is Schema.Collection<*> -> parseList(parser, path, ktorConfig)
            is Schema.Enumeration -> parseEnumeration(parser, path, ktorConfig)
            is Schema.Lazy -> parse(parser.schema(), path, ktorConfig)
            is Schema.Optional<*> -> parse(parser.schema, path, ktorConfig).orElse {
                valid(null)
            }

            is Schema.OrElse -> parse(parser.preferred, path, ktorConfig).orElse {
                parse(parser.fallback, path, ktorConfig)
            }

            is Schema.Default -> parse(parser.schema, path, ktorConfig).orElse {
                valid(parser.default)
            }

            is Schema.Bytes -> Validation.requireNotNull(ktorConfig) { ConfigParseError.MissingValue(path) }
                .mapValid { it.getString().toByteArray() }

            is Schema.Primitive -> parsePrimitive(parser, path, ktorConfig)
            is Schema.StringMap<*> -> parseStringMap(parser, path, ktorConfig)
            is Schema.Transform<*, *> -> parseTransform(parser, path, ktorConfig)
        } as Validation<ConfigParseError, A>

    private fun <A> parseUnion(
        parser: Union<A>,
        path: List<String>,
        config: KtorConfig
    ): Validation<ConfigParseError, A> =
        Validation.requireNotNull(config.atField(parser.key).getString()) {
            ConfigParseError.MissingValue(path.plus(parser.key))
        }.andThen { name ->
            Validation.requireNotNull(parser.unsafeCases().find { it.name == name }) {
                ConfigParseError.InvalidValue(path.plus(parser.key), name, parser.unsafeCases().map { it.name }.toSet())
            }
        }.andThen { case ->
            parse(case.schema, path, config)
        }

    private fun <A> parseRecord(
        schema: Record<A>,
        path: List<String>,
        config: KtorConfig
    ): Validation<ConfigParseError, A> =
        Validation.requireNotNull(config) { ConfigParseError.MissingValue(path) }
            .andThen { objectConfig ->
                Validation.sequence(
                    schema.unsafeFields().map { field ->
                        parse(
                            field.schema,
                            path.plus(field.name),
                            objectConfig.atField(field.name)
                        )
                    }
                )
            }.mapValid { fields -> schema.unsafeConstruct(fields) }

    private fun <A> parseEnumeration(
        schema: Schema.Enumeration<A>,
        path: List<String>,
        config: KtorConfig
    ): Validation<ConfigParseError, A> =
        Validation.requireNotNull(config.getString()) { ConfigParseError.MissingValue(path) }
            .andThen { rawString ->
                Validation.requireNotNull(schema.values.find { it.toString() == rawString }) {
                    ConfigParseError.InvalidValue(path, rawString, schema.values.map { it.toString() }.toSet())
                }
            }

    private fun <A> parsePrimitive(
        parser: Schema.Primitive<A>,
        path: List<String>,
        config: KtorConfig
    ): Validation<ConfigParseError, A> =
        Validation.requireNotNull(config.getString()) {
            ConfigParseError.MissingValue(path)
        }.andThen { configValue ->
            Validation.requireNotNull(parser.primitive.parse(configValue)) {
                FormatError(value = configValue, format = parser.primitive.toString(), path = path)
            }
        }

    private fun <A> parseList(
        schema: Schema.Collection<A>,
        path: List<String>,
        ktorConfig: KtorConfig
    ): Validation<ConfigParseError, List<A>> =
        Validation.requireNotNull(ktorConfig.getList()) { ConfigParseError.MissingValue(path) }
            .andThen { configs ->
                Validation.sequence(
                    configs.withIndex().map { (index, config) ->
                        parse(schema.itemSchema, indexedPath(path, index), config)
                    }
                )
            }

    private fun <V> parseStringMap(
        schema: Schema.StringMap<V>,
        path: List<String>,
        ktorConfig: KtorConfig
    ): Validation<ConfigParseError, Map<String, V>> =
        Validation.requireNotNull(ktorConfig.getMap()) {
            ConfigParseError.MissingValue(path)
        }.andThen { mapConfig ->
            Validation.sequence(
                mapConfig.map { (key, value) ->
                    parse(
                        schema.valueSchema,
                        path.plus(key),
                        value,
                    ).mapValid { key to it }
                }
            ).mapValid { it.toMap() }
        }

    private fun <A, B> parseTransform(
        parser: Schema.Transform<A, B>,
        path: List<String>,
        config: KtorConfig
    ): Validation<ConfigParseError, A> =
        parse(parser.schema, path, config).andThen { b ->
            parser.decode(b).fold(
                onSuccess = { valid(it) },
                onFailure = { invalid(FormatError(value = b.toString(), format = parser.metadata.name, path = path)) }
            )
        }

    private fun indexedPath(path: List<String>, idx: Int): List<String> =
        path.plus("[$idx]")
}
