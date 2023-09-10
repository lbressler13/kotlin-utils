package xyz.lbres.kotlinutils.set.multiset.impl.mutableconst

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.testimpl.TestMultiSet
import xyz.lbres.kotlinutils.set.multiset.testimpl.TestMutableMultiSet
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runMutableConstMinusTests() {
    // empty
    var intSet1 = mutableConstMultiSetOf<Int>()
    var intSet2 = mutableConstMultiSetOf<Int>()
    assertEquals(emptyConstMultiSet(), intSet1 - intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 - intSet1)

    intSet1 = mutableConstMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 - intSet1)

    // equal
    intSet1 = mutableConstMultiSetOf(1, 2, 3, 4, 5)
    assertEquals(emptyConstMultiSet(), intSet1 - intSet1)

    var listSet1 = mutableConstMultiSetOf(listOf(1, 2, 3), listOf(456, 789))
    assertEquals(emptyConstMultiSet(), listSet1 - listSet1)

    var otherSet = mutableConstMultiSetOf(1, 2, 3, 4, 5)
    assertEquals(emptyConstMultiSet(), intSet1 - otherSet)

    // all shared
    intSet1 = mutableConstMultiSetOf(1, 1, 2, 3, 4, 4, 4)
    intSet2 = mutableConstMultiSetOf(1, 2, 2, 3, 4, 4)
    var expectedInt = mutableConstMultiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = mutableConstMultiSetOf(2)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = mutableConstMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    intSet2 = mutableConstMultiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expectedInt = mutableConstMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = mutableConstMultiSetOf(1, 5, 5, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    // none shared
    intSet1 = mutableConstMultiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
    intSet2 = mutableConstMultiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(intSet2, intSet2 - intSet1)

    intSet1 = mutableConstMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    otherSet = mutableConstMultiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expectedInt = mutableConstMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    val stringSet1 = mutableConstMultiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
    val stringSet2 = mutableConstMultiSetOf("greetings", "planet", "farewell", "planet", "greetings", "farewell")
    assertEquals(stringSet1, stringSet1 - stringSet2)
    assertEquals(stringSet2, stringSet2 - stringSet1)

    // some shared
    intSet1 = mutableConstMultiSetOf(1, 1, 2, 3, 4, 5, 5)
    intSet2 = mutableConstMultiSetOf(1, 1, 5, 6, 6, 7)
    expectedInt = mutableConstMultiSetOf(2, 3, 4, 5)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = mutableConstMultiSetOf(6, 6, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = mutableConstMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    otherSet = mutableConstMultiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expectedInt = mutableConstMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    listSet1 = mutableConstMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listSet2: MutableConstMultiSet<List<Int>> = mutableConstMultiSetOf(emptyList(), listOf(1, 2, 3))
    var expectedList = mutableConstMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = mutableConstMultiSetOf(emptyList())
    assertEquals(expectedList, listSet2 - listSet1)

    val errorSet1: MutableConstMultiSet<Exception> = mutableConstMultiSetOf(e1, e2, e1, e1, e2)
    val errorSet2: MutableConstMultiSet<Exception> = mutableConstMultiSetOf(e1, e3, e3, e1, e1)
    var expectedError: MutableConstMultiSet<Exception> = mutableConstMultiSetOf(e2, e2)
    assertEquals(expectedError, errorSet1 - errorSet2)
    expectedError = mutableConstMultiSetOf(e3, e3)
    assertEquals(expectedError, errorSet2 - errorSet1)

    val compListMs1: MutableConstMultiSet<List<Comparable<*>>> = mutableConstMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListMs2: MutableConstMultiSet<List<Comparable<*>>> = mutableConstMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    var compListExpected: MutableConstMultiSet<List<Comparable<*>>> = mutableConstMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 - compListMs2)
    compListExpected = mutableConstMultiSetOf(listOf(1, 2, 3), emptyList())
    assertEquals(compListExpected, compListMs2 - compListMs1)

    val otherCompListSet: TestMutableMultiSet<List<Comparable<*>>> = TestMutableMultiSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    compListExpected = mutableConstMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 - otherCompListSet)

    // with immutable
    intSet1 = mutableConstMultiSetOf(1, 2, 3, 4)
    val immutable = multiSetOf(1, 4, 4, 5)
    expectedInt = mutableConstMultiSetOf(2, 3)
    assertEquals(expectedInt, intSet1 - immutable)

    intSet1 = mutableConstMultiSetOf(1, 2, 3, 4)
    val immutableOther = TestMultiSet(multiSetOf(1, 4, 4, 5))
    expectedInt = mutableConstMultiSetOf(2, 3)
    assertEquals(expectedInt, intSet1 - immutableOther)
}

fun runMutableConstPlusTests() {
    // empty
    var intSet1 = emptyConstMultiSet<Int>()
    var intSet2 = emptyConstMultiSet<Int>()
    assertEquals(emptyConstMultiSet(), intSet1 + intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 + intSet1)

    intSet1 = mutableConstMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 + intSet2)

    // non-empty
    intSet1 = mutableConstMultiSetOf(1)
    intSet2 = mutableConstMultiSetOf(1)
    var expected = mutableConstMultiSetOf(1, 1)
    assertEquals(expected, intSet1 + intSet2)

    intSet1 = mutableConstMultiSetOf(1, 2, 2, 3, 3, 3)
    intSet2 = mutableConstMultiSetOf(1, 2, 0)
    expected = mutableConstMultiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(expected, intSet1 + intSet2)

    val otherSet = TestMutableMultiSet(listOf(1, 2, 2, 3, 3, 3))
    assertEquals(expected, intSet2 + otherSet)

    val sms1 = mutableConstMultiSetOf("", "hello", "world", "goodbye")
    val sms2 = mutableConstMultiSetOf("hi", "no", "bye")
    val sExpected = mutableConstMultiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(sExpected, sms1 + sms2)
    assertEquals(sExpected, sms2 + sms1)

    val listSet1 = mutableConstMultiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listSet2 = mutableConstMultiSetOf(emptyList(), listOf(1, 2, 3))
    val expectedList = mutableConstMultiSetOf(emptyList(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    val errorSet1: MutableConstMultiSet<Exception> = mutableConstMultiSetOf(e1, e2, e1, e2)
    val errorSet2: MutableConstMultiSet<Exception> = mutableConstMultiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: MutableConstMultiSet<Exception> = mutableConstMultiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 + errorSet2)
    assertEquals(expectedError, errorSet2 + errorSet1)

    val compListMs1: MutableConstMultiSet<List<Comparable<*>>> = mutableConstMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListMs2: MutableConstMultiSet<List<Comparable<*>>> = mutableConstMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    val compListExpected: MutableConstMultiSet<List<Comparable<*>>> = mutableConstMultiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 + compListMs2)
    assertEquals(compListExpected, compListMs2 + compListMs1)

    // with immutable
    intSet1 = mutableConstMultiSetOf(1, 2, 3)
    val immutable = multiSetOf(1, 4, 5)
    expected = mutableConstMultiSetOf(1, 1, 2, 3, 4, 5)
    assertEquals(expected, intSet1 + immutable)
}

fun runMutableConstIntersectTests() {
    // empty
    var intSet1 = emptyConstMultiSet<Int>()

    var intSet2 = emptyConstMultiSet<Int>()
    assertEquals(emptyConstMultiSet(), intSet1 intersect intSet2)

    intSet2 = mutableConstMultiSetOf(1, 2, 3)
    assertEquals(emptyConstMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 intersect intSet1)

    // none shared
    intSet1 = mutableConstMultiSetOf(1, 2, 3)
    intSet2 = mutableConstMultiSetOf(4, 5, 6, 7, 8)
    assertEquals(emptyConstMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 intersect intSet1)

    var otherSet = TestMutableMultiSet(listOf(4, 5, 6, 7, 8))
    assertEquals(emptyConstMultiSet(), intSet1 intersect otherSet)

    var listSet1 = mutableConstMultiSetOf(listOf(1, 2, 3), listOf(4, 5))
    var listSet2 = mutableConstMultiSetOf(listOf(1, 2), listOf(3, 4, 5))
    assertEquals(emptyConstMultiSet(), listSet1 intersect listSet2)
    assertEquals(emptyConstMultiSet(), listSet2 intersect listSet1)

    // all shared
    intSet1 = mutableConstMultiSetOf(1, 2, 3)
    intSet2 = mutableConstMultiSetOf(1, 2, 3)
    var expectedInt = mutableConstMultiSetOf(1, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    intSet1 = mutableConstMultiSetOf(1, 1, 2, 2, 3, 3)
    intSet2 = mutableConstMultiSetOf(1, 2, 2, 2, 3)
    expectedInt = mutableConstMultiSetOf(1, 2, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    // some shared
    intSet1 = mutableConstMultiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
    intSet2 = mutableConstMultiSetOf(-1, 14, 3, 9, 9, 6)
    expectedInt = mutableConstMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    otherSet = TestMutableMultiSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = mutableConstMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect otherSet)

    listSet1 = mutableConstMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    listSet2 = mutableConstMultiSetOf(emptyList(), listOf(1, 2, 3))
    val expectedList = mutableConstMultiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    val errorSet1: MutableConstMultiSet<Exception> = mutableConstMultiSetOf(e1, e2, e1, e2)
    val errorSet2: MutableConstMultiSet<Exception> = mutableConstMultiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: MutableConstMultiSet<Exception> = mutableConstMultiSetOf(e1, e1, e2)
    assertEquals(expectedError, errorSet1 intersect errorSet2)
    assertEquals(expectedError, errorSet2 intersect errorSet1)

    // with immutable
    intSet1 = mutableConstMultiSetOf(1, 2, 3, 4)
    val immutable = multiSetOf(1, 4, 4, 5)
    expectedInt = mutableConstMultiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 intersect immutable)
}

fun runMutableConstEqualsTests() {
    // equals
    var set1: MutableConstMultiSet<Int> = mutableConstMultiSetOf()
    assertEquals(set1, set1)

    set1 = mutableConstMultiSetOf(3)
    assertEquals(set1, set1)

    set1 = mutableConstMultiSetOf(3, 3, 3)
    assertEquals(set1, set1)

    set1 = mutableConstMultiSetOf(2, 3, 4)
    assertEquals(set1, set1)

    set1 = mutableConstMultiSetOf(1, 2, 3)
    var set2 = mutableConstMultiSetOf(3, 1, 2)
    assertEquals(set1, set2)
    assertEquals(set2, set1)

    var otherSet: MutableMultiSet<Int> = TestMutableMultiSet(listOf(1, 2, 3))
    assertEquals(set1, otherSet)

    // not equals
    set1 = mutableConstMultiSetOf()
    set2 = mutableConstMultiSetOf(0)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = mutableConstMultiSetOf(1, 1)
    set2 = mutableConstMultiSetOf(1)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = mutableConstMultiSetOf(1, 2)
    set2 = mutableConstMultiSetOf(2, 2)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = mutableConstMultiSetOf(-1, 3, 1, -3)
    otherSet = TestMutableMultiSet(listOf(2, -2))
    assertNotEquals<MutableMultiSet<Int>>(set1, otherSet)

    // other type
    val stringSet1 = mutableConstMultiSetOf("", "abc")
    assertEquals(stringSet1, stringSet1)

    val stringSet2 = mutableConstMultiSetOf("abc")
    assertNotEquals(stringSet1, stringSet2)
    assertNotEquals(stringSet2, stringSet1)

    val listSet1 = mutableConstMultiSetOf(listOf(12, 34, 56), listOf(77, 78, 0, 15), listOf(5))
    assertEquals(listSet1, listSet1)

    val listSet2 = mutableConstMultiSetOf(listOf(12, 34, 56))
    assertNotEquals(listSet1, listSet2)
    assertNotEquals(listSet2, listSet1)

    assertFalse(set1 == stringSet1)

    // immutable
    set1 = mutableConstMultiSetOf(1, 2, 3)
    var immutableSet = multiSetOf(1, 2, 3)
    assertEquals(set1, immutableSet)

    immutableSet = multiSetOf(1)
    assertNotEquals(set1, immutableSet)
}
