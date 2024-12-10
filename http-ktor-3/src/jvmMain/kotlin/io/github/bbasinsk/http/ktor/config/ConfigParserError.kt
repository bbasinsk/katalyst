package io.github.bbasinsk.http.ktor.config

import kotlin.collections.joinToString


sealed class ConfigParseError(override val message: String) : Throwable(message = message) {
    data class MissingValue(val path: List<String>) :
        ConfigParseError("Property was not found at: ${renderPath(path)}")

    data class FormatError(val value: String, val format: String, val path: List<String>) :
        ConfigParseError("Expected $format at path: ${renderPath(path)}, but found: $value")

    data class InvalidValue(val path: List<String>, val found: String, val valid: Set<String>) :
        ConfigParseError("Value $found is invalid at $path. Valid values $valid")


    companion object {
        private fun renderPath(path: List<String>): String = path.joinToString(", ")


    }
}
