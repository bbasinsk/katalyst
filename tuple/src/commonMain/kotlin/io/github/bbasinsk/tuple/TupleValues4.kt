package io.github.bbasinsk.tuple

import kotlin.Pair
import kotlin.Unit
import kotlin.jvm.JvmName

public data class TupleValues4<A1, A2, A3, A4>(
  public val a1: A1,
  public val a2: A2,
  public val a3: A3,
  public val a4: A4,
) {
  public fun <B> applyTo(f: (
    A1,
    A2,
    A3,
    A4,
  ) -> B): B = f(a1, a2, a3, a4)
}

@JvmName("tupleValues_4_1_1")
public fun <A1> tupleValues(tuples: Pair<Pair<Pair<A1, Unit>, Unit>, Unit>): TupleValues1<A1> = TupleValues1<A1>(tuples.first.first.first)

@JvmName("tupleValues_4_1_2")
public fun <A2> tupleValues(tuples: Pair<Pair<Pair<Unit, A2>, Unit>, Unit>): TupleValues1<A2> = TupleValues1<A2>(tuples.first.first.second)

@JvmName("tupleValues_4_1_3")
public fun <A3> tupleValues(tuples: Pair<Pair<Pair<Unit, Unit>, A3>, Unit>): TupleValues1<A3> = TupleValues1<A3>(tuples.first.second)

@JvmName("tupleValues_4_1_4")
public fun <A4> tupleValues(tuples: Pair<Pair<Pair<Unit, Unit>, Unit>, A4>): TupleValues1<A4> = TupleValues1<A4>(tuples.second)

@JvmName("tupleValues_4_2_1")
public fun <A1, A2> tupleValues(tuples: Pair<Pair<Pair<A1, A2>, Unit>, Unit>): TupleValues2<A1, A2> = TupleValues2<A1, A2>(tuples.first.first.first, tuples.first.first.second)

@JvmName("tupleValues_4_2_2")
public fun <A1, A3> tupleValues(tuples: Pair<Pair<Pair<A1, Unit>, A3>, Unit>): TupleValues2<A1, A3> = TupleValues2<A1, A3>(tuples.first.first.first, tuples.first.second)

@JvmName("tupleValues_4_2_3")
public fun <A1, A4> tupleValues(tuples: Pair<Pair<Pair<A1, Unit>, Unit>, A4>): TupleValues2<A1, A4> = TupleValues2<A1, A4>(tuples.first.first.first, tuples.second)

@JvmName("tupleValues_4_2_4")
public fun <A2, A3> tupleValues(tuples: Pair<Pair<Pair<Unit, A2>, A3>, Unit>): TupleValues2<A2, A3> = TupleValues2<A2, A3>(tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_4_2_5")
public fun <A2, A4> tupleValues(tuples: Pair<Pair<Pair<Unit, A2>, Unit>, A4>): TupleValues2<A2, A4> = TupleValues2<A2, A4>(tuples.first.first.second, tuples.second)

@JvmName("tupleValues_4_2_6")
public fun <A3, A4> tupleValues(tuples: Pair<Pair<Pair<Unit, Unit>, A3>, A4>): TupleValues2<A3, A4> = TupleValues2<A3, A4>(tuples.first.second, tuples.second)

@JvmName("tupleValues_4_3_1")
public fun <A1, A2, A3> tupleValues(tuples: Pair<Pair<Pair<A1, A2>, A3>, Unit>): TupleValues3<A1, A2, A3> = TupleValues3<A1, A2, A3>(tuples.first.first.first, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_4_3_2")
public fun <A1, A2, A4> tupleValues(tuples: Pair<Pair<Pair<A1, A2>, Unit>, A4>): TupleValues3<A1, A2, A4> = TupleValues3<A1, A2, A4>(tuples.first.first.first, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_4_3_3")
public fun <A1, A3, A4> tupleValues(tuples: Pair<Pair<Pair<A1, Unit>, A3>, A4>): TupleValues3<A1, A3, A4> = TupleValues3<A1, A3, A4>(tuples.first.first.first, tuples.first.second, tuples.second)

@JvmName("tupleValues_4_3_4")
public fun <A2, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Unit, A2>, A3>, A4>): TupleValues3<A2, A3, A4> = TupleValues3<A2, A3, A4>(tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_4_4_1")
public fun <A1, A2, A3, A4> tupleValues(tuples: Pair<Pair<Pair<A1, A2>, A3>, A4>): TupleValues4<A1, A2, A3, A4> = TupleValues4<A1, A2, A3, A4>(tuples.first.first.first, tuples.first.first.second, tuples.first.second, tuples.second)
