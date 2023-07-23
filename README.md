# Kotlin Utils

[![Build and Test](https://github.com/lbressler13/kotlin-utils/actions/workflows/main_checks.yml/badge.svg?branch=main)](https://github.com/lbressler13/kotlin-utils/actions/workflows/main_checks.yml)

A collection of reusable functions, classes, and typealiases to use in Kotlin projects.

## Contents

### Typealiases

Useful aliases for common types.

See [here](https://kotlinlang.org/docs/type-aliases.html) for general information about typealiases in Kotlin.

### Classes

Reusable classes and data classes.

### Extension methods

Methods that extend existing classes and interfaces.

See [here](https://kotlinlang.org/docs/extensions.html) for general information about extensions in Kotlin.

### Util functions

Functions that are not a direct extension of any class.
Helper functions may still be associated with a class.

## Project structure

### Overall

```project
├── kotlin-utils
│   ├── build                    <-- Automatically generated build files
│   ├── src
│   │   ├── main
│   │   │   ├── kotlin
│   │   │   │   ├── kotlinutils  <-- Source code for kotlin-utils module
│   │   │   │   │   ├── general  <-- Generic util functions and basic classes
│   │   │   │   │   ├── sample   <-- Sample of a class that already exists in Kotlin
│   │   │   │   │   ├── sample2               
│   │   ├── test
│   │   │   ├── kotlin
│   │   │   │   ├── kotlinutils  <-- Unit tests for kotlin-utils module
│   ├── build.gradle.kts
└── settings.gradle.kts
```

### Within a class

```project
├── sample                <-- Class that already exists in Kotlin
│   ├── ext
│   │   ├── SampleExt.kt  <-- Extension methods for the class
│   ├── subclass1         <-- Subclass with the same project structure
│   ├── subclass2         
│   ├── SampleAliases.kt  <-- Typealiases related to the class
│   └── SampleUtils.kt    <-- Helper functions related to the class
```

Subpackages and files are only created when needed.
The full folder structure, including additional subclasses, can be repeated within each subclass.

## Building

The package can be built using an IDE, or with the following command:

```shell
./gradlew build
```

When the package is built, a .jar file will be generated in the build/libs folder.
The name will be in the format "kotlin-utils-version", where the version is specified in the build.gradle.kts file.

## Testing

Unit tests are written using the [Kotlin test](https://kotlinlang.org/api/latest/kotlin.test/) framework.
Tests must be written for helper functions and extension methods.
They should also be written for new classes, and for custom methods in data classes.

Tests can be run using an IDE, or with the following command:

```shell
./gradlew test
```

## Linting

Linting is done using [ktlint](https://ktlint.github.io/), using [this](https://github.com/jlleitschuh/ktlint-gradle) plugin.
See [here](https://github.com/pinterest/ktlint#standard-rules) for a list of standard rules.

Linting can be run using an IDE, or with the following command:
```shell
./gradlew ktlintCheck
```

To perform linting and fix issues where possible, run the following command:

```shell
./gradlew ktlintFormat
```

This can also be run through an IDE.

## Importing the package

This package is hosted in the GitHub Packages registry.
See [here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#using-a-published-package) for information on importing GitHub packages.

## Development practices

- Util functions and extension methods should be added at the highest possible level.
For example, if a method is applicable for `Collection`, it should be defined as an extension of `Collection`, rather than an extension of `List`, `Map`, or other child classes.
- If a method is added for one class, it should also be added for similar classes. For example, a method that is useful for `List` may also be useful for `Array`.
- When applicable, randomized functions should be defined with and without a seed.
