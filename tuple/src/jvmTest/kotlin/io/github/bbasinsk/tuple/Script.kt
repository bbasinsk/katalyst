package io.github.bbasinsk.tuple

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File
import kotlin.jvm.JvmName
import kotlin.test.Test

class TupleGenerator {

    @Test
    fun generate() {
        val maxArity = 10  // <-- change to your highest N
        val pkg = "io.github.bbasinsk.tuple" // <-- your package
        val outputDir = File("src/commonMain/kotlin")  // <-- where to write files

        // helper: build all combinations of size k from 1..n
        fun combinations(n: Int, k: Int): List<List<Int>> {
            if (k == 0) return listOf(emptyList())
            if (n < k) return emptyList()
            val result = mutableListOf<List<Int>>()
            fun recurse(start: Int, picked: List<Int>) {
                if (picked.size == k) {
                    result += picked
                    return
                }
                for (i in start..n) {
                    recurse(i + 1, picked + i)
                }
            }
            recurse(1, emptyList())
            return result
        }

        // helper: nested Pair<A_or_Unit, …> type of depth n, with real type-vars at 'vars'
        fun nestedPairType(n: Int, vars: Set<Int>): TypeName {
            val unit = UNIT
            fun nest(pos: Int): TypeName {
                return if (pos == n) {
                    if (vars.contains(pos)) TypeVariableName("A$pos") else unit
                } else {
                    val left = if (vars.contains(pos)) TypeVariableName("A$pos") else unit
                    val right = nest(pos + 1)
                    Pair::class.asClassName().parameterizedBy(left, right)
                }
            }
            return nest(1)
        }

        // helper: build the extractor chain for position 'pos' in 1..n
        fun extractExpr(pos: Int, n: Int): String {
            val sb = StringBuilder("tuples")
            repeat(pos - 1) { sb.append(".second") }
            if (pos < n) sb.append(".first")
            return sb.toString()
        }

        for (n in 1..maxArity) {
            val fileBuilder = FileSpec.builder(pkg, "TupleValues$n")

            // --- 1) Emit TupleValuesN class and its applyTo method ---
            // Type vars A1..An
            val allTypeVars = (1..n).map { TypeVariableName("A$it") }

            // build constructor (a1..an)
            val ctor = FunSpec.constructorBuilder().apply {
                allTypeVars.forEachIndexed { i, tv -> addParameter("a${i + 1}", tv) }
            }.build()

            val clsBuilder = TypeSpec.classBuilder("TupleValues$n")
                .addModifiers(KModifier.DATA)
                .addTypeVariables(allTypeVars)
                .primaryConstructor(ctor)

            // add `val aX: AX` props
            allTypeVars.forEachIndexed { i, tv ->
                clsBuilder.addProperty(
                    PropertySpec.builder("a${i + 1}", tv)
                        .initializer("a${i + 1}")
                        .build()
                )
            }

            // add applyTo
            clsBuilder.addFunction(
                FunSpec.builder("applyTo")
                    .addTypeVariable(TypeVariableName("B"))
                    .addParameter(
                        "f",
                        LambdaTypeName.get(
                            parameters = allTypeVars.toTypedArray(),
                            returnType = TypeVariableName("B")
                        )
                    )
                    .returns(TypeVariableName("B"))
                    .addStatement("return f(${(1..n).joinToString { "a$it" }})")
                    .build()
            )

            fileBuilder.addType(clsBuilder.build())

            // --- 2) Emit all tupleValues overloads for this n ---
            for (k in 1..n) {
                combinations(n, k).forEachIndexed { idx, subset ->
                    val vars = subset.toSet()
                    val tvars = subset.map { TypeVariableName("A$it") }
                    val paramT = nestedPairType(n, vars)
                    val returnT = ClassName(pkg, "TupleValues$k").parameterizedBy(tvars)
                    val args = subset.joinToString { extractExpr(it, n) }

                    val fn = FunSpec.builder("tupleValues")
                        .addAnnotation(
                            AnnotationSpec.builder(JvmName::class)
                                .addMember("%S", "tupleValues_${n}_${k}_${idx + 1}")
                                .build()
                        )
                        .addTypeVariables(tvars)
                        .addParameter("tuples", paramT)
                        .returns(returnT)
                        .addStatement("return %T($args)", returnT)
                        .build()

                    fileBuilder.addFunction(fn)
                }
            }

            // write this file out
            fileBuilder.build().writeTo(outputDir)
        }
    }
}
