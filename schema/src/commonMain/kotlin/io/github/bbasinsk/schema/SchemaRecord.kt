package io.github.bbasinsk.schema

data class Record1<A, B1>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val construct: (B1) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1)
}

data class Record2<A, B1, B2>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val construct: (B1, B2) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2)
}

data class Record3<A, B1, B2, B3>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val construct: (B1, B2, B3) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2, values[2] as B3)
}

data class Record4<A, B1, B2, B3, B4>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val construct: (B1, B2, B3, B4) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2, values[2] as B3, values[3] as B4)
}

data class Record5<A, B1, B2, B3, B4, B5>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val construct: (B1, B2, B3, B4, B5) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2, values[2] as B3, values[3] as B4, values[4] as B5)
}

data class Record6<A, B1, B2, B3, B4, B5, B6>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val construct: (B1, B2, B3, B4, B5, B6) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2, values[2] as B3, values[3] as B4, values[4] as B5, values[5] as B6)
}

data class Record7<A, B1, B2, B3, B4, B5, B6, B7>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val construct: (B1, B2, B3, B4, B5, B6, B7) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7
        )
}

data class Record8<A, B1, B2, B3, B4, B5, B6, B7, B8>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7, field8)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8
        )
}

data class Record9<A, B1, B2, B3, B4, B5, B6, B7, B8, B9>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7, field8, field9)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9
        )
}

data class Record10<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10
        )
}

data class Record11<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11
        )
}

data class Record12<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12
        )
}

data class Record13<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field13
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13
        )
}

data class Record14<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field14
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14
        )
}

data class Record15<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field15
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15
        )
}

data class Record16<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val field16: Field<A, B16>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field16
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15,
            values[15] as B16
        )
}

data class Record17<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val field16: Field<A, B16>,
    val field17: Field<A, B17>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field17
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15,
            values[15] as B16,
            values[16] as B17
        )
}


data class Record18<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val field16: Field<A, B16>,
    val field17: Field<A, B17>,
    val field18: Field<A, B18>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field18
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15,
            values[15] as B16,
            values[16] as B17,
            values[17] as B18
        )
}


data class Record19<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val field16: Field<A, B16>,
    val field17: Field<A, B17>,
    val field18: Field<A, B18>,
    val field19: Field<A, B19>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field19
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15,
            values[15] as B16,
            values[16] as B17,
            values[17] as B18,
            values[18] as B19
        )
}


data class Record20<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val field16: Field<A, B16>,
    val field17: Field<A, B17>,
    val field18: Field<A, B18>,
    val field19: Field<A, B19>,
    val field20: Field<A, B20>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field20
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15,
            values[15] as B16,
            values[16] as B17,
            values[17] as B18,
            values[18] as B19,
            values[19] as B20
        )
}


data class Record21<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val field16: Field<A, B16>,
    val field17: Field<A, B17>,
    val field18: Field<A, B18>,
    val field19: Field<A, B19>,
    val field20: Field<A, B20>,
    val field21: Field<A, B21>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field21
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15,
            values[15] as B16,
            values[16] as B17,
            values[17] as B18,
            values[18] as B19,
            values[19] as B20,
            values[20] as B21
        )
}

data class Record22<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val field16: Field<A, B16>,
    val field17: Field<A, B17>,
    val field18: Field<A, B18>,
    val field19: Field<A, B19>,
    val field20: Field<A, B20>,
    val field21: Field<A, B21>,
    val field22: Field<A, B22>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field22
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15,
            values[15] as B16,
            values[16] as B17,
            values[17] as B18,
            values[18] as B19,
            values[19] as B20,
            values[20] as B21,
            values[21] as B22
        )
}

data class Record23<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val field16: Field<A, B16>,
    val field17: Field<A, B17>,
    val field18: Field<A, B18>,
    val field19: Field<A, B19>,
    val field20: Field<A, B20>,
    val field21: Field<A, B21>,
    val field22: Field<A, B22>,
    val field23: Field<A, B23>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field23
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15,
            values[15] as B16,
            values[16] as B17,
            values[17] as B18,
            values[18] as B19,
            values[19] as B20,
            values[20] as B21,
            values[21] as B22,
            values[22] as B23
        )
}

data class Record24<A, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24>(
    override val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val field9: Field<A, B9>,
    val field10: Field<A, B10>,
    val field11: Field<A, B11>,
    val field12: Field<A, B12>,
    val field13: Field<A, B13>,
    val field14: Field<A, B14>,
    val field15: Field<A, B15>,
    val field16: Field<A, B16>,
    val field17: Field<A, B17>,
    val field18: Field<A, B18>,
    val field19: Field<A, B19>,
    val field20: Field<A, B20>,
    val field21: Field<A, B21>,
    val field22: Field<A, B22>,
    val field23: Field<A, B23>,
    val field24: Field<A, B24>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24) -> (A)
) : Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(
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
            field24
        )

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(
            values[0] as B1,
            values[1] as B2,
            values[2] as B3,
            values[3] as B4,
            values[4] as B5,
            values[5] as B6,
            values[6] as B7,
            values[7] as B8,
            values[8] as B9,
            values[9] as B10,
            values[10] as B11,
            values[11] as B12,
            values[12] as B13,
            values[13] as B14,
            values[14] as B15,
            values[15] as B16,
            values[16] as B17,
            values[17] as B18,
            values[18] as B19,
            values[19] as B20,
            values[20] as B21,
            values[21] as B22,
            values[22] as B23,
            values[23] as B24
        )
}
