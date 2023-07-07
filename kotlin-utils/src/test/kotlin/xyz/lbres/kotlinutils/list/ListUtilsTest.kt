package xyz.lbres.kotlinutils.list

import kotlin.test.Test
import kotlin.test.assertEquals

class ListUtilsTest {
    @Test
    fun testListOfNulls() {
        var expectedInt: List<Int?> = emptyList()
        assertEquals(expectedInt, listOfNulls(0))

        expectedInt = listOf(null)
        assertEquals(expectedInt, listOfNulls(1))

        expectedInt = listOf(null, null, null, null, null, null, null)
        assertEquals(expectedInt, listOfNulls(7))

        val expectedString: List<String?> = listOf(null, null, null)
        assertEquals(expectedString, listOfNulls(3))

        val expectedMutable: List<MutableList<String>?> = listOf(null, null, null, null, null)
        assertEquals(expectedMutable, listOfNulls(5))
    }

    @Test
    fun testListOfValue() {
        var expectedInt: IntList = emptyList()
        assertEquals(expectedInt, listOfValue(0, 10))

        expectedInt = listOf(5)
        assertEquals(expectedInt, listOfValue(1, 5))

        expectedInt = listOf(-123, -123, -123, -123, -123, -123, -123)
        assertEquals(expectedInt, listOfValue(7, -123))

        val expectedString = listOf("hello world", "hello world", "hello world")
        assertEquals(expectedString, listOfValue(3, "hello world"))

        val expectedMutable = listOf(mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5), mutableSetOf(1, 2, 3, 4, 5))
        assertEquals(expectedMutable, listOfValue(5, mutableSetOf(1, 2, 3, 4, 5)))

        val exception = ArithmeticException()
        val expectedException = listOf(exception, exception, exception)
        assertEquals(expectedException, listOfValue(3, exception))
    }
}
