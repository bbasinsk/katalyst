package io.github.bbasinsk.schema


sealed interface Schema<A> {

    // For non-composite types (ie. primitives, enums, etc.)
    // For use in csv values, http parameters, or other use-case where a single value is expected
    sealed class Primitive<A>(val name: kotlin.String) : Schema<A> {
        data object Boolean : Primitive<kotlin.Boolean>("Boolean")
        data object String : Primitive<kotlin.String>("String")
        data object Int : Primitive<kotlin.Int>("Int")
        data object Long : Primitive<kotlin.Long>("Long")
        data object Double : Primitive<kotlin.Double>("Double")
        data object Float : Primitive<kotlin.Float>("Float")
        data class Enumeration<A>(val metadata: ObjectMetadata<A>, val values: List<A>) : Primitive<A>(metadata.name)
    }

    data object Empty : Schema<Nothing?>
    data object Bytes : Schema<ByteArray>
    data class Lazy<A>(val schema: () -> Schema<A>) : Schema<A>
    data class Optional<A>(val schema: Schema<A>) : Schema<A?>
    data class Metadata<A>(val schema: Schema<A>, val metadata: FieldMetadata) : Schema<A>
    data class Default<A>(val schema: Schema<A>, val default: A) : Schema<A>
    data class Collection<A>(val itemSchema: Schema<A>) : Schema<List<A>>
    data class StringMap<B>(val valueSchema: Schema<B>) : Schema<Map<String, B>>

    data class OrElse<A, B>(
        val preferred: Schema<A>,
        val fallback: Schema<B>,
        val metadata: ObjectMetadata<B>,
        val decode: (B) -> Result<A>
    ) : Schema<A> {
        @Suppress("UNCHECKED_CAST")
        fun unsafeDecode(value: Any?): Result<A> = (decode as (Any?) -> Result<A>)(value)
    }

    data class Transform<A, B>(
        val metadata: ObjectMetadata<A>,
        val schema: Schema<B>,
        val encode: (A) -> B,
        val decode: (B) -> Result<A>
    ) : Schema<A> {
        @Suppress("UNCHECKED_CAST")
        fun unsafeDecode(b: Any?): Result<A> = (decode as (Any?) -> Result<A>)(b)
    }

    sealed interface Union<A> : Schema<A> {
        val metadata: ObjectMetadata<A>
        val key: String
        fun unsafeCases(): List<Case<A, *>>
    }

    sealed interface Record<A> : Schema<A> {
        val metadata: ObjectMetadata<A>
        fun unsafeFields(): List<Field<A, *>>
        fun unsafeConstruct(values: List<Any?>): A
    }

    fun optional(): Schema<A?> = Optional(this)
    fun default(default: A): Schema<A> = Default(this, default)
    fun description(description: String) = Metadata(this, FieldMetadata(description = description))

    fun isPrimitive(): Boolean = when (this) {
        is Bytes -> true
        is Primitive -> true
        is Union -> false
        is Record -> false
        is Empty -> false
        is StringMap<*> -> false
        is Collection<*> -> false
        is Default -> schema.isPrimitive()
        is Lazy -> schema().isPrimitive()
        is Metadata -> schema.isPrimitive()
        is Optional<*> -> schema.isPrimitive()
        is OrElse<*, *> -> preferred.isPrimitive()
        is Transform<A, *> -> schema.isPrimitive()
    }

    fun isRequired(): Boolean = when (this) {
        is Bytes -> true
        is Default -> false
        is Empty -> false
        is Union -> true
        is Record -> true
        is Primitive -> true
        is StringMap<*> -> true
        is Collection<*> -> true
        is Metadata -> schema.isRequired()
        is Optional<*> -> false
        is Lazy<*> -> schema().isRequired()
        is OrElse<*, *> -> preferred.isRequired()
        is Transform<*, *> -> schema.isRequired()
    }

    fun collectionItemSchema(): Schema<*>? = when (this) {
        is Collection<*> -> itemSchema
        is Default -> schema.collectionItemSchema()
        is Optional<*> -> schema.collectionItemSchema()
        is Metadata -> schema.collectionItemSchema()
        is Lazy<*> -> schema().collectionItemSchema()
        is OrElse<*, *> -> preferred.collectionItemSchema()
        is Transform<*, *> -> schema.collectionItemSchema()
        is Record -> null
        is Union -> null
        is Primitive -> null
        is Bytes -> null
        is Empty -> null
        is StringMap<*> -> null
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
                metadata = metadataFromType<A>(),
                values = enumValues<A>().toList()
            )

        fun <A, B> field(schema: Schema<B>, name: String, extract: A.() -> B): Field<A, B> =
            Field(name, schema) { with(it) { extract() } }

        inline fun <reified A> fix(
            crossinline builder: Companion.(Schema<A>, ObjectMetadata<A>) -> Schema<A>
        ): Schema<A> {
            val metadata = metadataFromType<A>()
            lateinit var self: Schema<A>
            self = Schema.builder(lazy { self }, metadata)
            return self
        }

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

        inline fun <reified A, B1> record(
            field1: Field<A, B1>,
            noinline construct: (B1) -> (A)
        ): Schema<A> = Record1(metadataFromType(), field1, construct)

        inline fun <reified A, B1, B2> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            noinline construct: (B1, B2) -> (A)
        ): Schema<A> = Record2(metadataFromType(), field1, field2, construct)

        inline fun <reified A, B1, B2, B3> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            noinline construct: (B1, B2, B3) -> (A)
        ): Schema<A> = Record3(metadataFromType(), field1, field2, field3, construct)

        inline fun <reified A, B1, B2, B3, B4> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            noinline construct: (B1, B2, B3, B4) -> (A)
        ): Schema<A> = Record4(metadataFromType(), field1, field2, field3, field4, construct)

        inline fun <reified A, B1, B2, B3, B4, B5> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            noinline construct: (B1, B2, B3, B4, B5) -> (A)
        ): Schema<A> = Record5(metadataFromType(), field1, field2, field3, field4, field5, construct)

        inline fun <reified A, B1, B2, B3, B4, B5, B6> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            noinline construct: (B1, B2, B3, B4, B5, B6) -> (A)
        ): Schema<A> = Record6(metadataFromType(), field1, field2, field3, field4, field5, field6, construct)

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7) -> (A)
        ): Schema<A> = Record7(metadataFromType(), field1, field2, field3, field4, field5, field6, field7, construct)

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8> record(
            field1: Field<A, B1>,
            field2: Field<A, B2>,
            field3: Field<A, B3>,
            field4: Field<A, B4>,
            field5: Field<A, B5>,
            field6: Field<A, B6>,
            field7: Field<A, B7>,
            field8: Field<A, B8>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8) -> (A)
        ): Schema<A> = Record8(metadataFromType(), field1, field2, field3, field4, field5, field6, field7, field8, construct)

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9> record(
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
        ): Schema<A> = Record9(metadataFromType(), field1, field2, field3, field4, field5, field6, field7, field8, field9, construct)

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> record(
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
        ): Schema<A> =
            Record10(metadataFromType(), field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, construct)

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> record(
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
        ): Schema<A> = Record11(metadataFromType(), field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, construct)

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> record(
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
            metadataFromType(),
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

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> record(
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
            metadataFromType(),
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

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> record(
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
            metadataFromType(),
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

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> record(
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
            metadataFromType(),
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

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> record(
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
            metadataFromType(),
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

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17> record(
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
            field17: Field<A, B17>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17) -> (A)
        ): Schema<A> = Record17(
            metadataFromType(),
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
            field17,
            construct
        )

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18> record(
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
            field17: Field<A, B17>,
            field18: Field<A, B18>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18) -> (A)
        ): Schema<A> = Record18(
            metadataFromType(),
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
            field17,
            field18,
            construct
        )

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19> record(
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
            field17: Field<A, B17>,
            field18: Field<A, B18>,
            field19: Field<A, B19>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19) -> (A)
        ): Schema<A> = Record19(
            metadataFromType(),
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
            field17,
            field18,
            field19,
            construct
        )

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20> record(
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
            field17: Field<A, B17>,
            field18: Field<A, B18>,
            field19: Field<A, B19>,
            field20: Field<A, B20>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20) -> (A)
        ): Schema<A> = Record20(
            metadataFromType(),
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
            field17,
            field18,
            field19,
            field20,
            construct
        )

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21> record(
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
            field17: Field<A, B17>,
            field18: Field<A, B18>,
            field19: Field<A, B19>,
            field20: Field<A, B20>,
            field21: Field<A, B21>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21) -> (A)
        ): Schema<A> = Record21(
            metadataFromType(),
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
            field17,
            field18,
            field19,
            field20,
            field21,
            construct
        )

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22> record(
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
            field17: Field<A, B17>,
            field18: Field<A, B18>,
            field19: Field<A, B19>,
            field20: Field<A, B20>,
            field21: Field<A, B21>,
            field22: Field<A, B22>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22) -> (A)
        ): Schema<A> = Record22(
            metadataFromType(),
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
            field17,
            field18,
            field19,
            field20,
            field21,
            field22,
            construct
        )

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23> record(
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
            field17: Field<A, B17>,
            field18: Field<A, B18>,
            field19: Field<A, B19>,
            field20: Field<A, B20>,
            field21: Field<A, B21>,
            field22: Field<A, B22>,
            field23: Field<A, B23>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23) -> (A)
        ): Schema<A> = Record23(
            metadataFromType(),
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
            field17,
            field18,
            field19,
            field20,
            field21,
            field22,
            field23,
            construct
        )

        inline fun <reified A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24> record(
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
            field17: Field<A, B17>,
            field18: Field<A, B18>,
            field19: Field<A, B19>,
            field20: Field<A, B20>,
            field21: Field<A, B21>,
            field22: Field<A, B22>,
            field23: Field<A, B23>,
            field24: Field<A, B24>,
            noinline construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24) -> (A)
        ): Schema<A> = Record24(
            metadataFromType(),
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
            field17,
            field18,
            field19,
            field20,
            field21,
            field22,
            field23,
            field24,
            construct
        )

        inline fun <reified A, B1 : A> union(
            case1: Case<A, B1>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union1(metadata, key, case1)

        inline fun <reified A, B1 : A, B2 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union2(metadata, key, case1, case2)

        inline fun <reified A, B1 : A, B2 : A, B3 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union3(metadata, key, case1, case2, case3)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union4(metadata, key, case1, case2, case3, case4)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union5(metadata, key, case1, case2, case3, case4, case5)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            case6: Case<A, B6>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union6(metadata, key, case1, case2, case3, case4, case5, case6)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            case6: Case<A, B6>,
            case7: Case<A, B7>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union7(metadata, key, case1, case2, case3, case4, case5, case6, case7)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            case6: Case<A, B6>,
            case7: Case<A, B7>,
            case8: Case<A, B8>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union8(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A> union(
            case1: Case<A, B1>,
            case2: Case<A, B2>,
            case3: Case<A, B3>,
            case4: Case<A, B4>,
            case5: Case<A, B5>,
            case6: Case<A, B6>,
            case7: Case<A, B7>,
            case8: Case<A, B8>,
            case9: Case<A, B9>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union9(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A> union(
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
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> =
            Union10(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A> union(
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
            case11: Case<A, B11>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union11(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union12(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union13(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            case14: Case<A, B14>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union14(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            case14: Case<A, B14>,
            case15: Case<A, B15>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union15(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            case14: Case<A, B14>,
            case15: Case<A, B15>,
            case16: Case<A, B16>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union16(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15, case16)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            case14: Case<A, B14>,
            case15: Case<A, B15>,
            case16: Case<A, B16>,
            case17: Case<A, B17>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union17(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15, case16, case17)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            case14: Case<A, B14>,
            case15: Case<A, B15>,
            case16: Case<A, B16>,
            case17: Case<A, B17>,
            case18: Case<A, B18>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union18(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15, case16, case17, case18)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A, B19 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            case14: Case<A, B14>,
            case15: Case<A, B15>,
            case16: Case<A, B16>,
            case17: Case<A, B17>,
            case18: Case<A, B18>,
            case19: Case<A, B19>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union19(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15, case16, case17, case18, case19)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A, B19 : A, B20 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            case14: Case<A, B14>,
            case15: Case<A, B15>,
            case16: Case<A, B16>,
            case17: Case<A, B17>,
            case18: Case<A, B18>,
            case19: Case<A, B19>,
            case20: Case<A, B20>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union20(metadata, key, case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15, case16, case17, case18, case19 ,case20)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A, B19 : A, B20 : A, B21 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            case14: Case<A, B14>,
            case15: Case<A, B15>,
            case16: Case<A, B16>,
            case17: Case<A, B17>,
            case18: Case<A, B18>,
            case19: Case<A, B19>,
            case20: Case<A, B20>,
            case21: Case<A, B21>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union21(metadata, key, case1, case2, case3, case4, case5 ,case6 ,case7 ,case8 ,case9 ,case10 ,case11 ,case12 ,case13 ,case14 ,case15 ,case16 ,case17 ,case18 ,case19 ,case20 ,case21)

        inline fun <reified A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A, B19 : A, B20 : A, B21 : A, B22 : A> union(
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
            case11: Case<A, B11>,
            case12: Case<A, B12>,
            case13: Case<A, B13>,
            case14: Case<A, B14>,
            case15: Case<A, B15>,
            case16: Case<A, B16>,
            case17: Case<A, B17>,
            case18: Case<A, B18>,
            case19: Case<A, B19>,
            case20: Case<A, B20>,
            case21: Case<A, B21>,
            case22: Case<A, B22>,
            key: String = "type",
            metadata: ObjectMetadata<A> = metadataFromType<A>()
        ): Schema<A> = Union22(metadata, key, case1 ,case2 ,case3 ,case4 ,case5 ,case6 ,case7 ,case8 ,case9 ,case10 ,case11 ,case12 ,case13 ,case14 ,case15 ,case16 ,case17 ,case18 ,case19 ,case20 ,case21 ,case22)
    }
}

inline fun <A, reified B> Schema<A>.transform(noinline decode: (A) -> B, noinline encode: (B) -> A): Schema<B> =
    Schema.Transform(
        metadata = metadataFromType<B>(),
        schema = this,
        encode = encode,
        decode = { runCatching { decode(it) } }
    )

inline fun <A, reified B> Schema<A>.orElse(fallback: Schema<B>, crossinline decode: (B) -> A): Schema<A> =
    Schema.OrElse(this, fallback, metadataFromType<B>()) { runCatching { decode(it) } }
