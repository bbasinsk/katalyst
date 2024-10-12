package io.github.bbasinsk.schema

data class Record1<A, B1>(
    val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val construct: (B1) -> (A)
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1)
}

data class Record2<A, B1, B2>(
    val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val construct: (B1, B2) -> (A)
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2)
}

data class Record3<A, B1, B2, B3>(
    val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val construct: (B1, B2, B3) -> (A)
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2, values[2] as B3)
}

data class Record4<A, B1, B2, B3, B4>(
    val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val construct: (B1, B2, B3, B4) -> (A)
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2, values[2] as B3, values[3] as B4)
}

data class Record5<A, B1, B2, B3, B4, B5>(
    val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val construct: (B1, B2, B3, B4, B5) -> (A)
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2, values[2] as B3, values[3] as B4, values[4] as B5)
}

data class Record6<A, B1, B2, B3, B4, B5, B6>(
    val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val construct: (B1, B2, B3, B4, B5, B6) -> (A)
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6)

    @Suppress("UNCHECKED_CAST")
    override fun unsafeConstruct(values: List<Any?>): A =
        construct(values[0] as B1, values[1] as B2, values[2] as B3, values[3] as B4, values[4] as B5, values[5] as B6)
}

data class Record7<A, B1, B2, B3, B4, B5, B6, B7>(
    val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val construct: (B1, B2, B3, B4, B5, B6, B7) -> (A)
) : Schema<A>, Schema.Record<A> {
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
    val metadata: Metadata<A>,
    val field1: Field<A, B1>,
    val field2: Field<A, B2>,
    val field3: Field<A, B3>,
    val field4: Field<A, B4>,
    val field5: Field<A, B5>,
    val field6: Field<A, B6>,
    val field7: Field<A, B7>,
    val field8: Field<A, B8>,
    val construct: (B1, B2, B3, B4, B5, B6, B7, B8) -> (A)
) : Schema<A>, Schema.Record<A> {
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
    val metadata: Metadata<A>,
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
) : Schema<A>, Schema.Record<A> {
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
    val metadata: Metadata<A>,
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
) : Schema<A>, Schema.Record<A> {
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
    val metadata: Metadata<A>,
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
) : Schema<A>, Schema.Record<A> {
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
    val metadata: Metadata<A>,
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
) : Schema<A>, Schema.Record<A> {
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
    val metadata: Metadata<A>,
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
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13)

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
    val metadata: Metadata<A>,
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
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14)

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
    val metadata: Metadata<A>,
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
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15)

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
    val metadata: Metadata<A>,
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
) : Schema<A>, Schema.Record<A> {
    override fun unsafeFields(): List<Field<A, *>> =
        listOf(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16)

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
