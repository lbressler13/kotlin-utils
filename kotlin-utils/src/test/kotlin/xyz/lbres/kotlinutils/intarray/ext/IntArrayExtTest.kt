package xyz.lbres.kotlinutils.intarray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals

class IntArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = intArrayOf()
        var expected = intArrayOf()
        array.setAllValues(0)
        assertContentEquals(expected, array)

        // no change
        array = intArrayOf(2, 2, 2, 2)
        expected = intArrayOf(2, 2, 2, 2)
        array.setAllValues(2)
        assertContentEquals(expected, array)

        // some change
        array = intArrayOf(0, -99, 1000)
        expected = intArrayOf(-99, -99, -99)
        array.setAllValues(-99)
        assertContentEquals(expected, array)

        // all change
        array = intArrayOf(100, -100, 200, 3)
        expected = intArrayOf(14, 14, 14, 14)
        array.setAllValues(14)
        assertContentEquals(expected, array)
    }
}
