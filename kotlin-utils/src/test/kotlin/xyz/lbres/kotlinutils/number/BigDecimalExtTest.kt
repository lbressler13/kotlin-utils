package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import java.math.BigDecimal
import kotlin.test.Test

class BigDecimalExtTest {
    private val zero = BigDecimal("0.0000000000000000000000000000")
    private val smallPositive = BigDecimal("0.0000000000000000000000000001")
    private val smallNegative = BigDecimal("-0.0000000000000000000000000001")

    @Test
    fun testIsZero() {
        val trueValues = listOf(BigDecimal.ZERO, zero)
        val falseValues = listOf(smallPositive, smallNegative, BigDecimal.ONE, BigDecimal("-1000"))
        checkTrueFalse(trueValues, falseValues, { "$it.isZero()" }, BigDecimal::isZero)
    }

    @Test
    fun testIsNegative() {
        val trueValues = listOf(smallNegative, BigDecimal("-1000"))
        val falseValues = listOf(BigDecimal.ZERO, zero, smallPositive, BigDecimal.ONE)
        checkTrueFalse(trueValues, falseValues, { "$it.isNegative()" }, BigDecimal::isNegative)
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues = listOf(null, BigDecimal.ZERO, zero)
        val falseValues = listOf(smallPositive, smallPositive, BigDecimal.ONE, BigDecimal("-1000"))
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }, { it.isNullOrZero() })
    }
}
