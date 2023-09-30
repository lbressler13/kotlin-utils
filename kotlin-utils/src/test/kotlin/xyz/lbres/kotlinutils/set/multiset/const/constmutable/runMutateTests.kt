package xyz.lbres.kotlinutils.set.multiset.const.constmutable

import xyz.lbres.kotlinutils.set.multiset.const.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals

fun runAddTests() {
    var set: ConstMutableMultiSet<Int> = constMutableMultiSetOf()

    var expected = constMutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.add(1) }

    expected = constMutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.add(1) }

    expected = constMutableMultiSetOf(1, 1, 2)
    runSingleMutateTest(set, expected, true) { set.add(2) }

    set = constMutableMultiSetOf(-1, 3, -5, 4, 1000, 17)
    expected = constMutableMultiSetOf(-1, 3, -5, 4, 1000, 17, 15)
    runSingleMutateTest(set, expected, true) { set.add(15) }

    val listSet: ConstMutableMultiSet<List<String>> = constMutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"))
    var listExpected = constMutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOf("farewell", "goodbye")) }

    listExpected = constMutableMultiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), listOf("goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOf("goodbye")) }
}

fun runAddAllTests() {
    var set: ConstMutableMultiSet<Int> = constMutableMultiSetOf()

    var expected: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.addAll(emptyList()) }

    expected = constMutableMultiSetOf(4)
    runSingleMutateTest(set, expected, true) { set.addAll(setOf(4)) }

    expected = constMutableMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.addAll(listOf(1, 2, 3)) }

    set = constMutableMultiSetOf(1, 2, 3)
    val other = constMutableMultiSetOf(2, 2, 3, 3)
    expected = constMutableMultiSetOf(1, 2, 2, 2, 3, 3, 3)
    runSingleMutateTest(set, expected, true) { set.addAll(other) }
}

fun runRemoveTests() {
    // true
    var set = constMutableMultiSetOf(1)
    var expected: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.remove(1) }

    set = constMutableMultiSetOf(1, 1, 1)
    expected = constMutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.remove(1) }

    expected = constMutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.remove(1) }

    set = constMutableMultiSetOf(100, 200, 200, 300)
    expected = constMutableMultiSetOf(100, 200, 200)
    runSingleMutateTest(set, expected, true) { set.remove(300) }

    // false
    set = constMutableMultiSetOf()
    expected = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.remove(1) }

    set = constMutableMultiSetOf(1, 2, 4)
    expected = constMutableMultiSetOf(1, 2, 4)
    runSingleMutateTest(set, expected, false) { set.remove(3) }

    set = constMutableMultiSetOf(1, 2, 3)
    expected = constMutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.remove(1) }
    runSingleMutateTest(set, expected, false) { set.remove(1) }
}

fun runRemoveAllTests() {
    // all success
    var set = constMutableMultiSetOf(1, 2, 3, 4)

    var expected = constMutableMultiSetOf(1, 2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(emptyList()) }

    expected = constMutableMultiSetOf(2, 3, 4)
    runSingleMutateTest(set, expected, true) { set.removeAll(listOf(1)) }

    expected = constMutableMultiSetOf(3)
    runSingleMutateTest(set, expected, true) { set.removeAll(listOf(2, 4)) }

    set = constMutableMultiSetOf(1, 1, 1, 1, 1)
    var other: Collection<Int> = listOf(1, 1, 1)
    expected = constMutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = constMutableMultiSetOf(-1, 0, 1)
    other = setOf(-1, 0, 1)
    expected = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    // all failure
    set = constMutableMultiSetOf()
    expected = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, false) { set.removeAll(listOf(1)) }

    set = constMutableMultiSetOf(1, 1)
    expected = constMutableMultiSetOf(1, 1)
    runSingleMutateTest(set, expected, false) { set.removeAll(listOf(2)) }

    set = constMutableMultiSetOf(-10, -20, -30)
    other = listOf(10, 20, 30)
    expected = constMutableMultiSetOf(-10, -20, -30)
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    // some success, some failure
    set = constMutableMultiSetOf(1, 2, 3)
    expected = constMutableMultiSetOf(2, 3)
    runSingleMutateTest(set, expected, true) { set.removeAll(setOf(1, 4)) }

    set = constMutableMultiSetOf(5, 5, 6)
    other = listOf(5, 5, 5)
    expected = constMutableMultiSetOf(6)
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
}

fun runRetainAllTests() {
    // subset
    var set: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    var expected: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.retainAll(listOf(1)) }

    set = constMutableMultiSetOf(1)
    expected = constMutableMultiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.retainAll(listOf(1)) }

    set = constMutableMultiSetOf(2, 2, 2, 2, 2)
    var other: Collection<Int> = multiSetOf(2, 2, 2)
    expected = constMutableMultiSetOf(2, 2, 2)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = constMutableMultiSetOf(1, 2, 3, 4, 5)
    other = multiSetOf(2, 3, 5)
    expected = constMutableMultiSetOf(2, 3, 5)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // completely separate
    expected = constMutableMultiSetOf()

    set = constMutableMultiSetOf()
    runSingleMutateTest(set, expected, true) { set.retainAll(setOf(1)) }

    set = constMutableMultiSetOf(1, 2, 3)
    other = multiSetOf(4, 5, 6)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = constMutableMultiSetOf(10, 10, 10)
    runSingleMutateTest(set, expected, true) { set.retainAll(listOf(-10)) }

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
    var set: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
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

/**
 * Run single test to mutate set
 *
 * @param set [ConstMutableMultiSet]<T>: set to be modified in operation
 * @param expected [ConstMutableMultiSet]<T>: expected state of set after operation
 * @param success [Boolean]: if operation is expected to succeed
 * @param op () -> Boolean: operation to perform
 */
private fun <T> runSingleMutateTest(
    set: ConstMutableMultiSet<T>,
    expected: ConstMutableMultiSet<T>,
    success: Boolean = true,
    op: () -> Boolean
) {
    val result = op()
    assertEquals(success, result)
    assertEquals(expected, set)
    assertEquals(expected.size, set.size)
}
