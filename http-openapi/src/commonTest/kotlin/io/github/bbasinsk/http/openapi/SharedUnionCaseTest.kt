package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonElement
import io.github.bbasinsk.schema.json.kotlinx.encodeToJsonElement
import io.github.bbasinsk.validation.Validation
import kotlinx.serialization.json.JsonObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class SharedUnionCaseTest {
    private val info = Info(title = "API", version = "1.0.0")
    private val prefix = "io.github.bbasinsk.http.openapi."
    private val canonicalBase = "${prefix}Command.Rename"
    private val canonicalWrapper = "${canonicalBase}WithDiscriminator"
    private val canonicalWrapperRef = "#/components/schemas/$canonicalWrapper"

    private val rename: Schema<Command.Rename> = with(Schema) {
        record(field(string(), "name") { name }, Command::Rename)
    }
    private val command: Schema<Command> = with(Schema) { union(case(rename, "Rename")) }
    private val event: Schema<Event> = with(Schema) {
        union(case(lazy { rename }.description("Rename event"), "Rename"))
    }
    private val commandRequest: Schema<CommandRequest> = with(Schema) {
        record(field(command, "command") { command }, ::CommandRequest)
    }
    private val eventRequest: Schema<EventRequest> = with(Schema) {
        record(field(event, "event") { event }, ::EventRequest)
    }
    private val commandHttp = endpoint("commands", commandRequest)
    private val eventHttp = endpoint("events", eventRequest)

    @Test
    fun `shared nested case has one canonical record and wrapper`() {
        val schemas = listOf(commandHttp, eventHttp).toOpenApiSpec(info).components.schemas
        assertEquals(
            setOf("${prefix}CommandRequest", "${prefix}EventRequest", "${prefix}Command", "${prefix}Event",
                canonicalBase, canonicalWrapper),
            schemas.keys
        )
        val base = schemas.getValue(canonicalBase)
        assertEquals(mapOf("name" to SchemaObject(type = "string")), base.properties)
        assertEquals(listOf("name"), base.required)
        assertEquals(
            listOf(
                SchemaObject(type = "object", properties = mapOf("type" to SchemaObject(type = "string", enum = listOf("Rename"))), required = listOf("type")),
                SchemaObject(ref = "#/components/schemas/$canonicalBase")
            ),
            schemas.getValue(canonicalWrapper).allOf
        )
        for (unionName in listOf("Command", "Event")) {
            val union = schemas.getValue("$prefix$unionName")
            assertEquals(listOf(SchemaObject(ref = canonicalWrapperRef)), union.oneOf)
            assertEquals("type", union.discriminator?.propertyName)
            assertEquals(mapOf("Rename" to canonicalWrapperRef), union.discriminator?.mapping)
        }
        assertEquals("#/components/schemas/${prefix}Command", schemas.getValue("${prefix}CommandRequest").properties?.get("command")?.ref)
        assertEquals("#/components/schemas/${prefix}Event", schemas.getValue("${prefix}EventRequest").properties?.get("event")?.ref)
        assertReferencesResolve(schemas)
        assertEquals(schemas, listOf(eventHttp, commandHttp).toOpenApiSpec(info).components.schemas)
    }

    @Test
    fun `both envelopes encode and decode the same required payload`() {
        val value = Command.Rename("Example")
        val commandValue = CommandRequest(value)
        val eventValue = EventRequest(value)
        val commandJson = commandRequest.encodeToJsonElement(commandValue) as JsonObject
        val eventJson = eventRequest.encodeToJsonElement(eventValue) as JsonObject
        val payload = OpenApiJson.parseToJsonElement("""{"type":"Rename","name":"Example"}""")
        assertEquals(payload, commandJson.getValue("command"))
        assertEquals(payload, eventJson.getValue("event"))
        assertEquals(Validation.valid(commandValue), commandRequest.decodeFromJsonElement(commandJson))
        assertEquals(Validation.valid(eventValue), eventRequest.decodeFromJsonElement(eventJson))

        val missingName = JsonObject((payload as JsonObject) - "name")
        assertTrue(commandRequest.decodeFromJsonElement(JsonObject(mapOf("command" to missingName))) is Validation.Invalid)
        assertTrue(eventRequest.decodeFromJsonElement(JsonObject(mapOf("event" to missingName))) is Validation.Invalid)
    }

    @Test
    fun `different discriminator keys and values keep distinct wrappers`() {
        val legacy: Schema<LegacyEvent> = with(Schema) { union(case(rename, "Rename"), key = "kind") }
        val relabeled: Schema<RelabeledEvent> = with(Schema) { union(case(rename, "Name/Changed")) }
        val dotted: Schema<DottedEvent> = with(Schema) { union(case(rename, "Name.Changed")) }
        val endpoints = listOf(commandHttp, eventHttp, endpoint("legacy", legacy), endpoint("relabeled", relabeled), endpoint("dotted", dotted))
        val schemas = endpoints.toOpenApiSpec(info).components.schemas
        val refs = mutableSetOf(canonicalWrapperRef)
        for ((unionName, key, value) in listOf(
            Triple("LegacyEvent", "kind", "Rename"),
            Triple("RelabeledEvent", "type", "Name/Changed"),
            Triple("DottedEvent", "type", "Name.Changed")
        )) {
            val union = schemas.getValue("$prefix$unionName")
            val ref = union.discriminator!!.mapping!!.getValue(value)
            assertEquals(key, union.discriminator.propertyName)
            assertEquals(listOf(SchemaObject(ref = ref)), union.oneOf)
            assertTrue(refs.add(ref), "Incompatible discriminator reused $ref")
            val wrapper = schemas.getValue(ref.removePrefix("#/components/schemas/"))
            assertEquals(
                listOf(
                    SchemaObject(type = "object", properties = mapOf(key to SchemaObject(type = "string", enum = listOf(value))), required = listOf(key)),
                    SchemaObject(ref = "#/components/schemas/$canonicalBase")
                ),
                wrapper.allOf
            )
        }
        assertReferencesResolve(schemas)
        assertEquals(schemas, endpoints.reversed().toOpenApiSpec(info).components.schemas)
    }

    @Test
    fun `distinct DTOs with the same simple name and fields stay distinct`() {
        val otherRename: Schema<OtherCommand.Rename> = with(Schema) {
            record(field(string(), "name") { name }, OtherCommand::Rename)
        }
        val otherCommand: Schema<OtherCommand> = with(Schema) { union(case(otherRename, "Rename")) }
        val schemas = listOf(commandHttp, endpoint("other", otherCommand)).toOpenApiSpec(info).components.schemas
        val otherBase = "${prefix}OtherCommand.Rename"
        assertEquals(schemas.getValue(canonicalBase), schemas.getValue(otherBase))
        val otherRef = schemas.getValue("${prefix}OtherCommand").discriminator!!.mapping!!.getValue("Rename")
        assertNotEquals(canonicalWrapperRef, otherRef)
        val otherWrapper = schemas.getValue(otherRef.removePrefix("#/components/schemas/"))
        assertEquals("#/components/schemas/$otherBase", otherWrapper.allOf?.last()?.ref)
        assertReferencesResolve(schemas)
    }

    private fun <A> endpoint(path: String, schema: Schema<A>) = Http.post { Root / path }
        .input { json { schema } }
        .output { status(Ok) { json { int() } } }

    private fun assertReferencesResolve(schemas: Map<String, SchemaObject>) {
        val definedRefs = schemas.keys.map { "#/components/schemas/$it" }.toSet()
        fun visit(schema: SchemaObject) {
            schema.ref?.let { assertTrue(it in definedRefs, "Unresolved ref: $it") }
            schema.discriminator?.mapping?.values?.forEach { assertTrue(it in definedRefs, "Unresolved mapping: $it") }
            schema.properties?.values?.forEach { visit(it) }
            schema.items?.let { visit(it) }
            schema.additionalProperties?.let { visit(it) }
            (schema.oneOf.orEmpty() + schema.anyOf.orEmpty() + schema.allOf.orEmpty()).forEach { visit(it) }
        }
        schemas.values.forEach { visit(it) }
    }
}

sealed interface Command {
    data class Rename(val name: String) : Command, Event, LegacyEvent, RelabeledEvent, DottedEvent
}
sealed interface Event
sealed interface LegacyEvent
sealed interface RelabeledEvent
sealed interface DottedEvent
sealed interface OtherCommand {
    data class Rename(val name: String) : OtherCommand
}
data class CommandRequest(val command: Command)
data class EventRequest(val event: Event)
