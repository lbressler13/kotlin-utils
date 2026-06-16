package xyz.lbres.kotlinutils.collection.list

import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ListExtTest {
    @Test
    fun testWithReplacementAt() {
        assertFails { listOf<String>().withReplacementAt(0, "a") }
        assertFails { listOf("a").withReplacementAt(-1, "b") }
        assertFails { listOf("a", "b").withReplacementAt(2, "c") }
        assertFails { listOf("a", "b").withReplacementAt(8, "c") }

        var listString = listOf("a")
        var index = 0
        var expectedString = listOf("abc")
        assertEquals(expectedString, listString.withReplacementAt(index, "abc"))

        listString = listOf("0", "1", "1", "2", "3", "6", "8")
        index = 5
        expectedString = listOf("0", "1", "1", "2", "3", "5", "8")
        assertEquals(expectedString, listString.withReplacementAt(index, "5"))

        listString = listOf("0", "1", "1", "2", "3", "6", "8")
        index = 5
        expectedString = listOf("0", "1", "1", "2", "3", "5", "8")
        assertEquals(expectedString, listString.withReplacementAt(index, "5"))

        listString = listOf("hello", "goodbye", "greetings", "hey y'all")
            .withReplacementAt(2, "farewell")
            .withReplacementAt(0, "hey")
            .withReplacementAt(3, "what's up")
            .withReplacementAt(1, "bye")
        expectedString = listOf("hey", "bye", "farewell", "what's up")
        assertEquals(expectedString, listString)

        val listInt = listOf(0, 1, 1, 2, 3, 6, 8)
        index = 5
        val expectedInt = listOf(0, 1, 1, 2, 3, 5, 8)
        assertEquals(expectedInt, listInt.withReplacementAt(index, 5))

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val listException = listOf(e1, e2, e3)
        index = 0
        val expectedException = listOf(e3, e2, e3)
        assertEquals(expectedException, listException.withReplacementAt(index, e3))
    }

    @Test
    fun testWithLastReplaced() {
        assertFails { listOf<String>().withLastReplaced("a") }

        var l = listOf("a")
        var expected = listOf("b")
        assertEquals(expected, l.withLastReplaced("b"))

        l = listOf("abc", "abc")
        expected = listOf("abc", "12")
        assertEquals(expected, l.withLastReplaced("12"))

        l = listOf("a b", "a b")
        expected = listOf("a b", "b")
        assertEquals(expected, l.withLastReplaced("b"))

        l = "A word another word and another and another and another".split(' ')
        expected = "A word another word and another and another and".split(' ') + "not another"
        assertEquals(expected, l.withLastReplaced("not another"))

        var ln = listOf(19, 107, 3)
        var expectedNum = listOf(19, 107, -1)
        assertEquals(expectedNum, ln.withLastReplaced(-1))

        ln = (-1000..10000).toList()
        expectedNum = (-1000..9999).toList() + (-1001)
        assertEquals(expectedNum, ln.withLastReplaced(-1001))

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val le = listOf(e1, e2, e3)
        val expectedErr = listOf(e1, e2, e2)
        assertEquals(expectedErr, le.withLastReplaced(e2))
    }

    @Test
    fun testWithFirstReplaced() {
        assertFails { listOf<String>().withFirstReplaced("a") }

        var l = listOf("a")
        var expected = listOf("b")
        assertEquals(expected, l.withFirstReplaced("b"))

        l = listOf("abc", "abc")
        expected = listOf("12", "abc")
        assertEquals(expected, l.withFirstReplaced("12"))

        l = listOf("a b", "a b")
        expected = listOf("b", "a b")
        assertEquals(expected, l.withFirstReplaced("b"))

        l = "A word another word and another and another and another".split(' ')
        expected = listOf("A single") + "word another word and another and another and another".split(' ')
        assertEquals(expected, l.withFirstReplaced("A single"))

        var ln = listOf(19, 107, 3)
        var expectedNum = listOf(-1, 107, 3)
        assertEquals(expectedNum, ln.withFirstReplaced(-1))

        ln = (-1000..10000).toList()
        expectedNum = listOf(10001) + (-999..10000).toList()
        assertEquals(expectedNum, ln.withFirstReplaced(10001))

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val le = listOf(e1, e2, e3)
        val expectedErr = listOf(e2, e2, e3)
        assertEquals(expectedErr, le.withFirstReplaced(e2))
    }

    @Test
    fun testWithoutLast() {
        assertFails { listOf<String>().withoutLast() }

        var listString = listOf("1")
        var expectedString = listOf<String>()
        assertEquals(expectedString, listString.withoutLast())

        listString = "Hello world this is a test".split(' ')
        expectedString = "Hello world this is a".split(' ')
        assertEquals(expectedString, listString.withoutLast())

        val listInt = (-1000..10000).toList()
        val expectedInt = (-1000 until 10000).toList()
        assertEquals(expectedInt, listInt.withoutLast())

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val listException = listOf(e1, e2, e3)
        val expectedException = listOf(e1, e2)
        assertEquals(expectedException, listException.withoutLast())
    }

    @Test
    fun testIsSingleValue() {
        val trueValues = listOf(
            listOf(5),
            listOf(-1000),
            listOf(""),
            listOf("1 1 1 1 1 1"),
        )
        val falseValues = listOf(
            emptyList<Int>(),
            listOf(1, 1),
            listOf(9, 44, 512, 5, -11110004, 124, 59, 0, 111, 2424),
            listOf("123", "456", "789"),
        )
        checkTrueFalse(trueValues, falseValues, { "$it.isSingleValue()" }) { it.isSingleValue() }
    }

    @Test
    fun testElementsEqual() {
        // same order
        var intList1: IntList = emptyList()
        assertTrue(intList1.elementsEqual(intList1))

        intList1 = listOf(1)
        assertTrue(intList1.elementsEqual(intList1))

        intList1 = listOf(1, 2, 3, 5)
        assertTrue(intList1.elementsEqual(intList1))

        var stringList1 = listOf("hello", "world", "goodbye", "world", "farewell", "planet")
        assertTrue(stringList1.elementsEqual(stringList1))

        // different order
        intList1 = listOf(1, 2, 3, 4, 5)
        var intList2 = listOf(5, 4, 3, 2, 1)
        assertTrue(intList1.elementsEqual(intList2))

        intList1 = listOf(1, 1, 2, 3)
        intList2 = listOf(1, 2, 1, 3)
        assertTrue(intList1.elementsEqual(intList2))

        stringList1 = listOf("", "", "abc", "abc", "", "def", "abc")
        var stringList2 = listOf("def", "abc", "", "", "abc", "", "abc")
        assertTrue(stringList1.elementsEqual(stringList2))

        val e1 = ArithmeticException()
        val e2 = NullPointerException()
        var exceptionList1 = listOf(e1, e2, e2)
        var exceptionList2 = listOf(e2, e2, e1)
        assertTrue(exceptionList1.elementsEqual(exceptionList2))

        // not equal
        intList1 = emptyList()
        intList2 = listOf(1)
        assertFalse(intList1.elementsEqual(intList2))
        assertFalse(intList2.elementsEqual(intList1))

        intList1 = listOf(1, 2, 3, 3)
        intList2 = listOf(1, 2, 3)
        assertFalse(intList1.elementsEqual(intList2))

        stringList1 = listOf("abc", "def")
        stringList2 = listOf("ghi", "jkl")
        assertFalse(stringList1.elementsEqual(stringList2))

        exceptionList1 = listOf(e1, e2, NullPointerException())
        exceptionList2 = listOf(e1, e2, NullPointerException())
        assertFalse(exceptionList1.elementsEqual(exceptionList2))
    }

    @Test
    fun testTimes() {
        var intList: List<Int> = emptyList()
        var intExpected: List<Int> = emptyList()
        assertEquals(intExpected, intList * 0)
        assertEquals(intExpected, intList * 10)

        intList = listOf(1)
        assertEquals(intExpected, intList * 0)
        intExpected = List(10) { 1 }
        assertEquals(intExpected, intList * 10)

        val stringList = listOf("123", "hello", "123", "45")
        val stringExpected =
            listOf("123", "123", "123", "123", "123", "123", "45", "45", "45", "hello", "hello", "hello")
        assertEquals(stringExpected.sorted(), (stringList * 3).sorted())

        var nullableList: List<Any?> = listOf(null, null)
        var nullableExpected: List<Any?> = listOf(null, null, null, null, null, null, null, null)
        assertEquals(nullableExpected, nullableList * 4)
        nullableList = listOf(123, "123", null, 1)
        nullableExpected = listOf(123, 123, "123", "123", null, null, 1, 1)
        val sortMethod: (Any?, Any?) -> Int = { val1, val2 ->
            val str1 = "${val1}_${val1?.javaClass}"
            val str2 = "${val2}_${val2?.javaClass}"
            str1.compareTo(str2)
        }
        assertEquals(nullableExpected.sortedWith(sortMethod), (nullableList * 2).sortedWith(sortMethod))
    }
}
