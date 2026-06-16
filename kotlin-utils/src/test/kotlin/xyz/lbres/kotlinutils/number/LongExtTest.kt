package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class LongExtTest {
    @Test
    fun testIfZero() {
        val getValue = { 2L }

        var long = 0L
        var expected = 2L
        assertEquals(expected, long.ifZero(getValue))

        long = 2
        expected = 2
        assertEquals(expected, long.ifZero(getValue))

        long = 15
        expected = 15
        assertEquals(expected, long.ifZero(getValue))

        long = -100
        expected = -100
        assertEquals(expected, long.ifZero(getValue))
    }

    @Test
    fun testIsNegative() {
        val trueValues = listOf(-1L, -100L)
        val falseValues = listOf(0L, 1L, 100L)
        checkTrueFalse(trueValues, falseValues, { "$it.isNegative()" }, Long::isNegative)
    }

    @Test
    fun testIsZero() {
        val trueValues = listOf(0L)
        val falseValues = listOf(1L, -1L, 100L, -100L)
        checkTrueFalse(trueValues, falseValues, { "$it.isZero()" }, Long::isZero)
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues = listOf(null, 0L)
        val falseValues = listOf(1L, -1L, 100L, -100L)
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }) { it.isNullOrZero() }
    }
}
