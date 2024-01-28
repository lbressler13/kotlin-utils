package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.StringList
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals

fun runAddTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>, createMutableStringListSet: (Collection<StringList>) -> MutableMultiSet<StringList>) {
    var intSet: MutableMultiSet<Int> = createMutableIntSet(emptyList())

    var intExpected = multiSetOf(1)
    runSingleMutateTest(intSet, intExpected, true) { intSet.add(1) }

    intExpected = multiSetOf(1, 1)
    runSingleMutateTest(intSet, intExpected, true) { intSet.add(1) }

    intExpected = multiSetOf(1, 1, 2)
    runSingleMutateTest(intSet, intExpected, true) { intSet.add(2) }

    intSet = createMutableIntSet(listOf(-1, 3, -5, 4, 1000, 17))
    intExpected = multiSetOf(-1, 3, -5, 4, 1000, 17, 15)
    runSingleMutateTest(intSet, intExpected, true) { intSet.add(15) }

    val listSet: MutableMultiSet<List<String>> = createMutableStringListSet(listOf(listOf("hello", "world"), listOf("goodbye")))
    var listExpected = multiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOf("farewell", "goodbye")) }

    listExpected = multiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), listOf("goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOf("goodbye")) }
}

fun runAddAllTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>) {
    var set: MutableMultiSet<Int> = createMutableIntSet(emptyList())

    var expected: MutableMultiSet<Int> = createMutableIntSet(emptyList())
    runSingleMutateTest(set, expected, true) { set.addAll(emptyList()) }

    expected = createMutableIntSet(listOf(4))
    runSingleMutateTest(set, expected, true) { set.addAll(setOf(4)) }

    expected = createMutableIntSet(listOf(1, 2, 3, 4))
    runSingleMutateTest(set, expected, true) { set.addAll(listOf(1, 2, 3)) }

    set = createMutableIntSet(listOf(1, 2, 3))
    val other = createMutableIntSet(listOf(2, 2, 3, 3))
    expected = createMutableIntSet(listOf(1, 2, 2, 2, 3, 3, 3))
    runSingleMutateTest(set, expected, true) { set.addAll(other) }
}

fun runRemoveTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>) {
    // true
    var set = createMutableIntSet(listOf(1))
    var expected = createMutableIntSet(emptyList())
    runSingleMutateTest(set, expected, true) { set.remove(1) }

    set = createMutableIntSet(listOf(1, 1, 1))
    expected = createMutableIntSet(listOf(1, 1))
    runSingleMutateTest(set, expected, true) { set.remove(1) }

    expected = createMutableIntSet(listOf(1))
    runSingleMutateTest(set, expected, true) { set.remove(1) }

    set = createMutableIntSet(listOf(100, 200, 200, 300))
    expected = createMutableIntSet(listOf(100, 200, 200))
    runSingleMutateTest(set, expected, true) { set.remove(300) }

    // false
    set = createMutableIntSet(emptyList())
    expected = createMutableIntSet(emptyList())
    runSingleMutateTest(set, expected, false) { set.remove(1) }

    set = createMutableIntSet(listOf(1, 2, 4))
    expected = createMutableIntSet(listOf(1, 2, 4))
    runSingleMutateTest(set, expected, false) { set.remove(3) }

    set = createMutableIntSet(listOf(1, 2, 3))
    expected = createMutableIntSet(listOf(2, 3))
    runSingleMutateTest(set, expected, true) { set.remove(1) }
    runSingleMutateTest(set, expected, false) { set.remove(1) }
}

fun runRemoveAllTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>) {
    // all success
    var set = createMutableIntSet(listOf(1, 2, 3, 4))

    var expected = createMutableIntSet(listOf(1, 2, 3, 4))
    runSingleMutateTest(set, expected, true) { set.removeAll(emptyList()) }

    expected = createMutableIntSet(listOf(2, 3, 4))
    runSingleMutateTest(set, expected, true) { set.removeAll(listOf(1)) }

    expected = createMutableIntSet(listOf(3))
    runSingleMutateTest(set, expected, true) { set.removeAll(listOf(2, 4)) }

    set = createMutableIntSet(listOf(1, 1, 1, 1, 1))
    var other: Collection<Int> = listOf(1, 1, 1)
    expected = createMutableIntSet(listOf(1, 1))
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    set = createMutableIntSet(listOf(-1, 0, 1))
    other = setOf(-1, 0, 1)
    expected = createMutableIntSet(emptyList())
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }

    // all failure
    set = createMutableIntSet(emptyList())
    expected = createMutableIntSet(emptyList())
    runSingleMutateTest(set, expected, false) { set.removeAll(listOf(1)) }

    set = createMutableIntSet(listOf(1, 1))
    expected = createMutableIntSet(listOf(1, 1))
    runSingleMutateTest(set, expected, false) { set.removeAll(listOf(2)) }

    set = createMutableIntSet(listOf(-10, -20, -30))
    other = listOf(10, 20, 30)
    expected = createMutableIntSet(listOf(-10, -20, -30))
    runSingleMutateTest(set, expected, false) { set.removeAll(other) }

    // some success, some failure
    set = createMutableIntSet(listOf(1, 2, 3))
    expected = createMutableIntSet(listOf(2, 3))
    runSingleMutateTest(set, expected, true) { set.removeAll(setOf(1, 4)) }

    set = createMutableIntSet(listOf(5, 5, 6))
    other = listOf(5, 5, 5)
    expected = createMutableIntSet(listOf(6))
    runSingleMutateTest(set, expected, true) { set.removeAll(other) }
}

fun runRetainAllTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>) {
    // equal
    var set: MutableMultiSet<Int> = createMutableIntSet(emptyList())
    var expected: MutableMultiSet<Int> = createMutableIntSet(emptyList())
    runSingleMutateTest(set, expected, true) { set.retainAll(emptyList()) }

    set = createMutableIntSet(listOf(1, 1))
    expected = createMutableIntSet(listOf(1, 1))
    runSingleMutateTest(set, expected, true) { set.retainAll(listOf(1, 1)) }

    set = createMutableIntSet(listOf(2, 4, 5, 7))
    expected = createMutableIntSet(listOf(2, 4, 5, 7))
    runSingleMutateTest(set, expected, true) { set.retainAll(listOf(2, 4, 5, 7)) }

    // subset
    set = createMutableIntSet(listOf(2, 2, 2, 2, 2))
    var other: Collection<Int> = multiSetOf(2, 2, 2)
    expected = createMutableIntSet(listOf(2, 2, 2))
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = createMutableIntSet(listOf(1, 2, 3, 4, 5))
    other = multiSetOf(2, 3, 5)
    expected = createMutableIntSet(listOf(2, 3, 5))
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    // no overlapping keys
    expected = createMutableIntSet(emptyList())

    set = createMutableIntSet(emptyList())
    runSingleMutateTest(set, expected, true) { set.retainAll(setOf(1)) }

    set = createMutableIntSet(listOf(1, 2, 3))
    other = multiSetOf(4, 5, 6)
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = createMutableIntSet(listOf(10, 10, 10))
    runSingleMutateTest(set, expected, true) { set.retainAll(listOf(-10)) }

    // some overlapping keys
    set = createMutableIntSet(listOf(1, 2, 3, 4))
    other = listOf(2, 3, 5)
    expected = createMutableIntSet(listOf(2, 3))
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }

    set = createMutableIntSet(listOf(1, 1, 2))
    other = createMutableIntSet(listOf(1, 2, 2))
    expected = createMutableIntSet(listOf(1, 2))
    runSingleMutateTest(set, expected, true) { set.retainAll(other) }
}

fun runClearTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>) {
    var set: MutableMultiSet<Int> = createMutableIntSet(emptyList())
    set.clear()
    assertEquals(0, set.size)
    assertEquals(emptyMultiSet(), set)

    set = createMutableIntSet(listOf(1, 2, 3))
    set.clear()
    assertEquals(0, set.size)
    assertEquals(emptyMultiSet(), set)

    set = createMutableIntSet(listOf(-45, -45, -45, -45))
    set.clear()
    assertEquals(0, set.size)
    assertEquals(emptyMultiSet(), set)
}
