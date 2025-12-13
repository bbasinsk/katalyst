package io.github.bbasinsk.tuple

import kotlin.Pair

public data class TupleValues2<A1, A2>(
  public val a1: A1,
  public val a2: A2,
) {
  public fun <B> applyTo(f: (A1, A2) -> B): B = f(a1, a2)
}

public fun <A1, A2> tupleValues(tuples: Pair<A1, A2>): TupleValues2<A1, A2> = TupleValues2(tuples.first, tuples.second)
