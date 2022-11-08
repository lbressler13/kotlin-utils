# Kotlin Utils

A collection of reusable functions, classes, and aliases that can be used across various Kotlin and Android projects.
Android-specific code, such as functions to handle Views, is not included.

## Purpose
The initial purpose of this package was to avoid copying code between projects.
The initial code had already been implemented several times, and was collected here as an alternative to repeated copy/paste.
This prevented unnecessary re-writing and re-testing of code, and also created a central location for any changes and bug fixes.

However, the package has since expanded to include various classes and functions that could be useful in different projects.

## Contents
### Typealiases
Useful aliases for common types.

See [here](https://kotlinlang.org/docs/type-aliases.html) for general information about typealiases in Kotlin.

### Classes
Reusable classes and data classes.

### General utils
Functions that are not associated with a specific class.

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
```shell
./gradlew build
```

When the package is built, a .jar file will be generated in the build/libs folder.
The name will be in the format "kotlin-utils-version", where the version is specified in the build.gradle.kts file.

## Testing
Unit tests are written using the [Kotlin test](https://kotlinlang.org/api/latest/kotlin.test/) framework.
Tests must be written for helper functions and extension functions.
They should also be written for new classes, with the exception of data classes.

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
This command will verify if the code passes linting, but will not attempt to fix any issues.
To perform linting and fix issues where possible, run the following command:
```shell
./gradlew ktlintFormat
```
This can also be run through an IDE.

## Importing the package
In order to import the package, copy the most recent .jar file into your project, and add the file to the list of imports for the project.
