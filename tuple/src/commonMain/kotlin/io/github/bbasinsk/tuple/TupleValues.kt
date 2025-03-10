package io.github.bbasinsk.tuple

import kotlin.jvm.JvmName


data class TupleValues1<A1>(val a1: A1) {
    fun <B> applyTo(f: (A1) -> B): B = f(a1)
}
data class TupleValues2<A1, A2>(val a1: A1, val a2: A2) {
    fun <B> applyTo(f: (A1, A2) -> B): B = f(a1, a2)
}
data class TupleValues3<A1, A2, A3>(val a1: A1, val a2: A2, val a3: A3) {
    fun <B> applyTo(f: (A1, A2, A3) -> B): B = f(a1, a2, a3)
}
data class TupleValues4<A1, A2, A3, A4>(val a1: A1, val a2: A2, val a3: A3, val a4: A4) {
    fun <B> applyTo(f: (A1, A2, A3, A4) -> B): B = f(a1, a2, a3, a4)
}
data class TupleValues5<A1, A2, A3, A4, A5>(val a1: A1, val a2: A2, val a3: A3, val a4: A4, val a5: A5) {
    fun <B> applyTo(f: (A1, A2, A3, A4, A5) -> B): B = f(a1, a2, a3, a4, a5)
}

/* ktlint-disable max-line-length */

// ////////////////////////////////////////////////
// I wish kotlin had either variadic tuples or macros to generate these.
// Maybe in a future version of Kotlin.
//
// To check that we're covering all combinations:
//
// Up to V values, and T types -> unique combinations C
// C = T! / (V! * (T - V)!)
//
// Example: 4 values, 2 types -> 6 unique combinations
// 4! / (2! * (4 - 2)!) = 6
// ////////////////////////////////////////////////

// Up to 2 values
// 1 types -> 2 unique combinations
// 2 types -> 1 unique combinations
@JvmName("tupleValues_2_1_1") fun <A> tupleValues(tuples: Pair<A, Unit>) = TupleValues1(tuples.first)
@JvmName("tupleValues_2_1_2") fun <A> tupleValues(tuples: Pair<Unit, A>) = TupleValues1(tuples.second)

@JvmName("tupleValues_2_2_1") fun <A, B> tupleValues(tuples: Pair<A, B>) = TupleValues2(tuples.first, tuples.second)

// Up to 3 values
// 1 types -> 3 unique combinations
// 2 types -> 3 unique combinations
// 3 types -> 1 unique combinations
@JvmName("tupleValues_3_1_1") fun <A> tupleValues(tuples: Pair<Pair<A, Unit>, Unit>): TupleValues1<A> = TupleValues1(tuples.first.first)
@JvmName("tupleValues_3_1_2") fun <A> tupleValues(tuples: Pair<Pair<Unit, A>, Unit>): TupleValues1<A> = TupleValues1(tuples.first.second)
@JvmName("tupleValues_3_1_3") fun <A> tupleValues(tuples: Pair<Pair<Unit, Unit>, A>): TupleValues1<A> = TupleValues1(tuples.second)

@JvmName("tupleValues_3_2_1") fun <A, B> tupleValues(tuples: Pair<Pair<A, B>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first, tuples.first.second)
@JvmName("tupleValues_3_2_2") fun <A, B> tupleValues(tuples: Pair<Pair<A, Unit>, B>): TupleValues2<A, B> = TupleValues2(tuples.first.first, tuples.second)
@JvmName("tupleValues_3_2_3") fun <A, B> tupleValues(tuples: Pair<Pair<Unit, A>, B>): TupleValues2<A, B> = TupleValues2(tuples.first.second, tuples.second)

@JvmName("tupleValues_3_3_1") fun <A, B, C> tupleValues(tuples: Pair<Pair<A, B>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first, tuples.first.second, tuples.second)

// Up to 4 values
// 1 types -> 4 unique combinations
// 2 types -> 6 unique combinations
// 3 types -> 4 unique combinations
// 4 types -> 1 unique combinations
@JvmName("tupleValues_4_1_1") fun <A> tupleValues(tuples: Pair<Pair<Pair<A, Unit>, Unit>, Unit>): TupleValues1<A> = TupleValues1(tuples.first.first.first)
@JvmName("tupleValues_4_1_2") fun <A> tupleValues(tuples: Pair<Pair<Pair<Unit, A>, Unit>, Unit>): TupleValues1<A> = TupleValues1(tuples.first.first.second)
@JvmName("tupleValues_4_1_3") fun <A> tupleValues(tuples: Pair<Pair<Pair<Unit, Unit>, A>, Unit>): TupleValues1<A> = TupleValues1(tuples.first.second)
@JvmName("tupleValues_4_1_4") fun <A> tupleValues(tuples: Pair<Pair<Pair<Unit, Unit>, Unit>, A>): TupleValues1<A> = TupleValues1(tuples.second)

@JvmName("tupleValues_4_2_1") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<A, B>, Unit>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first, tuples.first.first.second)
@JvmName("tupleValues_4_2_2") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<A, Unit>, B>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first, tuples.first.second)
@JvmName("tupleValues_4_2_3") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<A, Unit>, Unit>, B>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first, tuples.second)
@JvmName("tupleValues_4_2_4") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Unit, A>, B>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first.second, tuples.first.second)
@JvmName("tupleValues_4_2_5") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Unit, A>, Unit>, B>): TupleValues2<A, B> = TupleValues2(tuples.first.first.second, tuples.second)
@JvmName("tupleValues_4_2_6") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Unit, Unit>, A>, B>): TupleValues2<A, B> = TupleValues2(tuples.first.second, tuples.second)

@JvmName("tupleValues_4_3_1") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<A, B>, C>, Unit>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first, tuples.first.first.second, tuples.first.second)
@JvmName("tupleValues_4_3_2") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<A, B>, Unit>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first, tuples.first.first.second, tuples.second)
@JvmName("tupleValues_4_3_3") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<A, Unit>, B>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first, tuples.first.second, tuples.second)
@JvmName("tupleValues_4_3_4") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Unit, A>, B>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_4_4_1") fun <A, B, C, D> tupleValues(tuples: Pair<Pair<Pair<A, B>, C>, D>): TupleValues4<A, B, C, D> = TupleValues4(tuples.first.first.first, tuples.first.first.second, tuples.first.second, tuples.second)

// Up to 5 values
// 1 types -> 5 unique combinations
// 2 types -> 10 unique combinations
// 3 types -> 10 unique combinations
// 4 types -> 5 unique combinations
// 5 types -> 1 unique combinations
@JvmName("tupleValues_5_1_1") fun <A> tupleValues(tuples: Pair<Pair<Pair<Pair<A, Unit>, Unit>, Unit>, Unit>): TupleValues1<A> = TupleValues1(tuples.first.first.first.first)
@JvmName("tupleValues_5_1_2") fun <A> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, A>, Unit>, Unit>, Unit>): TupleValues1<A> = TupleValues1(tuples.first.first.first.second)
@JvmName("tupleValues_5_1_3") fun <A> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, Unit>, A>, Unit>, Unit>): TupleValues1<A> = TupleValues1(tuples.first.first.second)
@JvmName("tupleValues_5_1_4") fun <A> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A>, Unit>): TupleValues1<A> = TupleValues1(tuples.first.second)
@JvmName("tupleValues_5_1_5") fun <A> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A>): TupleValues1<A> = TupleValues1(tuples.second)

@JvmName("tupleValues_5_2_1") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<A, B>, Unit>, Unit>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first.first, tuples.first.first.first.second)
@JvmName("tupleValues_5_2_2") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<A, Unit>, B>, Unit>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first.first, tuples.first.first.second)
@JvmName("tupleValues_5_2_3") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<A, Unit>, Unit>, B>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first.first, tuples.first.second)
@JvmName("tupleValues_5_2_4") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<A, Unit>, Unit>, Unit>, B>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first.first, tuples.second)
@JvmName("tupleValues_5_2_5") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, A>, B>, Unit>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first.second, tuples.first.first.second)
@JvmName("tupleValues_5_2_6") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, A>, Unit>, B>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first.second, tuples.first.second)
@JvmName("tupleValues_5_2_7") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, A>, Unit>, Unit>, B>): TupleValues2<A, B> = TupleValues2(tuples.first.first.first.second, tuples.second)
@JvmName("tupleValues_5_2_8") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, Unit>, A>, B>, Unit>): TupleValues2<A, B> = TupleValues2(tuples.first.first.second, tuples.first.second)
@JvmName("tupleValues_5_2_9") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, Unit>, A>, Unit>, B>): TupleValues2<A, B> = TupleValues2(tuples.first.first.second, tuples.second)
@JvmName("tupleValues_5_2_10") fun <A, B> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A>, B>): TupleValues2<A, B> = TupleValues2(tuples.first.second, tuples.second)

@JvmName("tupleValues_5_3_1") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<A, B>, C>, Unit>, Unit>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second)
@JvmName("tupleValues_5_3_2") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<A, B>, Unit>, C>, Unit>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first.first, tuples.first.first.first.second, tuples.first.second)
@JvmName("tupleValues_5_3_3") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<A, B>, Unit>, Unit>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first.first, tuples.first.first.first.second, tuples.second)
@JvmName("tupleValues_5_3_4") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<A, Unit>, B>, C>, Unit>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first.first, tuples.first.first.second, tuples.first.second)
@JvmName("tupleValues_5_3_5") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<A, Unit>, B>, Unit>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first.first, tuples.first.first.second, tuples.second)
@JvmName("tupleValues_5_3_6") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<A, Unit>, Unit>, B>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first.first, tuples.first.second, tuples.second)
@JvmName("tupleValues_5_3_7") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, A>, B>, C>, Unit>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)
@JvmName("tupleValues_5_3_8") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, A>, B>, Unit>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first.second, tuples.first.first.second, tuples.second)
@JvmName("tupleValues_5_3_9") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, A>, Unit>, B>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.first.second, tuples.first.second, tuples.second)
@JvmName("tupleValues_5_3_10") fun <A, B, C> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, Unit>, A>, B>, C>): TupleValues3<A, B, C> = TupleValues3(tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_5_4_1") fun <A, B, C, D> tupleValues(tuples: Pair<Pair<Pair<Pair<A, B>, C>, D>, Unit>): TupleValues4<A, B, C, D> = TupleValues4(tuples.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)
@JvmName("tupleValues_5_4_2") fun <A, B, C, D> tupleValues(tuples: Pair<Pair<Pair<Pair<A, B>, C>, Unit>, D>): TupleValues4<A, B, C, D> = TupleValues4(tuples.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.second)
@JvmName("tupleValues_5_4_3") fun <A, B, C, D> tupleValues(tuples: Pair<Pair<Pair<Pair<A, B>, Unit>, C>, D>): TupleValues4<A, B, C, D> = TupleValues4(tuples.first.first.first.first, tuples.first.first.first.second, tuples.first.second, tuples.second)
@JvmName("tupleValues_5_4_4") fun <A, B, C, D> tupleValues(tuples: Pair<Pair<Pair<Pair<A, Unit>, B>, C>, D>): TupleValues4<A, B, C, D> = TupleValues4(tuples.first.first.first.first, tuples.first.first.second, tuples.first.second, tuples.second)
@JvmName("tupleValues_5_4_5") fun <A, B, C, D> tupleValues(tuples: Pair<Pair<Pair<Pair<Unit, A>, B>, C>, D>): TupleValues4<A, B, C, D> = TupleValues4(tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_5_5_1") fun <A, B, C, D, E> tupleValues(tuples: Pair<Pair<Pair<Pair<A, B>, C>, D>, E>): TupleValues5<A, B, C, D, E> = TupleValues5(tuples.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

// Up to 6 path values
// ... TODO: continue until 10(?) values

/* ktlint-enable max-line-length */
