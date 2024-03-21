package xyz.lbres.kotlinutils.set.multiset.utils.countsmap

import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runContainsTests() {
    var intMap: CountsMap<Int> = CountsMap.from(emptyList())
    assertFalse(intMap.contains(0))
    assertFalse(intMap.contains(1000))
    assertFalse(intMap.contains(-1000))

    intMap = CountsMap.from(listOf(1, 2))
    assertFalse(intMap.contains(0))
    assertTrue(intMap.contains(1))
    assertTrue(intMap.contains(2))

    intMap = CountsMap.from(listOf(1, 1, 1))
    assertTrue(intMap.contains(1))
    assertFalse(intMap.contains(2))

    val error = ArithmeticException()
    val errorMap = CountsMap.from(listOf(ArithmeticException(), error, NumberFormatException()))
    assertTrue(errorMap.contains(error))

    val listMap = CountsMap.from(listOf(emptyList(), listOf(5, 6), listOf(9, 8, 3)))
    assertTrue(listMap.contains(emptyList()))
    assertTrue(listMap.contains(listOf(9, 8, 3)))
    assertFalse(listMap.contains(listOf(6, 6)))
}

fun runContainsAllTests() {
    // equal
    var map: CountsMap<Int> = CountsMap.from(emptyList())
    var collection: Collection<Int> = emptyList()
    assertTrue(map.containsAll(collection))

    map = CountsMap.from(listOf(-445))
    collection = listOf(-445)
    assertTrue(map.containsAll(collection))

    map = CountsMap.from(listOf(1, 1))
    collection = listOf(1, 1)
    assertTrue(map.containsAll(collection))

    map = CountsMap.from(listOf(2, 3, 2, 4, 3, 4, 4))
    collection = listOf(2, 3, 2, 4, 3, 4, 4)
    assertTrue(map.containsAll(collection))

    map = CountsMap.from(listOf(1, 2, 3))
    collection = listOf(3, 1, 2)
    assertTrue(map.containsAll(collection))

    // submap
    map = CountsMap.from(listOf(1))
    collection = emptyList()
    assertTrue(map.containsAll(collection))

    map = CountsMap.from(emptyList())
    collection = listOf(1)
    assertFalse(map.containsAll(collection))

    map = CountsMap.from(listOf(1, 2, 3, 4))
    collection = listOf(1, 3)
    assertTrue(map.containsAll(collection))

    map = CountsMap.from(listOf(1, 1, 1))
    collection = listOf(1, 1)
    assertTrue(map.containsAll(collection))

    map = CountsMap.from(listOf(1, 3, 5))
    collection = listOf(1, 3, -1, 5)
    assertFalse(map.containsAll(collection))

    // overlapping keys
    map = CountsMap.from(listOf(1, 2, 3))
    collection = listOf(1, 3, 4)
    assertFalse(map.containsAll(collection))

    map = CountsMap.from(listOf(100, 100, 300, 400))
    collection = listOf(100, 300, 400, 400)
    assertFalse(map.containsAll(collection))

    map = CountsMap.from(listOf(-10, 5, -10, -10))
    collection = listOf(-10, -5, -10, -10)
    assertFalse(map.containsAll(collection))

    // no overlapping keys
    map = CountsMap.from(listOf(1))
    collection = listOf(2)
    assertFalse(map.containsAll(collection))

    map = CountsMap.from(listOf(1, 1, 1, 1))
    collection = listOf(2, 2, 2, 2)
    assertFalse(map.containsAll(collection))

    map = CountsMap.from(listOf(4, -4, 5, 7))
    collection = listOf(22, 23, 22)
    assertFalse(map.containsAll(collection))
}
