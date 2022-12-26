package xyz.lbres.kotlinutils.set.multiset.immutable

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal fun runImmutableRandomTests() {
    assertFailsWith<NoSuchElementException> { emptyMultiSet<Int>().random() }

    var intSet = multiSetOf(1)
    repeat(3) { assertEquals(1, intSet.random()) }

    intSet = multiSetOf(1, 1, 1)
    repeat(5) { assertEquals(1, intSet.random()) }

    intSet = multiSetOf(1, 2, 3)
    runSingleRandomTest(intSet)

    intSet = multiSetOf(-1, 1, 2, 2, 2, 3, 3)
    runSingleRandomTest(intSet)

    val stringSet = multiSetOf("", "", "hello", "world", "goodbye", "", "", "")
    runSingleRandomTest(stringSet)

    val e1 = NullPointerException("Cannot invoke method on null value")
    val e2 = ArithmeticException()
    val e3 = ClassCastException("Cannot cast Int to List")
    val errorSet = multiSetOf(e1, e2, e3, e3)
    runSingleRandomTest(errorSet)

    val listSet = multiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf("7"), listOf("7"), listOf("7"))
    runSingleRandomTest(listSet)
}

private fun <T> runSingleRandomTest(values: MultiSet<T>) {
    val results: MutableMultiSet<T> = mutableMultiSetOf()
    val repeats = 200

    repeat(repeats) {
        results.add(values.random())
    }

    val totalProbabilities = values.size

    val errorThreshold = 15
    for (value in values) {
        val probability = (values.getCountOf(value).toFloat() / totalProbabilities * repeats).toInt()
        val allowedRange = (probability - errorThreshold)..(probability + errorThreshold)
        assertTrue(results.getCountOf(value) in allowedRange)
    }
}
