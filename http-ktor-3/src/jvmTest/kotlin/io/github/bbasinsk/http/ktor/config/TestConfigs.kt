package io.github.bbasinsk.http.ktor.config

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.transform
import kotlin.collections.firstOrNull
import kotlin.collections.map
import kotlin.text.toList
import kotlin.toString

sealed interface OneOf
data class OneOfA(val a: String) : OneOf
data class OneOfB(val b: String) : OneOf

fun Schema.Companion.oneOfA() =
    record(
        field(string(), "a") { a },
        ::OneOfA
    )

fun Schema.Companion.oneOfB() =
    record(
        field(string(), "b") { b },
        ::OneOfB
    )

fun Schema.Companion.oneOf(): Schema<OneOf> =
    union(
        case(oneOfA()),
        case(oneOfB())
    )

enum class Enum {
    A, B
}

fun Schema.Companion.myEnum(): Schema<Enum> = enumeration()

data class Build1(val a: String)
data class Build2(val a: String, val b: String)
data class Build3(val a: String, val b: String, val c: String)

fun Schema.Companion.build1(): Schema<Build1> =
    record(
        field(string(), "a") { a },
        ::Build1
    )

fun Schema.Companion.build2(): Schema<Build2> =
    record(
        field(string(), "a") { a },
        field(string(), "b") { b },
        ::Build2
    )

fun Schema.Companion.build3(): Schema<Build3> =
    record(
        field(string(), "a") { a },
        field(string(), "b") { b },
        field(string(), "c") { c },
        ::Build3
    )


data class All(
    val string: String,
    val int: Int,
    val stringList: List<String>,
    val intList: List<Int>,
    val objList: List<Build2>,
    val stringMap: Map<String, Int>,
    val oneOfA: OneOf,
    val oneOfB: OneOf,
    val build1: Build1,
    val build2: Build2,
    val build3: Build3
)

fun Schema.Companion.all() =
    record(
        field(string(), "string") { string },
        field(int(), "int") { int },
        field(list(string()), "stringList") { stringList },
        field(list(int()), "intList") { intList },
        field(list(build2()), "objList") { objList },
        field(stringMap(int()), "stringMap") { stringMap },
        field(oneOf(), "oneOfA") { oneOfA },
        field(oneOf(), "oneOfB") { oneOfB },
        field(build1(), "build1") { build1 },
        field(build2(), "build2") { build2 },
        field(build3(), "build3") { build3 },
        ::All
    )

fun Schema.Companion.customList(): Schema<List<Char?>> =
    list(string()).transform({ it.map { it.toString() } }, { it.firstOrNull()?.toList() ?: emptyList() })
