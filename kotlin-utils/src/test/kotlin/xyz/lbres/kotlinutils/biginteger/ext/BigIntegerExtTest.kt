package xyz.lbres.kotlinutils.biginteger.ext

import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BigIntegerExtTest {
    @Test
    fun testIsNegative() {
        var bi = BigInteger.ZERO
        assertFalse { bi.isNegative() }

        bi = BigInteger.ONE
        assertFalse { bi.isNegative() }

        bi = BigInteger("100")
        assertFalse { bi.isNegative() }

        bi = BigInteger("-1")
        assertTrue { bi.isNegative() }

        bi = BigInteger("-100")
        assertTrue { bi.isNegative() }
    }

    @Test
    fun testIsZero() {
        var bi = BigInteger.ZERO
        assertTrue { bi.isZero() }

        bi = BigInteger.ONE
        assertFalse { bi.isZero() }

        bi = BigInteger("-1")
        assertFalse { bi.isZero() }

        bi = BigInteger("100")
        assertFalse { bi.isZero() }

        bi = BigInteger("-100")
        assertFalse { bi.isZero() }
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
}
