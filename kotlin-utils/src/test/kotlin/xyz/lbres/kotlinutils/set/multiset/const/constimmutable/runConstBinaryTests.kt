package xyz.lbres.kotlinutils.set.multiset.const.constimmutable

import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.testutils.TestMultiSet
import kotlin.test.assertEquals
import kotlin.test.assertIsNot

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runConstMinusTests() {
    // empty
    var intSet1: ConstMultiSet<Int> = emptyConstMultiSet()
    var intSet2: ConstMultiSet<Int> = emptyConstMultiSet()
    assertEquals(emptyConstMultiSet(), intSet1 - intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 - intSet1)

    assertIsNot<ConstMultiSet<*>>(intSet1 - intSet2)

    intSet1 = constMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 - emptyConstMultiSet())
    assertEquals(emptyConstMultiSet(), emptyConstMultiSet<Int>() - intSet1)

    // equal
    intSet1 = constMultiSetOf(1, 2, 3, 4, 5)
    assertEquals(emptyConstMultiSet(), intSet1 - intSet1)

    intSet1 = constMultiSetOf(1, 2, 3)
    intSet2 = constMultiSetOf(3, 1, 2)
    assertEquals(emptyConstMultiSet(), intSet1 - intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 - intSet1)

    var listSet1 = constMultiSetOf(listOf(1, 2, 3), listOf(456, 789))
    assertEquals(emptyConstMultiSet(), listSet1 - listSet1)

    var otherSet = TestMultiSet(listOf(1, 2, 3, 4, 5))
    assertEquals(emptyConstMultiSet(), intSet1 - otherSet)

    // all shared
    intSet1 = constMultiSetOf(1, 1, 2, 3, 4, 4, 4)
    intSet2 = constMultiSetOf(1, 2, 2, 3, 4, 4)
    var expectedInt = constMultiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = constMultiSetOf(2)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = constMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    intSet2 = constMultiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expectedInt = constMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = constMultiSetOf(1, 5, 5, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = constMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    otherSet = TestMultiSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = constMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    // none shared
    intSet1 = constMultiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
    intSet2 = constMultiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(intSet2, intSet2 - intSet1)

    val stringSet1 = constMultiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
    val stringSet2 = constMultiSetOf("greetings", "planet", "farewell", "planet", "greetings", "farewell")
    assertEquals(stringSet1, stringSet1 - stringSet2)
    assertEquals(stringSet2, stringSet2 - stringSet1)

    // some shared
    intSet1 = constMultiSetOf(1, 1, 2, 3, 4, 5, 5)
    intSet2 = constMultiSetOf(1, 1, 5, 6, 6, 7)
    expectedInt = constMultiSetOf(2, 3, 4, 5)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = constMultiSetOf(6, 6, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    listSet1 = constMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listSet2: ConstMultiSet<List<Int>> = constMultiSetOf(emptyList(), listOf(1, 2, 3))
    var expectedList = constMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = constMultiSetOf(emptyList())
    assertEquals(expectedList, listSet2 - listSet1)

    val errorSet1: ConstMultiSet<Exception> = constMultiSetOf(e1, e2, e1, e1, e2)
    val errorSet2: ConstMultiSet<Exception> = constMultiSetOf(e1, e3, e3, e1, e1)
    var expectedError: ConstMultiSet<Exception> = constMultiSetOf(e2, e2)
    assertEquals(expectedError, errorSet1 - errorSet2)
    expectedError = constMultiSetOf(e3, e3)
    assertEquals(expectedError, errorSet2 - errorSet1)

    val compListSet1: ConstMultiSet<List<Comparable<*>>> = constMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListSet2: ConstMultiSet<List<Comparable<*>>> = constMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    var expectedCompList: MultiSet<List<Comparable<*>>> = constMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedCompList, compListSet1 - compListSet2)
    expectedCompList = constMultiSetOf(listOf(1, 2, 3), emptyList())
    assertEquals(expectedCompList, compListSet2 - compListSet1)

    val otherCompListSet: TestMultiSet<List<Comparable<*>>> = TestMultiSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    expectedCompList = constMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedCompList, compListSet1 - otherCompListSet)
}

fun runConstPlusTests() {
    // empty
    var intSet1: ConstMultiSet<Int> = emptyConstMultiSet()
    var intSet2: ConstMultiSet<Int> = emptyConstMultiSet()
    assertEquals(emptyConstMultiSet(), intSet1 + intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 + intSet1)

    assertIsNot<ConstMultiSet<*>>(intSet1 - intSet2)

    // non-empty
    intSet1 = constMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 + emptyConstMultiSet())
    assertEquals(intSet1, emptyConstMultiSet<Int>() + intSet1)

    intSet1 = constMultiSetOf(1)
    intSet2 = constMultiSetOf(1)
    var expectedInt = constMultiSetOf(1, 1)
    assertEquals(expectedInt, intSet1 + intSet2)
    assertEquals(expectedInt, intSet2 + intSet1)

    intSet1 = constMultiSetOf(1, 2, 2, 3, 3, 3)
    intSet2 = constMultiSetOf(1, 2, 0)
    expectedInt = constMultiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(expectedInt, intSet1 + intSet2)
    assertEquals(expectedInt, intSet2 + intSet1)

    val otherSet = TestMultiSet(listOf(1, 2, 2, 3, 3, 3))
    assertEquals(expectedInt, intSet2 + otherSet)

    val stringSet1 = constMultiSetOf("", "hello", "world", "goodbye")
    val stringSet2 = constMultiSetOf("hi", "no", "bye")
    val expectedString = constMultiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(expectedString, stringSet1 + stringSet2)
    assertEquals(expectedString, stringSet2 + stringSet1)

    val listSet1 = constMultiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listSet2 = constMultiSetOf(emptyList(), listOf(1, 2, 3))
    val expectedList = constMultiSetOf(emptyList(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    val errorSet1: ConstMultiSet<Exception> = constMultiSetOf(e1, e2, e1, e2)
    val errorSet2: ConstMultiSet<Exception> = constMultiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 + errorSet2)
    assertEquals(expectedError, errorSet2 + errorSet1)

    val compListSet1: ConstMultiSet<List<Comparable<*>>> = constMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListSet2: ConstMultiSet<List<Comparable<*>>> = constMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    val expectedCompList: ConstMultiSet<List<Comparable<*>>> = constMultiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedCompList, compListSet1 + compListSet2)
    assertEquals(expectedCompList, compListSet2 + compListSet1)
}

fun runConstIntersectTests() {
    // empty
    var intSet1: ConstMultiSet<Int> = emptyConstMultiSet()

    var intSet2: ConstMultiSet<Int> = emptyConstMultiSet()
    assertEquals(emptyConstMultiSet(), intSet1 intersect intSet2)

    assertIsNot<ConstMultiSet<*>>(intSet1 - intSet2)

    intSet2 = constMultiSetOf(1, 2, 3)
    assertEquals(emptyConstMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 intersect intSet1)

    // none shared
    intSet1 = constMultiSetOf(1, 2, 3)
    intSet2 = constMultiSetOf(4, 5, 6, 7, 8)
    assertEquals(emptyConstMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 intersect intSet1)

    var otherSet = TestMultiSet(listOf(4, 5, 6, 7, 8))
    assertEquals(emptyConstMultiSet(), intSet1 intersect otherSet)

    var listSet1 = constMultiSetOf(listOf(1, 2, 3), listOf(4, 5))
    var listSet2 = constMultiSetOf(listOf(1, 2), listOf(3, 4, 5))
    assertEquals(emptyConstMultiSet(), listSet1 intersect listSet2)
    assertEquals(emptyConstMultiSet(), listSet2 intersect listSet1)

    // all shared
    intSet1 = constMultiSetOf(1, 2, 3)
    intSet2 = constMultiSetOf(1, 2, 3)
    var expectedInt = constMultiSetOf(1, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    intSet1 = constMultiSetOf(1, 1, 2, 2, 3, 3)
    intSet2 = constMultiSetOf(1, 2, 2, 2, 3)
    expectedInt = constMultiSetOf(1, 2, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    // some shared
    intSet1 = constMultiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
    intSet2 = constMultiSetOf(-1, 14, 3, 9, 9, 6)
    expectedInt = constMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    otherSet = TestMultiSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = constMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect otherSet)

    listSet1 = constMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    listSet2 = constMultiSetOf(emptyList(), listOf(1, 2, 3))
    val expectedList = constMultiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    val errorSet1: ConstMultiSet<Exception> = constMultiSetOf(e1, e2, e1, e2)
    val errorSet2: ConstMultiSet<Exception> = constMultiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: MultiSet<Exception> = constMultiSetOf(e1, e1, e2)
    assertEquals(expectedError, errorSet1 intersect errorSet2)
    assertEquals(expectedError, errorSet2 intersect errorSet1)
}
