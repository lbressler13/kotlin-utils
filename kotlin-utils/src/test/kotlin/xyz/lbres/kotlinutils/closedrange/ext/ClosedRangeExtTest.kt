package xyz.lbres.kotlinutils.closedrange.ext

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ClosedRangeExtTest {
    @Test
    internal fun testIsSingleValue() {
        // Int
        // single value
        var rangeInt: IntRange = 0..0
        assertTrue { rangeInt.isSingleValue() }

        rangeInt = 1000000..1000000
        assertTrue { rangeInt.isSingleValue() }

        rangeInt = -1000000..-1000000
        assertTrue { rangeInt.isSingleValue() }

        rangeInt = 1 until 2
        assertTrue { rangeInt.isSingleValue() }

        // multipleValues
        rangeInt = 0..1
        assertFalse { rangeInt.isSingleValue() }

        rangeInt = 1 until 3
        assertFalse { rangeInt.isSingleValue() }

        rangeInt = Int.MIN_VALUE..Int.MAX_VALUE
        assertFalse { rangeInt.isSingleValue() }

        rangeInt = 0 until 2
        assertFalse { rangeInt.isSingleValue() }

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
        var rangeInt = 0..1
        assertTrue { rangeInt.isNotSingleValue() }

        rangeInt = 1 until 3
        assertTrue { rangeInt.isNotSingleValue() }

        rangeInt = Int.MIN_VALUE..Int.MAX_VALUE
        assertTrue { rangeInt.isNotSingleValue() }

        rangeInt = 0 until 2
        assertTrue { rangeInt.isNotSingleValue() }

        // single value
        rangeInt = 0..0
        assertFalse { rangeInt.isNotSingleValue() }

        rangeInt = 1000000..1000000
        assertFalse { rangeInt.isNotSingleValue() }

        rangeInt = -1000000..-1000000
        assertFalse { rangeInt.isNotSingleValue() }

        rangeInt = 1 until 2
        assertFalse { rangeInt.isNotSingleValue() }

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
