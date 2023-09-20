package xyz.lbres.kotlinutils.set.multiset.manager

import xyz.lbres.kotlinutils.list.listOfValue
import xyz.lbres.kotlinutils.set.mutableset.ext.popRandom
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

fun runAddTests() {
    assertImmutableFails("add") { it.add(2) }

    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), true)
    runSingleMutateTest(manager, listOf(1), true) { it.add(1) }
    runSingleMutateTest(manager, listOf(1, 1), true) { it.add(1) }
    runSingleMutateTest(manager, listOf(1, 1, 2), true) { it.add(2) }

    manager = ConstMultiSetManager(listOf(-1, 3, -5, 4, 1000, 17), true)
    val expected = listOf(-1, 3, -5, 4, 1000, 17, 15)
    runSingleMutateTest(manager, expected, true) { it.add(15) }

    val listManager = ConstMultiSetManager(listOf(listOf("hello", "world"), listOf("goodbye")), true)
    val listExpected = listOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"))
    runSingleMutateTest(listManager, listExpected, true) { it.add(listOf("farewell", "goodbye")) }
}

fun runAddAllTests() {
    assertImmutableFails("addAll") { it.addAll(listOf(1, 2)) }

    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), true)
    runSingleMutateTest(manager, emptyList(), true) { manager.addAll(emptyList()) }

    manager = ConstMultiSetManager(emptyList(), true)
    runSingleMutateTest(manager, listOf(4), true) { manager.addAll(setOf(4)) }
    var expected = listOf(1, 2, 3, 4)
    runSingleMutateTest(manager, expected, true) { manager.addAll(listOf(1, 2, 3)) }

    manager = ConstMultiSetManager(listOf(1, 2, 3), true)
    expected = listOf(1, 2, 2, 2, 3, 3, 3)
    runSingleMutateTest(manager, expected, true) { manager.addAll(listOf(2, 2, 3, 3)) }
}

fun runRemoveTests() {
    assertImmutableFails("remove") {
        val value = it.distinctValues.random()
        it.remove(value)
    }

    // true
    var manager = ConstMultiSetManager(listOf(1), true)
    runSingleMutateTest(manager, emptyList(), true) { manager.remove(1) }

    manager = ConstMultiSetManager(listOf(1, 1, 1), true)
    runSingleMutateTest(manager, listOf(1, 1), true) { manager.remove(1) }
    runSingleMutateTest(manager, listOf(1), true) { manager.remove(1) }
    runSingleMutateTest(manager, emptyList(), true) { manager.remove(1) }

    manager = ConstMultiSetManager(listOf(100, 200, 200, 300), true)
    val expected = listOf(100, 200, 200)
    runSingleMutateTest(manager, expected, true) { it.remove(300) }
    runSingleMutateTest(manager, listOf(100, 200), true) { it.remove(200) }

    // false
    manager = ConstMultiSetManager(emptyList(), true)
    runSingleMutateTest(manager, emptyList(), false) { manager.remove(1) }

    manager = ConstMultiSetManager(listOf(1, 2, 4), true)
    runSingleMutateTest(manager, listOf(1, 2, 4), false) { manager.remove(3) }

    manager = ConstMultiSetManager(listOf(1, 2, 3), true)
    runSingleMutateTest(manager, listOf(2, 3), true) { manager.remove(1) }
    runSingleMutateTest(manager, listOf(2, 3), false) { manager.remove(1) }
}

fun runRemoveAllTests() {
    assertImmutableFails("removeAll") {
        val distinctValues = it.distinctValues.toMutableSet()
        val toRemove = listOf(distinctValues.popRandom()!!, distinctValues.popRandom()!!)
        it.removeAll(toRemove)
    }

    // all success
    var manager = ConstMultiSetManager(listOf(1, 2, 3, 4), true)
    var expected = listOf(1, 2, 3, 4)
    runSingleMutateTest(manager, expected, true) { manager.removeAll(emptyList()) }

    expected = listOf(2, 3, 4)
    runSingleMutateTest(manager, expected, true) { manager.removeAll(listOf(1)) }
    expected = listOf(3, 4)
    runSingleMutateTest(manager, expected, true) { manager.removeAll(listOf(2)) }

    manager = ConstMultiSetManager(listOfValue(5, 1), true)
    runSingleMutateTest(manager, listOf(1, 1), true) { manager.removeAll(listOf(1, 1, 1)) }

    manager = ConstMultiSetManager(listOf(-1, 0, 1), true)
    runSingleMutateTest(manager, emptyList(), true) { manager.removeAll(listOf(0, 1, -1)) }

    // all fail
    manager = ConstMultiSetManager(emptyList(), true)
    runSingleMutateTest(manager, emptyList(), false) { manager.removeAll(listOf(1)) }

    manager = ConstMultiSetManager(listOf(1, 1), true)
    runSingleMutateTest(manager, listOf(1, 1), false) { manager.removeAll(listOf(2)) }

    manager = ConstMultiSetManager(listOf(100, 200, 300), true)
    expected = listOf(100, 200, 300)
    runSingleMutateTest(manager, expected, false) { manager.removeAll(listOf(400, 500, 600)) }

    // some success, some failure
    manager = ConstMultiSetManager(setOf(1, 2, 3), true)
    runSingleMutateTest(manager, listOf(2, 3), true) { manager.removeAll(listOf(1, 4)) }

    manager = ConstMultiSetManager(setOf(5, 5, 6), true)
    runSingleMutateTest(manager, listOf(6), true) { manager.removeAll(listOf(5, 5, 5)) }
}

fun runRetainAllTests() {
    assertImmutableFails("retainAll") {
        val value = it.distinctValues.random()
        it.retainAll(listOf(value))
    }

    // subset
    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), true)
    runSingleMutateTest(manager, emptyList(), true) { it.retainAll(listOf(1)) }

    manager = ConstMultiSetManager(listOf(1), true)
    runSingleMutateTest(manager, listOf(1), true) { it.retainAll(listOf(1)) }

    manager = ConstMultiSetManager(listOfValue(6, 2), true)
    var values = listOf(2, 2, 2)
    runSingleMutateTest(manager, values, true) { it.retainAll(values) }

    manager = ConstMultiSetManager(listOf(1, 2, 3, 4, 5), true)
    values = listOf(2, 3, 5)
    runSingleMutateTest(manager, values, true) { it.retainAll(values) }

    // completely separate
    manager = ConstMultiSetManager(emptyList(), true)
    runSingleMutateTest(manager, emptyList(), true) { it.retainAll(setOf(1)) }

    manager = ConstMultiSetManager(listOf(1, 2, 3), true)
    runSingleMutateTest(manager, emptyList(), true) { it.retainAll(listOf(4, 5, 6)) }

    manager = ConstMultiSetManager(listOf(10, 10, 10), true)
    runSingleMutateTest(manager, emptyList(), true) { it.retainAll(listOf(1, 1)) }

    // some overlap
    manager = ConstMultiSetManager(listOf(1, 2, 3, 4), true)
    runSingleMutateTest(manager, listOf(2, 3), true) { it.retainAll(listOf(2, 3, 5)) }

    manager = ConstMultiSetManager(listOf(1, 1, 2), true)
    runSingleMutateTest(manager, listOf(1, 2), true) { it.retainAll(listOf(1, 2, 2)) }
}

fun runClearTests() {
    assertImmutableFails("clear") { it.clear() }

    var manager: ConstMultiSetManager<Int> = ConstMultiSetManager(emptyList(), true)
    manager.clear()
    assertEquals(0, manager.size)

    manager = ConstMultiSetManager(listOf(1, 2, 3), true)
    manager.clear()
    assertEquals(0, manager.size)

    manager = ConstMultiSetManager(listOf(-45, -45, -45, -45), true)
    manager.clear()
    assertEquals(0, manager.size)
}

private fun <T> runSingleMutateTest(
    manager: ConstMultiSetManager<T>,
    expectedValues: List<T>,
    success: Boolean = true,
    performOp: (ConstMultiSetManager<T>) -> Boolean
) {
    val result = performOp(manager)
    assertEquals(success, result)
    assertEquals(expectedValues.size, manager.size)
    assertTrue(manager.containsAll(expectedValues))
}

private fun assertImmutableFails(operationName: String, performOp: (ConstMultiSetManager<Int>) -> Unit) {
    val errorMessage = "Mutations cannot be performed on this object. Invalid operation: $operationName."
    val manager = ConstMultiSetManager(listOf(5, 3, 4), false)
    val error = assertFailsWith<UnsupportedOperationException> { performOp(manager) }
    assertEquals(errorMessage, error.message)
}
