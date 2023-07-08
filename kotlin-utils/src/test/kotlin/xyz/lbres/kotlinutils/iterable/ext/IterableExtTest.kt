package xyz.lbres.kotlinutils.iterable.ext

import xyz.lbres.kotlinutils.pair.TypePair
import kotlin.test.Test
import kotlin.test.assertEquals

class IterableExtTest {
    @Test
    fun testForEachWith() {
        val intIntPairs: MutableList<TypePair<Int>> = mutableListOf()
        var intRange1 = 5..8
        val intRange2 = 0..2
        intRange1.forEachWith(intRange2) { first, second ->
            intIntPairs.add(Pair(first, second))
        }
        val intIntExpectedPairs = listOf(
            Pair(5, 0), Pair(5, 1), Pair(5, 2),
            Pair(6, 0), Pair(6, 1), Pair(6, 2),
            Pair(7, 0), Pair(7, 1), Pair(7, 2),
            Pair(8, 0), Pair(8, 1), Pair(8, 2),
        )
        assertEquals(intIntExpectedPairs, intIntPairs)

        var intResult = 0
        intRange1.forEachWith(intRange2) { first, second ->
            intResult += first * second
        }
        var intExpected = 78
        assertEquals(intExpected, intResult)

        intRange1 = 2..4
        val stringList1 = listOf("abc", "def")
        var stringResult = ""
        stringList1.forEachWith(intRange1) { text, count ->
            repeat(count) { stringResult += text }
        }
        var stringExpected = "abcabcabcabcabcabcabcabcabcdefdefdefdefdefdefdefdefdef"
        assertEquals(stringExpected, stringResult)

        var listList1 = listOf(listOf(2, 5), listOf(17, -1, 4), listOf(5, 6, 7, 4))
        stringResult = ""
        stringList1.forEachWith(listList1) { text, list ->
            repeat(list.size) { stringResult += text }
        }
        stringExpected = "abcabcabcabcabcabcabcabcabcdefdefdefdefdefdefdefdefdef"
        assertEquals(stringExpected, stringResult)

        val listList2 = listOf(listOf(15, 5), listOf(16), listOf(-1, 4, 5), listOf(1, 100, 123, 4249))
        intResult = 0
        listList1.forEachWith(listList2) { list1, list2 ->
            val diff = list2.minOrNull()!! - list1.minOrNull()!!
            intResult += diff * diff
        }
        intExpected = 723
        assertEquals(intExpected, intResult)

        listList1 = emptyList()
        intResult = 0
        listList1.forEachWith(intRange1) { _, _ -> intResult++ }
        intExpected = 0
        assertEquals(intExpected, intResult)

        intResult = 0
        intRange1.forEachWith(listList1) { _, _ -> intResult++ }
        intExpected = 0
        assertEquals(intExpected, intResult)

        intResult = 0
        listList1.forEachWith(listList1) { _, _ -> intResult++ }
        intExpected = 0
        assertEquals(intExpected, intResult)
    }

    @Test
    fun testForEachWithIndexed() {
        val intIntPairs: MutableList<TypePair<Int>> = mutableListOf()
        val intRange1 = 5..8
        var intRange2 = 0..2
        intRange1.forEachWithIndexed(intRange2) { pair1, pair2 ->
            intIntPairs.add(Pair(pair1.index, pair2.value))
        }
        val intIntExpectedPairs = listOf(
            Pair(0, 0), Pair(0, 1), Pair(0, 2),
            Pair(1, 0), Pair(1, 1), Pair(1, 2),
            Pair(2, 0), Pair(2, 1), Pair(2, 2),
            Pair(3, 0), Pair(3, 1), Pair(3, 2),
        )
        assertEquals(intIntExpectedPairs, intIntPairs)

        intRange2 = 11..12
        var intResult = 0
        intRange2.forEachWithIndexed(intRange1) { pair1, pair2 ->
            intResult += pair1.value * pair2.value + pair1.index * pair2.index
        }
        var intExpected = 604
        assertEquals(intExpected, intResult)

        var stringList1 = listOf("abc", "def")
        intRange2 = 2..4
        val stringListResult: MutableList<String> = mutableListOf()
        stringList1.forEachWithIndexed(intRange2) { stringPair, intPair ->
            val text = "(${stringPair.value}, ${intPair.value}): (${stringPair.index}, ${intPair.index})"
            stringListResult.add(text)
        }
        val stringExpected = listOf(
            "(abc, 2): (0, 0)", "(abc, 3): (0, 1)", "(abc, 4): (0, 2)",
            "(def, 2): (1, 0)", "(def, 3): (1, 1)", "(def, 4): (1, 2)",
        )
        assertEquals(stringExpected, stringListResult)

        stringList1 = emptyList()
        val listList1 = listOf(listOf(1, 2, 3))
        intResult = 0
        listList1.forEachWith(stringList1) { _, _ -> intResult++ }
        intExpected = 0
        assertEquals(intExpected, intResult)

        intResult = 0
        intRange1.forEachWith(stringList1) { _, _ -> intResult++ }
        intExpected = 0
        assertEquals(intExpected, intResult)

        intResult = 0
        stringList1.forEachWith(stringList1) { _, _ -> intResult++ }
        intExpected = 0
        assertEquals(intExpected, intResult)
    }
}
