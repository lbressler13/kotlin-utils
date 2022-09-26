package xyz.lbres.kotlinutils.general

/**
 * Function to emulate use of ternary operator for a boolean check
 *
 * @param check [Boolean]
 * @param trueValue [T]: value to return if [check] is true
 * @param falseValue [T]: value to return if [check] is false
 * @return [trueValue] if the check is true, [falseValue] otherwise
 */
fun <T> ternaryIf(check: Boolean, trueValue: T, falseValue: T): T {
    return if (check) {
        trueValue
    } else {
        falseValue
    }
}
