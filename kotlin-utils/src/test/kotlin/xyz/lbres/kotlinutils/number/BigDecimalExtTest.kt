package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class BigDecimalExtTest {
    private val zero = BigDecimal("0.0000000000000000000000000000")
    private val smallPositive = BigDecimal("0.0000000000000000000000000001")
    private val smallNegative = BigDecimal("-0.0000000000000000000000000001")

    @Test
    fun testIsZero() {
        // zero
        var bd = BigDecimal.ZERO
        assertTrue(bd.isZero())

        bd = zero
        assertTrue(bd.isZero())

        // non zero
        bd = smallPositive
        assertFalse(bd.isZero())

        bd = smallNegative
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

        bd = smallNegative
        assertTrue(bd.isNegative())

        bd = BigDecimal("-1231.4252435")
        assertTrue(bd.isNegative())

        // positive
        bd = BigDecimal("1")
        assertFalse(bd.isNegative())

        bd = smallPositive
        assertFalse(bd.isNegative())

        bd = BigDecimal("1231.4252435")
        assertFalse(bd.isNegative())
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues = listOf(null, BigDecimal.ZERO, zero)
        val falseValues = listOf(smallPositive, smallPositive, BigDecimal.ONE, BigDecimal("-1000"))
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }, { it.isNullOrZero() })
    }
}
