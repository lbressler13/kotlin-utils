package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals

class FloatExtTest {
    @Test
    fun testIfZero() {
        val getValue = { 2f }

        var float = 0f
        var expected = 2f
        assertEquals(expected, float.ifZero(getValue))

        float = -0f
        assertEquals(expected, float.ifZero(getValue))

        float = 2f
        expected = 2f
        assertEquals(expected, float.ifZero(getValue))

        float = 15f
        expected = 15f
        assertEquals(expected, float.ifZero(getValue))

        float = -0.123f
        expected = -0.123f
        assertEquals(expected, float.ifZero(getValue))
    }

    @Test
    fun testIsNegative() {
        val trueValues = listOf(-0.000000000001f, -100f)
        val falseValues = listOf(0f, -0f, 0.000000000001f, 100f)
        checkTrueFalse(trueValues, falseValues, { "$it.isNegative()" }, Float::isNegative)
    }

    @Test
    fun testIsZero() {
        val trueValues = listOf(0f, -0f)
        val falseValues = listOf(0.000000000001f, 100f, -0.000000000001f, -100f)
        checkTrueFalse(trueValues, falseValues, { "$it.isZero()" }, Float::isZero)
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues = listOf(null, 0f, -0f)
        val falseValues = listOf(0.000000000001f, 100f, -0.000000000001f, -100f)
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }) { it.isNullOrZero() }
    }
}
