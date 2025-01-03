package io.github.bbasinsk.http.openapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

@Serializable
data class OpenAPI(
    val openapi: String = "3.0.0",
    val info: Info,
    val servers: List<Server> = emptyList(),
    val paths: Map<String, Map<String, Operation>> = emptyMap(),
    val components: Components = Components(emptyMap())
)

@Serializable
data class Info(
    val title: String,
    val version: String,
    val description: String? = null,
    val termsOfService: String? = null,
    val contact: Contact? = null,
    val license: License? = null
)

@Serializable
data class Contact(
    val name: String?,
    val url: String?,
    val email: String?
)

@Serializable
data class License(
    val name: String?,
    val url: String?
)

@Serializable
data class Server(
    val url: String
)

@Serializable
data class Operation(
    val summary: String?,
    val tags: List<String>?,
    val operationId: String?,
    val parameters: List<Parameter>,
    val requestBody: RequestBody?,
    val responses: Map<String, ResponseObject>,
    val deprecated: Boolean?,
)

@Serializable
data class Parameter(
    val name: String,
    val `in`: String,
    val description: String?,
    val required: Boolean,
    val deprecated: Boolean,
    val schema: SchemaObject,
    val examples: Map<String, ExampleObject>?,
)

@Serializable
data class ExampleObject(
    val summary: String?,
    val value: JsonElement
)

@Serializable
data class RequestBody(
    val description: String?,
    val required: Boolean,
    val content: Map<String, MediaTypeObject>
)

@Serializable
data class SchemaObject(
    @SerialName("\$ref") val ref: String? = null,
    val type: String? = null,
    val format: String? = null,
    val items: SchemaObject? = null,
    val properties: Map<String, SchemaObject>? = null,
    val required: List<String>? = null,
    val enum: List<String>? = null,
    val oneOf: List<SchemaObject>? = null,
    val anyOf: List<SchemaObject>? = null,
    val discriminator: DiscriminatorObject? = null,
    val additionalProperties: SchemaObject? = null,
    val description: String? = null, // TODO
)

@Serializable
data class DiscriminatorObject(val propertyName: String, val mapping: Map<String, String>?)

@Serializable
data class Components(
    val schemas: Map<String, SchemaObject>
)

@Serializable
data class ResponseObject(
    val description: String?,
    val content: Map<String, MediaTypeObject>?
)

@Serializable
data class MediaTypeObject(
    val schema: SchemaObject,
    val examples: Map<String, ExampleObject>?,
)

val OpenApiJson = Json {
    prettyPrint = true
    encodeDefaults = true
    explicitNulls = false // needed to drop fields on Schema Object
}
