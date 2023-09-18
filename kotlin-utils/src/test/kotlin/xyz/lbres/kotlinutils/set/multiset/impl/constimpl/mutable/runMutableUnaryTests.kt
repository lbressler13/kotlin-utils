package xyz.lbres.kotlinutils.set.multiset.impl.constimpl.mutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runMutableConstIsEmptyTests() {
    // empty
    var intSet: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    assertTrue(intSet.isEmpty())

    var stringSet: ConstMutableMultiSet<String> = constMutableMultiSetOf()
    assertTrue(stringSet.isEmpty())

    // not empty
    intSet = constMutableMultiSetOf(0)
    assertFalse(intSet.isEmpty())

    intSet = constMutableMultiSetOf(1000, -1000, 4, 2, 4)
    assertFalse(intSet.isEmpty())

    intSet = constMutableMultiSetOf(3, 3, 3)
    assertFalse(intSet.isEmpty())

    stringSet = constMutableMultiSetOf("123", "abc")
    assertFalse(stringSet.isEmpty())

    stringSet = constMutableMultiSetOf("hello world", "hello world")
    assertFalse(stringSet.isEmpty())

    // remove elements
    intSet = constMutableMultiSetOf(1)
    intSet.remove(1)
    assertTrue(intSet.isEmpty())

    intSet = constMutableMultiSetOf(1, 1)
    intSet.remove(1)
    assertFalse(intSet.isEmpty())
    intSet.remove(1)
    assertTrue(intSet.isEmpty())

    intSet = constMutableMultiSetOf(2, 3)
    intSet.remove(3)
    assertFalse(intSet.isEmpty())
    intSet.remove(2)
    assertTrue(intSet.isEmpty())

    intSet = constMutableMultiSetOf(2, 3)
    intSet.clear()
    assertTrue(intSet.isEmpty())
}

fun runMutableConstGetCountOfTests() {
    var set: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    var expected = 0

    var value = 0
    assertEquals(expected, set.getCountOf(value))

    value = 100
    assertEquals(expected, set.getCountOf(value))

    set = constMutableMultiSetOf(2)

    value = 2
    expected = 1
    assertEquals(expected, set.getCountOf(value))

    value = 1
    expected = 0
    assertEquals(expected, set.getCountOf(value))

    set = constMutableMultiSetOf(1, 1, 2, 1, -4, 5, 2)

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

    val list1 = listOf(1, 2, 3)
    val list2 = listOf(1, 2, 3)
    val listSet: ConstMutableMultiSet<IntList> = constMutableMultiSetOf(list1, list2)

    var listValue = listOf(1, 2, 3)
    expected = 2
    assertEquals(expected, listSet.getCountOf(listValue))

    listValue = listOf(1, 2)
    expected = 0
    assertEquals(expected, listSet.getCountOf(listValue))
}