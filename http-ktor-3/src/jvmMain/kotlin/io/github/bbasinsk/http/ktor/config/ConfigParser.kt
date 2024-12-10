package io.github.bbasinsk.http.ktor.config

import io.github.bbasinsk.http.ktor.config.ConfigParseError.FormatError
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Record
import io.github.bbasinsk.schema.Schema.Union
import io.github.bbasinsk.validation.Validation
import io.github.bbasinsk.validation.Validation.Companion.invalid
import io.github.bbasinsk.validation.Validation.Companion.valid
import io.github.bbasinsk.validation.andThen
import io.github.bbasinsk.validation.getOrElse
import io.github.bbasinsk.validation.mapValid
import io.github.bbasinsk.validation.orElse
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.config.ApplicationConfigValue
import io.ktor.server.config.tryGetString
import io.ktor.utils.io.core.toByteArray
import kotlin.collections.find
import kotlin.collections.map
import kotlin.collections.plus
import kotlin.collections.toSet
import kotlin.let
import kotlin.toString


object ConfigParser {

    private data class KtorConfig(
        val asObject: ApplicationConfig?,
        val asList: List<KtorConfig>?,
        val asValue: ApplicationConfigValue?
    )

    fun <A> ApplicationConfig.parseSchema(schema: Schema<A>): Validation<ConfigParseError, A> =
        parse(schema, emptyList(), KtorConfig(this, null, null))

    fun <A> ApplicationConfig.parseSchemaOrThrow(schema: Schema<A>): A =
        parseSchema(schema).getOrElse {
            error("Failed to parse config: $it")
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

            is Schema.Bytes -> Validation.requireNotNull(ktorConfig.asValue) { ConfigParseError.MissingValue(path) }
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
        Validation.requireNotNull(config.asObject?.tryGetString(parser.key)) {
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
        Validation.requireNotNull(config.asObject) { ConfigParseError.MissingValue(path) }
            .andThen { objectConfig ->
                Validation.sequence(
                    schema.unsafeFields().map { field ->
                        parse(
                            field.schema,
                            path.plus(field.name),
                            KtorConfig(
                                asObject = objectConfig.configOrNull(field.name),
                                asList = objectConfig.configListOrNull(field.name),
                                asValue = objectConfig.propertyOrNull(field.name)
                            )
                        )
                    }
                )
            }.mapValid { fields -> schema.unsafeConstruct(fields) }

    private fun <A> parseEnumeration(
        schema: Schema.Enumeration<A>,
        path: List<String>,
        config: KtorConfig
    ): Validation<ConfigParseError, A> =
        Validation.requireNotNull(config.asValue) { ConfigParseError.MissingValue(path) }.andThen {
            it.getString().let { rawString ->
                Validation.requireNotNull(schema.values.find { it.toString() == rawString }) {
                    ConfigParseError.InvalidValue(path, rawString, schema.values.map { it.toString() }.toSet())
                }
            }
        }

    private fun <A> parsePrimitive(
        parser: Schema.Primitive<A>,
        path: List<String>,
        config: KtorConfig
    ): Validation<ConfigParseError, A> =
        Validation.requireNotNull(config.asValue) {
            ConfigParseError.MissingValue(path)
        }.andThen { configValue ->
            Validation.requireNotNull(parser.primitive.parse(configValue.getString())) {
                FormatError(
                    value = configValue.getString(),
                    format = parser.primitive.toString(),
                    path = path
                )
            }
        }

    private fun <A> parseList(
        schema: Schema.Collection<A>,
        path: List<String>,
        ktorConfig: KtorConfig
    ): Validation<ConfigParseError, List<A>> =
        Validation.requireNotNull(ktorConfig.asList) { ConfigParseError.MissingValue(path) }
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
        Validation.requireNotNull(ktorConfig.asObject) {
            ConfigParseError.MissingValue(path)
        }.andThen { mapConfig ->
            Validation.sequence(
                mapConfig.toMap().keys.map { key ->
                    parse(
                        schema.valueSchema,
                        path.plus(key),
                        KtorConfig(
                            asObject = mapConfig.configOrNull(key),
                            asList = mapConfig.configListOrNull(key),
                            asValue = mapConfig.propertyOrNull(key)
                        )
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
                onFailure = {
                    invalid(
                        FormatError(
                            value = b.toString(),
                            format = parser.metadata.name,
                            path = path
                        )
                    )
                }
            )
        }

    private fun indexedPath(path: List<String>, idx: Int): List<String> =
        path.plus("[$idx]")

    private fun ApplicationConfig.configOrNull(path: String): ApplicationConfig? =
        try {
            this.config(path)
        } catch (_: Exception) {
            null
        }

    private fun ApplicationConfig.configListOrNull(path: String): List<KtorConfig>? =
        runCatching { configList(path).map { KtorConfig(it, null, null) } }
            .recoverCatching { property(path).getList().map { KtorConfig(null, null, it.asApplicationConfigValue()) } }
            .getOrNull()

    private fun String.asApplicationConfigValue(): ApplicationConfigValue =
        object : ApplicationConfigValue {
            override fun getString(): String = this@asApplicationConfigValue
            override fun getList(): List<String> = error("Not a list")
        }
}
