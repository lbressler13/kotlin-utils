package xyz.lbres.kotlinutils.set.multiset.utils.countsmap

import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runContainsTests() {
    var intCounts: CountsMap<Int> = CountsMap.from(emptyList())
    assertFalse(intCounts.contains(0))
    assertFalse(intCounts.contains(1000))
    assertFalse(intCounts.contains(-1000))

    intCounts = CountsMap.from(listOf(1, 2))
    assertFalse(intCounts.contains(0))
    assertTrue(intCounts.contains(1))
    assertTrue(intCounts.contains(2))

    intCounts = CountsMap.from(listOf(1, 1, 1))
    assertTrue(intCounts.contains(1))
    assertFalse(intCounts.contains(2))

    val error = ArithmeticException()
    val errorCounts = CountsMap.from(listOf(ArithmeticException(), error, NumberFormatException()))
    assertTrue(errorCounts.contains(error))

    val listCounts = CountsMap.from(listOf(emptyList(), listOf(5, 6), listOf(9, 8, 3)))
    assertTrue(listCounts.contains(emptyList()))
    assertTrue(listCounts.contains(listOf(9, 8, 3)))
    assertFalse(listCounts.contains(listOf(6, 6)))
}

fun runContainsAllTests() {
    // equal
    var counts: CountsMap<Int> = CountsMap.from(emptyList())
    var collection: Collection<Int> = emptyList()
    assertTrue(counts.containsAll(collection))

    counts = CountsMap.from(listOf(-445))
    collection = listOf(-445)
    assertTrue(counts.containsAll(collection))

    counts = CountsMap.from(listOf(1, 1))
    collection = listOf(1, 1)
    assertTrue(counts.containsAll(collection))

    counts = CountsMap.from(listOf(2, 3, 2, 4, 3, 4, 4))
    collection = listOf(2, 3, 2, 4, 3, 4, 4)
    assertTrue(counts.containsAll(collection))

    counts = CountsMap.from(listOf(1, 2, 3))
    collection = listOf(3, 1, 2)
    assertTrue(counts.containsAll(collection))

    // submap
    counts = CountsMap.from(listOf(1))
    collection = emptyList()
    assertTrue(counts.containsAll(collection))

    counts = CountsMap.from(emptyList())
    collection = listOf(1)
    assertFalse(counts.containsAll(collection))

    counts = CountsMap.from(listOf(1, 2, 3, 4))
    collection = listOf(1, 3)
    assertTrue(counts.containsAll(collection))

    counts = CountsMap.from(listOf(1, 1, 1))
    collection = listOf(1, 1)
    assertTrue(counts.containsAll(collection))

    counts = CountsMap.from(listOf(1, 3, 5))
    collection = listOf(1, 3, -1, 5)
    assertFalse(counts.containsAll(collection))

    // overlapping keys
    counts = CountsMap.from(listOf(1, 2, 3))
    collection = listOf(1, 3, 4)
    assertFalse(counts.containsAll(collection))

    counts = CountsMap.from(listOf(100, 100, 300, 400))
    collection = listOf(100, 300, 400, 400)
    assertFalse(counts.containsAll(collection))

    counts = CountsMap.from(listOf(-10, 5, -10, -10))
    collection = listOf(-10, -5, -10, -10)
    assertFalse(counts.containsAll(collection))

    // no overlapping keys
    counts = CountsMap.from(listOf(1))
    collection = listOf(2)
    assertFalse(counts.containsAll(collection))

    counts = CountsMap.from(listOf(1, 1, 1, 1))
    collection = listOf(2, 2, 2, 2)
    assertFalse(counts.containsAll(collection))

    counts = CountsMap.from(listOf(4, -4, 5, 7))
    collection = listOf(22, 23, 22)
    assertFalse(counts.containsAll(collection))
}
