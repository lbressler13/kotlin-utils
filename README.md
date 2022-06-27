# Kotlin Utils

A collection of reusable functions, classes, and aliases that I've defined across different Kotlin and Android projects.
Android-specific code, such as functions to handle Views, is not included.

## Purpose
The primary purpose of this package is to avoid copying code between projects.
Most of this code has already been implemented at least twice, so, as an alternative to continuing to copy/paste, I've moved it into this package.
This prevents unnecessary re-writing and re-testing of code, and also creates a central location for any changes and bug fixes.

## Contents
### Typealiases
Useful aliases for common types.

See [here](https://kotlinlang.org/docs/type-aliases.html) for general information about typealiases in Kotlin.

### Classes
Reusable classes and data classes.

### Extension functions
Functions that extend existing classes and interfaces.
Extensions are added to the broadest possible class/implementation in order to be more widely usable.

See [here](https://kotlinlang.org/docs/extensions.html) for general information about extensions in Kotlin.

### Helper functions
Functions that aren't extensions of a class, but provide added functionality.
In general, these can still be associated with a single class, as this package does not store specialized code for different projects.

## Project structure
### Overall
```project
├── kotlin-utils
│   ├── build                    <-- Automatically generated build files
│   ├── src
│   │   ├── main
│   │   │   ├── kotlin
│   │   │   │   ├── kotlinutils  <-- Source code for kotlin-utils module
│   │   │   │   │   ├── classes  <-- New classes defined by this package
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
│   ├── ext               <-- Extension functions for the class
│   ├── subclass1         
│   ├── subclass2         
│   ├── SampleAliases.kt  <-- Typealiases related to the class
│   └── SampleUtils.kt    <-- Helper functions related to the class
```

Subpackages and files are only created when needed.
The full folder structure, including additional subclasses, can be repeated within each subclass.

## Building
The package can be built using an IDE, or with the following command:
```./gradlew build```

When the package is built, a .jar file will be generated in the build/libs folder.
The name will be in the format "kotlin-utils-version", where the version is specified in the build.gradle.kts file.

## Testing
Unit tests are written using the [Kotlin test](https://kotlinlang.org/api/latest/kotlin.test/) framework.
Tests must be written for helper functions and extension functions.

Tests can be run using an IDE, or with the following command:
```./gradlew test```

## Linting
Linting is done using [ktlint](https://ktlint.github.io/), using [this](https://github.com/jlleitschuh/ktlint-gradle) plugin.
See [here](https://github.com/pinterest/ktlint#standard-rules) for a list of standard rules.

Linting can be run using an IDE, or with the following command:
```./gradlew ktlintCheck```

## Importing the package
In order to import the package, copy the most recent .jar file into your project, and add the file to the list of imports for the project.
