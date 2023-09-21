package xyz.lbres.kotlinutils.set.multiset.constimpl.immutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runConstIsEmptyTests() {
    // empty
    var intSet: ConstMultiSet<Int> = constMultiSetOf()
    assertTrue(intSet.isEmpty())

    var stringSet: ConstMultiSet<String> = constMultiSetOf()
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
    var set: ConstMultiSet<Int> = constMultiSetOf()
    var expected = 0

    var value = 0
    assertEquals(expected, set.getCountOf(value))

    value = 100
    assertEquals(expected, set.getCountOf(value))

    set = constMultiSetOf(2)

    value = 2
    expected = 1
    assertEquals(expected, set.getCountOf(value))

    value = 1
    expected = 0
    assertEquals(expected, set.getCountOf(value))

    set = constMultiSetOf(1, 1, 2, 1, -4, 5, 2)

    value = 1
    expected = 3
    assertEquals(expected, set.getCountOf(value))

    value = 2
    expected = 2
    assertEquals(expected, set.getCountOf(value))

    value = -4
    expected = 1
    assertEquals(expected, set.getCountOf(value))

    value = 5
    expected = 1
    assertEquals(expected, set.getCountOf(value))

    val list1 = mutableListOf(1, 2, 3)
    val list2 = mutableListOf(1, 2, 3)
    val listSet: ConstMultiSet<IntList> = constMultiSetOf(list1, list2)

    var listValue = listOf(1, 2, 3)
    expected = 2
    assertEquals(expected, listSet.getCountOf(listValue))

    listValue = listOf(1, 2)
    expected = 0
    assertEquals(expected, listSet.getCountOf(listValue))
}
