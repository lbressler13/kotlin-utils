package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class IntExtTest {
    @Test
    fun testIfZero() {
        val getValue = { 2 }

        var int = 0
        var expected = 2
        assertEquals(expected, int.ifZero(getValue))

        int = 2
        expected = 2
        assertEquals(expected, int.ifZero(getValue))

        int = 15
        expected = 15
        assertEquals(expected, int.ifZero(getValue))

        int = -100
        expected = -100
        assertEquals(expected, int.ifZero(getValue))
    }

    @Test
    fun testIsNegative() {
        val trueValues = listOf(-1, -100)
        val falseValues = listOf(0, 1, 100)
        checkTrueFalse(trueValues, falseValues, { "$it.isNegative()" }, Int::isNegative)
    }

    @Test
    fun testIsZero() {
        val trueValues = listOf(0)
        val falseValues = listOf(1, -1, 100, -100)
        checkTrueFalse(trueValues, falseValues, { "$it.isZero()" }, Int::isZero)
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues = listOf(null, 0)
        val falseValues = listOf(1, -1, 100, -100)
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }) { it.isNullOrZero() }
    }
}
