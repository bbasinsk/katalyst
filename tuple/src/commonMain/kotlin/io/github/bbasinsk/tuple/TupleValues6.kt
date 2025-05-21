package io.github.bbasinsk.tuple

import kotlin.Pair
import kotlin.Unit
import kotlin.jvm.JvmName

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

@JvmName("tupleValues_6_1_1")
public fun <A1> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A1> = TupleValues1<A1>(tuples.first.first.first.first.first)

@JvmName("tupleValues_6_1_2")
public fun <A2> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, Unit>): TupleValues1<A2> = TupleValues1<A2>(tuples.first.first.first.first.second)

@JvmName("tupleValues_6_1_3")
public fun <A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, Unit>): TupleValues1<A3> = TupleValues1<A3>(tuples.first.first.first.second)

@JvmName("tupleValues_6_1_4")
public fun <A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, Unit>): TupleValues1<A4> = TupleValues1<A4>(tuples.first.first.second)

@JvmName("tupleValues_6_1_5")
public fun <A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, Unit>): TupleValues1<A5> = TupleValues1<A5>(tuples.first.second)

@JvmName("tupleValues_6_1_6")
public fun <A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, Unit>, A6>): TupleValues1<A6> = TupleValues1<A6>(tuples.second)

@JvmName("tupleValues_6_2_1")
public fun <A1, A2> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, Unit>): TupleValues2<A1, A2> = TupleValues2<A1, A2>(tuples.first.first.first.first.first, tuples.first.first.first.first.second)

@JvmName("tupleValues_6_2_2")
public fun <A1, A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, Unit>): TupleValues2<A1, A3> = TupleValues2<A1, A3>(tuples.first.first.first.first.first, tuples.first.first.first.second)

@JvmName("tupleValues_6_2_3")
public fun <A1, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, Unit>): TupleValues2<A1, A4> = TupleValues2<A1, A4>(tuples.first.first.first.first.first, tuples.first.first.second)

@JvmName("tupleValues_6_2_4")
public fun <A1, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, Unit>): TupleValues2<A1, A5> = TupleValues2<A1, A5>(tuples.first.first.first.first.first, tuples.first.second)

@JvmName("tupleValues_6_2_5")
public fun <A1, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, Unit>, A6>): TupleValues2<A1, A6> = TupleValues2<A1, A6>(tuples.first.first.first.first.first, tuples.second)

@JvmName("tupleValues_6_2_6")
public fun <A2, A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, Unit>): TupleValues2<A2, A3> = TupleValues2<A2, A3>(tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_6_2_7")
public fun <A2, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, Unit>): TupleValues2<A2, A4> = TupleValues2<A2, A4>(tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_6_2_8")
public fun <A2, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, Unit>): TupleValues2<A2, A5> = TupleValues2<A2, A5>(tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_2_9")
public fun <A2, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, Unit>, A6>): TupleValues2<A2, A6> = TupleValues2<A2, A6>(tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_6_2_10")
public fun <A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, Unit>): TupleValues2<A3, A4> = TupleValues2<A3, A4>(tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_6_2_11")
public fun <A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, Unit>): TupleValues2<A3, A5> = TupleValues2<A3, A5>(tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_2_12")
public fun <A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, Unit>, A6>): TupleValues2<A3, A6> = TupleValues2<A3, A6>(tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_6_2_13")
public fun <A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, Unit>): TupleValues2<A4, A5> = TupleValues2<A4, A5>(tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_2_14")
public fun <A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, Unit>, A6>): TupleValues2<A4, A6> = TupleValues2<A4, A6>(tuples.first.first.second, tuples.second)

@JvmName("tupleValues_6_2_15")
public fun <A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, Unit>, A5>, A6>): TupleValues2<A5, A6> = TupleValues2<A5, A6>(tuples.first.second, tuples.second)

@JvmName("tupleValues_6_3_1")
public fun <A1, A2, A3> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, Unit>): TupleValues3<A1, A2, A3> = TupleValues3<A1, A2, A3>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second)

@JvmName("tupleValues_6_3_2")
public fun <A1, A2, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, Unit>): TupleValues3<A1, A2, A4> = TupleValues3<A1, A2, A4>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_6_3_3")
public fun <A1, A2, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, Unit>): TupleValues3<A1, A2, A5> = TupleValues3<A1, A2, A5>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_3_4")
public fun <A1, A2, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, Unit>, A6>): TupleValues3<A1, A2, A6> = TupleValues3<A1, A2, A6>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.second)

@JvmName("tupleValues_6_3_5")
public fun <A1, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, Unit>): TupleValues3<A1, A3, A4> = TupleValues3<A1, A3, A4>(tuples.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_6_3_6")
public fun <A1, A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, Unit>): TupleValues3<A1, A3, A5> = TupleValues3<A1, A3, A5>(tuples.first.first.first.first.first, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_3_7")
public fun <A1, A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, Unit>, A6>): TupleValues3<A1, A3, A6> = TupleValues3<A1, A3, A6>(tuples.first.first.first.first.first, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_6_3_8")
public fun <A1, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, Unit>): TupleValues3<A1, A4, A5> = TupleValues3<A1, A4, A5>(tuples.first.first.first.first.first, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_3_9")
public fun <A1, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, Unit>, A6>): TupleValues3<A1, A4, A6> = TupleValues3<A1, A4, A6>(tuples.first.first.first.first.first, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_6_3_10")
public fun <A1, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, Unit>, A5>, A6>): TupleValues3<A1, A5, A6> = TupleValues3<A1, A5, A6>(tuples.first.first.first.first.first, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_3_11")
public fun <A2, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, Unit>): TupleValues3<A2, A3, A4> = TupleValues3<A2, A3, A4>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_6_3_12")
public fun <A2, A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, Unit>): TupleValues3<A2, A3, A5> = TupleValues3<A2, A3, A5>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_3_13")
public fun <A2, A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, Unit>, A6>): TupleValues3<A2, A3, A6> = TupleValues3<A2, A3, A6>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_6_3_14")
public fun <A2, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, Unit>): TupleValues3<A2, A4, A5> = TupleValues3<A2, A4, A5>(tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_3_15")
public fun <A2, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, Unit>, A6>): TupleValues3<A2, A4, A6> = TupleValues3<A2, A4, A6>(tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_6_3_16")
public fun <A2, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, Unit>, A5>, A6>): TupleValues3<A2, A5, A6> = TupleValues3<A2, A5, A6>(tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_3_17")
public fun <A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, Unit>): TupleValues3<A3, A4, A5> = TupleValues3<A3, A4, A5>(tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_3_18")
public fun <A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, Unit>, A6>): TupleValues3<A3, A4, A6> = TupleValues3<A3, A4, A6>(tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_6_3_19")
public fun <A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, Unit>, A5>, A6>): TupleValues3<A3, A5, A6> = TupleValues3<A3, A5, A6>(tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_3_20")
public fun <A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, Unit>, A4>, A5>, A6>): TupleValues3<A4, A5, A6> = TupleValues3<A4, A5, A6>(tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_4_1")
public fun <A1, A2, A3, A4> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, Unit>): TupleValues4<A1, A2, A3, A4> = TupleValues4<A1, A2, A3, A4>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second)

@JvmName("tupleValues_6_4_2")
public fun <A1, A2, A3, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, Unit>): TupleValues4<A1, A2, A3, A5> = TupleValues4<A1, A2, A3, A5>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_4_3")
public fun <A1, A2, A3, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, Unit>, A6>): TupleValues4<A1, A2, A3, A6> = TupleValues4<A1, A2, A3, A6>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.second)

@JvmName("tupleValues_6_4_4")
public fun <A1, A2, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, Unit>): TupleValues4<A1, A2, A4, A5> = TupleValues4<A1, A2, A4, A5>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_4_5")
public fun <A1, A2, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, Unit>, A6>): TupleValues4<A1, A2, A4, A6> = TupleValues4<A1, A2, A4, A6>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_6_4_6")
public fun <A1, A2, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, Unit>, A5>, A6>): TupleValues4<A1, A2, A5, A6> = TupleValues4<A1, A2, A5, A6>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_4_7")
public fun <A1, A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, Unit>): TupleValues4<A1, A3, A4, A5> = TupleValues4<A1, A3, A4, A5>(tuples.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_4_8")
public fun <A1, A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, Unit>, A6>): TupleValues4<A1, A3, A4, A6> = TupleValues4<A1, A3, A4, A6>(tuples.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_6_4_9")
public fun <A1, A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, Unit>, A5>, A6>): TupleValues4<A1, A3, A5, A6> = TupleValues4<A1, A3, A5, A6>(tuples.first.first.first.first.first, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_4_10")
public fun <A1, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, Unit>, A4>, A5>, A6>): TupleValues4<A1, A4, A5, A6> = TupleValues4<A1, A4, A5, A6>(tuples.first.first.first.first.first, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_4_11")
public fun <A2, A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, Unit>): TupleValues4<A2, A3, A4, A5> = TupleValues4<A2, A3, A4, A5>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_4_12")
public fun <A2, A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, Unit>, A6>): TupleValues4<A2, A3, A4, A6> = TupleValues4<A2, A3, A4, A6>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_6_4_13")
public fun <A2, A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, Unit>, A5>, A6>): TupleValues4<A2, A3, A5, A6> = TupleValues4<A2, A3, A5, A6>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_4_14")
public fun <A2, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, Unit>, A4>, A5>, A6>): TupleValues4<A2, A4, A5, A6> = TupleValues4<A2, A4, A5, A6>(tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_4_15")
public fun <A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, Unit>, A3>, A4>, A5>, A6>): TupleValues4<A3, A4, A5, A6> = TupleValues4<A3, A4, A5, A6>(tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_5_1")
public fun <A1, A2, A3, A4, A5> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, Unit>): TupleValues5<A1, A2, A3, A4, A5> = TupleValues5<A1, A2, A3, A4, A5>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second)

@JvmName("tupleValues_6_5_2")
public fun <A1, A2, A3, A4, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, Unit>, A6>): TupleValues5<A1, A2, A3, A4, A6> = TupleValues5<A1, A2, A3, A4, A6>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.second)

@JvmName("tupleValues_6_5_3")
public fun <A1, A2, A3, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, Unit>, A5>, A6>): TupleValues5<A1, A2, A3, A5, A6> = TupleValues5<A1, A2, A3, A5, A6>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_5_4")
public fun <A1, A2, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, Unit>, A4>, A5>, A6>): TupleValues5<A1, A2, A4, A5, A6> = TupleValues5<A1, A2, A4, A5, A6>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_5_5")
public fun <A1, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, Unit>, A3>, A4>, A5>, A6>): TupleValues5<A1, A3, A4, A5, A6> = TupleValues5<A1, A3, A4, A5, A6>(tuples.first.first.first.first.first, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_5_6")
public fun <A2, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<Unit, A2>, A3>, A4>, A5>, A6>): TupleValues5<A2, A3, A4, A5, A6> = TupleValues5<A2, A3, A4, A5, A6>(tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)

@JvmName("tupleValues_6_6_1")
public fun <A1, A2, A3, A4, A5, A6> tupleValues(tuples: Pair<Pair<Pair<Pair<Pair<A1, A2>, A3>, A4>, A5>, A6>): TupleValues6<A1, A2, A3, A4, A5, A6> = TupleValues6<A1, A2, A3, A4, A5, A6>(tuples.first.first.first.first.first, tuples.first.first.first.first.second, tuples.first.first.first.second, tuples.first.first.second, tuples.first.second, tuples.second)
