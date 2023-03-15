package xyz.lbres.kotlinutils.booleanarray.ext

import kotlin.test.Test
import kotlin.test.assertContentEquals

class BooleanArrayExtTest {
    @Test
    fun testSetAllValues() {
        // empty array
        var array = booleanArrayOf()
        var expected = booleanArrayOf()
        array.setAllValues(true)
        assertContentEquals(expected, array)

        // no change
        array = booleanArrayOf(true, true, true)
        expected = booleanArrayOf(true, true, true)
        array.setAllValues(true)
        assertContentEquals(expected, array)

        array = booleanArrayOf(false, false, false)
        expected = booleanArrayOf(false, false, false)
        array.setAllValues(false)
        assertContentEquals(expected, array)

        // some change
        array = booleanArrayOf(true, false, false)
        expected = booleanArrayOf(true, true, true)
        array.setAllValues(true)
        assertContentEquals(expected, array)

        array = booleanArrayOf(true, false, false)
        expected = booleanArrayOf(false, false, false)
        array.setAllValues(false)
        assertContentEquals(expected, array)

        // all change
        array = booleanArrayOf(true, true, true)
        expected = booleanArrayOf(false, false, false)
        array.setAllValues(false)
        assertContentEquals(expected, array)

        array = booleanArrayOf(false, false, false)
        expected = booleanArrayOf(true, true, true)
        array.setAllValues(true)
        assertContentEquals(expected, array)
    }
}
