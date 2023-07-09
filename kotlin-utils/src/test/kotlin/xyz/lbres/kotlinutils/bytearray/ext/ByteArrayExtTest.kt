package xyz.lbres.kotlinutils.bytearray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ByteArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = byteArrayOf()
        var expected = byteArrayOf()
        array.setAllValues(8)
        assertContentEquals(expected, array)

        // no change
        array = byteArrayOf(2, 2, 2, 2)
        expected = byteArrayOf(2, 2, 2, 2)
        array.setAllValues(2)
        assertContentEquals(expected, array)

        // some change
        array = byteArrayOf(2, 0, -2, 9)
        expected = byteArrayOf(0, 0, 0, 0)
        array.setAllValues(0)
        assertContentEquals(expected, array)

        // all change
        array = byteArrayOf(5, 6, 7, 8)
        expected = byteArrayOf(-10, -10, -10, -10)
        array.setAllValues(-10)
        assertContentEquals(expected, array)
    }

    @Test
    fun testCountElement() {
        var array = byteArrayOf()
        assertEquals(0, array.countElement(0))

        array = byteArrayOf(127)
        assertEquals(1, array.countElement(127))

        array = byteArrayOf(5, 5, 1, 3, 5, -19, 52)
        assertEquals(3, array.countElement(5))
        assertEquals(1, array.countElement(-19))

        array[1] = -19
        assertEquals(2, array.countElement(5))
        assertEquals(2, array.countElement(-19))
    }
}
