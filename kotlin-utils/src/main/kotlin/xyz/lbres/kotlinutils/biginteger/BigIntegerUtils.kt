package xyz.lbres.kotlinutils.biginteger

import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.list.ext.isSingleValue
import java.math.BigInteger

/**
 * Get positive greatest common divisor of 2 numbers using Euclidean algorithm
 *
 * @param val1 [BigInteger]
 * @param val2 [BigInteger]
 * @return [BigInteger] the positive greatest common divisor of [val1] and [val2]
 */
fun getGCD(val1: BigInteger, val2: BigInteger): BigInteger {
    val abs1 = val1.abs()
    val abs2 = val2.abs()

    when {
        abs1.isZero() && abs2.isZero() -> return BigInteger.ONE
        abs1.isZero() -> return abs2
        abs2.isZero() -> return abs1
        abs1 == abs2 -> return abs1
    }

    var sum = abs1.max(abs2)
    var value = abs1.min(abs2)
    var finished = false

    while (!finished) {
        val remainder = sum % value

        if (remainder.isZero()) {
            finished = true
        } else {
            sum = value
            value = remainder
        }
    }

    return value
}

/**
 * Get positive greatest common divisor of a list of numbers using Euclidean algorithm
 *
 * @param values [List]<BigInteger>
 * @return [BigInteger] the positive greatest common divisor of [values]
 */
fun getListGCD(values: List<BigInteger>): BigInteger {
    when {
        values.isEmpty() -> return BigInteger.ONE
        values.isSingleValue() && values[0].isZero() -> return BigInteger.ONE
        values.isSingleValue() -> return values[0].abs()
        values.size == 2 -> return getGCD(values[0], values[1])
    }

    var current: BigInteger = values[0]
    // loop is more efficient than fold b/c fold is not parallel, and loop has the option to exit early
    for (value in values) {
        current = getGCD(value, current)
        if (current == BigInteger.ONE) {
            return BigInteger.ONE
        }
    }

    return current
}
