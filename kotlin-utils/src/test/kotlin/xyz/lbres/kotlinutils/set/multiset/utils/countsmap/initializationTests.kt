package xyz.lbres.kotlinutils.set.multiset.utils.countsmap

import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import kotlin.test.assertEquals

fun runFromTests() {
    var intExpected: CountsMap<Int> = CountsMap(emptyMap())
    var intActual: CountsMap<Int> = CountsMap.from(emptyList())
    assertEquals(intExpected, intActual)

    intExpected = CountsMap(mapOf(1 to 1))
    intActual = CountsMap.from(listOf(1))
    assertEquals(intExpected, intActual)

    intExpected = CountsMap(mapOf(1 to 1, 2 to 1, 3 to 1, 4 to 1))
    intActual = CountsMap.from(listOf(1, 2, 3, 4))
    assertEquals(intExpected, intActual)

    intExpected = CountsMap(mapOf(1 to 3, 0 to 2, -1 to 7))
    intActual = CountsMap.from(multiSetOf(-1, -1, 1, 1, 0, -1, -1, 0, 1, -1, -1, -1))
    assertEquals(intExpected, intActual)

    val stringExpected = CountsMap(mapOf("hello" to 2, "world" to 1, "hello world" to 1))
    val stringActual = CountsMap.from(listOf("hello", "world", "hello world", "hello"))
    assertEquals(stringExpected, stringActual)

    val listExpected = CountsMap(mapOf(listOf(123) to 1, listOf(1, 4, 5, 6) to 1, listOf(99, 100, 97) to 1))
    val listActual = CountsMap.from(setOf(listOf(123), listOf(1, 4, 5, 6), listOf(99, 100, 97)))
    assertEquals(listExpected, listActual)
}

fun runDistinctTests() {
    var intCounts: CountsMap<Int> = CountsMap(emptyMap())
    var intExpected: Set<Int> = emptySet()
    assertEquals(intExpected, intCounts.distinct)

    intCounts = CountsMap.from(listOf(123, 123, 123, 123))
    intExpected = setOf(123)
    assertEquals(intExpected, intCounts.distinct)

    intCounts = CountsMap.from(listOf(1, 2, 3, 4, 5))
    intExpected = setOf(1, 2, 3, 4, 5)
    assertEquals(intExpected, intCounts.distinct)

    intCounts = CountsMap.from(listOf(0, 1, 1, 2, 3, 5))
    intExpected = setOf(0, 1, 2, 3, 5)
    assertEquals(intExpected, intCounts.distinct)

    val stringCounts = CountsMap.from(listOf("hello", "world", "goodbye", "hello", "hello world", "farewell", "world", "world"))
    val stringExpected = setOf("farewell", "goodbye", "hello", "hello world", "world")
    assertEquals(stringExpected, stringCounts.distinct)

    val countsCounts = CountsMap.from(
        listOf(
            CountsMap.from(listOf(1)),
            CountsMap.from(listOf(1)),
            CountsMap.from(listOf(44, 5)),
            CountsMap.from(listOf(1, 1, 1)),
            CountsMap.from(listOf(1)),
        )
    )
    val countsExpected = setOf(
        CountsMap.from(listOf(1)),
        CountsMap.from(listOf(44, 5)),
        CountsMap.from(listOf(1, 1, 1))
    )
    assertEquals(countsExpected, countsCounts.distinct)
}
