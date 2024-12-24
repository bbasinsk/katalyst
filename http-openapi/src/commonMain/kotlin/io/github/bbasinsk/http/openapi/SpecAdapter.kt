package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.ParamsSchema
import io.github.bbasinsk.http.PathSchema
import io.github.bbasinsk.http.ResponseStatus
import io.github.bbasinsk.http.ParamSchema
import io.github.bbasinsk.http.BodySchema
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
    return schemas.mapNotNull { schema -> schema.schema().ref() }
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
        schema = this.schema().toSchemaObject(),
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
                schema = toSchemaObject(ref = true),
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
                schema = this.toSchemaObject(),
                examples = examples.ifEmpty { null }
            ),
        )

        is Schema.StringMap<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObject(),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Transform<A, *> -> schema.toContentTypeObject(contentType, examples)
        is Schema.Record<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObject(ref = true),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Union<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObject(ref = true),
                examples = examples.ifEmpty { null }
            )
        )
    }
}

internal fun <A> Schema<A>.toSchemaObject(ref: Boolean = false): SchemaObject =
    when (this) {
        is Schema.Empty -> error("Unit schema should not be converted to schema object")
        is Schema.Lazy<A> -> schema().toSchemaObject(ref)
        is Schema.Bytes -> SchemaObject(type = "string", format = "byte")
        is Schema.Collection<*> -> SchemaObject(type = "array", items = itemSchema.toSchemaObject(ref))
        is Schema.Default -> schema.toSchemaObject(ref)
        is Schema.Optional<*> -> schema.toSchemaObject(ref)
        is Schema.Primitive.Boolean -> SchemaObject(type = "boolean")
        is Schema.Primitive.String -> SchemaObject(type = "string")
        is Schema.Primitive.Double -> SchemaObject(type = "number", format = "double")
        is Schema.Primitive.Float -> SchemaObject(type = "number", format = "float")
        is Schema.Primitive.Int -> SchemaObject(type = "integer", format = "int32")
        is Schema.Primitive.Long -> SchemaObject(type = "integer", format = "int64")
        is Schema.Primitive.Enumeration<*> -> SchemaObject(type = "string", enum = values.map { it.toString() })
        is Schema.OrElse<*> -> preferred.toSchemaObject(ref)
        is Schema.Transform<*, *> -> this.toSchemaObject(ref)
        is Schema.StringMap<*> -> SchemaObject(type = "object", additionalProperties = valueSchema.toSchemaObject(ref))
        is Schema.Union<*> ->
            SchemaObject(
                oneOf = unsafeCases().map { it.schema.toSchemaObject(ref) },
                discriminator = DiscriminatorObject(
                    propertyName = key,
                    mapping = unsafeCases()
                        .mapNotNull { case -> case.schema.ref()?.let { case.name to it.first } }
                        .toMap()
                )
            )

        is Schema.Record<*> -> {
            if (ref) {
                SchemaObject(ref = "#/components/schemas/${metadata.qualifiedName()}")
            } else {
                SchemaObject(
                    type = "object",
                    properties = unsafeFields().associate { it.name to it.schema.toSchemaObject() },
                    required = unsafeFields().filter { it.schema !is Schema.Optional<*> }.map { it.name }
                )
            }
        }
    }

private fun <A, B> Schema.Transform<A, B>.toSchemaObject(ref: Boolean): SchemaObject =
    when (metadata.name.lowercase()) {
        "uuid" -> SchemaObject(type = "string", format = "uuid")
        "localdate" -> SchemaObject(type = "string", format = "date")
        "instant" -> SchemaObject(type = "string", format = "date-time")
        else -> schema.toSchemaObject(ref)
    }

private fun Schema<*>.ref(): Pair<String, SchemaObject>? =
    when (this) {
        is Schema.Empty -> null
        is Schema.Lazy<*> -> schema().ref()
        is Schema.Bytes -> null
        is Schema.Collection<*> -> itemSchema.ref()
        is Schema.Default -> schema.ref()
        is Schema.Optional<*> -> schema.ref()
        is Schema.OrElse<*> -> preferred.ref()
        is Schema.Primitive -> null
        is Schema.StringMap<*> -> valueSchema.ref()
        is Schema.Transform<*, *> -> schema.ref()
        is Schema.Union<*> -> null
        is Schema.Record<*> -> metadata.qualifiedName() to toSchemaObject()
    }
