package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.assertEmpty
import xyz.lbres.kotlinutils.list.IntList
import kotlin.test.assertEquals
import kotlin.test.assertIs

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runPlusCTests(createSet: (Collection<*>) -> ConstMultiSet<*>, createOtherSet: (Collection<*>) -> ConstMultiSet<*>) {
    val createIntSet = getCreateConstSet<Int>(createSet)
    val createStringSet = getCreateConstSet<String>(createSet)
    val createExceptionSet = getCreateConstSet<Exception>(createSet)
    val createCompListSet = getCreateConstSet<List<Comparable<*>>>(createSet)
    val createOtherIntSet = getCreateConstSet<Int>(createOtherSet)

    // empty
    var intSet1: ConstMultiSet<Int> = createIntSet(emptyList())
    var intSet2: ConstMultiSet<Int> = createIntSet(emptyList())
    assertEmpty(intSet1 plusC intSet2)
    assertEmpty(intSet2 plusC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 plusC intSet2)

    intSet1 = createIntSet(listOf(1, 2, 3, 3))
    assertEquals(intSet1, intSet1 plusC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 plusC intSet2)

    // non-empty
    intSet1 = createIntSet(listOf(1))
    intSet2 = createIntSet(listOf(1))
    var intExpected = constMultiSetOf(1, 1)
    assertEquals(intExpected, intSet1 plusC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 plusC intSet2)

    intSet1 = createIntSet(listOf(1, 2, 2, 3, 3, 3))
    intSet2 = createIntSet(listOf(1, 2, 0))
    intExpected = constMultiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(intExpected, intSet1 plusC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 plusC intSet2)

    val otherSet = createOtherIntSet(listOf(1, 2, 2, 3, 3, 3))
    assertEquals(intExpected, intSet2 plusC otherSet)
    assertIs<ConstMultiSet<Int>>(intSet2 plusC otherSet)

    val stringSet1 = createStringSet(listOf("", "hello", "world", "goodbye"))
    val stringSet2 = createStringSet(listOf("hi", "no", "bye"))
    val stringExpected = constMultiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(stringExpected, stringSet1 plusC stringSet2)
    assertEquals(stringExpected, stringSet2 plusC stringSet1)
    assertIs<ConstMultiSet<String>>(stringSet1 plusC stringSet2)

    val listSet1 = createCompListSet(listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")))
    val listSet2 = createCompListSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    val expectedList: ConstMultiSet<List<Comparable<*>>> = constMultiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedList, listSet1 plusC listSet2)
    assertEquals(expectedList, listSet2 plusC listSet1)
    assertIs<ConstMultiSet<List<Comparable<*>>>>(listSet1 plusC listSet2)

    val errorSet1 = createExceptionSet(listOf(e1, e2, e1, e2))
    val errorSet2 = createExceptionSet(listOf(e1, e3, e3, e2, e1, e1))
    val expectedError: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 plusC errorSet2)
    assertEquals(expectedError, errorSet2 plusC errorSet1)
    assertIs<ConstMultiSet<Exception>>(errorSet1.plusC(errorSet2))
}

fun runIntersectCTests(createSet: (Collection<*>) -> ConstMultiSet<*>, createOtherSet: (Collection<*>) -> ConstMultiSet<*>) {
    val createIntSet = getCreateConstSet<Int>(createSet)
    val createIntListSet = getCreateConstSet<IntList>(createSet)
    val createExceptionSet = getCreateConstSet<Exception>(createSet)
    val createOtherIntSet = getCreateConstSet<Int>(createOtherSet)

    // empty
    var intSet1 = createIntSet(emptyList())
    var intSet2 = createIntSet(emptyList())
    assertEmpty(intSet1 intersectC intSet2)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    intSet2 = createIntSet(listOf(1, 2, 3))
    assertEmpty(intSet1 intersectC intSet2)
    assertEmpty(intSet2 intersectC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    // none shared
    intSet1 = createIntSet(listOf(1, 2, 3))
    intSet2 = createIntSet(listOf(4, 5, 6, 7, 8))
    assertEmpty(intSet1 intersectC intSet2)
    assertEmpty(intSet2 intersectC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    var otherSet = createOtherIntSet(listOf(4, 5, 6, 7, 8))
    assertEmpty(intSet1 intersectC otherSet)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    var listSet1 = createIntListSet(listOf(listOf(1, 2, 3), listOf(4, 5)))
    var listSet2 = createIntListSet(listOf(listOf(1, 2), listOf(3, 4, 5)))
    assertEmpty(listSet1 intersectC listSet2)
    assertEmpty(listSet2 intersectC listSet1)
    assertIs<ConstMultiSet<IntList>>(intSet1 minusC intSet2)

    // all overlapping elements
    intSet1 = createIntSet(listOf(1, 2, 3))
    intSet2 = createIntSet(listOf(1, 2, 3))
    var expectedInt = constMultiSetOf(1, 2, 3)
    assertEquals(expectedInt, intSet1 intersectC intSet2)
    assertEquals(expectedInt, intSet2 intersectC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    intSet1 = createIntSet(listOf(1, 1, 2, 2, 3, 3))
    intSet2 = createIntSet(listOf(1, 2, 2, 2, 3))
    expectedInt = constMultiSetOf(1, 2, 2, 3)
    assertEquals(expectedInt, intSet1 intersectC intSet2)
    assertEquals(expectedInt, intSet2 intersectC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    // some overlapping elements
    intSet1 = createIntSet(listOf(1, 2, 2, 4, 5, 6, 7, -1, 10))
    intSet2 = createIntSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = constMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersectC intSet2)
    assertEquals(expectedInt, intSet2 intersectC intSet1)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    otherSet = createOtherIntSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = constMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersectC otherSet)
    assertIs<ConstMultiSet<Int>>(intSet1 minusC intSet2)

    listSet1 = createIntListSet(listOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3)))
    listSet2 = createIntListSet(listOf(emptyList(), listOf(1, 2, 3)))
    val expectedList = constMultiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersectC listSet2)
    assertEquals(expectedList, listSet2 intersectC listSet1)
    assertIs<ConstMultiSet<IntList>>(intSet1 minusC intSet2)

    val errorSet1: ConstMultiSet<Exception> = createExceptionSet(listOf(e1, e2, e1, e2))
    val errorSet2: ConstMultiSet<Exception> = createExceptionSet(listOf(e1, e3, e3, e2, e1, e1))
    val expectedError: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e2)
    assertEquals(expectedError, errorSet1 intersectC errorSet2)
    assertEquals(expectedError, errorSet2 intersectC errorSet1)
    assertIs<ConstMultiSet<Exception>>(intSet1 minusC intSet2)
}
