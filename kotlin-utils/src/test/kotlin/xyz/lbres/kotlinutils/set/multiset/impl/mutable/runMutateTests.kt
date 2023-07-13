package xyz.lbres.kotlinutils.set.multiset.impl.mutable

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals

fun runAddTests() {
    var set: MutableMultiSet<Int> = mutableMultiSetOf()

    var other = 1
    var expected = mutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    other = 1
    expected = mutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    other = 2
    expected = mutableMultiSetOf(1, 1, 2)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    set = mutableMultiSetOf(-1, 3, -5, 4, 1000, 17)
    other = 15
    expected = mutableMultiSetOf(-1, 3, -5, 4, 1000, 17, 15)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    val listSet: MutableMultiSet<List<String>> = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"))
    val listOther = listOf("farewell", "goodbye")
    var listExpected = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOther) }

    val mutableList = mutableListOf("goodbye")
    listExpected = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), listOf("goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(mutableList) }

    mutableList.clear()
    listExpected = mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), emptyList())
    assertEquals(listExpected, listSet)
}

fun runAddAllTests() {
    var set: MutableMultiSet<Int> = mutableMultiSetOf()

    var other: Collection<Int> = emptyList()
    var expected: MutableMultiSet<Int> = mutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    other = setOf(4)
    expected = mutableMultiSetOf(4)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    other = listOf(1, 2, 3)
    expected = mutableMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    set = mutableMultiSetOf(1, 2, 3)
    other = mutableMultiSetOf(2, 2, 3, 3)
    expected = mutableMultiSetOf(1, 2, 2, 2, 3, 3, 3)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }
}

fun runRemoveTests() {
    // true
    var set = mutableMultiSetOf(1)
    var other = 1
    var expected: MutableMultiSet<Int> = mutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    set = mutableMultiSetOf(1, 1, 1)

    other = 1
    expected = mutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    other = 1
    expected = mutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    set = mutableMultiSetOf(100, 200, 200, 300)
    other = 300
    expected = mutableMultiSetOf(100, 200, 200)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    // false
    set = mutableMultiSetOf()
    other = 1
    expected = mutableMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.remove(other) }

    set = mutableMultiSetOf(1, 2, 4)
    other = 3
    expected = mutableMultiSetOf(1, 2, 4)
    runSingleMutateTest(set, expected, false) { set.remove(other) }

    set = mutableMultiSetOf(1, 2, 3)
    other = 1
    expected = mutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.remove(other) }
    runSingleMutateTest(set, expected, false) { set.remove(other) }

    // changed value
    val mutableList = mutableListOf("goodbye")
    var listSet = mutableMultiSetOf(mutableList, listOf("hello world"))
    var listExpected = mutableMultiSetOf(listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.remove(listOf("goodbye")) }

    listSet = mutableMultiSetOf(mutableList, listOf("hello world"))
    mutableList.clear()
    listExpected = mutableMultiSetOf(emptyList(), listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, false) { listSet.remove(listOf("goodbye")) }

    listExpected = mutableMultiSetOf(listOf("hello world"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.remove(emptyList()) }
}

fun runRemoveAllTests() {
    // all success
    var set = mutableMultiSetOf(1, 2, 3, 4)

    var other: Collection<Int> = emptyList()
    var expected = mutableMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    other = listOf(1)
    expected = mutableMultiSetOf(2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    other = listOf(2, 4)
    expected = mutableMultiSetOf(3)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = mutableMultiSetOf(1, 1, 1, 1, 1)
    other = listOf(1, 1, 1)
    expected = mutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = mutableMultiSetOf(-1, 0, 1)
    other = setOf(-1, 0, 1)
    expected = mutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    // all failure
    set = mutableMultiSetOf()
    other = listOf(1)
    expected = mutableMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    set = mutableMultiSetOf(1, 1)
    other = listOf(2)
    expected = mutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    set = mutableMultiSetOf(-10, -20, -30)
    other = listOf(10, 20, 30)
    expected = mutableMultiSetOf(-10, -20, -30)
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    // some success, some failure
    set = mutableMultiSetOf(1, 2, 3)
    other = listOf(1, 4)
    expected = mutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = mutableMultiSetOf(5, 5, 6)
    other = listOf(5, 5, 5)
    expected = mutableMultiSetOf(6)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
}

fun runRetainAllTests() {
    // subset
    var set: MutableMultiSet<Int> = mutableMultiSetOf()
    var other: Collection<Int> = listOf(1)
    var expected: MutableMultiSet<Int> = mutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableMultiSetOf(1)
    other = listOf(1)
    expected = mutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableMultiSetOf(2, 2, 2, 2, 2)
    other = multiSetOf(2, 2, 2)
    expected = mutableMultiSetOf(2, 2, 2)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableMultiSetOf(1, 2, 3, 4, 5)
    other = multiSetOf(2, 3, 5)
    expected = mutableMultiSetOf(2, 3, 5)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // completely separate
    expected = mutableMultiSetOf()

    set = mutableMultiSetOf()
    other = setOf(1)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableMultiSetOf(1, 2, 3)
    other = multiSetOf(4, 5, 6)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableMultiSetOf(10, 10, 10)
    other = listOf(-10)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // some overlap
    set = mutableMultiSetOf(1, 2, 3, 4)
    other = listOf(2, 3, 5)
    expected = mutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = mutableMultiSetOf(1, 1, 2)
    other = mutableMultiSetOf(1, 2, 2)
    expected = mutableMultiSetOf(1, 2)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // changed value
    val mutableList = mutableListOf(1, 4)
    var listSet = mutableMultiSetOf(mutableList, listOf(1, 2, 3))
    var listExpected = mutableMultiSetOf(listOf(1, 4))
    runSingleMutateTest(listSet, listExpected, true) { listSet.retainAll(listOf(listOf(1, 4))) }

    listSet = mutableMultiSetOf(mutableList, listOf(1, 2, 3))
    mutableList.clear()
    listExpected = mutableMultiSetOf(emptyList())
    runSingleMutateTest(listSet, listExpected, true) { listSet.retainAll(listOf(emptyList())) }
}

fun runClearTests() {
    var set: MutableMultiSet<Int> = mutableMultiSetOf()
    set.clear()
    assertEquals(0, set.size)
    assertEquals(mutableMultiSetOf(), set)

    set = mutableMultiSetOf(1, 2, 3)
    set.clear()
    assertEquals(0, set.size)
    assertEquals(mutableMultiSetOf(), set)

    set = mutableMultiSetOf(-45, -45, -45, -45)
    set.clear()
    assertEquals(0, set.size)
    assertEquals(mutableMultiSetOf(), set)
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
