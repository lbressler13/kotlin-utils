package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.assertEmpty
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals
import kotlin.test.assertIsNot

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runPlusTests(createSet: (Collection<*>) -> MultiSet<*>, createOtherSet: (Collection<*>) -> MultiSet<*>) {
    val createIntSet = getCreateSet<Int>(createSet)
    val createStringSet = getCreateSet<String>(createSet)
    val createExceptionSet = getCreateSet<Exception>(createSet)
    val createCompListSet = getCreateSet<List<Comparable<*>>>(createSet)
    val createOtherIntSet = getCreateSet<Int>(createOtherSet)

    // empty
    var intSet1: MultiSet<Int> = createIntSet(emptyList())
    var intSet2: MultiSet<Int> = createIntSet(emptyList())
    assertEmpty(intSet1 + intSet2)
    assertEmpty(intSet2 + intSet1)

    assertIsNot<ConstMultiSet<*>>(intSet1 + intSet2)

    intSet1 = createIntSet(listOf(1, 2, 3, 3))
    assertEquals(intSet1, intSet1 + intSet2)

    // non-empty
    intSet1 = createIntSet(listOf(1))
    intSet2 = createIntSet(listOf(1))
    var intExpected = multiSetOf(1, 1)
    assertEquals(intExpected, intSet1 + intSet2)

    intSet1 = createIntSet(listOf(1, 2, 2, 3, 3, 3))
    intSet2 = createIntSet(listOf(1, 2, 0))
    intExpected = multiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(intExpected, intSet1 + intSet2)

    val otherSet = createOtherIntSet(listOf(1, 2, 2, 3, 3, 3))
    assertEquals(intExpected, intSet2 + otherSet)

    val stringSet1 = createStringSet(listOf("", "hello", "world", "goodbye"))
    val stringSet2 = createStringSet(listOf("hi", "no", "bye"))
    val stringExpected = multiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(stringExpected, stringSet1 + stringSet2)
    assertEquals(stringExpected, stringSet2 + stringSet1)

    val listSet1 = createCompListSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val listSet2 = createCompListSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    val expectedList: MultiSet<List<Comparable<*>>> = multiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    val errorSet1 = createExceptionSet(listOf(e1, e2, e1, e2))
    val errorSet2 = createExceptionSet(listOf(e1, e3, e3, e2, e1, e1))
    val expectedError: MultiSet<Exception> = multiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 + errorSet2)
    assertEquals(expectedError, errorSet2 + errorSet1)
}

fun runMutableElementPlusTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet1: MultiSet<IntList> = createIntListSet(listOf(mutableList1, mutableList2))
    val listSet2: MultiSet<IntList> = createIntListSet(listOf(listOf(1), mutableList1))

    var expectedList: MultiSet<IntList> = multiSetOf(listOf(1), listOf(1, 2, 3), listOf(1, 2, 3), listOf(0, 5, 7))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    mutableList1.add(4)
    expectedList = multiSetOf(listOf(1), listOf(1, 2, 3, 4), listOf(1, 2, 3, 4), listOf(0, 5, 7))
    assertEquals(expectedList, listSet2 + listSet1)
    assertEquals(expectedList, listSet1 + listSet2)
}
