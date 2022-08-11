package kotlinutils.bigdecimal.ext

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class BigDecimalExtTest {
    @Test
    internal fun testIsZero() {
        // zero
        var bd = BigDecimal.ZERO
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
    internal fun testIsNegative() {
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
}
