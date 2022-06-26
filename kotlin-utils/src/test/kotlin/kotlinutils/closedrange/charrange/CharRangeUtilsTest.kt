package kotlinutils.closedrange.charrange

import kotlin.test.Test
import kotlin.test.assertEquals

class CharRangeUtilsTest {
    @Test
    internal fun testRangeOfChar() {
        var c = Char(0)
        var expected = Char(0)..Char(0)
        assertEquals(expected, rangeOfChar(c))

        c = Char(1000)
        expected = Char(1000)..Char(1000)
        assertEquals(expected, rangeOfChar(c))

        c = Char.MAX_VALUE
        expected = Char.MAX_VALUE..Char.MAX_VALUE
        assertEquals(expected, rangeOfChar(c))

        c = Char.MIN_VALUE
        expected = Char.MIN_VALUE..Char.MIN_VALUE
        assertEquals(expected, rangeOfChar(c))
    }
}
