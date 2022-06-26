package kotlinutils.closedrange.longrange

import kotlin.test.assertEquals
import kotlin.test.Test

class LongRangeUtilsTest {
    @Test
    internal fun testRangeOfLong() {
        var l = 0L
        var expected = 0L..0L
        assertEquals(expected, rangeOfLong(l))

        l = -1000L
        expected = -1000L..-1000L
        assertEquals(expected, rangeOfLong(l))

        l = Long.MAX_VALUE
        expected = Long.MAX_VALUE..Long.MAX_VALUE
        assertEquals(expected, rangeOfLong(l))

        l = Long.MIN_VALUE
        expected = Long.MIN_VALUE..Long.MIN_VALUE
        assertEquals(expected, rangeOfLong(l))
    }
}
