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

    private data class ConfigWrapper(
        val path: List<String>,
        val config: Config?,
        val value: ConfigValue?,
    ) {
        fun atField(at: String): ConfigWrapper =
            ConfigWrapper(
                path = path.plus(at),
                config = runCatching { config?.getConfig(at) }.getOrNull(),
                value = runCatching { config?.getValue(at) }.getOrNull()
            )

        fun getString(): Validation<ConfigParseError, String> =
            Validation.requireNotNull(value) { ConfigParseError.MissingValue(path) }
                .mapValid { it.unwrapped().toString() }

        fun getList(): Validation<ConfigParseError, List<ConfigWrapper>> =
            when (value) {
                is ConfigList -> valid(value.mapIndexed { index, v ->
                    when (v) {
                        is ConfigObject -> ConfigWrapper(path.plus("[$index]"), v.toConfig(), v)
                        else -> ConfigWrapper(path.plus("[$index]"), null, v)
                    }
                })

                else -> invalid(ConfigParseError.MissingValue(path))
            }

        fun getMap(): Validation<ConfigParseError, Map<String, ConfigWrapper>> =
            when (value) {
                is ConfigObject -> valid(value.toMap().mapValues { (k, _) -> atField(k) })
                else -> invalid(ConfigParseError.MissingValue(path))
            }
    }

    fun <A> Config.parseSchema(schema: Schema<A>): Validation<ConfigParseError, A> =
        parse(schema, ConfigWrapper(emptyList(), this, this.root()))

    fun <A> Config.parseSchemaOrThrow(schema: Schema<A>): A =
        parseSchema(schema).getOrElse {
            error("Failed to parse config: ${it.first()}")
        }

    @Suppress("UNCHECKED_CAST")
    private fun <A> parse(
        parser: Schema<A>,
        conf: ConfigWrapper
    ): Validation<ConfigParseError, A> =
        when (parser) {
            is Schema.Empty -> valid(null)

            is Record<*> -> parseRecord(parser, conf)
            is Union<*> -> parseUnion(parser, conf)
            is Schema.Collection<*> -> parseList(parser, conf)
            is Schema.Enumeration -> parseEnumeration(parser, conf)
            is Schema.Lazy -> parse(parser.schema(), conf)
            is Schema.Optional<*> -> parse(parser.schema, conf).orElse {
                valid(null)
            }

            is Schema.OrElse -> parse(parser.preferred, conf).orElse {
                parse(parser.fallback, conf)
            }

            is Schema.Default -> parse(parser.schema, conf).orElse {
                valid(parser.default)
            }

            is Schema.Bytes -> conf.getString().mapValid { it.toByteArray() }
            is Schema.Primitive -> parsePrimitive(parser, conf)
            is Schema.StringMap<*> -> parseStringMap(parser, conf)
            is Schema.Transform<*, *> -> parseTransform(parser, conf)
        } as Validation<ConfigParseError, A>

    private fun <A> parseUnion(
        parser: Union<A>,
        config: ConfigWrapper
    ): Validation<ConfigParseError, A> =
        config.atField(parser.key).getString().andThen { name ->
            Validation.requireNotNull(parser.unsafeCases().find { it.name == name }) {
                ConfigParseError.InvalidValue(
                    config.path.plus(parser.key),
                    name,
                    parser.unsafeCases().map { it.name }.toSet()
                )
            }
        }.andThen { case ->
            parse(case.schema, config)
        }

    private fun <A> parseRecord(schema: Record<A>, config: ConfigWrapper): Validation<ConfigParseError, A> =
        Validation.sequence(
            schema.unsafeFields().map { field ->
                parse(
                    field.schema,
                    config.atField(field.name)
                )
            }
        ).mapValid { fields ->
            schema.unsafeConstruct(fields)
        }

    private fun <A> parseEnumeration(
        schema: Schema.Enumeration<A>,
        config: ConfigWrapper
    ): Validation<ConfigParseError, A> =
        config.getString().andThen { rawString ->
            Validation.requireNotNull(schema.values.find { it.toString() == rawString }) {
                ConfigParseError.InvalidValue(config.path, rawString, schema.values.map { it.toString() }.toSet())
            }
        }

    private fun <A> parsePrimitive(
        parser: Schema.Primitive<A>,
        config: ConfigWrapper
    ): Validation<ConfigParseError, A> =
        config.getString().andThen { configValue ->
            Validation.requireNotNull(parser.primitive.parse(configValue)) {
                FormatError(value = configValue, format = parser.primitive.toString(), path = config.path)
            }
        }

    private fun <A> parseList(
        schema: Schema.Collection<A>,
        conf: ConfigWrapper
    ): Validation<ConfigParseError, List<A>> =
        conf.getList().andThen { configs ->
            Validation.sequence(configs.map { parse(schema.itemSchema, it) })
        }

    private fun <V> parseStringMap(
        schema: Schema.StringMap<V>,
        conf: ConfigWrapper
    ): Validation<ConfigParseError, Map<String, V>> =
        conf.getMap().andThen { mapConfig ->
            Validation.sequence(
                mapConfig.map { (key, value) ->
                    parse(schema.valueSchema, value).mapValid { key to it }
                }
            ).mapValid { it.toMap() }
        }

    private fun <A, B> parseTransform(
        parser: Schema.Transform<A, B>,
        config: ConfigWrapper
    ): Validation<ConfigParseError, A> =
        parse(parser.schema, config).andThen { b ->
            parser.decode(b).fold(
                onSuccess = { valid(it) },
                onFailure = {
                    invalid(
                        FormatError(
                            value = b.toString(),
                            format = parser.metadata.name,
                            path = config.path
                        )
                    )
                }
            )
        }
}
