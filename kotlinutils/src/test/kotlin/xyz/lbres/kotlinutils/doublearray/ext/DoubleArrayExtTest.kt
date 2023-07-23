package xyz.lbres.kotlinutils.doublearray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class DoubleArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = doubleArrayOf()
        var expected = doubleArrayOf()
        array.setAllValues(0.0)
        assertContentEquals(expected, array)

        // no change
        array = doubleArrayOf(2.0, 2.0, 2.0, 2.0)
        expected = doubleArrayOf(2.0, 2.0, 2.0, 2.0)
        array.setAllValues(2.0)
        assertContentEquals(expected, array)

        // some change
        array = doubleArrayOf(0.0, 9.1, 2.123)
        expected = doubleArrayOf(2.123, 2.123, 2.123)
        array.setAllValues(2.123)
        assertContentEquals(expected, array)

        // all change
        array = doubleArrayOf(0.0, 9.1, 2.123)
        expected = doubleArrayOf(-12.2, -12.2, -12.2)
        array.setAllValues(-12.2)
        assertContentEquals(expected, array)
    }

    @Test
    fun testCountElement() {
        var array = doubleArrayOf()
        assertEquals(0, array.countElement(0.0))

        array = doubleArrayOf(12.007)
        assertEquals(1, array.countElement(12.007))

        array = doubleArrayOf(400.123, 400.123, 1.2, 0.3, 400.123, -19.0, 15.0)
        assertEquals(3, array.countElement(400.123))
        assertEquals(1, array.countElement(-19.0))

        array[1] = -19.0
        assertEquals(2, array.countElement(400.123))
        assertEquals(2, array.countElement(-19.0))
    }
}
