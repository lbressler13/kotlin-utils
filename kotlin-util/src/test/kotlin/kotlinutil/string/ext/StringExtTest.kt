package kotlinutil.string.ext

import kotlin.test.*

internal class StringExtTest {
    @Test
    internal fun testSubstringTo() {
        assertFailsWith<IndexOutOfBoundsException> { "".substringTo(1) }
        assertFailsWith<IndexOutOfBoundsException> { "a".substringTo(-1) }
        assertFailsWith<IndexOutOfBoundsException> { "a b".substringTo(4) }

        var s = ""
        var i = 0
        var expected = ""
        assertEquals(expected, s.substringTo(i))

        s = "hello world"

        i = 11
        expected = "hello world"
        assertEquals(expected, s.substringTo(i))

        i = 1
        expected = "h"
        assertEquals(expected, s.substringTo(i))

        i = 6
        expected = "hello "
        assertEquals(expected, s.substringTo(i))
    }

    @Test
    internal fun testIsInt() {
        // int
        var s = "0"
        assertTrue { s.isInt() }

        s = "1000000"
        assertTrue { s.isInt() }

        s = "-1000000"
        assertTrue { s.isInt() }

        s = Int.MAX_VALUE.toString()
        assertTrue { s.isInt() }

        // not int
        s = ""
        assertFalse { s.isInt() }

        s = "abc"
        assertFalse { s.isInt() }

        s = "1.0"
        assertFalse { s.isInt() }

        s = Long.MAX_VALUE.toString()
        assertFalse { s.isInt() }
    }
}
