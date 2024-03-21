package xyz.lbres.kotlinutils.set.multiset.utils.countsmap

import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runGetCountOfTests() {
    var intMap: CountsMap<Int> = CountsMap.from(emptyList())
    assertEquals(0, intMap.getCountOf(0))
    assertEquals(0, intMap.getCountOf(100))

    intMap = CountsMap.from(listOf(2))
    assertEquals(1, intMap.getCountOf(2))
    assertEquals(0, intMap.getCountOf(1))

    intMap = CountsMap.from(listOf(1, 1, 2, 1, -4, 5, 2))
    assertEquals(3, intMap.getCountOf(1))
    assertEquals(2, intMap.getCountOf(2))
    assertEquals(1, intMap.getCountOf(-4))
    assertEquals(1, intMap.getCountOf(5))

    val listMap = CountsMap.from(listOf(listOf(1, 2, 3), listOf(1, 2, 3)))
    assertEquals(2, listMap.getCountOf(listOf(1, 2, 3)))
    assertEquals(0, listMap.getCountOf(listOf(1, 2)))
}

fun runIsEmptyTests() {
    // empty
    var intMap: CountsMap<Int> = CountsMap.from(emptyList())
    assertTrue(intMap.isEmpty())

    var stringMap: CountsMap<String> = CountsMap.from(emptyList())
    assertTrue(stringMap.isEmpty())

    // not empty
    intMap = CountsMap.from(listOf(0))
    assertFalse(intMap.isEmpty())

    intMap = CountsMap.from(listOf(1000, -1000, 4, 2, 4))
    assertFalse(intMap.isEmpty())

    intMap = CountsMap.from(listOf(3, 3, 3))
    assertFalse(intMap.isEmpty())

    stringMap = CountsMap.from(listOf("123", "abc"))
    assertFalse(stringMap.isEmpty())

    stringMap = CountsMap.from(listOf("hello world", "hello world"))
    assertFalse(stringMap.isEmpty())
}
