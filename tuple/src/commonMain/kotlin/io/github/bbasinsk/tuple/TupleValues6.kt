package io.github.bbasinsk.tuple

import kotlin.Pair

public data class TupleValues6<A1, A2, A3, A4, A5, A6>(
  public val a1: A1,
  public val a2: A2,
  public val a3: A3,
  public val a4: A4,
  public val a5: A5,
  public val a6: A6,
) {
  public fun <B> applyTo(f: (
    A1,
    A2,
    A3,
    A4,
    A5,
    A6,
  ) -> B): B = f(a1, a2, a3, a4, a5, a6)
}

public fun <A1, A2, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>): TupleValues6<A1, A2, A3, A4, A5, A6> = TupleValues6(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)
