package kotlinutils.list.ext

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class ListExtTest {
    @Test
    internal fun testCopyWithReplacement() {
        assertFails { listOf<String>().copyWithReplacement(0, "a") }
        assertFails { listOf("a").copyWithReplacement(-1, "b") }
        assertFails { listOf("a", "b").copyWithReplacement(2, "c") }
        assertFails { listOf("a", "b").copyWithReplacement(8, "c") }

        var l = listOf("a")
        var i = 0
        var expected = listOf("abc")
        assertEquals(expected, l.copyWithReplacement(i, "abc"))

        l = listOf("0", "1", "1", "2", "3", "6", "8")
        i = 5
        expected = listOf("0", "1", "1", "2", "3", "5", "8")
        assertEquals(expected, l.copyWithReplacement(i, "5"))

        l = listOf("0", "1", "1", "2", "3", "6", "8")
        i = 5
        expected = listOf("0", "1", "1", "2", "3", "5", "8")
        assertEquals(expected, l.copyWithReplacement(i, "5"))

        l = listOf("hello", "goodbye", "greetings", "hey y'all")
            .copyWithReplacement(2, "farewell")
            .copyWithReplacement(0, "hey")
            .copyWithReplacement(3, "what's up")
            .copyWithReplacement(1, "byeeee")
        expected = listOf("hey", "byeeee", "farewell", "what's up")
        assertEquals(expected, l)

        val ln = listOf(0, 1, 1, 2, 3, 6, 8)
        i = 5
        val expectedNum = listOf(0, 1, 1, 2, 3, 5, 8)
        assertEquals(expectedNum, ln.copyWithReplacement(i, 5))

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val le = listOf(e1, e2, e3)
        i = 0
        val expectedErr = listOf(e3, e2, e3)
        assertEquals(expectedErr, le.copyWithReplacement(i, e3))
    }

    @Test
    internal fun testCopyWithLastReplaced() {
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
    internal fun testCopyWithFirstReplaced() {
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
    internal fun testCopyWithoutLast() {
        assertFails { listOf<String>().copyWithoutLast() }

        var l = listOf("1")
        var expected = listOf<String>()
        assertEquals(expected, l.copyWithoutLast())

        l = "Hello world this is a test".split(' ')
        expected = "Hello world this is a".split(' ')
        assertEquals(expected, l.copyWithoutLast())

        val lInt = (-1000..10000).toList()
        val expectedInt = (-1000 until 10000).toList()
        assertEquals(expected, l.copyWithoutLast())

        val e1 = ArithmeticException()
        val e2 = NumberFormatException()
        val e3 = RuntimeException()
        val le = listOf(e1, e2, e3)
        val expectedErr = listOf(e1, e2)
        assertEquals(expectedErr, le.copyWithoutLast())
    }
}
