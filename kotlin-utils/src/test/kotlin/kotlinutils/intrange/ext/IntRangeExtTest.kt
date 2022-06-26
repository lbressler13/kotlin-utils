package kotlinutils.intrange.ext

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
    internal fun testIsSingleValue() {
        // single value
        var range = 0..0
        assertTrue { range.isSingleValue() }

        range = 1000000..1000000
        assertTrue { range.isSingleValue() }

        range = -1000000..-1000000
        assertTrue { range.isSingleValue() }

        range = 1 until 2
        assertTrue { range.isSingleValue() }

        // multipleValues
        range = 0..1
        assertFalse { range.isSingleValue() }

        range = 1 until 3
        assertFalse { range.isSingleValue() }

        range = Int.MIN_VALUE..Int.MAX_VALUE
        assertFalse { range.isSingleValue() }

        range = 0 until 2
        assertFalse { range.isSingleValue() }
    }

    @Test
    internal fun testIsNotSingleValue() {
        // multiple values
        var range = 0..1
        assertTrue { range.isNotSingleValue() }

        range = 1 until 3
        assertTrue { range.isNotSingleValue() }

        range = Int.MIN_VALUE..Int.MAX_VALUE
        assertTrue { range.isNotSingleValue() }

        range = 0 until 2
        assertTrue { range.isNotSingleValue() }

        // single value
        range = 0..0
        assertFalse { range.isNotSingleValue() }

        range = 1000000..1000000
        assertFalse { range.isNotSingleValue() }

        range = -1000000..-1000000
        assertFalse { range.isNotSingleValue() }

        range = 1 until 2
        assertFalse { range.isNotSingleValue() }
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
