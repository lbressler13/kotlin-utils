package xyz.lbres.kotlinutils.set.multiset

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal fun runMutableIsEmptyTests() {
    // empty
    var intSet: MutableMultiSet<Int> = mutableMultiSetOf()
    assertTrue(intSet.isEmpty())

    var stringSet: MutableMultiSet<String> = mutableMultiSetOf()
    assertTrue(stringSet.isEmpty())

    // not empty
    intSet = mutableMultiSetOf(0)
    assertFalse(intSet.isEmpty())

    intSet = mutableMultiSetOf(1000, -1000, 4, 2, 4)
    assertFalse(intSet.isEmpty())

    intSet = mutableMultiSetOf(3, 3, 3)
    assertFalse(intSet.isEmpty())

    stringSet = mutableMultiSetOf("123", "abc")
    assertFalse(stringSet.isEmpty())

    stringSet = mutableMultiSetOf("abcdefg", "abcdefg")
    assertFalse(stringSet.isEmpty())

    // remove elements
    intSet = mutableMultiSetOf(1)
    intSet.remove(1)
    assertTrue(intSet.isEmpty())

    intSet = mutableMultiSetOf(1, 1)
    intSet.remove(1)
    assertFalse(intSet.isEmpty())
    intSet.remove(1)
    assertTrue(intSet.isEmpty())

    intSet = mutableMultiSetOf(2, 3)
    intSet.remove(3)
    assertFalse(intSet.isEmpty())
    intSet.remove(2)
    assertTrue(intSet.isEmpty())

    intSet = mutableMultiSetOf(2, 3)
    intSet.clear()
    assertTrue(intSet.isEmpty())
}

internal fun runMutableGetCountOfTests() {
    var set: MutableMultiSet<Int> = mutableMultiSetOf()
    var expected = 0

    var value = 0
    assertEquals(expected, set.getCountOf(value))

    value = 100
    assertEquals(expected, set.getCountOf(value))

    set = mutableMultiSetOf(2)

    value = 2
    expected = 1
    assertEquals(expected, set.getCountOf(value))

    value = 1
    expected = 0
    assertEquals(expected, set.getCountOf(value))

    set = mutableMultiSetOf(1, 1, 2, 1, -4, 5, 2)

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
}
