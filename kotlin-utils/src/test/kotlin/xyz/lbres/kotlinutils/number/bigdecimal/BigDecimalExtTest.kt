package xyz.lbres.kotlinutils.number.bigdecimal

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.test.Test
import kotlin.test.assertEquals

class BigDecimalExtTest {
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
