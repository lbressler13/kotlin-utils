package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.ext.elementsEqual
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runIteratorTests(
    createIntSet: (Collection<Int>) -> MultiSet<Int>,
    createIntListSet: (Collection<IntList>) -> MultiSet<IntList>
) {
    var intSet: MultiSet<Int> = createIntSet(emptyList())
    var intIter = intSet.iterator()
    assertFalse(intIter.hasNext())

    intSet = createIntSet(listOf(1, 2, 3, 4))
    intIter = intSet.iterator()
    val intValues: MutableList<Int> = mutableListOf()
    var intExpected = listOf(1, 2, 3, 4)
    while (intIter.hasNext()) {
        intValues.add(intIter.next())
    }
    assertTrue(intValues.elementsEqual(intExpected))

    intSet = createIntSet(listOf(1, 2, 3, 4, 1, 4, 5))
    intIter = intSet.iterator()
    intValues.clear()
    intExpected = listOf(1, 1, 2, 3, 4, 4, 5)
    while (intIter.hasNext()) {
        intValues.add(intIter.next())
    }
    assertTrue(intValues.elementsEqual(intExpected))

    val listSet = createIntListSet(listOf(listOf(1, 2, 3), listOf(0, 5, 7)))
    val listIter = listSet.iterator()
    val listExpected = listOf(listOf(0, 5, 7), listOf(1, 2, 3))
    val listValues: MutableList<IntList> = mutableListOf()
    while (listIter.hasNext()) {
        listValues.add(listIter.next())
    }
    assertTrue(listValues.elementsEqual(listExpected))
}

fun runMutableIteratorTests(
    createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>,
    createMutableIntListSet: (Collection<IntList>) -> MutableMultiSet<IntList>
) {
    runIteratorTests(createMutableIntSet, createMutableIntListSet)

    val intSet = createMutableIntSet(listOf(1, 2, 3, 4, 1, 4, 5))
    var intIter = intSet.iterator()
    val intValues: MutableList<Int> = mutableListOf()
    var intExpected = listOf(1, 1, 2, 3, 4, 4, 5)
    while (intIter.hasNext()) {
        intValues.add(intIter.next())
    }
    assertTrue(intValues.elementsEqual(intExpected))

    intSet.add(5)
    intIter = intSet.iterator()
    intValues.clear()
    intExpected = listOf(1, 1, 2, 3, 4, 4, 5, 5)
    while (intIter.hasNext()) {
        intValues.add(intIter.next())
    }
    assertTrue(intValues.elementsEqual(intExpected))

    intSet.remove(2)
    intIter = intSet.iterator()
    intValues.clear()
    intExpected = listOf(1, 1, 3, 4, 4, 5, 5)
    while (intIter.hasNext()) {
        intValues.add(intIter.next())
    }
    assertTrue(intValues.elementsEqual(intExpected))
}

fun runMutableElementsIteratorTests(createIntListSet: (Collection<IntList>) -> MultiSet<IntList>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))

    var listIter = listSet.iterator()
    var listExpected = listOf(listOf(0, 5, 7), listOf(1, 2, 3))
    var listValues: MutableList<IntList> = mutableListOf()
    while (listIter.hasNext()) {
        listValues.add(listIter.next())
    }
    assertTrue(listValues.elementsEqual(listExpected))

    mutableList2.clear()
    listIter = listSet.iterator()
    listExpected = listOf(emptyList(), listOf(1, 2, 3))
    listValues = mutableListOf()
    while (listIter.hasNext()) {
        listValues.add(listIter.next())
    }
    assertTrue(listValues.elementsEqual(listExpected))
}
