package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.*
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
    return schemas.flatMap { schema -> schema.schema().byRefName(outputOptions = OutputOptions()).toList() }
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
        schema = this.schema().toSchemaObject(OutputOptions()),
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
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions()),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Default -> schema.toContentTypeObject(contentType, examples)
        is Schema.Empty -> mapOf()
        is Schema.Lazy -> schema().toContentTypeObject(contentType, examples)
        is Schema.Metadata -> schema.toContentTypeObject(contentType, examples)
        is Schema.Optional<*> -> schema.toContentTypeObject(contentType, examples)
        is Schema.OrElse<A, *> -> preferred.toContentTypeObject(contentType, examples)
        is Schema.Primitive -> mapOf(
            "text/plain" to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions()),
                examples = examples.ifEmpty { null }
            ),
        )

        is Schema.StringMap<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions()),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Transform<A, *> -> schema.toContentTypeObject(contentType, examples)
        is Schema.Record<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions()),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Union<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions()),
                examples = examples.ifEmpty { null }
            )
        )
    }
}

data class FieldOptions(
    val ref: Boolean = false,
    val nullable: Boolean? = null,
    val description: String? = null,
)

data class OutputOptions(
    // Really for google gemini structured output
    val inlineRefs: Boolean = false,
    val useAnyOf: Boolean = false,
    val usePropertyOrdering: Boolean = false,
    val supportsStringFormat: (String) -> Boolean = { true }
) {
    companion object {
        val gemini = OutputOptions(
            inlineRefs = true, // because it's a single SchemaObject
            useAnyOf = true, // Does not support oneOf / discriminator
            usePropertyOrdering = true, // https://cloud.google.com/vertex-ai/generative-ai/docs/multimodal/control-generated-output#fields
            supportsStringFormat = {
                when (it) {
                    // https://ai.google.dev/api/caching#Schema
                    // Supported formats:
                    // for NUMBER type: float, double
                    // for INTEGER type: int32, int64
                    // for STRING type: enum, date-time
                    "enum", "date-time" -> true
                    else -> false
                }
            }
        )
    }
}

fun <A> Schema<A>.toSchemaObject(outputOptions: OutputOptions = OutputOptions()): SchemaObject =
    toSchemaObjectImpl(FieldOptions(), outputOptions)

private fun <A> Schema<A>.toSchemaObjectImpl(
    field: FieldOptions,
    outputOptions: OutputOptions,
): SchemaObject =
    when (this) {
        is Schema.Empty -> error("Unit schema should not be converted to schema object")
        is Schema.Lazy<A> -> schema().toSchemaObjectImpl(field, outputOptions)

        is Schema.Metadata -> schema.toSchemaObjectImpl(
            field = field.copy(description = this.metadata.description),
            outputOptions = outputOptions
        )

        is Schema.Bytes -> SchemaObject(nullable = field.nullable, type = "string", format = "byte")
        is Schema.Collection<*> -> SchemaObject(
            nullable = field.nullable,
            type = "array",
            items = itemSchema.toSchemaObjectImpl(FieldOptions(ref = field.ref), outputOptions)
        )

        is Schema.Default -> schema.toSchemaObjectImpl(field, outputOptions)
        is Schema.Optional<*> -> schema.toSchemaObjectImpl(field.copy(nullable = true), outputOptions)

        is Schema.Primitive.Boolean ->
            SchemaObject(nullable = field.nullable, type = "boolean", description = field.description)

        is Schema.Primitive.String ->
            SchemaObject(nullable = field.nullable, type = "string", description = field.description)

        is Schema.Primitive.Double ->
            SchemaObject(nullable = field.nullable, type = "number", format = "double", description = field.description)

        is Schema.Primitive.Float ->
            SchemaObject(nullable = field.nullable, type = "number", format = "float", description = field.description)

        is Schema.Primitive.Int ->
            SchemaObject(nullable = field.nullable, type = "integer", format = "int32", description = field.description)

        is Schema.Primitive.Long ->
            SchemaObject(nullable = field.nullable, type = "integer", format = "int64", description = field.description)

        is Schema.Primitive.Enumeration<*> ->
            SchemaObject(
                nullable = field.nullable,
                type = "string",
                format = "enum",
                enum = values.map { it.toString() },
                description = field.description
            )

        is Schema.OrElse<A, *> -> preferred.toSchemaObjectImpl(field, outputOptions)

        is Schema.Transform<*, *> -> when {
            metadata.name.lowercase() == "uuid" && outputOptions.supportsStringFormat("uuid") ->
                SchemaObject(type = "string", format = "uuid", nullable = field.nullable)

            metadata.name.lowercase() == "localdate" && outputOptions.supportsStringFormat("date") ->
                SchemaObject(type = "string", format = "date", nullable = field.nullable)

            metadata.name.lowercase() == "instant" && outputOptions.supportsStringFormat("date-time") ->
                SchemaObject(type = "string", format = "date-time", nullable = field.nullable)

            else -> schema.toSchemaObjectImpl(field, outputOptions)
        }

        is Schema.StringMap<*> -> SchemaObject(
            nullable = field.nullable,
            type = "object",
            additionalProperties = valueSchema.toSchemaObjectImpl(
                FieldOptions(ref = field.ref),
                outputOptions
            ),
        )

        is Schema.Union<*> ->
            if (field.ref) {
                SchemaObject(
                    nullable = field.nullable,
                    ref = refPath(metadata.qualifiedName())
                )
            } else {
                if (outputOptions.useAnyOf) {
                    SchemaObject(
                        nullable = field.nullable,
                        anyOf = unsafeCases().map { case ->
                            val keySchema = SchemaObject(type = "string", enum = listOf(case.name))
                            val childSchema = case.schema.toSchemaObjectImpl(FieldOptions(ref = !outputOptions.inlineRefs), outputOptions)
                            childSchema.copy(
                                properties = mapOf(key to keySchema).plus(childSchema.properties.orEmpty()),
                                required = listOf(key).plus(childSchema.required.orEmpty()),
                                propertyOrdering = childSchema.propertyOrdering?.let { listOf(key).plus(it) }
                            )
                        },
                    )
                } else {
                    SchemaObject(
                        nullable = field.nullable,
                        oneOf = unsafeCases().map { it.schema.toSchemaObjectImpl(FieldOptions(ref = !outputOptions.inlineRefs), outputOptions) },
                        discriminator = DiscriminatorObject(
                            propertyName = key,
                            mapping = unsafeCases().mapNotNull { case ->
                                case.schema.byRefName(outputOptions = outputOptions).toList().singleOrNull()?.run { case.name to refPath(first) }
                            }.toMap()
                        )
                    )
                }
            }

        is Schema.Record<*> -> {
            if (field.ref) {
                SchemaObject(
                    nullable = field.nullable,
                    ref = refPath(metadata.qualifiedName())
                )
            } else {
                SchemaObject(
                    type = "object",
                    nullable = field.nullable,
                    properties = unsafeFields().associate {
                        it.name to it.schema.toSchemaObjectImpl(FieldOptions(ref = !outputOptions.inlineRefs), outputOptions)
                    },
                    required = unsafeFields().filter { it.schema !is Schema.Optional<*> }.map { it.name },
                    propertyOrdering = if (outputOptions.usePropertyOrdering) unsafeFields().map { it.name } else null
                )
            }
        }
    }

private fun Schema<*>.byRefName(nullable: Boolean? = null, outputOptions: OutputOptions): Map<String, SchemaObject> =
    when (this) {
        is Schema.Empty -> emptyMap()
        is Schema.Lazy<*> -> schema().byRefName(outputOptions = outputOptions)
        is Schema.Metadata -> schema.byRefName(outputOptions = outputOptions)
        is Schema.Bytes -> emptyMap()
        is Schema.Collection<*> -> itemSchema.byRefName(outputOptions = outputOptions)
        is Schema.Default -> schema.byRefName(outputOptions = outputOptions)
        is Schema.Optional<*> -> schema.byRefName(nullable = true, outputOptions = outputOptions)
        is Schema.OrElse<*, *> -> preferred.byRefName(outputOptions = outputOptions)
        is Schema.Primitive -> emptyMap()
        is Schema.StringMap<*> -> valueSchema.byRefName(nullable = nullable, outputOptions = outputOptions)
        is Schema.Transform<*, *> -> schema.byRefName(nullable = nullable, outputOptions = outputOptions)

        is Schema.Union<*> -> unsafeCases().fold(mapOf(metadata.qualifiedName() to toSchemaObject(outputOptions))) { acc, case ->
            acc + case.schema.byRefName(nullable, outputOptions)
        }

        is Schema.Record<*> -> unsafeFields().fold(mapOf(metadata.qualifiedName() to toSchemaObject(outputOptions))) { acc, field ->
            acc + field.schema.byRefName(nullable, outputOptions)
        }
    }

private fun refPath(name: String): String = "#/components/schemas/$name"
