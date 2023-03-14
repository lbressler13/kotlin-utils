package xyz.lbres.kotlinutils.floatarray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals

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
}
