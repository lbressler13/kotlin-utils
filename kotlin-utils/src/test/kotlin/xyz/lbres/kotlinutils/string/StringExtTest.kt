package xyz.lbres.kotlinutils.string

import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StringExtTest {
    @Test
    fun testSubstringTo() {
        assertFailsWith<IndexOutOfBoundsException> { "".substringTo(1) }
        assertFailsWith<IndexOutOfBoundsException> { "a".substringTo(-1) }
        assertFailsWith<IndexOutOfBoundsException> { "a b".substringTo(4) }

        var string = ""
        var int = 0
        var expected = ""
        assertEquals(expected, string.substringTo(int))

        string = "hello world"

        int = 11
        expected = "hello world"
        assertEquals(expected, string.substringTo(int))

        int = 1
        expected = "h"
        assertEquals(expected, string.substringTo(int))

        int = 6
        expected = "hello "
        assertEquals(expected, string.substringTo(int))
    }

    @Test
    fun testCountElement() {
        var string = ""
        assertEquals(0, string.countElement('-'))
        assertEquals(0, string.countElement('a'))

        string = "hello world"
        assertEquals(0, string.countElement('a'))
        assertEquals(1, string.countElement('e'))
        assertEquals(3, string.countElement('l'))

        string = "%#\\%   \n \\.$?'' \" 919"
        assertEquals(6, string.countElement(' '))
        assertEquals(2, string.countElement('\\'))
        assertEquals(2, string.countElement('\''))
        assertEquals(1, string.countElement('"'))
        assertEquals(2, string.countElement('9'))
        assertEquals(1, string.countElement('$'))
        assertEquals(1, string.countElement('\n'))
    }

    @Test
    fun testIsInt() {
        val trueValues = listOf("0", "1000000", "-1000000", Int.MAX_VALUE.toString())
        val falseValues = listOf("", "abc", "1.0", Long.MAX_VALUE.toString())
        checkTrueFalse(trueValues, falseValues, { "$it.isInt()" }, String::isInt)
    }
}
