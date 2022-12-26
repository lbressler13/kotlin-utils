package xyz.lbres.kotlinutils.set.multiset.methods

import xyz.lbres.kotlinutils.list.mutablelist.ext.popRandom
import xyz.lbres.kotlinutils.runRandomTest
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal fun runPopRandomTests() {
    assertNull(mutableListOf<Int>().popRandom())

    var intSet = mutableMultiSetOf(1)
    assertEquals(1, intSet.popRandom())
    assertNull(intSet.popRandom())
    assertTrue(intSet.isEmpty())

    intSet = mutableMultiSetOf(1, 1, 1)
    repeat(3) { assertEquals(1, intSet.popRandom()) }
    assertNull(intSet.popRandom())
    assertTrue(intSet.isEmpty())

    intSet = mutableMultiSetOf(1, 2, 3)
    runSinglePopRandomTest(intSet)

    intSet = mutableMultiSetOf(-1, 1, 2, 2, 2, 3, 3)
    runSinglePopRandomTest(intSet)

    val stringSet = mutableMultiSetOf("", "", "hello", "world", "goodbye", "", "", "")
    runSinglePopRandomTest(stringSet)

    val e1 = NullPointerException("Cannot invoke method on null value")
    val e2 = ArithmeticException()
    val e3 = ClassCastException("Cannot cast Int to List")
    val errorSet = mutableMultiSetOf(e1, e2, e3, e3)
    runSinglePopRandomTest(errorSet)

    val listSet = mutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf("7"), listOf("7"), listOf("7"))
    runSinglePopRandomTest(listSet)
}

/**
 * Run popRandom test on a single set.
 * Assumes the set has size >= 2 and has at least 2 distinct values
 *
 * @param values [MutableMultiSet]: the value to test
 */
private fun <T> runSinglePopRandomTest(values: MutableMultiSet<T>) {
    val createResultSet = {
        val copy = mutableMultiSetOf<T>()
        copy.addAll(values)
        val resultSet = mutableMultiSetOf<T>()
        repeat(values.size) {
            val result = copy.popRandom()
            assertNotNull(result)
            resultSet.add(result)
        }

        resultSet
    }
    val checkResult: (MutableMultiSet<T>) -> Boolean = { result: MutableMultiSet<T> -> result == values }

    runRandomTest(createResultSet, checkResult)
}
