package kotlinutils.closedrange.longrange.ext

import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertFailsWith

class LongRangeExtTest {
    @Test
    internal fun testSize() {
        // single value
        var r: LongRange = 1L..1L
        var expected: Long = 1
        assertEquals(expected, r.size())

        // positive
        r = 10L..99L
        expected = 90
        assertEquals(expected, r.size())

        // negative
        r = -100L..-11L
        expected = 90
        assertEquals(expected, r.size())

        // postive + negative
        r = -1000L..1000L
        expected = 2001
        assertEquals(expected, r.size())

        // until
        r = -1000L until 1000L
        expected = 2000
        assertEquals(expected, r.size())
    }


    @Test
    internal fun testGet() {
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
