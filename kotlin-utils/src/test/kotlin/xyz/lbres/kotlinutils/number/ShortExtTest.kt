package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class ShortExtTest {
    @Test
    fun testIfZero() {
        val getValue = { 2.toShort() }

        var short: Short = 0
        var expected: Short = 2
        assertEquals(expected, short.ifZero(getValue))

        short = 2
        expected = 2
        assertEquals(expected, short.ifZero(getValue))

        short = 15
        expected = 15
        assertEquals(expected, short.ifZero(getValue))

        short = -100
        expected = -100
        assertEquals(expected, short.ifZero(getValue))
    }

    @Test
    fun testIsNegative() {
        val trueValues: List<Short> = listOf(-1, -100)
        val falseValues: List<Short> = listOf(0, 1, 100)
        checkTrueFalse(trueValues, falseValues, { "$it.isNegative()" }, Short::isNegative)
    }

    @Test
    fun testIsZero() {
        val trueValues: List<Short> = listOf(0)
        val falseValues: List<Short> = listOf(1, -1, 100, -100)
        checkTrueFalse(trueValues, falseValues, { "$it.isZero()" }, Short::isZero)
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues: List<Short?> = listOf(null, 0)
        val falseValues: List<Short?> = listOf(1, -1, 100, -100)
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }) { it.isNullOrZero() }
    }
}
