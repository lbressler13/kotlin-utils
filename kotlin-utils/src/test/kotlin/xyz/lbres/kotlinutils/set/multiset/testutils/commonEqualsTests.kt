package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@Suppress(Suppressions.UNCHECKED_CAST)
fun runEqualsTests(createSet: (Collection<*>) -> MultiSet<*>, createOtherSet: (Collection<*>) -> MultiSet<*>) {
    val createIntSet = getCreateSet<Int>(createSet)
    val createStringSet = getCreateSet<String>(createSet)
    val createIntListSet = getCreateSet<IntList>(createSet)
    val createOtherIntSet = getCreateSet<Int>(createOtherSet)

    // equal
    var set1: MultiSet<Int> = createIntSet(emptyList())
    assertEquals(set1, set1)

    set1 = createIntSet(listOf(3))
    assertEquals(set1, set1)

    set1 = createIntSet(listOf(3, 3, 3))
    assertEquals(set1, set1)

    set1 = createIntSet(listOf(2, 3, 4))
    assertEquals(set1, set1)

    set1 = createIntSet(listOf(1, 2, 3))
    var set2 = createIntSet(listOf(3, 1, 2))
    assertEquals(set1, set2)
    assertEquals(set2, set1)

    var otherSet = createOtherIntSet(listOf(1, 2, 3))
    assertEquals(set1, otherSet)

    // not equal
    set1 = createIntSet(emptyList())
    set2 = createIntSet(listOf(0))
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = createIntSet(listOf(1, 1))
    set2 = createIntSet(listOf(1))
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = createIntSet(listOf(1, 2))
    set2 = createIntSet(listOf(2, 2))
    assertNotEquals(set1, set2)
    assertNotEquals(set2, set1)

    set1 = createIntSet(listOf(-1, 3, 1, -3))
    otherSet = createOtherIntSet(listOf(2, -2))
    assertNotEquals(set1, otherSet)

    // other types
    val stringSet1 = createStringSet(listOf("", "abc"))
    assertEquals(stringSet1, stringSet1)

    val stringSet2 = createStringSet(listOf("abc"))
    assertNotEquals(stringSet1, stringSet2)
    assertNotEquals(stringSet2, stringSet1)

    val listSet1 = createIntListSet(listOf(listOf(12, 34, 56), listOf(77, 78, 0, 15), listOf(5)))
    assertEquals(listSet1, listSet1)

    val listSet2 = createIntListSet(listOf(listOf(12, 34, 56)))
    assertNotEquals(listSet1, listSet2)
    assertNotEquals(listSet2, listSet1)

    val anySet1 = stringSet1 as MultiSet<Any>
    val anySet2 = set1 as MultiSet<Any>
    assertNotEquals(anySet1, anySet2)
}

fun runMutableEqualsTests(createMutableSet: (Collection<*>) -> MutableMultiSet<*>, createOtherSet: (Collection<*>) -> MultiSet<*>) {
    runEqualsTests(createMutableSet, createOtherSet)

    val createMutableIntSet = getCreateMutableSet<Int>(createMutableSet)
    val intSet1 = createMutableIntSet(listOf(1, 2, 3, 4, 5, 6))
    val intSet2 = createMutableIntSet(listOf(1, 2, 3, 4, 5, 6))
    assertEquals(intSet1, intSet2)
    assertEquals(intSet2, intSet1)

    intSet1.add(7)
    assertNotEquals(intSet1, intSet2)
    assertNotEquals(intSet2, intSet1)

    intSet1.remove(7)
    assertEquals(intSet1, intSet2)
    assertEquals(intSet2, intSet1)

    intSet2.remove(4)
    assertNotEquals(intSet1, intSet2)
    assertNotEquals(intSet2, intSet1)
}

fun runMutableElementsEqualsTests(createSet: (Collection<*>) -> MultiSet<*>, createOtherSet: (Collection<*>) -> MultiSet<*>) {
    val createIntListSet = getCreateSet<IntList>(createSet)
    val createIntSetSet = getCreateSet<Set<Int>>(createSet)
    val createOtherIntSetSet = getCreateSet<Set<Int>>(createOtherSet)

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val mutableList3 = mutableListOf(0, 5, 7)
    var listSet1 = createIntListSet(listOf(mutableList1, mutableList2))
    var listSet2 = createIntListSet(listOf(emptyList(), mutableList3))
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
    listSet1 = createIntListSet(listOf(mutableList2, mutableList2, mutableList1, mutableList2))
    listSet2 = createIntListSet(listOf(listOf(1, 2), listOf(1, 2), listOf(1, 2), emptyList()))
    assertEquals(listSet1, listSet2)

    mutableList2.remove(1)
    listSet2 = createIntListSet(listOf(listOf(2), listOf(2), listOf(2), emptyList()))
    assertEquals(listSet2, listSet1)

    val mutableSet1 = mutableSetOf(1, 2, 3)
    val setSet1: MultiSet<Set<Int>> = createIntSetSet(listOf(mutableSet1))
    var setSet2 = createIntSetSet(listOf(setOf(1, 2, 3)))
    assertEquals(setSet1, setSet2)
    assertEquals(setSet2, setSet1)

    mutableSet1.remove(2)
    assertNotEquals(setSet1, setSet2)
    assertNotEquals(setSet2, setSet1)

    setSet2 = createIntSetSet(listOf(setOf(1, 3)))
    assertEquals(setSet1, setSet2)
    assertEquals(setSet2, setSet1)

    mutableSet1.add(2)
    setSet2 = createOtherIntSetSet(listOf(setOf(1, 3, 2)))
    assertEquals(setSet2, setSet1)
}
