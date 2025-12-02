package io.github.bbasinsk.tuple

import kotlin.Pair

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

public fun <A1, A2, A3> tupleValues(tuples: Pair<Pair<A1, A2>, A3>): TupleValues3<A1, A2, A3> = TupleValues3(tuples.first.first, tuples.first.second, tuples.second)
