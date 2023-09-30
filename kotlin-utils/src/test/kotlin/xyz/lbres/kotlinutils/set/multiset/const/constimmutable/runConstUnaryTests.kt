package xyz.lbres.kotlinutils.set.multiset.const.constimmutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runConstIsEmptyTests() {
    // empty
    var intSet: ConstMultiSet<Int> = emptyConstMultiSet()
    assertTrue(intSet.isEmpty())

    var stringSet: ConstMultiSet<String> = emptyConstMultiSet()
    assertTrue(stringSet.isEmpty())

    // not empty
    intSet = constMultiSetOf(0)
    assertFalse(intSet.isEmpty())

    intSet = constMultiSetOf(1000, -1000, 4, 2, 4)
    assertFalse(intSet.isEmpty())

    intSet = constMultiSetOf(3, 3, 3)
    assertFalse(intSet.isEmpty())

    stringSet = constMultiSetOf("123", "abc")
    assertFalse(stringSet.isEmpty())

    stringSet = constMultiSetOf("abcdefg", "abcdefg")
    assertFalse(stringSet.isEmpty())
}

fun runConstGetCountOfTests() {
    var set: ConstMultiSet<Int> = emptyConstMultiSet()
    assertEquals(0, set.getCountOf(0))
    assertEquals(0, set.getCountOf(100))

    set = constMultiSetOf(2)
    assertEquals(1, set.getCountOf(2))
    assertEquals(0, set.getCountOf(1))

    set = constMultiSetOf(1, 1, 2, 1, -4, 5, 2)
    assertEquals(3, set.getCountOf(1))
    assertEquals(2, set.getCountOf(2))
    assertEquals(1, set.getCountOf(-4))
    assertEquals(1, set.getCountOf(5))

    val listSet: ConstMultiSet<IntList> = constMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3))
    assertEquals(2, listSet.getCountOf(listOf(1, 2, 3)))
    assertEquals(0, listSet.getCountOf(listOf(1, 2)))
}
