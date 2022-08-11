package kotlinutils.biginteger

import kotlinutils.biginteger.ext.isZero
import java.math.BigInteger

/**
 * Get positive greatest common divisor of 2 numbers using Euclidean algorithm
 *
 * @param val1 [BigInteger]
 * @param val2 [BigInteger]
 * @return [BigInteger] the positive greatest common divisor of [val1] and [val2]
 */
fun getGCD(val1: BigInteger, val2: BigInteger): BigInteger {
    val aval1 = val1.abs()
    val aval2 = val2.abs()

    when {
        aval1.isZero() && aval2.isZero() -> return BigInteger.ONE
        aval1.isZero() -> return aval2
        aval2.isZero() -> return aval1
        aval1 == aval2 -> return aval1
    }

    var sum = aval1.max(aval2)
    var value = aval1.min(aval2)
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
 * @param values [List<BigInteger>]
 * @return [BigInteger] the positive greatest common divisor of [values]
 */
fun getListGCD(values: List<BigInteger>): BigInteger {
    when {
        values.isEmpty() -> return BigInteger.ONE
        values.size == 1 && values[0].isZero() -> return BigInteger.ONE
        values.size == 1 -> return values[0].abs()
        values.size == 2 -> return getGCD(values[0], values[1])
    }

    var current: BigInteger = values[0]
    for (value in values) {
        current = getGCD(value, current)
        if (current == BigInteger.ONE) {
            return BigInteger.ONE
        }
    }

    return current
}
