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
