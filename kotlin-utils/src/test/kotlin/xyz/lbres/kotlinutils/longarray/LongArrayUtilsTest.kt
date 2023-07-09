package xyz.lbres.kotlinutils.longarray

import kotlin.test.Test
import kotlin.test.assertContentEquals

class LongArrayUtilsTest {
    @Test
    fun testLongArrayOfValue() {
        var expected = longArrayOf()
        assertContentEquals(expected, longArrayOfValue(0, 5))

        expected = longArrayOf(5)
        assertContentEquals(expected, longArrayOfValue(1, 5))

        expected = longArrayOf(-4)
        assertContentEquals(expected, longArrayOfValue(1, -4))

        expected = longArrayOf(0, 0)
        assertContentEquals(expected, longArrayOfValue(2, 0))

        expected = longArrayOf(123456, 123456, 123456)
        assertContentEquals(expected, longArrayOfValue(3, 123456))

        expected = longArrayOf(-12121212, -12121212, -12121212, -12121212, -12121212)
        assertContentEquals(expected, longArrayOfValue(5, -12121212))
    }
}
