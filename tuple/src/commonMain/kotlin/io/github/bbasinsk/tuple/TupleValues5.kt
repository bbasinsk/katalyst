package io.github.bbasinsk.tuple

import kotlin.Pair
import kotlin.Unit
import kotlin.jvm.JvmName

public data class TupleValues5<A1, A2, A3, A4, A5>(
  public val a1: A1,
  public val a2: A2,
  public val a3: A3,
  public val a4: A4,
  public val a5: A5,
) {
  public fun <B> applyTo(f: (
    A1,
    A2,
    A3,
    A4,
    A5,
  ) -> B): B = f(a1, a2, a3, a4, a5)
}

@JvmName("tupleValues_5_1_1")
public fun <A1> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>): TupleValues1<A1> = TupleValues1<A1>(tuples.first)

@JvmName("tupleValues_5_1_2")
public fun <A2> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Unit>>>>): TupleValues1<A2> = TupleValues1<A2>(tuples.second.first)

@JvmName("tupleValues_5_1_3")
public fun <A3> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Unit>>>>): TupleValues1<A3> = TupleValues1<A3>(tuples.second.second.first)

@JvmName("tupleValues_5_1_4")
public fun <A4> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Unit>>>>): TupleValues1<A4> = TupleValues1<A4>(tuples.second.second.second.first)

@JvmName("tupleValues_5_1_5")
public fun <A5> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, A5>>>>): TupleValues1<A5> = TupleValues1<A5>(tuples.second.second.second.second)

@JvmName("tupleValues_5_2_1")
public fun <A1, A2> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Unit>>>>): TupleValues2<A1, A2> = TupleValues2<A1, A2>(tuples.first, tuples.second.first)

@JvmName("tupleValues_5_2_2")
public fun <A1, A3> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Unit>>>>): TupleValues2<A1, A3> = TupleValues2<A1, A3>(tuples.first, tuples.second.second.first)

@JvmName("tupleValues_5_2_3")
public fun <A1, A4> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Unit>>>>): TupleValues2<A1, A4> = TupleValues2<A1, A4>(tuples.first, tuples.second.second.second.first)

@JvmName("tupleValues_5_2_4")
public fun <A1, A5> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, A5>>>>): TupleValues2<A1, A5> = TupleValues2<A1, A5>(tuples.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_2_5")
public fun <A2, A3> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Unit>>>>): TupleValues2<A2, A3> = TupleValues2<A2, A3>(tuples.second.first, tuples.second.second.first)

@JvmName("tupleValues_5_2_6")
public fun <A2, A4> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Unit>>>>): TupleValues2<A2, A4> = TupleValues2<A2, A4>(tuples.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_5_2_7")
public fun <A2, A5> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, A5>>>>): TupleValues2<A2, A5> = TupleValues2<A2, A5>(tuples.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_2_8")
public fun <A3, A4> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Unit>>>>): TupleValues2<A3, A4> = TupleValues2<A3, A4>(tuples.second.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_5_2_9")
public fun <A3, A5> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, A5>>>>): TupleValues2<A3, A5> = TupleValues2<A3, A5>(tuples.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_2_10")
public fun <A4, A5> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, A5>>>>): TupleValues2<A4, A5> = TupleValues2<A4, A5>(tuples.second.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_3_1")
public fun <A1, A2, A3> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Unit>>>>): TupleValues3<A1, A2, A3> = TupleValues3<A1, A2, A3>(tuples.first, tuples.second.first, tuples.second.second.first)

@JvmName("tupleValues_5_3_2")
public fun <A1, A2, A4> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Unit>>>>): TupleValues3<A1, A2, A4> = TupleValues3<A1, A2, A4>(tuples.first, tuples.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_5_3_3")
public fun <A1, A2, A5> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, A5>>>>): TupleValues3<A1, A2, A5> = TupleValues3<A1, A2, A5>(tuples.first, tuples.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_3_4")
public fun <A1, A3, A4> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Unit>>>>): TupleValues3<A1, A3, A4> = TupleValues3<A1, A3, A4>(tuples.first, tuples.second.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_5_3_5")
public fun <A1, A3, A5> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, A5>>>>): TupleValues3<A1, A3, A5> = TupleValues3<A1, A3, A5>(tuples.first, tuples.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_3_6")
public fun <A1, A4, A5> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, A5>>>>): TupleValues3<A1, A4, A5> = TupleValues3<A1, A4, A5>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_3_7")
public fun <A2, A3, A4> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Unit>>>>): TupleValues3<A2, A3, A4> = TupleValues3<A2, A3, A4>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_5_3_8")
public fun <A2, A3, A5> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, A5>>>>): TupleValues3<A2, A3, A5> = TupleValues3<A2, A3, A5>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_3_9")
public fun <A2, A4, A5> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, A5>>>>): TupleValues3<A2, A4, A5> = TupleValues3<A2, A4, A5>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_3_10")
public fun <A3, A4, A5> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, A5>>>>): TupleValues3<A3, A4, A5> = TupleValues3<A3, A4, A5>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_4_1")
public fun <A1, A2, A3, A4> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Unit>>>>): TupleValues4<A1, A2, A3, A4> = TupleValues4<A1, A2, A3, A4>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_5_4_2")
public fun <A1, A2, A3, A5> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, A5>>>>): TupleValues4<A1, A2, A3, A5> = TupleValues4<A1, A2, A3, A5>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_4_3")
public fun <A1, A2, A4, A5> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, A5>>>>): TupleValues4<A1, A2, A4, A5> = TupleValues4<A1, A2, A4, A5>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_4_4")
public fun <A1, A3, A4, A5> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, A5>>>>): TupleValues4<A1, A3, A4, A5> = TupleValues4<A1, A3, A4, A5>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_4_5")
public fun <A2, A3, A4, A5> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, A5>>>>): TupleValues4<A2, A3, A4, A5> = TupleValues4<A2, A3, A4, A5>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second)

@JvmName("tupleValues_5_5_1")
public fun <A1, A2, A3, A4, A5> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, A5>>>>): TupleValues5<A1, A2, A3, A4, A5> = TupleValues5<A1, A2, A3, A4, A5>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second)
