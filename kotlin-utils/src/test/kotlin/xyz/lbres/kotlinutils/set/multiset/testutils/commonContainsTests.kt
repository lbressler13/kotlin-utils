package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runContainsTests(createSet: (Collection<*>) -> MultiSet<*>) {
    val createIntSet = getCreateSet<Int>(createSet)
    val createIntListSet = getCreateSet<IntList>(createSet)
    val createExceptionSet = getCreateSet<Exception>(createSet)

    var intSet: MultiSet<Int> = createIntSet(emptyList())
    assertFalse(intSet.contains(0))
    assertFalse(intSet.contains(1000))
    assertFalse(intSet.contains(-1000))

    intSet = createIntSet(listOf(1, 2))
    assertFalse(intSet.contains(0))
    assertTrue(intSet.contains(1))
    assertTrue(intSet.contains(2))

    intSet = createIntSet(listOf(1, 1, 1))
    assertTrue(intSet.contains(1))
    assertFalse(intSet.contains(2))

    val error = ArithmeticException()
    val errorSet = createExceptionSet(listOf(ArithmeticException(), error, NumberFormatException()))
    assertTrue(errorSet.contains(error))

    val listSet = createIntListSet(listOf(emptyList(), listOf(5, 6), listOf(9, 8, 3)))
    assertTrue(listSet.contains(emptyList()))
    assertTrue(listSet.contains(listOf(9, 8, 3)))
    assertFalse(listSet.contains(listOf(6, 6)))
}

fun runContainsAllTests(createIntSet: (Collection<Int>) -> MultiSet<Int>) {
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

    // overlapping keys
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

    // no overlapping keys
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

fun runMutableContainsTests(createMutableSet: (Collection<*>) -> MutableMultiSet<*>) {
    runContainsTests(createMutableSet)

    val createMutableIntSet = getCreateMutableSet<Int>(createMutableSet)
    val set = createMutableIntSet(emptyList())
    set.add(1)
    assertTrue(set.contains(1))
    assertFalse(set.contains(2))
    set.remove(1)
    assertFalse(set.contains(1))
    set.add(2)
    assertTrue(set.contains(2))
}

fun runMutableContainsAllTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>) {
    runContainsAllTests(createMutableIntSet)

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

fun runMutableElementContainsTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))

    mutableList1.remove(3)
    assertTrue(listSet.contains(listOf(1, 2)))
    assertTrue(listSet.contains(listOf(1, 2, 3)))

    mutableList2.add(0)
    assertFalse(listSet.contains(listOf(1, 2, 3)))
}

fun runMutableElementContainsAllTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))
    assertFalse(listSet.containsAll(listOf(listOf(1, 2), listOf(1, 2, 3))))

    mutableList1.remove(3)
    assertTrue(listSet.containsAll(listOf(listOf(1, 2), listOf(1, 2, 3))))

    mutableList2.add(0)
    assertFalse(listSet.containsAll(listOf(listOf(1, 2), listOf(1, 2, 3))))
}
