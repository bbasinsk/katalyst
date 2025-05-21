package io.github.bbasinsk.tuple

import kotlin.Pair
import kotlin.Unit
import kotlin.jvm.JvmName

public data class TupleValues3<A1, A2, A3>(
  public val a1: A1,
  public val a2: A2,
  public val a3: A3,
) {
  public fun <B> applyTo(f: (
    A1,
    A2,
    A3,
  ) -> B): B = f(a1, a2, a3)
}

@JvmName("tupleValues_3_1_1")
public fun <A1> tupleValues(tuples: Pair<A1, Pair<Unit, Unit>>): TupleValues1<A1> = TupleValues1<A1>(tuples.first)

@JvmName("tupleValues_3_1_2")
public fun <A2> tupleValues(tuples: Pair<Unit, Pair<A2, Unit>>): TupleValues1<A2> = TupleValues1<A2>(tuples.second.first)

@JvmName("tupleValues_3_1_3")
public fun <A3> tupleValues(tuples: Pair<Unit, Pair<Unit, A3>>): TupleValues1<A3> = TupleValues1<A3>(tuples.second.second)

@JvmName("tupleValues_3_2_1")
public fun <A1, A2> tupleValues(tuples: Pair<A1, Pair<A2, Unit>>): TupleValues2<A1, A2> = TupleValues2<A1, A2>(tuples.first, tuples.second.first)

@JvmName("tupleValues_3_2_2")
public fun <A1, A3> tupleValues(tuples: Pair<A1, Pair<Unit, A3>>): TupleValues2<A1, A3> = TupleValues2<A1, A3>(tuples.first, tuples.second.second)

@JvmName("tupleValues_3_2_3")
public fun <A2, A3> tupleValues(tuples: Pair<Unit, Pair<A2, A3>>): TupleValues2<A2, A3> = TupleValues2<A2, A3>(tuples.second.first, tuples.second.second)

@JvmName("tupleValues_3_3_1")
public fun <A1, A2, A3> tupleValues(tuples: Pair<A1, Pair<A2, A3>>): TupleValues3<A1, A2, A3> = TupleValues3<A1, A2, A3>(tuples.first, tuples.second.first, tuples.second.second)
