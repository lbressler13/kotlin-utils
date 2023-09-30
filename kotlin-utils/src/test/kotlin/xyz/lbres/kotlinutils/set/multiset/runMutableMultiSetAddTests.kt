package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.list.StringList
import kotlin.test.assertEquals

fun runMutableMultiSetAddTests(createMutableIntSet: (Collection<Int>) -> MutableMultiSet<Int>, createMutableStringListSet: (Collection<StringList>) -> MutableMultiSet<StringList>) {
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

/**
 * Run single test to mutate set
 *
 * @param set [MutableMultiSet]<T>: set to be modified in operation
 * @param expected [MutableMultiSet]<T>: expected state of set after operation
 * @param success [Boolean]: if operation is expected to succeed
 * @param op () -> Boolean: operation to perform
 */
fun <T> runSingleMutateTest(
    set: MutableMultiSet<T>,
    expected: MultiSet<T>,
    success: Boolean = true,
    op: () -> Boolean
) {
    val result = op()
    assertEquals(success, result)
    assertEquals(expected, set)
    assertEquals(expected.size, set.size)
}
