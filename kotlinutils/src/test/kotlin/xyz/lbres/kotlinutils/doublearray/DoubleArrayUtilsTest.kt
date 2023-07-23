package xyz.lbres.kotlinutils.doublearray

import kotlin.test.Test
import kotlin.test.assertContentEquals

class DoubleArrayUtilsTest {
    @Test
    fun testDoubleArrayOfValue() {
        var expected = doubleArrayOf()
        assertContentEquals(expected, doubleArrayOfValue(0, 5.0))

        expected = doubleArrayOf(5.0)
        assertContentEquals(expected, doubleArrayOfValue(1, 5.0))

        expected = doubleArrayOf(-123.567)
        assertContentEquals(expected, doubleArrayOfValue(1, -123.567))

        expected = doubleArrayOf(0.0, 0.0)
        assertContentEquals(expected, doubleArrayOfValue(2, 0.0))

        expected = doubleArrayOf(0.0000127, 0.0000127, 0.0000127)
        assertContentEquals(expected, doubleArrayOfValue(3, 0.0000127))

        expected = doubleArrayOf(100000.0000001, 100000.0000001, 100000.0000001, 100000.0000001, 100000.0000001)
        assertContentEquals(expected, doubleArrayOfValue(5, 100000.0000001))
    }
}
