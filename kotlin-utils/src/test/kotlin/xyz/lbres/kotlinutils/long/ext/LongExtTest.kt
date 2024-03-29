package xyz.lbres.kotlinutils.long.ext

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
        var long = 0L
        assertFalse(long.isNegative())

        long = 1L
        assertFalse(long.isNegative())

        long = 100
        assertFalse(long.isNegative())

        long = -1
        assertTrue(long.isNegative())

        long = -100
        assertTrue(long.isNegative())
    }

    @Test
    fun testIsZero() {
        var long = 0L
        assertTrue(long.isZero())

        long = 1
        assertFalse(long.isZero())

        long = -1
        assertFalse(long.isZero())

        long = 100
        assertFalse(long.isZero())

        long = -100
        assertFalse(long.isZero())
    }
}
