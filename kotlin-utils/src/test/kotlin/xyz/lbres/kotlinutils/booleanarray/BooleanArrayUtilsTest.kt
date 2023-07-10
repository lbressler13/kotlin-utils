package xyz.lbres.kotlinutils.booleanarray

import kotlin.test.Test
import kotlin.test.assertContentEquals

class BooleanArrayUtilsTest {
    @Test
    fun testBooleanArrayOfValue() {
        var expected = booleanArrayOf()
        assertContentEquals(expected, booleanArrayOfValue(0, true))

        expected = booleanArrayOf(true)
        assertContentEquals(expected, booleanArrayOfValue(1, true))

        expected = booleanArrayOf(false)
        assertContentEquals(expected, booleanArrayOfValue(1, false))

        expected = booleanArrayOf(true, true, true)
        assertContentEquals(expected, booleanArrayOfValue(3, true))

        expected = booleanArrayOf(false, false, false, false, false)
        assertContentEquals(expected, booleanArrayOfValue(5, false))
    }
}
