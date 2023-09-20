package xyz.lbres.kotlinutils.set.multiset.manager

import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runContainsTests() {
    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
    assertFalse(manager.contains(0))
    assertFalse(manager.contains(1000))
    assertFalse(manager.contains(-1000))

    manager = ConstMultiSetManager(emptyList(), true)
    assertFalse(manager.contains(0))
    assertFalse(manager.contains(1000))
    assertFalse(manager.contains(-1000))

    manager = ConstMultiSetManager(setOf(1, 2), false)
    assertFalse(manager.contains(0))
    assertTrue(manager.contains(1))
    assertTrue(manager.contains(2))

    manager = ConstMultiSetManager(listOf(1, 1, 1), false)
    assertTrue(manager.contains(1))
    assertFalse(manager.contains(2))

    val error = ArithmeticException()
    val errSet = ConstMultiSetManager(listOf(ArithmeticException(), error, NumberFormatException()), true)
    assertTrue(errSet.contains(error))

    val listSet = ConstMultiSetManager(listOf(emptyList(), listOf(5, 6), listOf(9, 8, 3)), false)
    assertTrue(listSet.contains(emptyList()))
    assertTrue(listSet.contains(listOf(9, 8, 3)))
    assertFalse(listSet.contains(listOf(6, 6)))

    // adding elements
    manager = ConstMultiSetManager(emptySet(), true)
    manager.add(1)
    assertTrue(manager.contains(1))
    assertFalse(manager.contains(2))
    manager.remove(1)
    manager.add(2)
    assertTrue(manager.contains(2))
}

fun runContainsAllTests() {
    // equal
    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), false)
    assertTrue(manager.containsAll(emptySet()))

    manager = ConstMultiSetManager(emptyList(), true)
    assertTrue(manager.containsAll(emptySet()))

    manager = ConstMultiSetManager(listOf(-445), false)
    assertTrue(manager.containsAll(listOf(-445)))

    manager = ConstMultiSetManager(listOf(1, 1), true)
    assertTrue(manager.containsAll(listOf(1, 1)))

    val values = listOf(2, 3, 2, 4, 3, 4, 4)
    val reorderedValues = listOf(4, 4, 2, 3, 3, 2, 4)
    manager = ConstMultiSetManager(values, false)
    assertTrue(manager.containsAll(reorderedValues))

    // subset
    manager = ConstMultiSetManager(setOf(1), true)
    assertTrue(manager.containsAll(emptyList()))

    manager = ConstMultiSetManager(listOf(1, 2, 3, 4), true)
    assertTrue(manager.containsAll(listOf(3, 1)))

    manager = ConstMultiSetManager(listOf(1, 1, 1), false)
    assertTrue(manager.containsAll(listOf(1, 1)))

    // overlap
    manager = ConstMultiSetManager(listOf(1, 1), true)
    assertFalse(manager.containsAll(listOf(1, 1, 1)))

    manager = ConstMultiSetManager(listOf(1, 2, 3), true)
    assertFalse(manager.containsAll(listOf(1, 3, 4)))

    manager = ConstMultiSetManager(listOf(-100, -100, 300, 400), false)
    assertFalse(manager.containsAll(listOf(-100, 300, 400, 400)))

    // no overlap
    manager = ConstMultiSetManager(emptyList(), true)
    assertFalse(manager.containsAll(listOf(0)))

    manager = ConstMultiSetManager(listOf(2), false)
    assertFalse(manager.containsAll(listOf(0)))

    manager = ConstMultiSetManager(listOf(1, 1, 1, 1), false)
    assertFalse(manager.containsAll(listOf(2, 2, 2, 2)))

    manager = ConstMultiSetManager(listOf(4, -4, 5), true)
    assertFalse(manager.containsAll(listOf(22, 23, 22)))

    // changing elements
    manager = ConstMultiSetManager(listOf(1, 2, 3), true)
    assertFalse(manager.containsAll(listOf(2, 4)))
    manager.add(4)
    assertTrue(manager.containsAll(listOf(2, 4)))
    manager.remove(2)
    assertFalse(manager.containsAll(listOf(2, 4)))
}
