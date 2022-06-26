package kotlinutils.closedrange.charrange.ext

import kotlin.test.*

class CharRangeExtTest {
    @Test
    internal fun testSize() {
        // single value
        var r: CharRange = Char(1)..Char(1)
        var expected = 1
        assertEquals(expected, r.size())

        // multiple values
        r = Char(10)..Char(99)
        expected = 90
        assertEquals(expected, r.size())

        // until
        r = Char(100) until Char(1000)
        expected = 900
        assertEquals(expected, r.size())
    }


    @Test
    internal fun testGet() {
        var range: CharRange = Char(0)..Char(0)
        assertFailsWith<IndexOutOfBoundsException> { range.get(1) }
        assertFailsWith<IndexOutOfBoundsException> { range.get(-1) }

        range = Char(1)..Char(10)
        assertFailsWith<IndexOutOfBoundsException> { range.get(11) }

        range = Char(0)..Char(0)
        var expected = Char(0)
        assertEquals(expected, range.get(0))

        range = Char(3)..Char(17)

        expected = Char(3)
        assertEquals(expected, range.get(0))

        expected = Char(4)
        assertEquals(expected, range.get(1))

        expected = Char(13)
        assertEquals(expected, range.get(10))

        expected = Char(7)
        assertEquals(expected, range.get(4))
    }
}
