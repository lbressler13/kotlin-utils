package xyz.lbres.kotlinutils.set.multiset.mutable

import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")
private val e4 = ClassCastException("other message")

internal fun runMutableFilterTests() {
    var intSet = mutableMultiSetOf<Int>()
    var intExpected = mutableMultiSetOf<Int>()
    assertEquals(intExpected, intSet.filter { true })

    intSet = mutableMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = mutableMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected, intSet.filter { true })

    intExpected = mutableMultiSetOf()
    assertEquals(intExpected, intSet.filter { false })

    intExpected = mutableMultiSetOf(1, 1, 1, 5, 5, 0, 0)
    assertEquals(intExpected, intSet.filter { intSet.getCountOf(it) > 1 })

    intExpected = mutableMultiSetOf(1, 1, 1, 3, -1, 6)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected, intSet.filter { it in testInts })

    val stringSet = mutableMultiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = mutableMultiSetOf("abc", "abc", "hello", "world", "world", "world")
    assertEquals(stringExpected, stringSet.filter { it.length in 3..5 })

    val errorSet: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: MutableMultiSet<Exception> = mutableMultiSetOf(e3, e4, e4)
    assertEquals(errorExpected, errorSet.filter { it is ClassCastException })
}

internal fun runMutableFilterNotTests() {
    var intSet = mutableMultiSetOf<Int>()
    var intExpected = mutableMultiSetOf<Int>()
    assertEquals(intExpected, intSet.filterNot { true })

    intSet = mutableMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = mutableMultiSetOf()
    assertEquals(intExpected, intSet.filterNot { true })

    intExpected = mutableMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected, intSet.filterNot { false })

    intExpected = mutableMultiSetOf(2, 3, 4, 6, -1)
    assertEquals(intExpected, intSet.filterNot { intSet.getCountOf(it) > 1 })

    intExpected = mutableMultiSetOf(2, 4, 5, 5, 0, 0)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected, intSet.filterNot { it in testInts })

    val stringSet = mutableMultiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = mutableMultiSetOf("goodbye", "hi")
    assertEquals(stringExpected, stringSet.filterNot { it.length in 3..5 })

    val errorSet: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: MutableMultiSet<Exception> = mutableMultiSetOf(e1, e1, e2)
    assertEquals(errorExpected, errorSet.filterNot { it is ClassCastException })
}
