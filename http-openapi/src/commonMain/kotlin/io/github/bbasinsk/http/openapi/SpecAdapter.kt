package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.HeaderSchema
import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.ParamsSchema
import io.github.bbasinsk.http.PathSchema
import io.github.bbasinsk.http.QuerySchema
import io.github.bbasinsk.http.ResponseCase
import io.github.bbasinsk.http.ResponseStatus
import io.github.bbasinsk.http.Metadata
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.StandardPrimitive
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

private fun Http<*, *, *, *>.toComponents(): List<Pair<String, SchemaObject>> =
    (output.schemasByStatus() + error.schemasByStatus()).mapNotNull { (_, schema) ->
        schema.useRef { ref -> ref to schema.toSchemaObject() }
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
            is PathSchema.Parameter -> "{${param.name}}"
            is PathSchema.Segment -> param.name
            else -> null
        }
    }.joinToString(separator = "/", prefix = "/")

private fun <Params, Input, Error, Output> Http<Params, Input, Error, Output>.operation(): Operation =
    Operation(
        description = metadata.description?.description,
        deprecated = metadata.deprecated != null,
        operationId = null,
        parameters = params.pathParams(
            optional = false,
            deprecated = false,
            description = null,
            examples = emptyMap()
        ),
        requestBody = requestBody(this.input),
        responses = (output.flatten() + error.flatten()).map { (status, case) ->
            status.code.toString() to case.toResponseObject(status)
        }.toMap()
    )

fun <A, B : A> ResponseCase<A, B>.toResponseObject(status: ResponseStatus): ResponseObject =
    ResponseObject(
        description = status.description,
        content = (schema as Schema<A>).asJsonContent(examples = examples)
    )

private fun <A> ParamsSchema<A>.pathParams(
    optional: Boolean,
    deprecated: Boolean,
    description: String?,
    examples: Map<String, Any?>
): List<Parameter> =
    when (this) {
        is ParamsSchema.Combine<*, *> ->
            this.left.pathParams(optional, deprecated, description, examples) +
                    this.right.pathParams(optional, deprecated, description, examples)

        is PathSchema.Combine<*, *> ->
            this.left.pathParams(optional, deprecated, description, examples) +
                    this.right.pathParams(optional, deprecated, description, examples)

        is PathSchema.Segment, PathSchema.RootSchema -> listOf()
        is PathSchema.Parameter -> listOf(
            Parameter(
                name = name,
                `in` = "path",
                description = description,
                required = true,
                deprecated = deprecated,
                schema = schema.toSchema(),
                examples = (examples as Map<String, A>).mapValues { (key, value) ->
                    ExampleObject(
                        summary = key,
                        value = Schema.Primitive(schema).encodeToJsonElement(value)
                    )
                }.ifEmpty { null }
            )
        )

        is QuerySchema.Optional<*> ->
            schema.pathParams(optional = true, deprecated, description, examples)

        is QuerySchema.Single -> listOf(
            Parameter(
                name = name,
                `in` = "query",
                description = description,
                required = !optional,
                deprecated = deprecated,
                schema = schema.toSchema(),
                examples = (examples as Map<String, A>).mapValues { (key, value) ->
                    ExampleObject(
                        summary = key,
                        value = Schema.Primitive(schema).encodeToJsonElement(value)
                    )
                }.ifEmpty { null }
            )
        )

        is HeaderSchema.Optional<*> ->
            schema.pathParams(optional = true, deprecated, description, examples)

        is HeaderSchema.Single -> listOf(
            Parameter(
                name = name,
                `in` = "header",
                description = description,
                required = !optional,
                deprecated = deprecated,
                schema = schema.primitive.toSchema(),
                examples = (examples as Map<String, A>).mapValues { (key, value) ->
                    ExampleObject(
                        summary = key,
                        value = schema.encodeToJsonElement(value)
                    )
                }.ifEmpty { null }
            )
        )

        is HeaderSchema.WithMetadata ->
            when (val metadata = metadata) {
                is Metadata.Example<*> ->
                    schema.pathParams(
                        optional,
                        deprecated,
                        description,
                        examples = mapOf(metadata.example.first to metadata.example.second) + examples
                    )

                is Metadata.Deprecated ->
                    schema.pathParams(optional, deprecated = true, description, examples)

                is Metadata.Description ->
                    schema.pathParams(optional, deprecated, description = metadata.description, examples)
            }

        is QuerySchema.WithMetadata ->
            when (val metadata = metadata) {
                is Metadata.Example<*> ->
                    schema.pathParams(
                        optional,
                        deprecated,
                        description,
                        examples = mapOf(metadata.example.first to metadata.example.second) + examples
                    )

                is Metadata.Deprecated ->
                    schema.pathParams(optional, deprecated = true, description, examples)

                is Metadata.Description ->
                    schema.pathParams(optional, deprecated, description = metadata.description, examples)
            }

        is PathSchema.WithMetadata ->
            when (val metadata = metadata) {
                is Metadata.Example<*> ->
                    schema.pathParams(
                        optional,
                        deprecated,
                        description,
                        examples = mapOf(metadata.example.first to metadata.example.second) + examples
                    )

                is Metadata.Deprecated ->
                    schema.pathParams(optional, deprecated = true, description, examples)

                is Metadata.Description ->
                    schema.pathParams(optional, deprecated, description = metadata.description, examples)
            }
    }

private fun StandardPrimitive<*>.toSchema(): SchemaObject =
    when (this) {
        StandardPrimitive.Boolean -> SchemaObject(type = "boolean")
        StandardPrimitive.String -> SchemaObject(type = "string")
        StandardPrimitive.Long -> SchemaObject(type = "integer", format = "int64")
        StandardPrimitive.Int -> SchemaObject(type = "integer", format = "int32")
        StandardPrimitive.Float -> SchemaObject(type = "number", format = "float")
        StandardPrimitive.Double -> SchemaObject(type = "number", format = "double")
    }

private fun <A> requestBody(schema: Schema<A>): RequestBody? =
    if (schema is Schema.Empty) {
        null
    } else {
        RequestBody(
            content = schema.asJsonContent(examples = emptyMap()),
            required = schema !is Schema.Optional<*>,
            description = null
        )
    }

// Maps a content type to a schema
private fun <A> Schema<A>.asJsonContent(examples: Map<String, A>): Map<String, MediaTypeObject> {
    val serializedExamples: Map<String, ExampleObject> = examples.mapValues { (key, value) ->
        ExampleObject(
            summary = key,
            value = encodeToJsonElement(value)
        )
    }

    return when (this) {
        is Schema.Bytes -> TODO()
        is Schema.Collection<*> -> mapOf(
            "application/json" to MediaTypeObject(
                schema = toSchemaObject(ref = true),
                examples = serializedExamples.ifEmpty { null }
            )
        )

        is Schema.Default -> schema.asJsonContent(examples)
        is Schema.Empty -> mapOf()
        is Schema.Enumeration -> TODO()
        is Schema.Lazy -> schema().asJsonContent(examples)
        is Schema.Optional<*> -> TODO()
        is Schema.OrElse<*> -> TODO()
        is Schema.Primitive -> mapOf(
            "text/plain" to MediaTypeObject(
                schema = primitive.toSchema(),
                examples = serializedExamples.ifEmpty { null }
            ),
        )

        is Schema.StringMap<*> -> TODO()
        is Schema.Transform<A, *> -> schema.asJsonContent(TODO())
        is Schema.Record<*> -> mapOf(
            "application/json" to MediaTypeObject(
                schema = toSchemaObject(ref = true),
                examples = serializedExamples.ifEmpty { null })
        )

        is Schema.Union<*> -> mapOf(
            "application/json" to MediaTypeObject(
                schema = toSchemaObject(ref = true),
                examples = serializedExamples.ifEmpty { null })
        )
    }
}

internal fun <A> Schema<A>.toSchemaObject(ref: Boolean = false): SchemaObject =
    when (this) {
        is Schema.Empty -> error("Unit schema should not be converted to schema object")
        is Schema.Lazy<A> -> schema().toSchemaObject(ref)
        is Schema.Bytes -> SchemaObject(type = "string", format = "byte")
        is Schema.Collection<*> -> SchemaObject(type = "array", items = itemSchema.toSchemaObject(ref))
        is Schema.Enumeration -> SchemaObject(type = "string", enum = values.map { it.toString() }, format = null)
        is Schema.Default -> schema.toSchemaObject(ref)
        is Schema.Optional<*> -> schema.toSchemaObject(ref)
        is Schema.Primitive -> primitive.toSchema()
        is Schema.OrElse<*> -> preferred.toSchemaObject(ref)
        is Schema.Transform<*, *> -> this.toSchemaObject(ref)
        is Schema.StringMap<*> -> SchemaObject(type = "object", additionalProperties = valueSchema.toSchemaObject(ref))
        is Schema.Union<*> ->
            SchemaObject(
                oneOf = unsafeCases().map { it.schema.toSchemaObject(ref) },
                discriminator = DiscriminatorObject(
                    propertyName = key,
                    mapping = unsafeCases()
                        .mapNotNull { case -> case.schema.useRef { case.name to it } }
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
    when (metadata.name) {
        "UUID" -> SchemaObject(type = "string", format = "uuid")
        "LocalDate" -> SchemaObject(type = "string", format = "date")
        "Instant" -> SchemaObject(type = "string", format = "date-time")
        else -> schema.toSchemaObject(ref)
    }

private fun <B> Schema<*>.useRef(f: (String) -> B): B? =
    when (this) {
        is Schema.Empty -> null
        is Schema.Lazy<*> -> schema().useRef(f)
        is Schema.Bytes -> null
        is Schema.Collection<*> -> null
        is Schema.Enumeration -> null
        is Schema.Default -> schema.useRef(f)
        is Schema.Optional<*> -> schema.useRef(f)
        is Schema.OrElse<*> -> preferred.useRef(f)
        is Schema.Primitive -> null
        is Schema.StringMap<*> -> null
        is Schema.Transform<*, *> -> schema.useRef(f)
        is Schema.Union<*> -> null
        is Schema.Record<*> -> f(metadata.qualifiedName())
    }
