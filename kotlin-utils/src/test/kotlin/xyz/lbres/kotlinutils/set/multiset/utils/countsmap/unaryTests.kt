package xyz.lbres.kotlinutils.set.multiset.utils.countsmap

import xyz.lbres.kotlinutils.list.StringList
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
    // empty
    var intCounts: CountsMap<Int> = CountsMap.from(emptyList())
    assertTrue(intCounts.isEmpty())

    var stringCounts: CountsMap<String> = CountsMap.from(emptyList())
    assertTrue(stringCounts.isEmpty())

    // not empty
    intCounts = CountsMap.from(listOf(0))
    assertFalse(intCounts.isEmpty())

    intCounts = CountsMap.from(listOf(1000, -1000, 4, 2, 4))
    assertFalse(intCounts.isEmpty())

    intCounts = CountsMap.from(listOf(3, 3, 3))
    assertFalse(intCounts.isEmpty())

    stringCounts = CountsMap.from(listOf("123", "abc"))
    assertFalse(stringCounts.isEmpty())

    stringCounts = CountsMap.from(listOf("hello world", "hello world"))
    assertFalse(stringCounts.isEmpty())

    val listCounts: CountsMap<StringList> = CountsMap.from(listOf(emptyList()))
    assertFalse(listCounts.isEmpty())
}
