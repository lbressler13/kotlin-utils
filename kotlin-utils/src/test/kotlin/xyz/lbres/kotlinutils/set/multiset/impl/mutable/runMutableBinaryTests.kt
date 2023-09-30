package xyz.lbres.kotlinutils.set.multiset.impl.mutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMinusTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetPlusTests
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

fun runMutableMinusTests() {
    runMultiSetMinusTests(
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { ConstMultiSetImpl(it) }
    )

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet1: MutableMultiSet<IntList> = mutableMultiSetOf(mutableList1, mutableList2)
    val listSet2: MutableMultiSet<List<Int>> = mutableMultiSetOf(listOf(1, 2, 3), listOf(0, 5, 7))

    var expectedList: MutableMultiSet<IntList> = mutableMultiSetOf()
    assertEquals(expectedList, listSet1 - listSet2)
    assertEquals(expectedList, listSet2 - listSet1)

    mutableList2.add(3)
    expectedList = mutableMultiSetOf(listOf(0, 5, 7, 3))
    assertEquals(expectedList, listSet1 - listSet2)
    expectedList = mutableMultiSetOf(listOf(0, 5, 7))
    assertEquals(expectedList, listSet2 - listSet1)
}

fun runMutablePlusTests() {
    runMultiSetPlusTests(
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { ConstMultiSetImpl(it) }
    )

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet1: MutableMultiSet<IntList> = mutableMultiSetOf(mutableList1, mutableList2)
    val listSet2: MutableMultiSet<IntList> = mutableMultiSetOf(listOf(1), mutableList1)

    var expectedList: MultiSet<IntList> = multiSetOf(listOf(1), listOf(1, 2, 3), listOf(1, 2, 3), listOf(0, 5, 7))
    assertEquals(expectedList, listSet1 + listSet2)
    assertEquals(expectedList, listSet2 + listSet1)

    mutableList1.add(4)
    expectedList = multiSetOf(listOf(1), listOf(1, 2, 3, 4), listOf(1, 2, 3, 4), listOf(0, 5, 7))
    assertEquals(expectedList, listSet2 + listSet1)
    assertEquals(expectedList, listSet1 + listSet2)
}

fun runMutableIntersectTests() {
    runMultiSetPlusTests(
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { MutableMultiSetImpl(it) },
        { ConstMultiSetImpl(it) }
    )

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet1: MutableMultiSet<IntList> = mutableMultiSetOf(mutableList1, mutableList2)
    var listSet2: MutableMultiSet<IntList> = mutableMultiSetOf(listOf(1), mutableList1)

    var expectedList: MultiSet<IntList> = multiSetOf(listOf(1, 2, 3))
    assertEquals(expectedList, listSet1 intersect listSet2)
    assertEquals(expectedList, listSet2 intersect listSet1)

    listSet2 = mutableMultiSetOf(listOf(1), listOf(1, 2, 3))
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

    var otherSet = ConstMutableMultiSet(listOf(1, 2, 3))
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
    otherSet = ConstMutableMultiSet(listOf(2, -2))
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
    setSet2 = ConstMutableMultiSet(listOf(setOf(1, 3, 2)))
    assertEquals(setSet2, setSet1)
}
