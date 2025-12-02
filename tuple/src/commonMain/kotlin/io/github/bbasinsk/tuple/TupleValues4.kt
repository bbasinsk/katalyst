package io.github.bbasinsk.tuple

import kotlin.Pair

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

public fun <A1, A2, A3, A4> tupleValues(tuples: Pair<Pair<Pair<A1, A2>, A3>, A4>): TupleValues4<A1, A2, A3, A4> = TupleValues4(tuples.first.first.first, tuples.first.first.second, tuples.first.second, tuples.second)
