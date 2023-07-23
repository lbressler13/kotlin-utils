package xyz.lbres.kotlinutils.list.ext

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ListExtTest {
    @Test
    fun testCopyWithReplacement() {
        assertFails { listOf<String>().copyWithReplacement(0, "a") }
        assertFails { listOf("a").copyWithReplacement(-1, "b") }
        assertFails { listOf("a", "b").copyWithReplacement(2, "c") }
        assertFails { listOf("a", "b").copyWithReplacement(8, "c") }

        var listString = listOf("a")
        var index = 0
        var expectedString = listOf("abc")
        assertEquals(expectedString, listString.copyWithReplacement(index, "abc"))

        listString = listOf("0", "1", "1", "2", "3", "6", "8")
        index = 5
        expectedString = listOf("0", "1", "1", "2", "3", "5", "8")
        assertEquals(expectedString, listString.copyWithReplacement(index, "5"))

        listString = listOf("0", "1", "1", "2", "3", "6", "8")
        index = 5
        expectedString = listOf("0", "1", "1", "2", "3", "5", "8")
        assertEquals(expectedString, listString.copyWithReplacement(index, "5"))

        listString = listOf("hello", "goodbye", "greetings", "hey y'all")
            .copyWithReplacement(2, "farewell")
            .copyWithReplacement(0, "hey")
            .copyWithReplacement(3, "what's up")
            .copyWithReplacement(1, "byeeee")
        expectedString = listOf("hey", "byeeee", "farewell", "what's up")
        assertEquals(expectedString, listString)

        val listInt = listOf(0, 1, 1, 2, 3, 6, 8)
        index = 5
        val expectedInt = listOf(0, 1, 1, 2, 3, 5, 8)
        assertEquals(expectedInt, listInt.copyWithReplacement(index, 5))

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val listException = listOf(e1, e2, e3)
        index = 0
        val expectedException = listOf(e3, e2, e3)
        assertEquals(expectedException, listException.copyWithReplacement(index, e3))
    }

    @Test
    fun testCopyWithLastReplaced() {
        assertFails { listOf<String>().copyWithLastReplaced("a") }

        var l = listOf("a")
        var expected = listOf("b")
        assertEquals(expected, l.copyWithLastReplaced("b"))

        l = listOf("abc", "abc")
        expected = listOf("abc", "12")
        assertEquals(expected, l.copyWithLastReplaced("12"))

        l = listOf("a b", "a b")
        expected = listOf("a b", "b")
        assertEquals(expected, l.copyWithLastReplaced("b"))

        l = "A word another word and another and another and another".split(' ')
        expected = "A word another word and another and another and".split(' ') + "not another"
        assertEquals(expected, l.copyWithLastReplaced("not another"))

        var ln = listOf(19, 107, 3)
        var expectedNum = listOf(19, 107, -1)
        assertEquals(expectedNum, ln.copyWithLastReplaced(-1))

        ln = (-1000..10000).toList()
        expectedNum = (-1000..9999).toList() + (-1001)
        assertEquals(expectedNum, ln.copyWithLastReplaced(-1001))

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val le = listOf(e1, e2, e3)
        val expectedErr = listOf(e1, e2, e2)
        assertEquals(expectedErr, le.copyWithLastReplaced(e2))
    }

    @Test
    fun testCopyWithFirstReplaced() {
        assertFails { listOf<String>().copyWithFirstReplaced("a") }

        var l = listOf("a")
        var expected = listOf("b")
        assertEquals(expected, l.copyWithFirstReplaced("b"))

        l = listOf("abc", "abc")
        expected = listOf("12", "abc")
        assertEquals(expected, l.copyWithFirstReplaced("12"))

        l = listOf("a b", "a b")
        expected = listOf("b", "a b")
        assertEquals(expected, l.copyWithFirstReplaced("b"))

        l = "A word another word and another and another and another".split(' ')
        expected = listOf("A single") + "word another word and another and another and another".split(' ')
        assertEquals(expected, l.copyWithFirstReplaced("A single"))

        var ln = listOf(19, 107, 3)
        var expectedNum = listOf(-1, 107, 3)
        assertEquals(expectedNum, ln.copyWithFirstReplaced(-1))

        ln = (-1000..10000).toList()
        expectedNum = listOf(10001) + (-999..10000).toList()
        assertEquals(expectedNum, ln.copyWithFirstReplaced(10001))

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val le = listOf(e1, e2, e3)
        val expectedErr = listOf(e2, e2, e3)
        assertEquals(expectedErr, le.copyWithFirstReplaced(e2))
    }

    @Test
    fun testCopyWithoutLast() {
        assertFails { listOf<String>().copyWithoutLast() }

        var listString = listOf("1")
        var expectedString = listOf<String>()
        assertEquals(expectedString, listString.copyWithoutLast())

        listString = "Hello world this is a test".split(' ')
        expectedString = "Hello world this is a".split(' ')
        assertEquals(expectedString, listString.copyWithoutLast())

        val listInt = (-1000..10000).toList()
        val expectedInt = (-1000 until 10000).toList()
        assertEquals(expectedInt, listInt.copyWithoutLast())

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val listException = listOf(e1, e2, e3)
        val expectedException = listOf(e1, e2)
        assertEquals(expectedException, listException.copyWithoutLast())
    }

    @Test
    fun testIsSingleValue() {
        // single value
        var intList = listOf(5)
        assertTrue(intList.isSingleValue())

        intList = listOf(-1000)
        assertTrue(intList.isSingleValue())

        var stringList = listOf("")
        assertTrue(stringList.isSingleValue())

        stringList = listOf("1 1 1 1 1 1")
        assertTrue(stringList.isSingleValue())

        // not single value
        intList = emptyList()
        assertFalse(intList.isSingleValue())

        intList = listOf(1, 1)
        assertFalse(intList.isSingleValue())

        intList = listOf(9, 44, 512, 5, -11110004, 124, 59, 0, 111, 2424)
        assertFalse(intList.isSingleValue())

        stringList = listOf("123", "456", "789")
        assertFalse(stringList.isSingleValue())
    }
}
