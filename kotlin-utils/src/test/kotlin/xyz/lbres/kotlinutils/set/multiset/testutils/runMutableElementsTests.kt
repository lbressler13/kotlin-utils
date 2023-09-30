package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

fun runMultiSetMutableElementMinusTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet1: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))
    val listSet2: MultiSet<List<Int>> = createIntListSet(listOf(listOf(1, 2, 3), listOf(0, 5, 7)))

    var expectedList: MultiSet<IntList> = emptyMultiSet()
    assertEquals(expectedList, listSet1 - listSet2)
    assertEquals(expectedList, listSet2 - listSet1)

    mutableList2.add(3)
    expectedList = createIntListSet(listOf(listOf(0, 5, 7, 3)))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = createIntListSet(listOf(listOf(0, 5, 7)))
    assertEquals(expectedList, listSet2 - listSet1)
}

fun runMultiSetMutableElementPlusTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet1: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))
    val listSet2: MultiSet<List<Int>> = createIntListSet(listOf(listOf(1), mutableList1))

    var expectedList: MultiSet<IntList> = multiSetOf(listOf(1), listOf(1, 2, 3), listOf(1, 2, 3), listOf(0, 5, 7))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    mutableList1.add(4)
    expectedList = multiSetOf(listOf(1), listOf(1, 2, 3, 4), listOf(1, 2, 3, 4), listOf(0, 5, 7))
    assertEquals(expectedList, listSet2 + listSet1)
    assertEquals(expectedList, listSet1 + listSet2)
}

fun runMultiSetMutableElementIntersectTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet1: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))
    var listSet2: MultiSet<List<Int>> = createIntListSet(listOf(listOf(1), mutableList1))

    var expectedList: MultiSet<IntList> = multiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    listSet2 = createIntListSet(listOf(listOf(1), listOf(1, 2, 3)))
    expectedList = multiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    mutableList2.clear()
    mutableList2.add(1)
    expectedList = multiSetOf(listOf(1), listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    mutableList1.add(4)
    mutableList2.add(1)
    expectedList = emptyMultiSet()
    assertEquals(expectedList, listSet2 intersect listSet1)
    assertEquals(expectedList, listSet1 intersect listSet2)
}

fun runMultiSetMutableElementGetCountOfTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))

    var listValue = listOf(1, 2, 3)
    var expected = 2
    assertEquals(expected, listSet.getCountOf(listValue))

    listValue = listOf(1, 2)
    expected = 0
    assertEquals(expected, listSet.getCountOf(listValue))

    mutableList1.remove(3)

    listValue = listOf(1, 2, 3)
    expected = 1
    assertEquals(expected, listSet.getCountOf(listValue))

    listValue = listOf(1, 2)
    expected = 1
    assertEquals(expected, listSet.getCountOf(listValue))
}
