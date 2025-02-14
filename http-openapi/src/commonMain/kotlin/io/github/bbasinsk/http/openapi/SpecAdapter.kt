package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.BodySchema
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.ParamSchema
import io.github.bbasinsk.http.ParamsSchema
import io.github.bbasinsk.http.PathSchema
import io.github.bbasinsk.http.ResponseStatus
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.isRequired
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement

fun List<Http<*, *, *, *>>.toOpenApiSpec(info: Info, servers: List<Server> = emptyList()): OpenAPI =
    OpenAPI(
        info = info,
        servers = servers,
        paths = this.toOpenApiPaths(),
        components = Components(
            schemas = this.flatMap { it.toComponents() }.toMap()
        )
    )

private fun Http<*, *, *, *>.toComponents(): List<Pair<String, SchemaObject>> {
    val schemas = listOf(input) + (output.schemaByStatus() + error.schemaByStatus()).values
    return schemas.flatMap { schema -> schema.schema().byRefName().toList() }
}

private fun List<Http<*, *, *, *>>.toOpenApiPaths(): Map<String, Map<String, Operation>> =
    this
        .map { it to it.operation() }
        .groupBy { (endpoint, _) -> endpoint.params.toFormattedPath() }
        .mapValues { (_, value) ->
            value.associate { (endpoint, operation) ->
                endpoint.method.name.lowercase() to operation
            }
        }

private fun ParamsSchema<*>.toFormattedPath(): String =
    pathSchemas().mapNotNull { param ->
        when (param) {
            is PathSchema.Parameter -> "{${param.param.name()}}"
            is PathSchema.Segment -> param.name
            else -> null
        }
    }.joinToString(separator = "/", prefix = "/")

private fun <Params, Input, Error, Output> Http<Params, Input, Error, Output>.operation(): Operation =
    Operation(
        summary = metadata.summary,
        tags = metadata.tags.ifEmpty { null },
        deprecated = true.takeIf { metadata.deprecatedReason != null },
        operationId = null,
        parameters = params.pathParams(),
        requestBody = requestBody(this.input),
        responses = (output.schemaByStatus() + error.schemaByStatus()).map { (status, case) ->
            status.code.toString() to case.toResponseObject(status)
        }.toMap()
    )

fun <A> BodySchema<A>.toResponseObject(status: ResponseStatus): ResponseObject =
    ResponseObject(
        description = status.description,
        content = schema().toContentTypeObject(
            contentType = contentType().mimeType,
            examples = examples().mapValues { (key, value) ->
                ExampleObject(summary = key, value = schema().encodeToJsonElement(value))
            }
        )
    )

private fun <A> ParamsSchema<A>.pathParams(): List<Parameter> =
    when (this) {
        is ParamsSchema.Combine<*, *> -> left.pathParams() + right.pathParams()
        is PathSchema.Combine<*, *> -> left.pathParams() + right.pathParams()
        is PathSchema.Segment, PathSchema.RootSchema -> listOf()
        is PathSchema.Parameter -> listOf(param.toParameter(`in` = "path"))
        is ParamsSchema.HeaderSchema -> listOf(param.toParameter(`in` = "header"))
        is ParamsSchema.QuerySchema -> listOf(param.toParameter(`in` = "query"))
    }

private fun <A> ParamSchema<A>.toParameter(`in`: String): Parameter =
    Parameter(
        name = this.name(),
        `in` = `in`,
        description = this.description(),
        required = this.schema().isRequired(),
        deprecated = this.deprecatedReason() != null,
        schema = this.schema().toSchemaObject(Options()),
        examples = examples().mapValues { (key, value) ->
            ExampleObject(summary = key, value = this.schema().encodeToJsonElement(value))
        }
    )

private fun <A> requestBody(request: BodySchema<A>): RequestBody? =
    if (request.schema() is Schema.Empty) {
        null
    } else {
        RequestBody(
            content = request.schema().toContentTypeObject(
                contentType = request.contentType().mimeType,
                examples = request.examples().mapValues { (key, value) ->
                    ExampleObject(summary = key, value = request.schema().encodeToJsonElement(value))
                }
            ),
            required = request.schema().isRequired(),
            description = request.description()
        )
    }

// Maps a content type to a schema
private fun <A> Schema<A>.toContentTypeObject(
    contentType: String,
    examples: Map<String, ExampleObject>
): Map<String, MediaTypeObject> {
    return when (this) {
        is Schema.Bytes -> TODO()
        is Schema.Collection<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObject(Options(ref = true)),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Default -> schema.toContentTypeObject(contentType, examples)
        is Schema.Empty -> mapOf()
        is Schema.Lazy -> schema().toContentTypeObject(contentType, examples)
        is Schema.Optional<*> -> schema.toContentTypeObject(contentType, examples)
        is Schema.OrElse<*> -> preferred.toContentTypeObject(contentType, examples)
        is Schema.Primitive -> mapOf(
            "text/plain" to MediaTypeObject(
                schema = this.toSchemaObject(Options()),
                examples = examples.ifEmpty { null }
            ),
        )

        is Schema.StringMap<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObject(Options()),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Transform<A, *> -> schema.toContentTypeObject(contentType, examples)
        is Schema.Record<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObject(Options(ref = true)),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Union<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObject(Options(ref = true)),
                examples = examples.ifEmpty { null }
            )
        )
    }
}

data class Options(
    val ref: Boolean = false,
    val nullable: Boolean? = null,

    // Really for google gemini structured output
    val useAnyOf: Boolean = false,
    val usePropertyOrdering: Boolean = false
)

fun <A> Schema<A>.toSchemaObject(options: Options = Options()): SchemaObject =
    when (this) {
        is Schema.Empty -> error("Unit schema should not be converted to schema object")
        is Schema.Lazy<A> -> schema().toSchemaObject(options)
        is Schema.Bytes -> SchemaObject(nullable = options.nullable, type = "string", format = "byte")
        is Schema.Collection<*> -> SchemaObject(nullable = options.nullable, type = "array", items = itemSchema.toSchemaObject(Options(ref = options.ref)))
        is Schema.Default -> schema.toSchemaObject(options)
        is Schema.Optional<*> -> schema.toSchemaObject(options.copy(nullable = true))
        is Schema.Primitive.Boolean -> SchemaObject(nullable = options.nullable, type = "boolean")
        is Schema.Primitive.String -> SchemaObject(nullable = options.nullable, type = "string")
        is Schema.Primitive.Double -> SchemaObject(nullable = options.nullable, type = "number", format = "double")
        is Schema.Primitive.Float -> SchemaObject(nullable = options.nullable, type = "number", format = "float")
        is Schema.Primitive.Int -> SchemaObject(nullable = options.nullable, type = "integer", format = "int32")
        is Schema.Primitive.Long -> SchemaObject(nullable = options.nullable, type = "integer", format = "int64")
        is Schema.Primitive.Enumeration<*> -> SchemaObject(nullable = options.nullable, type = "string", enum = values.map { it.toString() })
        is Schema.OrElse<*> -> preferred.toSchemaObject(options)
        is Schema.Transform<*, *> -> this.toSchemaObject(options)
        is Schema.StringMap<*> -> SchemaObject(nullable = options.nullable, type = "object", additionalProperties = valueSchema.toSchemaObject(Options(ref = options.ref)))
        is Schema.Union<*> ->
            if (options.ref) {
                SchemaObject(
                    nullable = options.nullable,
                    ref = refPath(metadata.qualifiedName())
                )
            } else {
                if (options.useAnyOf) {
                    SchemaObject(
                        nullable = options.nullable,
                        anyOf = unsafeCases()
                            .map { it.schema.toSchemaObject(Options()) }
                            .map { childSchema ->
                                childSchema.copy(
                                    properties = childSchema.properties?.plus(key to SchemaObject(type = "string")),
                                    required = childSchema.required?.plus(key)
                                )
                            },
                    )
                } else {
                    SchemaObject(
                        nullable = options.nullable,
                        oneOf = unsafeCases().map { it.schema.toSchemaObject(Options()) },
                        discriminator = DiscriminatorObject(
                            propertyName = key,
                            mapping = unsafeCases().mapNotNull { case ->
                                case.schema.byRefName().toList().singleOrNull()?.run { case.name to refPath(first) }
                            }.toMap()
                        )
                    )
                }
            }

        is Schema.Record<*> -> {
            if (options.ref) {
                SchemaObject(
                    nullable = options.nullable,
                    ref = refPath(metadata.qualifiedName())
                )
            } else {
                SchemaObject(
                    type = "object",
                    nullable = options.nullable,
                    properties = unsafeFields().associate { it.name to it.schema.toSchemaObject(Options()) },
                    required = unsafeFields().filter { it.schema !is Schema.Optional<*> }.map { it.name },
                    propertyOrdering = if (options.usePropertyOrdering) unsafeFields().map { it.name } else null
                )
            }
        }
    }

private fun <A, B> Schema.Transform<A, B>.toSchemaObject(options: Options): SchemaObject =
    when (metadata.name.lowercase()) {
        "uuid" -> SchemaObject(type = "string", format = "uuid", nullable = options.nullable)
        "localdate" -> SchemaObject(type = "string", format = "date", nullable = options.nullable)
        "instant" -> SchemaObject(type = "string", format = "date-time", nullable = options.nullable)
        else -> schema.toSchemaObject(options)
    }

private fun Schema<*>.byRefName(nullable: Boolean? = null): Map<String, SchemaObject> =
    when (this) {
        is Schema.Empty -> emptyMap()
        is Schema.Lazy<*> -> schema().byRefName()
        is Schema.Bytes -> emptyMap()
        is Schema.Collection<*> -> itemSchema.byRefName()
        is Schema.Default -> schema.byRefName()
        is Schema.Optional<*> -> schema.byRefName(nullable = true)
        is Schema.OrElse<*> -> preferred.byRefName()
        is Schema.Primitive -> emptyMap()
        is Schema.StringMap<*> -> valueSchema.byRefName(nullable = nullable)
        is Schema.Transform<*, *> -> schema.byRefName(nullable = nullable)
        is Schema.Union<*> -> unsafeCases().fold(mapOf(metadata.qualifiedName() to toSchemaObject(Options()))) { acc, case ->
            acc // + case.schema.byRefName()
        }

        is Schema.Record<*> -> mapOf(metadata.qualifiedName() to toSchemaObject(Options()))
    }

private fun refPath(name: String): String = "#/components/schemas/$name"
