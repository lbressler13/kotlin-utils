package xyz.lbres.kotlinutils.string.ext

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
        // int
        var string = "0"
        assertTrue { string.isInt() }

        string = "1000000"
        assertTrue { string.isInt() }

        string = "-1000000"
        assertTrue { string.isInt() }

        string = Int.MAX_VALUE.toString()
        assertTrue { string.isInt() }

        // not int
        string = ""
        assertFalse { string.isInt() }

        string = "abc"
        assertFalse { string.isInt() }

        string = "1.0"
        assertFalse { string.isInt() }

        string = Long.MAX_VALUE.toString()
        assertFalse { string.isInt() }
    }
}
