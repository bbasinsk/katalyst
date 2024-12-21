package io.github.bbasinsk.schema


sealed interface Schema<A> {

    // For non-composite types (ie. primitives, enums, etc.)
    // For use in csv values, http parameters, or other use-case where a single value is expected
    sealed interface Primitive<A> : Schema<A> {
        data object Boolean : Primitive<kotlin.Boolean>
        data object String : Primitive<kotlin.String>
        data object Int : Primitive<kotlin.Int>
        data object Long : Primitive<kotlin.Long>
        data object Double : Primitive<kotlin.Double>
        data object Float : Primitive<kotlin.Float>
        data class Enumeration<A>(val metadata: Metadata<A>, val values: List<A>) : Primitive<A>

//        data class Optional<A>(val schema: Primitive<A>) : Primitive<A?>
//        data class Default<A>(val schema: Primitive<A>, val default: A) : Primitive<A>
//        data class OrElse<A>(val preferred: Primitive<A>, val fallback: Primitive<A>) : Primitive<A>
//        data class Transform<A, B>(
//            val metadata: Metadata<A>,
//            val schema: Primitive<B>,
//            val encode: (A) -> B,
//            val decode: (B) -> Result<A>
//        ) : Primitive<A>

        @Suppress("UNCHECKED_CAST")
        fun decodeString(str: kotlin.String): Result<A> =
            when (this) {
                is String -> Result.success(str as A)
                is Int -> runCatching { str.toInt() as A }
                is Long -> runCatching { str.toLong() as A }
                is Float -> runCatching { str.toFloat() as A }
                is Double -> runCatching { str.toDouble() as A }
                is Boolean -> runCatching { str.toBooleanStrict() as A }
                is Enumeration<*> -> runCatching { values.first { it.toString() == str } as A }
            }

        fun name(): kotlin.String =
            when (this) {
                is Boolean -> "Boolean"
                is String -> "String"
                is Int -> "Int"
                is Long -> "Long"
                is Float -> "Float"
                is Double -> "Double"
                is Enumeration<*> -> metadata.name
//                is Optional<*> -> schema.name()
//                is Default<*> -> schema.name()
//                is OrElse<*> -> preferred.name()
//                is Transform<*, *> -> metadata.qualifiedName()
            }
    }

    data object Empty : Schema<Nothing?>
    data object Bytes : Schema<ByteArray>
    data class Lazy<A>(val schema: () -> Schema<A>) : Schema<A>
    data class Optional<A>(val schema: Schema<A>) : Schema<A?>
    data class Default<A>(val schema: Schema<A>, val default: A) : Schema<A>
    data class OrElse<A>(val preferred: Schema<A>, val fallback: Schema<A>) : Schema<A>

    data class Transform<A, B>(
        val metadata: Metadata<A>,
        val schema: Schema<B>,
        val encode: (A) -> B,
        val decode: (B) -> Result<A>
    ) : Schema<A>

    data class Collection<A>(val itemSchema: Schema<A>) : Schema<List<A>>
    data class StringMap<B>(val valueSchema: Schema<B>) : Schema<Map<String, B>>

    //    data class Enumeration<A>(val metadata: Metadata<A>, val values: List<A>) : Schema<A>
//    data class Primitive<A>(val primitive: StandardPrimitive<A>) : Schema<A>
    sealed interface Union<A> : Schema<A> {
        val key: String
        fun unsafeCases(): List<Case<A, *>>
    }

    sealed interface Record<A> : Schema<A> {
        val metadata: Metadata<A>
        fun unsafeFields(): List<Field<A, *>>
        fun unsafeConstruct(values: List<Any?>): A
    }

    companion object {
        fun <A> lazy(schema: () -> Schema<A>): Schema<A> = Lazy(schema)
        fun empty(): Schema<Nothing?> = Empty
        fun byteArray(): Schema<ByteArray> = Bytes

        fun boolean(): Primitive<Boolean> = Primitive.Boolean
        fun string(): Primitive<String> = Primitive.String
        fun int(): Primitive<Int> = Primitive.Int
        fun long(): Primitive<Long> = Primitive.Long
        fun double(): Primitive<Double> = Primitive.Double
        fun float(): Primitive<Float> = Primitive.Float

        fun <A> list(schema: Schema<A>): Schema<List<A>> = Collection(schema)
        fun <B> stringMap(valueSchema: Schema<B>): Schema<Map<String, B>> = StringMap(valueSchema)

        inline fun <reified A : Enum<A>> enumeration(): Primitive<A> =
            Primitive.Enumeration(
                metadata = A::class.toMetadata(),
                values = enumValues<A>().toList()
            )

        fun <A, B> field(schema: Schema<B>, name: String, extract: A.() -> B): Field<A, B> =
            Field(name, schema, extract)

        inline fun <A, reified B : A> case(
            schema: Schema<B>,
            name: String = requireNotNull(B::class.simpleName) { "Cannot get name of type ${B::class}" },
            noinline deconstruct: (A) -> B? = { it as? B }
        ): Case<A, B> =
            Case(
                name = name,
                schema = schema,
                deconstruct = deconstruct
            )

        inline fun <reified A : Any, B1> record(
            field1: Field<A, B1>,
            noinline construct: (B1) -> (A)
        ): Schema<A> = Record1(A::class.toMetadata(), field1, construct)

        inline fun <reified A : Any, B1, B2> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            noinline construct: (B1, B2) -> (A)
        ): Schema<A> = Record2(A::class.toMetadata(), field1, field2, construct)

        inline fun <reified A : Any, B1, B2, B3> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            noinline construct: (B1, B2, B3) -> (A)
        ): Schema<A> = Record3(A::class.toMetadata(), field1, field2, field3, construct)

        inline fun <reified A : Any, B1, B2, B3, B4> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            noinline construct: (B1, B2, B3, B4) -> (A)
        ): Schema<A> = Record4(A::class.toMetadata(), field1, field2, field3, field4, construct)

        inline fun <reified A : Any, B1, B2, B3, B4, B5> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            noinline construct: (B1, B2, B3, B4, B5) -> (A)
        ): Schema<A> = Record5(A::class.toMetadata(), field1, field2, field3, field4, field5, construct)

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            noinline construct: (B1, B2, B3, B4, B5, B6) -> (A)
        ): Schema<A> = Record6(A::class.toMetadata(), field1, field2, field3, field4, field5, field6, construct)

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7) -> (A)
        ): Schema<A> = Record7(A::class.toMetadata(), field1, field2, field3, field4, field5, field6, field7, construct)

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7, B8> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8) -> (A)
        ): Schema<A> =
            Record8(A::class.toMetadata(), field1, field2, field3, field4, field5, field6, field7, field8, construct)

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7, B8, B9> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            field9: Field<A, B9>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9) -> (A)
        ): Schema<A> = Record9(
            A::class.toMetadata(),
            field1,
            field2,
            field3,
            field4,
            field5,
            field6,
            field7,
            field8,
            field9,
            construct
        )

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            field9: Field<A, B9>,
            field10: Field<A, B10>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10) -> (A)
        ): Schema<A> = Record10(
            A::class.toMetadata(),
            field1,
            field2,
            field3,
            field4,
            field5,
            field6,
            field7,
            field8,
            field9,
            field10,
            construct
        )

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            field9: Field<A, B9>,
            field10: Field<A, B10>,
            field11: Field<A, B11>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11) -> (A)
        ): Schema<A> = Record11(
            A::class.toMetadata(),
            field1,
            field2,
            field3,
            field4,
            field5,
            field6,
            field7,
            field8,
            field9,
            field10,
            field11,
            construct
        )

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            field9: Field<A, B9>,
            field10: Field<A, B10>,
            field11: Field<A, B11>,
            field12: Field<A, B12>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12) -> (A)
        ): Schema<A> = Record12(
            A::class.toMetadata(),
            field1,
            field2,
            field3,
            field4,
            field5,
            field6,
            field7,
            field8,
            field9,
            field10,
            field11,
            field12,
            construct
        )

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            field9: Field<A, B9>,
            field10: Field<A, B10>,
            field11: Field<A, B11>,
            field12: Field<A, B12>,
            field13: Field<A, B13>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13) -> (A)
        ): Schema<A> = Record13(
            A::class.toMetadata(),
            field1,
            field2,
            field3,
            field4,
            field5,
            field6,
            field7,
            field8,
            field9,
            field10,
            field11,
            field12,
            field13,
            construct
        )

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            field9: Field<A, B9>,
            field10: Field<A, B10>,
            field11: Field<A, B11>,
            field12: Field<A, B12>,
            field13: Field<A, B13>,
            field14: Field<A, B14>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14) -> (A)
        ): Schema<A> = Record14(
            A::class.toMetadata(),
            field1,
            field2,
            field3,
            field4,
            field5,
            field6,
            field7,
            field8,
            field9,
            field10,
            field11,
            field12,
            field13,
            field14,
            construct
        )

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            field9: Field<A, B9>,
            field10: Field<A, B10>,
            field11: Field<A, B11>,
            field12: Field<A, B12>,
            field13: Field<A, B13>,
            field14: Field<A, B14>,
            field15: Field<A, B15>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15) -> (A)
        ): Schema<A> = Record15(
            A::class.toMetadata(),
            field1,
            field2,
            field3,
            field4,
            field5,
            field6,
            field7,
            field8,
            field9,
            field10,
            field11,
            field12,
            field13,
            field14,
            field15,
            construct
        )

        inline fun <reified A : Any, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            field9: Field<A, B9>,
            field10: Field<A, B10>,
            field11: Field<A, B11>,
            field12: Field<A, B12>,
            field13: Field<A, B13>,
            field14: Field<A, B14>,
            field15: Field<A, B15>,
            field16: Field<A, B16>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16) -> (A)
        ): Schema<A> = Record16(
            A::class.toMetadata(),
            field1,
            field2,
            field3,
            field4,
            field5,
            field6,
            field7,
            field8,
            field9,
            field10,
            field11,
            field12,
            field13,
            field14,
            field15,
            field16,
            construct
        )

        fun <A, B1 : A> union(
            case1: Case<A, B1>,
            key: String = "type"
        ): Schema<A> = Union1(key, case1)

        fun <A, B1 : A, B2 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            key: String = "type"
        ): Schema<A> = Union2(key, case1, case2)

        fun <A, B1 : A, B2 : A, B3 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            key: String = "type"
        ): Schema<A> = Union3(key, case1, case2, case3)

        fun <A, B1 : A, B2 : A, B3 : A, B4 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            key: String = "type"
        ): Schema<A> = Union4(key, case1, case2, case3, case4)

        fun <A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            key: String = "type"
        ): Schema<A> = Union5(key, case1, case2, case3, case4, case5)

        fun <A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            case6: Case<A, B6>,
            key: String = "type"
        ): Schema<A> = Union6(key, case1, case2, case3, case4, case5, case6)

        fun <A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            case6: Case<A, B6>,
            case7: Case<A, B7>,
            key: String = "type"
        ): Schema<A> = Union7(key, case1, case2, case3, case4, case5, case6, case7)

        fun <A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            case6: Case<A, B6>,
            case7: Case<A, B7>,
            case8: Case<A, B8>,
            key: String = "type"
        ): Schema<A> = Union8(key, case1, case2, case3, case4, case5, case6, case7, case8)

        fun <A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            case6: Case<A, B6>,
            case7: Case<A, B7>,
            case8: Case<A, B8>,
            case9: Case<A, B9>,
            key: String = "type"
        ): Schema<A> = Union9(key, case1, case2, case3, case4, case5, case6, case7, case8, case9)

        fun <A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            case6: Case<A, B6>,
            case7: Case<A, B7>,
            case8: Case<A, B8>,
            case9: Case<A, B9>,
            case10: Case<A, B10>,
            key: String = "type"
        ): Schema<A> = Union10(key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10)
    }
}

//inline fun <A, reified B : Any> Schema.Primitive<A>.transform(
//    noinline encode: (B) -> A,
//    noinline decode: (A) -> B
//): Schema.Primitive<B> =
//    Schema.Primitive.Transform(
//        metadata = B::class.toMetadata(),
//        schema = this,
//        encode = encode
//    ) { runCatching { decode(it) } }

inline fun <A, reified B : Any> Schema<A>.transform(noinline encode: (B) -> A, noinline decode: (A) -> B): Schema<B> =
    Schema.Transform(
        metadata = B::class.toMetadata(),
        schema = this,
        encode = encode
    ) { runCatching { decode(it) } }

//inline fun <A> Schema.Primitive<A>.optional(): Schema.Primitive<A?> =
//    Schema.Primitive.Optional(this)

fun <A> Schema<A>.optional(): Schema<A?> =
    Schema.Optional(this)

//inline fun <A> Schema.Primitive<A>.default(default: A): Schema.Primitive<A> =
//    Schema.Primitive.Default(this, default)

fun <A> Schema<A>.default(default: A): Schema<A> =
    Schema.Default(this, default)

//inline fun <A> Schema.Primitive<A>.orElse(fallback: Schema.Primitive<A>): Schema.Primitive<A> =
//    Schema.Primitive.OrElse(this, fallback)

fun <A> Schema<A>.orElse(fallback: Schema<A>): Schema<A> =
    Schema.OrElse(this, fallback)
