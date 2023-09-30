package xyz.lbres.kotlinutils.set.multiset.const.constmutable

import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.testutils.TestMutableMultiSet
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

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
