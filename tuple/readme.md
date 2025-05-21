# Tuple

A small Kotlin utility library and code generator to make working with deeply nested `Pair`-based tuples a breeze. It provides H-list–style type support in Kotlin: you can treat a nested `Pair<A, Pair<B, C>>` as a 3-tuple, destructure it, and avoid manual boilerplate.

## Features
- **Automatic tuple generation** up to N-arity  
- **Data classes** `TupleValuesN<A1,…,AN>` with `applyTo` for generic mapping  
- **Overloaded `tupleValues(...)`** functions to destructure any nested `Pair<…,Pair<…,Unit>>` shape  
- **Configurable arity** via a small KotlinPoet-based generator  

## Example

```kotlin
val someNestedTuples = Pair(42, Pair("Hello world", 3.1415))

// Destructure as a 3-tuple
val (a, b, c) = tupleValues(someNestedTuples)

println(a) // 42     : Int
println(b) // Hello world : String
println(c) // 3.1415 : Double
````

Under the hood you get:

```kotlin
data class TupleValues3<A1, A2, A3>(
  val a1: A1, val a2: A2, val a3: A3
) {
  fun <B> applyTo(f: (A1, A2, A3) -> B): B = f(a1, a2, a3)
}

@JvmName("tupleValues_3_3_1")
fun <A1, A2, A3> tupleValues(
  tuples: Pair<Pair<A1, A2>, A3>
): TupleValues3<A1, A2, A3> =
  TupleValues3(tuples.first.first, tuples.first.second, tuples.second)
```

## Installation

If this library is published, add to your Gradle or Maven build:

```
dependencies {
  implementation("io.github.bbasinsk:tuple:{version}")
}
```

## For contributors - if you'd like to add more arity - just ask!

Otherwise, if you want to generate the code yourself, you can use the `TupleGenerator`.  It uses KotlinPoet to generate the data classes and functions.

Running that `TupleGenerator` will emit:

* `TupleValues0.kt` … `TupleValues10.kt`
* All the `tupleValues(...)` overloads for each arity

into the common source folder.

You can then create a PR with the generated files.
