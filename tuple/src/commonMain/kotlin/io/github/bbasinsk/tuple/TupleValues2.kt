package io.github.bbasinsk.tuple

import kotlin.Pair
import kotlin.Unit
import kotlin.jvm.JvmName

public data class TupleValues2<A1, A2>(
  public val a1: A1,
  public val a2: A2,
) {
  public fun <B> applyTo(f: (A1, A2) -> B): B = f(a1, a2)
}

@JvmName("tupleValues_2_1_1")
public fun <A1> tupleValues(tuples: Pair<A1, Unit>): TupleValues1<A1> = TupleValues1<A1>(tuples.first)

@JvmName("tupleValues_2_1_2")
public fun <A2> tupleValues(tuples: Pair<Unit, A2>): TupleValues1<A2> = TupleValues1<A2>(tuples.second)

@JvmName("tupleValues_2_2_1")
public fun <A1, A2> tupleValues(tuples: Pair<A1, A2>): TupleValues2<A1, A2> = TupleValues2<A1, A2>(tuples.first, tuples.second)
