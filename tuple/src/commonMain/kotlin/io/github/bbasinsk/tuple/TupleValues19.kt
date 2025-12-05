package io.github.bbasinsk.tuple

import kotlin.Pair

public data class TupleValues19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19>(
  public val a1: A1,
  public val a2: A2,
  public val a3: A3,
  public val a4: A4,
  public val a5: A5,
  public val a6: A6,
  public val a7: A7,
  public val a8: A8,
  public val a9: A9,
  public val a10: A10,
  public val a11: A11,
  public val a12: A12,
  public val a13: A13,
  public val a14: A14,
  public val a15: A15,
  public val a16: A16,
  public val a17: A17,
  public val a18: A18,
  public val a19: A19,
) {
  public fun <B> applyTo(f: (
    A1,
    A2,
    A3,
    A4,
    A5,
    A6,
    A7,
    A8,
    A9,
    A10,
    A11,
    A12,
    A13,
    A14,
    A15,
    A16,
    A17,
    A18,
    A19,
  ) -> B): B = f(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19)
}

public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, A8>, A9>, A10>, A11>, A12>, A13>, A14>, A15>, A16>, A17>, A18>, A19>): TupleValues19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19> = TupleValues19(tuples.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)
