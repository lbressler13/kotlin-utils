package xyz.lbres.kotlinutils.set.multiset.manager

import xyz.lbres.kotlinutils.list.IntList
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runIsEmptyTests() {
    // empty
    var intManager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
    assertTrue(intManager.isEmpty())

    intManager = ConstMultiSetManager(emptyList(), true)
    assertTrue(intManager.isEmpty())

    var stringManager: ConstMultiSetManager<String> = ConstMultiSetManager(emptyList(), false)
    assertTrue(stringManager.isEmpty())

    // not empty
    intManager = ConstMultiSetManager(listOf(0), false)
    assertFalse(intManager.isEmpty())

    intManager = ConstMultiSetManager(listOf(1000, -1000, 4, 2, 4), false)
    assertFalse(intManager.isEmpty())

    intManager = ConstMultiSetManager(listOf(3, 3, 3), true)
    assertFalse(intManager.isEmpty())

    stringManager = ConstMultiSetManager(listOf("123", "abc"), true)
    assertFalse(stringManager.isEmpty())

    stringManager = ConstMultiSetManager(listOf("hello world", "hello world"), false)
    assertFalse(stringManager.isEmpty())

    // remove elements
    intManager = ConstMultiSetManager(listOf(1), true)
    intManager.remove(1)
    assertTrue(intManager.isEmpty())

    intManager = ConstMultiSetManager(listOf(1, 1), true)
    intManager.remove(1)
    assertFalse(intManager.isEmpty())
    intManager.remove(1)
    assertTrue(intManager.isEmpty())

    intManager = ConstMultiSetManager(listOf(2, 3), true)
    intManager.remove(3)
    assertFalse(intManager.isEmpty())
    intManager.remove(2)
    assertTrue(intManager.isEmpty())

    intManager = ConstMultiSetManager(listOf(2, 3), true)
    intManager.clear()
    assertTrue(intManager.isEmpty())
}

fun runGetCountOfTests() {
    var intManager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
    var expected = 0

    var value = 0
    assertEquals(expected, intManager.getCountOf(value))

    value = 100
    assertEquals(expected, intManager.getCountOf(value))

    intManager = ConstMultiSetManager(listOf(2), true)

    value = 2
    expected = 1
    assertEquals(expected, intManager.getCountOf(value))

    value = 1
    expected = 0
    assertEquals(expected, intManager.getCountOf(value))

    intManager = ConstMultiSetManager(listOf(1, 1, 2, 1, -4, 5, 2), true)

    value = 1
    expected = 3
    assertEquals(expected, intManager.getCountOf(value))

    value = 2
    expected = 2
    assertEquals(expected, intManager.getCountOf(value))

    value = -4
    expected = 1
    assertEquals(expected, intManager.getCountOf(value))

    value = 5
    expected = 1
    assertEquals(expected, intManager.getCountOf(value))

    val list1 = listOf(1, 2, 3)
    val list2 = listOf(1, 2, 3)
    val listManager: ConstMultiSetManager<IntList> = ConstMultiSetManager(listOf(list1, list2), false)

    var listValue = listOf(1, 2, 3)
    expected = 2
    assertEquals(expected, listManager.getCountOf(listValue))

    listValue = listOf(1, 2)
    expected = 0
    assertEquals(expected, listManager.getCountOf(listValue))
}
