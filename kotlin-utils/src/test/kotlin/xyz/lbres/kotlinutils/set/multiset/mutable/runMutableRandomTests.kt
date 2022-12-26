package xyz.lbres.kotlinutils.set.multiset.mutable

import xyz.lbres.kotlinutils.list.mutablelist.ext.popRandom
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.*

internal fun runMutableRandomTests() {
    assertFailsWith<NoSuchElementException> { mutableMultiSetOf<Int>().random() }

    var intSet = mutableMultiSetOf(1)
    repeat(3) { assertEquals(1, intSet.random()) }

    intSet = mutableMultiSetOf(1, 1, 1)
    repeat(5) { assertEquals(1, intSet.random()) }

    intSet = mutableMultiSetOf(1, 2, 3)
    runSingleRandomTest(intSet)

    intSet = mutableMultiSetOf(-1, 1, 2, 2, 2, 3, 3)
    runSingleRandomTest(intSet)

    val stringSet = mutableMultiSetOf("", "", "hello", "world", "goodbye", "", "", "")
    runSingleRandomTest(stringSet)

    val e1 = NullPointerException("Cannot invoke method on null value")
    val e2 = ArithmeticException()
    val e3 = ClassCastException("Cannot cast Int to List")
    val errorSet = mutableMultiSetOf(e1, e2, e3, e3)
    runSingleRandomTest(errorSet)

    val listSet = mutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf("7"), listOf("7"), listOf("7"))
    runSingleRandomTest(listSet)
}

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
    try {
        runSinglePopRandomTest(intSet)
    } catch (_: Throwable) {
        runSinglePopRandomTest(intSet)
    }

    intSet = mutableMultiSetOf(-1, 1, 2, 2, 2, 3, 3)
    try {
        runSinglePopRandomTest(intSet)
    } catch (_: Throwable) {
        runSinglePopRandomTest(intSet)
    }

    val stringSet = mutableMultiSetOf("", "", "hello", "world", "goodbye", "", "", "")
    try {
        runSinglePopRandomTest(stringSet)
    } catch (_: Throwable) {
        runSinglePopRandomTest(stringSet)
    }

    val e1 = NullPointerException("Cannot invoke method on null value")
    val e2 = ArithmeticException()
    val e3 = ClassCastException("Cannot cast Int to List")
    val errorSet = mutableMultiSetOf(e1, e2, e3, e3)
    try {
        runSinglePopRandomTest(errorSet)
    } catch (_: Throwable) {
        runSinglePopRandomTest(errorSet)
    }

    val listSet = mutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf("7"), listOf("7"), listOf("7"))
    try {
        runSinglePopRandomTest(listSet)
    } catch (_: Throwable) {
        runSinglePopRandomTest(listSet)
    }
}

private fun <T> runSingleRandomTest(values: MutableMultiSet<T>) {
    val results: MutableMultiSet<T> = mutableMultiSetOf()
    val repeats = 200

    repeat(repeats) {
        results.add(values.random())
    }

    val totalProbabilities = values.size

    val errorThreshold = 15
    for (value in values) {
        val probability = (values.getCountOf(value).toFloat() / totalProbabilities* repeats).toInt()
        val allowedRange = (probability - errorThreshold)..(probability + errorThreshold)
        assertTrue(results.getCountOf(value) in allowedRange)
    }
}

private fun <T> runSinglePopRandomTest(values: MutableMultiSet<T>) {
    val initialList = values.toList()
    val list = mutableListOf<T>()
    val repeats = values.size

    repeat(repeats) {
        val value = values.popRandom()!!
        list.add(value)
        println(Pair(value, list))
        println(values.toString())
    }

    assertTrue(values.isEmpty())
    assertNotEquals(initialList, list)
}
