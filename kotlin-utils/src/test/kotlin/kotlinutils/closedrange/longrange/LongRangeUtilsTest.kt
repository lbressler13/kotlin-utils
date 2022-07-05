package kotlinutils.closedrange.longrange

import kotlin.test.Test
import kotlin.test.assertEquals

class LongRangeUtilsTest {
    @Test
    internal fun testRangeOfLong() {
        var long = 0L
        var expected = 0L..0L
        assertEquals(expected, rangeOfLong(long))

        long = -1000L
        expected = -1000L..-1000L
        assertEquals(expected, rangeOfLong(long))

        long = Long.MAX_VALUE
        expected = Long.MAX_VALUE..Long.MAX_VALUE
        assertEquals(expected, rangeOfLong(long))

        long = Long.MIN_VALUE
        expected = Long.MIN_VALUE..Long.MIN_VALUE
        assertEquals(expected, rangeOfLong(long))
    }
}
