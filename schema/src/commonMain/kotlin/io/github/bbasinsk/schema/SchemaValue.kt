package io.github.bbasinsk.schema

sealed interface SchemaValue {
    data object Null : SchemaValue
    data class Bool(val value: Boolean) : SchemaValue
    data class Integer(val value: Long) : SchemaValue
    data class Decimal(val value: Double) : SchemaValue
    data class Str(val value: String) : SchemaValue
    data class Arr(val values: List<SchemaValue>) : SchemaValue
    data class Obj(val entries: Map<String, SchemaValue>) : SchemaValue
}
