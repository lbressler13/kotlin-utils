package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
        var short: Short = 0
        assertFalse(short.isNegative())

        short = 1
        assertFalse(short.isNegative())

        short = 100
        assertFalse(short.isNegative())

        short = -1
        assertTrue(short.isNegative())

        short = -100
        assertTrue(short.isNegative())
    }

    @Test
    fun testIsZero() {
        var short: Short = 0
        assertTrue(short.isZero())

        short = 1
        assertFalse(short.isZero())

        short = -1
        assertFalse(short.isZero())

        short = 100
        assertFalse(short.isZero())

        short = -100
        assertFalse(short.isZero())
    }

    @Test
    fun testIsNullOrZero() {
        var short: Short? = null
        assertTrue(short.isNullOrZero())

        short = 0
        assertTrue(short.isNullOrZero())

        short = 1
        assertFalse(short.isNullOrZero())

        short = -1
        assertFalse(short.isNullOrZero())

        short = 100
        assertFalse(short.isNullOrZero())

        short = -100
        assertFalse(short.isNullOrZero())
    }
}
