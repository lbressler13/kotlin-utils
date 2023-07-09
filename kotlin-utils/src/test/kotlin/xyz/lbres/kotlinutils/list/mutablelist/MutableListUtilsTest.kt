package xyz.lbres.kotlinutils.list.mutablelist

import kotlin.test.Test
import kotlin.test.assertEquals

class MutableListUtilsTest {
    @Test
    fun testMutableListOfNulls() {
        var expectedInt: MutableList<Int?> = mutableListOf()
        assertEquals(expectedInt, mutableListOfNulls(0))

        expectedInt = mutableListOf(null)
        assertEquals(expectedInt, mutableListOfNulls(1))

        expectedInt = mutableListOf(null, null, null, null, null, null, null)
        assertEquals(expectedInt, mutableListOfNulls(7))

        val expectedString: List<String?> = mutableListOf(null, null, null)
        assertEquals(expectedString, mutableListOfNulls(3))

        val expectedMutable: List<MutableList<String>?> = mutableListOf(null, null, null, null, null)
        assertEquals(expectedMutable, mutableListOfNulls(5))
    }

    @Test
    fun testMutableListOfValue() {
        var expectedInt: MutableList<Int> = mutableListOf()
        assertEquals(expectedInt, mutableListOfValue(0, 10))

        expectedInt = mutableListOf(5)
        assertEquals(expectedInt, mutableListOfValue(1, 5))

        expectedInt = mutableListOf(-123, -123, -123, -123, -123, -123, -123)
        assertEquals(expectedInt, mutableListOfValue(7, -123))

        val expectedString = mutableListOf("hello world", "hello world", "hello world")
        assertEquals(expectedString, mutableListOfValue(3, "hello world"))

        val expectedMutable = mutableListOf(mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5))
        assertEquals(expectedMutable, mutableListOfValue(5, mutableSetOf(1, 2, 3, 4, 5)))

        val exception = ArithmeticException()
        val expectedException = mutableListOf(exception, exception, exception)
        assertEquals(expectedException, mutableListOfValue(3, exception))

        val expectedNullable: MutableList<String?> = mutableListOf(null, null, null, null, null)
        assertEquals<MutableList<String?>>(expectedNullable, mutableListOfValue(5, null))
    }
}
