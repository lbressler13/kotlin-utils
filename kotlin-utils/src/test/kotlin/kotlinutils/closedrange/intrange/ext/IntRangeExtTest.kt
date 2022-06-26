package kotlinutils.closedrange.intrange.ext

import kotlin.test.*

internal class IntRangeExtTest {
    @Test
    internal fun testSize() {
        // single value
        var r = 1..1
        var expected = 1
        assertEquals(expected, r.size())

        // positive
        r = 10..99
        expected = 90
        assertEquals(expected, r.size())

        // negative
        r = -100..-11
        expected = 90
        assertEquals(expected, r.size())

        // postive + negative
        r = -1000..1000
        expected = 2001
        assertEquals(expected, r.size())

        // until
        r = -1000 until 1000
        expected = 2000
        assertEquals(expected, r.size())
    }


    @Test
    internal fun testGet() {
        var range = 0..0
        assertFailsWith<IndexOutOfBoundsException> { range.get(1) }
        assertFailsWith<IndexOutOfBoundsException> { range.get(-1) }

        range = 1..10
        assertFailsWith<IndexOutOfBoundsException> { range.get(11) }

        range = 0..0
        var expected = 0
        assertEquals(expected, range.get(0))

        range = -3..7

        expected = -3
        assertEquals(expected, range.get(0))

        expected = -2
        assertEquals(expected, range.get(1))

        expected = 7
        assertEquals(expected, range.get(10))

        expected = 1
        assertEquals(expected, range.get(4))
    }
}
