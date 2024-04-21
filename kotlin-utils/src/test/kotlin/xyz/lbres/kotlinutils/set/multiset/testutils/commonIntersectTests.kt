package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.assertEmpty
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

@Suppress(Suppressions.UNCHECKED_CAST)
private val genericIntersect: MultiSetOp<*> = { set1: MultiSet<Any>, set2: MultiSet<Any> -> set1 intersect set2 } as MultiSetOp<*>

@Suppress(Suppressions.UNCHECKED_CAST)
private val genericConstIntersect: MultiSetOp<*> = { set1: MultiSet<Any>, set2: MultiSet<Any> ->
    set1 as ConstMultiSet<Any>
    set2 as ConstMultiSet<Any>
    set1 intersectC set2
} as MultiSetOp<*>

fun runIntersectTests(createSet: (Collection<*>) -> MultiSet<*>, createOtherSet: (Collection<*>) -> MultiSet<*>, const: Boolean) {
    val intersectFn = simpleIf(const, genericConstIntersect, genericIntersect)
    val createIntSet = getCreateSet<Int>(createSet)
    val createIntListSet = getCreateSet<IntList>(createSet)
    val createExceptionSet = getCreateSet<Exception>(createSet)
    val createOtherIntSet = getCreateSet<Int>(createOtherSet)

    // empty
    var intSet1 = createIntSet(emptyList())
    var intSet2 = createIntSet(emptyList())
    assertEmpty(intersectFn(intSet1, intSet2))

    assertEquals(const, (intersectFn(intSet1, intSet2)) is ConstMultiSet<*>)

    intSet2 = createIntSet(listOf(1, 2, 3))
    assertEmpty(intersectFn(intSet1, intSet2))
    assertEmpty(intersectFn(intSet2, intSet1))

    // none shared
    intSet1 = createIntSet(listOf(1, 2, 3))
    intSet2 = createIntSet(listOf(4, 5, 6, 7, 8))
    assertEmpty(intersectFn(intSet1, intSet2))
    assertEmpty(intersectFn(intSet2, intSet1))

    var otherSet = createOtherIntSet(listOf(4, 5, 6, 7, 8))
    assertEmpty(intersectFn(intSet1, otherSet))

    var listSet1 = createIntListSet(listOf(listOf(1, 2, 3), listOf(4, 5)))
    var listSet2 = createIntListSet(listOf(listOf(1, 2), listOf(3, 4, 5)))
    assertEmpty(intersectFn(listSet1, intSet2))
    assertEmpty(intersectFn(listSet2, intSet1))

    // all overlapping elements
    intSet1 = createIntSet(listOf(1, 2, 3))
    intSet2 = createIntSet(listOf(1, 2, 3))
    var expectedInt = multiSetOf(1, 2, 3)
    assertEquals(expectedInt, intersectFn(intSet1, intSet2))
    assertEquals(expectedInt, intersectFn(intSet2, intSet1))

    intSet1 = createIntSet(listOf(1, 1, 2, 2, 3, 3))
    intSet2 = createIntSet(listOf(1, 2, 2, 2, 3))
    expectedInt = multiSetOf(1, 2, 2, 3)
    assertEquals(expectedInt, intersectFn(intSet1, intSet2))
    assertEquals(expectedInt, intersectFn(intSet2, intSet1))

    // some overlapping elements
    intSet1 = createIntSet(listOf(1, 2, 2, 4, 5, 6, 7, -1, 10))
    intSet2 = createIntSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = multiSetOf(-1, 6)
    assertEquals(expectedInt, intersectFn(intSet1, intSet2))
    assertEquals(expectedInt, intersectFn(intSet2, intSet1))

    otherSet = createOtherIntSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = multiSetOf(-1, 6)
    assertEquals(expectedInt, intersectFn(intSet1, otherSet))

    listSet1 = createIntListSet(listOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3)))
    listSet2 = createIntListSet(listOf(emptyList(), listOf(1, 2, 3)))
    val expectedList = multiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, intersectFn(listSet1, listSet2))
    assertEquals(expectedList, intersectFn(listSet2, listSet1))

    val errorSet1: MultiSet<Exception> = createExceptionSet(listOf(e1, e2, e1, e2))
    val errorSet2: MultiSet<Exception> = createExceptionSet(listOf(e1, e3, e3, e2, e1, e1))
    val expectedError: MultiSet<Exception> = multiSetOf(e1, e1, e2)
    assertEquals(expectedError, intersectFn(errorSet1, errorSet2))
    assertEquals(expectedError, intersectFn(errorSet2, errorSet1))
}

fun runMutableElementIntersectTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
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
