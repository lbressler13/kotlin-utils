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
    private val longSingleValues = intSingleValues.map { it.first.toLong()..it.last.toLong() }
    private val longMultipleValues = intMultipleValues
        .map { it.first.toLong()..it.last.toLong() } + listOf(Long.MIN_VALUE..Long.MAX_VALUE)

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
