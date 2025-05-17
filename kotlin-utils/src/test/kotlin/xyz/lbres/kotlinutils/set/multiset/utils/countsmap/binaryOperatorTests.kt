package xyz.lbres.kotlinutils.set.multiset.utils.countsmap

import xyz.lbres.kotlinutils.CompList
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import kotlin.test.assertEquals

fun runPlusTests() {
    // empty
    var intCounts1: CountsMap<Int> = CountsMap.from(emptyList())
    var intCounts2: CountsMap<Int> = CountsMap.from(emptyList())
    var intExpected: CountsMap<Int> = CountsMap.from(emptyList())
    assertEquals(intExpected, intCounts1 + intCounts2)

    intCounts1 = CountsMap.from(emptyList())
    intCounts2 = CountsMap.from(listOf(1, 2, 1, 4))
    intExpected = CountsMap.from(listOf(1, 1, 2, 4))
    assertEquals(intExpected, intCounts1 + intCounts2)
    assertEquals(intExpected, intCounts2 + intCounts1)

    // non-empty
    intCounts1 = CountsMap.from(listOf(1))
    intCounts2 = CountsMap.from(listOf(1))
    intExpected = CountsMap.from(listOf(1, 1))
    assertEquals(intExpected, intCounts1 + intCounts2)
    assertEquals(intExpected, intCounts2 + intCounts1)

    val stringCounts1 = CountsMap.from(listOf("hello", "world", "hello world", "hello"))
    val stringCounts2 = CountsMap.from(listOf("hello", "", "", "planet", "farewell"))
    val stringExpected = CountsMap.from(listOf("", "", "farewell", "hello", "hello", "hello", "hello world", "planet", "world"))
    assertEquals(stringExpected, stringCounts1 + stringCounts2)
    assertEquals(stringExpected, stringCounts2 + stringCounts1)

    val listCounts1: CountsMap<CompList> = CountsMap.from(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val listCounts2: CountsMap<CompList> = CountsMap.from(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    val listExpected: CountsMap<CompList> = CountsMap.from(listOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    assertEquals(listExpected, listCounts1 + listCounts2)
    assertEquals(listExpected, listCounts2 + listCounts1)
}

fun runMinusTests() {
    // empty
    var intCounts1: CountsMap<Int> = CountsMap.from(emptyList())
    var intCounts2: CountsMap<Int> = CountsMap.from(emptyList())
    var intExpected: CountsMap<Int> = CountsMap.from(emptyList())
    assertEquals(intExpected, intCounts1 - intCounts2)

    intCounts1 = CountsMap.from(emptyList())
    intCounts2 = CountsMap.from(listOf(1, 2, 3))
    assertEquals(intCounts1, intCounts1 - intCounts2)
    assertEquals(intCounts2, intCounts2 - intCounts1)

    // equal
    intCounts1 = CountsMap.from(listOf(2, 2, 5, 1))
    intCounts2 = CountsMap.from(listOf(1, 2, 5, 2))
    intExpected = CountsMap.from(emptyList())
    assertEquals(intExpected, intCounts1 - intCounts2)

    // all overlapping elements
    intCounts1 = CountsMap.from(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    intCounts2 = CountsMap.from(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    intExpected = CountsMap.from(listOf(2, 2, 6))
    assertEquals(intExpected, intCounts1 - intCounts2)
    intExpected = CountsMap.from(listOf(1, 5, 5, 7))
    assertEquals(intExpected, intCounts2 - intCounts1)

    // no overlapping elements
    intCounts1 = CountsMap.from(listOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8))
    intCounts2 = CountsMap.from(listOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8))
    assertEquals(intCounts1, intCounts1 - intCounts2)
    assertEquals(intCounts2, intCounts2 - intCounts1)

    val stringCounts1 = CountsMap.from(listOf("hello", "world", "goodbye", "world", "hello", "goodbye"))
    val stringCounts2 = CountsMap.from(listOf("greetings", "planet", "farewell", "planet", "greetings", "farewell"))
    assertEquals(stringCounts1, stringCounts1 - stringCounts2)
    assertEquals(stringCounts2, stringCounts2 - stringCounts1)

    // some overlapping elements
    intCounts1 = CountsMap.from(listOf(1, 1, 2, 3, 4, 5, 5))
    intCounts2 = CountsMap.from(listOf(1, 1, 5, 6, 6, 7))
    intExpected = CountsMap.from(listOf(2, 3, 4, 5))
    assertEquals(intExpected, intCounts1 - intCounts2)
    intExpected = CountsMap.from(listOf(6, 6, 7))
    assertEquals(intExpected, intCounts2 - intCounts1)

    val listCounts1: CountsMap<CompList> = CountsMap.from(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val listCounts2: CountsMap<CompList> = CountsMap.from(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    var listExpected: CountsMap<CompList> = CountsMap.from(listOf(listOf("abc", "def"), listOf("abc", "def")))
    assertEquals(listExpected, listCounts1 - listCounts2)
    listExpected = CountsMap.from(listOf(listOf(1, 2, 3), emptyList()))
    assertEquals(listExpected, listCounts2 - listCounts1)
}

fun runIntersectTests() {
    // empty
    var intCounts1: CountsMap<Int> = CountsMap.from(emptyList())
    var intCounts2: CountsMap<Int> = CountsMap.from(emptyList())
    var intExpected: CountsMap<Int> = CountsMap.from(emptyList())
    assertEquals(intExpected, intCounts1 intersect intCounts2)

    intCounts1 = CountsMap.from(emptyList())
    intCounts2 = CountsMap.from(listOf(1, 2, 3))
    intExpected = CountsMap.from(emptyList())
    assertEquals(intExpected, intCounts1 intersect intCounts2)
    assertEquals(intExpected, intCounts2 intersect intCounts1)

    // equal
    intCounts1 = CountsMap.from(listOf(1, 4, 5, 1, 3))
    assertEquals(intCounts1, intCounts1 intersect intCounts1)

    // none shared
    intCounts1 = CountsMap.from(listOf(1, 2, 3))
    intCounts2 = CountsMap.from(listOf(4, 5, 6, 7, 8))
    intExpected = CountsMap.from(emptyList())
    assertEquals(intExpected, intCounts1 intersect intCounts2)
    assertEquals(intExpected, intCounts2 intersect intCounts1)

    // all overlapping elements
    intCounts1 = CountsMap.from(listOf(1, 1, 2, 2, 3, 3))
    intCounts2 = CountsMap.from(listOf(1, 2, 2, 2, 3))
    intExpected = CountsMap.from(listOf(1, 2, 2, 3))
    assertEquals(intExpected, intCounts1 intersect intCounts2)
    assertEquals(intExpected, intCounts2 intersect intCounts1)

    // some overlapping elements
    intCounts1 = CountsMap.from(listOf(1, 2, 2, 4, 5, 6, 7, -1, 10))
    intCounts2 = CountsMap.from(listOf(-1, 14, 3, 9, 9, 6))
    intExpected = CountsMap.from(listOf(-1, 6))
    assertEquals(intExpected, intCounts1 intersect intCounts2)
    assertEquals(intExpected, intCounts2 intersect intCounts1)

    val listCounts1: CountsMap<CompList> = CountsMap.from(listOf(listOf(1, 2, 3), emptyList(), listOf("abc", "def"), listOf("abc", "def")))
    val listCounts2: CountsMap<CompList> = CountsMap.from(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    val listExpected: CountsMap<CompList> = CountsMap.from(listOf(listOf(1, 2, 3), emptyList()))
    assertEquals(listExpected, listCounts1 intersect listCounts2)
    assertEquals(listExpected, listCounts2 intersect listCounts1)
}
