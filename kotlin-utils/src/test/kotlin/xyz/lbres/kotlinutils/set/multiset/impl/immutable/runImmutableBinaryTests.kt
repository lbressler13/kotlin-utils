package xyz.lbres.kotlinutils.set.multiset.impl.immutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.TestMultiSet
import xyz.lbres.kotlinutils.set.multiset.testutils.TestMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMinusTests
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = IllegalArgumentException()

fun runImmutableEqualsTests() {
    // equals
    var set1: MultiSet<Int> = multiSetOf()
    assertEquals(set1, set1)

    set1 = multiSetOf(3)
    assertEquals(set1, set1)

    set1 = multiSetOf(3, 3, 3)
    assertEquals(set1, set1)

    set1 = multiSetOf(2, 3, 4)
    assertEquals(set1, set1)

    set1 = multiSetOf(1, 2, 3)
    var set2 = multiSetOf(3, 1, 2)
    assertEquals(set1, set2)
    assertEquals(set2, set1)

    var otherSet = TestMultiSet(listOf(1, 2, 3))
    assertEquals(set1, otherSet)

    // not equals
    set1 = multiSetOf()
    set2 = multiSetOf(0)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = multiSetOf(1, 1)
    set2 = multiSetOf(1)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = multiSetOf(1, 2)
    set2 = multiSetOf(2, 2)
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = multiSetOf(-1, 3, 1, -3)
    otherSet = TestMultiSet(listOf(2, -2))
    assertNotEquals(set1, otherSet)

    // other type
    val stringSet1 = multiSetOf("", "abc")
    assertEquals(stringSet1, stringSet1)

    val stringSet2 = multiSetOf("abc")
    assertNotEquals(stringSet1, stringSet2)
    assertNotEquals(stringSet2, stringSet1)

    var listSet1 = multiSetOf(listOf(12, 34, 56), listOf(77, 78, 0, 15), listOf(5))
    assertEquals(listSet1, listSet1)

    var listSet2 = multiSetOf(listOf(12, 34, 56))
    assertNotEquals(listSet1, listSet2)
    assertNotEquals(listSet2, listSet1)

    assertFalse(stringSet1 == set1)

    val otherListSet = TestMultiSet(listOf(listOf(12)))
    assertNotEquals(listSet2, otherListSet)

    // mutable
    set1 = multiSetOf(1, 2, 3)
    var mutableSet = mutableMultiSetOf(1, 2, 3)
    assertEquals(set1, mutableSet)

    mutableSet = mutableMultiSetOf(1)
    assertNotEquals(set1, mutableSet)

    val otherMutableSet = TestMutableMultiSet(listOf(1, 2, 3))
    assertEquals(set1, otherMutableSet)

    // changing values
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val mutableList3 = mutableListOf(0, 5, 7)
    listSet1 = multiSetOf(mutableList1, mutableList2)
    listSet2 = multiSetOf(emptyList(), mutableList3)
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
    listSet1 = multiSetOf(mutableList2, mutableList2, mutableList1, mutableList2)
    listSet2 = multiSetOf(listOf(1, 2), listOf(1, 2), listOf(1, 2), emptyList())
    assertEquals(listSet1, listSet2)

    mutableList2.remove(1)
    listSet2 = multiSetOf(listOf(2), listOf(2), listOf(2), emptyList())
    assertEquals(listSet2, listSet1)

    val mutableSet1 = mutableSetOf(1, 2, 3)
    val setSet1: MultiSet<Set<Int>> = multiSetOf(mutableSet1)
    var setSet2 = multiSetOf(setOf(1, 2, 3))
    assertEquals(setSet1, setSet2)
    assertEquals(setSet2, setSet1)

    mutableSet1.remove(2)
    assertNotEquals(setSet1, setSet2)
    assertNotEquals(setSet2, setSet1)

    setSet2 = multiSetOf(setOf(1, 3))
    assertEquals(setSet1, setSet2)
    assertEquals(setSet2, setSet1)

    mutableSet1.add(2)
    setSet2 = TestMultiSet(listOf(setOf(1, 3, 2)))
    assertEquals(setSet2, setSet1)
}

fun runImmutableMinusTests() {
    runMultiSetMinusTests(
        { MultiSetImpl(it) },
        { MultiSetImpl(it) },
        { MultiSetImpl(it) },
        { MultiSetImpl(it) },
        { MultiSetImpl(it) },
        { ConstMutableMultiSet(it) }
    )

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet1: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)
    val listSet2: MultiSet<List<Int>> = multiSetOf(listOf(1, 2, 3), listOf(0, 5, 7))

    var expectedList: MultiSet<IntList> = multiSetOf()
    assertEquals(expectedList, listSet1 - listSet2)
    assertEquals(expectedList, listSet2 - listSet1)

    mutableList2.add(3)
    expectedList = multiSetOf(listOf(0, 5, 7, 3))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = multiSetOf(listOf(0, 5, 7))
    assertEquals(expectedList, listSet2 - listSet1)
}

fun runImmutablePlusTests() {
    // empty
    var intSet1 = emptyMultiSet<Int>()
    var intSet2 = emptyMultiSet<Int>()
    assertEquals(emptyMultiSet(), intSet1 + intSet2)
    assertEquals(emptyMultiSet(), intSet2 + intSet1)

    // non-empty
    intSet1 = multiSetOf(1, 2, 3, 3)
    assertEquals(intSet1, intSet1 + intSet2)
    assertEquals(intSet1, intSet2 + intSet1)

    intSet1 = multiSetOf(1)
    intSet2 = multiSetOf(1)
    var expectedInt = multiSetOf(1, 1)
    assertEquals(expectedInt, intSet1 + intSet2)
    assertEquals(expectedInt, intSet2 + intSet1)

    intSet1 = multiSetOf(1, 2, 2, 3, 3, 3)
    intSet2 = multiSetOf(1, 2, 0)
    expectedInt = multiSetOf(0, 1, 1, 2, 2, 2, 3, 3, 3)
    assertEquals(expectedInt, intSet1 + intSet2)
    assertEquals(expectedInt, intSet2 + intSet1)

    val otherSet = TestMultiSet(listOf(1, 2, 2, 3, 3, 3))
    assertEquals(expectedInt, intSet2 + otherSet)

    val stringSet1 = multiSetOf("", "hello", "world", "goodbye")
    val stringSet2 = multiSetOf("hi", "no", "bye")
    val expectedString = multiSetOf("", "bye", "goodbye", "hello", "hi", "no", "world")
    assertEquals(expectedString, stringSet1 + stringSet2)
    assertEquals(expectedString, stringSet2 + stringSet1)

    var listSet1 = multiSetOf(listOf(-3), listOf(2, 3, 4), listOf(1, 2, 3))
    var listSet2 = multiSetOf(emptyList(), listOf(1, 2, 3))
    var expectedList = multiSetOf(emptyList(), listOf(-3), listOf(1, 2, 3), listOf(1, 2, 3), listOf(2, 3, 4))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    val errorSet1: MultiSet<Exception> = multiSetOf(e1, e2, e1, e2)
    val errorSet2: MultiSet<Exception> = multiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: MultiSet<Exception> = multiSetOf(e1, e1, e1, e1, e1, e2, e2, e2, e3, e3)
    assertEquals(expectedError, errorSet1 + errorSet2)
    assertEquals(expectedError, errorSet2 + errorSet1)

    val compListSet1: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    val compListSet2: MultiSet<List<Comparable<*>>> = multiSetOf(listOf(1, 2, 3), listOf(1, 2, 3), emptyList())
    val expectedCompList: MultiSet<List<Comparable<*>>> = multiSetOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3), listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def"))
    assertEquals(expectedCompList, compListSet1 + compListSet2)
    assertEquals(expectedCompList, compListSet2 + compListSet1)

    // changing values
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet1 = multiSetOf(mutableList1, mutableList2)
    listSet2 = multiSetOf(listOf(1), mutableList1)

    expectedList = multiSetOf(listOf(1), listOf(1, 2, 3), listOf(1, 2, 3), listOf(0, 5, 7))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    mutableList1.add(4)
    expectedList = multiSetOf(listOf(1), listOf(1, 2, 3, 4), listOf(1, 2, 3, 4), listOf(0, 5, 7))
    assertEquals(expectedList, listSet2 + listSet1)
    assertEquals(expectedList, listSet1 + listSet2)
}

fun runImmutableIntersectTests() {
    // empty
    var intSet1 = emptyMultiSet<Int>()

    var intSet2 = emptyMultiSet<Int>()
    assertEquals(emptyMultiSet(), intSet1 intersect intSet2)

    intSet2 = multiSetOf(1, 2, 3)
    assertEquals(emptyMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyMultiSet(), intSet2 intersect intSet1)

    // none shared
    intSet1 = multiSetOf(1, 2, 3)
    intSet2 = multiSetOf(4, 5, 6, 7, 8)
    assertEquals(emptyMultiSet(), intSet1 intersect intSet2)
    assertEquals(emptyMultiSet(), intSet2 intersect intSet1)

    var otherSet = TestMultiSet(listOf(4, 5, 6, 7, 8))
    assertEquals(emptyMultiSet(), intSet1 intersect otherSet)

    var listSet1 = multiSetOf(listOf(1, 2, 3), listOf(4, 5))
    var listSet2 = multiSetOf(listOf(1, 2), listOf(3, 4, 5))
    assertEquals(emptyMultiSet(), listSet1 intersect listSet2)
    assertEquals(emptyMultiSet(), listSet2 intersect listSet1)

    // all shared
    intSet1 = multiSetOf(1, 2, 3)
    intSet2 = multiSetOf(1, 2, 3)
    var expectedInt = multiSetOf(1, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    intSet1 = multiSetOf(1, 1, 2, 2, 3, 3)
    intSet2 = multiSetOf(1, 2, 2, 2, 3)
    expectedInt = multiSetOf(1, 2, 2, 3)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    // some shared
    intSet1 = multiSetOf(1, 2, 2, 4, 5, 6, 7, -1, 10)
    intSet2 = multiSetOf(-1, 14, 3, 9, 9, 6)
    expectedInt = multiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect intSet2)
    assertEquals(expectedInt, intSet2 intersect intSet1)

    otherSet = TestMultiSet(listOf(-1, 14, 3, 9, 9, 6))
    expectedInt = multiSetOf(-1, 6)
    assertEquals(expectedInt, intSet1 intersect otherSet)

    listSet1 = multiSetOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(1, 2, 3))
    listSet2 = multiSetOf(emptyList(), listOf(1, 2, 3))
    var expectedList = multiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    val errorSet1: MultiSet<Exception> = multiSetOf(e1, e2, e1, e2)
    val errorSet2: MultiSet<Exception> = multiSetOf(e1, e3, e3, e2, e1, e1)
    val expectedError: MultiSet<Exception> = multiSetOf(e1, e1, e2)
    assertEquals(expectedError, errorSet1 intersect errorSet2)
    assertEquals(expectedError, errorSet2 intersect errorSet1)

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet1 = multiSetOf(mutableList1, mutableList2)
    listSet2 = multiSetOf(listOf(1), mutableList1)

    expectedList = multiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    listSet2 = multiSetOf(listOf(1), listOf(1, 2, 3))
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
