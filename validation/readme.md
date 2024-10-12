# Validation 

A library for validating input data. This library provides a set of combinators that can be used to define
complex validation rules for input data, and provides a functional and type-safe validation experience.

## Installation

To use `validation` in your project, add the following dependency to your build.gradle.kts file:

```kt
dependencies {
    implementation("io.github.bbasinsk:validation:$katalystVersion")
}
```

## Usage

To use, you first need to create a `Validation` for the type of data you want to validate. A
`Validation` is created by combining one or more validation rules using the provided combinators.

Here is an example of how to create a `Validation` for a Person class:

```kt
data class Person(val name: String, val age: Int)

fun Validation.Companion.person(name: String, age: Int): Validation<String, Person> =
    build(
        nonEmpty(name) { "Name must not be empty" },
        nonNegative(age) { "Age must be non-negative" },
        ::Person
    )
```

This `Validation` will only return Person instances where the name is not blank and the age is non-negative.

### Valid and Invalid

A Validation instance will either be a Valid instance containing the validated Person
object, or an Invalid instance containing a list of error messages.

Here is an example showing how to use the `person` validation:

```kt
when (val result = Validation.person("John", 42)) {
    // Output: name=John, age=42
    is Invalid -> println("errors; ${result.errors}")
    is Valid -> println("name=${result.value.name}, age=${result.value.age}")
}

when (val result = Validation.person("", -1)) {
    // Output: errors: [Name must not be empty, Age must be non-negative]
    is Invalid -> println("errors: ${result.errors}")
    is Valid -> println("name=${result.value.name}, age=${result.value.age}")
}
```

### Validating collections

The `sequence` function can be used to validate a list of items. It will return a `Validation` that is valid if all
items in the list are valid, and invalid if any of the items are invalid.

Here is an example of how to use the `sequence` function:

```kt
val validations: List<Validation<String, Person>> = listOf(
    Validation.person("John", 42),
    Validation.person("Jane", 21),
    Validation.person("Bob", 18)
)

when (val result: Validation<String, List<Person>> = Validation.sequence(validations)) {
    // Output: [name=John, age=42, name=Jane, age=21, name=Bob, age=18]
    is Invalid -> println("errors: ${result.errors}")
    is Valid -> println(result.value.map { "name=${it.name}, age=${it.age}" })
}
```

### Falling back on invalid

The `orElse` function can be used to fall back to a default value if the validation fails.

Here is an example of how to use the `orElse` function:

```kt
val result: Validation<String, Person> =
    Validation.person("", -1)
        .orElse { Validation.person("John", 42) }

when (result) {
    // Output: name=John, age=42
    is Invalid -> println("errors: ${result.errors}")
    is Valid -> println("name=${result.value.name}, age=${result.value.age}")
}
```
