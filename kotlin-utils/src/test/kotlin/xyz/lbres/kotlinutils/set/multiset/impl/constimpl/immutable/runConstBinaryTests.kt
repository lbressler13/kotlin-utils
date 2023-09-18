package xyz.lbres.kotlinutils.set.multiset.impl.constimpl.immutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.testimpl.TestMultiSet
import xyz.lbres.kotlinutils.set.multiset.testimpl.TestMutableMultiSet
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runConstEqualsTests() {
    // equals
    var set1: ConstMultiSet<Int> = constMultiSetOf()
    assertEquals(set1, set1)

    set1 = constMultiSetOf(3)
    assertEquals(set1, set1)

    set1 = constMultiSetOf(3, 3, 3)
    assertEquals(set1, set1)

    set1 = constMultiSetOf(2, 3, 4)
    assertEquals(set1, set1)

    set1 = constMultiSetOf(1, 2, 3)
    var set2 = multiSetOf(3, 1, 2)
    assertEquals(set1, set2)
    assertEquals(set2, set1)

    var otherSet = TestMultiSet(listOf(1, 2, 3))
    assertEquals<MultiSet<Int>>(set1, otherSet)

    // not equals
    set1 = constMultiSetOf()
    set2 = constMultiSetOf(0)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = constMultiSetOf(1, 1)
    set2 = constMultiSetOf(1)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = constMultiSetOf(1, 2)
    set2 = constMultiSetOf(2, 2)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = constMultiSetOf(-1, 3, 1, -3)
    otherSet = TestMultiSet(listOf(2, -2))
    assertNotEquals<MultiSet<Int>>(set1, otherSet)

    // other type
    val stringSet1 = constMultiSetOf("", "abc")
    assertEquals(stringSet1, stringSet1)

    val stringSet2 = constMultiSetOf("abc")
    assertNotEquals(stringSet1, stringSet2)
    assertNotEquals(stringSet2, stringSet1)

    val listSet1 = constMultiSetOf(listOf(12, 34, 56), listOf(77, 78, 0, 15), listOf(5))
    assertEquals(listSet1, listSet1)

    val listSet2 = constMultiSetOf(listOf(12, 34, 56))
    assertNotEquals(listSet1, listSet2)
    assertNotEquals(listSet2, listSet1)

    assertFalse(stringSet1 == set1)

    val otherListSet = TestMultiSet(listOf(listOf(12)))
    assertNotEquals<MultiSet<IntList>>(listSet2, otherListSet)

    // mutable
    set1 = constMultiSetOf(1, 2, 3)
    var mutableSet = mutableMultiSetOf(1, 2, 3)
    assertEquals<MultiSet<Int>>(set1, mutableSet)

    mutableSet = mutableMultiSetOf(1)
    assertNotEquals<MultiSet<Int>>(set1, mutableSet)

    val otherMutableSet = TestMutableMultiSet(listOf(1, 2, 3))
    assertEquals<MultiSet<Int>>(set1, otherMutableSet)
}

fun runConstMinusTests() {
    // empty
    var intSet1 = constMultiSetOf<Int>()
    var intSet2 = constMultiSetOf<Int>()
    assertEquals(constMultiSetOf(), intSet1 - intSet2)
    assertEquals(constMultiSetOf(), intSet2 - intSet1)

    intSet1 = constMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 - intSet2)
    assertEquals(constMultiSetOf(), intSet2 - intSet1)

    // equal
    intSet1 = constMultiSetOf(1, 2, 3, 4, 5)
    assertEquals(constMultiSetOf(), intSet1 - intSet1)

    var listSet1 = constMultiSetOf(listOf(1, 2, 3), listOf(456, 789))
    assertEquals(constMultiSetOf(), listSet1 - listSet1)

    var otherSet = TestMultiSet(listOf(1, 2, 3, 4, 5))
    assertEquals(constMultiSetOf(), intSet1 - otherSet)

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
    var expectedCompList: ConstMultiSet<List<Comparable<*>>> = constMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedCompList, compListSet1 - compListSet2)
    expectedCompList = constMultiSetOf(listOf(1, 2, 3), emptyList())
    assertEquals(expectedCompList, compListSet2 - compListSet1)

    val otherCompListSet: TestMultiSet<List<Comparable<*>>> = TestMultiSet(listOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList()))
    expectedCompList = constMultiSetOf(listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedCompList, compListSet1 - otherCompListSet)
}

fun runConstPlusTests() {
    // empty
    var intSet1 = constMultiSetOf<Int>()
    var intSet2 = constMultiSetOf<Int>()
    assertEquals(constMultiSetOf(), intSet1 + intSet2)
    assertEquals(constMultiSetOf(), intSet2 + intSet1)

    // non-empty
    intSet1 = constMultiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 + intSet2)
    assertEquals(intSet1, intSet2 + intSet1)

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
    var intSet1 = constMultiSetOf<Int>()

    var intSet2 = constMultiSetOf<Int>()
    assertEquals(constMultiSetOf(), intSet1 intersect intSet2)

    intSet2 = constMultiSetOf(1, 2, 3)
    assertEquals(constMultiSetOf(), intSet1 intersect intSet2)
    assertEquals(constMultiSetOf(), intSet2 intersect intSet1)

    // none shared
    intSet1 = constMultiSetOf(1, 2, 3)
    intSet2 = constMultiSetOf(4, 5, 6, 7, 8)
    assertEquals(constMultiSetOf(), intSet1 intersect intSet2)
    assertEquals(constMultiSetOf(), intSet2 intersect intSet1)

    var otherSet = TestMultiSet(listOf(4, 5, 6, 7, 8))
    assertEquals(constMultiSetOf(), intSet1 intersect otherSet)

    var listSet1 = constMultiSetOf(listOf(1, 2, 3), listOf(4, 5))
    var listSet2 = constMultiSetOf(listOf(1, 2), listOf(3, 4, 5))
    assertEquals(constMultiSetOf(), listSet1 intersect listSet2)
    assertEquals(constMultiSetOf(), listSet2 intersect listSet1)

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
    val expectedError: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e2)
    assertEquals(expectedError, errorSet1 intersect errorSet2)
    assertEquals(expectedError, errorSet2 intersect errorSet1)
}
