package io.github.bbasinsk.schema.jsonschema

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive

object TypeListSerializer : KSerializer<List<String>> {
    private val listSerializer = ListSerializer(String.Companion.serializer())

    override val descriptor: SerialDescriptor = listSerializer.descriptor

    override fun deserialize(decoder: Decoder): List<String> {
        val input = decoder as? JsonDecoder
            ?: error("Can only deserialize with Json")
        val element = input.decodeJsonElement()
        return when (element) {
            is JsonArray ->
                listSerializer.deserialize(decoder)

            is JsonPrimitive ->
                listOf(element.content)

            else ->
                error("Unexpected JSON for type field: $element")
        }
    }

    override fun serialize(encoder: Encoder, value: List<String>) {
        val output = encoder as? JsonEncoder
            ?: error("Can only serialize with Json")
        when {
            value.size == 1 ->
                output.encodeString(value.first())

            else ->
                listSerializer.serialize(encoder, value)
        }
    }
}
