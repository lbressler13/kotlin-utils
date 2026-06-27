package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals

class DoubleExtTest {
    @Test
    fun testIfZero() {
        val getValue = { 2.0 }

        var double = 0.0
        var expected = 2.0
        assertEquals(expected, double.ifZero(getValue))

        double = -0.0
        assertEquals(expected, double.ifZero(getValue))

        double = 2.0
        expected = 2.0
        assertEquals(expected, double.ifZero(getValue))

        double = 15.0
        expected = 15.0
        assertEquals(expected, double.ifZero(getValue))

        double = -0.1230
        expected = -0.123
        assertEquals(expected, double.ifZero(getValue))
    }

    @Test
    fun testIsNegative() {
        val trueValues = listOf(-0.000000000001, -100.0)
        val falseValues = listOf(0.0, -0.0, 0.000000000001, 100.0)
        checkTrueFalse(trueValues, falseValues, { "$it.isNegative()" }, Double::isNegative)
    }

    @Test
    fun testIsWholeNumber() {
        val trueValues = listOf(
            0.0,
            -0.0,
            100.0,
            -1234560000000999.0,
            123456000000000.0000,
            7.00000000000000000000000000000,
        )
        val falseValues = listOf(
            0.00000000000000000001,
            1.01,
            123456000000099.9,
            999999999999999.9,
        )
        checkTrueFalse(trueValues, falseValues, { "$it.isWholeNumber()" }, Double::isWholeNumber)
    }

    @Test
    fun testIsZero() {
        val trueValues = listOf(0.0, -0.0)
        val falseValues = listOf(-0.000000000001, -100.0, 0.000000000001, 100.0)
        checkTrueFalse(trueValues, falseValues, { "$it.isZero()" }, Double::isZero)
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues = listOf(null, 0.0, -0.0)
        val falseValues = listOf(-0.000000000001, -100.0, 0.000000000001, 100.0)
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }, { it.isNullOrZero() })
    }
}
