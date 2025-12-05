package io.github.bbasinsk.tuple

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File
import kotlin.jvm.JvmName
import kotlin.test.Ignore
import kotlin.test.Test

class TupleGenerator {

    @Test
    @Ignore // Uncomment to run
    fun generate() {
        val maxArity = 22 // <-- change to your highest N
        val pkg = "io.github.bbasinsk.tuple" // <-- your package
        val outputDir = File("src/commonMain/kotlin")  // <-- where to write files

        // Helper: Build nested Pair<Pair<..., A1>, An> type (clean, no Unit)
        fun buildNestedPairType(n: Int, vars: List<TypeVariableName>): TypeName {
            var t: TypeName = vars[0]
            for (i in 1 until n) {
                t = Pair::class.asClassName().parameterizedBy(t, vars[i])
            }
            return t
        }

        // Helper: Build extractor for position pos in nested Pair of depth n (clean)
        fun buildExtractor(pos: Int, n: Int): String = buildString {
            append("tuples")
            for (level in n downTo 2) {
                if (level == pos) {
                    append(".second")
                    return@buildString
                } else {
                    append(".first")
                }
            }
        }

        for (n in 1..maxArity) {
            val fileBuilder = FileSpec.builder(pkg, "TupleValues$n")
            val allTypeVars = (1..n).map { TypeVariableName("A$it") }

            // --- 1) TupleValuesN data class ---
            val ctor = FunSpec.constructorBuilder().apply {
                allTypeVars.forEachIndexed { i, tv -> addParameter("a${i + 1}", tv) }
            }.build()

            val clsBuilder = TypeSpec.classBuilder("TupleValues$n")
                .addModifiers(KModifier.DATA)
                .addTypeVariables(allTypeVars)
                .primaryConstructor(ctor)

            allTypeVars.forEachIndexed { i, tv ->
                clsBuilder.addProperty(
                    PropertySpec.builder("a${i + 1}", tv)
                        .initializer("a${i + 1}")
                        .build()
                )
            }

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

            // --- 2) tupleValues overloads ---
            if (n == 1) {
                // Arity 1: tupleValues(value: A1) -> TupleValues1<A1>
                fileBuilder.addFunction(
                    FunSpec.builder("tupleValues")
                        .addTypeVariable(TypeVariableName("A1"))
                        .addParameter("value", TypeVariableName("A1"))
                        .returns(ClassName(pkg, "TupleValues1").parameterizedBy(TypeVariableName("A1")))
                        .addStatement("return TupleValues1(value)")
                        .build()
                )
            } else {
                // Arity n: tupleValues(tuples: Pair<Pair<...>, An>) -> TupleValuesN (clean)
                val nestedPairType = buildNestedPairType(n, allTypeVars)
                val extractors = (1..n).map { buildExtractor(it, n) }

                fileBuilder.addFunction(
                    FunSpec.builder("tupleValues")
                        .addTypeVariables(allTypeVars)
                        .addParameter("tuples", nestedPairType)
                        .returns(ClassName(pkg, "TupleValues$n").parameterizedBy(allTypeVars))
                        .addStatement("return TupleValues$n(${extractors.joinToString()})")
                        .build()
                )
            }

            fileBuilder.build().writeTo(outputDir)
        }
    }
}
