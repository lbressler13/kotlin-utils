package xyz.lbres.kotlinutils.closedrange.longrange.ext

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LongRangeExtTest {
    @Test
    fun testSize() {
        // single value
        var range: LongRange = 1L..1L
        var expected: Long = 1
        assertEquals(expected, range.size())

        // positive
        range = 10L..99L
        expected = 90
        assertEquals(expected, range.size())

        // negative
        range = -100L..-11L
        expected = 90
        assertEquals(expected, range.size())

        // postive + negative
        range = -1000L..1000L
        expected = 2001
        assertEquals(expected, range.size())

        // until
        range = -1000L until 1000L
        expected = 2000
        assertEquals(expected, range.size())
    }

    @Test
    fun testGet() {
        var range: LongRange = 0L..0L
        assertFailsWith<IndexOutOfBoundsException> { range.get(1) }
        assertFailsWith<IndexOutOfBoundsException> { range.get(-1) }

        range = 1L..10L
        assertFailsWith<IndexOutOfBoundsException> { range.get(11) }

        range = 0L..0L
        var expected: Long = 0
        assertEquals(expected, range.get(0))

        range = -3L..7L

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
