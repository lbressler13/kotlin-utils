package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.list.StringList
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf

fun runMultiSetAddTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>, createMutableStringListSet: (Collection<StringList>) -> MutableMultiSet<StringList>) {
    var set: MutableMultiSet<Int> = createMutableIntSet(emptyList())

    var expected = multiSetOf(1)
    runSingleMutateTest(set, expected, true) { set.add(1) }

    expected = multiSetOf(1, 1)
    runSingleMutateTest(set, expected, true) { set.add(1) }

    expected = multiSetOf(1, 1, 2)
    runSingleMutateTest(set, expected, true) { set.add(2) }

    set = createMutableIntSet(listOf(-1, 3, -5, 4, 1000, 17))
    expected = multiSetOf(-1, 3, -5, 4, 1000, 17, 15)
    runSingleMutateTest(set, expected, true) { set.add(15) }

    val listSet: MutableMultiSet<List<String>> = createMutableStringListSet(listOf(listOf("hello", "world"), listOf("goodbye")))
    var listExpected = multiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOf("farewell", "goodbye")) }

    listExpected = multiSetOf(listOf("hello", "world"), listOf("goodbye"), listOf("farewell", "goodbye"), listOf("goodbye"))
    runSingleMutateTest(listSet, listExpected, true) { listSet.add(listOf("goodbye")) }
}

fun runMultiSetAddAllTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>) {
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
