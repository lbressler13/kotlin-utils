package xyz.lbres.kotlinutils.set.multiset.immutable

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runImmutableIsEmptyTests() {
    // empty
    var intSet: MultiSet<Int> = multiSetOf()
    assertTrue(intSet.isEmpty())

    var stringSet: MultiSet<String> = multiSetOf()
    assertTrue(stringSet.isEmpty())

    // not empty
    intSet = multiSetOf(0)
    assertFalse(intSet.isEmpty())

    intSet = multiSetOf(1000, -1000, 4, 2, 4)
    assertFalse(intSet.isEmpty())

    intSet = multiSetOf(3, 3, 3)
    assertFalse(intSet.isEmpty())

    stringSet = multiSetOf("123", "abc")
    assertFalse(stringSet.isEmpty())

    stringSet = multiSetOf("abcdefg", "abcdefg")
    assertFalse(stringSet.isEmpty())
}

fun runImmutableGetCountOfTests() {
    var set: MultiSet<Int> = multiSetOf()
    var expected = 0

    var value = 0
    assertEquals(expected, set.getCountOf(value))

    value = 100
    assertEquals(expected, set.getCountOf(value))

    set = multiSetOf(2)

    value = 2
    expected = 1
    assertEquals(expected, set.getCountOf(value))

    value = 1
    expected = 0
    assertEquals(expected, set.getCountOf(value))

    set = multiSetOf(1, 1, 2, 1, -4, 5, 2)

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
