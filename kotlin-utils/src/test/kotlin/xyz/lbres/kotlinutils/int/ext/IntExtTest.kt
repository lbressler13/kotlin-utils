package xyz.lbres.kotlinutils.int.ext

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress("KotlinConstantConditions")
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
}
