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

fun runMinusTests(
    createIntSet: (Collection<Int>) -> MultiSet<Int>,
    createIntListSet: (Collection<IntList>) -> MultiSet<IntList>,
    createStringSet: (Collection<String>) -> MultiSet<String>,
    createExceptionSet: (Collection<Exception>) -> MultiSet<Exception>,
    createCompListSet: (Collection<List<Comparable<*>>>) -> MultiSet<List<Comparable<*>>>,
    createOtherIntSet: (Collection<Int>) -> MultiSet<Int>
) {
    // empty
    var intSet1 = createIntSet(emptyList())
    var intSet2 = createIntSet(emptyList())
    assertEquals(emptyMultiSet(), intSet1 - intSet2)
    assertEquals(emptyMultiSet(), intSet2 - intSet1)

    assertIsNot<ConstMultiSet<*>>(intSet1 - intSet2)

    intSet1 = createIntSet(listOf(1, 2, 3, 3))
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(emptyMultiSet(), intSet2 - intSet1)

    // equal
    intSet1 = createIntSet(listOf(1, 2, 3, 4, 5))
    assertEquals(emptyMultiSet(), intSet1 - intSet1)

    var listSet1 = createIntListSet(listOf(listOf(1, 2, 3), listOf(456, 789)))
    assertEquals(emptyMultiSet(), listSet1 - listSet1)

    var otherSet = createOtherIntSet(listOf(1, 2, 3, 4, 5))
    assertEquals(emptyMultiSet(), intSet1 - otherSet)

    // all shared
    intSet1 = createIntSet(listOf(1, 1, 2, 3, 4, 4, 4))
    intSet2 = createIntSet(listOf(1, 2, 2, 3, 4, 4))
    var expectedInt = multiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = multiSetOf(2)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    intSet2 = createIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = multiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = multiSetOf(1, 5, 5, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    // none shared
    intSet1 = createIntSet(listOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8))
    intSet2 = createIntSet(listOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8))
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(intSet2, intSet2 - intSet1)

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    otherSet = createOtherIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = multiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    val stringSet1 = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "goodbye"))
    val stringSet2 = createStringSet(listOf("greetings", "planet", "farewell", "planet", "greetings", "farewell"))
    assertEquals(stringSet1, stringSet1 - stringSet2)
    assertEquals(stringSet2, stringSet2 - stringSet1)

    // some shared
    intSet1 = createIntSet(listOf(1, 1, 2, 3, 4, 5, 5))
    intSet2 = createIntSet(listOf(1, 1, 5, 6, 6, 7))
    expectedInt = multiSetOf(2, 3, 4, 5)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = multiSetOf(6, 6, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = createIntSet(listOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7))
    otherSet = createIntSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = multiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    listSet1 = createIntListSet(listOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3)))
    val listSet2 = createIntListSet(listOf(emptyList(), listOf(1, 2, 3)))
    var expectedList = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = multiSetOf(emptyList())
    assertEquals(expectedList, listSet2 - listSet1)

    val errorSet1 = createExceptionSet(listOf(e1, e2, e1, e1, e2))
    val errorSet2 = createExceptionSet(listOf(e1, e3, e3, e1, e1))
    var expectedError: MultiSet<Exception> = multiSetOf(e2, e2)
    assertEquals(expectedError, errorSet1 - errorSet2)
    expectedError = multiSetOf(e3, e3)
    assertEquals(expectedError, errorSet2 - errorSet1)

    val compListSet1: MultiSet<List<Comparable<*>>> = createCompListSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val compListSet2: MultiSet<List<Comparable<*>>> = createCompListSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    var compListExpected: MultiSet<List<Comparable<*>>> = multiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListSet1 - compListSet2)
    compListExpected = multiSetOf(listOf(1, 2, 3), emptyList())
    assertEquals(compListExpected, compListSet2 - compListSet1)
}

fun runPlusTests(
    createIntSet: (Collection<Int>) -> MultiSet<Int>,
    createIntListSet: (Collection<IntList>) -> MultiSet<IntList>,
    createStringSet: (Collection<String>) -> MultiSet<String>,
    createExceptionSet: (Collection<Exception>) -> MultiSet<Exception>,
    createCompListSet: (Collection<List<Comparable<*>>>) -> MultiSet<List<Comparable<*>>>,
    createOtherIntSet: (Collection<Int>) -> MultiSet<Int>
) {
    // empty
    var intSet1: MultiSet<Int> = createIntSet(emptyList())
    var intSet2: MultiSet<Int> = createIntSet(emptyList())
    assertEquals(emptyMultiSet(), intSet1 + intSet2)
    assertEquals(emptyMultiSet(), intSet2 + intSet1)

    assertIsNot<ConstMultiSet<*>>(intSet1 - intSet2)

    intSet1 = createIntSet(listOf(1, 2, 3, 3))
    assertEquals(intSet1, intSet1 + intSet2)

    // non-empty
    intSet1 = createIntSet(listOf(1))
    intSet2 = createIntSet(listOf(1))
    var expected = multiSetOf(1, 1)
    assertEquals(expected, intSet1 + intSet2)

    intSet1 = createIntSet(listOf(1, 2, 2, 3, 3, 3))
    intSet2 = createIntSet(listOf(1, 2, 0))
    expected = multiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(expected, intSet1 + intSet2)

    val otherSet = createOtherIntSet(listOf(1, 2, 2, 3, 3, 3))
    assertEquals(expected, intSet2 + otherSet)

    val stringSet1 = createStringSet(listOf("", "hello", "world", "goodbye"))
    val stringSet2 = createStringSet(listOf("hi", "no", "bye"))
    val stringExpected = multiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(stringExpected, stringSet1 + stringSet2)
    assertEquals(stringExpected, stringSet2 + stringSet1)

    val listSet1 = createIntListSet(listOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3)))
    val listSet2 = createIntListSet(listOf(emptyList(), listOf(1, 2, 3)))
    val expectedList = multiSetOf(emptyList(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    val errorSet1: MultiSet<Exception> = createExceptionSet(listOf(e1, e2, e1, e2))
    val errorSet2: MultiSet<Exception> = createExceptionSet(listOf(e1, e3, e3, e2, e1, e1))
    val expectedError: MultiSet<Exception> = multiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 + errorSet2)
    assertEquals(expectedError, errorSet2 + errorSet1)

    val compListSet1: MultiSet<List<Comparable<*>>> = createCompListSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val compListSet2: MultiSet<List<Comparable<*>>> = createCompListSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    val compListExpected: MultiSet<List<Comparable<*>>> = multiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListSet1 + compListSet2)
    assertEquals(compListExpected, compListSet2 + compListSet1)
}

fun runMutableElementMinusTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
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

fun runMutableElementPlusTests(createIntListSet: (List<IntList>) -> MultiSet<List<Int>>) {
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
