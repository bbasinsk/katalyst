package io.github.bbasinsk.tuple

public data class TupleValues1<A1>(
  public val a1: A1,
) {
  public fun <B> applyTo(f: (A1) -> B): B = f(a1)
}

public fun <A1> tupleValues(`value`: A1): TupleValues1<A1> = TupleValues1(value)
