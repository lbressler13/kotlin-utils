package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.test.assertEquals
import kotlin.test.assertFalse

fun runIteratorTests(
    createIntSet: (Collection<Int>) -> MultiSet<Int>,
    createIntListSet: (Collection<IntList>) -> MultiSet<IntList>,
) {
    var set: MultiSet<Int> = createIntSet(emptyList())
    var iter = set.iterator()
    assertFalse(iter.hasNext())

    set = createIntSet(listOf(1, 2, 3, 4))
    iter = set.iterator()
    val values: MutableList<Int> = mutableListOf()
    var expected = listOf(1, 2, 3, 4)
    while (iter.hasNext()) {
        values.add(iter.next())
    }
    assertEquals(expected.sorted(), values.sorted())

    set = createIntSet(listOf(1, 2, 3, 4, 1, 4, 5))
    iter = set.iterator()
    values.clear()
    expected = listOf(1, 1, 2, 3, 4, 4, 5)
    while (iter.hasNext()) {
        values.add(iter.next())
    }
    assertEquals(expected.sorted(), values.sorted())

    val listSet = createIntListSet(listOf(listOf(1, 2, 3), listOf(0, 5, 7)))
    val listIter = listSet.iterator()
    val listExpected = listOf(listOf(1, 2, 3), listOf(0, 5, 7))
    val listValues: MutableList<IntList> = mutableListOf()
    while (listIter.hasNext()) {
        listValues.add(listIter.next())
    }
    assertEquals(listExpected, listValues)
}

fun runMutableIteratorTests(
    createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>,
    createMutableIntListSet: (Collection<IntList>) -> MutableMultiSet<IntList>
) {
    runIteratorTests(createMutableIntSet, createMutableIntListSet)

    val set = createMutableIntSet(listOf(1, 2, 3, 4, 1, 4, 5))
    var iter = set.iterator()
    val values: MutableList<Int> = mutableListOf()
    var expected = listOf(1, 1, 2, 3, 4, 4, 5)
    while (iter.hasNext()) {
        values.add(iter.next())
    }
    assertEquals(expected.sorted(), values.sorted())

    set.add(5)
    iter = set.iterator()
    values.clear()
    expected = listOf(1, 1, 2, 3, 4, 4, 5, 5)
    while (iter.hasNext()) {
        values.add(iter.next())
    }
    assertEquals(expected.sorted(), values.sorted())

    set.remove(2)
    iter = set.iterator()
    values.clear()
    expected = listOf(1, 1, 3, 4, 4, 5, 5)
    while (iter.hasNext()) {
        values.add(iter.next())
    }
    assertEquals(expected.sorted(), values.sorted())
}

fun runMutableElementsIteratorTests(createIntListSet: (Collection<IntList>) -> MultiSet<IntList>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))

    var listIter = listSet.iterator()
    var listExpected = listOf(listOf(1, 2, 3), listOf(0, 5, 7))
    var listValues: MutableList<IntList> = mutableListOf()
    while (listIter.hasNext()) {
        listValues.add(listIter.next())
    }
    assertEquals(listExpected, listValues)

    mutableList2.clear()
    listIter = listSet.iterator()
    listExpected = listOf(listOf(1, 2, 3), emptyList())
    listValues = mutableListOf()
    while (listIter.hasNext()) {
        listValues.add(listIter.next())
    }
    assertEquals(listExpected, listValues)
}
