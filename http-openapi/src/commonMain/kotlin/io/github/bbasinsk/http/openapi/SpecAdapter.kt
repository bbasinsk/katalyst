package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.*
import io.github.bbasinsk.schema.DefinitionNameResolver
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.isRequired
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement

fun List<Http<*, *, *, *>>.toOpenApiSpec(info: Info, servers: List<Server> = emptyList()): OpenAPI {
    val resolver = DefinitionNameResolver()
    return OpenAPI(
        info = info,
        servers = servers,
        paths = this.toOpenApiPaths(resolver),
        components = Components(
            schemas = this.flatMap { it.toComponents(resolver) }.toMap()
        )
    )
}

private fun Http<*, *, *, *>.toComponents(resolver: DefinitionNameResolver): List<Pair<String, SchemaObject>> {
    val schemas = listOf(input) + (output.schemaByStatus() + error.schemaByStatus()).values
    return schemas.flatMap { schema ->
        schema.schema().byRefName(outputOptions = OutputOptions(), resolver = resolver).toList()
    }
}

private fun List<Http<*, *, *, *>>.toOpenApiPaths(resolver: DefinitionNameResolver): Map<String, Map<String, Operation>> =
    this
        .map { it to it.operation(resolver) }
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

private fun <Params, Input, Error, Output> Http<Params, Input, Error, Output>.operation(
    resolver: DefinitionNameResolver
): Operation =
    Operation(
        summary = metadata.summary,
        tags = metadata.tags.ifEmpty { null },
        deprecated = true.takeIf { metadata.deprecatedReason != null },
        operationId = null,
        parameters = params.pathParams(resolver),
        requestBody = requestBody(this.input, resolver),
        responses = (output.schemaByStatus() + error.schemaByStatus()).map { (status, case) ->
            status.code.toString() to case.toResponseObject(status, resolver)
        }.toMap()
    )

fun <A> BodySchema<A>.toResponseObject(status: ResponseStatus, resolver: DefinitionNameResolver): ResponseObject =
    ResponseObject(
        description = status.description,
        content = schema().toContentTypeObject(
            contentType = contentType().mimeType,
            examples = examples().mapValues { (key, value) ->
                ExampleObject(summary = key, value = schema().encodeToJsonElement(value))
            },
            resolver = resolver
        )
    )

private fun <A> ParamsSchema<A>.pathParams(resolver: DefinitionNameResolver): List<Parameter> =
    when (this) {
        is ParamsSchema.Combine<*, *> -> left.pathParams(resolver) + right.pathParams(resolver)
        is PathSchema.Combine<*, *> -> left.pathParams(resolver) + right.pathParams(resolver)
        is PathSchema.Segment, PathSchema.RootSchema -> listOf()
        is PathSchema.Parameter -> listOf(param.toParameter(`in` = "path", resolver = resolver))
        is ParamsSchema.HeaderSchema -> listOf(param.toParameter(`in` = "header", resolver = resolver))
        is ParamsSchema.QuerySchema -> listOf(param.toParameter(`in` = "query", resolver = resolver))
    }

private fun <A> ParamSchema<A>.toParameter(`in`: String, resolver: DefinitionNameResolver): Parameter =
    Parameter(
        name = this.name(),
        `in` = `in`,
        description = this.description(),
        required = this.schema().isRequired(),
        deprecated = this.deprecatedReason() != null,
        schema = this.schema().toSchemaObject(OutputOptions(), resolver),
        examples = examples().mapValues { (key, value) ->
            ExampleObject(summary = key, value = this.schema().encodeToJsonElement(value))
        }
    )

private fun <A> requestBody(request: BodySchema<A>, resolver: DefinitionNameResolver): RequestBody? =
    if (request.schema() is Schema.Empty) {
        null
    } else {
        RequestBody(
            content = request.schema().toContentTypeObject(
                contentType = request.contentType().mimeType,
                examples = request.examples().mapValues { (key, value) ->
                    ExampleObject(summary = key, value = request.schema().encodeToJsonElement(value))
                },
                resolver = resolver
            ),
            required = request.schema().isRequired(),
            description = request.description()
        )
    }

// Maps a content type to a schema
private fun <A> Schema<A>.toContentTypeObject(
    contentType: String,
    examples: Map<String, ExampleObject>,
    resolver: DefinitionNameResolver
): Map<String, MediaTypeObject> {
    return when (this) {
        is Schema.Bytes -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions(), resolver),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Collection<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions(), resolver),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Default -> schema.toContentTypeObject(contentType, examples, resolver)
        is Schema.Empty -> mapOf()
        is Schema.Lazy -> schema().toContentTypeObject(contentType, examples, resolver)
        is Schema.Metadata -> schema.toContentTypeObject(contentType, examples, resolver)
        is Schema.Optional<*> -> schema.toContentTypeObject(contentType, examples, resolver)
        is Schema.OrElse<A, *> -> preferred.toContentTypeObject(contentType, examples, resolver)
        is Schema.Primitive -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions(), resolver),
                examples = examples.ifEmpty { null }
            ),
        )

        is Schema.StringMap<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions(), resolver),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Transform<A, *> -> schema.toContentTypeObject(contentType, examples, resolver)
        is Schema.Record<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions(), resolver),
                examples = examples.ifEmpty { null }
            )
        )

        is Schema.Union<*> -> mapOf(
            contentType to MediaTypeObject(
                schema = toSchemaObjectImpl(FieldOptions(ref = true), OutputOptions(), resolver),
                examples = examples.ifEmpty { null }
            )
        )
    }
}

data class FieldOptions(
    val ref: Boolean = false,
    val nullable: Boolean? = null,
    val description: String? = null
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

fun <A> Schema<A>.toSchemaObject(
    outputOptions: OutputOptions = OutputOptions(),
    resolver: DefinitionNameResolver = DefinitionNameResolver()
): SchemaObject =
    toSchemaObjectImpl(FieldOptions(), outputOptions, resolver)

private fun <A> Schema<A>.toSchemaObjectImpl(
    field: FieldOptions,
    outputOptions: OutputOptions,
    resolver: DefinitionNameResolver,
): SchemaObject =
    when (this) {
        is Schema.Empty -> error("Unit schema should not be converted to schema object")
        is Schema.Lazy<A> -> schema().toSchemaObjectImpl(field, outputOptions, resolver)

        is Schema.Metadata -> schema.toSchemaObjectImpl(
            field = field.copy(description = this.metadata.description),
            outputOptions = outputOptions,
            resolver = resolver
        )

        is Schema.Bytes -> SchemaObject(nullable = field.nullable, type = "string", format = "binary")
        is Schema.Collection<*> -> SchemaObject(
            nullable = field.nullable,
            type = "array",
            items = itemSchema.toSchemaObjectImpl(FieldOptions(ref = field.ref), outputOptions, resolver)
        )

        is Schema.Default -> schema.toSchemaObjectImpl(field, outputOptions, resolver)
        is Schema.Optional<*> -> schema.toSchemaObjectImpl(field.copy(nullable = true), outputOptions, resolver)

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

        is Schema.OrElse<A, *> -> preferred.toSchemaObjectImpl(field, outputOptions, resolver)

        is Schema.Transform<*, *> -> when {
            metadata.name.lowercase() == "uuid" && outputOptions.supportsStringFormat("uuid") ->
                SchemaObject(type = "string", format = "uuid", nullable = field.nullable)

            metadata.name.lowercase() == "localdate" && outputOptions.supportsStringFormat("date") ->
                SchemaObject(type = "string", format = "date", nullable = field.nullable)

            metadata.name.lowercase() == "instant" && outputOptions.supportsStringFormat("date-time") ->
                SchemaObject(type = "string", format = "date-time", nullable = field.nullable)

            else -> schema.toSchemaObjectImpl(field, outputOptions, resolver)
        }

        is Schema.StringMap<*> -> SchemaObject(
            nullable = field.nullable,
            type = "object",
            additionalProperties = valueSchema.toSchemaObjectImpl(
                FieldOptions(ref = field.ref),
                outputOptions,
                resolver
            ),
        )

        is Schema.Union<*> ->
            if (field.ref) {
                SchemaObject(
                    nullable = field.nullable,
                    ref = refPath(resolver.resolve(this, this.metadata))
                )
            } else {
                if (outputOptions.inlineRefs) {
                    // Original inline behavior for Gemini/inlineRefs mode
                    val cases = unsafeCases().map { case ->
                        val commonPropertiesSchema = SchemaObject(
                            type = "object",
                            properties = mapOf(
                                key to SchemaObject(
                                    type = "string",
                                    enum = listOf(case.name)
                                )
                            ),
                            required = listOf(key)
                        )

                        val childSchema = case.schema.toSchemaObjectImpl(
                            FieldOptions(ref = false),
                            outputOptions,
                            resolver
                        )

                        SchemaObject(
                            allOf = listOf(commonPropertiesSchema, childSchema)
                        )
                    }

                    if (outputOptions.useAnyOf) {
                        SchemaObject(
                            nullable = field.nullable,
                            anyOf = cases,
                        )
                    } else {
                        SchemaObject(
                            nullable = field.nullable,
                            oneOf = cases,
                            discriminator = DiscriminatorObject(
                                propertyName = key,
                                mapping = unsafeCases().mapNotNull { case ->
                                    val caseSchemas = case.schema.byRefName(outputOptions = outputOptions, resolver = resolver)
                                    val caseSchema = caseSchemas.toList().firstOrNull()
                                    caseSchema?.run { case.name to refPath(first) }
                                }.toMap()
                            )
                        )
                    }
                } else {
                    // New ref-based behavior for code generators
                    val baseName = resolver.resolve(this, this.metadata)
                    val withDiscriminatorRefs = unsafeCases().map { case ->
                        SchemaObject(ref = refPath("$baseName.${case.name}WithDiscriminator"))
                    }

                    if (outputOptions.useAnyOf) {
                        SchemaObject(
                            nullable = field.nullable,
                            anyOf = withDiscriminatorRefs,
                        )
                    } else {
                            SchemaObject(
                                nullable = field.nullable,
                                oneOf = withDiscriminatorRefs,
                                discriminator = DiscriminatorObject(
                                    propertyName = key,
                                    mapping = unsafeCases().associate { case ->
                                        case.name to refPath("$baseName.${case.name}WithDiscriminator")
                                    }
                                )
                            )
                        }
                }
            }

        is Schema.Record<*> -> {
            if (field.ref) {
                SchemaObject(
                    nullable = field.nullable,
                    ref = refPath(resolver.resolve(this, this.metadata))
                )
            } else {
                SchemaObject(
                    type = "object",
                    nullable = field.nullable,
                    properties = unsafeFields().associate {
                        it.name to it.schema.toSchemaObjectImpl(FieldOptions(ref = !outputOptions.inlineRefs), outputOptions, resolver)
                    },
                    required = unsafeFields().filter { it.schema !is Schema.Optional<*> }.map { it.name },
                    propertyOrdering = if (outputOptions.usePropertyOrdering) unsafeFields().map { it.name } else null
                )
            }
        }
    }

private fun Schema<*>.byRefName(
    nullable: Boolean? = null,
    outputOptions: OutputOptions,
    resolver: DefinitionNameResolver,
): Map<String, SchemaObject> =
    when (this) {
        is Schema.Empty -> emptyMap()
        is Schema.Lazy<*> -> schema().byRefName(nullable = nullable, outputOptions = outputOptions, resolver = resolver)
        is Schema.Metadata -> schema.byRefName(nullable = nullable, outputOptions = outputOptions, resolver = resolver)
        is Schema.Bytes -> emptyMap()
        is Schema.Collection<*> -> itemSchema.byRefName(nullable = nullable, outputOptions = outputOptions, resolver = resolver)
        is Schema.Default -> schema.byRefName(nullable = nullable, outputOptions = outputOptions, resolver = resolver)
        is Schema.Optional<*> -> schema.byRefName(nullable = true, outputOptions = outputOptions, resolver = resolver)
        is Schema.OrElse<*, *> -> preferred.byRefName(outputOptions = outputOptions, resolver = resolver)
        is Schema.Primitive -> emptyMap()
        is Schema.StringMap<*> -> valueSchema.byRefName(nullable = nullable, outputOptions = outputOptions, resolver = resolver)
        is Schema.Transform<*, *> -> schema.byRefName(nullable = nullable, outputOptions = outputOptions, resolver = resolver)

        is Schema.Union<*> -> {
            val unionName = resolver.resolve(this, this.metadata)
            val mainSchema = mapOf(unionName to toSchemaObject(outputOptions, resolver))

            if (resolver.isCurrentlyProcessing(unionName)) {
                mainSchema
            } else {
                resolver.withProcessing(unionName) {
                    if (outputOptions.inlineRefs) {
                        // For inline mode, only generate nested schemas from cases
                        val nestedSchemas = unsafeCases().fold(emptyMap<String, SchemaObject>()) { acc, case ->
                            acc + case.schema.byRefName(nullable, outputOptions, resolver)
                        }
                        mainSchema + nestedSchemas
                    } else {
                        // For ref mode, generate WithDiscriminator and base schemas
                        val unionSpecificSchemas = unsafeCases().fold(emptyMap<String, SchemaObject>()) { acc, case ->
                            val baseSchemaName = "$unionName.${case.name}"
                            val withDiscriminatorSchemaName = "${baseSchemaName}WithDiscriminator"

                            val baseSchema = case.schema.toSchemaObjectImpl(FieldOptions(), outputOptions, resolver)

                            val discriminatorSchema = SchemaObject(
                                type = "object",
                                properties = mapOf(
                                    key to SchemaObject(
                                        type = "string",
                                        enum = listOf(case.name)
                                    )
                                ),
                                required = listOf(key)
                            )

                            val withDiscriminatorSchema = SchemaObject(
                                allOf = listOf(
                                    discriminatorSchema,
                                    SchemaObject(ref = refPath(baseSchemaName))
                                )
                            )

                            acc + mapOf(
                                baseSchemaName to baseSchema,
                                withDiscriminatorSchemaName to withDiscriminatorSchema
                            )
                        }

                        val nestedSchemas = unsafeCases().fold(emptyMap<String, SchemaObject>()) { acc, case ->
                            acc + case.schema.byRefName(nullable, outputOptions, resolver)
                        }

                        mainSchema + unionSpecificSchemas + nestedSchemas
                    }
                }
            }
        }

        is Schema.Record<*> -> {
            val name = resolver.resolve(this, this.metadata)
            if (resolver.isCurrentlyProcessing(name)) {
                mapOf(name to toSchemaObjectImpl(FieldOptions(), outputOptions, resolver))
            } else {
                resolver.withProcessing(name) {
                    unsafeFields().fold(
                        mapOf(name to toSchemaObjectImpl(FieldOptions(), outputOptions, resolver))
                    ) { acc, field ->
                        acc + field.schema.byRefName(nullable, outputOptions, resolver)
                    }
                }
            }
        }
    }

private fun refPath(name: String): String = "#/components/schemas/$name"
