package xyz.lbres.kotlinutils.array

import kotlin.test.Test
import kotlin.test.assertContentEquals

class ArrayUtilsTest {
    @Test
    fun testArrayOfValue() {
        var expectedInt: Array<Int> = emptyArray()
        assertContentEquals(expectedInt, arrayOfValue(0, 10))

        expectedInt = arrayOf(5)
        assertContentEquals(expectedInt, arrayOfValue(1, 5))

        expectedInt = arrayOf(-123, -123, -123, -123, -123, -123, -123)
        assertContentEquals(expectedInt, arrayOfValue(7, -123))

        val expectedString = arrayOf("hello world", "hello world", "hello world")
        assertContentEquals(expectedString, arrayOfValue(3, "hello world"))

        val expectedMutable = arrayOf(mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5))
        assertContentEquals(expectedMutable, arrayOfValue(5, mutableSetOf(1, 2, 3, 4, 5)))

        val exception = ArithmeticException()
        val expectedException = arrayOf(exception, exception, exception)
        assertContentEquals(expectedException, arrayOfValue(3, exception))

        val expectedNullable: Array<String?> = arrayOf(null, null, null, null, null)
        assertContentEquals(expectedNullable, arrayOfValue(5, null))
    }

    @Test
    fun testArrayOfNull() {
        var expectedInt: Array<Int?> = emptyArray()
        assertContentEquals(expectedInt, arrayOfNulls(0))

        expectedInt = arrayOf(null)
        assertContentEquals(expectedInt, arrayOfNulls(1))

        expectedInt = arrayOf(null, null, null, null, null, null, null)
        assertContentEquals(expectedInt, arrayOfNulls(7))

        val expectedString: Array<String?> = arrayOf(null, null, null)
        assertContentEquals(expectedString, arrayOfNulls(3))

        val expectedMutable: Array<MutableList<String>?> = arrayOf(null, null, null, null, null)
        assertContentEquals(expectedMutable, arrayOfNulls(5))
    }
}
