package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals
import kotlin.test.assertIsNot

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runMultiSetIntersectTests(
    createIntSet: (Collection<Int>) -> MultiSet<Int>,
    createIntListSet: (Collection<IntList>) -> MultiSet<IntList>,
    createExceptionSet: (Collection<Exception>) -> MultiSet<Exception>,
    createOtherIntSet: (Collection<Int>) -> MultiSet<Int>
) {
    // empty
    var intSet1 = createIntSet(emptyList())

    var intSet2 = createIntSet(emptyList())
    assertEquals(emptyMultiSet(), intSet1 intersect intSet2)

    assertIsNot<ConstMultiSet<*>>(intSet1 - intSet2)

    intSet2 = createIntSet(listOf(1, 2, 3))
    assertEquals(emptyMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyMultiSet(), intSet2 intersect intSet1)

    // none shared
    intSet1 = createIntSet(listOf(1, 2, 3))
    intSet2 = createIntSet(listOf(4, 5, 6, 7, 8))
    assertEquals(emptyMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyMultiSet(), intSet2 intersect intSet1)

    var otherSet = createOtherIntSet(listOf(4, 5, 6, 7, 8))
    assertEquals(emptyMultiSet(), intSet1 intersect otherSet)

    var listSet1 = createIntListSet(listOf(listOf(1, 2, 3), listOf(4, 5)))
    var listSet2 = createIntListSet(listOf(listOf(1, 2), listOf(3, 4, 5)))
    assertEquals(emptyMultiSet(), listSet1 intersect listSet2)
    assertEquals(emptyMultiSet(), listSet2 intersect listSet1)

    // all shared
    intSet1 = createIntSet(listOf(1, 2, 3))
    intSet2 = createIntSet(listOf(1, 2, 3))
    var expectedInt = multiSetOf(1, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    intSet1 = createIntSet(listOf(1, 1, 2, 2, 3, 3))
    intSet2 = createIntSet(listOf(1, 2, 2, 2, 3))
    expectedInt = multiSetOf(1, 2, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    // some shared
    intSet1 = createIntSet(listOf(1, 2, 2, 4, 5, 6, 7, -1, 10))
    intSet2 = createIntSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = multiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    otherSet = createOtherIntSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = multiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect otherSet)

    listSet1 = createIntListSet(listOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3)))
    listSet2 = createIntListSet(listOf(emptyList(), listOf(1, 2, 3)))
    val expectedList = multiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    val errorSet1: MultiSet<Exception> = createExceptionSet(listOf(e1, e2, e1, e2))
    val errorSet2: MultiSet<Exception> = createExceptionSet(listOf(e1, e3, e3, e2, e1, e1))
    val expectedError: MultiSet<Exception> = multiSetOf(e1, e1, e2)
    assertEquals(expectedError, errorSet1 intersect errorSet2)
    assertEquals(expectedError, errorSet2 intersect errorSet1)
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
