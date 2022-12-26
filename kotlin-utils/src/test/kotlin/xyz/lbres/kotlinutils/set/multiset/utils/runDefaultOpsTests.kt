package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals

internal fun runDefaultPlusTests() {
    // empty
    var intSet1 = emptyMultiSet<Int>()
    var intSet2 = emptyMultiSet<Int>()
    assertEquals(intSet1 + intSet2, defaultPlus(intSet1, intSet2))
    assertEquals(intSet2 + intSet1, defaultPlus(intSet2, intSet1))

    // non-empty
    intSet1 = multiSetOf(1)
    intSet2 = multiSetOf(1)
    assertEquals(intSet1 + intSet2, defaultPlus(intSet1, intSet2))
    assertEquals(intSet2 + intSet1, defaultPlus(intSet2, intSet1))

    intSet1 = multiSetOf(1, 2, 2, 3, 3, 3)
    intSet2 = multiSetOf(1, 2, 0)
    assertEquals(intSet1 + intSet2, defaultPlus(intSet1, intSet2))
    assertEquals(intSet2 + intSet1, defaultPlus(intSet2, intSet1))

    val stringSet1 = multiSetOf("", "hello", "world", "goodbye")
    val stringSet2 = multiSetOf("hi", "no", "bye")
    assertEquals(stringSet1 + stringSet2, defaultPlus(stringSet1, stringSet2))
    assertEquals(stringSet2 + stringSet1, defaultPlus(stringSet2, stringSet1))

    val listSet1 = multiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listSet2 = multiSetOf(listOf(), listOf(1, 2, 3))
    assertEquals(listSet1 + listSet2, defaultPlus(listSet1, listSet2))
    assertEquals(listSet2 + listSet1, defaultPlus(listSet2, listSet1))

    val compListSet1: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListSet2: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), listOf())
    assertEquals(compListSet1 + compListSet2, defaultPlus(compListSet1, compListSet2))
    assertEquals(compListSet2 + compListSet1, defaultPlus(compListSet2, compListSet1))

    val nullSet1 = multiSetOf(1, 2, 4, null)
    val nullSet2 = multiSetOf(1, 3, null, null)
    assertEquals(nullSet1 + nullSet2, defaultPlus(nullSet1, nullSet2))
    assertEquals(nullSet2 + nullSet1, defaultPlus(nullSet2, nullSet1))
}

internal fun runDefaultMinusTests() {
    // empty
    var intSet1 = emptyMultiSet<Int>()
    var intSet2 = emptyMultiSet<Int>()
    assertEquals(intSet1 - intSet2, defaultMinus(intSet1, intSet2))
    assertEquals(intSet2 - intSet1, defaultMinus(intSet2, intSet1))

    intSet1 = multiSetOf(1, 1, 2, 3, 4, 4, 4)
    intSet2 = multiSetOf(1, 2, 2, 3, 4, 4)
    assertEquals(intSet1 - intSet2, defaultMinus(intSet1, intSet2))
    assertEquals(intSet2 - intSet1, defaultMinus(intSet2, intSet1))

    intSet1 = multiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    intSet2 = multiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    assertEquals(intSet1 - intSet2, defaultMinus(intSet1, intSet2))
    assertEquals(intSet2 - intSet1, defaultMinus(intSet2, intSet1))

    intSet1 = multiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
    intSet2 = multiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
    assertEquals(intSet1 - intSet2, defaultMinus(intSet1, intSet2))
    assertEquals(intSet2 - intSet1, defaultMinus(intSet2, intSet1))

    val stringSet1 = multiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
    val stringSet2 = multiSetOf("greetings", "planet", "farewell", "planet", "greetings", "farewell")
    assertEquals(stringSet1 - stringSet2, defaultMinus(stringSet1, stringSet2))
    assertEquals(stringSet2 - stringSet1, defaultMinus(stringSet2, stringSet1))

    intSet1 = multiSetOf(1, 1, 2, 3, 4, 5, 5)
    intSet2 = multiSetOf(1, 1, 5, 6, 6, 7)
    assertEquals(intSet1 - intSet2, defaultMinus(intSet1, intSet2))
    assertEquals(intSet2 - intSet1, defaultMinus(intSet2, intSet1))

    val listSet1 = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listSet2: MultiSet<List<Int>> = multiSetOf(listOf(), listOf(1, 2, 3))
    assertEquals(listSet1 - listSet2, defaultMinus(listSet1, listSet2))
    assertEquals(listSet2 - listSet1, defaultMinus(listSet2, listSet1))

    val compListSet1: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListSet2: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), listOf())
    assertEquals(compListSet1 - compListSet2, defaultMinus(compListSet1, compListSet2))
    assertEquals(compListSet2 - compListSet1, defaultMinus(compListSet2, compListSet1))

    val nullSet1 = multiSetOf(1, 1, 2, null)
    val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
    assertEquals(nullSet1 - nullSet2, defaultMinus(nullSet1, nullSet2))
    assertEquals(nullSet2 - nullSet1, defaultMinus(nullSet2, nullSet1))
}

internal fun runDefaultIntersectTests() {
    // empty
    var intSet1 = emptyMultiSet<Int>()
    var intSet2 = emptyMultiSet<Int>()
    assertEquals(intSet1 intersect intSet2, defaultIntersect(intSet1, intSet2))
    assertEquals(intSet2 intersect intSet1, defaultIntersect(intSet2, intSet1))

    intSet1 = emptyMultiSet()
    intSet2 = multiSetOf(1, 2, 3)
    assertEquals(intSet1 intersect intSet2, defaultIntersect(intSet1, intSet2))
    assertEquals(intSet2 intersect intSet1, defaultIntersect(intSet2, intSet1))

    intSet1 = multiSetOf(1, 2, 3)
    intSet2 = multiSetOf(4, 5, 6, 7, 8)
    assertEquals(intSet1 intersect intSet2, defaultIntersect(intSet1, intSet2))
    assertEquals(intSet2 intersect intSet1, defaultIntersect(intSet2, intSet1))

    var listSet1 = multiSetOf(listOf(1, 2, 3), listOf(4, 5))
    var listSet2 = multiSetOf(listOf(1, 2), listOf(3, 4, 5))
    assertEquals(listSet1 intersect listSet2, defaultIntersect(listSet1, listSet2))
    assertEquals(listSet2 intersect listSet1, defaultIntersect(listSet2, listSet1))

    intSet1 = multiSetOf(1, 2, 3)
    intSet2 = multiSetOf(1, 2, 3)
    assertEquals(intSet1 intersect intSet2, defaultIntersect(intSet1, intSet2))
    assertEquals(intSet2 intersect intSet1, defaultIntersect(intSet2, intSet1))

    intSet1 = multiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
    intSet2 = multiSetOf(-1, 14, 3, 9, 9, 6)
    assertEquals(intSet1 intersect intSet2, defaultIntersect(intSet1, intSet2))
    assertEquals(intSet2 intersect intSet1, defaultIntersect(intSet2, intSet1))

    listSet1 = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    listSet2 = multiSetOf(listOf(), listOf(1, 2, 3))
    assertEquals(listSet1 intersect listSet2, defaultIntersect(listSet1, listSet2))
    assertEquals(listSet2 intersect listSet1, defaultIntersect(listSet2, listSet1))

    val nullSet1 = multiSetOf(1, 1, 2, null)
    val nullSet2 = multiSetOf(1, 1, 5, 6, null, null)
    assertEquals(nullSet1 intersect nullSet2, defaultIntersect(nullSet1, nullSet2))
    assertEquals(nullSet2 intersect nullSet1, defaultIntersect(nullSet2, nullSet1))
}
