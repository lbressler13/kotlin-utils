package xyz.lbres.kotlinutils.set.multiset.impl.mutableconst

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals

fun runAddTests() {
    var set: MutableMultiSet<Int> = constMutableMultiSetOf()

    var other = 1
    var expected = constMutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    other = 1
    expected = constMutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    other = 2
    expected = constMutableMultiSetOf(1, 1, 2)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    set = constMutableMultiSetOf(-1, 3, -5, 4, 1000, 17)
    other = 15
    expected = constMutableMultiSetOf(-1, 3, -5, 4, 1000, 17, 15)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    val listSet: MutableMultiSet<List<String>> = constMutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"))
    val listOther = listOf("farewell", "goodbye")
    var listExpected = constMutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOther) }

    val list = listOf("goodbye")
    listExpected = constMutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), listOf("goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(list) }
}

fun runAddAllTests() {
    var set: MutableMultiSet<Int> = constMutableMultiSetOf()

    var other: Collection<Int> = emptyList()
    var expected: MutableMultiSet<Int> = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    other = setOf(4)
    expected = constMutableMultiSetOf(4)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    other = listOf(1, 2, 3)
    expected = constMutableMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    set = constMutableMultiSetOf(1, 2, 3)
    other = constMutableMultiSetOf(2, 2, 3, 3)
    expected = constMutableMultiSetOf(1, 2, 2, 2, 3, 3, 3)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }
}

fun runRemoveTests() {
    // true
    var set = constMutableMultiSetOf(1)
    var other = 1
    var expected: MutableMultiSet<Int> = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    set = constMutableMultiSetOf(1, 1, 1)

    other = 1
    expected = constMutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    other = 1
    expected = constMutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    set = constMutableMultiSetOf(100, 200, 200, 300)
    other = 300
    expected = constMutableMultiSetOf(100, 200, 200)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    // false
    set = constMutableMultiSetOf()
    other = 1
    expected = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.remove(other) }

    set = constMutableMultiSetOf(1, 2, 4)
    other = 3
    expected = constMutableMultiSetOf(1, 2, 4)
    runSingleMutateTest(set, expected, false) { set.remove(other) }

    set = constMutableMultiSetOf(1, 2, 3)
    other = 1
    expected = constMutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.remove(other) }
    runSingleMutateTest(set, expected, false) { set.remove(other) }
}

fun runRemoveAllTests() {
    // all success
    var set = constMutableMultiSetOf(1, 2, 3, 4)

    var other: Collection<Int> = emptyList()
    var expected = constMutableMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    other = listOf(1)
    expected = constMutableMultiSetOf(2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    other = listOf(2, 4)
    expected = constMutableMultiSetOf(3)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = constMutableMultiSetOf(1, 1, 1, 1, 1)
    other = listOf(1, 1, 1)
    expected = constMutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = constMutableMultiSetOf(-1, 0, 1)
    other = setOf(-1, 0, 1)
    expected = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    // all failure
    set = constMutableMultiSetOf()
    other = listOf(1)
    expected = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    set = constMutableMultiSetOf(1, 1)
    other = listOf(2)
    expected = constMutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    set = constMutableMultiSetOf(-10, -20, -30)
    other = listOf(10, 20, 30)
    expected = constMutableMultiSetOf(-10, -20, -30)
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    // some success, some failure
    set = constMutableMultiSetOf(1, 2, 3)
    other = listOf(1, 4)
    expected = constMutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = constMutableMultiSetOf(5, 5, 6)
    other = listOf(5, 5, 5)
    expected = constMutableMultiSetOf(6)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
}

fun runRetainAllTests() {
    // subset
    var set: MutableMultiSet<Int> = constMutableMultiSetOf()
    var other: Collection<Int> = listOf(1)
    var expected: MutableMultiSet<Int> = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = constMutableMultiSetOf(1)
    other = listOf(1)
    expected = constMutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = constMutableMultiSetOf(2, 2, 2, 2, 2)
    other = multiSetOf(2, 2, 2)
    expected = constMutableMultiSetOf(2, 2, 2)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = constMutableMultiSetOf(1, 2, 3, 4, 5)
    other = multiSetOf(2, 3, 5)
    expected = constMutableMultiSetOf(2, 3, 5)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // completely separate
    expected = constMutableMultiSetOf()

    set = constMutableMultiSetOf()
    other = setOf(1)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = constMutableMultiSetOf(1, 2, 3)
    other = multiSetOf(4, 5, 6)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = constMutableMultiSetOf(10, 10, 10)
    other = listOf(-10)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // some overlap
    set = constMutableMultiSetOf(1, 2, 3, 4)
    other = listOf(2, 3, 5)
    expected = constMutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = constMutableMultiSetOf(1, 1, 2)
    other = constMutableMultiSetOf(1, 2, 2)
    expected = constMutableMultiSetOf(1, 2)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
}

fun runClearTests() {
    var set: MutableMultiSet<Int> = constMutableMultiSetOf()
    set.clear()
    assertEquals(0, set.size)
    assertEquals(constMutableMultiSetOf(), set)

    set = constMutableMultiSetOf(1, 2, 3)
    set.clear()
    assertEquals(0, set.size)
    assertEquals(constMutableMultiSetOf(), set)

    set = constMutableMultiSetOf(-45, -45, -45, -45)
    set.clear()
    assertEquals(0, set.size)
    assertEquals(constMutableMultiSetOf(), set)
}

private fun <T> runSingleMutateTest(
    set: MutableMultiSet<T>,
    expected: MutableMultiSet<T>,
    success: Boolean = true,
    op: () -> Boolean
) {
    val result = op()
    assertEquals(success, result)
    assertEquals(expected, set)
    assertEquals(expected.size, set.size)
}
