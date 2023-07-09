package xyz.lbres.kotlinutils.floatarray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class FloatArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = floatArrayOf()
        var expected = floatArrayOf()
        array.setAllValues(0f)
        assertContentEquals(expected, array)

        // no change
        array = floatArrayOf(2f, 2f, 2f, 2f)
        expected = floatArrayOf(2f, 2f, 2f, 2f)
        array.setAllValues(2f)
        assertContentEquals(expected, array)

        // some change
        array = floatArrayOf(0f, 9.1f, 2.123f)
        expected = floatArrayOf(2.123f, 2.123f, 2.123f)
        array.setAllValues(2.123f)
        assertContentEquals(expected, array)

        // all change
        array = floatArrayOf(0f, 9.1f, 2.123f)
        expected = floatArrayOf(-12.2f, -12.2f, -12.2f)
        array.setAllValues(-12.2f)
        assertContentEquals(expected, array)
    }

    @Test
    fun testCountElement() {
        var array = floatArrayOf()
        assertEquals(0, array.countElement(0f))

        array = floatArrayOf(12.007f)
        assertEquals(1, array.countElement(12.007f))

        array = floatArrayOf(400.123f, 400.123f, 1.2f, 0.3f, 400.123f, -19f, 15f)
        assertEquals(3, array.countElement(400.123f))
        assertEquals(1, array.countElement(-19f))

        array[1] = -19f
        assertEquals(2, array.countElement(400.123f))
        assertEquals(2, array.countElement(-19f))
    }
}
