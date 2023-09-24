package xyz.lbres.kotlinutils.set.multiset.constimpl.mutable

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
    var intSet1 = constMutableMultiSetOf<Int>()
    var intSet2 = constMutableMultiSetOf<Int>()
    assertEquals(constMultiSetOf(), intSet1 - intSet2)
    assertEquals(constMultiSetOf(), intSet2 - intSet1)

    intSet1 = constMutableMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(constMultiSetOf(), intSet2 - intSet1)

    // equal
    intSet1 = constMutableMultiSetOf(1, 2, 3, 4, 5)
    assertEquals(constMultiSetOf(), intSet1 - intSet1)

    var listSet1 = constMutableMultiSetOf(listOf(1, 2, 3), listOf(456, 789))
    assertEquals(constMultiSetOf(), listSet1 - listSet1)

    var otherSet = constMutableMultiSetOf(1, 2, 3, 4, 5)
    assertEquals(constMultiSetOf(), intSet1 - otherSet)

    // all shared
    intSet1 = constMutableMultiSetOf(1, 1, 2, 3, 4, 4, 4)
    intSet2 = constMutableMultiSetOf(1, 2, 2, 3, 4, 4)
    var expectedInt = constMutableMultiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = constMutableMultiSetOf(2)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = constMutableMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    intSet2 = constMutableMultiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expectedInt = constMutableMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = constMutableMultiSetOf(1, 5, 5, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    // none shared
    intSet1 = constMutableMultiSetOf(1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 8)
    intSet2 = constMutableMultiSetOf(-1, -1, -1, -1, -2, -3, -4, -5, -6, -7, -7, -8)
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(intSet2, intSet2 - intSet1)

    intSet1 = constMutableMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    otherSet = constMutableMultiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expectedInt = constMutableMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    val stringSet1 = constMutableMultiSetOf("hello", "world", "goodbye", "world", "hello", "goodbye")
    val stringSet2 = constMutableMultiSetOf("greetings", "planet", "farewell", "planet", "greetings", "farewell")
    assertEquals(stringSet1, stringSet1 - stringSet2)
    assertEquals(stringSet2, stringSet2 - stringSet1)

    // some shared
    intSet1 = constMutableMultiSetOf(1, 1, 2, 3, 4, 5, 5)
    intSet2 = constMutableMultiSetOf(1, 1, 5, 6, 6, 7)
    expectedInt = constMutableMultiSetOf(2, 3, 4, 5)
    assertEquals(expectedInt, intSet1 - intSet2)
    expectedInt = constMutableMultiSetOf(6, 6, 7)
    assertEquals(expectedInt, intSet2 - intSet1)

    intSet1 = constMutableMultiSetOf(1, 2, 2, 2, 3, 3, 5, 6, 6, 7)
    otherSet = constMutableMultiSetOf(1, 1, 2, 3, 3, 5, 5, 5, 6, 7, 7)
    expectedInt = constMutableMultiSetOf(2, 2, 6)
    assertEquals(expectedInt, intSet1 - otherSet)

    listSet1 = constMutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listSet2: ConstMutableMultiSet<List<Int>> = constMutableMultiSetOf(emptyList(), listOf(1, 2, 3))
    var expectedList = constMutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = constMutableMultiSetOf(emptyList())
    assertEquals(expectedList, listSet2 - listSet1)

    val errorSet1: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e2, e1, e1, e2)
    val errorSet2: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e3, e3, e1, e1)
    var expectedError: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e2, e2)
    assertEquals(expectedError, errorSet1 - errorSet2)
    expectedError = constMutableMultiSetOf(e3, e3)
    assertEquals(expectedError, errorSet2 - errorSet1)

    val compListMs1: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListMs2: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    var compListExpected: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 - compListMs2)
    compListExpected = constMutableMultiSetOf(listOf(1, 2, 3), emptyList())
    assertEquals(compListExpected, compListMs2 - compListMs1)

    val otherCompListSet: TestMutableMultiSet<List<Comparable<*>>> = TestMutableMultiSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    compListExpected = constMutableMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 - otherCompListSet)

    // with immutable
    intSet1 = constMutableMultiSetOf(1, 2, 3, 4)
    val immutable = multiSetOf(1, 4, 4, 5)
    expectedInt = constMutableMultiSetOf(2, 3)
    assertEquals(expectedInt, intSet1 - immutable)

    intSet1 = constMutableMultiSetOf(1, 2, 3, 4)
    val immutableOther = TestMultiSet(multiSetOf(1, 4, 4, 5))
    expectedInt = constMutableMultiSetOf(2, 3)
    assertEquals(expectedInt, intSet1 - immutableOther)
}

fun runMutableConstPlusTests() {
    // empty
    var intSet1: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    var intSet2: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    assertEquals(emptyMultiSet(), intSet1 + intSet2)
    assertEquals(emptyMultiSet(), intSet2 + intSet1)

    intSet1 = constMutableMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 + intSet2)

    // non-empty
    intSet1 = constMutableMultiSetOf(1)
    intSet2 = constMutableMultiSetOf(1)
    var expected = constMutableMultiSetOf(1, 1)
    assertEquals(expected, intSet1 + intSet2)

    intSet1 = constMutableMultiSetOf(1, 2, 2, 3, 3, 3)
    intSet2 = constMutableMultiSetOf(1, 2, 0)
    expected = constMutableMultiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(expected, intSet1 + intSet2)

    val otherSet = TestMutableMultiSet(listOf(1, 2, 2, 3, 3, 3))
    assertEquals(expected, intSet2 + otherSet)

    val sms1 = constMutableMultiSetOf("", "hello", "world", "goodbye")
    val sms2 = constMutableMultiSetOf("hi", "no", "bye")
    val sExpected = constMutableMultiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(sExpected, sms1 + sms2)
    assertEquals(sExpected, sms2 + sms1)

    val listSet1 = constMutableMultiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listSet2 = constMutableMultiSetOf(emptyList(), listOf(1, 2, 3))
    val expectedList = constMutableMultiSetOf(emptyList(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    val errorSet1: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e2, e1, e2)
    val errorSet2: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 + errorSet2)
    assertEquals(expectedError, errorSet2 + errorSet1)

    val compListMs1: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListMs2: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    val compListExpected: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListMs1 + compListMs2)
    assertEquals(compListExpected, compListMs2 + compListMs1)

    // with immutable
    intSet1 = constMutableMultiSetOf(1, 2, 3)
    val immutable = multiSetOf(1, 4, 5)
    expected = constMutableMultiSetOf(1, 1, 2, 3, 4, 5)
    assertEquals(expected, intSet1 + immutable)
}

fun runMutableConstIntersectTests() {
    // empty
    var intSet1 = constMutableMultiSetOf<Int>()

    var intSet2 = constMutableMultiSetOf<Int>()
    assertEquals(constMultiSetOf(), intSet1 intersect intSet2)

    intSet2 = constMutableMultiSetOf(1, 2, 3)
    assertEquals(constMultiSetOf(), intSet1 intersect intSet2)
    assertEquals(constMultiSetOf(), intSet2 intersect intSet1)

    // none shared
    intSet1 = constMutableMultiSetOf(1, 2, 3)
    intSet2 = constMutableMultiSetOf(4, 5, 6, 7, 8)
    assertEquals(constMultiSetOf(), intSet1 intersect intSet2)
    assertEquals(constMultiSetOf(), intSet2 intersect intSet1)

    var otherSet = TestMutableMultiSet(listOf(4, 5, 6, 7, 8))
    assertEquals(constMultiSetOf(), intSet1 intersect otherSet)

    var listSet1 = constMutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5))
    var listSet2 = constMutableMultiSetOf(listOf(1, 2), listOf(3, 4, 5))
    assertEquals(constMultiSetOf(), listSet1 intersect listSet2)
    assertEquals(constMultiSetOf(), listSet2 intersect listSet1)

    // all shared
    intSet1 = constMutableMultiSetOf(1, 2, 3)
    intSet2 = constMutableMultiSetOf(1, 2, 3)
    var expectedInt = constMutableMultiSetOf(1, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    intSet1 = constMutableMultiSetOf(1, 1, 2, 2, 3, 3)
    intSet2 = constMutableMultiSetOf(1, 2, 2, 2, 3)
    expectedInt = constMutableMultiSetOf(1, 2, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    // some shared
    intSet1 = constMutableMultiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
    intSet2 = constMutableMultiSetOf(-1, 14, 3, 9, 9, 6)
    expectedInt = constMutableMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    otherSet = TestMutableMultiSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = constMutableMultiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect otherSet)

    listSet1 = constMutableMultiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    listSet2 = constMutableMultiSetOf(emptyList(), listOf(1, 2, 3))
    val expectedList = constMutableMultiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    val errorSet1: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e2, e1, e2)
    val errorSet2: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e1, e2)
    assertEquals(expectedError, errorSet1 intersect errorSet2)
    assertEquals(expectedError, errorSet2 intersect errorSet1)

    // with immutable
    intSet1 = constMutableMultiSetOf(1, 2, 3, 4)
    val immutable = multiSetOf(1, 4, 4, 5)
    expectedInt = constMutableMultiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 intersect immutable)
}

fun runMutableConstEqualsTests() {
    // equals
    var set1: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    assertEquals(set1, set1)

    set1 = constMutableMultiSetOf(3)
    assertEquals(set1, set1)

    set1 = constMutableMultiSetOf(3, 3, 3)
    assertEquals(set1, set1)

    set1 = constMutableMultiSetOf(2, 3, 4)
    assertEquals(set1, set1)

    set1 = constMutableMultiSetOf(1, 2, 3)
    var set2 = constMutableMultiSetOf(3, 1, 2)
    assertEquals(set1, set2)
    assertEquals(set2, set1)

    var otherSet: MutableMultiSet<Int> = TestMutableMultiSet(listOf(1, 2, 3))
    assertEquals(set1, otherSet)

    // not equals
    set1 = constMutableMultiSetOf()
    set2 = constMutableMultiSetOf(0)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = constMutableMultiSetOf(1, 1)
    set2 = constMutableMultiSetOf(1)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = constMutableMultiSetOf(1, 2)
    set2 = constMutableMultiSetOf(2, 2)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = constMutableMultiSetOf(-1, 3, 1, -3)
    otherSet = TestMutableMultiSet(listOf(2, -2))
    assertNotEquals<MutableMultiSet<Int>>(set1, otherSet)

    // other type
    val stringSet1 = constMutableMultiSetOf("", "abc")
    assertEquals(stringSet1, stringSet1)

    val stringSet2 = constMutableMultiSetOf("abc")
    assertNotEquals(stringSet1, stringSet2)
    assertNotEquals(stringSet2, stringSet1)

    val listSet1 = constMutableMultiSetOf(listOf(12, 34, 56), listOf(77, 78, 0, 15), listOf(5))
    assertEquals(listSet1, listSet1)

    val listSet2 = constMutableMultiSetOf(listOf(12, 34, 56))
    assertNotEquals(listSet1, listSet2)
    assertNotEquals(listSet2, listSet1)

    assertFalse(set1 == stringSet1)

    // immutable
    set1 = constMutableMultiSetOf(1, 2, 3)
    var immutableSet = multiSetOf(1, 2, 3)
    assertEquals(set1, immutableSet)

    immutableSet = multiSetOf(1)
    assertNotEquals(set1, immutableSet)
}
