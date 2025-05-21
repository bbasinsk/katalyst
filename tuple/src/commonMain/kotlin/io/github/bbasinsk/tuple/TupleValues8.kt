package io.github.bbasinsk.tuple

import kotlin.Pair
import kotlin.Unit
import kotlin.jvm.JvmName

public data class TupleValues8<A1, A2, A3, A4, A5, A6, A7, A8>(
  public val a1: A1,
  public val a2: A2,
  public val a3: A3,
  public val a4: A4,
  public val a5: A5,
  public val a6: A6,
  public val a7: A7,
  public val a8: A8,
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
  ) -> B): B = f(a1, a2, a3, a4, a5, a6, a7, a8)
}

@JvmName("tupleValues_8_1_1")
public fun <A1> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues1<A1> = TupleValues1<A1>(tuples.first)

@JvmName("tupleValues_8_1_2")
public fun <A2> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues1<A2> = TupleValues1<A2>(tuples.second.first)

@JvmName("tupleValues_8_1_3")
public fun <A3> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues1<A3> = TupleValues1<A3>(tuples.second.second.first)

@JvmName("tupleValues_8_1_4")
public fun <A4> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues1<A4> = TupleValues1<A4>(tuples.second.second.second.first)

@JvmName("tupleValues_8_1_5")
public fun <A5> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues1<A5> = TupleValues1<A5>(tuples.second.second.second.second.first)

@JvmName("tupleValues_8_1_6")
public fun <A6> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues1<A6> = TupleValues1<A6>(tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_1_7")
public fun <A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues1<A7> = TupleValues1<A7>(tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_1_8")
public fun <A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues1<A8> = TupleValues1<A8>(tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_2_1")
public fun <A1, A2> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A1, A2> = TupleValues2<A1, A2>(tuples.first, tuples.second.first)

@JvmName("tupleValues_8_2_2")
public fun <A1, A3> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A1, A3> = TupleValues2<A1, A3>(tuples.first, tuples.second.second.first)

@JvmName("tupleValues_8_2_3")
public fun <A1, A4> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A1, A4> = TupleValues2<A1, A4>(tuples.first, tuples.second.second.second.first)

@JvmName("tupleValues_8_2_4")
public fun <A1, A5> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A1, A5> = TupleValues2<A1, A5>(tuples.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_2_5")
public fun <A1, A6> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues2<A1, A6> = TupleValues2<A1, A6>(tuples.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_6")
public fun <A1, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues2<A1, A7> = TupleValues2<A1, A7>(tuples.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_7")
public fun <A1, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues2<A1, A8> = TupleValues2<A1, A8>(tuples.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_2_8")
public fun <A2, A3> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A2, A3> = TupleValues2<A2, A3>(tuples.second.first, tuples.second.second.first)

@JvmName("tupleValues_8_2_9")
public fun <A2, A4> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A2, A4> = TupleValues2<A2, A4>(tuples.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_8_2_10")
public fun <A2, A5> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A2, A5> = TupleValues2<A2, A5>(tuples.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_2_11")
public fun <A2, A6> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues2<A2, A6> = TupleValues2<A2, A6>(tuples.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_12")
public fun <A2, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues2<A2, A7> = TupleValues2<A2, A7>(tuples.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_13")
public fun <A2, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues2<A2, A8> = TupleValues2<A2, A8>(tuples.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_2_14")
public fun <A3, A4> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A3, A4> = TupleValues2<A3, A4>(tuples.second.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_8_2_15")
public fun <A3, A5> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A3, A5> = TupleValues2<A3, A5>(tuples.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_2_16")
public fun <A3, A6> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues2<A3, A6> = TupleValues2<A3, A6>(tuples.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_17")
public fun <A3, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues2<A3, A7> = TupleValues2<A3, A7>(tuples.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_18")
public fun <A3, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues2<A3, A8> = TupleValues2<A3, A8>(tuples.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_2_19")
public fun <A4, A5> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues2<A4, A5> = TupleValues2<A4, A5>(tuples.second.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_2_20")
public fun <A4, A6> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues2<A4, A6> = TupleValues2<A4, A6>(tuples.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_21")
public fun <A4, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues2<A4, A7> = TupleValues2<A4, A7>(tuples.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_22")
public fun <A4, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues2<A4, A8> = TupleValues2<A4, A8>(tuples.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_2_23")
public fun <A5, A6> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues2<A5, A6> = TupleValues2<A5, A6>(tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_24")
public fun <A5, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues2<A5, A7> = TupleValues2<A5, A7>(tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_25")
public fun <A5, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues2<A5, A8> = TupleValues2<A5, A8>(tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_2_26")
public fun <A6, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues2<A6, A7> = TupleValues2<A6, A7>(tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_2_27")
public fun <A6, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues2<A6, A8> = TupleValues2<A6, A8>(tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_2_28")
public fun <A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues2<A7, A8> = TupleValues2<A7, A8>(tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_1")
public fun <A1, A2, A3> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A2, A3> = TupleValues3<A1, A2, A3>(tuples.first, tuples.second.first, tuples.second.second.first)

@JvmName("tupleValues_8_3_2")
public fun <A1, A2, A4> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A2, A4> = TupleValues3<A1, A2, A4>(tuples.first, tuples.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_8_3_3")
public fun <A1, A2, A5> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A2, A5> = TupleValues3<A1, A2, A5>(tuples.first, tuples.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_3_4")
public fun <A1, A2, A6> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A2, A6> = TupleValues3<A1, A2, A6>(tuples.first, tuples.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_5")
public fun <A1, A2, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A1, A2, A7> = TupleValues3<A1, A2, A7>(tuples.first, tuples.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_6")
public fun <A1, A2, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A1, A2, A8> = TupleValues3<A1, A2, A8>(tuples.first, tuples.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_7")
public fun <A1, A3, A4> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A3, A4> = TupleValues3<A1, A3, A4>(tuples.first, tuples.second.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_8_3_8")
public fun <A1, A3, A5> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A3, A5> = TupleValues3<A1, A3, A5>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_3_9")
public fun <A1, A3, A6> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A3, A6> = TupleValues3<A1, A3, A6>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_10")
public fun <A1, A3, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A1, A3, A7> = TupleValues3<A1, A3, A7>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_11")
public fun <A1, A3, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A1, A3, A8> = TupleValues3<A1, A3, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_12")
public fun <A1, A4, A5> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A4, A5> = TupleValues3<A1, A4, A5>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_3_13")
public fun <A1, A4, A6> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A4, A6> = TupleValues3<A1, A4, A6>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_14")
public fun <A1, A4, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A1, A4, A7> = TupleValues3<A1, A4, A7>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_15")
public fun <A1, A4, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A1, A4, A8> = TupleValues3<A1, A4, A8>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_16")
public fun <A1, A5, A6> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A1, A5, A6> = TupleValues3<A1, A5, A6>(tuples.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_17")
public fun <A1, A5, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A1, A5, A7> = TupleValues3<A1, A5, A7>(tuples.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_18")
public fun <A1, A5, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A1, A5, A8> = TupleValues3<A1, A5, A8>(tuples.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_19")
public fun <A1, A6, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues3<A1, A6, A7> = TupleValues3<A1, A6, A7>(tuples.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_20")
public fun <A1, A6, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues3<A1, A6, A8> = TupleValues3<A1, A6, A8>(tuples.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_21")
public fun <A1, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues3<A1, A7, A8> = TupleValues3<A1, A7, A8>(tuples.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_22")
public fun <A2, A3, A4> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A2, A3, A4> = TupleValues3<A2, A3, A4>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_8_3_23")
public fun <A2, A3, A5> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A2, A3, A5> = TupleValues3<A2, A3, A5>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_3_24")
public fun <A2, A3, A6> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A2, A3, A6> = TupleValues3<A2, A3, A6>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_25")
public fun <A2, A3, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A2, A3, A7> = TupleValues3<A2, A3, A7>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_26")
public fun <A2, A3, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A2, A3, A8> = TupleValues3<A2, A3, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_27")
public fun <A2, A4, A5> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A2, A4, A5> = TupleValues3<A2, A4, A5>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_3_28")
public fun <A2, A4, A6> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A2, A4, A6> = TupleValues3<A2, A4, A6>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_29")
public fun <A2, A4, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A2, A4, A7> = TupleValues3<A2, A4, A7>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_30")
public fun <A2, A4, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A2, A4, A8> = TupleValues3<A2, A4, A8>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_31")
public fun <A2, A5, A6> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A2, A5, A6> = TupleValues3<A2, A5, A6>(tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_32")
public fun <A2, A5, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A2, A5, A7> = TupleValues3<A2, A5, A7>(tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_33")
public fun <A2, A5, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A2, A5, A8> = TupleValues3<A2, A5, A8>(tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_34")
public fun <A2, A6, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues3<A2, A6, A7> = TupleValues3<A2, A6, A7>(tuples.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_35")
public fun <A2, A6, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues3<A2, A6, A8> = TupleValues3<A2, A6, A8>(tuples.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_36")
public fun <A2, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues3<A2, A7, A8> = TupleValues3<A2, A7, A8>(tuples.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_37")
public fun <A3, A4, A5> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues3<A3, A4, A5> = TupleValues3<A3, A4, A5>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_3_38")
public fun <A3, A4, A6> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A3, A4, A6> = TupleValues3<A3, A4, A6>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_39")
public fun <A3, A4, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A3, A4, A7> = TupleValues3<A3, A4, A7>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_40")
public fun <A3, A4, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A3, A4, A8> = TupleValues3<A3, A4, A8>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_41")
public fun <A3, A5, A6> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A3, A5, A6> = TupleValues3<A3, A5, A6>(tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_42")
public fun <A3, A5, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A3, A5, A7> = TupleValues3<A3, A5, A7>(tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_43")
public fun <A3, A5, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A3, A5, A8> = TupleValues3<A3, A5, A8>(tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_44")
public fun <A3, A6, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues3<A3, A6, A7> = TupleValues3<A3, A6, A7>(tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_45")
public fun <A3, A6, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues3<A3, A6, A8> = TupleValues3<A3, A6, A8>(tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_46")
public fun <A3, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues3<A3, A7, A8> = TupleValues3<A3, A7, A8>(tuples.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_47")
public fun <A4, A5, A6> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues3<A4, A5, A6> = TupleValues3<A4, A5, A6>(tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_48")
public fun <A4, A5, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues3<A4, A5, A7> = TupleValues3<A4, A5, A7>(tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_49")
public fun <A4, A5, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues3<A4, A5, A8> = TupleValues3<A4, A5, A8>(tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_50")
public fun <A4, A6, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues3<A4, A6, A7> = TupleValues3<A4, A6, A7>(tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_51")
public fun <A4, A6, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues3<A4, A6, A8> = TupleValues3<A4, A6, A8>(tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_52")
public fun <A4, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues3<A4, A7, A8> = TupleValues3<A4, A7, A8>(tuples.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_53")
public fun <A5, A6, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues3<A5, A6, A7> = TupleValues3<A5, A6, A7>(tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_3_54")
public fun <A5, A6, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues3<A5, A6, A8> = TupleValues3<A5, A6, A8>(tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_55")
public fun <A5, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues3<A5, A7, A8> = TupleValues3<A5, A7, A8>(tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_3_56")
public fun <A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues3<A6, A7, A8> = TupleValues3<A6, A7, A8>(tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_1")
public fun <A1, A2, A3, A4> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A2, A3, A4> = TupleValues4<A1, A2, A3, A4>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first)

@JvmName("tupleValues_8_4_2")
public fun <A1, A2, A3, A5> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A2, A3, A5> = TupleValues4<A1, A2, A3, A5>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_4_3")
public fun <A1, A2, A3, A6> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A2, A3, A6> = TupleValues4<A1, A2, A3, A6>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_4")
public fun <A1, A2, A3, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A2, A3, A7> = TupleValues4<A1, A2, A3, A7>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_5")
public fun <A1, A2, A3, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A2, A3, A8> = TupleValues4<A1, A2, A3, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_6")
public fun <A1, A2, A4, A5> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A2, A4, A5> = TupleValues4<A1, A2, A4, A5>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_4_7")
public fun <A1, A2, A4, A6> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A2, A4, A6> = TupleValues4<A1, A2, A4, A6>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_8")
public fun <A1, A2, A4, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A2, A4, A7> = TupleValues4<A1, A2, A4, A7>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_9")
public fun <A1, A2, A4, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A2, A4, A8> = TupleValues4<A1, A2, A4, A8>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_10")
public fun <A1, A2, A5, A6> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A2, A5, A6> = TupleValues4<A1, A2, A5, A6>(tuples.first, tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_11")
public fun <A1, A2, A5, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A2, A5, A7> = TupleValues4<A1, A2, A5, A7>(tuples.first, tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_12")
public fun <A1, A2, A5, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A2, A5, A8> = TupleValues4<A1, A2, A5, A8>(tuples.first, tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_13")
public fun <A1, A2, A6, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A2, A6, A7> = TupleValues4<A1, A2, A6, A7>(tuples.first, tuples.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_14")
public fun <A1, A2, A6, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A2, A6, A8> = TupleValues4<A1, A2, A6, A8>(tuples.first, tuples.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_15")
public fun <A1, A2, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A1, A2, A7, A8> = TupleValues4<A1, A2, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_16")
public fun <A1, A3, A4, A5> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A3, A4, A5> = TupleValues4<A1, A3, A4, A5>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_4_17")
public fun <A1, A3, A4, A6> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A3, A4, A6> = TupleValues4<A1, A3, A4, A6>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_18")
public fun <A1, A3, A4, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A3, A4, A7> = TupleValues4<A1, A3, A4, A7>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_19")
public fun <A1, A3, A4, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A3, A4, A8> = TupleValues4<A1, A3, A4, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_20")
public fun <A1, A3, A5, A6> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A3, A5, A6> = TupleValues4<A1, A3, A5, A6>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_21")
public fun <A1, A3, A5, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A3, A5, A7> = TupleValues4<A1, A3, A5, A7>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_22")
public fun <A1, A3, A5, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A3, A5, A8> = TupleValues4<A1, A3, A5, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_23")
public fun <A1, A3, A6, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A3, A6, A7> = TupleValues4<A1, A3, A6, A7>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_24")
public fun <A1, A3, A6, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A3, A6, A8> = TupleValues4<A1, A3, A6, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_25")
public fun <A1, A3, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A1, A3, A7, A8> = TupleValues4<A1, A3, A7, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_26")
public fun <A1, A4, A5, A6> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A1, A4, A5, A6> = TupleValues4<A1, A4, A5, A6>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_27")
public fun <A1, A4, A5, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A4, A5, A7> = TupleValues4<A1, A4, A5, A7>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_28")
public fun <A1, A4, A5, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A4, A5, A8> = TupleValues4<A1, A4, A5, A8>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_29")
public fun <A1, A4, A6, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A4, A6, A7> = TupleValues4<A1, A4, A6, A7>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_30")
public fun <A1, A4, A6, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A4, A6, A8> = TupleValues4<A1, A4, A6, A8>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_31")
public fun <A1, A4, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A1, A4, A7, A8> = TupleValues4<A1, A4, A7, A8>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_32")
public fun <A1, A5, A6, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A1, A5, A6, A7> = TupleValues4<A1, A5, A6, A7>(tuples.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_33")
public fun <A1, A5, A6, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A1, A5, A6, A8> = TupleValues4<A1, A5, A6, A8>(tuples.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_34")
public fun <A1, A5, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A1, A5, A7, A8> = TupleValues4<A1, A5, A7, A8>(tuples.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_35")
public fun <A1, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues4<A1, A6, A7, A8> = TupleValues4<A1, A6, A7, A8>(tuples.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_36")
public fun <A2, A3, A4, A5> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues4<A2, A3, A4, A5> = TupleValues4<A2, A3, A4, A5>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_4_37")
public fun <A2, A3, A4, A6> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A2, A3, A4, A6> = TupleValues4<A2, A3, A4, A6>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_38")
public fun <A2, A3, A4, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A2, A3, A4, A7> = TupleValues4<A2, A3, A4, A7>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_39")
public fun <A2, A3, A4, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A2, A3, A4, A8> = TupleValues4<A2, A3, A4, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_40")
public fun <A2, A3, A5, A6> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A2, A3, A5, A6> = TupleValues4<A2, A3, A5, A6>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_41")
public fun <A2, A3, A5, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A2, A3, A5, A7> = TupleValues4<A2, A3, A5, A7>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_42")
public fun <A2, A3, A5, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A2, A3, A5, A8> = TupleValues4<A2, A3, A5, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_43")
public fun <A2, A3, A6, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A2, A3, A6, A7> = TupleValues4<A2, A3, A6, A7>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_44")
public fun <A2, A3, A6, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A2, A3, A6, A8> = TupleValues4<A2, A3, A6, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_45")
public fun <A2, A3, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A2, A3, A7, A8> = TupleValues4<A2, A3, A7, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_46")
public fun <A2, A4, A5, A6> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A2, A4, A5, A6> = TupleValues4<A2, A4, A5, A6>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_47")
public fun <A2, A4, A5, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A2, A4, A5, A7> = TupleValues4<A2, A4, A5, A7>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_48")
public fun <A2, A4, A5, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A2, A4, A5, A8> = TupleValues4<A2, A4, A5, A8>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_49")
public fun <A2, A4, A6, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A2, A4, A6, A7> = TupleValues4<A2, A4, A6, A7>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_50")
public fun <A2, A4, A6, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A2, A4, A6, A8> = TupleValues4<A2, A4, A6, A8>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_51")
public fun <A2, A4, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A2, A4, A7, A8> = TupleValues4<A2, A4, A7, A8>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_52")
public fun <A2, A5, A6, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A2, A5, A6, A7> = TupleValues4<A2, A5, A6, A7>(tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_53")
public fun <A2, A5, A6, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A2, A5, A6, A8> = TupleValues4<A2, A5, A6, A8>(tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_54")
public fun <A2, A5, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A2, A5, A7, A8> = TupleValues4<A2, A5, A7, A8>(tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_55")
public fun <A2, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues4<A2, A6, A7, A8> = TupleValues4<A2, A6, A7, A8>(tuples.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_56")
public fun <A3, A4, A5, A6> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues4<A3, A4, A5, A6> = TupleValues4<A3, A4, A5, A6>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_57")
public fun <A3, A4, A5, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues4<A3, A4, A5, A7> = TupleValues4<A3, A4, A5, A7>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_58")
public fun <A3, A4, A5, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues4<A3, A4, A5, A8> = TupleValues4<A3, A4, A5, A8>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_59")
public fun <A3, A4, A6, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A3, A4, A6, A7> = TupleValues4<A3, A4, A6, A7>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_60")
public fun <A3, A4, A6, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A3, A4, A6, A8> = TupleValues4<A3, A4, A6, A8>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_61")
public fun <A3, A4, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A3, A4, A7, A8> = TupleValues4<A3, A4, A7, A8>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_62")
public fun <A3, A5, A6, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A3, A5, A6, A7> = TupleValues4<A3, A5, A6, A7>(tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_63")
public fun <A3, A5, A6, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A3, A5, A6, A8> = TupleValues4<A3, A5, A6, A8>(tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_64")
public fun <A3, A5, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A3, A5, A7, A8> = TupleValues4<A3, A5, A7, A8>(tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_65")
public fun <A3, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues4<A3, A6, A7, A8> = TupleValues4<A3, A6, A7, A8>(tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_66")
public fun <A4, A5, A6, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues4<A4, A5, A6, A7> = TupleValues4<A4, A5, A6, A7>(tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_4_67")
public fun <A4, A5, A6, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues4<A4, A5, A6, A8> = TupleValues4<A4, A5, A6, A8>(tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_68")
public fun <A4, A5, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues4<A4, A5, A7, A8> = TupleValues4<A4, A5, A7, A8>(tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_69")
public fun <A4, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues4<A4, A6, A7, A8> = TupleValues4<A4, A6, A7, A8>(tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_4_70")
public fun <A5, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues4<A5, A6, A7, A8> = TupleValues4<A5, A6, A7, A8>(tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_1")
public fun <A1, A2, A3, A4, A5> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, Unit>>>>>>>): TupleValues5<A1, A2, A3, A4, A5> = TupleValues5<A1, A2, A3, A4, A5>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first)

@JvmName("tupleValues_8_5_2")
public fun <A1, A2, A3, A4, A6> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues5<A1, A2, A3, A4, A6> = TupleValues5<A1, A2, A3, A4, A6>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_3")
public fun <A1, A2, A3, A4, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A2, A3, A4, A7> = TupleValues5<A1, A2, A3, A4, A7>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_4")
public fun <A1, A2, A3, A4, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A2, A3, A4, A8> = TupleValues5<A1, A2, A3, A4, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_5")
public fun <A1, A2, A3, A5, A6> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues5<A1, A2, A3, A5, A6> = TupleValues5<A1, A2, A3, A5, A6>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_6")
public fun <A1, A2, A3, A5, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A2, A3, A5, A7> = TupleValues5<A1, A2, A3, A5, A7>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_7")
public fun <A1, A2, A3, A5, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A2, A3, A5, A8> = TupleValues5<A1, A2, A3, A5, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_8")
public fun <A1, A2, A3, A6, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A2, A3, A6, A7> = TupleValues5<A1, A2, A3, A6, A7>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_9")
public fun <A1, A2, A3, A6, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A2, A3, A6, A8> = TupleValues5<A1, A2, A3, A6, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_10")
public fun <A1, A2, A3, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A1, A2, A3, A7, A8> = TupleValues5<A1, A2, A3, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_11")
public fun <A1, A2, A4, A5, A6> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues5<A1, A2, A4, A5, A6> = TupleValues5<A1, A2, A4, A5, A6>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_12")
public fun <A1, A2, A4, A5, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A2, A4, A5, A7> = TupleValues5<A1, A2, A4, A5, A7>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_13")
public fun <A1, A2, A4, A5, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A2, A4, A5, A8> = TupleValues5<A1, A2, A4, A5, A8>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_14")
public fun <A1, A2, A4, A6, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A2, A4, A6, A7> = TupleValues5<A1, A2, A4, A6, A7>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_15")
public fun <A1, A2, A4, A6, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A2, A4, A6, A8> = TupleValues5<A1, A2, A4, A6, A8>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_16")
public fun <A1, A2, A4, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A1, A2, A4, A7, A8> = TupleValues5<A1, A2, A4, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_17")
public fun <A1, A2, A5, A6, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A2, A5, A6, A7> = TupleValues5<A1, A2, A5, A6, A7>(tuples.first, tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_18")
public fun <A1, A2, A5, A6, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A2, A5, A6, A8> = TupleValues5<A1, A2, A5, A6, A8>(tuples.first, tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_19")
public fun <A1, A2, A5, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A1, A2, A5, A7, A8> = TupleValues5<A1, A2, A5, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_20")
public fun <A1, A2, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A1, A2, A6, A7, A8> = TupleValues5<A1, A2, A6, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_21")
public fun <A1, A3, A4, A5, A6> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues5<A1, A3, A4, A5, A6> = TupleValues5<A1, A3, A4, A5, A6>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_22")
public fun <A1, A3, A4, A5, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A3, A4, A5, A7> = TupleValues5<A1, A3, A4, A5, A7>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_23")
public fun <A1, A3, A4, A5, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A3, A4, A5, A8> = TupleValues5<A1, A3, A4, A5, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_24")
public fun <A1, A3, A4, A6, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A3, A4, A6, A7> = TupleValues5<A1, A3, A4, A6, A7>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_25")
public fun <A1, A3, A4, A6, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A3, A4, A6, A8> = TupleValues5<A1, A3, A4, A6, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_26")
public fun <A1, A3, A4, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A1, A3, A4, A7, A8> = TupleValues5<A1, A3, A4, A7, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_27")
public fun <A1, A3, A5, A6, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A3, A5, A6, A7> = TupleValues5<A1, A3, A5, A6, A7>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_28")
public fun <A1, A3, A5, A6, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A3, A5, A6, A8> = TupleValues5<A1, A3, A5, A6, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_29")
public fun <A1, A3, A5, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A1, A3, A5, A7, A8> = TupleValues5<A1, A3, A5, A7, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_30")
public fun <A1, A3, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A1, A3, A6, A7, A8> = TupleValues5<A1, A3, A6, A7, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_31")
public fun <A1, A4, A5, A6, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A1, A4, A5, A6, A7> = TupleValues5<A1, A4, A5, A6, A7>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_32")
public fun <A1, A4, A5, A6, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A1, A4, A5, A6, A8> = TupleValues5<A1, A4, A5, A6, A8>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_33")
public fun <A1, A4, A5, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A1, A4, A5, A7, A8> = TupleValues5<A1, A4, A5, A7, A8>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_34")
public fun <A1, A4, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A1, A4, A6, A7, A8> = TupleValues5<A1, A4, A6, A7, A8>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_35")
public fun <A1, A5, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A1, A5, A6, A7, A8> = TupleValues5<A1, A5, A6, A7, A8>(tuples.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_36")
public fun <A2, A3, A4, A5, A6> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues5<A2, A3, A4, A5, A6> = TupleValues5<A2, A3, A4, A5, A6>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_37")
public fun <A2, A3, A4, A5, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues5<A2, A3, A4, A5, A7> = TupleValues5<A2, A3, A4, A5, A7>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_38")
public fun <A2, A3, A4, A5, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues5<A2, A3, A4, A5, A8> = TupleValues5<A2, A3, A4, A5, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_39")
public fun <A2, A3, A4, A6, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A2, A3, A4, A6, A7> = TupleValues5<A2, A3, A4, A6, A7>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_40")
public fun <A2, A3, A4, A6, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A2, A3, A4, A6, A8> = TupleValues5<A2, A3, A4, A6, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_41")
public fun <A2, A3, A4, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A2, A3, A4, A7, A8> = TupleValues5<A2, A3, A4, A7, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_42")
public fun <A2, A3, A5, A6, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A2, A3, A5, A6, A7> = TupleValues5<A2, A3, A5, A6, A7>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_43")
public fun <A2, A3, A5, A6, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A2, A3, A5, A6, A8> = TupleValues5<A2, A3, A5, A6, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_44")
public fun <A2, A3, A5, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A2, A3, A5, A7, A8> = TupleValues5<A2, A3, A5, A7, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_45")
public fun <A2, A3, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A2, A3, A6, A7, A8> = TupleValues5<A2, A3, A6, A7, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_46")
public fun <A2, A4, A5, A6, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A2, A4, A5, A6, A7> = TupleValues5<A2, A4, A5, A6, A7>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_47")
public fun <A2, A4, A5, A6, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A2, A4, A5, A6, A8> = TupleValues5<A2, A4, A5, A6, A8>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_48")
public fun <A2, A4, A5, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A2, A4, A5, A7, A8> = TupleValues5<A2, A4, A5, A7, A8>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_49")
public fun <A2, A4, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A2, A4, A6, A7, A8> = TupleValues5<A2, A4, A6, A7, A8>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_50")
public fun <A2, A5, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A2, A5, A6, A7, A8> = TupleValues5<A2, A5, A6, A7, A8>(tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_51")
public fun <A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues5<A3, A4, A5, A6, A7> = TupleValues5<A3, A4, A5, A6, A7>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_5_52")
public fun <A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues5<A3, A4, A5, A6, A8> = TupleValues5<A3, A4, A5, A6, A8>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_53")
public fun <A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues5<A3, A4, A5, A7, A8> = TupleValues5<A3, A4, A5, A7, A8>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_54")
public fun <A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A3, A4, A6, A7, A8> = TupleValues5<A3, A4, A6, A7, A8>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_55")
public fun <A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A3, A5, A6, A7, A8> = TupleValues5<A3, A5, A6, A7, A8>(tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_5_56")
public fun <A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues5<A4, A5, A6, A7, A8> = TupleValues5<A4, A5, A6, A7, A8>(tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_1")
public fun <A1, A2, A3, A4, A5, A6> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, Unit>>>>>>>): TupleValues6<A1, A2, A3, A4, A5, A6> = TupleValues6<A1, A2, A3, A4, A5, A6>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first)

@JvmName("tupleValues_8_6_2")
public fun <A1, A2, A3, A4, A5, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, Unit>>>>>>>): TupleValues6<A1, A2, A3, A4, A5, A7> = TupleValues6<A1, A2, A3, A4, A5, A7>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_6_3")
public fun <A1, A2, A3, A4, A5, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<Unit, A8>>>>>>>): TupleValues6<A1, A2, A3, A4, A5, A8> = TupleValues6<A1, A2, A3, A4, A5, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_4")
public fun <A1, A2, A3, A4, A6, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues6<A1, A2, A3, A4, A6, A7> = TupleValues6<A1, A2, A3, A4, A6, A7>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_6_5")
public fun <A1, A2, A3, A4, A6, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues6<A1, A2, A3, A4, A6, A8> = TupleValues6<A1, A2, A3, A4, A6, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_6")
public fun <A1, A2, A3, A4, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues6<A1, A2, A3, A4, A7, A8> = TupleValues6<A1, A2, A3, A4, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_7")
public fun <A1, A2, A3, A5, A6, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues6<A1, A2, A3, A5, A6, A7> = TupleValues6<A1, A2, A3, A5, A6, A7>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_6_8")
public fun <A1, A2, A3, A5, A6, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues6<A1, A2, A3, A5, A6, A8> = TupleValues6<A1, A2, A3, A5, A6, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_9")
public fun <A1, A2, A3, A5, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues6<A1, A2, A3, A5, A7, A8> = TupleValues6<A1, A2, A3, A5, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_10")
public fun <A1, A2, A3, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A1, A2, A3, A6, A7, A8> = TupleValues6<A1, A2, A3, A6, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_11")
public fun <A1, A2, A4, A5, A6, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues6<A1, A2, A4, A5, A6, A7> = TupleValues6<A1, A2, A4, A5, A6, A7>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_6_12")
public fun <A1, A2, A4, A5, A6, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues6<A1, A2, A4, A5, A6, A8> = TupleValues6<A1, A2, A4, A5, A6, A8>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_13")
public fun <A1, A2, A4, A5, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues6<A1, A2, A4, A5, A7, A8> = TupleValues6<A1, A2, A4, A5, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_14")
public fun <A1, A2, A4, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A1, A2, A4, A6, A7, A8> = TupleValues6<A1, A2, A4, A6, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_15")
public fun <A1, A2, A5, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A1, A2, A5, A6, A7, A8> = TupleValues6<A1, A2, A5, A6, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_16")
public fun <A1, A3, A4, A5, A6, A7> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues6<A1, A3, A4, A5, A6, A7> = TupleValues6<A1, A3, A4, A5, A6, A7>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_6_17")
public fun <A1, A3, A4, A5, A6, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues6<A1, A3, A4, A5, A6, A8> = TupleValues6<A1, A3, A4, A5, A6, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_18")
public fun <A1, A3, A4, A5, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues6<A1, A3, A4, A5, A7, A8> = TupleValues6<A1, A3, A4, A5, A7, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_19")
public fun <A1, A3, A4, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A1, A3, A4, A6, A7, A8> = TupleValues6<A1, A3, A4, A6, A7, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_20")
public fun <A1, A3, A5, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A1, A3, A5, A6, A7, A8> = TupleValues6<A1, A3, A5, A6, A7, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_21")
public fun <A1, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A1, A4, A5, A6, A7, A8> = TupleValues6<A1, A4, A5, A6, A7, A8>(tuples.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_22")
public fun <A2, A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues6<A2, A3, A4, A5, A6, A7> = TupleValues6<A2, A3, A4, A5, A6, A7>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_6_23")
public fun <A2, A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues6<A2, A3, A4, A5, A6, A8> = TupleValues6<A2, A3, A4, A5, A6, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_24")
public fun <A2, A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues6<A2, A3, A4, A5, A7, A8> = TupleValues6<A2, A3, A4, A5, A7, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_25")
public fun <A2, A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A2, A3, A4, A6, A7, A8> = TupleValues6<A2, A3, A4, A6, A7, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_26")
public fun <A2, A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A2, A3, A5, A6, A7, A8> = TupleValues6<A2, A3, A5, A6, A7, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_27")
public fun <A2, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A2, A4, A5, A6, A7, A8> = TupleValues6<A2, A4, A5, A6, A7, A8>(tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_6_28")
public fun <A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues6<A3, A4, A5, A6, A7, A8> = TupleValues6<A3, A4, A5, A6, A7, A8>(tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_7_1")
public fun <A1, A2, A3, A4, A5, A6, A7> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<A7, Unit>>>>>>>): TupleValues7<A1, A2, A3, A4, A5, A6, A7> = TupleValues7<A1, A2, A3, A4, A5, A6, A7>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first)

@JvmName("tupleValues_8_7_2")
public fun <A1, A2, A3, A4, A5, A6, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<Unit, A8>>>>>>>): TupleValues7<A1, A2, A3, A4, A5, A6, A8> = TupleValues7<A1, A2, A3, A4, A5, A6, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_7_3")
public fun <A1, A2, A3, A4, A5, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<Unit, Pair<A7, A8>>>>>>>): TupleValues7<A1, A2, A3, A4, A5, A7, A8> = TupleValues7<A1, A2, A3, A4, A5, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_7_4")
public fun <A1, A2, A3, A4, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<Unit, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues7<A1, A2, A3, A4, A6, A7, A8> = TupleValues7<A1, A2, A3, A4, A6, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_7_5")
public fun <A1, A2, A3, A5, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<Unit, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues7<A1, A2, A3, A5, A6, A7, A8> = TupleValues7<A1, A2, A3, A5, A6, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_7_6")
public fun <A1, A2, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<Unit, Pair<A4, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues7<A1, A2, A4, A5, A6, A7, A8> = TupleValues7<A1, A2, A4, A5, A6, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_7_7")
public fun <A1, A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<Unit, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues7<A1, A3, A4, A5, A6, A7, A8> = TupleValues7<A1, A3, A4, A5, A6, A7, A8>(tuples.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_7_8")
public fun <A2, A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Unit, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues7<A2, A3, A4, A5, A6, A7, A8> = TupleValues7<A2, A3, A4, A5, A6, A7, A8>(tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)

@JvmName("tupleValues_8_8_1")
public fun <A1, A2, A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<A1, Pair<A2, Pair<A3, Pair<A4, Pair<A5, Pair<A6, Pair<A7, A8>>>>>>>): TupleValues8<A1, A2, A3, A4, A5, A6, A7, A8> = TupleValues8<A1, A2, A3, A4, A5, A6, A7, A8>(tuples.first, tuples.second.first, tuples.second.second.first, tuples.second.second.second.first, tuples.second.second.second.second.first, tuples.second.second.second.second.second.first, tuples.second.second.second.second.second.second.first, tuples.second.second.second.second.second.second.second)
