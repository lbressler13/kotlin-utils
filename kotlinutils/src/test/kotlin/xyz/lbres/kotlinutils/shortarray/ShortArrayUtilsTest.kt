package xyz.lbres.kotlinutils.shortarray

import kotlin.test.Test
import kotlin.test.assertContentEquals

class ShortArrayUtilsTest {
    @Test
    fun testShortArrayOfValue() {
        var expected = shortArrayOf()
        assertContentEquals(expected, shortArrayOfValue(0, 5))

        expected = shortArrayOf(5)
        assertContentEquals(expected, shortArrayOfValue(1, 5))

        expected = shortArrayOf(-4)
        assertContentEquals(expected, shortArrayOfValue(1, -4))

        expected = shortArrayOf(0, 0)
        assertContentEquals(expected, shortArrayOfValue(2, 0))

        expected = shortArrayOf(12345, 12345, 12345)
        assertContentEquals(expected, shortArrayOfValue(3, 12345))

        expected = shortArrayOf(-12121, -12121, -12121, -12121, -12121)
        assertContentEquals(expected, shortArrayOfValue(5, -12121))
    }
}
