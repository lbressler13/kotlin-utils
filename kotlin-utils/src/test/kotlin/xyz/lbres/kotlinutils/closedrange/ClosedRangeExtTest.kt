package xyz.lbres.kotlinutils.closedrange

import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test

class ClosedRangeExtTest {
    private val intSingleValues = listOf(
        0..0,
        1000000..1000000,
        -1000000..-1000000,
        1 until 2,
    )
    private val intMultipleValues = listOf(
        0..1,
        1 until 3,
        Int.MIN_VALUE..Int.MAX_VALUE,
        0 until 2
    )
    private val charSingleValues = listOf(
        Char(0)..Char(0),
        Char(1000)..Char(1000),
        Char(1) until Char(2),
    )
    private val charMultipleValues = listOf(
        Char(0)..Char(1),
        Char(1) until Char(3),
        Char.MIN_VALUE..Char.MAX_VALUE,
    )
    private val longSingleValues = listOf(
        0L..0L,
        1000000L..1000000L,
        -1000000L..-1000000L,
        1L until 2L,
    )
    private val longMultipleValues = listOf(
        0L..1L,
        1L until 3L,
        Long.MIN_VALUE..Long.MAX_VALUE,
    )

    @Test
    fun testIsSingleValue() {
        checkTrueFalse(intSingleValues, intMultipleValues, { "$it.isSingleValue()" }, IntRange::isSingleValue)
        checkTrueFalse(charSingleValues, charMultipleValues, { "$it.isSingleValue()" }, CharRange::isSingleValue)
        checkTrueFalse(longSingleValues, longMultipleValues, { "$it.isSingleValue()" }, LongRange::isSingleValue)
    }

    @Test
    fun testIsNotSingleValue() {
        checkTrueFalse(intMultipleValues, intSingleValues, { "$it.isNotSingleValue()" }, IntRange::isNotSingleValue)
        checkTrueFalse(charMultipleValues, charSingleValues, { "$it.isNotSingleValue()" }, CharRange::isNotSingleValue)
        checkTrueFalse(longMultipleValues, longSingleValues, { "$it.isNotSingleValue()" }, LongRange::isNotSingleValue)
    }
}
