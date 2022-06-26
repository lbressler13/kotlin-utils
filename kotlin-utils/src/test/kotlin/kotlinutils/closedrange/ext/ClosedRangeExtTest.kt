package kotlinutils.closedrange.ext

import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.Test

class ClosedRangeExtTest {
    @Test
    internal fun testIsSingleValue() {
        // Int
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

        // Char
        // single value
        var rangeChar: CharRange = Char(0)..Char(0)
        assertTrue { rangeChar.isSingleValue() }

        rangeChar = Char(1000)..Char(1000)
        assertTrue { rangeChar.isSingleValue() }

        rangeChar = Char(1) until Char(2)
        assertTrue { rangeChar.isSingleValue() }

        // multipleValues
        rangeChar = Char(0)..Char(1)
        assertFalse { rangeChar.isSingleValue() }

        rangeChar = Char(1) until Char(3)
        assertFalse { rangeChar.isSingleValue() }

        rangeChar = Char.MIN_VALUE..Char.MAX_VALUE
        assertFalse { rangeChar.isSingleValue() }

        // Long
        // single value
        var rangeLong: LongRange = 0L..0L
        assertTrue { rangeLong.isSingleValue() }

        rangeLong = 1000000L..1000000L
        assertTrue { rangeLong.isSingleValue() }

        rangeLong = -1000000L..-1000000L
        assertTrue { rangeLong.isSingleValue() }

        rangeLong = 1L until 2L
        assertTrue { rangeLong.isSingleValue() }

        // multipleValues
        rangeLong = 0L..1L
        assertFalse { rangeLong.isSingleValue() }

        rangeLong = 1L until 3L
        assertFalse { rangeLong.isSingleValue() }

        rangeLong = Long.MIN_VALUE..Long.MAX_VALUE
        assertFalse { rangeLong.isSingleValue() }

        rangeLong = 0L until 2L
        assertFalse { rangeLong.isSingleValue() }
    }

    @Test
    internal fun testIsNotSingleValue() {
        // Int
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

        // Char
        // multiple values
        var rangeChar = Char(0)..Char(1)
        assertTrue { rangeChar.isNotSingleValue() }

        rangeChar = Char(1) until Char(3)
        assertTrue { rangeChar.isNotSingleValue() }

        rangeChar = Char.MIN_VALUE..Char.MAX_VALUE
        assertTrue { rangeChar.isNotSingleValue() }

        rangeChar = Char(0) until Char(2)
        assertTrue { rangeChar.isNotSingleValue() }

        // single value
        rangeChar = Char(1)..Char(1)
        assertFalse { rangeChar.isNotSingleValue() }

        rangeChar = Char(1000)..Char(1000)
        assertFalse { rangeChar.isNotSingleValue() }

        rangeChar = Char(1) until Char(2)
        assertFalse { rangeChar.isNotSingleValue() }

        // Long
        // multiple values
        var rangeLong: LongRange = 0L..1L
        assertTrue { rangeLong.isNotSingleValue() }

        rangeLong = 1L until 3L
        assertTrue { rangeLong.isNotSingleValue() }

        rangeLong = Long.MIN_VALUE..Long.MAX_VALUE
        assertTrue { rangeLong.isNotSingleValue() }

        rangeLong = 0L until 2L
        assertTrue { rangeLong.isNotSingleValue() }

        // single value
        rangeLong = 0L..0L
        assertFalse { rangeLong.isNotSingleValue() }

        rangeLong = 1000000L..1000000L
        assertFalse { rangeLong.isNotSingleValue() }

        rangeLong = -1000000L..-1000000L
        assertFalse { rangeLong.isNotSingleValue() }

        rangeLong = 1L until 2L
        assertFalse { rangeLong.isNotSingleValue() }
    }
}
