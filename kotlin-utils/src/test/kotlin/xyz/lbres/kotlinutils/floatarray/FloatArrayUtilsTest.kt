package xyz.lbres.kotlinutils.floatarray

import kotlin.test.Test
import kotlin.test.assertContentEquals

class FloatArrayUtilsTest {
    @Test
    fun testFloatArrayOfValue() {
        var expected = floatArrayOf()
        assertContentEquals(expected, floatArrayOfValue(0, 5.0f))

        expected = floatArrayOf(5.0f)
        assertContentEquals(expected, floatArrayOfValue(1, 5.0f))

        expected = floatArrayOf(-123.567f)
        assertContentEquals(expected, floatArrayOfValue(1, -123.567f))

        expected = floatArrayOf(0.0f, 0.0f)
        assertContentEquals(expected, floatArrayOfValue(2, 0.0f))

        expected = floatArrayOf(0.0000127f, 0.0000127f, 0.0000127f)
        assertContentEquals(expected, floatArrayOfValue(3, 0.0000127f))

        expected = floatArrayOf(1000.0001f, 1000.0001f, 1000.0001f, 1000.0001f, 1000.0001f)
        assertContentEquals(expected, floatArrayOfValue(5, 1000.0001f))
    }
}
