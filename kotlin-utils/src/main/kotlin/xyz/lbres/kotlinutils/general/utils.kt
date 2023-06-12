package xyz.lbres.kotlinutils.general

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
 * Function to perform a simple boolean check, and return a value based on the result
 *
 * @param check [Boolean]
 * @param trueValue [T]: value to return if [check] is true
 * @param falseValue [T]: value to return if [check] is false
 * @return [trueValue] if the check is true, [falseValue] otherwise
 */
@Deprecated("Replace with simpleIf", ReplaceWith("simpleIf", "xyz.lbres.kotlinutils.general.simpleIf"), DeprecationLevel.WARNING)
fun <T> ternaryIf(check: Boolean, trueValue: T, falseValue: T) = simpleIf(check, trueValue, falseValue)
