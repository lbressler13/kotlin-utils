package xyz.lbres.kotlinutils.set.multiset.constimmutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.testutils.TestMultiSet
import xyz.lbres.kotlinutils.set.multiset.testutils.TestMutableMultiSet
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

fun runConstEqualsTests() {
    // equals
    var set1: ConstMultiSet<Int> = emptyConstMultiSet()
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
    set1 = emptyConstMultiSet()
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
    var mutableSet: MutableMultiSet<Int> = mutableMultiSetOf(1, 2, 3)
    assertEquals<MultiSet<Int>>(set1, mutableSet)

    mutableSet = mutableMultiSetOf(1)
    assertNotEquals<MultiSet<Int>>(set1, mutableSet)

    mutableSet = TestMutableMultiSet(listOf(1, 2, 3))
    assertEquals<MultiSet<Int>>(set1, mutableSet)
}
