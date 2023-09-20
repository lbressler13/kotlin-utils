package xyz.lbres.kotlinutils.set.multiset.manager

import xyz.lbres.kotlinutils.list.StringList
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

//fun runAddAllTests() {
//    var set: MutableMultiSet<Int> = mutableMultiSetOf()
//
//    var other: Collection<Int> = emptyList()
//    var expected: MutableMultiSet<Int> = mutableMultiSetOf()
//    runSingleMutateTest(set, expected, true) { set.addAll(other) }
//
//    other = setOf(4)
//    expected = mutableMultiSetOf(4)
//    runSingleMutateTest(set, expected, true) { set.addAll(other) }
//
//    other = listOf(1, 2, 3)
//    expected = mutableMultiSetOf(1, 2, 3, 4)
//    runSingleMutateTest(set, expected, true) { set.addAll(other) }
//
//    set = mutableMultiSetOf(1, 2, 3)
//    other = mutableMultiSetOf(2, 2, 3, 3)
//    expected = mutableMultiSetOf(1, 2, 2, 2, 3, 3, 3)
//    runSingleMutateTest(set, expected, true) { set.addAll(other) }
//
//    // changed value
//    val mutableList1 = mutableListOf("goodbye")
//    val mutableList2 = mutableListOf("hello", "world")
//    val listSet: MutableMultiSet<StringList> = mutableMultiSetOf(mutableList1, listOf("goodbye"))
//    val listOther: MutableMultiSet<StringList> = mutableMultiSetOf(listOf("farewell", "goodbye"), mutableList2)
//    var listExpected: MutableMultiSet<StringList> = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), listOf("goodbye"))
//
//    runSingleMutateTest(listSet, listExpected, true) { listSet.addAll(listOther) }
//
//    mutableList1.clear()
//    listExpected = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), emptyList())
//    assertEquals(listExpected, listSet)
//}

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

//fun runRemoveAllTests() {
//    // all success
//    var set = mutableMultiSetOf(1, 2, 3, 4)
//
//    var other: Collection<Int> = emptyList()
//    var expected = mutableMultiSetOf(1, 2, 3, 4)
//    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
//
//    other = listOf(1)
//    expected = mutableMultiSetOf(2, 3, 4)
//    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
//
//    other = listOf(2, 4)
//    expected = mutableMultiSetOf(3)
//    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
//
//    set = mutableMultiSetOf(1, 1, 1, 1, 1)
//    other = listOf(1, 1, 1)
//    expected = mutableMultiSetOf(1, 1)
//    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
//
//    set = mutableMultiSetOf(-1, 0, 1)
//    other = setOf(-1, 0, 1)
//    expected = mutableMultiSetOf()
//    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
//
//    // all failure
//    set = mutableMultiSetOf()
//    other = listOf(1)
//    expected = mutableMultiSetOf()
//    runSingleMutateTest(set, expected, false) { set.removeAll(other) }
//
//    set = mutableMultiSetOf(1, 1)
//    other = listOf(2)
//    expected = mutableMultiSetOf(1, 1)
//    runSingleMutateTest(set, expected, false) { set.removeAll(other) }
//
//    set = mutableMultiSetOf(-10, -20, -30)
//    other = listOf(10, 20, 30)
//    expected = mutableMultiSetOf(-10, -20, -30)
//    runSingleMutateTest(set, expected, false) { set.removeAll(other) }
//
//    // some success, some failure
//    set = mutableMultiSetOf(1, 2, 3)
//    other = listOf(1, 4)
//    expected = mutableMultiSetOf(2, 3)
//    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
//
//    set = mutableMultiSetOf(5, 5, 6)
//    other = listOf(5, 5, 5)
//    expected = mutableMultiSetOf(6)
//    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
//
//    // changed value
//    val mutableList = mutableListOf("goodbye")
//    var listSet = mutableMultiSetOf(mutableList, listOf("hello world"), listOf("goodbye"))
//    var listExpected = mutableMultiSetOf(listOf("hello world"))
//    runSingleMutateTest(listSet, listExpected, true) { listSet.removeAll(listOf(listOf("goodbye"), listOf("goodbye"))) }
//
//    listSet = mutableMultiSetOf(mutableList, listOf("hello world"))
//    mutableList.clear()
//    listExpected = mutableMultiSetOf(emptyList(), listOf("hello world"))
//    runSingleMutateTest(listSet, listExpected, false) { listSet.removeAll(listOf(listOf("goodbye"))) }
//
//    listExpected = mutableMultiSetOf(emptyList(), listOf("hello world"))
//    runSingleMutateTest(listSet, listExpected, false) { listSet.removeAll(listOf(listOf("123"))) }
//
//    mutableList.add("123")
//    listExpected = mutableMultiSetOf(listOf("hello world"))
//    runSingleMutateTest(listSet, listExpected, true) { listSet.removeAll(listOf(listOf("123"))) }
//}
//
//fun runRetainAllTests() {
//    // subset
//    var set: MutableMultiSet<Int> = mutableMultiSetOf()
//    var other: Collection<Int> = listOf(1)
//    var expected: MutableMultiSet<Int> = mutableMultiSetOf()
//    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
//
//    set = mutableMultiSetOf(1)
//    other = listOf(1)
//    expected = mutableMultiSetOf(1)
//    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
//
//    set = mutableMultiSetOf(2, 2, 2, 2, 2)
//    other = multiSetOf(2, 2, 2)
//    expected = mutableMultiSetOf(2, 2, 2)
//    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
//
//    set = mutableMultiSetOf(1, 2, 3, 4, 5)
//    other = multiSetOf(2, 3, 5)
//    expected = mutableMultiSetOf(2, 3, 5)
//    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
//
//    // completely separate
//    expected = mutableMultiSetOf()
//
//    set = mutableMultiSetOf()
//    other = setOf(1)
//    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
//
//    set = mutableMultiSetOf(1, 2, 3)
//    other = multiSetOf(4, 5, 6)
//    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
//
//    set = mutableMultiSetOf(10, 10, 10)
//    other = listOf(-10)
//    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
//
//    // some overlap
//    set = mutableMultiSetOf(1, 2, 3, 4)
//    other = listOf(2, 3, 5)
//    expected = mutableMultiSetOf(2, 3)
//    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
//
//    set = mutableMultiSetOf(1, 1, 2)
//    other = mutableMultiSetOf(1, 2, 2)
//    expected = mutableMultiSetOf(1, 2)
//    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
//
//    // changed value
//    val mutableList = mutableListOf(1, 4)
//    var listSet = mutableMultiSetOf(mutableList, listOf(1, 2, 3))
//    var listExpected = mutableMultiSetOf(listOf(1, 4))
//    runSingleMutateTest(listSet, listExpected, true) { listSet.retainAll(listOf(listOf(1, 4))) }
//
//    listSet = mutableMultiSetOf(mutableList, listOf(1, 2, 3))
//    mutableList.clear()
//    listExpected = mutableMultiSetOf()
//    runSingleMutateTest(listSet, listExpected, true) { listSet.retainAll(listOf(listOf(1, 4))) }
//
//    listSet = mutableMultiSetOf(mutableList, listOf(1, 2, 3))
//    listExpected = mutableMultiSetOf(emptyList())
//    runSingleMutateTest(listSet, listExpected, true) { listSet.retainAll(listOf(emptyList())) }
//}

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
