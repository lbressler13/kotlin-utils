package xyz.lbres.kotlinutils.set.multiset.const.constmutable

import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
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
    assertEquals(0, set.getCountOf(0))
    assertEquals(0, set.getCountOf(100))

    set = constMutableMultiSetOf(2)
    assertEquals(1, set.getCountOf(2))
    assertEquals(0, set.getCountOf(1))

    set = constMutableMultiSetOf(1, 1, 2, 1, -4, 5, 2)
    assertEquals(3, set.getCountOf(1))
    assertEquals(2, set.getCountOf(2))
    assertEquals(1, set.getCountOf(-4))
    assertEquals(1, set.getCountOf(5))

    set.add(2)
    assertEquals(3, set.getCountOf(2))
    set.remove(5)
    assertEquals(0, set.getCountOf(5))

    val listSet = constMutableMultiSetOf(listOf(1, 2, 3), listOf(1, 2, 3))
    assertEquals(2, listSet.getCountOf(listOf(1, 2, 3)))
    assertEquals(0, listSet.getCountOf(listOf(1, 2)))
}
