package xyz.lbres.kotlinutils.shortarray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals

class ShortArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = shortArrayOf()
        var expected = shortArrayOf()
        array.setAllValues(0)
        assertContentEquals(expected, array)

        // no change
        array = shortArrayOf(2, 2, 2, 2)
        expected = shortArrayOf(2, 2, 2, 2)
        array.setAllValues(2)
        assertContentEquals(expected, array)

        // some change
        array = shortArrayOf(0, -99, 1000)
        expected = shortArrayOf(-99, -99, -99)
        array.setAllValues(-99)
        assertContentEquals(expected, array)

        // all change
        array = shortArrayOf(100, -100, 200, 3)
        expected = shortArrayOf(14, 14, 14, 14)
        array.setAllValues(14)
        assertContentEquals(expected, array)
    }
}
