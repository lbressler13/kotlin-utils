package xyz.lbres.kotlinutils.set.multiset.impl.mutable

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.testutils.TestMultiSet
import xyz.lbres.kotlinutils.set.multiset.testutils.TestMutableMultiSet
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runMutableMinusTests() {
    // empty
    var intSet1 = mutableMultiSetOf<Int>()
    var intSet2 = mutableMultiSetOf<Int>()
    assertEquals(emptyMultiSet(), intSet1 - intSet2)
    assertEquals(emptyMultiSet(), intSet2 - intSet1)

    intSet1 = mutableMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(emptyMultiSet(), intSet2 - intSet1)

    // equal
    intSet1 = mutableMultiSetOf(1, 2, 3, 4, 5)
    assertEquals(emptyMultiSet(), intSet1 - intSet1)

    var listSet1 = mutableMultiSetOf(listOf(1, 2, 3), listOf(456, 789))
    assertEquals(emptyMultiSet(), listSet1 - listSet1)

    var otherSet = TestMutableMultiSet(listOf(1, 2, 3, 4, 5))
    assertEquals(emptyMultiSet(), intSet1 - otherSet)

    // all shared
    intSet1 = mutableMultiSetOf(1, 1, 2, 3, 4, 4, 4)
    intSet2 = mutableMultiSetOf(1, 2, 2, 3, 4, 4)
    var expectedInt = mutableMultiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = mutableMultiSetOf(2)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = mutableMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    intSet2 = mutableMultiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expectedInt = mutableMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = mutableMultiSetOf(1, 5, 5, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    // none shared
    intSet1 = mutableMultiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
    intSet2 = mutableMultiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(intSet2, intSet2 - intSet1)

    intSet1 = mutableMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    otherSet = TestMutableMultiSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = mutableMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    val stringSet1 = mutableMultiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
    val stringSet2 = mutableMultiSetOf("greetings", "planet", "farewell", "planet", "greetings", "farewell")
    assertEquals(stringSet1, stringSet1 - stringSet2)
    assertEquals(stringSet2, stringSet2 - stringSet1)

    // some shared
    intSet1 = mutableMultiSetOf(1, 1, 2, 3, 4, 5, 5)
    intSet2 = mutableMultiSetOf(1, 1, 5, 6, 6, 7)
    expectedInt = mutableMultiSetOf(2, 3, 4, 5)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = mutableMultiSetOf(6, 6, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = mutableMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    otherSet = TestMutableMultiSet(listOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7))
    expectedInt = mutableMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    listSet1 = mutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    var listSet2: MutableMultiSet<List<Int>> = mutableMultiSetOf(emptyList(), listOf(1, 2, 3))
    var expectedList = mutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = mutableMultiSetOf(emptyList())
    assertEquals(expectedList, listSet2 - listSet1)

    val errorSet1: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e2, e1, e1, e2)
    val errorSet2: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e3, e3, e1, e1)
    var expectedError: MutableMultiSet<Exception> = mutableMultiSetOf(e2, e2)
    assertEquals(expectedError, errorSet1 - errorSet2)
    expectedError = mutableMultiSetOf(e3, e3)
    assertEquals(expectedError, errorSet2 - errorSet1)

    val compListMs1: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListMs2: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    var compListExpected: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 - compListMs2)
    compListExpected = mutableMultiSetOf(listOf(1, 2, 3), emptyList())
    assertEquals(compListExpected, compListMs2 - compListMs1)

    val otherCompListSet: TestMutableMultiSet<List<Comparable<*>>> = TestMutableMultiSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    compListExpected = mutableMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 - otherCompListSet)

    // with immutable
    intSet1 = mutableMultiSetOf(1, 2, 3, 4)
    val immutable = multiSetOf(1, 4, 4, 5)
    expectedInt = mutableMultiSetOf(2, 3)
    assertEquals(expectedInt, intSet1 - immutable)

    intSet1 = mutableMultiSetOf(1, 2, 3, 4)
    val immutableOther = TestMultiSet(multiSetOf(1, 4, 4, 5))
    expectedInt = mutableMultiSetOf(2, 3)
    assertEquals(expectedInt, intSet1 - immutableOther)

    // changing values
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet1 = mutableMultiSetOf(mutableList1, mutableList2)
    listSet2 = mutableMultiSetOf(listOf(1, 2, 3), listOf(0, 5, 7))

    expectedList = mutableMultiSetOf()
    assertEquals(expectedList, listSet1 - listSet2)
    assertEquals(expectedList, listSet2 - listSet1)

    mutableList2.add(3)
    expectedList = mutableMultiSetOf(listOf(0, 5, 7, 3))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = mutableMultiSetOf(listOf(0, 5, 7))
    assertEquals(expectedList, listSet2 - listSet1)
}

fun runMutablePlusTests() {
    // empty
    var intSet1 = emptyMultiSet<Int>()
    var intSet2 = emptyMultiSet<Int>()
    assertEquals(emptyMultiSet(), intSet1 + intSet2)
    assertEquals(emptyMultiSet(), intSet2 + intSet1)

    intSet1 = mutableMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 + intSet2)

    // non-empty
    intSet1 = mutableMultiSetOf(1)
    intSet2 = mutableMultiSetOf(1)
    var expected = mutableMultiSetOf(1, 1)
    assertEquals(expected, intSet1 + intSet2)

    intSet1 = mutableMultiSetOf(1, 2, 2, 3, 3, 3)
    intSet2 = mutableMultiSetOf(1, 2, 0)
    expected = mutableMultiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(expected, intSet1 + intSet2)

    val otherSet = TestMutableMultiSet(listOf(1, 2, 2, 3, 3, 3))
    assertEquals(expected, intSet2 + otherSet)

    val sms1 = mutableMultiSetOf("", "hello", "world", "goodbye")
    val sms2 = mutableMultiSetOf("hi", "no", "bye")
    val sExpected = mutableMultiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(sExpected, sms1 + sms2)
    assertEquals(sExpected, sms2 + sms1)

    var listSet1 = mutableMultiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    var listSet2 = mutableMultiSetOf(emptyList(), listOf(1, 2, 3))
    var expectedList = mutableMultiSetOf(emptyList(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    val errorSet1: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e2, e1, e2)
    val errorSet2: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 + errorSet2)
    assertEquals(expectedError, errorSet2 + errorSet1)

    val compListMs1: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListMs2: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    val compListExpected: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 + compListMs2)
    assertEquals(compListExpected, compListMs2 + compListMs1)

    // with immutable
    intSet1 = mutableMultiSetOf(1, 2, 3)
    val immutable = multiSetOf(1, 4, 5)
    expected = mutableMultiSetOf(1, 1, 2, 3, 4, 5)
    assertEquals(expected, intSet1 + immutable)

    // changing values
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet1 = mutableMultiSetOf(mutableList1, mutableList2)
    listSet2 = mutableMultiSetOf(listOf(1), mutableList1)

    expectedList = mutableMultiSetOf(listOf(1), listOf(1, 2, 3), listOf(1, 2, 3), listOf(0, 5, 7))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    mutableList1.add(4)
    expectedList = mutableMultiSetOf(listOf(1), listOf(1, 2, 3, 4), listOf(1, 2, 3, 4), listOf(0, 5, 7))
    assertEquals(expectedList, listSet2 + listSet1)
    assertEquals(expectedList, listSet1 + listSet2)
}

fun runMutableIntersectTests() {
    // empty
    var intSet1 = emptyMultiSet<Int>()

    var intSet2 = emptyMultiSet<Int>()
    assertEquals(emptyMultiSet(), intSet1 intersect intSet2)

    intSet2 = mutableMultiSetOf(1, 2, 3)
    assertEquals(emptyMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyMultiSet(), intSet2 intersect intSet1)

    // none shared
    intSet1 = mutableMultiSetOf(1, 2, 3)
    intSet2 = mutableMultiSetOf(4, 5, 6, 7, 8)
    assertEquals(emptyMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyMultiSet(), intSet2 intersect intSet1)

    var otherSet = TestMutableMultiSet(listOf(4, 5, 6, 7, 8))
    assertEquals(emptyMultiSet(), intSet1 intersect otherSet)

    var listSet1 = mutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5))
    var listSet2 = mutableMultiSetOf(listOf(1, 2), listOf(3, 4, 5))
    assertEquals(emptyMultiSet(), listSet1 intersect listSet2)
    assertEquals(emptyMultiSet(), listSet2 intersect listSet1)

    // all shared
    intSet1 = mutableMultiSetOf(1, 2, 3)
    intSet2 = mutableMultiSetOf(1, 2, 3)
    var expectedInt = mutableMultiSetOf(1, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    intSet1 = mutableMultiSetOf(1, 1, 2, 2, 3, 3)
    intSet2 = mutableMultiSetOf(1, 2, 2, 2, 3)
    expectedInt = mutableMultiSetOf(1, 2, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    // some shared
    intSet1 = mutableMultiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
    intSet2 = mutableMultiSetOf(-1, 14, 3, 9, 9, 6)
    expectedInt = mutableMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    otherSet = TestMutableMultiSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = mutableMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect otherSet)

    listSet1 = mutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    listSet2 = mutableMultiSetOf(emptyList(), listOf(1, 2, 3))
    var expectedList = mutableMultiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    val errorSet1: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e2, e1, e2)
    val errorSet2: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e1, e2)
    assertEquals(expectedError, errorSet1 intersect errorSet2)
    assertEquals(expectedError, errorSet2 intersect errorSet1)

    // with immutable
    intSet1 = mutableMultiSetOf(1, 2, 3, 4)
    val immutable = multiSetOf(1, 4, 4, 5)
    expectedInt = mutableMultiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 intersect immutable)

    // changing values
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet1 = mutableMultiSetOf(mutableList1, mutableList2)
    listSet2 = mutableMultiSetOf(listOf(1), mutableList1)

    expectedList = mutableMultiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    listSet2 = mutableMultiSetOf(listOf(1), listOf(1, 2, 3))
    expectedList = mutableMultiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    mutableList2.clear()
    mutableList2.add(1)
    expectedList = mutableMultiSetOf(listOf(1), listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    mutableList1.add(4)
    mutableList2.add(1)
    expectedList = mutableMultiSetOf()
    assertEquals(expectedList, listSet2 intersect listSet1)
    assertEquals(expectedList, listSet1 intersect listSet2)
}

fun runMutableEqualsTests() {
    // equals
    var set1: MutableMultiSet<Int> = mutableMultiSetOf()
    assertEquals(set1, set1)

    set1 = mutableMultiSetOf(3)
    assertEquals(set1, set1)

    set1 = mutableMultiSetOf(3, 3, 3)
    assertEquals(set1, set1)

    set1 = mutableMultiSetOf(2, 3, 4)
    assertEquals(set1, set1)

    set1 = mutableMultiSetOf(1, 2, 3)
    var set2 = mutableMultiSetOf(3, 1, 2)
    assertEquals(set1, set2)
    assertEquals(set2, set1)

    var otherSet = TestMutableMultiSet(listOf(1, 2, 3))
    assertEquals(set1, otherSet)

    // not equals
    set1 = mutableMultiSetOf()
    set2 = mutableMultiSetOf(0)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = mutableMultiSetOf(1, 1)
    set2 = mutableMultiSetOf(1)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = mutableMultiSetOf(1, 2)
    set2 = mutableMultiSetOf(2, 2)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = mutableMultiSetOf(-1, 3, 1, -3)
    otherSet = TestMutableMultiSet(listOf(2, -2))
    assertNotEquals(set1, otherSet)

    // other type
    val stringSet1 = mutableMultiSetOf("", "abc")
    assertEquals(stringSet1, stringSet1)

    val stringSet2 = mutableMultiSetOf("abc")
    assertNotEquals(stringSet1, stringSet2)
    assertNotEquals(stringSet2, stringSet1)

    var listSet1 = mutableMultiSetOf(listOf(12, 34, 56), listOf(77, 78, 0, 15), listOf(5))
    assertEquals(listSet1, listSet1)

    var listSet2 = mutableMultiSetOf(listOf(12, 34, 56))
    assertNotEquals(listSet1, listSet2)
    assertNotEquals(listSet2, listSet1)

    assertFalse(set1 == stringSet1)

    // immutable
    set1 = mutableMultiSetOf(1, 2, 3)
    var immutableSet = multiSetOf(1, 2, 3)
    assertEquals(set1, immutableSet)

    immutableSet = multiSetOf(1)
    assertNotEquals(set1, immutableSet)

    // changing values
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val mutableList3 = mutableListOf(0, 5, 7)
    listSet1 = mutableMultiSetOf(mutableList1, mutableList2)
    listSet2 = mutableMultiSetOf(emptyList(), mutableList3)
    assertNotEquals(listSet1, listSet2)

    mutableList1.clear()
    assertEquals(listSet2, listSet1)
    assertEquals(listSet1, listSet2)

    mutableList2.add(7)
    assertNotEquals(listSet1, listSet2)

    mutableList3.add(7)
    assertEquals(listSet1, listSet2)
    assertEquals(listSet2, listSet1)

    mutableList2.clear()
    mutableList2.add(1)
    mutableList2.add(2)
    listSet1 = mutableMultiSetOf(mutableList2, mutableList2, mutableList1, mutableList2)
    listSet2 = mutableMultiSetOf(listOf(1, 2), listOf(1, 2), listOf(1, 2), emptyList())
    assertEquals(listSet1, listSet2)

    mutableList2.remove(1)
    listSet2 = mutableMultiSetOf(listOf(2), listOf(2), listOf(2), emptyList())
    assertEquals(listSet2, listSet1)

    val mutableSet1 = mutableSetOf(1, 2, 3)
    val setSet1: MutableMultiSet<Set<Int>> = mutableMultiSetOf(mutableSet1)
    var setSet2 = mutableMultiSetOf(setOf(1, 2, 3))
    assertEquals(setSet1, setSet2)
    assertEquals(setSet2, setSet1)

    mutableSet1.remove(2)
    assertNotEquals(setSet1, setSet2)
    assertNotEquals(setSet2, setSet1)

    setSet2 = mutableMultiSetOf(setOf(1, 3))
    assertEquals(setSet1, setSet2)
    assertEquals(setSet2, setSet1)

    mutableSet1.add(2)
    setSet2 = TestMutableMultiSet(listOf(setOf(1, 3, 2)))
    assertEquals(setSet2, setSet1)
}
