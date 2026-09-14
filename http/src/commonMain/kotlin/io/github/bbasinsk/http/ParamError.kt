package io.github.bbasinsk.http

import io.github.bbasinsk.schema.Schema

data class ParamError(
    val source: Source,
    val name: String,
    val message: String,
    val index: Int?
) {
    enum class Source { Path, Query, Header }

    companion object {
        val schema: Schema<ParamError> = Schema.record(
            Schema.field(Schema.enumeration<Source>(), "source") { source },
            Schema.field(Schema.string(), "name") { name },
            Schema.field(Schema.string(), "message") { message },
            Schema.field(Schema.int().optional(), "index") { index },
            ::ParamError
        )
    }
}
