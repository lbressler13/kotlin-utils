package xyz.lbres.kotlinutils.closedrange.intrange

import kotlin.test.Test
import kotlin.test.assertEquals

internal class IntRangeUtilsTest {
    @Test
    internal fun testRangeOfInt() {
        var int = 0
        var expected = 0..0
        assertEquals(expected, rangeOfInt(int))

        int = -1000
        expected = -1000..-1000
        assertEquals(expected, rangeOfInt(int))

        int = Int.MAX_VALUE
        expected = Int.MAX_VALUE..Int.MAX_VALUE
        assertEquals(expected, rangeOfInt(int))

        int = Int.MIN_VALUE
        expected = Int.MIN_VALUE..Int.MIN_VALUE
        assertEquals(expected, rangeOfInt(int))
    }
}
