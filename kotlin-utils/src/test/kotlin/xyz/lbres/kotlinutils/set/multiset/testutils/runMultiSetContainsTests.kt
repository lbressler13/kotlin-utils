package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runMultiSetContainsTests(
    createIntSet: (Collection<Int>) -> MultiSet<Int>,
    createIntListSet: (Collection<IntList>) -> MultiSet<IntList>,
    createExceptionSet: (Collection<Exception>) -> MultiSet<Exception>,
) {
    var set: MultiSet<Int> = createIntSet(emptyList())
    assertFalse(set.contains(0))
    assertFalse(set.contains(1000))
    assertFalse(set.contains(-1000))

    set = createIntSet(listOf(1, 2))
    assertFalse(set.contains(0))
    assertTrue(set.contains(1))
    assertTrue(set.contains(2))

    set = createIntSet(listOf(1, 1, 1))
    assertTrue(set.contains(1))
    assertFalse(set.contains(2))

    val error = ArithmeticException()
    val errSet = createExceptionSet(listOf(ArithmeticException(), error, NumberFormatException()))
    assertTrue(errSet.contains(error))

    val listSet = createIntListSet(listOf(emptyList(), listOf(5, 6), listOf(9, 8, 3)))
    assertTrue(listSet.contains(emptyList()))
    assertTrue(listSet.contains(listOf(9, 8, 3)))
    assertFalse(listSet.contains(listOf(6, 6)))
}

fun runMultiSetContainsAllTests(createIntSet: (Collection<Int>) -> MultiSet<Int>) {
    // equal
    var set1: MultiSet<Int> = createIntSet(emptyList())
    assertTrue(set1.containsAll(set1))

    set1 = createIntSet(listOf(-445))
    assertTrue(set1.containsAll(set1))

    set1 = createIntSet(listOf(1, 1))
    assertTrue(set1.containsAll(set1))

    set1 = createIntSet(listOf(2, 3, 2, 4, 3, 4, 4))
    assertTrue(set1.containsAll(set1))

    set1 = createIntSet(listOf(1, 2, 3))
    var set2 = createIntSet(listOf(3, 1, 2))
    assertTrue(set1.containsAll(set2))
    assertTrue(set2.containsAll(set1))

    // subset
    set1 = createIntSet(listOf(1))
    set2 = createIntSet(emptyList())
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = createIntSet(listOf(1, 2, 3, 4))
    set2 = createIntSet(listOf(1, 3))
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = createIntSet(listOf(1, 1, 1))
    set2 = createIntSet(listOf(1, 1))
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = createIntSet(listOf(1, 3, -1, 5))
    set2 = createIntSet(listOf(1, 3, 5))
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    // overlap
    set1 = createIntSet(listOf(1, 2, 3))
    set2 = createIntSet(listOf(1, 3, 4))
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = createIntSet(listOf(100, 100, 300, 400))
    set2 = createIntSet(listOf(100, 300, 400, 400))
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = createIntSet(listOf(-10, 5, -10, -10))
    set2 = createIntSet(listOf(-10, -5, -10, -10))
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    // no overlap
    set1 = createIntSet(listOf(1))
    set2 = createIntSet(listOf(2))
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = createIntSet(listOf(1, 1, 1, 1))
    set2 = createIntSet(listOf(2, 2, 2, 2))
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1 = createIntSet(listOf(4, -4, 5, 7))
    set2 = createIntSet(listOf(22, 23, 22))
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))
}

fun runMultiSetMutableContainsTests(
    createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>,
    createMutableIntListSet: (Collection<IntList>) -> MutableMultiSet<IntList>,
    createMutableExceptionSet: (Collection<Exception>) -> MutableMultiSet<Exception>,
) {
    runMultiSetContainsTests(createMutableIntSet, createMutableIntListSet, createMutableExceptionSet)

    val set = createMutableIntSet(emptyList())
    set.add(1)
    assertTrue(set.contains(1))
    assertFalse(set.contains(2))
    set.remove(1)
    set.add(2)
    assertTrue(set.contains(2))
}

fun runMultiSetMutableContainsAllTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>) {
    runMultiSetContainsAllTests(createMutableIntSet)

    val set1 = createMutableIntSet(listOf(1, 2, 3))
    val set2 = createMutableIntSet(listOf(2, 4))
    assertFalse(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set1.add(4)
    assertTrue(set1.containsAll(set2))
    assertFalse(set2.containsAll(set1))

    set2.add(1)
    set2.add(3)
    assertTrue(set1.containsAll(set2))
    assertTrue(set2.containsAll(set1))
}

fun runMultiSetMutableElementContainsTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))

    mutableList1.remove(3)
    assertTrue(listSet.contains(listOf(1, 2)))
    assertTrue(listSet.contains(listOf(1, 2, 3)))

    mutableList2.add(0)
    assertFalse(listSet.contains(listOf(1, 2, 3)))
}

fun runMultiSetMutableElementContainsAllTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))
    assertFalse(listSet.containsAll(listOf(listOf(1, 2))))

    mutableList1.remove(3)
    assertTrue(listSet.containsAll(listOf(listOf(1, 2))))
    assertTrue(listSet.containsAll(listOf(listOf(1, 2, 3))))

    mutableList2.add(0)
    assertFalse(listSet.containsAll(listOf(listOf(1, 2, 3))))
}
