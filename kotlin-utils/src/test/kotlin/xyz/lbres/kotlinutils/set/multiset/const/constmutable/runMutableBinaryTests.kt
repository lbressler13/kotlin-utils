package xyz.lbres.kotlinutils.set.multiset.const.constmutable

import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import xyz.lbres.kotlinutils.set.multiset.testutils.TestMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMinusTests
import kotlin.test.assertEquals
import kotlin.test.assertIsNot

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runMutableConstMinusTests() {
    runMultiSetMinusTests(
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { ConstMutableMultiSet(it) },
        { MultiSetImpl(it) }
    )
}

fun runMutableConstPlusTests() {
    // empty
    var intSet1: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    var intSet2: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    assertEquals(emptyMultiSet(), intSet1 + intSet2)
    assertEquals(emptyMultiSet(), intSet2 + intSet1)

    assertIsNot<ConstMultiSet<*>>(intSet1 - intSet2)

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

    val stringSet1 = constMutableMultiSetOf("", "hello", "world", "goodbye")
    val stringSet2 = constMutableMultiSetOf("hi", "no", "bye")
    val stringExpected = constMutableMultiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(stringExpected, stringSet1 + stringSet2)
    assertEquals(stringExpected, stringSet2 + stringSet1)

    val listSet1 = constMutableMultiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    val listSet2 = constMutableMultiSetOf(emptyList(), listOf(1, 2, 3))
    val expectedList = constMutableMultiSetOf(emptyList(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    val errorSet1: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e2, e1, e2)
    val errorSet2: ConstMutableMultiSet<Exception> = constMutableMultiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: MutableMultiSet<Exception> = constMutableMultiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 + errorSet2)
    assertEquals(expectedError, errorSet2 + errorSet1)

    val compListSet1: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListSet2: ConstMutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    val compListExpected: MutableMultiSet<List<Comparable<*>>> = constMutableMultiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(compListExpected, compListSet1 + compListSet2)
    assertEquals(compListExpected, compListSet2 + compListSet1)

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
    assertEquals(emptyConstMultiSet(), intSet1 intersect intSet2)

    assertIsNot<ConstMultiSet<*>>(intSet1 - intSet2)

    intSet2 = constMutableMultiSetOf(1, 2, 3)
    assertEquals(emptyConstMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 intersect intSet1)

    // none shared
    intSet1 = constMutableMultiSetOf(1, 2, 3)
    intSet2 = constMutableMultiSetOf(4, 5, 6, 7, 8)
    assertEquals(emptyConstMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyConstMultiSet(), intSet2 intersect intSet1)

    var otherSet = TestMutableMultiSet(listOf(4, 5, 6, 7, 8))
    assertEquals(emptyConstMultiSet(), intSet1 intersect otherSet)

    var listSet1 = constMutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5))
    var listSet2 = constMutableMultiSetOf(listOf(1, 2), listOf(3, 4, 5))
    assertEquals(emptyConstMultiSet(), listSet1 intersect listSet2)
    assertEquals(emptyConstMultiSet(), listSet2 intersect listSet1)

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
    val expectedError: MutableMultiSet<Exception> = constMutableMultiSetOf(e1, e1, e2)
    assertEquals(expectedError, errorSet1 intersect errorSet2)
    assertEquals(expectedError, errorSet2 intersect errorSet1)

    // with immutable
    intSet1 = constMutableMultiSetOf(1, 2, 3, 4)
    val immutable = multiSetOf(1, 4, 4, 5)
    expectedInt = constMutableMultiSetOf(1, 4)
    assertEquals(expectedInt, intSet1 intersect immutable)
}
