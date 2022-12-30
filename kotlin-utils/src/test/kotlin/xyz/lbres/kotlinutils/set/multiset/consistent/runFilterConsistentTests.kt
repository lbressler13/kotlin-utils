package xyz.lbres.kotlinutils.set.multiset.consistent

import xyz.lbres.kotlinutils.assertEqualsAny
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")
private val e4 = ClassCastException("other message")

internal fun runFilterConsistentTests() {
    var intSet = emptyMultiSet<Int>()
    var intExpected = emptyList<Int>()
    assertEquals(intExpected, intSet.filterConsistent { true })

    intSet = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = listOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected.sorted(), intSet.filterConsistent { true }.sorted())

    intExpected = emptyList()
    assertEquals(intExpected, intSet.filterConsistent { false })

    intExpected = listOf(1, 1, 1, 5, 5, 0, 0)
    assertEquals(intExpected.sorted(), intSet.filterConsistent { intSet.getCountOf(it) > 1 }.sorted())

    intExpected = listOf(1, 1, 1, 3, -1, 6)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected.sorted(), intSet.filterConsistent { it in testInts }.sorted())

    val stringSet = multiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = listOf("abc", "abc", "hello", "world", "world", "world")
    assertEquals(stringExpected.sorted(), stringSet.filterConsistent { it.length in 3..5 }.sorted())

    val errorSet: MultiSet<Exception> = multiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: List<Exception> = listOf(e3, e4, e4)
    assertEquals(errorExpected, errorSet.filterConsistent { it is ClassCastException }.sortedBy { it.message!! })

    // modified
    intSet = multiSetOf(1, 1, 3, 2, 14, 14)
    val intOptions = listOf(listOf(1, 1, 2, 14, 14), listOf(3, 2, 14, 14))
    var previousOdd = false
    val intActual = intSet.filterConsistent {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }.sorted()
    assertEqualsAny(intActual, intOptions)
}

internal fun runFilterNotConsistentTests() {
    var intSet = emptyMultiSet<Int>()
    var intExpected = emptyList<Int>()
    assertEquals(intExpected, intSet.filterNotConsistent { true })

    intSet = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = emptyList()
    assertEquals(intExpected, intSet.filterNotConsistent { true })

    intExpected = listOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected.sorted(), intSet.filterNotConsistent { false }.sorted())

    intExpected = listOf(2, 3, 4, 6, -1)
    assertEquals(intExpected.sorted(), intSet.filterNotConsistent { intSet.getCountOf(it) > 1 }.sorted())

    intExpected = listOf(2, 4, 5, 5, 0, 0)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected.sorted(), intSet.filterNotConsistent { it in testInts }.sorted())

    val stringSet = multiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = listOf("goodbye", "hi")
    assertEquals(stringExpected, stringSet.filterNotConsistent { it.length in 3..5 }.sorted())

    val errorSet: MultiSet<Exception> = multiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: List<Exception> = listOf(e1, e1, e2)
    assertEquals(errorExpected, errorSet.filterNotConsistent { it is ClassCastException }.sortedBy { it.message ?: "ZYX" })

    // modified
    intSet = multiSetOf(1, 1, 3, 2, 14, 14)
    val intOptions = listOf(listOf(1, 1), listOf(3))
    var previousOdd = false
    val intActual = intSet.filterNotConsistent {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }.sorted()
    assertEqualsAny(intActual, intOptions)
}

internal fun runFilterToSetConsistentTests() {
    var intSet = emptyMultiSet<Int>()
    var intExpected = emptyMultiSet<Int>()
    assertEquals(intExpected, intSet.filterToSetConsistent { true })

    intSet = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected, intSet.filterToSetConsistent { true })

    intExpected = emptyMultiSet()
    assertEquals(intExpected, intSet.filterToSetConsistent { false })

    intExpected = multiSetOf(1, 1, 1, 5, 5, 0, 0)
    assertEquals(intExpected, intSet.filterToSetConsistent { intSet.getCountOf(it) > 1 })

    intExpected = multiSetOf(1, 1, 1, 3, -1, 6)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected, intSet.filterToSetConsistent { it in testInts })

    val stringSet = multiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = multiSetOf("abc", "abc", "hello", "world", "world", "world")
    assertEquals(stringExpected, stringSet.filterToSetConsistent { it.length in 3..5 })

    val errorSet: MultiSet<Exception> = multiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: MultiSet<Exception> = multiSetOf(e3, e4, e4)
    assertEquals(errorExpected, errorSet.filterToSetConsistent { it is ClassCastException })

    // modified
    intSet = multiSetOf(1, 1, 3, 2, 14, 14)
    val intOptions = listOf(multiSetOf(1, 1, 2, 14, 14), multiSetOf(3, 2, 14, 14))
    var previousOdd = false
    val intActual = intSet.filterToSetConsistent {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }
    assertEqualsAny(intActual, intOptions)
}

internal fun runFilterNotToSetConsistentTests() {
    var intSet = emptyMultiSet<Int>()
    var intExpected = emptyMultiSet<Int>()
    assertEquals(intExpected, intSet.filterNotToSetConsistent { true })

    intSet = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = emptyMultiSet()
    assertEquals(intExpected, intSet.filterNotToSetConsistent { true })

    intExpected = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected, intSet.filterNotToSetConsistent { false })

    intExpected = multiSetOf(2, 3, 4, 6, -1)
    assertEquals(intExpected, intSet.filterNotToSetConsistent { intSet.getCountOf(it) > 1 })

    intExpected = multiSetOf(2, 4, 5, 5, 0, 0)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected, intSet.filterNotToSetConsistent { it in testInts })

    val stringSet = multiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = multiSetOf("goodbye", "hi")
    assertEquals(stringExpected, stringSet.filterNotToSetConsistent { it.length in 3..5 })

    val errorSet: MultiSet<Exception> = multiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: MultiSet<Exception> = multiSetOf(e1, e1, e2)
    assertEquals(errorExpected, errorSet.filterNotToSetConsistent { it is ClassCastException })

    // modified
    intSet = multiSetOf(1, 1, 3, 2, 14, 14)
    val intOptions = listOf(multiSetOf(1, 1), multiSetOf(3))
    var previousOdd = false
    val intActual = intSet.filterNotToSetConsistent {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }
    assertEqualsAny(intActual, intOptions)
}
