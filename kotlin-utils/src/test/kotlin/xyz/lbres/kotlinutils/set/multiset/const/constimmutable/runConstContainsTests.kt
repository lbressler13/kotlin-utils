package xyz.lbres.kotlinutils.set.multiset.const.constimmutable

import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runConstContainsTests() {
    var set: ConstMultiSet<Int> = emptyConstMultiSet()
    assertFalse(set.contains(0))
    assertFalse(set.contains(1000))
    assertFalse(set.contains(-1000))

    set = constMultiSetOf(1, 2)
    assertFalse(set.contains(0))
    assertTrue(set.contains(1))
    assertTrue(set.contains(2))

    set = constMultiSetOf(1, 1, 1)
    assertTrue(set.contains(1))
    assertFalse(set.contains(2))

    val error = ArithmeticException()
    val errSet = constMultiSetOf(ArithmeticException(), error, NumberFormatException())
    assertTrue(errSet.contains(error))

    val listSet = constMultiSetOf(emptyList(), listOf(5, 6), listOf(9, 8, 3))
    assertTrue(listSet.contains(emptyList()))
    assertTrue(listSet.contains(listOf(9, 8, 3)))
    assertFalse(listSet.contains(listOf(6, 6)))
}

fun runConstContainsAllTests() {
    // equal
    var set1: ConstMultiSet<Int> = emptyConstMultiSet()
    assertTrue(set1.containsAll(set1))

    set1 = constMultiSetOf(-445)
    assertTrue(set1.containsAll(set1))

    set1 = constMultiSetOf(1, 1)
    assertTrue(set1.containsAll(set1))

    set1 = constMultiSetOf(2, 3, 2, 4, 3, 4, 4)
    assertTrue(set1.containsAll(set1))

    set1 = constMultiSetOf(1, 2, 3)
    var set2 = constMultiSetOf(3, 1, 2)
    assertTrue(set1.containsAll(set2))
    assertTrue(set2.containsAll(set1))

    // subset
    set1 = constMultiSetOf(1)
    set2 = emptyConstMultiSet()
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = constMultiSetOf(1, 2, 3, 4)
    set2 = constMultiSetOf(1, 3)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = constMultiSetOf(1, 1, 1)
    set2 = constMultiSetOf(1, 1)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = constMultiSetOf(1, 3, -1, 5)
    set2 = constMultiSetOf(1, 3, 5)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    // overlap
    set1 = constMultiSetOf(1, 2, 3)
    set2 = constMultiSetOf(1, 3, 4)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = constMultiSetOf(100, 100, 300, 400)
    set2 = constMultiSetOf(100, 300, 400, 400)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = constMultiSetOf(-10, 5, -10, -10)
    set2 = constMultiSetOf(-10, -5, -10, -10)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    // no overlap
    set1 = constMultiSetOf(1)
    set2 = constMultiSetOf(2)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = constMultiSetOf(1, 1, 1, 1)
    set2 = constMultiSetOf(2, 2, 2, 2)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = constMultiSetOf(4, -4, 5, 7)
    set2 = constMultiSetOf(22, 23, 22)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))
}
