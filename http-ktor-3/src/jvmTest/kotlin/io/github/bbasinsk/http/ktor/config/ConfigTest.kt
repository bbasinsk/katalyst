package io.github.bbasinsk.http.ktor.config

import io.github.bbasinsk.http.ktor.config.ConfigParser.parseSchemaOrThrow
import io.github.bbasinsk.schema.Schema
import io.ktor.server.config.ConfigLoader
import kotlin.test.Test

class ConfigTest {

    private val config = ConfigLoader.load("config-parser.conf")

    @Test
    fun `valid all`() {
        val parsed = config.parseSchemaOrThrow(Schema.all())
        println(parsed)
    }
}
