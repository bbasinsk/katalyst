package io.github.bbasinsk.tuple

import kotlin.jvm.JvmName

public data class TupleValues1<A1>(
  public val a1: A1,
) {
  public fun <B> applyTo(f: (A1) -> B): B = f(a1)
}

@JvmName("tupleValues_1_1_1")
public fun <A1> tupleValues(tuples: A1): TupleValues1<A1> = TupleValues1<A1>(tuples)
