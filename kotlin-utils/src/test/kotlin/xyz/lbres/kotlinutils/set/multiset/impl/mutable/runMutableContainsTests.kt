package xyz.lbres.kotlinutils.set.multiset.impl.mutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runMutableContainsTests() {
    var set: MutableMultiSet<Int> = mutableMultiSetOf()
    assertFalse(set.contains(0))
    assertFalse(set.contains(1000))
    assertFalse(set.contains(-1000))

    set = mutableMultiSetOf(1, 2)
    assertFalse(set.contains(0))
    assertTrue(set.contains(1))
    assertTrue(set.contains(2))

    set = mutableMultiSetOf(1, 1, 1)
    assertTrue(set.contains(1))
    assertFalse(set.contains(2))

    val error = ArithmeticException()
    val errSet = mutableMultiSetOf(ArithmeticException(), error, NumberFormatException())
    assertTrue(errSet.contains(error))

    var listSet = mutableMultiSetOf(emptyList(), listOf(5, 6), listOf(9, 8, 3))
    assertTrue(listSet.contains(emptyList()))
    assertTrue(listSet.contains(listOf(9, 8, 3)))
    assertFalse(listSet.contains(listOf(6, 6)))

    // adding elements
    set = mutableMultiSetOf()
    set.add(1)
    assertTrue(set.contains(1))
    assertFalse(set.contains(2))
    set.remove(1)
    set.add(2)
    assertTrue(set.contains(2))

    // changing values
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    listSet = mutableMultiSetOf(mutableList1, mutableList2)
    assertFalse(listSet.contains(listOf(1, 2)))

    mutableList1.remove(3)
    assertTrue(listSet.contains(listOf(1, 2)))
    assertTrue(listSet.contains(listOf(1, 2, 3)))

    mutableList2.add(0)
    assertFalse(listSet.contains(listOf(1, 2, 3)))
}

fun runMutableContainsAllTests() {
    // equal
    var set1: MutableMultiSet<Int> = mutableMultiSetOf()
    assertTrue(set1.containsAll(set1))

    set1 = mutableMultiSetOf(-445)
    assertTrue(set1.containsAll(set1))

    set1 = mutableMultiSetOf(1, 1)
    assertTrue(set1.containsAll(set1))

    set1 = mutableMultiSetOf(2, 3, 2, 4, 3, 4, 4)
    assertTrue(set1.containsAll(set1))

    set1 = mutableMultiSetOf(1, 2, 3)
    var set2 = mutableMultiSetOf(3, 1, 2)
    assertTrue(set1.containsAll(set2))
    assertTrue(set2.containsAll(set1))

    // subset
    set1 = mutableMultiSetOf(1)
    set2 = mutableMultiSetOf()
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = mutableMultiSetOf(1, 2, 3, 4)
    set2 = mutableMultiSetOf(1, 3)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = mutableMultiSetOf(1, 1, 1)
    set2 = mutableMultiSetOf(1, 1)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = mutableMultiSetOf(1, 3, -1, 5)
    set2 = mutableMultiSetOf(1, 3, 5)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    // overlap
    set1 = mutableMultiSetOf(1, 2, 3)
    set2 = mutableMultiSetOf(1, 3, 4)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = mutableMultiSetOf(100, 100, 300, 400)
    set2 = mutableMultiSetOf(100, 300, 400, 400)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = mutableMultiSetOf(-10, 5, -10, -10)
    set2 = mutableMultiSetOf(-10, -5, -10, -10)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    // no overlap
    set1 = mutableMultiSetOf(1)
    set2 = mutableMultiSetOf(2)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = mutableMultiSetOf(1, 1, 1, 1)
    set2 = mutableMultiSetOf(2, 2, 2, 2)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = mutableMultiSetOf(4, -4, 5, 7)
    set2 = mutableMultiSetOf(22, 23, 22)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    // adding elements
    set1 = mutableMultiSetOf(1, 2, 3)
    set2 = mutableMultiSetOf(2, 4)
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1.add(4)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set2.add(1)
    set2.add(3)
    assertTrue(set1.containsAll(set2))
    assertTrue(set2.containsAll(set1))

    // changing values
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MutableMultiSet<IntList> = mutableMultiSetOf(mutableList1, mutableList2)
    assertFalse(listSet.containsAll(listOf(listOf(1, 2))))

    mutableList1.remove(3)
    assertTrue(listSet.containsAll(listOf(listOf(1, 2))))
    assertTrue(listSet.containsAll(listOf(listOf(1, 2, 3))))

    mutableList2.add(0)
    assertFalse(listSet.containsAll(listOf(listOf(1, 2, 3))))
}