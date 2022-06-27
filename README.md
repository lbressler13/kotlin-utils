# Kotlin Utils

A collection of reusable functions, classes, and aliases that I've defined across different Kotlin and Android projects.
Android-specific code is not included.

## Purpose
The primary purpose to avoid copying code between projects.
Much of the code in this package has already been implemented at least twice over other projects.
Instead of copy/pasting code across projects, I've centralized the code into this package.
It prevents re-writing code and tests, and also creates a central location for any changes and bug fixes that need to be made.

## Contents
The package currently contains 4 types of values.

### Typealiases
Useful aliases for common types

See [here](https://kotlinlang.org/docs/type-aliases.html) for general information about typealiases in Kotlin.

### Classes
Classes and data classes that are useful and reusable.
Currently, only one class is included.

### Extension functions
Functions that extended existing classes.
These can extend classes, as well as interfaces and abstract classes.
When possible, extensions are added to the broadest class/implementation possible in order to be broadly usable.

See [here](https://kotlinlang.org/docs/extensions.html) for general information about extensions in Kotlin.

### Helper functions
Functions that aren't extensions of a class, but provide added functionality.
In general, these can still b associated with a single class, as this package does not store specialized code for different projects.

## Project structure
```project
├── kotlin-utils
│   ├── build                                <-- Automatically generated build files
│   ├── src
│   │   ├── main
│   │   │   ├── kotlin
│   │   │   │   ├── kotlinutils               <-- Source code for kotlin-utils module
│   │   │   │   │   ├── classes               <-- New classes defined by this package
│   │   │   │   │   ├── sample                <-- Sample of a class that already exists in Kotlin
│   │   │   │   │   │   ├── ext               <-- Extension functions for the class
│   │   │   │   │   │   ├── subclass1         <-- Any code related to a subclass. Folder structure is repeated within each subclass
│   │   │   │   │   │   ├── subclass2         
│   │   │   │   │   │   ├── SampleAliases.kt  <-- Typealiases related to the class
│   │   │   │   │   │   ├── SampleUtils.kt    <-- Helper functions related to the class
│   │   │   │   │   ├── sample2               
│   │   ├── test
│   │   │   ├── kotlin
│   │   │   │   ├── kotlinutils   <-- Unit tests for kotlin-utils module
│   ├── build.gradle.kts
└── settings.gradle.kts
```
Subpackages and code for each class are only created when needed.
Any empty packages and/or code are omitted.

## Building
The package can be built via the command line by running `./gradlew build`.
IDEs such as IntelliJ may also provide the option to build the package through the UI.

When the package is built, a .jar file will be generated in the build/libs folder.
The name will be in the format 'kotlin-utils-<version>', where the version is specified in the build.gradle file.

## Testing
Unit tests are written using the [Kotlin test](https://kotlinlang.org/api/latest/kotlin.test/) framework.
Tests must be written for helper functions and extension functions.

Tests can be run using an IDE, or with the following command:
`./gradlew test`

## Importing the package
In order to import the package, copy the most recent .jar file into your project, and add the file to the list of imports for the project.
