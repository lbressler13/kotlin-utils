package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
        var int = 0
        assertFalse(int.isNegative())

        int = 1
        assertFalse(int.isNegative())

        int = 100
        assertFalse(int.isNegative())

        int = -1
        assertTrue(int.isNegative())

        int = -100
        assertTrue(int.isNegative())
    }

    @Test
    fun testIsZero() {
        var int = 0
        assertTrue(int.isZero())

        int = 1
        assertFalse(int.isZero())

        int = -1
        assertFalse(int.isZero())

        int = 100
        assertFalse(int.isZero())

        int = -100
        assertFalse(int.isZero())
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues = listOf(null, 0)
        val falseValues = listOf(1, -1, 100, -100)
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }) { it.isNullOrZero() }
    }
}
