package io.github.bbasinsk.tuple

import kotlin.Pair
import kotlin.Unit
import kotlin.jvm.JvmName

public data class TupleValues9<A1, A2, A3, A4, A5, A6, A7, A8, A9>(
  public val a1: A1,
  public val a2: A2,
  public val a3: A3,
  public val a4: A4,
  public val a5: A5,
  public val a6: A6,
  public val a7: A7,
  public val a8: A8,
  public val a9: A9,
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
  ) -> B): B = f(a1, a2, a3, a4, a5, a6, a7, a8, a9)
}

@JvmName("tupleValues_9_1_1")
public fun <A1> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A1> = TupleValues1<A1>(tuples.first.first.first.first.first.first.first.first)

@JvmName("tupleValues_9_1_2")
public fun <A2> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A2> = TupleValues1<A2>(tuples.first.first.first.first.first.first.first.second)

@JvmName("tupleValues_9_1_3")
public fun <A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A3> = TupleValues1<A3>(tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_9_1_4")
public fun <A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A4> = TupleValues1<A4>(tuples.first.first.first.first.first.second)

@JvmName("tupleValues_9_1_5")
public fun <A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A5> = TupleValues1<A5>(tuples.first.first.first.first.second)

@JvmName("tupleValues_9_1_6")
public fun <A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues1<A6> = TupleValues1<A6>(tuples.first.first.first.second)

@JvmName("tupleValues_9_1_7")
public fun <A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues1<A7> = TupleValues1<A7>(tuples.first.first.second)

@JvmName("tupleValues_9_1_8")
public fun <A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues1<A8> = TupleValues1<A8>(tuples.first.second)

@JvmName("tupleValues_9_1_9")
public fun <A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues1<A9> = TupleValues1<A9>(tuples.second)

@JvmName("tupleValues_9_2_1")
public fun <A1, A2> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A2> = TupleValues2<A1, A2>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second)

@JvmName("tupleValues_9_2_2")
public fun <A1, A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A3> = TupleValues2<A1, A3>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_9_2_3")
public fun <A1, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A4> = TupleValues2<A1, A4>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_9_2_4")
public fun <A1, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A5> = TupleValues2<A1, A5>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_2_5")
public fun <A1, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues2<A1, A6> = TupleValues2<A1, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.second)

@JvmName("tupleValues_9_2_6")
public fun <A1, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues2<A1, A7> = TupleValues2<A1, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.second)

@JvmName("tupleValues_9_2_7")
public fun <A1, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues2<A1, A8> = TupleValues2<A1, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.second)

@JvmName("tupleValues_9_2_8")
public fun <A1, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues2<A1, A9> = TupleValues2<A1, A9>(tuples.first.first.first.first.first.first.first.first, tuples.second)

@JvmName("tupleValues_9_2_9")
public fun <A2, A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A2, A3> = TupleValues2<A2, A3>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_9_2_10")
public fun <A2, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A2, A4> = TupleValues2<A2, A4>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_9_2_11")
public fun <A2, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A2, A5> = TupleValues2<A2, A5>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_2_12")
public fun <A2, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues2<A2, A6> = TupleValues2<A2, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_2_13")
public fun <A2, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues2<A2, A7> = TupleValues2<A2, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_2_14")
public fun <A2, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues2<A2, A8> = TupleValues2<A2, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_2_15")
public fun <A2, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues2<A2, A9> = TupleValues2<A2, A9>(tuples.first.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_2_16")
public fun <A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A3, A4> = TupleValues2<A3, A4>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_9_2_17")
public fun <A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A3, A5> = TupleValues2<A3, A5>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_2_18")
public fun <A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues2<A3, A6> = TupleValues2<A3, A6>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_2_19")
public fun <A3, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues2<A3, A7> = TupleValues2<A3, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_2_20")
public fun <A3, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues2<A3, A8> = TupleValues2<A3, A8>(tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_2_21")
public fun <A3, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues2<A3, A9> = TupleValues2<A3, A9>(tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_2_22")
public fun <A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A4, A5> = TupleValues2<A4, A5>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_2_23")
public fun <A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues2<A4, A6> = TupleValues2<A4, A6>(tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_2_24")
public fun <A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues2<A4, A7> = TupleValues2<A4, A7>(tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_2_25")
public fun <A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues2<A4, A8> = TupleValues2<A4, A8>(tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_2_26")
public fun <A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues2<A4, A9> = TupleValues2<A4, A9>(tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_2_27")
public fun <A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues2<A5, A6> = TupleValues2<A5, A6>(tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_2_28")
public fun <A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues2<A5, A7> = TupleValues2<A5, A7>(tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_2_29")
public fun <A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues2<A5, A8> = TupleValues2<A5, A8>(tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_2_30")
public fun <A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues2<A5, A9> = TupleValues2<A5, A9>(tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_2_31")
public fun <A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues2<A6, A7> = TupleValues2<A6, A7>(tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_2_32")
public fun <A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues2<A6, A8> = TupleValues2<A6, A8>(tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_2_33")
public fun <A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues2<A6, A9> = TupleValues2<A6, A9>(tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_2_34")
public fun <A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues2<A7, A8> = TupleValues2<A7, A8>(tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_2_35")
public fun <A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues2<A7, A9> = TupleValues2<A7, A9>(tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_2_36")
public fun <A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues2<A8, A9> = TupleValues2<A8, A9>(tuples.first.second, tuples.second)

@JvmName("tupleValues_9_3_1")
public fun <A1, A2, A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A3> = TupleValues3<A1, A2, A3>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_9_3_2")
public fun <A1, A2, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A4> = TupleValues3<A1, A2, A4>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_9_3_3")
public fun <A1, A2, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A5> = TupleValues3<A1, A2, A5>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_3_4")
public fun <A1, A2, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A6> = TupleValues3<A1, A2, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_5")
public fun <A1, A2, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues3<A1, A2, A7> = TupleValues3<A1, A2, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_6")
public fun <A1, A2, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues3<A1, A2, A8> = TupleValues3<A1, A2, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_7")
public fun <A1, A2, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues3<A1, A2, A9> = TupleValues3<A1, A2, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_8")
public fun <A1, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A3, A4> = TupleValues3<A1, A3, A4>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_9_3_9")
public fun <A1, A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A3, A5> = TupleValues3<A1, A3, A5>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_3_10")
public fun <A1, A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues3<A1, A3, A6> = TupleValues3<A1, A3, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_11")
public fun <A1, A3, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues3<A1, A3, A7> = TupleValues3<A1, A3, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_12")
public fun <A1, A3, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues3<A1, A3, A8> = TupleValues3<A1, A3, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_13")
public fun <A1, A3, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues3<A1, A3, A9> = TupleValues3<A1, A3, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_14")
public fun <A1, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A4, A5> = TupleValues3<A1, A4, A5>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_3_15")
public fun <A1, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues3<A1, A4, A6> = TupleValues3<A1, A4, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_16")
public fun <A1, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues3<A1, A4, A7> = TupleValues3<A1, A4, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_17")
public fun <A1, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues3<A1, A4, A8> = TupleValues3<A1, A4, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_18")
public fun <A1, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues3<A1, A4, A9> = TupleValues3<A1, A4, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_19")
public fun <A1, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues3<A1, A5, A6> = TupleValues3<A1, A5, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_20")
public fun <A1, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues3<A1, A5, A7> = TupleValues3<A1, A5, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_21")
public fun <A1, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues3<A1, A5, A8> = TupleValues3<A1, A5, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_22")
public fun <A1, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues3<A1, A5, A9> = TupleValues3<A1, A5, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_23")
public fun <A1, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues3<A1, A6, A7> = TupleValues3<A1, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_24")
public fun <A1, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues3<A1, A6, A8> = TupleValues3<A1, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_25")
public fun <A1, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues3<A1, A6, A9> = TupleValues3<A1, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_26")
public fun <A1, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues3<A1, A7, A8> = TupleValues3<A1, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_27")
public fun <A1, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues3<A1, A7, A9> = TupleValues3<A1, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_28")
public fun <A1, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues3<A1, A8, A9> = TupleValues3<A1, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_3_29")
public fun <A2, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A2, A3, A4> = TupleValues3<A2, A3, A4>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_9_3_30")
public fun <A2, A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A2, A3, A5> = TupleValues3<A2, A3, A5>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_3_31")
public fun <A2, A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues3<A2, A3, A6> = TupleValues3<A2, A3, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_32")
public fun <A2, A3, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues3<A2, A3, A7> = TupleValues3<A2, A3, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_33")
public fun <A2, A3, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues3<A2, A3, A8> = TupleValues3<A2, A3, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_34")
public fun <A2, A3, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues3<A2, A3, A9> = TupleValues3<A2, A3, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_35")
public fun <A2, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A2, A4, A5> = TupleValues3<A2, A4, A5>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_3_36")
public fun <A2, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues3<A2, A4, A6> = TupleValues3<A2, A4, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_37")
public fun <A2, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues3<A2, A4, A7> = TupleValues3<A2, A4, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_38")
public fun <A2, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues3<A2, A4, A8> = TupleValues3<A2, A4, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_39")
public fun <A2, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues3<A2, A4, A9> = TupleValues3<A2, A4, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_40")
public fun <A2, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues3<A2, A5, A6> = TupleValues3<A2, A5, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_41")
public fun <A2, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues3<A2, A5, A7> = TupleValues3<A2, A5, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_42")
public fun <A2, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues3<A2, A5, A8> = TupleValues3<A2, A5, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_43")
public fun <A2, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues3<A2, A5, A9> = TupleValues3<A2, A5, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_44")
public fun <A2, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues3<A2, A6, A7> = TupleValues3<A2, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_45")
public fun <A2, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues3<A2, A6, A8> = TupleValues3<A2, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_46")
public fun <A2, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues3<A2, A6, A9> = TupleValues3<A2, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_47")
public fun <A2, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues3<A2, A7, A8> = TupleValues3<A2, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_48")
public fun <A2, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues3<A2, A7, A9> = TupleValues3<A2, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_49")
public fun <A2, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues3<A2, A8, A9> = TupleValues3<A2, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_3_50")
public fun <A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A3, A4, A5> = TupleValues3<A3, A4, A5>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_3_51")
public fun <A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues3<A3, A4, A6> = TupleValues3<A3, A4, A6>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_52")
public fun <A3, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues3<A3, A4, A7> = TupleValues3<A3, A4, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_53")
public fun <A3, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues3<A3, A4, A8> = TupleValues3<A3, A4, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_54")
public fun <A3, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues3<A3, A4, A9> = TupleValues3<A3, A4, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_55")
public fun <A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues3<A3, A5, A6> = TupleValues3<A3, A5, A6>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_56")
public fun <A3, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues3<A3, A5, A7> = TupleValues3<A3, A5, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_57")
public fun <A3, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues3<A3, A5, A8> = TupleValues3<A3, A5, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_58")
public fun <A3, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues3<A3, A5, A9> = TupleValues3<A3, A5, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_59")
public fun <A3, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues3<A3, A6, A7> = TupleValues3<A3, A6, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_60")
public fun <A3, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues3<A3, A6, A8> = TupleValues3<A3, A6, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_61")
public fun <A3, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues3<A3, A6, A9> = TupleValues3<A3, A6, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_62")
public fun <A3, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues3<A3, A7, A8> = TupleValues3<A3, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_63")
public fun <A3, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues3<A3, A7, A9> = TupleValues3<A3, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_64")
public fun <A3, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues3<A3, A8, A9> = TupleValues3<A3, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_3_65")
public fun <A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues3<A4, A5, A6> = TupleValues3<A4, A5, A6>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_3_66")
public fun <A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues3<A4, A5, A7> = TupleValues3<A4, A5, A7>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_67")
public fun <A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues3<A4, A5, A8> = TupleValues3<A4, A5, A8>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_68")
public fun <A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues3<A4, A5, A9> = TupleValues3<A4, A5, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_69")
public fun <A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues3<A4, A6, A7> = TupleValues3<A4, A6, A7>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_70")
public fun <A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues3<A4, A6, A8> = TupleValues3<A4, A6, A8>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_71")
public fun <A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues3<A4, A6, A9> = TupleValues3<A4, A6, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_72")
public fun <A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues3<A4, A7, A8> = TupleValues3<A4, A7, A8>(tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_73")
public fun <A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues3<A4, A7, A9> = TupleValues3<A4, A7, A9>(tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_74")
public fun <A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues3<A4, A8, A9> = TupleValues3<A4, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_3_75")
public fun <A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>): TupleValues3<A5, A6, A7> = TupleValues3<A5, A6, A7>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_3_76")
public fun <A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>): TupleValues3<A5, A6, A8> = TupleValues3<A5, A6, A8>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_77")
public fun <A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>): TupleValues3<A5, A6, A9> = TupleValues3<A5, A6, A9>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_78")
public fun <A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>): TupleValues3<A5, A7, A8> = TupleValues3<A5, A7, A8>(tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_79")
public fun <A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>): TupleValues3<A5, A7, A9> = TupleValues3<A5, A7, A9>(tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_80")
public fun <A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>): TupleValues3<A5, A8, A9> = TupleValues3<A5, A8, A9>(tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_3_81")
public fun <A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>): TupleValues3<A6, A7, A8> = TupleValues3<A6, A7, A8>(tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_3_82")
public fun <A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>): TupleValues3<A6, A7, A9> = TupleValues3<A6, A7, A9>(tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_3_83")
public fun <A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>): TupleValues3<A6, A8, A9> = TupleValues3<A6, A8, A9>(tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_3_84")
public fun <A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>): TupleValues3<A7, A8, A9> = TupleValues3<A7, A8, A9>(tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_1")
public fun <A1, A2, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A3, A4> = TupleValues4<A1, A2, A3, A4>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_9_4_2")
public fun <A1, A2, A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A3, A5> = TupleValues4<A1, A2, A3, A5>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_4_3")
public fun <A1, A2, A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A3, A6> = TupleValues4<A1, A2, A3, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_4")
public fun <A1, A2, A3, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues4<A1, A2, A3, A7> = TupleValues4<A1, A2, A3, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_5")
public fun <A1, A2, A3, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues4<A1, A2, A3, A8> = TupleValues4<A1, A2, A3, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_6")
public fun <A1, A2, A3, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues4<A1, A2, A3, A9> = TupleValues4<A1, A2, A3, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_7")
public fun <A1, A2, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A4, A5> = TupleValues4<A1, A2, A4, A5>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_4_8")
public fun <A1, A2, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A4, A6> = TupleValues4<A1, A2, A4, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_9")
public fun <A1, A2, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues4<A1, A2, A4, A7> = TupleValues4<A1, A2, A4, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_10")
public fun <A1, A2, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues4<A1, A2, A4, A8> = TupleValues4<A1, A2, A4, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_11")
public fun <A1, A2, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues4<A1, A2, A4, A9> = TupleValues4<A1, A2, A4, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_12")
public fun <A1, A2, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A5, A6> = TupleValues4<A1, A2, A5, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_13")
public fun <A1, A2, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues4<A1, A2, A5, A7> = TupleValues4<A1, A2, A5, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_14")
public fun <A1, A2, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues4<A1, A2, A5, A8> = TupleValues4<A1, A2, A5, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_15")
public fun <A1, A2, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues4<A1, A2, A5, A9> = TupleValues4<A1, A2, A5, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_16")
public fun <A1, A2, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues4<A1, A2, A6, A7> = TupleValues4<A1, A2, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_17")
public fun <A1, A2, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues4<A1, A2, A6, A8> = TupleValues4<A1, A2, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_18")
public fun <A1, A2, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues4<A1, A2, A6, A9> = TupleValues4<A1, A2, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_19")
public fun <A1, A2, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues4<A1, A2, A7, A8> = TupleValues4<A1, A2, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_20")
public fun <A1, A2, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues4<A1, A2, A7, A9> = TupleValues4<A1, A2, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_21")
public fun <A1, A2, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues4<A1, A2, A8, A9> = TupleValues4<A1, A2, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_22")
public fun <A1, A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A3, A4, A5> = TupleValues4<A1, A3, A4, A5>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_4_23")
public fun <A1, A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues4<A1, A3, A4, A6> = TupleValues4<A1, A3, A4, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_24")
public fun <A1, A3, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues4<A1, A3, A4, A7> = TupleValues4<A1, A3, A4, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_25")
public fun <A1, A3, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues4<A1, A3, A4, A8> = TupleValues4<A1, A3, A4, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_26")
public fun <A1, A3, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues4<A1, A3, A4, A9> = TupleValues4<A1, A3, A4, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_27")
public fun <A1, A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues4<A1, A3, A5, A6> = TupleValues4<A1, A3, A5, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_28")
public fun <A1, A3, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues4<A1, A3, A5, A7> = TupleValues4<A1, A3, A5, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_29")
public fun <A1, A3, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues4<A1, A3, A5, A8> = TupleValues4<A1, A3, A5, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_30")
public fun <A1, A3, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues4<A1, A3, A5, A9> = TupleValues4<A1, A3, A5, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_31")
public fun <A1, A3, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues4<A1, A3, A6, A7> = TupleValues4<A1, A3, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_32")
public fun <A1, A3, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues4<A1, A3, A6, A8> = TupleValues4<A1, A3, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_33")
public fun <A1, A3, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues4<A1, A3, A6, A9> = TupleValues4<A1, A3, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_34")
public fun <A1, A3, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues4<A1, A3, A7, A8> = TupleValues4<A1, A3, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_35")
public fun <A1, A3, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues4<A1, A3, A7, A9> = TupleValues4<A1, A3, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_36")
public fun <A1, A3, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues4<A1, A3, A8, A9> = TupleValues4<A1, A3, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_37")
public fun <A1, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues4<A1, A4, A5, A6> = TupleValues4<A1, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_38")
public fun <A1, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues4<A1, A4, A5, A7> = TupleValues4<A1, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_39")
public fun <A1, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues4<A1, A4, A5, A8> = TupleValues4<A1, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_40")
public fun <A1, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues4<A1, A4, A5, A9> = TupleValues4<A1, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_41")
public fun <A1, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues4<A1, A4, A6, A7> = TupleValues4<A1, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_42")
public fun <A1, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues4<A1, A4, A6, A8> = TupleValues4<A1, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_43")
public fun <A1, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues4<A1, A4, A6, A9> = TupleValues4<A1, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_44")
public fun <A1, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues4<A1, A4, A7, A8> = TupleValues4<A1, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_45")
public fun <A1, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues4<A1, A4, A7, A9> = TupleValues4<A1, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_46")
public fun <A1, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues4<A1, A4, A8, A9> = TupleValues4<A1, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_47")
public fun <A1, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>): TupleValues4<A1, A5, A6, A7> = TupleValues4<A1, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_48")
public fun <A1, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>): TupleValues4<A1, A5, A6, A8> = TupleValues4<A1, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_49")
public fun <A1, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>): TupleValues4<A1, A5, A6, A9> = TupleValues4<A1, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_50")
public fun <A1, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>): TupleValues4<A1, A5, A7, A8> = TupleValues4<A1, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_51")
public fun <A1, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>): TupleValues4<A1, A5, A7, A9> = TupleValues4<A1, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_52")
public fun <A1, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>): TupleValues4<A1, A5, A8, A9> = TupleValues4<A1, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_53")
public fun <A1, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>): TupleValues4<A1, A6, A7, A8> = TupleValues4<A1, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_54")
public fun <A1, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>): TupleValues4<A1, A6, A7, A9> = TupleValues4<A1, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_55")
public fun <A1, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>): TupleValues4<A1, A6, A8, A9> = TupleValues4<A1, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_56")
public fun <A1, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>): TupleValues4<A1, A7, A8, A9> = TupleValues4<A1, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_57")
public fun <A2, A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A2, A3, A4, A5> = TupleValues4<A2, A3, A4, A5>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_4_58")
public fun <A2, A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues4<A2, A3, A4, A6> = TupleValues4<A2, A3, A4, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_59")
public fun <A2, A3, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues4<A2, A3, A4, A7> = TupleValues4<A2, A3, A4, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_60")
public fun <A2, A3, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues4<A2, A3, A4, A8> = TupleValues4<A2, A3, A4, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_61")
public fun <A2, A3, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues4<A2, A3, A4, A9> = TupleValues4<A2, A3, A4, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_62")
public fun <A2, A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues4<A2, A3, A5, A6> = TupleValues4<A2, A3, A5, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_63")
public fun <A2, A3, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues4<A2, A3, A5, A7> = TupleValues4<A2, A3, A5, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_64")
public fun <A2, A3, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues4<A2, A3, A5, A8> = TupleValues4<A2, A3, A5, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_65")
public fun <A2, A3, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues4<A2, A3, A5, A9> = TupleValues4<A2, A3, A5, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_66")
public fun <A2, A3, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues4<A2, A3, A6, A7> = TupleValues4<A2, A3, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_67")
public fun <A2, A3, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues4<A2, A3, A6, A8> = TupleValues4<A2, A3, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_68")
public fun <A2, A3, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues4<A2, A3, A6, A9> = TupleValues4<A2, A3, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_69")
public fun <A2, A3, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues4<A2, A3, A7, A8> = TupleValues4<A2, A3, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_70")
public fun <A2, A3, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues4<A2, A3, A7, A9> = TupleValues4<A2, A3, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_71")
public fun <A2, A3, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues4<A2, A3, A8, A9> = TupleValues4<A2, A3, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_72")
public fun <A2, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues4<A2, A4, A5, A6> = TupleValues4<A2, A4, A5, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_73")
public fun <A2, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues4<A2, A4, A5, A7> = TupleValues4<A2, A4, A5, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_74")
public fun <A2, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues4<A2, A4, A5, A8> = TupleValues4<A2, A4, A5, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_75")
public fun <A2, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues4<A2, A4, A5, A9> = TupleValues4<A2, A4, A5, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_76")
public fun <A2, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues4<A2, A4, A6, A7> = TupleValues4<A2, A4, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_77")
public fun <A2, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues4<A2, A4, A6, A8> = TupleValues4<A2, A4, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_78")
public fun <A2, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues4<A2, A4, A6, A9> = TupleValues4<A2, A4, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_79")
public fun <A2, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues4<A2, A4, A7, A8> = TupleValues4<A2, A4, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_80")
public fun <A2, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues4<A2, A4, A7, A9> = TupleValues4<A2, A4, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_81")
public fun <A2, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues4<A2, A4, A8, A9> = TupleValues4<A2, A4, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_82")
public fun <A2, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>): TupleValues4<A2, A5, A6, A7> = TupleValues4<A2, A5, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_83")
public fun <A2, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>): TupleValues4<A2, A5, A6, A8> = TupleValues4<A2, A5, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_84")
public fun <A2, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>): TupleValues4<A2, A5, A6, A9> = TupleValues4<A2, A5, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_85")
public fun <A2, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>): TupleValues4<A2, A5, A7, A8> = TupleValues4<A2, A5, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_86")
public fun <A2, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>): TupleValues4<A2, A5, A7, A9> = TupleValues4<A2, A5, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_87")
public fun <A2, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>): TupleValues4<A2, A5, A8, A9> = TupleValues4<A2, A5, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_88")
public fun <A2, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>): TupleValues4<A2, A6, A7, A8> = TupleValues4<A2, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_89")
public fun <A2, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>): TupleValues4<A2, A6, A7, A9> = TupleValues4<A2, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_90")
public fun <A2, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>): TupleValues4<A2, A6, A8, A9> = TupleValues4<A2, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_91")
public fun <A2, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>): TupleValues4<A2, A7, A8, A9> = TupleValues4<A2, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_92")
public fun <A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues4<A3, A4, A5, A6> = TupleValues4<A3, A4, A5, A6>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_4_93")
public fun <A3, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues4<A3, A4, A5, A7> = TupleValues4<A3, A4, A5, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_94")
public fun <A3, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues4<A3, A4, A5, A8> = TupleValues4<A3, A4, A5, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_95")
public fun <A3, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues4<A3, A4, A5, A9> = TupleValues4<A3, A4, A5, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_96")
public fun <A3, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues4<A3, A4, A6, A7> = TupleValues4<A3, A4, A6, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_97")
public fun <A3, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues4<A3, A4, A6, A8> = TupleValues4<A3, A4, A6, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_98")
public fun <A3, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues4<A3, A4, A6, A9> = TupleValues4<A3, A4, A6, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_99")
public fun <A3, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues4<A3, A4, A7, A8> = TupleValues4<A3, A4, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_100")
public fun <A3, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues4<A3, A4, A7, A9> = TupleValues4<A3, A4, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_101")
public fun <A3, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues4<A3, A4, A8, A9> = TupleValues4<A3, A4, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_102")
public fun <A3, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>): TupleValues4<A3, A5, A6, A7> = TupleValues4<A3, A5, A6, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_103")
public fun <A3, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>): TupleValues4<A3, A5, A6, A8> = TupleValues4<A3, A5, A6, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_104")
public fun <A3, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>): TupleValues4<A3, A5, A6, A9> = TupleValues4<A3, A5, A6, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_105")
public fun <A3, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>): TupleValues4<A3, A5, A7, A8> = TupleValues4<A3, A5, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_106")
public fun <A3, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>): TupleValues4<A3, A5, A7, A9> = TupleValues4<A3, A5, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_107")
public fun <A3, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>): TupleValues4<A3, A5, A8, A9> = TupleValues4<A3, A5, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_108")
public fun <A3, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>): TupleValues4<A3, A6, A7, A8> = TupleValues4<A3, A6, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_109")
public fun <A3, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>): TupleValues4<A3, A6, A7, A9> = TupleValues4<A3, A6, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_110")
public fun <A3, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>): TupleValues4<A3, A6, A8, A9> = TupleValues4<A3, A6, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_111")
public fun <A3, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>): TupleValues4<A3, A7, A8, A9> = TupleValues4<A3, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_112")
public fun <A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>): TupleValues4<A4, A5, A6, A7> = TupleValues4<A4, A5, A6, A7>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_4_113")
public fun <A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>): TupleValues4<A4, A5, A6, A8> = TupleValues4<A4, A5, A6, A8>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_114")
public fun <A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>): TupleValues4<A4, A5, A6, A9> = TupleValues4<A4, A5, A6, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_115")
public fun <A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>): TupleValues4<A4, A5, A7, A8> = TupleValues4<A4, A5, A7, A8>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_116")
public fun <A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>): TupleValues4<A4, A5, A7, A9> = TupleValues4<A4, A5, A7, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_117")
public fun <A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>): TupleValues4<A4, A5, A8, A9> = TupleValues4<A4, A5, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_118")
public fun <A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>): TupleValues4<A4, A6, A7, A8> = TupleValues4<A4, A6, A7, A8>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_119")
public fun <A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>): TupleValues4<A4, A6, A7, A9> = TupleValues4<A4, A6, A7, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_120")
public fun <A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>): TupleValues4<A4, A6, A8, A9> = TupleValues4<A4, A6, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_121")
public fun <A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>): TupleValues4<A4, A7, A8, A9> = TupleValues4<A4, A7, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_122")
public fun <A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>): TupleValues4<A5, A6, A7, A8> = TupleValues4<A5, A6, A7, A8>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_4_123")
public fun <A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>): TupleValues4<A5, A6, A7, A9> = TupleValues4<A5, A6, A7, A9>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_4_124")
public fun <A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>): TupleValues4<A5, A6, A8, A9> = TupleValues4<A5, A6, A8, A9>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_125")
public fun <A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>): TupleValues4<A5, A7, A8, A9> = TupleValues4<A5, A7, A8, A9>(tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_4_126")
public fun <A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>): TupleValues4<A6, A7, A8, A9> = TupleValues4<A6, A7, A8, A9>(tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_1")
public fun <A1, A2, A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A3, A4, A5> = TupleValues5<A1, A2, A3, A4, A5>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_9_5_2")
public fun <A1, A2, A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A3, A4, A6> = TupleValues5<A1, A2, A3, A4, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_5_3")
public fun <A1, A2, A3, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>): TupleValues5<A1, A2, A3, A4, A7> = TupleValues5<A1, A2, A3, A4, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_4")
public fun <A1, A2, A3, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>): TupleValues5<A1, A2, A3, A4, A8> = TupleValues5<A1, A2, A3, A4, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_5")
public fun <A1, A2, A3, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>): TupleValues5<A1, A2, A3, A4, A9> = TupleValues5<A1, A2, A3, A4, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_6")
public fun <A1, A2, A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A3, A5, A6> = TupleValues5<A1, A2, A3, A5, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_5_7")
public fun <A1, A2, A3, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues5<A1, A2, A3, A5, A7> = TupleValues5<A1, A2, A3, A5, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_8")
public fun <A1, A2, A3, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues5<A1, A2, A3, A5, A8> = TupleValues5<A1, A2, A3, A5, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_9")
public fun <A1, A2, A3, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues5<A1, A2, A3, A5, A9> = TupleValues5<A1, A2, A3, A5, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_10")
public fun <A1, A2, A3, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues5<A1, A2, A3, A6, A7> = TupleValues5<A1, A2, A3, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_11")
public fun <A1, A2, A3, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues5<A1, A2, A3, A6, A8> = TupleValues5<A1, A2, A3, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_12")
public fun <A1, A2, A3, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues5<A1, A2, A3, A6, A9> = TupleValues5<A1, A2, A3, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_13")
public fun <A1, A2, A3, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues5<A1, A2, A3, A7, A8> = TupleValues5<A1, A2, A3, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_14")
public fun <A1, A2, A3, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues5<A1, A2, A3, A7, A9> = TupleValues5<A1, A2, A3, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_15")
public fun <A1, A2, A3, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues5<A1, A2, A3, A8, A9> = TupleValues5<A1, A2, A3, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_16")
public fun <A1, A2, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A4, A5, A6> = TupleValues5<A1, A2, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_5_17")
public fun <A1, A2, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues5<A1, A2, A4, A5, A7> = TupleValues5<A1, A2, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_18")
public fun <A1, A2, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues5<A1, A2, A4, A5, A8> = TupleValues5<A1, A2, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_19")
public fun <A1, A2, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues5<A1, A2, A4, A5, A9> = TupleValues5<A1, A2, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_20")
public fun <A1, A2, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues5<A1, A2, A4, A6, A7> = TupleValues5<A1, A2, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_21")
public fun <A1, A2, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues5<A1, A2, A4, A6, A8> = TupleValues5<A1, A2, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_22")
public fun <A1, A2, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues5<A1, A2, A4, A6, A9> = TupleValues5<A1, A2, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_23")
public fun <A1, A2, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues5<A1, A2, A4, A7, A8> = TupleValues5<A1, A2, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_24")
public fun <A1, A2, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues5<A1, A2, A4, A7, A9> = TupleValues5<A1, A2, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_25")
public fun <A1, A2, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues5<A1, A2, A4, A8, A9> = TupleValues5<A1, A2, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_26")
public fun <A1, A2, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>): TupleValues5<A1, A2, A5, A6, A7> = TupleValues5<A1, A2, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_27")
public fun <A1, A2, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>): TupleValues5<A1, A2, A5, A6, A8> = TupleValues5<A1, A2, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_28")
public fun <A1, A2, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>): TupleValues5<A1, A2, A5, A6, A9> = TupleValues5<A1, A2, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_29")
public fun <A1, A2, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>): TupleValues5<A1, A2, A5, A7, A8> = TupleValues5<A1, A2, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_30")
public fun <A1, A2, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>): TupleValues5<A1, A2, A5, A7, A9> = TupleValues5<A1, A2, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_31")
public fun <A1, A2, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>): TupleValues5<A1, A2, A5, A8, A9> = TupleValues5<A1, A2, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_32")
public fun <A1, A2, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>): TupleValues5<A1, A2, A6, A7, A8> = TupleValues5<A1, A2, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_33")
public fun <A1, A2, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>): TupleValues5<A1, A2, A6, A7, A9> = TupleValues5<A1, A2, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_34")
public fun <A1, A2, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>): TupleValues5<A1, A2, A6, A8, A9> = TupleValues5<A1, A2, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_35")
public fun <A1, A2, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>): TupleValues5<A1, A2, A7, A8, A9> = TupleValues5<A1, A2, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_36")
public fun <A1, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues5<A1, A3, A4, A5, A6> = TupleValues5<A1, A3, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_5_37")
public fun <A1, A3, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues5<A1, A3, A4, A5, A7> = TupleValues5<A1, A3, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_38")
public fun <A1, A3, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues5<A1, A3, A4, A5, A8> = TupleValues5<A1, A3, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_39")
public fun <A1, A3, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues5<A1, A3, A4, A5, A9> = TupleValues5<A1, A3, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_40")
public fun <A1, A3, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues5<A1, A3, A4, A6, A7> = TupleValues5<A1, A3, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_41")
public fun <A1, A3, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues5<A1, A3, A4, A6, A8> = TupleValues5<A1, A3, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_42")
public fun <A1, A3, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues5<A1, A3, A4, A6, A9> = TupleValues5<A1, A3, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_43")
public fun <A1, A3, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues5<A1, A3, A4, A7, A8> = TupleValues5<A1, A3, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_44")
public fun <A1, A3, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues5<A1, A3, A4, A7, A9> = TupleValues5<A1, A3, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_45")
public fun <A1, A3, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues5<A1, A3, A4, A8, A9> = TupleValues5<A1, A3, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_46")
public fun <A1, A3, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>): TupleValues5<A1, A3, A5, A6, A7> = TupleValues5<A1, A3, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_47")
public fun <A1, A3, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>): TupleValues5<A1, A3, A5, A6, A8> = TupleValues5<A1, A3, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_48")
public fun <A1, A3, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>): TupleValues5<A1, A3, A5, A6, A9> = TupleValues5<A1, A3, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_49")
public fun <A1, A3, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>): TupleValues5<A1, A3, A5, A7, A8> = TupleValues5<A1, A3, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_50")
public fun <A1, A3, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>): TupleValues5<A1, A3, A5, A7, A9> = TupleValues5<A1, A3, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_51")
public fun <A1, A3, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>): TupleValues5<A1, A3, A5, A8, A9> = TupleValues5<A1, A3, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_52")
public fun <A1, A3, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>): TupleValues5<A1, A3, A6, A7, A8> = TupleValues5<A1, A3, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_53")
public fun <A1, A3, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>): TupleValues5<A1, A3, A6, A7, A9> = TupleValues5<A1, A3, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_54")
public fun <A1, A3, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>): TupleValues5<A1, A3, A6, A8, A9> = TupleValues5<A1, A3, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_55")
public fun <A1, A3, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>): TupleValues5<A1, A3, A7, A8, A9> = TupleValues5<A1, A3, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_56")
public fun <A1, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>): TupleValues5<A1, A4, A5, A6, A7> = TupleValues5<A1, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_57")
public fun <A1, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>): TupleValues5<A1, A4, A5, A6, A8> = TupleValues5<A1, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_58")
public fun <A1, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>): TupleValues5<A1, A4, A5, A6, A9> = TupleValues5<A1, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_59")
public fun <A1, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>): TupleValues5<A1, A4, A5, A7, A8> = TupleValues5<A1, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_60")
public fun <A1, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>): TupleValues5<A1, A4, A5, A7, A9> = TupleValues5<A1, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_61")
public fun <A1, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>): TupleValues5<A1, A4, A5, A8, A9> = TupleValues5<A1, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_62")
public fun <A1, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>): TupleValues5<A1, A4, A6, A7, A8> = TupleValues5<A1, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_63")
public fun <A1, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>): TupleValues5<A1, A4, A6, A7, A9> = TupleValues5<A1, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_64")
public fun <A1, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>): TupleValues5<A1, A4, A6, A8, A9> = TupleValues5<A1, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_65")
public fun <A1, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>): TupleValues5<A1, A4, A7, A8, A9> = TupleValues5<A1, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_66")
public fun <A1, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>): TupleValues5<A1, A5, A6, A7, A8> = TupleValues5<A1, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_67")
public fun <A1, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>): TupleValues5<A1, A5, A6, A7, A9> = TupleValues5<A1, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_68")
public fun <A1, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>): TupleValues5<A1, A5, A6, A8, A9> = TupleValues5<A1, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_69")
public fun <A1, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>): TupleValues5<A1, A5, A7, A8, A9> = TupleValues5<A1, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_70")
public fun <A1, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>): TupleValues5<A1, A6, A7, A8, A9> = TupleValues5<A1, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_71")
public fun <A2, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues5<A2, A3, A4, A5, A6> = TupleValues5<A2, A3, A4, A5, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_5_72")
public fun <A2, A3, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues5<A2, A3, A4, A5, A7> = TupleValues5<A2, A3, A4, A5, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_73")
public fun <A2, A3, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues5<A2, A3, A4, A5, A8> = TupleValues5<A2, A3, A4, A5, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_74")
public fun <A2, A3, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues5<A2, A3, A4, A5, A9> = TupleValues5<A2, A3, A4, A5, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_75")
public fun <A2, A3, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues5<A2, A3, A4, A6, A7> = TupleValues5<A2, A3, A4, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_76")
public fun <A2, A3, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues5<A2, A3, A4, A6, A8> = TupleValues5<A2, A3, A4, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_77")
public fun <A2, A3, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues5<A2, A3, A4, A6, A9> = TupleValues5<A2, A3, A4, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_78")
public fun <A2, A3, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues5<A2, A3, A4, A7, A8> = TupleValues5<A2, A3, A4, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_79")
public fun <A2, A3, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues5<A2, A3, A4, A7, A9> = TupleValues5<A2, A3, A4, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_80")
public fun <A2, A3, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues5<A2, A3, A4, A8, A9> = TupleValues5<A2, A3, A4, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_81")
public fun <A2, A3, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>): TupleValues5<A2, A3, A5, A6, A7> = TupleValues5<A2, A3, A5, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_82")
public fun <A2, A3, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>): TupleValues5<A2, A3, A5, A6, A8> = TupleValues5<A2, A3, A5, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_83")
public fun <A2, A3, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>): TupleValues5<A2, A3, A5, A6, A9> = TupleValues5<A2, A3, A5, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_84")
public fun <A2, A3, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>): TupleValues5<A2, A3, A5, A7, A8> = TupleValues5<A2, A3, A5, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_85")
public fun <A2, A3, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>): TupleValues5<A2, A3, A5, A7, A9> = TupleValues5<A2, A3, A5, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_86")
public fun <A2, A3, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>): TupleValues5<A2, A3, A5, A8, A9> = TupleValues5<A2, A3, A5, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_87")
public fun <A2, A3, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>): TupleValues5<A2, A3, A6, A7, A8> = TupleValues5<A2, A3, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_88")
public fun <A2, A3, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>): TupleValues5<A2, A3, A6, A7, A9> = TupleValues5<A2, A3, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_89")
public fun <A2, A3, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>): TupleValues5<A2, A3, A6, A8, A9> = TupleValues5<A2, A3, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_90")
public fun <A2, A3, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>): TupleValues5<A2, A3, A7, A8, A9> = TupleValues5<A2, A3, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_91")
public fun <A2, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>): TupleValues5<A2, A4, A5, A6, A7> = TupleValues5<A2, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_92")
public fun <A2, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>): TupleValues5<A2, A4, A5, A6, A8> = TupleValues5<A2, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_93")
public fun <A2, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>): TupleValues5<A2, A4, A5, A6, A9> = TupleValues5<A2, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_94")
public fun <A2, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>): TupleValues5<A2, A4, A5, A7, A8> = TupleValues5<A2, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_95")
public fun <A2, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>): TupleValues5<A2, A4, A5, A7, A9> = TupleValues5<A2, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_96")
public fun <A2, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>): TupleValues5<A2, A4, A5, A8, A9> = TupleValues5<A2, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_97")
public fun <A2, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>): TupleValues5<A2, A4, A6, A7, A8> = TupleValues5<A2, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_98")
public fun <A2, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>): TupleValues5<A2, A4, A6, A7, A9> = TupleValues5<A2, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_99")
public fun <A2, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>): TupleValues5<A2, A4, A6, A8, A9> = TupleValues5<A2, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_100")
public fun <A2, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>): TupleValues5<A2, A4, A7, A8, A9> = TupleValues5<A2, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_101")
public fun <A2, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>): TupleValues5<A2, A5, A6, A7, A8> = TupleValues5<A2, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_102")
public fun <A2, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>): TupleValues5<A2, A5, A6, A7, A9> = TupleValues5<A2, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_103")
public fun <A2, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>): TupleValues5<A2, A5, A6, A8, A9> = TupleValues5<A2, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_104")
public fun <A2, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>): TupleValues5<A2, A5, A7, A8, A9> = TupleValues5<A2, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_105")
public fun <A2, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>): TupleValues5<A2, A6, A7, A8, A9> = TupleValues5<A2, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_106")
public fun <A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>): TupleValues5<A3, A4, A5, A6, A7> = TupleValues5<A3, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_5_107")
public fun <A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>): TupleValues5<A3, A4, A5, A6, A8> = TupleValues5<A3, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_108")
public fun <A3, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>): TupleValues5<A3, A4, A5, A6, A9> = TupleValues5<A3, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_109")
public fun <A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>): TupleValues5<A3, A4, A5, A7, A8> = TupleValues5<A3, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_110")
public fun <A3, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>): TupleValues5<A3, A4, A5, A7, A9> = TupleValues5<A3, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_111")
public fun <A3, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>): TupleValues5<A3, A4, A5, A8, A9> = TupleValues5<A3, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_112")
public fun <A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>): TupleValues5<A3, A4, A6, A7, A8> = TupleValues5<A3, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_113")
public fun <A3, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>): TupleValues5<A3, A4, A6, A7, A9> = TupleValues5<A3, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_114")
public fun <A3, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>): TupleValues5<A3, A4, A6, A8, A9> = TupleValues5<A3, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_115")
public fun <A3, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>): TupleValues5<A3, A4, A7, A8, A9> = TupleValues5<A3, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_116")
public fun <A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>): TupleValues5<A3, A5, A6, A7, A8> = TupleValues5<A3, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_117")
public fun <A3, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>): TupleValues5<A3, A5, A6, A7, A9> = TupleValues5<A3, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_118")
public fun <A3, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>): TupleValues5<A3, A5, A6, A8, A9> = TupleValues5<A3, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_119")
public fun <A3, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>): TupleValues5<A3, A5, A7, A8, A9> = TupleValues5<A3, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_120")
public fun <A3, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>): TupleValues5<A3, A6, A7, A8, A9> = TupleValues5<A3, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_121")
public fun <A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>): TupleValues5<A4, A5, A6, A7, A8> = TupleValues5<A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_5_122")
public fun <A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>): TupleValues5<A4, A5, A6, A7, A9> = TupleValues5<A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_5_123")
public fun <A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>): TupleValues5<A4, A5, A6, A8, A9> = TupleValues5<A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_124")
public fun <A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>): TupleValues5<A4, A5, A7, A8, A9> = TupleValues5<A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_125")
public fun <A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>): TupleValues5<A4, A6, A7, A8, A9> = TupleValues5<A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_5_126")
public fun <A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>): TupleValues5<A5, A6, A7, A8, A9> = TupleValues5<A5, A6, A7, A8, A9>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_1")
public fun <A1, A2, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>): TupleValues6<A1, A2, A3, A4, A5, A6> = TupleValues6<A1, A2, A3, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_9_6_2")
public fun <A1, A2, A3, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>): TupleValues6<A1, A2, A3, A4, A5, A7> = TupleValues6<A1, A2, A3, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_6_3")
public fun <A1, A2, A3, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>): TupleValues6<A1, A2, A3, A4, A5, A8> = TupleValues6<A1, A2, A3, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_4")
public fun <A1, A2, A3, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>): TupleValues6<A1, A2, A3, A4, A5, A9> = TupleValues6<A1, A2, A3, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_5")
public fun <A1, A2, A3, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>): TupleValues6<A1, A2, A3, A4, A6, A7> = TupleValues6<A1, A2, A3, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_6_6")
public fun <A1, A2, A3, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>): TupleValues6<A1, A2, A3, A4, A6, A8> = TupleValues6<A1, A2, A3, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_7")
public fun <A1, A2, A3, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>): TupleValues6<A1, A2, A3, A4, A6, A9> = TupleValues6<A1, A2, A3, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_8")
public fun <A1, A2, A3, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>): TupleValues6<A1, A2, A3, A4, A7, A8> = TupleValues6<A1, A2, A3, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_9")
public fun <A1, A2, A3, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>): TupleValues6<A1, A2, A3, A4, A7, A9> = TupleValues6<A1, A2, A3, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_10")
public fun <A1, A2, A3, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>): TupleValues6<A1, A2, A3, A4, A8, A9> = TupleValues6<A1, A2, A3, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_11")
public fun <A1, A2, A3, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>): TupleValues6<A1, A2, A3, A5, A6, A7> = TupleValues6<A1, A2, A3, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_6_12")
public fun <A1, A2, A3, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>): TupleValues6<A1, A2, A3, A5, A6, A8> = TupleValues6<A1, A2, A3, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_13")
public fun <A1, A2, A3, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>): TupleValues6<A1, A2, A3, A5, A6, A9> = TupleValues6<A1, A2, A3, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_14")
public fun <A1, A2, A3, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>): TupleValues6<A1, A2, A3, A5, A7, A8> = TupleValues6<A1, A2, A3, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_15")
public fun <A1, A2, A3, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>): TupleValues6<A1, A2, A3, A5, A7, A9> = TupleValues6<A1, A2, A3, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_16")
public fun <A1, A2, A3, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>): TupleValues6<A1, A2, A3, A5, A8, A9> = TupleValues6<A1, A2, A3, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_17")
public fun <A1, A2, A3, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>): TupleValues6<A1, A2, A3, A6, A7, A8> = TupleValues6<A1, A2, A3, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_18")
public fun <A1, A2, A3, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>): TupleValues6<A1, A2, A3, A6, A7, A9> = TupleValues6<A1, A2, A3, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_19")
public fun <A1, A2, A3, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>): TupleValues6<A1, A2, A3, A6, A8, A9> = TupleValues6<A1, A2, A3, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_20")
public fun <A1, A2, A3, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>): TupleValues6<A1, A2, A3, A7, A8, A9> = TupleValues6<A1, A2, A3, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_21")
public fun <A1, A2, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>): TupleValues6<A1, A2, A4, A5, A6, A7> = TupleValues6<A1, A2, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_6_22")
public fun <A1, A2, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>): TupleValues6<A1, A2, A4, A5, A6, A8> = TupleValues6<A1, A2, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_23")
public fun <A1, A2, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>): TupleValues6<A1, A2, A4, A5, A6, A9> = TupleValues6<A1, A2, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_24")
public fun <A1, A2, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>): TupleValues6<A1, A2, A4, A5, A7, A8> = TupleValues6<A1, A2, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_25")
public fun <A1, A2, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>): TupleValues6<A1, A2, A4, A5, A7, A9> = TupleValues6<A1, A2, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_26")
public fun <A1, A2, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>): TupleValues6<A1, A2, A4, A5, A8, A9> = TupleValues6<A1, A2, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_27")
public fun <A1, A2, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>): TupleValues6<A1, A2, A4, A6, A7, A8> = TupleValues6<A1, A2, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_28")
public fun <A1, A2, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>): TupleValues6<A1, A2, A4, A6, A7, A9> = TupleValues6<A1, A2, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_29")
public fun <A1, A2, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>): TupleValues6<A1, A2, A4, A6, A8, A9> = TupleValues6<A1, A2, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_30")
public fun <A1, A2, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>): TupleValues6<A1, A2, A4, A7, A8, A9> = TupleValues6<A1, A2, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_31")
public fun <A1, A2, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>): TupleValues6<A1, A2, A5, A6, A7, A8> = TupleValues6<A1, A2, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_32")
public fun <A1, A2, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>): TupleValues6<A1, A2, A5, A6, A7, A9> = TupleValues6<A1, A2, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_33")
public fun <A1, A2, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>): TupleValues6<A1, A2, A5, A6, A8, A9> = TupleValues6<A1, A2, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_34")
public fun <A1, A2, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>): TupleValues6<A1, A2, A5, A7, A8, A9> = TupleValues6<A1, A2, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_35")
public fun <A1, A2, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>): TupleValues6<A1, A2, A6, A7, A8, A9> = TupleValues6<A1, A2, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_36")
public fun <A1, A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>): TupleValues6<A1, A3, A4, A5, A6, A7> = TupleValues6<A1, A3, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_6_37")
public fun <A1, A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>): TupleValues6<A1, A3, A4, A5, A6, A8> = TupleValues6<A1, A3, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_38")
public fun <A1, A3, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>): TupleValues6<A1, A3, A4, A5, A6, A9> = TupleValues6<A1, A3, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_39")
public fun <A1, A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>): TupleValues6<A1, A3, A4, A5, A7, A8> = TupleValues6<A1, A3, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_40")
public fun <A1, A3, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>): TupleValues6<A1, A3, A4, A5, A7, A9> = TupleValues6<A1, A3, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_41")
public fun <A1, A3, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>): TupleValues6<A1, A3, A4, A5, A8, A9> = TupleValues6<A1, A3, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_42")
public fun <A1, A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>): TupleValues6<A1, A3, A4, A6, A7, A8> = TupleValues6<A1, A3, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_43")
public fun <A1, A3, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>): TupleValues6<A1, A3, A4, A6, A7, A9> = TupleValues6<A1, A3, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_44")
public fun <A1, A3, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>): TupleValues6<A1, A3, A4, A6, A8, A9> = TupleValues6<A1, A3, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_45")
public fun <A1, A3, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>): TupleValues6<A1, A3, A4, A7, A8, A9> = TupleValues6<A1, A3, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_46")
public fun <A1, A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>): TupleValues6<A1, A3, A5, A6, A7, A8> = TupleValues6<A1, A3, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_47")
public fun <A1, A3, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>): TupleValues6<A1, A3, A5, A6, A7, A9> = TupleValues6<A1, A3, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_48")
public fun <A1, A3, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>): TupleValues6<A1, A3, A5, A6, A8, A9> = TupleValues6<A1, A3, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_49")
public fun <A1, A3, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>): TupleValues6<A1, A3, A5, A7, A8, A9> = TupleValues6<A1, A3, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_50")
public fun <A1, A3, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>): TupleValues6<A1, A3, A6, A7, A8, A9> = TupleValues6<A1, A3, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_51")
public fun <A1, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>): TupleValues6<A1, A4, A5, A6, A7, A8> = TupleValues6<A1, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_52")
public fun <A1, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>): TupleValues6<A1, A4, A5, A6, A7, A9> = TupleValues6<A1, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_53")
public fun <A1, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>): TupleValues6<A1, A4, A5, A6, A8, A9> = TupleValues6<A1, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_54")
public fun <A1, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>): TupleValues6<A1, A4, A5, A7, A8, A9> = TupleValues6<A1, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_55")
public fun <A1, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>): TupleValues6<A1, A4, A6, A7, A8, A9> = TupleValues6<A1, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_56")
public fun <A1, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>): TupleValues6<A1, A5, A6, A7, A8, A9> = TupleValues6<A1, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_57")
public fun <A2, A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>): TupleValues6<A2, A3, A4, A5, A6, A7> = TupleValues6<A2, A3, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_6_58")
public fun <A2, A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>): TupleValues6<A2, A3, A4, A5, A6, A8> = TupleValues6<A2, A3, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_59")
public fun <A2, A3, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>): TupleValues6<A2, A3, A4, A5, A6, A9> = TupleValues6<A2, A3, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_60")
public fun <A2, A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>): TupleValues6<A2, A3, A4, A5, A7, A8> = TupleValues6<A2, A3, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_61")
public fun <A2, A3, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>): TupleValues6<A2, A3, A4, A5, A7, A9> = TupleValues6<A2, A3, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_62")
public fun <A2, A3, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>): TupleValues6<A2, A3, A4, A5, A8, A9> = TupleValues6<A2, A3, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_63")
public fun <A2, A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>): TupleValues6<A2, A3, A4, A6, A7, A8> = TupleValues6<A2, A3, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_64")
public fun <A2, A3, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>): TupleValues6<A2, A3, A4, A6, A7, A9> = TupleValues6<A2, A3, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_65")
public fun <A2, A3, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>): TupleValues6<A2, A3, A4, A6, A8, A9> = TupleValues6<A2, A3, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_66")
public fun <A2, A3, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>): TupleValues6<A2, A3, A4, A7, A8, A9> = TupleValues6<A2, A3, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_67")
public fun <A2, A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>): TupleValues6<A2, A3, A5, A6, A7, A8> = TupleValues6<A2, A3, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_68")
public fun <A2, A3, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>): TupleValues6<A2, A3, A5, A6, A7, A9> = TupleValues6<A2, A3, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_69")
public fun <A2, A3, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>): TupleValues6<A2, A3, A5, A6, A8, A9> = TupleValues6<A2, A3, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_70")
public fun <A2, A3, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>): TupleValues6<A2, A3, A5, A7, A8, A9> = TupleValues6<A2, A3, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_71")
public fun <A2, A3, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>): TupleValues6<A2, A3, A6, A7, A8, A9> = TupleValues6<A2, A3, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_72")
public fun <A2, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>): TupleValues6<A2, A4, A5, A6, A7, A8> = TupleValues6<A2, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_73")
public fun <A2, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>): TupleValues6<A2, A4, A5, A6, A7, A9> = TupleValues6<A2, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_74")
public fun <A2, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>): TupleValues6<A2, A4, A5, A6, A8, A9> = TupleValues6<A2, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_75")
public fun <A2, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>): TupleValues6<A2, A4, A5, A7, A8, A9> = TupleValues6<A2, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_76")
public fun <A2, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>): TupleValues6<A2, A4, A6, A7, A8, A9> = TupleValues6<A2, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_77")
public fun <A2, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>): TupleValues6<A2, A5, A6, A7, A8, A9> = TupleValues6<A2, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_78")
public fun <A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>): TupleValues6<A3, A4, A5, A6, A7, A8> = TupleValues6<A3, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_6_79")
public fun <A3, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>): TupleValues6<A3, A4, A5, A6, A7, A9> = TupleValues6<A3, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_6_80")
public fun <A3, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>): TupleValues6<A3, A4, A5, A6, A8, A9> = TupleValues6<A3, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_81")
public fun <A3, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>): TupleValues6<A3, A4, A5, A7, A8, A9> = TupleValues6<A3, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_82")
public fun <A3, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>): TupleValues6<A3, A4, A6, A7, A8, A9> = TupleValues6<A3, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_83")
public fun <A3, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>): TupleValues6<A3, A5, A6, A7, A8, A9> = TupleValues6<A3, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_6_84")
public fun <A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>): TupleValues6<A4, A5, A6, A7, A8, A9> = TupleValues6<A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_1")
public fun <A1, A2, A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>): TupleValues7<A1, A2, A3, A4, A5, A6, A7> = TupleValues7<A1, A2, A3, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_9_7_2")
public fun <A1, A2, A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>): TupleValues7<A1, A2, A3, A4, A5, A6, A8> = TupleValues7<A1, A2, A3, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_7_3")
public fun <A1, A2, A3, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>): TupleValues7<A1, A2, A3, A4, A5, A6, A9> = TupleValues7<A1, A2, A3, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_9_7_4")
public fun <A1, A2, A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>): TupleValues7<A1, A2, A3, A4, A5, A7, A8> = TupleValues7<A1, A2, A3, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_7_5")
public fun <A1, A2, A3, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>): TupleValues7<A1, A2, A3, A4, A5, A7, A9> = TupleValues7<A1, A2, A3, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_7_6")
public fun <A1, A2, A3, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>): TupleValues7<A1, A2, A3, A4, A5, A8, A9> = TupleValues7<A1, A2, A3, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_7")
public fun <A1, A2, A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>): TupleValues7<A1, A2, A3, A4, A6, A7, A8> = TupleValues7<A1, A2, A3, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_7_8")
public fun <A1, A2, A3, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>): TupleValues7<A1, A2, A3, A4, A6, A7, A9> = TupleValues7<A1, A2, A3, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_7_9")
public fun <A1, A2, A3, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>): TupleValues7<A1, A2, A3, A4, A6, A8, A9> = TupleValues7<A1, A2, A3, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_10")
public fun <A1, A2, A3, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>): TupleValues7<A1, A2, A3, A4, A7, A8, A9> = TupleValues7<A1, A2, A3, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_11")
public fun <A1, A2, A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>): TupleValues7<A1, A2, A3, A5, A6, A7, A8> = TupleValues7<A1, A2, A3, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_7_12")
public fun <A1, A2, A3, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>): TupleValues7<A1, A2, A3, A5, A6, A7, A9> = TupleValues7<A1, A2, A3, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_7_13")
public fun <A1, A2, A3, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>): TupleValues7<A1, A2, A3, A5, A6, A8, A9> = TupleValues7<A1, A2, A3, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_14")
public fun <A1, A2, A3, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>): TupleValues7<A1, A2, A3, A5, A7, A8, A9> = TupleValues7<A1, A2, A3, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_15")
public fun <A1, A2, A3, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>): TupleValues7<A1, A2, A3, A6, A7, A8, A9> = TupleValues7<A1, A2, A3, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_16")
public fun <A1, A2, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>): TupleValues7<A1, A2, A4, A5, A6, A7, A8> = TupleValues7<A1, A2, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_7_17")
public fun <A1, A2, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>): TupleValues7<A1, A2, A4, A5, A6, A7, A9> = TupleValues7<A1, A2, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_7_18")
public fun <A1, A2, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>): TupleValues7<A1, A2, A4, A5, A6, A8, A9> = TupleValues7<A1, A2, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_19")
public fun <A1, A2, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>): TupleValues7<A1, A2, A4, A5, A7, A8, A9> = TupleValues7<A1, A2, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_20")
public fun <A1, A2, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>): TupleValues7<A1, A2, A4, A6, A7, A8, A9> = TupleValues7<A1, A2, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_21")
public fun <A1, A2, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>): TupleValues7<A1, A2, A5, A6, A7, A8, A9> = TupleValues7<A1, A2, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_22")
public fun <A1, A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>): TupleValues7<A1, A3, A4, A5, A6, A7, A8> = TupleValues7<A1, A3, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_7_23")
public fun <A1, A3, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>): TupleValues7<A1, A3, A4, A5, A6, A7, A9> = TupleValues7<A1, A3, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_7_24")
public fun <A1, A3, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>): TupleValues7<A1, A3, A4, A5, A6, A8, A9> = TupleValues7<A1, A3, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_25")
public fun <A1, A3, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>): TupleValues7<A1, A3, A4, A5, A7, A8, A9> = TupleValues7<A1, A3, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_26")
public fun <A1, A3, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>): TupleValues7<A1, A3, A4, A6, A7, A8, A9> = TupleValues7<A1, A3, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_27")
public fun <A1, A3, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>): TupleValues7<A1, A3, A5, A6, A7, A8, A9> = TupleValues7<A1, A3, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_28")
public fun <A1, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>): TupleValues7<A1, A4, A5, A6, A7, A8, A9> = TupleValues7<A1, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_29")
public fun <A2, A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>): TupleValues7<A2, A3, A4, A5, A6, A7, A8> = TupleValues7<A2, A3, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_7_30")
public fun <A2, A3, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>): TupleValues7<A2, A3, A4, A5, A6, A7, A9> = TupleValues7<A2, A3, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_7_31")
public fun <A2, A3, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>): TupleValues7<A2, A3, A4, A5, A6, A8, A9> = TupleValues7<A2, A3, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_32")
public fun <A2, A3, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>): TupleValues7<A2, A3, A4, A5, A7, A8, A9> = TupleValues7<A2, A3, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_33")
public fun <A2, A3, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>): TupleValues7<A2, A3, A4, A6, A7, A8, A9> = TupleValues7<A2, A3, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_34")
public fun <A2, A3, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>): TupleValues7<A2, A3, A5, A6, A7, A8, A9> = TupleValues7<A2, A3, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_35")
public fun <A2, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>): TupleValues7<A2, A4, A5, A6, A7, A8, A9> = TupleValues7<A2, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_7_36")
public fun <A3, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, A9>): TupleValues7<A3, A4, A5, A6, A7, A8, A9> = TupleValues7<A3, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_8_1")
public fun <A1, A2, A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>): TupleValues8<A1, A2, A3, A4, A5, A6, A7, A8> = TupleValues8<A1, A2, A3, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_9_8_2")
public fun <A1, A2, A3, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>): TupleValues8<A1, A2, A3, A4, A5, A6, A7, A9> = TupleValues8<A1, A2, A3, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_9_8_3")
public fun <A1, A2, A3, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>): TupleValues8<A1, A2, A3, A4, A5, A6, A8, A9> = TupleValues8<A1, A2, A3, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_8_4")
public fun <A1, A2, A3, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>): TupleValues8<A1, A2, A3, A4, A5, A7, A8, A9> = TupleValues8<A1, A2, A3, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_8_5")
public fun <A1, A2, A3, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>): TupleValues8<A1, A2, A3, A4, A6, A7, A8, A9> = TupleValues8<A1, A2, A3, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_8_6")
public fun <A1, A2, A3, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>): TupleValues8<A1, A2, A3, A5, A6, A7, A8, A9> = TupleValues8<A1, A2, A3, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_8_7")
public fun <A1, A2, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>): TupleValues8<A1, A2, A4, A5, A6, A7, A8, A9> = TupleValues8<A1, A2, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_8_8")
public fun <A1, A3, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, A9>): TupleValues8<A1, A3, A4, A5, A6, A7, A8, A9> = TupleValues8<A1, A3, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_8_9")
public fun <A2, A3, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, A8>, A9>): TupleValues8<A2, A3, A4, A5, A6, A7, A8, A9> = TupleValues8<A2, A3, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_9_9_1")
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, A8>, A9>): TupleValues9<A1, A2, A3, A4, A5, A6, A7, A8, A9> = TupleValues9<A1, A2, A3, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)
