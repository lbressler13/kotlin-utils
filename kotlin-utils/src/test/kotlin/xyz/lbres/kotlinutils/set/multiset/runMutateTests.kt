package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.classes.multiset.MutableMultiSet
import kotlin.test.assertEquals

internal fun runAddTests() {
    var set: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()

    var other = 1
    var expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    other = 1
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    other = 2
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 2)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-1, 3, -5, 4, 1000, 17)
    other = 15
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-1, 3, -5, 4, 1000, 17, 15)
    runSingleMutateTest(set, expected, true) { set.add(other) }

    val listSet: MutableMultiSet<List<String>> =
        xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"))
    val listOther = listOf("farewell", "goodbye")
    val listExpected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(
        listOf("hello", "world"),
        listOf("goodbye"),
        listOf("farewell", "goodbye")
    )
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOther) }
}

internal fun runAddAllTests() {
    var set: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()

    var other: Collection<Int> = listOf()
    var expected: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    other = setOf(4)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(4)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    other = listOf(1, 2, 3)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3)
    other = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 2, 3, 3)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 2, 2, 3, 3, 3)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }
}

internal fun runRemoveTests() {
    // true
    var set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
    var other = 1
    var expected: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 1)

    other = 1
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    other = 1
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(100, 200, 200, 300)
    other = 300
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(100, 200, 200)
    runSingleMutateTest(set, expected, true) { set.remove(other) }

    // false
    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    other = 1
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.remove(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 4)
    other = 3
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 4)
    runSingleMutateTest(set, expected, false) { set.remove(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3)
    other = 1
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.remove(other) }
    runSingleMutateTest(set, expected, false) { set.remove(other) }
}

internal fun runRemoveAllTests() {
    // all success
    var set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3, 4)

    var other: Collection<Int> = listOf()
    var expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    other = listOf(1)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    other = listOf(2, 4)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(3)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 1, 1, 1)
    other = listOf(1, 1, 1)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-1, 0, 1)
    other = setOf(-1, 0, 1)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    // all failure
    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    other = listOf(1)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1)
    other = listOf(2)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-10, -20, -30)
    other = listOf(10, 20, 30)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-10, -20, -30)
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    // some success, some failure
    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3)
    other = listOf(1, 4)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(5, 5, 6)
    other = listOf(5, 5, 5)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(6)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
}

internal fun runRetainAllTests() {
    // subset
    var set: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    var other: Collection<Int> = listOf(1)
    var expected: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
    other = listOf(1)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 2, 2, 2, 2)
    other = xyz.lbres.kotlinutils.classes.multiset.multiSetOf(2, 2, 2)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 2, 2)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3, 4, 5)
    other = xyz.lbres.kotlinutils.classes.multiset.multiSetOf(2, 3, 5)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 3, 5)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // completely separate
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    other = setOf(1)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3)
    other = xyz.lbres.kotlinutils.classes.multiset.multiSetOf(4, 5, 6)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(10, 10, 10)
    other = listOf(-10)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // some overlap
    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3, 4)
    other = listOf(2, 3, 5)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 1, 2)
    other = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 2)
    expected = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
}

internal fun runClearTests() {
    var set: MutableMultiSet<Int> = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf()
    set.clear()
    assertEquals(0, set.size)
    assertEquals(setOf(), set)

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(1, 2, 3)
    set.clear()
    assertEquals(0, set.size)
    assertEquals(setOf(), set)

    set = xyz.lbres.kotlinutils.classes.multiset.mutableMultiSetOf(-45, -45, -45, -45)
    set.clear()
    assertEquals(0, set.size)
    assertEquals(setOf(), set)
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
