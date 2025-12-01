package io.github.bbasinsk.tuple

import kotlin.Pair
import kotlin.Unit
import kotlin.jvm.JvmName

public data class TupleValues10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>(
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
  ) -> B): B = f(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)
}

@JvmName("tupleValues_10_1_1")
public fun <A1> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A1> = TupleValues1<A1>(tuples.first.first.first.first.first.first.first.first.first)

@JvmName("tupleValues_10_1_2")
public fun <A2> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A2> = TupleValues1<A2>(tuples.first.first.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_1_3")
public fun <A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A3> = TupleValues1<A3>(tuples.first.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_1_4")
public fun <A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A4> = TupleValues1<A4>(tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_1_5")
public fun <A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A5> = TupleValues1<A5>(tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_1_6")
public fun <A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A6> = TupleValues1<A6>(tuples.first.first.first.first.second)

@JvmName("tupleValues_10_1_7")
public fun <A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues1<A7> = TupleValues1<A7>(tuples.first.first.first.second)

@JvmName("tupleValues_10_1_8")
public fun <A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues1<A8> = TupleValues1<A8>(tuples.first.first.second)

@JvmName("tupleValues_10_1_9")
public fun <A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues1<A9> = TupleValues1<A9>(tuples.first.second)

@JvmName("tupleValues_10_1_10")
public fun <A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues1<A10> = TupleValues1<A10>(tuples.second)

@JvmName("tupleValues_10_2_1")
public fun <A1, A2> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A2> = TupleValues2<A1, A2>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_2")
public fun <A1, A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A3> = TupleValues2<A1, A3>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_3")
public fun <A1, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A4> = TupleValues2<A1, A4>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_4")
public fun <A1, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A5> = TupleValues2<A1, A5>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_5")
public fun <A1, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A6> = TupleValues2<A1, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_2_6")
public fun <A1, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues2<A1, A7> = TupleValues2<A1, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.second)

@JvmName("tupleValues_10_2_7")
public fun <A1, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues2<A1, A8> = TupleValues2<A1, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.second)

@JvmName("tupleValues_10_2_8")
public fun <A1, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues2<A1, A9> = TupleValues2<A1, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.second)

@JvmName("tupleValues_10_2_9")
public fun <A1, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues2<A1, A10> = TupleValues2<A1, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.second)

@JvmName("tupleValues_10_2_10")
public fun <A2, A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A2, A3> = TupleValues2<A2, A3>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_11")
public fun <A2, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A2, A4> = TupleValues2<A2, A4>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_12")
public fun <A2, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A2, A5> = TupleValues2<A2, A5>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_13")
public fun <A2, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A2, A6> = TupleValues2<A2, A6>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_2_14")
public fun <A2, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues2<A2, A7> = TupleValues2<A2, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_2_15")
public fun <A2, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues2<A2, A8> = TupleValues2<A2, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_2_16")
public fun <A2, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues2<A2, A9> = TupleValues2<A2, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_2_17")
public fun <A2, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues2<A2, A10> = TupleValues2<A2, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_2_18")
public fun <A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A3, A4> = TupleValues2<A3, A4>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_19")
public fun <A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A3, A5> = TupleValues2<A3, A5>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_20")
public fun <A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A3, A6> = TupleValues2<A3, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_2_21")
public fun <A3, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues2<A3, A7> = TupleValues2<A3, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_2_22")
public fun <A3, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues2<A3, A8> = TupleValues2<A3, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_2_23")
public fun <A3, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues2<A3, A9> = TupleValues2<A3, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_2_24")
public fun <A3, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues2<A3, A10> = TupleValues2<A3, A10>(tuples.first.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_2_25")
public fun <A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A4, A5> = TupleValues2<A4, A5>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_2_26")
public fun <A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A4, A6> = TupleValues2<A4, A6>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_2_27")
public fun <A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues2<A4, A7> = TupleValues2<A4, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_2_28")
public fun <A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues2<A4, A8> = TupleValues2<A4, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_2_29")
public fun <A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues2<A4, A9> = TupleValues2<A4, A9>(tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_2_30")
public fun <A4, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues2<A4, A10> = TupleValues2<A4, A10>(tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_2_31")
public fun <A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A5, A6> = TupleValues2<A5, A6>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_2_32")
public fun <A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues2<A5, A7> = TupleValues2<A5, A7>(tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_2_33")
public fun <A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues2<A5, A8> = TupleValues2<A5, A8>(tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_2_34")
public fun <A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues2<A5, A9> = TupleValues2<A5, A9>(tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_2_35")
public fun <A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues2<A5, A10> = TupleValues2<A5, A10>(tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_2_36")
public fun <A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues2<A6, A7> = TupleValues2<A6, A7>(tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_2_37")
public fun <A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues2<A6, A8> = TupleValues2<A6, A8>(tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_2_38")
public fun <A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues2<A6, A9> = TupleValues2<A6, A9>(tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_2_39")
public fun <A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues2<A6, A10> = TupleValues2<A6, A10>(tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_2_40")
public fun <A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues2<A7, A8> = TupleValues2<A7, A8>(tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_2_41")
public fun <A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues2<A7, A9> = TupleValues2<A7, A9>(tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_2_42")
public fun <A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues2<A7, A10> = TupleValues2<A7, A10>(tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_2_43")
public fun <A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues2<A8, A9> = TupleValues2<A8, A9>(tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_2_44")
public fun <A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues2<A8, A10> = TupleValues2<A8, A10>(tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_2_45")
public fun <A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues2<A9, A10> = TupleValues2<A9, A10>(tuples.first.second, tuples.second)

@JvmName("tupleValues_10_3_1")
public fun <A1, A2, A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A3> = TupleValues3<A1, A2, A3>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_2")
public fun <A1, A2, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A4> = TupleValues3<A1, A2, A4>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_3")
public fun <A1, A2, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A5> = TupleValues3<A1, A2, A5>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_4")
public fun <A1, A2, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A6> = TupleValues3<A1, A2, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_5")
public fun <A1, A2, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A7> = TupleValues3<A1, A2, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_6")
public fun <A1, A2, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A1, A2, A8> = TupleValues3<A1, A2, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_7")
public fun <A1, A2, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A1, A2, A9> = TupleValues3<A1, A2, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_8")
public fun <A1, A2, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A1, A2, A10> = TupleValues3<A1, A2, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_9")
public fun <A1, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A3, A4> = TupleValues3<A1, A3, A4>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_10")
public fun <A1, A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A3, A5> = TupleValues3<A1, A3, A5>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_11")
public fun <A1, A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A3, A6> = TupleValues3<A1, A3, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_12")
public fun <A1, A3, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A1, A3, A7> = TupleValues3<A1, A3, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_13")
public fun <A1, A3, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A1, A3, A8> = TupleValues3<A1, A3, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_14")
public fun <A1, A3, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A1, A3, A9> = TupleValues3<A1, A3, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_15")
public fun <A1, A3, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A1, A3, A10> = TupleValues3<A1, A3, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_16")
public fun <A1, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A4, A5> = TupleValues3<A1, A4, A5>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_17")
public fun <A1, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A4, A6> = TupleValues3<A1, A4, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_18")
public fun <A1, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A1, A4, A7> = TupleValues3<A1, A4, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_19")
public fun <A1, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A1, A4, A8> = TupleValues3<A1, A4, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_20")
public fun <A1, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A1, A4, A9> = TupleValues3<A1, A4, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_21")
public fun <A1, A4, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A1, A4, A10> = TupleValues3<A1, A4, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_22")
public fun <A1, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A1, A5, A6> = TupleValues3<A1, A5, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_23")
public fun <A1, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A1, A5, A7> = TupleValues3<A1, A5, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_24")
public fun <A1, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A1, A5, A8> = TupleValues3<A1, A5, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_25")
public fun <A1, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A1, A5, A9> = TupleValues3<A1, A5, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_26")
public fun <A1, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A1, A5, A10> = TupleValues3<A1, A5, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_27")
public fun <A1, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues3<A1, A6, A7> = TupleValues3<A1, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_28")
public fun <A1, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues3<A1, A6, A8> = TupleValues3<A1, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_29")
public fun <A1, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues3<A1, A6, A9> = TupleValues3<A1, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_30")
public fun <A1, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues3<A1, A6, A10> = TupleValues3<A1, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_31")
public fun <A1, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues3<A1, A7, A8> = TupleValues3<A1, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_32")
public fun <A1, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues3<A1, A7, A9> = TupleValues3<A1, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_33")
public fun <A1, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues3<A1, A7, A10> = TupleValues3<A1, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_34")
public fun <A1, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues3<A1, A8, A9> = TupleValues3<A1, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_35")
public fun <A1, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues3<A1, A8, A10> = TupleValues3<A1, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_36")
public fun <A1, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues3<A1, A9, A10> = TupleValues3<A1, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_3_37")
public fun <A2, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A2, A3, A4> = TupleValues3<A2, A3, A4>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_38")
public fun <A2, A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A2, A3, A5> = TupleValues3<A2, A3, A5>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_39")
public fun <A2, A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A2, A3, A6> = TupleValues3<A2, A3, A6>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_40")
public fun <A2, A3, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A2, A3, A7> = TupleValues3<A2, A3, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_41")
public fun <A2, A3, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A2, A3, A8> = TupleValues3<A2, A3, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_42")
public fun <A2, A3, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A2, A3, A9> = TupleValues3<A2, A3, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_43")
public fun <A2, A3, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A2, A3, A10> = TupleValues3<A2, A3, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_44")
public fun <A2, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A2, A4, A5> = TupleValues3<A2, A4, A5>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_45")
public fun <A2, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A2, A4, A6> = TupleValues3<A2, A4, A6>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_46")
public fun <A2, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A2, A4, A7> = TupleValues3<A2, A4, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_47")
public fun <A2, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A2, A4, A8> = TupleValues3<A2, A4, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_48")
public fun <A2, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A2, A4, A9> = TupleValues3<A2, A4, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_49")
public fun <A2, A4, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A2, A4, A10> = TupleValues3<A2, A4, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_50")
public fun <A2, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A2, A5, A6> = TupleValues3<A2, A5, A6>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_51")
public fun <A2, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A2, A5, A7> = TupleValues3<A2, A5, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_52")
public fun <A2, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A2, A5, A8> = TupleValues3<A2, A5, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_53")
public fun <A2, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A2, A5, A9> = TupleValues3<A2, A5, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_54")
public fun <A2, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A2, A5, A10> = TupleValues3<A2, A5, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_55")
public fun <A2, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues3<A2, A6, A7> = TupleValues3<A2, A6, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_56")
public fun <A2, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues3<A2, A6, A8> = TupleValues3<A2, A6, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_57")
public fun <A2, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues3<A2, A6, A9> = TupleValues3<A2, A6, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_58")
public fun <A2, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues3<A2, A6, A10> = TupleValues3<A2, A6, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_59")
public fun <A2, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues3<A2, A7, A8> = TupleValues3<A2, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_60")
public fun <A2, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues3<A2, A7, A9> = TupleValues3<A2, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_61")
public fun <A2, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues3<A2, A7, A10> = TupleValues3<A2, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_62")
public fun <A2, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues3<A2, A8, A9> = TupleValues3<A2, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_63")
public fun <A2, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues3<A2, A8, A10> = TupleValues3<A2, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_64")
public fun <A2, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues3<A2, A9, A10> = TupleValues3<A2, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_3_65")
public fun <A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A3, A4, A5> = TupleValues3<A3, A4, A5>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_3_66")
public fun <A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A3, A4, A6> = TupleValues3<A3, A4, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_67")
public fun <A3, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A3, A4, A7> = TupleValues3<A3, A4, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_68")
public fun <A3, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A3, A4, A8> = TupleValues3<A3, A4, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_69")
public fun <A3, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A3, A4, A9> = TupleValues3<A3, A4, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_70")
public fun <A3, A4, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A3, A4, A10> = TupleValues3<A3, A4, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_71")
public fun <A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A3, A5, A6> = TupleValues3<A3, A5, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_72")
public fun <A3, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A3, A5, A7> = TupleValues3<A3, A5, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_73")
public fun <A3, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A3, A5, A8> = TupleValues3<A3, A5, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_74")
public fun <A3, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A3, A5, A9> = TupleValues3<A3, A5, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_75")
public fun <A3, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A3, A5, A10> = TupleValues3<A3, A5, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_76")
public fun <A3, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues3<A3, A6, A7> = TupleValues3<A3, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_77")
public fun <A3, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues3<A3, A6, A8> = TupleValues3<A3, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_78")
public fun <A3, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues3<A3, A6, A9> = TupleValues3<A3, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_79")
public fun <A3, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues3<A3, A6, A10> = TupleValues3<A3, A6, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_80")
public fun <A3, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues3<A3, A7, A8> = TupleValues3<A3, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_81")
public fun <A3, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues3<A3, A7, A9> = TupleValues3<A3, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_82")
public fun <A3, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues3<A3, A7, A10> = TupleValues3<A3, A7, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_83")
public fun <A3, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues3<A3, A8, A9> = TupleValues3<A3, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_84")
public fun <A3, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues3<A3, A8, A10> = TupleValues3<A3, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_85")
public fun <A3, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues3<A3, A9, A10> = TupleValues3<A3, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_3_86")
public fun <A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues3<A4, A5, A6> = TupleValues3<A4, A5, A6>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_3_87")
public fun <A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues3<A4, A5, A7> = TupleValues3<A4, A5, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_88")
public fun <A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues3<A4, A5, A8> = TupleValues3<A4, A5, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_89")
public fun <A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues3<A4, A5, A9> = TupleValues3<A4, A5, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_90")
public fun <A4, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues3<A4, A5, A10> = TupleValues3<A4, A5, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_91")
public fun <A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues3<A4, A6, A7> = TupleValues3<A4, A6, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_92")
public fun <A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues3<A4, A6, A8> = TupleValues3<A4, A6, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_93")
public fun <A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues3<A4, A6, A9> = TupleValues3<A4, A6, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_94")
public fun <A4, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues3<A4, A6, A10> = TupleValues3<A4, A6, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_95")
public fun <A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues3<A4, A7, A8> = TupleValues3<A4, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_96")
public fun <A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues3<A4, A7, A9> = TupleValues3<A4, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_97")
public fun <A4, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues3<A4, A7, A10> = TupleValues3<A4, A7, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_98")
public fun <A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues3<A4, A8, A9> = TupleValues3<A4, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_99")
public fun <A4, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues3<A4, A8, A10> = TupleValues3<A4, A8, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_100")
public fun <A4, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues3<A4, A9, A10> = TupleValues3<A4, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_3_101")
public fun <A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues3<A5, A6, A7> = TupleValues3<A5, A6, A7>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_3_102")
public fun <A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues3<A5, A6, A8> = TupleValues3<A5, A6, A8>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_103")
public fun <A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues3<A5, A6, A9> = TupleValues3<A5, A6, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_104")
public fun <A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues3<A5, A6, A10> = TupleValues3<A5, A6, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_105")
public fun <A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues3<A5, A7, A8> = TupleValues3<A5, A7, A8>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_106")
public fun <A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues3<A5, A7, A9> = TupleValues3<A5, A7, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_107")
public fun <A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues3<A5, A7, A10> = TupleValues3<A5, A7, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_108")
public fun <A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues3<A5, A8, A9> = TupleValues3<A5, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_109")
public fun <A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues3<A5, A8, A10> = TupleValues3<A5, A8, A10>(tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_110")
public fun <A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues3<A5, A9, A10> = TupleValues3<A5, A9, A10>(tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_3_111")
public fun <A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues3<A6, A7, A8> = TupleValues3<A6, A7, A8>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_3_112")
public fun <A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues3<A6, A7, A9> = TupleValues3<A6, A7, A9>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_113")
public fun <A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues3<A6, A7, A10> = TupleValues3<A6, A7, A10>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_114")
public fun <A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues3<A6, A8, A9> = TupleValues3<A6, A8, A9>(tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_115")
public fun <A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues3<A6, A8, A10> = TupleValues3<A6, A8, A10>(tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_116")
public fun <A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues3<A6, A9, A10> = TupleValues3<A6, A9, A10>(tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_3_117")
public fun <A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues3<A7, A8, A9> = TupleValues3<A7, A8, A9>(tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_3_118")
public fun <A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues3<A7, A8, A10> = TupleValues3<A7, A8, A10>(tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_3_119")
public fun <A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues3<A7, A9, A10> = TupleValues3<A7, A9, A10>(tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_3_120")
public fun <A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues3<A8, A9, A10> = TupleValues3<A8, A9, A10>(tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_1")
public fun <A1, A2, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A3, A4> = TupleValues4<A1, A2, A3, A4>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second)

@JvmName("tupleValues_10_4_2")
public fun <A1, A2, A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A3, A5> = TupleValues4<A1, A2, A3, A5>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_4_3")
public fun <A1, A2, A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A3, A6> = TupleValues4<A1, A2, A3, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_4")
public fun <A1, A2, A3, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A3, A7> = TupleValues4<A1, A2, A3, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_5")
public fun <A1, A2, A3, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A2, A3, A8> = TupleValues4<A1, A2, A3, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_6")
public fun <A1, A2, A3, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A2, A3, A9> = TupleValues4<A1, A2, A3, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_7")
public fun <A1, A2, A3, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A2, A3, A10> = TupleValues4<A1, A2, A3, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_8")
public fun <A1, A2, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A4, A5> = TupleValues4<A1, A2, A4, A5>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_4_9")
public fun <A1, A2, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A4, A6> = TupleValues4<A1, A2, A4, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_10")
public fun <A1, A2, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A4, A7> = TupleValues4<A1, A2, A4, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_11")
public fun <A1, A2, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A2, A4, A8> = TupleValues4<A1, A2, A4, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_12")
public fun <A1, A2, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A2, A4, A9> = TupleValues4<A1, A2, A4, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_13")
public fun <A1, A2, A4, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A2, A4, A10> = TupleValues4<A1, A2, A4, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_14")
public fun <A1, A2, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A5, A6> = TupleValues4<A1, A2, A5, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_15")
public fun <A1, A2, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A5, A7> = TupleValues4<A1, A2, A5, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_16")
public fun <A1, A2, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A2, A5, A8> = TupleValues4<A1, A2, A5, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_17")
public fun <A1, A2, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A2, A5, A9> = TupleValues4<A1, A2, A5, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_18")
public fun <A1, A2, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A2, A5, A10> = TupleValues4<A1, A2, A5, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_19")
public fun <A1, A2, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A2, A6, A7> = TupleValues4<A1, A2, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_20")
public fun <A1, A2, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A2, A6, A8> = TupleValues4<A1, A2, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_21")
public fun <A1, A2, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A2, A6, A9> = TupleValues4<A1, A2, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_22")
public fun <A1, A2, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A2, A6, A10> = TupleValues4<A1, A2, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_23")
public fun <A1, A2, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A1, A2, A7, A8> = TupleValues4<A1, A2, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_24")
public fun <A1, A2, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A1, A2, A7, A9> = TupleValues4<A1, A2, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_25")
public fun <A1, A2, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A1, A2, A7, A10> = TupleValues4<A1, A2, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_26")
public fun <A1, A2, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A1, A2, A8, A9> = TupleValues4<A1, A2, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_27")
public fun <A1, A2, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A1, A2, A8, A10> = TupleValues4<A1, A2, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_28")
public fun <A1, A2, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A1, A2, A9, A10> = TupleValues4<A1, A2, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_29")
public fun <A1, A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A3, A4, A5> = TupleValues4<A1, A3, A4, A5>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_4_30")
public fun <A1, A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A3, A4, A6> = TupleValues4<A1, A3, A4, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_31")
public fun <A1, A3, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A3, A4, A7> = TupleValues4<A1, A3, A4, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_32")
public fun <A1, A3, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A3, A4, A8> = TupleValues4<A1, A3, A4, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_33")
public fun <A1, A3, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A3, A4, A9> = TupleValues4<A1, A3, A4, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_34")
public fun <A1, A3, A4, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A3, A4, A10> = TupleValues4<A1, A3, A4, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_35")
public fun <A1, A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A3, A5, A6> = TupleValues4<A1, A3, A5, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_36")
public fun <A1, A3, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A3, A5, A7> = TupleValues4<A1, A3, A5, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_37")
public fun <A1, A3, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A3, A5, A8> = TupleValues4<A1, A3, A5, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_38")
public fun <A1, A3, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A3, A5, A9> = TupleValues4<A1, A3, A5, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_39")
public fun <A1, A3, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A3, A5, A10> = TupleValues4<A1, A3, A5, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_40")
public fun <A1, A3, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A3, A6, A7> = TupleValues4<A1, A3, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_41")
public fun <A1, A3, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A3, A6, A8> = TupleValues4<A1, A3, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_42")
public fun <A1, A3, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A3, A6, A9> = TupleValues4<A1, A3, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_43")
public fun <A1, A3, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A3, A6, A10> = TupleValues4<A1, A3, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_44")
public fun <A1, A3, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A1, A3, A7, A8> = TupleValues4<A1, A3, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_45")
public fun <A1, A3, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A1, A3, A7, A9> = TupleValues4<A1, A3, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_46")
public fun <A1, A3, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A1, A3, A7, A10> = TupleValues4<A1, A3, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_47")
public fun <A1, A3, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A1, A3, A8, A9> = TupleValues4<A1, A3, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_48")
public fun <A1, A3, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A1, A3, A8, A10> = TupleValues4<A1, A3, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_49")
public fun <A1, A3, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A1, A3, A9, A10> = TupleValues4<A1, A3, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_50")
public fun <A1, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A1, A4, A5, A6> = TupleValues4<A1, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_51")
public fun <A1, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A4, A5, A7> = TupleValues4<A1, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_52")
public fun <A1, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A4, A5, A8> = TupleValues4<A1, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_53")
public fun <A1, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A4, A5, A9> = TupleValues4<A1, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_54")
public fun <A1, A4, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A4, A5, A10> = TupleValues4<A1, A4, A5, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_55")
public fun <A1, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A4, A6, A7> = TupleValues4<A1, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_56")
public fun <A1, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A4, A6, A8> = TupleValues4<A1, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_57")
public fun <A1, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A4, A6, A9> = TupleValues4<A1, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_58")
public fun <A1, A4, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A4, A6, A10> = TupleValues4<A1, A4, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_59")
public fun <A1, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A1, A4, A7, A8> = TupleValues4<A1, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_60")
public fun <A1, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A1, A4, A7, A9> = TupleValues4<A1, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_61")
public fun <A1, A4, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A1, A4, A7, A10> = TupleValues4<A1, A4, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_62")
public fun <A1, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A1, A4, A8, A9> = TupleValues4<A1, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_63")
public fun <A1, A4, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A1, A4, A8, A10> = TupleValues4<A1, A4, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_64")
public fun <A1, A4, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A1, A4, A9, A10> = TupleValues4<A1, A4, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_65")
public fun <A1, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A1, A5, A6, A7> = TupleValues4<A1, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_66")
public fun <A1, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A1, A5, A6, A8> = TupleValues4<A1, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_67")
public fun <A1, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A1, A5, A6, A9> = TupleValues4<A1, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_68")
public fun <A1, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A1, A5, A6, A10> = TupleValues4<A1, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_69")
public fun <A1, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A1, A5, A7, A8> = TupleValues4<A1, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_70")
public fun <A1, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A1, A5, A7, A9> = TupleValues4<A1, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_71")
public fun <A1, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A1, A5, A7, A10> = TupleValues4<A1, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_72")
public fun <A1, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A1, A5, A8, A9> = TupleValues4<A1, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_73")
public fun <A1, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A1, A5, A8, A10> = TupleValues4<A1, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_74")
public fun <A1, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A1, A5, A9, A10> = TupleValues4<A1, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_75")
public fun <A1, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues4<A1, A6, A7, A8> = TupleValues4<A1, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_76")
public fun <A1, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues4<A1, A6, A7, A9> = TupleValues4<A1, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_77")
public fun <A1, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues4<A1, A6, A7, A10> = TupleValues4<A1, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_78")
public fun <A1, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues4<A1, A6, A8, A9> = TupleValues4<A1, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_79")
public fun <A1, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues4<A1, A6, A8, A10> = TupleValues4<A1, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_80")
public fun <A1, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues4<A1, A6, A9, A10> = TupleValues4<A1, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_81")
public fun <A1, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues4<A1, A7, A8, A9> = TupleValues4<A1, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_82")
public fun <A1, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues4<A1, A7, A8, A10> = TupleValues4<A1, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_83")
public fun <A1, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues4<A1, A7, A9, A10> = TupleValues4<A1, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_84")
public fun <A1, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues4<A1, A8, A9, A10> = TupleValues4<A1, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_85")
public fun <A2, A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A2, A3, A4, A5> = TupleValues4<A2, A3, A4, A5>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_4_86")
public fun <A2, A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A2, A3, A4, A6> = TupleValues4<A2, A3, A4, A6>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_87")
public fun <A2, A3, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A2, A3, A4, A7> = TupleValues4<A2, A3, A4, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_88")
public fun <A2, A3, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A2, A3, A4, A8> = TupleValues4<A2, A3, A4, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_89")
public fun <A2, A3, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A2, A3, A4, A9> = TupleValues4<A2, A3, A4, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_90")
public fun <A2, A3, A4, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A2, A3, A4, A10> = TupleValues4<A2, A3, A4, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_91")
public fun <A2, A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A2, A3, A5, A6> = TupleValues4<A2, A3, A5, A6>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_92")
public fun <A2, A3, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A2, A3, A5, A7> = TupleValues4<A2, A3, A5, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_93")
public fun <A2, A3, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A2, A3, A5, A8> = TupleValues4<A2, A3, A5, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_94")
public fun <A2, A3, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A2, A3, A5, A9> = TupleValues4<A2, A3, A5, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_95")
public fun <A2, A3, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A2, A3, A5, A10> = TupleValues4<A2, A3, A5, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_96")
public fun <A2, A3, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A2, A3, A6, A7> = TupleValues4<A2, A3, A6, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_97")
public fun <A2, A3, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A2, A3, A6, A8> = TupleValues4<A2, A3, A6, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_98")
public fun <A2, A3, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A2, A3, A6, A9> = TupleValues4<A2, A3, A6, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_99")
public fun <A2, A3, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A2, A3, A6, A10> = TupleValues4<A2, A3, A6, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_100")
public fun <A2, A3, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A2, A3, A7, A8> = TupleValues4<A2, A3, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_101")
public fun <A2, A3, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A2, A3, A7, A9> = TupleValues4<A2, A3, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_102")
public fun <A2, A3, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A2, A3, A7, A10> = TupleValues4<A2, A3, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_103")
public fun <A2, A3, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A2, A3, A8, A9> = TupleValues4<A2, A3, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_104")
public fun <A2, A3, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A2, A3, A8, A10> = TupleValues4<A2, A3, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_105")
public fun <A2, A3, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A2, A3, A9, A10> = TupleValues4<A2, A3, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_106")
public fun <A2, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A2, A4, A5, A6> = TupleValues4<A2, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_107")
public fun <A2, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A2, A4, A5, A7> = TupleValues4<A2, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_108")
public fun <A2, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A2, A4, A5, A8> = TupleValues4<A2, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_109")
public fun <A2, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A2, A4, A5, A9> = TupleValues4<A2, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_110")
public fun <A2, A4, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A2, A4, A5, A10> = TupleValues4<A2, A4, A5, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_111")
public fun <A2, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A2, A4, A6, A7> = TupleValues4<A2, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_112")
public fun <A2, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A2, A4, A6, A8> = TupleValues4<A2, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_113")
public fun <A2, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A2, A4, A6, A9> = TupleValues4<A2, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_114")
public fun <A2, A4, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A2, A4, A6, A10> = TupleValues4<A2, A4, A6, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_115")
public fun <A2, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A2, A4, A7, A8> = TupleValues4<A2, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_116")
public fun <A2, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A2, A4, A7, A9> = TupleValues4<A2, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_117")
public fun <A2, A4, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A2, A4, A7, A10> = TupleValues4<A2, A4, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_118")
public fun <A2, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A2, A4, A8, A9> = TupleValues4<A2, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_119")
public fun <A2, A4, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A2, A4, A8, A10> = TupleValues4<A2, A4, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_120")
public fun <A2, A4, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A2, A4, A9, A10> = TupleValues4<A2, A4, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_121")
public fun <A2, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A2, A5, A6, A7> = TupleValues4<A2, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_122")
public fun <A2, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A2, A5, A6, A8> = TupleValues4<A2, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_123")
public fun <A2, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A2, A5, A6, A9> = TupleValues4<A2, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_124")
public fun <A2, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A2, A5, A6, A10> = TupleValues4<A2, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_125")
public fun <A2, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A2, A5, A7, A8> = TupleValues4<A2, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_126")
public fun <A2, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A2, A5, A7, A9> = TupleValues4<A2, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_127")
public fun <A2, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A2, A5, A7, A10> = TupleValues4<A2, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_128")
public fun <A2, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A2, A5, A8, A9> = TupleValues4<A2, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_129")
public fun <A2, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A2, A5, A8, A10> = TupleValues4<A2, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_130")
public fun <A2, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A2, A5, A9, A10> = TupleValues4<A2, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_131")
public fun <A2, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues4<A2, A6, A7, A8> = TupleValues4<A2, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_132")
public fun <A2, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues4<A2, A6, A7, A9> = TupleValues4<A2, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_133")
public fun <A2, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues4<A2, A6, A7, A10> = TupleValues4<A2, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_134")
public fun <A2, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues4<A2, A6, A8, A9> = TupleValues4<A2, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_135")
public fun <A2, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues4<A2, A6, A8, A10> = TupleValues4<A2, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_136")
public fun <A2, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues4<A2, A6, A9, A10> = TupleValues4<A2, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_137")
public fun <A2, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues4<A2, A7, A8, A9> = TupleValues4<A2, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_138")
public fun <A2, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues4<A2, A7, A8, A10> = TupleValues4<A2, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_139")
public fun <A2, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues4<A2, A7, A9, A10> = TupleValues4<A2, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_140")
public fun <A2, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues4<A2, A8, A9, A10> = TupleValues4<A2, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_141")
public fun <A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues4<A3, A4, A5, A6> = TupleValues4<A3, A4, A5, A6>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_4_142")
public fun <A3, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues4<A3, A4, A5, A7> = TupleValues4<A3, A4, A5, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_143")
public fun <A3, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues4<A3, A4, A5, A8> = TupleValues4<A3, A4, A5, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_144")
public fun <A3, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues4<A3, A4, A5, A9> = TupleValues4<A3, A4, A5, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_145")
public fun <A3, A4, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues4<A3, A4, A5, A10> = TupleValues4<A3, A4, A5, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_146")
public fun <A3, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A3, A4, A6, A7> = TupleValues4<A3, A4, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_147")
public fun <A3, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A3, A4, A6, A8> = TupleValues4<A3, A4, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_148")
public fun <A3, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A3, A4, A6, A9> = TupleValues4<A3, A4, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_149")
public fun <A3, A4, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A3, A4, A6, A10> = TupleValues4<A3, A4, A6, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_150")
public fun <A3, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A3, A4, A7, A8> = TupleValues4<A3, A4, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_151")
public fun <A3, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A3, A4, A7, A9> = TupleValues4<A3, A4, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_152")
public fun <A3, A4, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A3, A4, A7, A10> = TupleValues4<A3, A4, A7, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_153")
public fun <A3, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A3, A4, A8, A9> = TupleValues4<A3, A4, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_154")
public fun <A3, A4, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A3, A4, A8, A10> = TupleValues4<A3, A4, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_155")
public fun <A3, A4, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A3, A4, A9, A10> = TupleValues4<A3, A4, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_156")
public fun <A3, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A3, A5, A6, A7> = TupleValues4<A3, A5, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_157")
public fun <A3, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A3, A5, A6, A8> = TupleValues4<A3, A5, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_158")
public fun <A3, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A3, A5, A6, A9> = TupleValues4<A3, A5, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_159")
public fun <A3, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A3, A5, A6, A10> = TupleValues4<A3, A5, A6, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_160")
public fun <A3, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A3, A5, A7, A8> = TupleValues4<A3, A5, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_161")
public fun <A3, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A3, A5, A7, A9> = TupleValues4<A3, A5, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_162")
public fun <A3, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A3, A5, A7, A10> = TupleValues4<A3, A5, A7, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_163")
public fun <A3, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A3, A5, A8, A9> = TupleValues4<A3, A5, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_164")
public fun <A3, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A3, A5, A8, A10> = TupleValues4<A3, A5, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_165")
public fun <A3, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A3, A5, A9, A10> = TupleValues4<A3, A5, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_166")
public fun <A3, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues4<A3, A6, A7, A8> = TupleValues4<A3, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_167")
public fun <A3, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues4<A3, A6, A7, A9> = TupleValues4<A3, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_168")
public fun <A3, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues4<A3, A6, A7, A10> = TupleValues4<A3, A6, A7, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_169")
public fun <A3, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues4<A3, A6, A8, A9> = TupleValues4<A3, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_170")
public fun <A3, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues4<A3, A6, A8, A10> = TupleValues4<A3, A6, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_171")
public fun <A3, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues4<A3, A6, A9, A10> = TupleValues4<A3, A6, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_172")
public fun <A3, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues4<A3, A7, A8, A9> = TupleValues4<A3, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_173")
public fun <A3, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues4<A3, A7, A8, A10> = TupleValues4<A3, A7, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_174")
public fun <A3, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues4<A3, A7, A9, A10> = TupleValues4<A3, A7, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_175")
public fun <A3, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues4<A3, A8, A9, A10> = TupleValues4<A3, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_176")
public fun <A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues4<A4, A5, A6, A7> = TupleValues4<A4, A5, A6, A7>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_4_177")
public fun <A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues4<A4, A5, A6, A8> = TupleValues4<A4, A5, A6, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_178")
public fun <A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues4<A4, A5, A6, A9> = TupleValues4<A4, A5, A6, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_179")
public fun <A4, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues4<A4, A5, A6, A10> = TupleValues4<A4, A5, A6, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_180")
public fun <A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues4<A4, A5, A7, A8> = TupleValues4<A4, A5, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_181")
public fun <A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues4<A4, A5, A7, A9> = TupleValues4<A4, A5, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_182")
public fun <A4, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues4<A4, A5, A7, A10> = TupleValues4<A4, A5, A7, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_183")
public fun <A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues4<A4, A5, A8, A9> = TupleValues4<A4, A5, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_184")
public fun <A4, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues4<A4, A5, A8, A10> = TupleValues4<A4, A5, A8, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_185")
public fun <A4, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues4<A4, A5, A9, A10> = TupleValues4<A4, A5, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_186")
public fun <A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues4<A4, A6, A7, A8> = TupleValues4<A4, A6, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_187")
public fun <A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues4<A4, A6, A7, A9> = TupleValues4<A4, A6, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_188")
public fun <A4, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues4<A4, A6, A7, A10> = TupleValues4<A4, A6, A7, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_189")
public fun <A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues4<A4, A6, A8, A9> = TupleValues4<A4, A6, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_190")
public fun <A4, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues4<A4, A6, A8, A10> = TupleValues4<A4, A6, A8, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_191")
public fun <A4, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues4<A4, A6, A9, A10> = TupleValues4<A4, A6, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_192")
public fun <A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues4<A4, A7, A8, A9> = TupleValues4<A4, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_193")
public fun <A4, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues4<A4, A7, A8, A10> = TupleValues4<A4, A7, A8, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_194")
public fun <A4, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues4<A4, A7, A9, A10> = TupleValues4<A4, A7, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_195")
public fun <A4, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues4<A4, A8, A9, A10> = TupleValues4<A4, A8, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_196")
public fun <A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues4<A5, A6, A7, A8> = TupleValues4<A5, A6, A7, A8>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_4_197")
public fun <A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues4<A5, A6, A7, A9> = TupleValues4<A5, A6, A7, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_198")
public fun <A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues4<A5, A6, A7, A10> = TupleValues4<A5, A6, A7, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_199")
public fun <A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues4<A5, A6, A8, A9> = TupleValues4<A5, A6, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_200")
public fun <A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues4<A5, A6, A8, A10> = TupleValues4<A5, A6, A8, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_201")
public fun <A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues4<A5, A6, A9, A10> = TupleValues4<A5, A6, A9, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_202")
public fun <A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues4<A5, A7, A8, A9> = TupleValues4<A5, A7, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_203")
public fun <A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues4<A5, A7, A8, A10> = TupleValues4<A5, A7, A8, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_204")
public fun <A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues4<A5, A7, A9, A10> = TupleValues4<A5, A7, A9, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_205")
public fun <A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues4<A5, A8, A9, A10> = TupleValues4<A5, A8, A9, A10>(tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_206")
public fun <A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues4<A6, A7, A8, A9> = TupleValues4<A6, A7, A8, A9>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_4_207")
public fun <A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues4<A6, A7, A8, A10> = TupleValues4<A6, A7, A8, A10>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_4_208")
public fun <A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues4<A6, A7, A9, A10> = TupleValues4<A6, A7, A9, A10>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_209")
public fun <A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues4<A6, A8, A9, A10> = TupleValues4<A6, A8, A9, A10>(tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_4_210")
public fun <A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues4<A7, A8, A9, A10> = TupleValues4<A7, A8, A9, A10>(tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_1")
public fun <A1, A2, A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A3, A4, A5> = TupleValues5<A1, A2, A3, A4, A5>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second)

@JvmName("tupleValues_10_5_2")
public fun <A1, A2, A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A3, A4, A6> = TupleValues5<A1, A2, A3, A4, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_5_3")
public fun <A1, A2, A3, A4, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A3, A4, A7> = TupleValues5<A1, A2, A3, A4, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_4")
public fun <A1, A2, A3, A4, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A3, A4, A8> = TupleValues5<A1, A2, A3, A4, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_5")
public fun <A1, A2, A3, A4, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A3, A4, A9> = TupleValues5<A1, A2, A3, A4, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_6")
public fun <A1, A2, A3, A4, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A3, A4, A10> = TupleValues5<A1, A2, A3, A4, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_7")
public fun <A1, A2, A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A3, A5, A6> = TupleValues5<A1, A2, A3, A5, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_5_8")
public fun <A1, A2, A3, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A3, A5, A7> = TupleValues5<A1, A2, A3, A5, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_9")
public fun <A1, A2, A3, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A3, A5, A8> = TupleValues5<A1, A2, A3, A5, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_10")
public fun <A1, A2, A3, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A3, A5, A9> = TupleValues5<A1, A2, A3, A5, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_11")
public fun <A1, A2, A3, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A3, A5, A10> = TupleValues5<A1, A2, A3, A5, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_12")
public fun <A1, A2, A3, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A3, A6, A7> = TupleValues5<A1, A2, A3, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_13")
public fun <A1, A2, A3, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A3, A6, A8> = TupleValues5<A1, A2, A3, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_14")
public fun <A1, A2, A3, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A3, A6, A9> = TupleValues5<A1, A2, A3, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_15")
public fun <A1, A2, A3, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A3, A6, A10> = TupleValues5<A1, A2, A3, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_16")
public fun <A1, A2, A3, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A3, A7, A8> = TupleValues5<A1, A2, A3, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_17")
public fun <A1, A2, A3, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A3, A7, A9> = TupleValues5<A1, A2, A3, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_18")
public fun <A1, A2, A3, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A3, A7, A10> = TupleValues5<A1, A2, A3, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_19")
public fun <A1, A2, A3, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A2, A3, A8, A9> = TupleValues5<A1, A2, A3, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_20")
public fun <A1, A2, A3, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A2, A3, A8, A10> = TupleValues5<A1, A2, A3, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_21")
public fun <A1, A2, A3, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A2, A3, A9, A10> = TupleValues5<A1, A2, A3, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_22")
public fun <A1, A2, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A4, A5, A6> = TupleValues5<A1, A2, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_5_23")
public fun <A1, A2, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A4, A5, A7> = TupleValues5<A1, A2, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_24")
public fun <A1, A2, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A4, A5, A8> = TupleValues5<A1, A2, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_25")
public fun <A1, A2, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A4, A5, A9> = TupleValues5<A1, A2, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_26")
public fun <A1, A2, A4, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A4, A5, A10> = TupleValues5<A1, A2, A4, A5, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_27")
public fun <A1, A2, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A4, A6, A7> = TupleValues5<A1, A2, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_28")
public fun <A1, A2, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A4, A6, A8> = TupleValues5<A1, A2, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_29")
public fun <A1, A2, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A4, A6, A9> = TupleValues5<A1, A2, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_30")
public fun <A1, A2, A4, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A4, A6, A10> = TupleValues5<A1, A2, A4, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_31")
public fun <A1, A2, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A4, A7, A8> = TupleValues5<A1, A2, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_32")
public fun <A1, A2, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A4, A7, A9> = TupleValues5<A1, A2, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_33")
public fun <A1, A2, A4, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A4, A7, A10> = TupleValues5<A1, A2, A4, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_34")
public fun <A1, A2, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A2, A4, A8, A9> = TupleValues5<A1, A2, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_35")
public fun <A1, A2, A4, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A2, A4, A8, A10> = TupleValues5<A1, A2, A4, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_36")
public fun <A1, A2, A4, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A2, A4, A9, A10> = TupleValues5<A1, A2, A4, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_37")
public fun <A1, A2, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A2, A5, A6, A7> = TupleValues5<A1, A2, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_38")
public fun <A1, A2, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A5, A6, A8> = TupleValues5<A1, A2, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_39")
public fun <A1, A2, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A5, A6, A9> = TupleValues5<A1, A2, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_40")
public fun <A1, A2, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A5, A6, A10> = TupleValues5<A1, A2, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_41")
public fun <A1, A2, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A5, A7, A8> = TupleValues5<A1, A2, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_42")
public fun <A1, A2, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A5, A7, A9> = TupleValues5<A1, A2, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_43")
public fun <A1, A2, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A5, A7, A10> = TupleValues5<A1, A2, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_44")
public fun <A1, A2, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A2, A5, A8, A9> = TupleValues5<A1, A2, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_45")
public fun <A1, A2, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A2, A5, A8, A10> = TupleValues5<A1, A2, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_46")
public fun <A1, A2, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A2, A5, A9, A10> = TupleValues5<A1, A2, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_47")
public fun <A1, A2, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A2, A6, A7, A8> = TupleValues5<A1, A2, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_48")
public fun <A1, A2, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A2, A6, A7, A9> = TupleValues5<A1, A2, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_49")
public fun <A1, A2, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A2, A6, A7, A10> = TupleValues5<A1, A2, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_50")
public fun <A1, A2, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A2, A6, A8, A9> = TupleValues5<A1, A2, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_51")
public fun <A1, A2, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A2, A6, A8, A10> = TupleValues5<A1, A2, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_52")
public fun <A1, A2, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A2, A6, A9, A10> = TupleValues5<A1, A2, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_53")
public fun <A1, A2, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A1, A2, A7, A8, A9> = TupleValues5<A1, A2, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_54")
public fun <A1, A2, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A1, A2, A7, A8, A10> = TupleValues5<A1, A2, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_55")
public fun <A1, A2, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A1, A2, A7, A9, A10> = TupleValues5<A1, A2, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_56")
public fun <A1, A2, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A1, A2, A8, A9, A10> = TupleValues5<A1, A2, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_57")
public fun <A1, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues5<A1, A3, A4, A5, A6> = TupleValues5<A1, A3, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_5_58")
public fun <A1, A3, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A3, A4, A5, A7> = TupleValues5<A1, A3, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_59")
public fun <A1, A3, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A3, A4, A5, A8> = TupleValues5<A1, A3, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_60")
public fun <A1, A3, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A3, A4, A5, A9> = TupleValues5<A1, A3, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_61")
public fun <A1, A3, A4, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A3, A4, A5, A10> = TupleValues5<A1, A3, A4, A5, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_62")
public fun <A1, A3, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A3, A4, A6, A7> = TupleValues5<A1, A3, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_63")
public fun <A1, A3, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A3, A4, A6, A8> = TupleValues5<A1, A3, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_64")
public fun <A1, A3, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A3, A4, A6, A9> = TupleValues5<A1, A3, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_65")
public fun <A1, A3, A4, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A3, A4, A6, A10> = TupleValues5<A1, A3, A4, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_66")
public fun <A1, A3, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A3, A4, A7, A8> = TupleValues5<A1, A3, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_67")
public fun <A1, A3, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A3, A4, A7, A9> = TupleValues5<A1, A3, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_68")
public fun <A1, A3, A4, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A3, A4, A7, A10> = TupleValues5<A1, A3, A4, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_69")
public fun <A1, A3, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A3, A4, A8, A9> = TupleValues5<A1, A3, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_70")
public fun <A1, A3, A4, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A3, A4, A8, A10> = TupleValues5<A1, A3, A4, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_71")
public fun <A1, A3, A4, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A3, A4, A9, A10> = TupleValues5<A1, A3, A4, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_72")
public fun <A1, A3, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A3, A5, A6, A7> = TupleValues5<A1, A3, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_73")
public fun <A1, A3, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A3, A5, A6, A8> = TupleValues5<A1, A3, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_74")
public fun <A1, A3, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A3, A5, A6, A9> = TupleValues5<A1, A3, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_75")
public fun <A1, A3, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A3, A5, A6, A10> = TupleValues5<A1, A3, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_76")
public fun <A1, A3, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A3, A5, A7, A8> = TupleValues5<A1, A3, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_77")
public fun <A1, A3, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A3, A5, A7, A9> = TupleValues5<A1, A3, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_78")
public fun <A1, A3, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A3, A5, A7, A10> = TupleValues5<A1, A3, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_79")
public fun <A1, A3, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A3, A5, A8, A9> = TupleValues5<A1, A3, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_80")
public fun <A1, A3, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A3, A5, A8, A10> = TupleValues5<A1, A3, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_81")
public fun <A1, A3, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A3, A5, A9, A10> = TupleValues5<A1, A3, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_82")
public fun <A1, A3, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A3, A6, A7, A8> = TupleValues5<A1, A3, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_83")
public fun <A1, A3, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A3, A6, A7, A9> = TupleValues5<A1, A3, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_84")
public fun <A1, A3, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A3, A6, A7, A10> = TupleValues5<A1, A3, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_85")
public fun <A1, A3, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A3, A6, A8, A9> = TupleValues5<A1, A3, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_86")
public fun <A1, A3, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A3, A6, A8, A10> = TupleValues5<A1, A3, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_87")
public fun <A1, A3, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A3, A6, A9, A10> = TupleValues5<A1, A3, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_88")
public fun <A1, A3, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A1, A3, A7, A8, A9> = TupleValues5<A1, A3, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_89")
public fun <A1, A3, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A1, A3, A7, A8, A10> = TupleValues5<A1, A3, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_90")
public fun <A1, A3, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A1, A3, A7, A9, A10> = TupleValues5<A1, A3, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_91")
public fun <A1, A3, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A1, A3, A8, A9, A10> = TupleValues5<A1, A3, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_92")
public fun <A1, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A1, A4, A5, A6, A7> = TupleValues5<A1, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_93")
public fun <A1, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A1, A4, A5, A6, A8> = TupleValues5<A1, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_94")
public fun <A1, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A1, A4, A5, A6, A9> = TupleValues5<A1, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_95")
public fun <A1, A4, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A1, A4, A5, A6, A10> = TupleValues5<A1, A4, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_96")
public fun <A1, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A4, A5, A7, A8> = TupleValues5<A1, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_97")
public fun <A1, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A4, A5, A7, A9> = TupleValues5<A1, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_98")
public fun <A1, A4, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A4, A5, A7, A10> = TupleValues5<A1, A4, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_99")
public fun <A1, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A4, A5, A8, A9> = TupleValues5<A1, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_100")
public fun <A1, A4, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A4, A5, A8, A10> = TupleValues5<A1, A4, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_101")
public fun <A1, A4, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A4, A5, A9, A10> = TupleValues5<A1, A4, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_102")
public fun <A1, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A4, A6, A7, A8> = TupleValues5<A1, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_103")
public fun <A1, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A4, A6, A7, A9> = TupleValues5<A1, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_104")
public fun <A1, A4, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A4, A6, A7, A10> = TupleValues5<A1, A4, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_105")
public fun <A1, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A4, A6, A8, A9> = TupleValues5<A1, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_106")
public fun <A1, A4, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A4, A6, A8, A10> = TupleValues5<A1, A4, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_107")
public fun <A1, A4, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A4, A6, A9, A10> = TupleValues5<A1, A4, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_108")
public fun <A1, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A1, A4, A7, A8, A9> = TupleValues5<A1, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_109")
public fun <A1, A4, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A1, A4, A7, A8, A10> = TupleValues5<A1, A4, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_110")
public fun <A1, A4, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A1, A4, A7, A9, A10> = TupleValues5<A1, A4, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_111")
public fun <A1, A4, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A1, A4, A8, A9, A10> = TupleValues5<A1, A4, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_112")
public fun <A1, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A1, A5, A6, A7, A8> = TupleValues5<A1, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_113")
public fun <A1, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A1, A5, A6, A7, A9> = TupleValues5<A1, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_114")
public fun <A1, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A1, A5, A6, A7, A10> = TupleValues5<A1, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_115")
public fun <A1, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A1, A5, A6, A8, A9> = TupleValues5<A1, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_116")
public fun <A1, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A1, A5, A6, A8, A10> = TupleValues5<A1, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_117")
public fun <A1, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A1, A5, A6, A9, A10> = TupleValues5<A1, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_118")
public fun <A1, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A1, A5, A7, A8, A9> = TupleValues5<A1, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_119")
public fun <A1, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A1, A5, A7, A8, A10> = TupleValues5<A1, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_120")
public fun <A1, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A1, A5, A7, A9, A10> = TupleValues5<A1, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_121")
public fun <A1, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A1, A5, A8, A9, A10> = TupleValues5<A1, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_122")
public fun <A1, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues5<A1, A6, A7, A8, A9> = TupleValues5<A1, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_123")
public fun <A1, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues5<A1, A6, A7, A8, A10> = TupleValues5<A1, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_124")
public fun <A1, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues5<A1, A6, A7, A9, A10> = TupleValues5<A1, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_125")
public fun <A1, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues5<A1, A6, A8, A9, A10> = TupleValues5<A1, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_126")
public fun <A1, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues5<A1, A7, A8, A9, A10> = TupleValues5<A1, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_127")
public fun <A2, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues5<A2, A3, A4, A5, A6> = TupleValues5<A2, A3, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_5_128")
public fun <A2, A3, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues5<A2, A3, A4, A5, A7> = TupleValues5<A2, A3, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_129")
public fun <A2, A3, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues5<A2, A3, A4, A5, A8> = TupleValues5<A2, A3, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_130")
public fun <A2, A3, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues5<A2, A3, A4, A5, A9> = TupleValues5<A2, A3, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_131")
public fun <A2, A3, A4, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues5<A2, A3, A4, A5, A10> = TupleValues5<A2, A3, A4, A5, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_132")
public fun <A2, A3, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A2, A3, A4, A6, A7> = TupleValues5<A2, A3, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_133")
public fun <A2, A3, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A2, A3, A4, A6, A8> = TupleValues5<A2, A3, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_134")
public fun <A2, A3, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A2, A3, A4, A6, A9> = TupleValues5<A2, A3, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_135")
public fun <A2, A3, A4, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A2, A3, A4, A6, A10> = TupleValues5<A2, A3, A4, A6, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_136")
public fun <A2, A3, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A2, A3, A4, A7, A8> = TupleValues5<A2, A3, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_137")
public fun <A2, A3, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A2, A3, A4, A7, A9> = TupleValues5<A2, A3, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_138")
public fun <A2, A3, A4, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A2, A3, A4, A7, A10> = TupleValues5<A2, A3, A4, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_139")
public fun <A2, A3, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A2, A3, A4, A8, A9> = TupleValues5<A2, A3, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_140")
public fun <A2, A3, A4, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A2, A3, A4, A8, A10> = TupleValues5<A2, A3, A4, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_141")
public fun <A2, A3, A4, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A2, A3, A4, A9, A10> = TupleValues5<A2, A3, A4, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_142")
public fun <A2, A3, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A2, A3, A5, A6, A7> = TupleValues5<A2, A3, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_143")
public fun <A2, A3, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A2, A3, A5, A6, A8> = TupleValues5<A2, A3, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_144")
public fun <A2, A3, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A2, A3, A5, A6, A9> = TupleValues5<A2, A3, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_145")
public fun <A2, A3, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A2, A3, A5, A6, A10> = TupleValues5<A2, A3, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_146")
public fun <A2, A3, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A2, A3, A5, A7, A8> = TupleValues5<A2, A3, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_147")
public fun <A2, A3, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A2, A3, A5, A7, A9> = TupleValues5<A2, A3, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_148")
public fun <A2, A3, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A2, A3, A5, A7, A10> = TupleValues5<A2, A3, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_149")
public fun <A2, A3, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A2, A3, A5, A8, A9> = TupleValues5<A2, A3, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_150")
public fun <A2, A3, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A2, A3, A5, A8, A10> = TupleValues5<A2, A3, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_151")
public fun <A2, A3, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A2, A3, A5, A9, A10> = TupleValues5<A2, A3, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_152")
public fun <A2, A3, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A2, A3, A6, A7, A8> = TupleValues5<A2, A3, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_153")
public fun <A2, A3, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A2, A3, A6, A7, A9> = TupleValues5<A2, A3, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_154")
public fun <A2, A3, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A2, A3, A6, A7, A10> = TupleValues5<A2, A3, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_155")
public fun <A2, A3, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A2, A3, A6, A8, A9> = TupleValues5<A2, A3, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_156")
public fun <A2, A3, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A2, A3, A6, A8, A10> = TupleValues5<A2, A3, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_157")
public fun <A2, A3, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A2, A3, A6, A9, A10> = TupleValues5<A2, A3, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_158")
public fun <A2, A3, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A2, A3, A7, A8, A9> = TupleValues5<A2, A3, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_159")
public fun <A2, A3, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A2, A3, A7, A8, A10> = TupleValues5<A2, A3, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_160")
public fun <A2, A3, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A2, A3, A7, A9, A10> = TupleValues5<A2, A3, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_161")
public fun <A2, A3, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A2, A3, A8, A9, A10> = TupleValues5<A2, A3, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_162")
public fun <A2, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A2, A4, A5, A6, A7> = TupleValues5<A2, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_163")
public fun <A2, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A2, A4, A5, A6, A8> = TupleValues5<A2, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_164")
public fun <A2, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A2, A4, A5, A6, A9> = TupleValues5<A2, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_165")
public fun <A2, A4, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A2, A4, A5, A6, A10> = TupleValues5<A2, A4, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_166")
public fun <A2, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A2, A4, A5, A7, A8> = TupleValues5<A2, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_167")
public fun <A2, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A2, A4, A5, A7, A9> = TupleValues5<A2, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_168")
public fun <A2, A4, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A2, A4, A5, A7, A10> = TupleValues5<A2, A4, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_169")
public fun <A2, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A2, A4, A5, A8, A9> = TupleValues5<A2, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_170")
public fun <A2, A4, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A2, A4, A5, A8, A10> = TupleValues5<A2, A4, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_171")
public fun <A2, A4, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A2, A4, A5, A9, A10> = TupleValues5<A2, A4, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_172")
public fun <A2, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A2, A4, A6, A7, A8> = TupleValues5<A2, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_173")
public fun <A2, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A2, A4, A6, A7, A9> = TupleValues5<A2, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_174")
public fun <A2, A4, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A2, A4, A6, A7, A10> = TupleValues5<A2, A4, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_175")
public fun <A2, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A2, A4, A6, A8, A9> = TupleValues5<A2, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_176")
public fun <A2, A4, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A2, A4, A6, A8, A10> = TupleValues5<A2, A4, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_177")
public fun <A2, A4, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A2, A4, A6, A9, A10> = TupleValues5<A2, A4, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_178")
public fun <A2, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A2, A4, A7, A8, A9> = TupleValues5<A2, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_179")
public fun <A2, A4, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A2, A4, A7, A8, A10> = TupleValues5<A2, A4, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_180")
public fun <A2, A4, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A2, A4, A7, A9, A10> = TupleValues5<A2, A4, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_181")
public fun <A2, A4, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A2, A4, A8, A9, A10> = TupleValues5<A2, A4, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_182")
public fun <A2, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A2, A5, A6, A7, A8> = TupleValues5<A2, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_183")
public fun <A2, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A2, A5, A6, A7, A9> = TupleValues5<A2, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_184")
public fun <A2, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A2, A5, A6, A7, A10> = TupleValues5<A2, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_185")
public fun <A2, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A2, A5, A6, A8, A9> = TupleValues5<A2, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_186")
public fun <A2, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A2, A5, A6, A8, A10> = TupleValues5<A2, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_187")
public fun <A2, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A2, A5, A6, A9, A10> = TupleValues5<A2, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_188")
public fun <A2, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A2, A5, A7, A8, A9> = TupleValues5<A2, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_189")
public fun <A2, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A2, A5, A7, A8, A10> = TupleValues5<A2, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_190")
public fun <A2, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A2, A5, A7, A9, A10> = TupleValues5<A2, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_191")
public fun <A2, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A2, A5, A8, A9, A10> = TupleValues5<A2, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_192")
public fun <A2, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues5<A2, A6, A7, A8, A9> = TupleValues5<A2, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_193")
public fun <A2, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues5<A2, A6, A7, A8, A10> = TupleValues5<A2, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_194")
public fun <A2, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues5<A2, A6, A7, A9, A10> = TupleValues5<A2, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_195")
public fun <A2, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues5<A2, A6, A8, A9, A10> = TupleValues5<A2, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_196")
public fun <A2, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues5<A2, A7, A8, A9, A10> = TupleValues5<A2, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_197")
public fun <A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues5<A3, A4, A5, A6, A7> = TupleValues5<A3, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_5_198")
public fun <A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues5<A3, A4, A5, A6, A8> = TupleValues5<A3, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_199")
public fun <A3, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues5<A3, A4, A5, A6, A9> = TupleValues5<A3, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_200")
public fun <A3, A4, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues5<A3, A4, A5, A6, A10> = TupleValues5<A3, A4, A5, A6, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_201")
public fun <A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues5<A3, A4, A5, A7, A8> = TupleValues5<A3, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_202")
public fun <A3, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues5<A3, A4, A5, A7, A9> = TupleValues5<A3, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_203")
public fun <A3, A4, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues5<A3, A4, A5, A7, A10> = TupleValues5<A3, A4, A5, A7, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_204")
public fun <A3, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues5<A3, A4, A5, A8, A9> = TupleValues5<A3, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_205")
public fun <A3, A4, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues5<A3, A4, A5, A8, A10> = TupleValues5<A3, A4, A5, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_206")
public fun <A3, A4, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues5<A3, A4, A5, A9, A10> = TupleValues5<A3, A4, A5, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_207")
public fun <A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A3, A4, A6, A7, A8> = TupleValues5<A3, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_208")
public fun <A3, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A3, A4, A6, A7, A9> = TupleValues5<A3, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_209")
public fun <A3, A4, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A3, A4, A6, A7, A10> = TupleValues5<A3, A4, A6, A7, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_210")
public fun <A3, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A3, A4, A6, A8, A9> = TupleValues5<A3, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_211")
public fun <A3, A4, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A3, A4, A6, A8, A10> = TupleValues5<A3, A4, A6, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_212")
public fun <A3, A4, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A3, A4, A6, A9, A10> = TupleValues5<A3, A4, A6, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_213")
public fun <A3, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A3, A4, A7, A8, A9> = TupleValues5<A3, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_214")
public fun <A3, A4, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A3, A4, A7, A8, A10> = TupleValues5<A3, A4, A7, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_215")
public fun <A3, A4, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A3, A4, A7, A9, A10> = TupleValues5<A3, A4, A7, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_216")
public fun <A3, A4, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A3, A4, A8, A9, A10> = TupleValues5<A3, A4, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_217")
public fun <A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A3, A5, A6, A7, A8> = TupleValues5<A3, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_218")
public fun <A3, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A3, A5, A6, A7, A9> = TupleValues5<A3, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_219")
public fun <A3, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A3, A5, A6, A7, A10> = TupleValues5<A3, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_220")
public fun <A3, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A3, A5, A6, A8, A9> = TupleValues5<A3, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_221")
public fun <A3, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A3, A5, A6, A8, A10> = TupleValues5<A3, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_222")
public fun <A3, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A3, A5, A6, A9, A10> = TupleValues5<A3, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_223")
public fun <A3, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A3, A5, A7, A8, A9> = TupleValues5<A3, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_224")
public fun <A3, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A3, A5, A7, A8, A10> = TupleValues5<A3, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_225")
public fun <A3, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A3, A5, A7, A9, A10> = TupleValues5<A3, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_226")
public fun <A3, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A3, A5, A8, A9, A10> = TupleValues5<A3, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_227")
public fun <A3, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues5<A3, A6, A7, A8, A9> = TupleValues5<A3, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_228")
public fun <A3, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues5<A3, A6, A7, A8, A10> = TupleValues5<A3, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_229")
public fun <A3, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues5<A3, A6, A7, A9, A10> = TupleValues5<A3, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_230")
public fun <A3, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues5<A3, A6, A8, A9, A10> = TupleValues5<A3, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_231")
public fun <A3, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues5<A3, A7, A8, A9, A10> = TupleValues5<A3, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_232")
public fun <A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues5<A4, A5, A6, A7, A8> = TupleValues5<A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_5_233")
public fun <A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues5<A4, A5, A6, A7, A9> = TupleValues5<A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_234")
public fun <A4, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues5<A4, A5, A6, A7, A10> = TupleValues5<A4, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_235")
public fun <A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues5<A4, A5, A6, A8, A9> = TupleValues5<A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_236")
public fun <A4, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues5<A4, A5, A6, A8, A10> = TupleValues5<A4, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_237")
public fun <A4, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues5<A4, A5, A6, A9, A10> = TupleValues5<A4, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_238")
public fun <A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues5<A4, A5, A7, A8, A9> = TupleValues5<A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_239")
public fun <A4, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues5<A4, A5, A7, A8, A10> = TupleValues5<A4, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_240")
public fun <A4, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues5<A4, A5, A7, A9, A10> = TupleValues5<A4, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_241")
public fun <A4, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues5<A4, A5, A8, A9, A10> = TupleValues5<A4, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_242")
public fun <A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues5<A4, A6, A7, A8, A9> = TupleValues5<A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_243")
public fun <A4, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues5<A4, A6, A7, A8, A10> = TupleValues5<A4, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_244")
public fun <A4, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues5<A4, A6, A7, A9, A10> = TupleValues5<A4, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_245")
public fun <A4, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues5<A4, A6, A8, A9, A10> = TupleValues5<A4, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_246")
public fun <A4, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues5<A4, A7, A8, A9, A10> = TupleValues5<A4, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_247")
public fun <A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues5<A5, A6, A7, A8, A9> = TupleValues5<A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_5_248")
public fun <A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues5<A5, A6, A7, A8, A10> = TupleValues5<A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_5_249")
public fun <A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues5<A5, A6, A7, A9, A10> = TupleValues5<A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_250")
public fun <A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues5<A5, A6, A8, A9, A10> = TupleValues5<A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_251")
public fun <A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues5<A5, A7, A8, A9, A10> = TupleValues5<A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_5_252")
public fun <A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues5<A6, A7, A8, A9, A10> = TupleValues5<A6, A7, A8, A9, A10>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_1")
public fun <A1, A2, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>, Unit>): TupleValues6<A1, A2, A3, A4, A5, A6> = TupleValues6<A1, A2, A3, A4, A5, A6>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second)

@JvmName("tupleValues_10_6_2")
public fun <A1, A2, A3, A4, A5, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>, Unit>): TupleValues6<A1, A2, A3, A4, A5, A7> = TupleValues6<A1, A2, A3, A4, A5, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_6_3")
public fun <A1, A2, A3, A4, A5, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A3, A4, A5, A8> = TupleValues6<A1, A2, A3, A4, A5, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_4")
public fun <A1, A2, A3, A4, A5, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A3, A4, A5, A9> = TupleValues6<A1, A2, A3, A4, A5, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_5")
public fun <A1, A2, A3, A4, A5, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A3, A4, A5, A10> = TupleValues6<A1, A2, A3, A4, A5, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_6")
public fun <A1, A2, A3, A4, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues6<A1, A2, A3, A4, A6, A7> = TupleValues6<A1, A2, A3, A4, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_6_7")
public fun <A1, A2, A3, A4, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A3, A4, A6, A8> = TupleValues6<A1, A2, A3, A4, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_8")
public fun <A1, A2, A3, A4, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A3, A4, A6, A9> = TupleValues6<A1, A2, A3, A4, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_9")
public fun <A1, A2, A3, A4, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A3, A4, A6, A10> = TupleValues6<A1, A2, A3, A4, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_10")
public fun <A1, A2, A3, A4, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A3, A4, A7, A8> = TupleValues6<A1, A2, A3, A4, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_11")
public fun <A1, A2, A3, A4, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A3, A4, A7, A9> = TupleValues6<A1, A2, A3, A4, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_12")
public fun <A1, A2, A3, A4, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A3, A4, A7, A10> = TupleValues6<A1, A2, A3, A4, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_13")
public fun <A1, A2, A3, A4, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A2, A3, A4, A8, A9> = TupleValues6<A1, A2, A3, A4, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_14")
public fun <A1, A2, A3, A4, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A2, A3, A4, A8, A10> = TupleValues6<A1, A2, A3, A4, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_15")
public fun <A1, A2, A3, A4, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A2, A3, A4, A9, A10> = TupleValues6<A1, A2, A3, A4, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_16")
public fun <A1, A2, A3, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues6<A1, A2, A3, A5, A6, A7> = TupleValues6<A1, A2, A3, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_6_17")
public fun <A1, A2, A3, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A3, A5, A6, A8> = TupleValues6<A1, A2, A3, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_18")
public fun <A1, A2, A3, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A3, A5, A6, A9> = TupleValues6<A1, A2, A3, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_19")
public fun <A1, A2, A3, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A3, A5, A6, A10> = TupleValues6<A1, A2, A3, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_20")
public fun <A1, A2, A3, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A3, A5, A7, A8> = TupleValues6<A1, A2, A3, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_21")
public fun <A1, A2, A3, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A3, A5, A7, A9> = TupleValues6<A1, A2, A3, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_22")
public fun <A1, A2, A3, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A3, A5, A7, A10> = TupleValues6<A1, A2, A3, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_23")
public fun <A1, A2, A3, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A2, A3, A5, A8, A9> = TupleValues6<A1, A2, A3, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_24")
public fun <A1, A2, A3, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A2, A3, A5, A8, A10> = TupleValues6<A1, A2, A3, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_25")
public fun <A1, A2, A3, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A2, A3, A5, A9, A10> = TupleValues6<A1, A2, A3, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_26")
public fun <A1, A2, A3, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A3, A6, A7, A8> = TupleValues6<A1, A2, A3, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_27")
public fun <A1, A2, A3, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A3, A6, A7, A9> = TupleValues6<A1, A2, A3, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_28")
public fun <A1, A2, A3, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A3, A6, A7, A10> = TupleValues6<A1, A2, A3, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_29")
public fun <A1, A2, A3, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A2, A3, A6, A8, A9> = TupleValues6<A1, A2, A3, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_30")
public fun <A1, A2, A3, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A2, A3, A6, A8, A10> = TupleValues6<A1, A2, A3, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_31")
public fun <A1, A2, A3, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A2, A3, A6, A9, A10> = TupleValues6<A1, A2, A3, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_32")
public fun <A1, A2, A3, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A2, A3, A7, A8, A9> = TupleValues6<A1, A2, A3, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_33")
public fun <A1, A2, A3, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A2, A3, A7, A8, A10> = TupleValues6<A1, A2, A3, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_34")
public fun <A1, A2, A3, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A2, A3, A7, A9, A10> = TupleValues6<A1, A2, A3, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_35")
public fun <A1, A2, A3, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A2, A3, A8, A9, A10> = TupleValues6<A1, A2, A3, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_36")
public fun <A1, A2, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues6<A1, A2, A4, A5, A6, A7> = TupleValues6<A1, A2, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_6_37")
public fun <A1, A2, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A4, A5, A6, A8> = TupleValues6<A1, A2, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_38")
public fun <A1, A2, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A4, A5, A6, A9> = TupleValues6<A1, A2, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_39")
public fun <A1, A2, A4, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A4, A5, A6, A10> = TupleValues6<A1, A2, A4, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_40")
public fun <A1, A2, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A4, A5, A7, A8> = TupleValues6<A1, A2, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_41")
public fun <A1, A2, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A4, A5, A7, A9> = TupleValues6<A1, A2, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_42")
public fun <A1, A2, A4, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A4, A5, A7, A10> = TupleValues6<A1, A2, A4, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_43")
public fun <A1, A2, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A2, A4, A5, A8, A9> = TupleValues6<A1, A2, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_44")
public fun <A1, A2, A4, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A2, A4, A5, A8, A10> = TupleValues6<A1, A2, A4, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_45")
public fun <A1, A2, A4, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A2, A4, A5, A9, A10> = TupleValues6<A1, A2, A4, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_46")
public fun <A1, A2, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A4, A6, A7, A8> = TupleValues6<A1, A2, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_47")
public fun <A1, A2, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A4, A6, A7, A9> = TupleValues6<A1, A2, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_48")
public fun <A1, A2, A4, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A4, A6, A7, A10> = TupleValues6<A1, A2, A4, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_49")
public fun <A1, A2, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A2, A4, A6, A8, A9> = TupleValues6<A1, A2, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_50")
public fun <A1, A2, A4, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A2, A4, A6, A8, A10> = TupleValues6<A1, A2, A4, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_51")
public fun <A1, A2, A4, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A2, A4, A6, A9, A10> = TupleValues6<A1, A2, A4, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_52")
public fun <A1, A2, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A2, A4, A7, A8, A9> = TupleValues6<A1, A2, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_53")
public fun <A1, A2, A4, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A2, A4, A7, A8, A10> = TupleValues6<A1, A2, A4, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_54")
public fun <A1, A2, A4, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A2, A4, A7, A9, A10> = TupleValues6<A1, A2, A4, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_55")
public fun <A1, A2, A4, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A2, A4, A8, A9, A10> = TupleValues6<A1, A2, A4, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_56")
public fun <A1, A2, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A2, A5, A6, A7, A8> = TupleValues6<A1, A2, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_57")
public fun <A1, A2, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A2, A5, A6, A7, A9> = TupleValues6<A1, A2, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_58")
public fun <A1, A2, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A2, A5, A6, A7, A10> = TupleValues6<A1, A2, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_59")
public fun <A1, A2, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A2, A5, A6, A8, A9> = TupleValues6<A1, A2, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_60")
public fun <A1, A2, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A2, A5, A6, A8, A10> = TupleValues6<A1, A2, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_61")
public fun <A1, A2, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A2, A5, A6, A9, A10> = TupleValues6<A1, A2, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_62")
public fun <A1, A2, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A2, A5, A7, A8, A9> = TupleValues6<A1, A2, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_63")
public fun <A1, A2, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A2, A5, A7, A8, A10> = TupleValues6<A1, A2, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_64")
public fun <A1, A2, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A2, A5, A7, A9, A10> = TupleValues6<A1, A2, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_65")
public fun <A1, A2, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A2, A5, A8, A9, A10> = TupleValues6<A1, A2, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_66")
public fun <A1, A2, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A2, A6, A7, A8, A9> = TupleValues6<A1, A2, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_67")
public fun <A1, A2, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A2, A6, A7, A8, A10> = TupleValues6<A1, A2, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_68")
public fun <A1, A2, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A2, A6, A7, A9, A10> = TupleValues6<A1, A2, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_69")
public fun <A1, A2, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A2, A6, A8, A9, A10> = TupleValues6<A1, A2, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_70")
public fun <A1, A2, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A1, A2, A7, A8, A9, A10> = TupleValues6<A1, A2, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_71")
public fun <A1, A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues6<A1, A3, A4, A5, A6, A7> = TupleValues6<A1, A3, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_6_72")
public fun <A1, A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues6<A1, A3, A4, A5, A6, A8> = TupleValues6<A1, A3, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_73")
public fun <A1, A3, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues6<A1, A3, A4, A5, A6, A9> = TupleValues6<A1, A3, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_74")
public fun <A1, A3, A4, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues6<A1, A3, A4, A5, A6, A10> = TupleValues6<A1, A3, A4, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_75")
public fun <A1, A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A3, A4, A5, A7, A8> = TupleValues6<A1, A3, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_76")
public fun <A1, A3, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A3, A4, A5, A7, A9> = TupleValues6<A1, A3, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_77")
public fun <A1, A3, A4, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A3, A4, A5, A7, A10> = TupleValues6<A1, A3, A4, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_78")
public fun <A1, A3, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A3, A4, A5, A8, A9> = TupleValues6<A1, A3, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_79")
public fun <A1, A3, A4, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A3, A4, A5, A8, A10> = TupleValues6<A1, A3, A4, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_80")
public fun <A1, A3, A4, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A3, A4, A5, A9, A10> = TupleValues6<A1, A3, A4, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_81")
public fun <A1, A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A3, A4, A6, A7, A8> = TupleValues6<A1, A3, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_82")
public fun <A1, A3, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A3, A4, A6, A7, A9> = TupleValues6<A1, A3, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_83")
public fun <A1, A3, A4, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A3, A4, A6, A7, A10> = TupleValues6<A1, A3, A4, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_84")
public fun <A1, A3, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A3, A4, A6, A8, A9> = TupleValues6<A1, A3, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_85")
public fun <A1, A3, A4, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A3, A4, A6, A8, A10> = TupleValues6<A1, A3, A4, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_86")
public fun <A1, A3, A4, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A3, A4, A6, A9, A10> = TupleValues6<A1, A3, A4, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_87")
public fun <A1, A3, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A3, A4, A7, A8, A9> = TupleValues6<A1, A3, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_88")
public fun <A1, A3, A4, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A3, A4, A7, A8, A10> = TupleValues6<A1, A3, A4, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_89")
public fun <A1, A3, A4, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A3, A4, A7, A9, A10> = TupleValues6<A1, A3, A4, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_90")
public fun <A1, A3, A4, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A3, A4, A8, A9, A10> = TupleValues6<A1, A3, A4, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_91")
public fun <A1, A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A3, A5, A6, A7, A8> = TupleValues6<A1, A3, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_92")
public fun <A1, A3, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A3, A5, A6, A7, A9> = TupleValues6<A1, A3, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_93")
public fun <A1, A3, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A3, A5, A6, A7, A10> = TupleValues6<A1, A3, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_94")
public fun <A1, A3, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A3, A5, A6, A8, A9> = TupleValues6<A1, A3, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_95")
public fun <A1, A3, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A3, A5, A6, A8, A10> = TupleValues6<A1, A3, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_96")
public fun <A1, A3, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A3, A5, A6, A9, A10> = TupleValues6<A1, A3, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_97")
public fun <A1, A3, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A3, A5, A7, A8, A9> = TupleValues6<A1, A3, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_98")
public fun <A1, A3, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A3, A5, A7, A8, A10> = TupleValues6<A1, A3, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_99")
public fun <A1, A3, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A3, A5, A7, A9, A10> = TupleValues6<A1, A3, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_100")
public fun <A1, A3, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A3, A5, A8, A9, A10> = TupleValues6<A1, A3, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_101")
public fun <A1, A3, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A3, A6, A7, A8, A9> = TupleValues6<A1, A3, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_102")
public fun <A1, A3, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A3, A6, A7, A8, A10> = TupleValues6<A1, A3, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_103")
public fun <A1, A3, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A3, A6, A7, A9, A10> = TupleValues6<A1, A3, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_104")
public fun <A1, A3, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A3, A6, A8, A9, A10> = TupleValues6<A1, A3, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_105")
public fun <A1, A3, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A1, A3, A7, A8, A9, A10> = TupleValues6<A1, A3, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_106")
public fun <A1, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A1, A4, A5, A6, A7, A8> = TupleValues6<A1, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_107")
public fun <A1, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A1, A4, A5, A6, A7, A9> = TupleValues6<A1, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_108")
public fun <A1, A4, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A1, A4, A5, A6, A7, A10> = TupleValues6<A1, A4, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_109")
public fun <A1, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A1, A4, A5, A6, A8, A9> = TupleValues6<A1, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_110")
public fun <A1, A4, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A1, A4, A5, A6, A8, A10> = TupleValues6<A1, A4, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_111")
public fun <A1, A4, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A1, A4, A5, A6, A9, A10> = TupleValues6<A1, A4, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_112")
public fun <A1, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A4, A5, A7, A8, A9> = TupleValues6<A1, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_113")
public fun <A1, A4, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A4, A5, A7, A8, A10> = TupleValues6<A1, A4, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_114")
public fun <A1, A4, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A4, A5, A7, A9, A10> = TupleValues6<A1, A4, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_115")
public fun <A1, A4, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A4, A5, A8, A9, A10> = TupleValues6<A1, A4, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_116")
public fun <A1, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A4, A6, A7, A8, A9> = TupleValues6<A1, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_117")
public fun <A1, A4, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A4, A6, A7, A8, A10> = TupleValues6<A1, A4, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_118")
public fun <A1, A4, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A4, A6, A7, A9, A10> = TupleValues6<A1, A4, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_119")
public fun <A1, A4, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A4, A6, A8, A9, A10> = TupleValues6<A1, A4, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_120")
public fun <A1, A4, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A1, A4, A7, A8, A9, A10> = TupleValues6<A1, A4, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_121")
public fun <A1, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A1, A5, A6, A7, A8, A9> = TupleValues6<A1, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_122")
public fun <A1, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A1, A5, A6, A7, A8, A10> = TupleValues6<A1, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_123")
public fun <A1, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A1, A5, A6, A7, A9, A10> = TupleValues6<A1, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_124")
public fun <A1, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A1, A5, A6, A8, A9, A10> = TupleValues6<A1, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_125")
public fun <A1, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A1, A5, A7, A8, A9, A10> = TupleValues6<A1, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_126")
public fun <A1, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues6<A1, A6, A7, A8, A9, A10> = TupleValues6<A1, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_127")
public fun <A2, A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues6<A2, A3, A4, A5, A6, A7> = TupleValues6<A2, A3, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_6_128")
public fun <A2, A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues6<A2, A3, A4, A5, A6, A8> = TupleValues6<A2, A3, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_129")
public fun <A2, A3, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues6<A2, A3, A4, A5, A6, A9> = TupleValues6<A2, A3, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_130")
public fun <A2, A3, A4, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues6<A2, A3, A4, A5, A6, A10> = TupleValues6<A2, A3, A4, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_131")
public fun <A2, A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues6<A2, A3, A4, A5, A7, A8> = TupleValues6<A2, A3, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_132")
public fun <A2, A3, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues6<A2, A3, A4, A5, A7, A9> = TupleValues6<A2, A3, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_133")
public fun <A2, A3, A4, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues6<A2, A3, A4, A5, A7, A10> = TupleValues6<A2, A3, A4, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_134")
public fun <A2, A3, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues6<A2, A3, A4, A5, A8, A9> = TupleValues6<A2, A3, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_135")
public fun <A2, A3, A4, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues6<A2, A3, A4, A5, A8, A10> = TupleValues6<A2, A3, A4, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_136")
public fun <A2, A3, A4, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues6<A2, A3, A4, A5, A9, A10> = TupleValues6<A2, A3, A4, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_137")
public fun <A2, A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A2, A3, A4, A6, A7, A8> = TupleValues6<A2, A3, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_138")
public fun <A2, A3, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A2, A3, A4, A6, A7, A9> = TupleValues6<A2, A3, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_139")
public fun <A2, A3, A4, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A2, A3, A4, A6, A7, A10> = TupleValues6<A2, A3, A4, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_140")
public fun <A2, A3, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A2, A3, A4, A6, A8, A9> = TupleValues6<A2, A3, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_141")
public fun <A2, A3, A4, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A2, A3, A4, A6, A8, A10> = TupleValues6<A2, A3, A4, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_142")
public fun <A2, A3, A4, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A2, A3, A4, A6, A9, A10> = TupleValues6<A2, A3, A4, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_143")
public fun <A2, A3, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A2, A3, A4, A7, A8, A9> = TupleValues6<A2, A3, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_144")
public fun <A2, A3, A4, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A2, A3, A4, A7, A8, A10> = TupleValues6<A2, A3, A4, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_145")
public fun <A2, A3, A4, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A2, A3, A4, A7, A9, A10> = TupleValues6<A2, A3, A4, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_146")
public fun <A2, A3, A4, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A2, A3, A4, A8, A9, A10> = TupleValues6<A2, A3, A4, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_147")
public fun <A2, A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A2, A3, A5, A6, A7, A8> = TupleValues6<A2, A3, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_148")
public fun <A2, A3, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A2, A3, A5, A6, A7, A9> = TupleValues6<A2, A3, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_149")
public fun <A2, A3, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A2, A3, A5, A6, A7, A10> = TupleValues6<A2, A3, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_150")
public fun <A2, A3, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A2, A3, A5, A6, A8, A9> = TupleValues6<A2, A3, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_151")
public fun <A2, A3, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A2, A3, A5, A6, A8, A10> = TupleValues6<A2, A3, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_152")
public fun <A2, A3, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A2, A3, A5, A6, A9, A10> = TupleValues6<A2, A3, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_153")
public fun <A2, A3, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A2, A3, A5, A7, A8, A9> = TupleValues6<A2, A3, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_154")
public fun <A2, A3, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A2, A3, A5, A7, A8, A10> = TupleValues6<A2, A3, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_155")
public fun <A2, A3, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A2, A3, A5, A7, A9, A10> = TupleValues6<A2, A3, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_156")
public fun <A2, A3, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A2, A3, A5, A8, A9, A10> = TupleValues6<A2, A3, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_157")
public fun <A2, A3, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A2, A3, A6, A7, A8, A9> = TupleValues6<A2, A3, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_158")
public fun <A2, A3, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A2, A3, A6, A7, A8, A10> = TupleValues6<A2, A3, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_159")
public fun <A2, A3, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A2, A3, A6, A7, A9, A10> = TupleValues6<A2, A3, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_160")
public fun <A2, A3, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A2, A3, A6, A8, A9, A10> = TupleValues6<A2, A3, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_161")
public fun <A2, A3, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A2, A3, A7, A8, A9, A10> = TupleValues6<A2, A3, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_162")
public fun <A2, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A2, A4, A5, A6, A7, A8> = TupleValues6<A2, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_163")
public fun <A2, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A2, A4, A5, A6, A7, A9> = TupleValues6<A2, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_164")
public fun <A2, A4, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A2, A4, A5, A6, A7, A10> = TupleValues6<A2, A4, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_165")
public fun <A2, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A2, A4, A5, A6, A8, A9> = TupleValues6<A2, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_166")
public fun <A2, A4, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A2, A4, A5, A6, A8, A10> = TupleValues6<A2, A4, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_167")
public fun <A2, A4, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A2, A4, A5, A6, A9, A10> = TupleValues6<A2, A4, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_168")
public fun <A2, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A2, A4, A5, A7, A8, A9> = TupleValues6<A2, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_169")
public fun <A2, A4, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A2, A4, A5, A7, A8, A10> = TupleValues6<A2, A4, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_170")
public fun <A2, A4, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A2, A4, A5, A7, A9, A10> = TupleValues6<A2, A4, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_171")
public fun <A2, A4, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A2, A4, A5, A8, A9, A10> = TupleValues6<A2, A4, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_172")
public fun <A2, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A2, A4, A6, A7, A8, A9> = TupleValues6<A2, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_173")
public fun <A2, A4, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A2, A4, A6, A7, A8, A10> = TupleValues6<A2, A4, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_174")
public fun <A2, A4, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A2, A4, A6, A7, A9, A10> = TupleValues6<A2, A4, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_175")
public fun <A2, A4, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A2, A4, A6, A8, A9, A10> = TupleValues6<A2, A4, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_176")
public fun <A2, A4, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A2, A4, A7, A8, A9, A10> = TupleValues6<A2, A4, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_177")
public fun <A2, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A2, A5, A6, A7, A8, A9> = TupleValues6<A2, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_178")
public fun <A2, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A2, A5, A6, A7, A8, A10> = TupleValues6<A2, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_179")
public fun <A2, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A2, A5, A6, A7, A9, A10> = TupleValues6<A2, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_180")
public fun <A2, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A2, A5, A6, A8, A9, A10> = TupleValues6<A2, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_181")
public fun <A2, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A2, A5, A7, A8, A9, A10> = TupleValues6<A2, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_182")
public fun <A2, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues6<A2, A6, A7, A8, A9, A10> = TupleValues6<A2, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_183")
public fun <A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues6<A3, A4, A5, A6, A7, A8> = TupleValues6<A3, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_6_184")
public fun <A3, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues6<A3, A4, A5, A6, A7, A9> = TupleValues6<A3, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_185")
public fun <A3, A4, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues6<A3, A4, A5, A6, A7, A10> = TupleValues6<A3, A4, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_186")
public fun <A3, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues6<A3, A4, A5, A6, A8, A9> = TupleValues6<A3, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_187")
public fun <A3, A4, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues6<A3, A4, A5, A6, A8, A10> = TupleValues6<A3, A4, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_188")
public fun <A3, A4, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues6<A3, A4, A5, A6, A9, A10> = TupleValues6<A3, A4, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_189")
public fun <A3, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues6<A3, A4, A5, A7, A8, A9> = TupleValues6<A3, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_190")
public fun <A3, A4, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues6<A3, A4, A5, A7, A8, A10> = TupleValues6<A3, A4, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_191")
public fun <A3, A4, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues6<A3, A4, A5, A7, A9, A10> = TupleValues6<A3, A4, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_192")
public fun <A3, A4, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues6<A3, A4, A5, A8, A9, A10> = TupleValues6<A3, A4, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_193")
public fun <A3, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A3, A4, A6, A7, A8, A9> = TupleValues6<A3, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_194")
public fun <A3, A4, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A3, A4, A6, A7, A8, A10> = TupleValues6<A3, A4, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_195")
public fun <A3, A4, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A3, A4, A6, A7, A9, A10> = TupleValues6<A3, A4, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_196")
public fun <A3, A4, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A3, A4, A6, A8, A9, A10> = TupleValues6<A3, A4, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_197")
public fun <A3, A4, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A3, A4, A7, A8, A9, A10> = TupleValues6<A3, A4, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_198")
public fun <A3, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A3, A5, A6, A7, A8, A9> = TupleValues6<A3, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_199")
public fun <A3, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A3, A5, A6, A7, A8, A10> = TupleValues6<A3, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_200")
public fun <A3, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A3, A5, A6, A7, A9, A10> = TupleValues6<A3, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_201")
public fun <A3, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A3, A5, A6, A8, A9, A10> = TupleValues6<A3, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_202")
public fun <A3, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A3, A5, A7, A8, A9, A10> = TupleValues6<A3, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_203")
public fun <A3, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues6<A3, A6, A7, A8, A9, A10> = TupleValues6<A3, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_204")
public fun <A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues6<A4, A5, A6, A7, A8, A9> = TupleValues6<A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_6_205")
public fun <A4, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues6<A4, A5, A6, A7, A8, A10> = TupleValues6<A4, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_6_206")
public fun <A4, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues6<A4, A5, A6, A7, A9, A10> = TupleValues6<A4, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_207")
public fun <A4, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues6<A4, A5, A6, A8, A9, A10> = TupleValues6<A4, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_208")
public fun <A4, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues6<A4, A5, A7, A8, A9, A10> = TupleValues6<A4, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_209")
public fun <A4, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues6<A4, A6, A7, A8, A9, A10> = TupleValues6<A4, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_6_210")
public fun <A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues6<A5, A6, A7, A8, A9, A10> = TupleValues6<A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_1")
public fun <A1, A2, A3, A4, A5, A6, A7> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>, Unit>): TupleValues7<A1, A2, A3, A4, A5, A6, A7> = TupleValues7<A1, A2, A3, A4, A5, A6, A7>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_10_7_2")
public fun <A1, A2, A3, A4, A5, A6, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>, Unit>): TupleValues7<A1, A2, A3, A4, A5, A6, A8> = TupleValues7<A1, A2, A3, A4, A5, A6, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_7_3")
public fun <A1, A2, A3, A4, A5, A6, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>, Unit>): TupleValues7<A1, A2, A3, A4, A5, A6, A9> = TupleValues7<A1, A2, A3, A4, A5, A6, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_4")
public fun <A1, A2, A3, A4, A5, A6, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, Unit>, A10>): TupleValues7<A1, A2, A3, A4, A5, A6, A10> = TupleValues7<A1, A2, A3, A4, A5, A6, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_5")
public fun <A1, A2, A3, A4, A5, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>, Unit>): TupleValues7<A1, A2, A3, A4, A5, A7, A8> = TupleValues7<A1, A2, A3, A4, A5, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_7_6")
public fun <A1, A2, A3, A4, A5, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>, Unit>): TupleValues7<A1, A2, A3, A4, A5, A7, A9> = TupleValues7<A1, A2, A3, A4, A5, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_7")
public fun <A1, A2, A3, A4, A5, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, Unit>, A10>): TupleValues7<A1, A2, A3, A4, A5, A7, A10> = TupleValues7<A1, A2, A3, A4, A5, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_8")
public fun <A1, A2, A3, A4, A5, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>, Unit>): TupleValues7<A1, A2, A3, A4, A5, A8, A9> = TupleValues7<A1, A2, A3, A4, A5, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_9")
public fun <A1, A2, A3, A4, A5, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, Unit>, A10>): TupleValues7<A1, A2, A3, A4, A5, A8, A10> = TupleValues7<A1, A2, A3, A4, A5, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_10")
public fun <A1, A2, A3, A4, A5, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, Unit>, A9>, A10>): TupleValues7<A1, A2, A3, A4, A5, A9, A10> = TupleValues7<A1, A2, A3, A4, A5, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_11")
public fun <A1, A2, A3, A4, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>, Unit>): TupleValues7<A1, A2, A3, A4, A6, A7, A8> = TupleValues7<A1, A2, A3, A4, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_7_12")
public fun <A1, A2, A3, A4, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>, Unit>): TupleValues7<A1, A2, A3, A4, A6, A7, A9> = TupleValues7<A1, A2, A3, A4, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_13")
public fun <A1, A2, A3, A4, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, Unit>, A10>): TupleValues7<A1, A2, A3, A4, A6, A7, A10> = TupleValues7<A1, A2, A3, A4, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_14")
public fun <A1, A2, A3, A4, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>, Unit>): TupleValues7<A1, A2, A3, A4, A6, A8, A9> = TupleValues7<A1, A2, A3, A4, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_15")
public fun <A1, A2, A3, A4, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, Unit>, A10>): TupleValues7<A1, A2, A3, A4, A6, A8, A10> = TupleValues7<A1, A2, A3, A4, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_16")
public fun <A1, A2, A3, A4, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, Unit>, A9>, A10>): TupleValues7<A1, A2, A3, A4, A6, A9, A10> = TupleValues7<A1, A2, A3, A4, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_17")
public fun <A1, A2, A3, A4, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A2, A3, A4, A7, A8, A9> = TupleValues7<A1, A2, A3, A4, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_18")
public fun <A1, A2, A3, A4, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A2, A3, A4, A7, A8, A10> = TupleValues7<A1, A2, A3, A4, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_19")
public fun <A1, A2, A3, A4, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A2, A3, A4, A7, A9, A10> = TupleValues7<A1, A2, A3, A4, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_20")
public fun <A1, A2, A3, A4, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A2, A3, A4, A8, A9, A10> = TupleValues7<A1, A2, A3, A4, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_21")
public fun <A1, A2, A3, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues7<A1, A2, A3, A5, A6, A7, A8> = TupleValues7<A1, A2, A3, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_7_22")
public fun <A1, A2, A3, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues7<A1, A2, A3, A5, A6, A7, A9> = TupleValues7<A1, A2, A3, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_23")
public fun <A1, A2, A3, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues7<A1, A2, A3, A5, A6, A7, A10> = TupleValues7<A1, A2, A3, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_24")
public fun <A1, A2, A3, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues7<A1, A2, A3, A5, A6, A8, A9> = TupleValues7<A1, A2, A3, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_25")
public fun <A1, A2, A3, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues7<A1, A2, A3, A5, A6, A8, A10> = TupleValues7<A1, A2, A3, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_26")
public fun <A1, A2, A3, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues7<A1, A2, A3, A5, A6, A9, A10> = TupleValues7<A1, A2, A3, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_27")
public fun <A1, A2, A3, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A2, A3, A5, A7, A8, A9> = TupleValues7<A1, A2, A3, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_28")
public fun <A1, A2, A3, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A2, A3, A5, A7, A8, A10> = TupleValues7<A1, A2, A3, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_29")
public fun <A1, A2, A3, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A2, A3, A5, A7, A9, A10> = TupleValues7<A1, A2, A3, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_30")
public fun <A1, A2, A3, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A2, A3, A5, A8, A9, A10> = TupleValues7<A1, A2, A3, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_31")
public fun <A1, A2, A3, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A2, A3, A6, A7, A8, A9> = TupleValues7<A1, A2, A3, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_32")
public fun <A1, A2, A3, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A2, A3, A6, A7, A8, A10> = TupleValues7<A1, A2, A3, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_33")
public fun <A1, A2, A3, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A2, A3, A6, A7, A9, A10> = TupleValues7<A1, A2, A3, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_34")
public fun <A1, A2, A3, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A2, A3, A6, A8, A9, A10> = TupleValues7<A1, A2, A3, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_35")
public fun <A1, A2, A3, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A1, A2, A3, A7, A8, A9, A10> = TupleValues7<A1, A2, A3, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_36")
public fun <A1, A2, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues7<A1, A2, A4, A5, A6, A7, A8> = TupleValues7<A1, A2, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_7_37")
public fun <A1, A2, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues7<A1, A2, A4, A5, A6, A7, A9> = TupleValues7<A1, A2, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_38")
public fun <A1, A2, A4, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues7<A1, A2, A4, A5, A6, A7, A10> = TupleValues7<A1, A2, A4, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_39")
public fun <A1, A2, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues7<A1, A2, A4, A5, A6, A8, A9> = TupleValues7<A1, A2, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_40")
public fun <A1, A2, A4, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues7<A1, A2, A4, A5, A6, A8, A10> = TupleValues7<A1, A2, A4, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_41")
public fun <A1, A2, A4, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues7<A1, A2, A4, A5, A6, A9, A10> = TupleValues7<A1, A2, A4, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_42")
public fun <A1, A2, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A2, A4, A5, A7, A8, A9> = TupleValues7<A1, A2, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_43")
public fun <A1, A2, A4, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A2, A4, A5, A7, A8, A10> = TupleValues7<A1, A2, A4, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_44")
public fun <A1, A2, A4, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A2, A4, A5, A7, A9, A10> = TupleValues7<A1, A2, A4, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_45")
public fun <A1, A2, A4, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A2, A4, A5, A8, A9, A10> = TupleValues7<A1, A2, A4, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_46")
public fun <A1, A2, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A2, A4, A6, A7, A8, A9> = TupleValues7<A1, A2, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_47")
public fun <A1, A2, A4, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A2, A4, A6, A7, A8, A10> = TupleValues7<A1, A2, A4, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_48")
public fun <A1, A2, A4, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A2, A4, A6, A7, A9, A10> = TupleValues7<A1, A2, A4, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_49")
public fun <A1, A2, A4, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A2, A4, A6, A8, A9, A10> = TupleValues7<A1, A2, A4, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_50")
public fun <A1, A2, A4, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A1, A2, A4, A7, A8, A9, A10> = TupleValues7<A1, A2, A4, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_51")
public fun <A1, A2, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A2, A5, A6, A7, A8, A9> = TupleValues7<A1, A2, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_52")
public fun <A1, A2, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A2, A5, A6, A7, A8, A10> = TupleValues7<A1, A2, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_53")
public fun <A1, A2, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A2, A5, A6, A7, A9, A10> = TupleValues7<A1, A2, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_54")
public fun <A1, A2, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A2, A5, A6, A8, A9, A10> = TupleValues7<A1, A2, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_55")
public fun <A1, A2, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A1, A2, A5, A7, A8, A9, A10> = TupleValues7<A1, A2, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_56")
public fun <A1, A2, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A1, A2, A6, A7, A8, A9, A10> = TupleValues7<A1, A2, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_57")
public fun <A1, A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues7<A1, A3, A4, A5, A6, A7, A8> = TupleValues7<A1, A3, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_7_58")
public fun <A1, A3, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues7<A1, A3, A4, A5, A6, A7, A9> = TupleValues7<A1, A3, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_59")
public fun <A1, A3, A4, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues7<A1, A3, A4, A5, A6, A7, A10> = TupleValues7<A1, A3, A4, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_60")
public fun <A1, A3, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues7<A1, A3, A4, A5, A6, A8, A9> = TupleValues7<A1, A3, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_61")
public fun <A1, A3, A4, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues7<A1, A3, A4, A5, A6, A8, A10> = TupleValues7<A1, A3, A4, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_62")
public fun <A1, A3, A4, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues7<A1, A3, A4, A5, A6, A9, A10> = TupleValues7<A1, A3, A4, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_63")
public fun <A1, A3, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A3, A4, A5, A7, A8, A9> = TupleValues7<A1, A3, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_64")
public fun <A1, A3, A4, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A3, A4, A5, A7, A8, A10> = TupleValues7<A1, A3, A4, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_65")
public fun <A1, A3, A4, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A3, A4, A5, A7, A9, A10> = TupleValues7<A1, A3, A4, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_66")
public fun <A1, A3, A4, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A3, A4, A5, A8, A9, A10> = TupleValues7<A1, A3, A4, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_67")
public fun <A1, A3, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A3, A4, A6, A7, A8, A9> = TupleValues7<A1, A3, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_68")
public fun <A1, A3, A4, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A3, A4, A6, A7, A8, A10> = TupleValues7<A1, A3, A4, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_69")
public fun <A1, A3, A4, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A3, A4, A6, A7, A9, A10> = TupleValues7<A1, A3, A4, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_70")
public fun <A1, A3, A4, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A3, A4, A6, A8, A9, A10> = TupleValues7<A1, A3, A4, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_71")
public fun <A1, A3, A4, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A1, A3, A4, A7, A8, A9, A10> = TupleValues7<A1, A3, A4, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_72")
public fun <A1, A3, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A3, A5, A6, A7, A8, A9> = TupleValues7<A1, A3, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_73")
public fun <A1, A3, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A3, A5, A6, A7, A8, A10> = TupleValues7<A1, A3, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_74")
public fun <A1, A3, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A3, A5, A6, A7, A9, A10> = TupleValues7<A1, A3, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_75")
public fun <A1, A3, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A3, A5, A6, A8, A9, A10> = TupleValues7<A1, A3, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_76")
public fun <A1, A3, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A1, A3, A5, A7, A8, A9, A10> = TupleValues7<A1, A3, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_77")
public fun <A1, A3, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A1, A3, A6, A7, A8, A9, A10> = TupleValues7<A1, A3, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_78")
public fun <A1, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A1, A4, A5, A6, A7, A8, A9> = TupleValues7<A1, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_79")
public fun <A1, A4, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A1, A4, A5, A6, A7, A8, A10> = TupleValues7<A1, A4, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_80")
public fun <A1, A4, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A1, A4, A5, A6, A7, A9, A10> = TupleValues7<A1, A4, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_81")
public fun <A1, A4, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A1, A4, A5, A6, A8, A9, A10> = TupleValues7<A1, A4, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_82")
public fun <A1, A4, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A1, A4, A5, A7, A8, A9, A10> = TupleValues7<A1, A4, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_83")
public fun <A1, A4, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A1, A4, A6, A7, A8, A9, A10> = TupleValues7<A1, A4, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_84")
public fun <A1, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A1, A5, A6, A7, A8, A9, A10> = TupleValues7<A1, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_85")
public fun <A2, A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues7<A2, A3, A4, A5, A6, A7, A8> = TupleValues7<A2, A3, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_7_86")
public fun <A2, A3, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues7<A2, A3, A4, A5, A6, A7, A9> = TupleValues7<A2, A3, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_87")
public fun <A2, A3, A4, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues7<A2, A3, A4, A5, A6, A7, A10> = TupleValues7<A2, A3, A4, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_88")
public fun <A2, A3, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues7<A2, A3, A4, A5, A6, A8, A9> = TupleValues7<A2, A3, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_89")
public fun <A2, A3, A4, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues7<A2, A3, A4, A5, A6, A8, A10> = TupleValues7<A2, A3, A4, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_90")
public fun <A2, A3, A4, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues7<A2, A3, A4, A5, A6, A9, A10> = TupleValues7<A2, A3, A4, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_91")
public fun <A2, A3, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues7<A2, A3, A4, A5, A7, A8, A9> = TupleValues7<A2, A3, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_92")
public fun <A2, A3, A4, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues7<A2, A3, A4, A5, A7, A8, A10> = TupleValues7<A2, A3, A4, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_93")
public fun <A2, A3, A4, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues7<A2, A3, A4, A5, A7, A9, A10> = TupleValues7<A2, A3, A4, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_94")
public fun <A2, A3, A4, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues7<A2, A3, A4, A5, A8, A9, A10> = TupleValues7<A2, A3, A4, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_95")
public fun <A2, A3, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A2, A3, A4, A6, A7, A8, A9> = TupleValues7<A2, A3, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_96")
public fun <A2, A3, A4, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A2, A3, A4, A6, A7, A8, A10> = TupleValues7<A2, A3, A4, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_97")
public fun <A2, A3, A4, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A2, A3, A4, A6, A7, A9, A10> = TupleValues7<A2, A3, A4, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_98")
public fun <A2, A3, A4, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A2, A3, A4, A6, A8, A9, A10> = TupleValues7<A2, A3, A4, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_99")
public fun <A2, A3, A4, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A2, A3, A4, A7, A8, A9, A10> = TupleValues7<A2, A3, A4, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_100")
public fun <A2, A3, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A2, A3, A5, A6, A7, A8, A9> = TupleValues7<A2, A3, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_101")
public fun <A2, A3, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A2, A3, A5, A6, A7, A8, A10> = TupleValues7<A2, A3, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_102")
public fun <A2, A3, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A2, A3, A5, A6, A7, A9, A10> = TupleValues7<A2, A3, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_103")
public fun <A2, A3, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A2, A3, A5, A6, A8, A9, A10> = TupleValues7<A2, A3, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_104")
public fun <A2, A3, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A2, A3, A5, A7, A8, A9, A10> = TupleValues7<A2, A3, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_105")
public fun <A2, A3, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A2, A3, A6, A7, A8, A9, A10> = TupleValues7<A2, A3, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_106")
public fun <A2, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A2, A4, A5, A6, A7, A8, A9> = TupleValues7<A2, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_107")
public fun <A2, A4, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A2, A4, A5, A6, A7, A8, A10> = TupleValues7<A2, A4, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_108")
public fun <A2, A4, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A2, A4, A5, A6, A7, A9, A10> = TupleValues7<A2, A4, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_109")
public fun <A2, A4, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A2, A4, A5, A6, A8, A9, A10> = TupleValues7<A2, A4, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_110")
public fun <A2, A4, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A2, A4, A5, A7, A8, A9, A10> = TupleValues7<A2, A4, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_111")
public fun <A2, A4, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A2, A4, A6, A7, A8, A9, A10> = TupleValues7<A2, A4, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_112")
public fun <A2, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A2, A5, A6, A7, A8, A9, A10> = TupleValues7<A2, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_113")
public fun <A3, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues7<A3, A4, A5, A6, A7, A8, A9> = TupleValues7<A3, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_7_114")
public fun <A3, A4, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues7<A3, A4, A5, A6, A7, A8, A10> = TupleValues7<A3, A4, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_7_115")
public fun <A3, A4, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues7<A3, A4, A5, A6, A7, A9, A10> = TupleValues7<A3, A4, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_116")
public fun <A3, A4, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues7<A3, A4, A5, A6, A8, A9, A10> = TupleValues7<A3, A4, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_117")
public fun <A3, A4, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues7<A3, A4, A5, A7, A8, A9, A10> = TupleValues7<A3, A4, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_118")
public fun <A3, A4, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A3, A4, A6, A7, A8, A9, A10> = TupleValues7<A3, A4, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_119")
public fun <A3, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A3, A5, A6, A7, A8, A9, A10> = TupleValues7<A3, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_7_120")
public fun <A4, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues7<A4, A5, A6, A7, A8, A9, A10> = TupleValues7<A4, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_1")
public fun <A1, A2, A3, A4, A5, A6, A7, A8> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>, Unit>): TupleValues8<A1, A2, A3, A4, A5, A6, A7, A8> = TupleValues8<A1, A2, A3, A4, A5, A6, A7, A8>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_10_8_2")
public fun <A1, A2, A3, A4, A5, A6, A7, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>, Unit>): TupleValues8<A1, A2, A3, A4, A5, A6, A7, A9> = TupleValues8<A1, A2, A3, A4, A5, A6, A7, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_8_3")
public fun <A1, A2, A3, A4, A5, A6, A7, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, Unit>, A10>): TupleValues8<A1, A2, A3, A4, A5, A6, A7, A10> = TupleValues8<A1, A2, A3, A4, A5, A6, A7, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_10_8_4")
public fun <A1, A2, A3, A4, A5, A6, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>, Unit>): TupleValues8<A1, A2, A3, A4, A5, A6, A8, A9> = TupleValues8<A1, A2, A3, A4, A5, A6, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_8_5")
public fun <A1, A2, A3, A4, A5, A6, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, Unit>, A10>): TupleValues8<A1, A2, A3, A4, A5, A6, A8, A10> = TupleValues8<A1, A2, A3, A4, A5, A6, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_8_6")
public fun <A1, A2, A3, A4, A5, A6, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, Unit>, A9>, A10>): TupleValues8<A1, A2, A3, A4, A5, A6, A9, A10> = TupleValues8<A1, A2, A3, A4, A5, A6, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_7")
public fun <A1, A2, A3, A4, A5, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>, Unit>): TupleValues8<A1, A2, A3, A4, A5, A7, A8, A9> = TupleValues8<A1, A2, A3, A4, A5, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_8_8")
public fun <A1, A2, A3, A4, A5, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, Unit>, A10>): TupleValues8<A1, A2, A3, A4, A5, A7, A8, A10> = TupleValues8<A1, A2, A3, A4, A5, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_8_9")
public fun <A1, A2, A3, A4, A5, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, Unit>, A9>, A10>): TupleValues8<A1, A2, A3, A4, A5, A7, A9, A10> = TupleValues8<A1, A2, A3, A4, A5, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_10")
public fun <A1, A2, A3, A4, A5, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, Unit>, A8>, A9>, A10>): TupleValues8<A1, A2, A3, A4, A5, A8, A9, A10> = TupleValues8<A1, A2, A3, A4, A5, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_11")
public fun <A1, A2, A3, A4, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>, Unit>): TupleValues8<A1, A2, A3, A4, A6, A7, A8, A9> = TupleValues8<A1, A2, A3, A4, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_8_12")
public fun <A1, A2, A3, A4, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, Unit>, A10>): TupleValues8<A1, A2, A3, A4, A6, A7, A8, A10> = TupleValues8<A1, A2, A3, A4, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_8_13")
public fun <A1, A2, A3, A4, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, Unit>, A9>, A10>): TupleValues8<A1, A2, A3, A4, A6, A7, A9, A10> = TupleValues8<A1, A2, A3, A4, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_14")
public fun <A1, A2, A3, A4, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, Unit>, A8>, A9>, A10>): TupleValues8<A1, A2, A3, A4, A6, A8, A9, A10> = TupleValues8<A1, A2, A3, A4, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_15")
public fun <A1, A2, A3, A4, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>, A7>, A8>, A9>, A10>): TupleValues8<A1, A2, A3, A4, A7, A8, A9, A10> = TupleValues8<A1, A2, A3, A4, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_16")
public fun <A1, A2, A3, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues8<A1, A2, A3, A5, A6, A7, A8, A9> = TupleValues8<A1, A2, A3, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_8_17")
public fun <A1, A2, A3, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues8<A1, A2, A3, A5, A6, A7, A8, A10> = TupleValues8<A1, A2, A3, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_8_18")
public fun <A1, A2, A3, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues8<A1, A2, A3, A5, A6, A7, A9, A10> = TupleValues8<A1, A2, A3, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_19")
public fun <A1, A2, A3, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues8<A1, A2, A3, A5, A6, A8, A9, A10> = TupleValues8<A1, A2, A3, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_20")
public fun <A1, A2, A3, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues8<A1, A2, A3, A5, A7, A8, A9, A10> = TupleValues8<A1, A2, A3, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_21")
public fun <A1, A2, A3, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A1, A2, A3, A6, A7, A8, A9, A10> = TupleValues8<A1, A2, A3, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_22")
public fun <A1, A2, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues8<A1, A2, A4, A5, A6, A7, A8, A9> = TupleValues8<A1, A2, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_8_23")
public fun <A1, A2, A4, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues8<A1, A2, A4, A5, A6, A7, A8, A10> = TupleValues8<A1, A2, A4, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_8_24")
public fun <A1, A2, A4, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues8<A1, A2, A4, A5, A6, A7, A9, A10> = TupleValues8<A1, A2, A4, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_25")
public fun <A1, A2, A4, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues8<A1, A2, A4, A5, A6, A8, A9, A10> = TupleValues8<A1, A2, A4, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_26")
public fun <A1, A2, A4, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues8<A1, A2, A4, A5, A7, A8, A9, A10> = TupleValues8<A1, A2, A4, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_27")
public fun <A1, A2, A4, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A1, A2, A4, A6, A7, A8, A9, A10> = TupleValues8<A1, A2, A4, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_28")
public fun <A1, A2, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A1, A2, A5, A6, A7, A8, A9, A10> = TupleValues8<A1, A2, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_29")
public fun <A1, A3, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues8<A1, A3, A4, A5, A6, A7, A8, A9> = TupleValues8<A1, A3, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_8_30")
public fun <A1, A3, A4, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues8<A1, A3, A4, A5, A6, A7, A8, A10> = TupleValues8<A1, A3, A4, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_8_31")
public fun <A1, A3, A4, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues8<A1, A3, A4, A5, A6, A7, A9, A10> = TupleValues8<A1, A3, A4, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_32")
public fun <A1, A3, A4, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues8<A1, A3, A4, A5, A6, A8, A9, A10> = TupleValues8<A1, A3, A4, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_33")
public fun <A1, A3, A4, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues8<A1, A3, A4, A5, A7, A8, A9, A10> = TupleValues8<A1, A3, A4, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_34")
public fun <A1, A3, A4, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A1, A3, A4, A6, A7, A8, A9, A10> = TupleValues8<A1, A3, A4, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_35")
public fun <A1, A3, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A1, A3, A5, A6, A7, A8, A9, A10> = TupleValues8<A1, A3, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_36")
public fun <A1, A4, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A1, A4, A5, A6, A7, A8, A9, A10> = TupleValues8<A1, A4, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_37")
public fun <A2, A3, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues8<A2, A3, A4, A5, A6, A7, A8, A9> = TupleValues8<A2, A3, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_8_38")
public fun <A2, A3, A4, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues8<A2, A3, A4, A5, A6, A7, A8, A10> = TupleValues8<A2, A3, A4, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_8_39")
public fun <A2, A3, A4, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues8<A2, A3, A4, A5, A6, A7, A9, A10> = TupleValues8<A2, A3, A4, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_40")
public fun <A2, A3, A4, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues8<A2, A3, A4, A5, A6, A8, A9, A10> = TupleValues8<A2, A3, A4, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_41")
public fun <A2, A3, A4, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues8<A2, A3, A4, A5, A7, A8, A9, A10> = TupleValues8<A2, A3, A4, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_42")
public fun <A2, A3, A4, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A2, A3, A4, A6, A7, A8, A9, A10> = TupleValues8<A2, A3, A4, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_43")
public fun <A2, A3, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A2, A3, A5, A6, A7, A8, A9, A10> = TupleValues8<A2, A3, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_44")
public fun <A2, A4, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A2, A4, A5, A6, A7, A8, A9, A10> = TupleValues8<A2, A4, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_8_45")
public fun <A3, A4, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues8<A3, A4, A5, A6, A7, A8, A9, A10> = TupleValues8<A3, A4, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_9_1")
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, A8>, A9>, Unit>): TupleValues9<A1, A2, A3, A4, A5, A6, A7, A8, A9> = TupleValues9<A1, A2, A3, A4, A5, A6, A7, A8, A9>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_10_9_2")
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, A8>, Unit>, A10>): TupleValues9<A1, A2, A3, A4, A5, A6, A7, A8, A10> = TupleValues9<A1, A2, A3, A4, A5, A6, A7, A8, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_10_9_3")
public fun <A1, A2, A3, A4, A5, A6, A7, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, Unit>, A9>, A10>): TupleValues9<A1, A2, A3, A4, A5, A6, A7, A9, A10> = TupleValues9<A1, A2, A3, A4, A5, A6, A7, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_9_4")
public fun <A1, A2, A3, A4, A5, A6, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, Unit>, A8>, A9>, A10>): TupleValues9<A1, A2, A3, A4, A5, A6, A8, A9, A10> = TupleValues9<A1, A2, A3, A4, A5, A6, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_9_5")
public fun <A1, A2, A3, A4, A5, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>, A7>, A8>, A9>, A10>): TupleValues9<A1, A2, A3, A4, A5, A7, A8, A9, A10> = TupleValues9<A1, A2, A3, A4, A5, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_9_6")
public fun <A1, A2, A3, A4, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>, A7>, A8>, A9>, A10>): TupleValues9<A1, A2, A3, A4, A6, A7, A8, A9, A10> = TupleValues9<A1, A2, A3, A4, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_9_7")
public fun <A1, A2, A3, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues9<A1, A2, A3, A5, A6, A7, A8, A9, A10> = TupleValues9<A1, A2, A3, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_9_8")
public fun <A1, A2, A4, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues9<A1, A2, A4, A5, A6, A7, A8, A9, A10> = TupleValues9<A1, A2, A4, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_9_9")
public fun <A1, A3, A4, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues9<A1, A3, A4, A5, A6, A7, A8, A9, A10> = TupleValues9<A1, A3, A4, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_9_10")
public fun <A2, A3, A4, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues9<A2, A3, A4, A5, A6, A7, A8, A9, A10> = TupleValues9<A2, A3, A4, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_10_10_1")
public fun <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>, A7>, A8>, A9>, A10>): TupleValues10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> = TupleValues10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>(tuples.first.first.first.first.first.first.first.first.first, tuples.first.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.first.second, tuples.first.first.first.first.first.first.second, tuples.first.first.first.first.first.second, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)
