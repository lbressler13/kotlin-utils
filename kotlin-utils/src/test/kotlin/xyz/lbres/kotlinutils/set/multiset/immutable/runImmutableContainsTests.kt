package xyz.lbres.kotlinutils.set.multiset.immutable

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runImmutableContainsTests() {
    var set: MultiSet<Int> = multiSetOf()
    assertFalse(set.contains(0))
    assertFalse(set.contains(1000))
    assertFalse(set.contains(-1000))

    set = multiSetOf(1, 2)
    assertFalse(set.contains(0))
    assertTrue(set.contains(1))
    assertTrue(set.contains(2))

    set = multiSetOf(1, 1, 1)
    assertTrue(set.contains(1))
    assertFalse(set.contains(2))

    val error = ArithmeticException()
    val errSet = multiSetOf(ArithmeticException(), error, NumberFormatException())
    assertTrue(errSet.contains(error))

    val listSet = multiSetOf(listOf(), listOf(5, 6), listOf(9, 8, 3))
    assertTrue(listSet.contains(listOf()))
    assertTrue(listSet.contains(listOf(9, 8, 3)))
    assertFalse(listSet.contains(listOf(6, 6)))
}

fun runImmutableContainsAllTests() {
    // equal
    var set1: MultiSet<Int> = multiSetOf()
    assertTrue(set1.containsAll(set1))

    set1 = multiSetOf(-445)
    assertTrue(set1.containsAll(set1))

    set1 = multiSetOf(1, 1)
    assertTrue(set1.containsAll(set1))

    set1 = multiSetOf(2, 3, 2, 4, 3, 4, 4)
    assertTrue(set1.containsAll(set1))

    set1 = multiSetOf(1, 2, 3)
    var set2 = multiSetOf(3, 1, 2)
    assertTrue(set1.containsAll(set2))
    assertTrue(set2.containsAll(set1))

    // subset
    set1 = multiSetOf(1)
    set2 = multiSetOf()
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = multiSetOf(1, 2, 3, 4)
    set2 = multiSetOf(1, 3)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = multiSetOf(1, 1, 1)
    set2 = multiSetOf(1, 1)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = multiSetOf(1, 3, -1, 5)
    set2 = multiSetOf(1, 3, 5)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    // overlap
    set1 = multiSetOf(1, 2, 3)
    set2 = multiSetOf(1, 3, 4)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = multiSetOf(100, 100, 300, 400)
    set2 = multiSetOf(100, 300, 400, 400)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = multiSetOf(-10, 5, -10, -10)
    set2 = multiSetOf(-10, -5, -10, -10)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    // no overlap
    set1 = multiSetOf(1)
    set2 = multiSetOf(2)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = multiSetOf(1, 1, 1, 1)
    set2 = multiSetOf(2, 2, 2, 2)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = multiSetOf(4, -4, 5, 7)
    set2 = multiSetOf(22, 23, 22)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))
}
