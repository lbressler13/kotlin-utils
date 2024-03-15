package xyz.lbres.kotlinutils.bigdecimal.ext

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BigDecimalExtTest {
    @Test
    fun testIsZero() {
        // zero
        var bd = BigDecimal.ZERO
        assertTrue(bd.isZero())

        bd = BigDecimal("0.0000000000000000000000000000")
        assertTrue(bd.isZero())

        // non zero
        bd = BigDecimal("0.0000000000000000000000000001")
        assertFalse(bd.isZero())

        bd = BigDecimal("-0.0000000000000000000000000001")
        assertFalse(bd.isZero())

        bd = BigDecimal.ONE
        assertFalse(bd.isZero())

        bd = BigDecimal("-1000")
        assertFalse(bd.isZero())
    }

    @Test
    fun testIsNegative() {
        // zero
        var bd = BigDecimal.ZERO
        assertFalse(bd.isNegative())

        // negative
        bd = BigDecimal("-1")
        assertTrue(bd.isNegative())

        bd = BigDecimal("-0.0000000000000000000000000001")
        assertTrue(bd.isNegative())

        bd = BigDecimal("-1231.4252435")
        assertTrue(bd.isNegative())

        // positive
        bd = BigDecimal("1")
        assertFalse(bd.isNegative())

        bd = BigDecimal("0.0000000000000000000000000001")
        assertFalse(bd.isNegative())

        bd = BigDecimal("1231.4252435")
        assertFalse(bd.isNegative())
    }

    @Test
    fun testIsWholeNumber() {
        // whole number
        var bd = BigDecimal.ZERO
        assertTrue(bd.isWholeNumber())

        bd = BigDecimal("100")
        assertTrue(bd.isWholeNumber())

        bd = BigDecimal("-100")
        assertTrue(bd.isWholeNumber())

        bd = BigDecimal("-1234560000000000000999")
        assertTrue(bd.isWholeNumber())

        bd = BigDecimal("123456000000000.0000")
        assertTrue(bd.isWholeNumber())

        bd = BigDecimal("7.00000000000000000000000000000")
        assertTrue(bd.isWholeNumber())

        // decimal
        bd = BigDecimal("0.00000000000000000001")
        assertFalse(bd.isWholeNumber())

        bd = BigDecimal("-1.01")
        assertFalse(bd.isWholeNumber())

        bd = BigDecimal("123456000000000000099.9")
        assertFalse(bd.isWholeNumber())

        bd = BigDecimal("99999999999999999999999.9")
        assertFalse(bd.isWholeNumber())
    }

    @Test
    fun testRoundToBigInteger() {
        // whole
        assertEquals(BigInteger.ZERO, BigDecimal.ZERO.roundToBigInteger())
        assertEquals(BigInteger.ONE, BigDecimal.ONE.roundToBigInteger())

        var bd = BigDecimal("1000000000000000000000")
        var expected = BigInteger("1000000000000000000000")
        assertEquals(expected, bd.roundToBigInteger())

        bd = BigDecimal("-123.000000000000000000000000000")
        expected = BigInteger("-123")
        assertEquals(expected, bd.roundToBigInteger())

        // up
        bd = BigDecimal("0.5")
        expected = BigInteger.ONE
        assertEquals(expected, bd.roundToBigInteger())

        bd = BigDecimal("-0.4555555555555555555555555555")
        expected = BigInteger.ZERO
        assertEquals(expected, bd.roundToBigInteger())

        bd = BigDecimal("12345.67")
        expected = BigInteger("12346")
        assertEquals(expected, bd.roundToBigInteger())

        bd = BigDecimal("-12.2000000000000000000045679")
        expected = BigInteger("-12")
        assertEquals(expected, bd.roundToBigInteger())

        // down
        bd = BigDecimal("0.4555555555555555555555555555")
        expected = BigInteger.ZERO
        assertEquals(expected, bd.roundToBigInteger())

        bd = BigDecimal("-0.5")
        expected = -BigInteger.ONE
        assertEquals(expected, bd.roundToBigInteger())

        bd = BigDecimal("-12345.67")
        expected = BigInteger("-12346")
        assertEquals(expected, bd.roundToBigInteger())

        bd = BigDecimal("12.2000000000000000000045679")
        expected = BigInteger("12")
        assertEquals(expected, bd.roundToBigInteger())

        // other rounding modes
        bd = BigDecimal("0.333")
        expected = BigInteger.ONE
        assertEquals(expected, bd.roundToBigInteger(RoundingMode.UP))

        bd = BigDecimal("6.5")
        expected = BigInteger("6")
        assertEquals(expected, bd.roundToBigInteger(RoundingMode.HALF_EVEN))

        bd = BigDecimal("9.99999999999999")
        expected = BigInteger("9")
        assertEquals(expected, bd.roundToBigInteger(RoundingMode.DOWN))
    }
}
