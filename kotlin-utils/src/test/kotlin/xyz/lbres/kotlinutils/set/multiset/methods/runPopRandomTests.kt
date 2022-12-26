package xyz.lbres.kotlinutils.set.multiset.methods

import xyz.lbres.kotlinutils.list.mutablelist.ext.popRandom
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
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

// TODO borrow from other pop random functions
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
