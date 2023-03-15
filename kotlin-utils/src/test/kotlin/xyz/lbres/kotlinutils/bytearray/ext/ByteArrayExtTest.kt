package xyz.lbres.kotlinutils.bytearray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals

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
}
