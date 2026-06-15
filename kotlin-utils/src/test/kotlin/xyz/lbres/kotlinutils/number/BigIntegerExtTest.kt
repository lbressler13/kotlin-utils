package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class BigIntegerExtTest {
    @Test
    fun testIsNegative() {
        val trueValues = listOf(BigInteger("-1"), BigInteger("-100"))
        val falseValues = listOf(BigInteger.ZERO, BigInteger.ONE, BigInteger("100"))
        checkTrueFalse(trueValues, falseValues, { "$it.isNegative()" }, BigInteger::isNegative)
    }

    @Test
    fun testIsZero() {
        val trueValues = listOf(BigInteger.ZERO)
        val falseValues = listOf(BigInteger.ONE, BigInteger("-1"), BigInteger("100"), BigInteger("-100"))
        checkTrueFalse(trueValues, falseValues, { "$it.isZero()" }, BigInteger::isZero)
    }

    @Test
    fun testIfZero() {
        val getValue = { BigInteger.TWO }

        var bi = BigInteger.ZERO
        var expected = BigInteger.TWO
        assertEquals(expected, bi.ifZero(getValue))

        bi = BigInteger.TWO
        expected = BigInteger.TWO
        assertEquals(expected, bi.ifZero(getValue))

        bi = BigInteger("15")
        expected = BigInteger("15")
        assertEquals(expected, bi.ifZero(getValue))

        bi = BigInteger("-100")
        expected = BigInteger("-100")
        assertEquals(expected, bi.ifZero(getValue))
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues = listOf(null, BigInteger.ZERO)
        val falseValues = listOf(BigInteger.ONE, BigInteger("-1"), BigInteger("100"), BigInteger("-100"))
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }, { it.isNullOrZero() })
    }
}
