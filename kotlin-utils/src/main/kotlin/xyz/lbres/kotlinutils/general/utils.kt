package xyz.lbres.kotlinutils.general

import kotlin.reflect.KClass

/**
 * Function to perform a simple boolean check, and return a value based on the result.
 * Values are computed before check occurs.
 *
 * @param check [Boolean]
 * @param trueValue [T]: value to return if [check] is true
 * @param falseValue [T]: value to return if [check] is false
 * @return [trueValue] if the check is true, [falseValue] otherwise
 */
fun <T> simpleIf(check: Boolean, trueValue: T, falseValue: T): T {
    return if (check) {
        trueValue
    } else {
        falseValue
    }
}

/**
 * Function to perform a simple boolean check, and execute a function based on value of the check.
 * Returns the value of the function, which can be [Unit].
 *
 * @param check [Boolean]
 * @param trueMethod [T]: function to invoke if [check] is `true`
 * @param trueMethod [T]: function to invoke if [check] is `false`
 * @return result of [trueMethod] if the check is true, result of [falseMethod] otherwise
 */
fun <T> simpleIf(check: Boolean, trueMethod: () -> T, falseMethod: () -> T): T {
    return if (check) {
        trueMethod()
    } else {
        falseMethod()
    }
}

/**
 * Try to execute a function, and return a default value if the function throws any exception
 *
 * @param defaultValue [T]: default value to return if any exception is thrown
 * @param function () -> [T]: function to execute
 */
fun <T> tryOrDefault(defaultValue: T, function: () -> T): T {
    return try {
        function()
    } catch (_: Exception) {
        defaultValue
    }
}

/**
 * Try to execute a function, and return a default value if the function throws any of the given exceptions
 *
 * @param defaultValue [T]: default value to return if an exception from [exceptions] is thrown
 * @param exceptions List<KClass<out Exception>>: list of exception classes for which [defaultValue] should be returned.
 * All other exceptions will be thrown.
 * @param function () -> [T]: function to execute
 */
fun <T> tryOrDefault(defaultValue: T, exceptions: List<KClass<out Exception>>, function: () -> T): T {
    return try {
        function()
    } catch (e: Exception) {
        for (exceptionClass in exceptions) {
            if (exceptionClass.isInstance(e)) {
                return defaultValue
            }
        }

        throw e
    }
}

/**
 * Function to perform a simple boolean check, and return a value based on the result
 *
 * @param check [Boolean]
 * @param trueValue [T]: value to return if [check] is true
 * @param falseValue [T]: value to return if [check] is false
 * @return [trueValue] if the check is true, [falseValue] otherwise
 */
@Deprecated("Replace with simpleIf", ReplaceWith("simpleIf", "xyz.lbres.kotlinutils.general.simpleIf"), DeprecationLevel.WARNING)
fun <T> ternaryIf(check: Boolean, trueValue: T, falseValue: T) = simpleIf(check, trueValue, falseValue)
