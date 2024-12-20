package io.github.bbasinsk.schema.config

import io.github.bbasinsk.schema.Schema
import com.typesafe.config.ConfigFactory
import io.github.bbasinsk.schema.config.ConfigParser.parseSchema
import io.github.bbasinsk.schema.config.ConfigParser.parseSchemaOrThrow
import kotlin.test.Test

class ConfigTest {

    private val config = ConfigFactory.load("config-parser.conf")

    @Test
    fun `valid all`() {
        println(config.parseSchemaOrThrow(Schema.all()))
        println(config.parseSchema(Schema.all()))
    }
}
