package xyz.lbres.kotlinutils.intarray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

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

    @Test
    fun testCountElement() {
        var array = intArrayOf()
        assertEquals(0, array.countElement(0))

        array = intArrayOf(12)
        assertEquals(1, array.countElement(12))

        array = intArrayOf(400123, 400123, 12, 3, 400123, -19, 15)
        assertEquals(3, array.countElement(400123))
        assertEquals(1, array.countElement(-19))

        array[1] = -19
        assertEquals(2, array.countElement(400123))
        assertEquals(2, array.countElement(-19))
    }
}
