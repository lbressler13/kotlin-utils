package xyz.lbres.kotlinutils.intarray

import kotlin.test.Test
import kotlin.test.assertContentEquals

class IntArrayUtilsTest {
    @Test
    fun testIntArrayOfValue() {
        var expected = intArrayOf()
        assertContentEquals(expected, intArrayOfValue(0, 5))

        expected = intArrayOf(5)
        assertContentEquals(expected, intArrayOfValue(1, 5))

        expected = intArrayOf(-4)
        assertContentEquals(expected, intArrayOfValue(1, -4))

        expected = intArrayOf(0, 0)
        assertContentEquals(expected, intArrayOfValue(2, 0))

        expected = intArrayOf(123456, 123456, 123456)
        assertContentEquals(expected, intArrayOfValue(3, 123456))

        expected = intArrayOf(-12121212, -12121212, -12121212, -12121212, -12121212)
        assertContentEquals(expected, intArrayOfValue(5, -12121212))
    }
}
