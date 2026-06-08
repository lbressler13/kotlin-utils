package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class ByteExtTest {
    @Test
    fun testIfZero() {
        val getValue = { 2.toByte() }

        var byte: Byte = 0
        var expected: Byte = 2
        assertEquals(expected, byte.ifZero(getValue))

        byte = 2
        expected = 2
        assertEquals(expected, byte.ifZero(getValue))

        byte = 15
        expected = 15
        assertEquals(expected, byte.ifZero(getValue))

        byte = -100
        expected = -100
        assertEquals(expected, byte.ifZero(getValue))
    }

    @Test
    fun testIsNegative() {
        var byte: Byte = 0
        assertFalse(byte.isNegative())

        byte = 1
        assertFalse(byte.isNegative())

        byte = 100
        assertFalse(byte.isNegative())

        byte = -1
        assertTrue(byte.isNegative())

        byte = -100
        assertTrue(byte.isNegative())
    }

    @Test
    fun testIsZero() {
        var byte: Byte = 0
        assertTrue(byte.isZero())

        byte = 1
        assertFalse(byte.isZero())

        byte = -1
        assertFalse(byte.isZero())

        byte = 100
        assertFalse(byte.isZero())

        byte = -100
        assertFalse(byte.isZero())
    }

    @Test
    fun testIsNullOrZero() {
        var byte: Byte? = null
        assertTrue(byte.isNullOrZero())

        byte = 0
        assertTrue(byte.isNullOrZero())

        byte = 1
        assertFalse(byte.isNullOrZero())

        byte = -1
        assertFalse(byte.isNullOrZero())

        byte = 100
        assertFalse(byte.isNullOrZero())

        byte = -100
        assertFalse(byte.isNullOrZero())
    }
}
