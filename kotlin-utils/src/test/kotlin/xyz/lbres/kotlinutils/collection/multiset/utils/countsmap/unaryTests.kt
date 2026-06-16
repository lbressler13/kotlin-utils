package xyz.lbres.kotlinutils.collection.multiset.utils.countsmap

import xyz.lbres.kotlinutils.collection.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.assertEquals

fun runGetCountOfTests() {
    var intCounts: CountsMap<Int> = CountsMap.from(emptyList())
    assertEquals(0, intCounts.getCountOf(0))
    assertEquals(0, intCounts.getCountOf(100))

    intCounts = CountsMap.from(listOf(2))
    assertEquals(1, intCounts.getCountOf(2))
    assertEquals(0, intCounts.getCountOf(1))

    intCounts = CountsMap.from(listOf(1, 1, 2, 1, -4, 5, 2))
    assertEquals(3, intCounts.getCountOf(1))
    assertEquals(2, intCounts.getCountOf(2))
    assertEquals(1, intCounts.getCountOf(-4))
    assertEquals(1, intCounts.getCountOf(5))

    val listCounts = CountsMap.from(listOf(listOf(1, 2, 3), listOf(1, 2, 3)))
    assertEquals(2, listCounts.getCountOf(listOf(1, 2, 3)))
    assertEquals(0, listCounts.getCountOf(listOf(1, 2)))
}

fun runIsEmptyTests() {
    val trueValues = listOf(emptyList<Int>(), emptyList<String>())
    val falseValues = listOf(
        listOf(0),
        listOf(1000, -1000, 4, 2, 4),
        listOf(3, 3, 3),
        listOf("123", "abc"),
        listOf("hello world", "hello world"),
        listOf(emptyList<String>())
    )
    checkTrueFalse(trueValues, falseValues, { "${CountsMap.from(it)}.isEmpty()" }) { CountsMap.from(it).isEmpty() }
}
