package xyz.lbres.kotlinutils.set.multiset.impl.mutableconst

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals

fun runAddTests() {
    var set: MutableConstMultiSet<Int> = mutableConstMultiSetOf()

    var other = 1
    var expected = mutableConstMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    other = 1
    expected = mutableConstMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    other = 2
    expected = mutableConstMultiSetOf(1, 1, 2)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    set = mutableConstMultiSetOf(-1, 3, -5, 4, 1000, 17)
    other = 15
    expected = mutableConstMultiSetOf(-1, 3, -5, 4, 1000, 17, 15)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    val listSet: MutableConstMultiSet<List<String>> = mutableConstMultiSetOf(listOf("hello", "world"), listOf("goodbye"))
    val listOther = listOf("farewell", "goodbye")
    var listExpected = mutableConstMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOther) }

    val list = listOf("goodbye")
    listExpected = mutableConstMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), listOf("goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(list) }
}

fun runAddAllTests() {
    var set: MutableConstMultiSet<Int> = mutableConstMultiSetOf()

    var other: Collection<Int> = emptyList()
    var expected: MutableConstMultiSet<Int> = mutableConstMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    other = setOf(4)
    expected = mutableConstMultiSetOf(4)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    other = listOf(1, 2, 3)
    expected = mutableConstMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    set = mutableConstMultiSetOf(1, 2, 3)
    other = mutableConstMultiSetOf(2, 2, 3, 3)
    expected = mutableConstMultiSetOf(1, 2, 2, 2, 3, 3, 3)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }
}

fun runRemoveTests() {
    // true
    var set = mutableConstMultiSetOf(1)
    var other = 1
    var expected: MutableConstMultiSet<Int> = mutableConstMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    set = mutableConstMultiSetOf(1, 1, 1)

    other = 1
    expected = mutableConstMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    other = 1
    expected = mutableConstMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    set = mutableConstMultiSetOf(100, 200, 200, 300)
    other = 300
    expected = mutableConstMultiSetOf(100, 200, 200)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    // false
    set = mutableConstMultiSetOf()
    other = 1
    expected = mutableConstMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.remove(other) }

    set = mutableConstMultiSetOf(1, 2, 4)
    other = 3
    expected = mutableConstMultiSetOf(1, 2, 4)
    runSingleMutateTest(set, expected, false) { set.remove(other) }

    set = mutableConstMultiSetOf(1, 2, 3)
    other = 1
    expected = mutableConstMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.remove(other) }
    runSingleMutateTest(set, expected, false) { set.remove(other) }
}

fun runRemoveAllTests() {
    // all success
    var set = mutableConstMultiSetOf(1, 2, 3, 4)

    var other: Collection<Int> = emptyList()
    var expected = mutableConstMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    other = listOf(1)
    expected = mutableConstMultiSetOf(2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    other = listOf(2, 4)
    expected = mutableConstMultiSetOf(3)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = mutableConstMultiSetOf(1, 1, 1, 1, 1)
    other = listOf(1, 1, 1)
    expected = mutableConstMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = mutableConstMultiSetOf(-1, 0, 1)
    other = setOf(-1, 0, 1)
    expected = mutableConstMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    // all failure
    set = mutableConstMultiSetOf()
    other = listOf(1)
    expected = mutableConstMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    set = mutableConstMultiSetOf(1, 1)
    other = listOf(2)
    expected = mutableConstMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    set = mutableConstMultiSetOf(-10, -20, -30)
    other = listOf(10, 20, 30)
    expected = mutableConstMultiSetOf(-10, -20, -30)
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    // some success, some failure
    set = mutableConstMultiSetOf(1, 2, 3)
    other = listOf(1, 4)
    expected = mutableConstMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = mutableConstMultiSetOf(5, 5, 6)
    other = listOf(5, 5, 5)
    expected = mutableConstMultiSetOf(6)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
}

fun runRetainAllTests() {
    // subset
    var set: MutableConstMultiSet<Int> = mutableConstMultiSetOf()
    var other: Collection<Int> = listOf(1)
    var expected: MutableConstMultiSet<Int> = mutableConstMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableConstMultiSetOf(1)
    other = listOf(1)
    expected = mutableConstMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableConstMultiSetOf(2, 2, 2, 2, 2)
    other = multiSetOf(2, 2, 2)
    expected = mutableConstMultiSetOf(2, 2, 2)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableConstMultiSetOf(1, 2, 3, 4, 5)
    other = multiSetOf(2, 3, 5)
    expected = mutableConstMultiSetOf(2, 3, 5)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // completely separate
    expected = mutableConstMultiSetOf()

    set = mutableConstMultiSetOf()
    other = setOf(1)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableConstMultiSetOf(1, 2, 3)
    other = multiSetOf(4, 5, 6)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableConstMultiSetOf(10, 10, 10)
    other = listOf(-10)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // some overlap
    set = mutableConstMultiSetOf(1, 2, 3, 4)
    other = listOf(2, 3, 5)
    expected = mutableConstMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableConstMultiSetOf(1, 1, 2)
    other = mutableConstMultiSetOf(1, 2, 2)
    expected = mutableConstMultiSetOf(1, 2)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
}

fun runClearTests() {
    var set: MutableConstMultiSet<Int> = mutableConstMultiSetOf()
    set.clear()
    assertEquals(0, set.size)
    assertEquals(mutableConstMultiSetOf(), set)

    set = mutableConstMultiSetOf(1, 2, 3)
    set.clear()
    assertEquals(0, set.size)
    assertEquals(mutableConstMultiSetOf(), set)

    set = mutableConstMultiSetOf(-45, -45, -45, -45)
    set.clear()
    assertEquals(0, set.size)
    assertEquals(mutableConstMultiSetOf(), set)
}

private fun <T> runSingleMutateTest(
    set: MutableConstMultiSet<T>,
    expected: MutableConstMultiSet<T>,
    success: Boolean = true,
    op: () -> Boolean
) {
    val result = op()
    assertEquals(success, result)
    assertEquals(expected, set)
    assertEquals(expected.size, set.size)
}
