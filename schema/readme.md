# Schema


## Overview
This project is about how to describe entire data structures using values.
It is inspired by [zio-schema](https://zio.dev/zio-schema/).

The "built-in" way in kotlin on how to describe data structures are data classes and sealed interfaces.

For example, assume we have the `Person` data type, like this:

```kt
data class Person(
    val name: String, 
    val age: Int
)
```

It has the following information:

- Name of the structure: Person
- Fields: name and age
- Type of the fields: String and Int
- Type of the structure: Person

A `Schema` tries to reify the concept of structure for datatypes by turning the above information into values.
Not only for data classes, but also for other types like collections, unions, enumerations etc.

## Use cases

- Serialization and deserialization
  - JSON
  - Protobuf
  - Avro
  - CSV
  - HOCON
- Documentation
  - OpenAPI
  - AsyncAPI
- Code generation
  - Arbitrary test data

We can have a single source of truth for the structure of the data, and use it for multiple purposes.

We can also separate the structure from the implementation,
so that we can change the implementation without changing the structure,
allowing for infrastructure upgrades.

## Supported types

### Primitives
- `int`
- `long`
- `float`
- `double`
- `string`
- `boolean`
- `bytes`
- `enumeration`

### Collections
- `list` (array, set)
- `stringMap` (map of any value with string keys)

### Complex
- `record` (object with fields)
- `oneOf` (union of multiple types)

### JVM-specific
- `java.util.UUID`
- `java.time.Instant`
- `java.time.LocalDate`
