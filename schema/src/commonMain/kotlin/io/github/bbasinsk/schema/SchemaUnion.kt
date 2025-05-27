package io.github.bbasinsk.schema

data class Union1<A, B1 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1
    )
}

data class Union2<A, B1 : A, B2 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2
    )
}

data class Union3<A, B1 : A, B2 : A, B3 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3
    )
}

data class Union4<A, B1 : A, B2 : A, B3 : A, B4 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4
    )
}

data class Union5<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5
    )
}

data class Union6<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6
    )
}

data class Union7<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7
    )
}

data class Union8<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8
    )
}

data class Union9<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9
    )
}

data class Union10<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10
    )
}

data class Union11<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11
    )
}

data class Union12<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12
    )
}

data class Union13<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13
    )
}

data class Union14<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>,
    val case14: Case<A, B14>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13,
        case14
    )
}

data class Union15<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>,
    val case14: Case<A, B14>,
    val case15: Case<A, B15>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13,
        case14,
        case15
    )
}

data class Union16<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>,
    val case14: Case<A, B14>,
    val case15: Case<A, B15>,
    val case16: Case<A, B16>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13,
        case14,
        case15,
        case16
    )
}

data class Union17<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>,
    val case14: Case<A, B14>,
    val case15: Case<A, B15>,
    val case16: Case<A, B16>,
    val case17: Case<A, B17>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13,
        case14,
        case15,
        case16,
        case17
    )
}

data class Union18<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>,
    val case14: Case<A, B14>,
    val case15: Case<A, B15>,
    val case16: Case<A, B16>,
    val case17: Case<A, B17>,
    val case18: Case<A, B18>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13,
        case14,
        case15,
        case16,
        case17,
        case18
    )
}

data class Union19<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A, B19 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>,
    val case14: Case<A, B14>,
    val case15: Case<A, B15>,
    val case16: Case<A, B16>,
    val case17: Case<A, B17>,
    val case18: Case<A, B18>,
    val case19: Case<A, B19>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13,
        case14,
        case15,
        case16,
        case17,
        case18,
        case19
    )
}

data class Union20<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A, B19 : A, B20 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>,
    val case14: Case<A, B14>,
    val case15: Case<A, B15>,
    val case16: Case<A, B16>,
    val case17: Case<A, B17>,
    val case18: Case<A, B18>,
    val case19: Case<A, B19>,
    val case20: Case<A, B20>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13,
        case14,
        case15,
        case16,
        case17,
        case18,
        case19,
        case20
    )
}

data class Union21<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A, B19 : A, B20 : A, B21 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>,
    val case14: Case<A, B14>,
    val case15: Case<A, B15>,
    val case16: Case<A, B16>,
    val case17: Case<A, B17>,
    val case18: Case<A, B18>,
    val case19: Case<A, B19>,
    val case20: Case<A, B20>,
    val case21: Case<A, B21>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13,
        case14,
        case15,
        case16,
        case17,
        case18,
        case19,
        case20,
        case21
    )
}

data class Union22<A, B1 : A, B2 : A, B3 : A, B4 : A, B5 : A, B6 : A, B7 : A, B8 : A, B9 : A, B10 : A, B11 : A, B12 : A, B13 : A, B14 : A, B15 : A, B16 : A, B17 : A, B18 : A, B19 : A, B20 : A, B21 : A, B22 : A>(
    override val metadata: ObjectMetadata<A>,
    override val key: String,
    val case1: Case<A, B1>,
    val case2: Case<A, B2>,
    val case3: Case<A, B3>,
    val case4: Case<A, B4>,
    val case5: Case<A, B5>,
    val case6: Case<A, B6>,
    val case7: Case<A, B7>,
    val case8: Case<A, B8>,
    val case9: Case<A, B9>,
    val case10: Case<A, B10>,
    val case11: Case<A, B11>,
    val case12: Case<A, B12>,
    val case13: Case<A, B13>,
    val case14: Case<A, B14>,
    val case15: Case<A, B15>,
    val case16: Case<A, B16>,
    val case17: Case<A, B17>,
    val case18: Case<A, B18>,
    val case19: Case<A, B19>,
    val case20: Case<A, B20>,
    val case21: Case<A, B21>,
    val case22: Case<A, B22>
) : Schema.Union<A> {
    override fun unsafeCases(): List<Case<A, *>> = listOf(
        case1,
        case2,
        case3,
        case4,
        case5,
        case6,
        case7,
        case8,
        case9,
        case10,
        case11,
        case12,
        case13,
        case14,
        case15,
        case16,
        case17,
        case18,
        case19,
        case20,
        case21,
        case22
    )
}
