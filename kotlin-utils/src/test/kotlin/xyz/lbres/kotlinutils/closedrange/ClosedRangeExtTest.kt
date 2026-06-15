package xyz.lbres.kotlinutils.closedrange

import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test

class ClosedRangeExtTest {
    @Test
    fun testIsSingleValue() {
        // Int
        val intTrueValues = listOf(
            0..0,
            1000000..1000000,
            -1000000..-1000000,
            1 until 2,
        )
        val intFalseValues = listOf(
            0..1,
            1 until 3,
            Int.MIN_VALUE..Int.MAX_VALUE,
            0 until 2
        )
        checkTrueFalse(intTrueValues, intFalseValues, { "$it.isSingleValue()" }, IntRange::isSingleValue)

        val charTrueValues = listOf(
            Char(0)..Char(0),
            Char(1000)..Char(1000),
            Char(1) until Char(2),
        )
        val charFalseValues = listOf(
            Char(0)..Char(1),
            Char(1) until Char(3),
            Char.MIN_VALUE..Char.MAX_VALUE,
        )
        checkTrueFalse(charTrueValues, charFalseValues, { "$it.isSingleValue()" }, CharRange::isSingleValue)

        val longTrueValues = listOf(
            0L..0L,
            1000000L..1000000L,
            -1000000L..-1000000L,
            1L until 2L,
        )
        val longFalseValues = listOf(
            0L..1L,
            1L until 3L,
            Long.MIN_VALUE..Long.MAX_VALUE,
        )
        checkTrueFalse(longTrueValues, longFalseValues, { "$it.isSingleValue()" }, LongRange::isSingleValue)
    }

    @Test
    fun testIsNotSingleValue() {
        // Int
        val intTrueValues = listOf(
            0..1,
            1 until 3,
            Int.MIN_VALUE..Int.MAX_VALUE,
            0 until 2
        )
        val intFalseValues = listOf(
            0..0,
            1000000..1000000,
            -1000000..-1000000,
            1 until 2,
        )
        checkTrueFalse(intTrueValues, intFalseValues, { "$it.isNotSingleValue()" }, IntRange::isNotSingleValue)

        val charTrueValues = listOf(
            Char(0)..Char(1),
            Char(1) until Char(3),
            Char.MIN_VALUE..Char.MAX_VALUE,
        )
        val charFalseValues = listOf(
            Char(0)..Char(0),
            Char(1000)..Char(1000),
            Char(1) until Char(2),
        )
        checkTrueFalse(charTrueValues, charFalseValues, { "$it.isNotSingleValue()" }, CharRange::isNotSingleValue)

        val longTrueValues = listOf(
            0L..1L,
            1L until 3L,
            Long.MIN_VALUE..Long.MAX_VALUE,
        )
        val longFalseValues = listOf(
            0L..0L,
            1000000L..1000000L,
            -1000000L..-1000000L,
            1L until 2L,
        )
        checkTrueFalse(longTrueValues, longFalseValues, { "$it.isNotSingleValue()" }, LongRange::isNotSingleValue)
    }
}
