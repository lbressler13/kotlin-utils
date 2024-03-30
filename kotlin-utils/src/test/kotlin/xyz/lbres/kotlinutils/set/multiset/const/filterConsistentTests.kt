package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.list.IntList
import kotlin.test.assertContains
import kotlin.test.assertEquals

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")
private val e4 = ClassCastException("other message")

// TODO check is const set

fun runFilterToConstSetConsistentTests() {
    var intSet = emptyConstMultiSet<Int>()
    var intExpected = emptyConstMultiSet<Int>()
    assertEquals(intExpected, intSet.filterToConstSetConsistent { true })

    intSet = constMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = constMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected, intSet.filterToConstSetConsistent { true })

    intExpected = emptyConstMultiSet()
    assertEquals(intExpected, intSet.filterToConstSetConsistent { false })

    intExpected = constMultiSetOf(1, 1, 1, 5, 5, 0, 0)
    assertEquals(intExpected, intSet.filterToConstSetConsistent { intSet.getCountOf(it) > 1 })

    intExpected = constMultiSetOf(1, 1, 1, 3, -1, 6)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected, intSet.filterToConstSetConsistent { it in testInts })

    val stringSet = constMultiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = constMultiSetOf("abc", "abc", "hello", "world", "world", "world")
    assertEquals(stringExpected, stringSet.filterToConstSetConsistent { it.length in 3..5 })

    val errorSet: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: ConstMultiSet<Exception> = constMultiSetOf(e3, e4, e4)
    assertEquals(errorExpected, errorSet.filterToConstSetConsistent { it is ClassCastException })

    // modified
    intSet = constMultiSetOf(1, 1, 3, 2, 14, 14)
    val intOptions = listOf(constMultiSetOf(1, 1, 2, 14, 14), constMultiSetOf(3, 2, 14, 14))
    var previousOdd = false
    val intPredicate: (Int) -> Boolean = {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }
    assertContains(intOptions, intSet.filterToConstSetConsistent(intPredicate))

    val listSet: ConstMultiSet<IntList> = constMultiSetOf(listOf(1, 2, 3), listOf(0, 5, 7), listOf(1, 2, 3))

    var previous12 = false
    val listPredicate: (IntList) -> Boolean = {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            true
        } else {
            previous12 = true
            false
        }
    }
    val listExpected: ConstMultiSet<IntList> = emptyConstMultiSet()
    assertEquals(listExpected, listSet.filterToConstSetConsistent(listPredicate))
}

fun runFilterNotToConstSetConsistentTests() {
    var intSet = emptyConstMultiSet<Int>()
    var intExpected = emptyConstMultiSet<Int>()
    assertEquals(intExpected, intSet.filterNotToConstSetConsistent { true })

    intSet = constMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = emptyConstMultiSet()
    assertEquals(intExpected, intSet.filterNotToConstSetConsistent { true })

    intExpected = constMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected, intSet.filterNotToConstSetConsistent { false })

    intExpected = constMultiSetOf(2, 3, 4, 6, -1)
    assertEquals(intExpected, intSet.filterNotToConstSetConsistent { intSet.getCountOf(it) > 1 })

    intExpected = constMultiSetOf(2, 4, 5, 5, 0, 0)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected, intSet.filterNotToConstSetConsistent { it in testInts })

    val stringSet = constMultiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = constMultiSetOf("goodbye", "hi")
    assertEquals(stringExpected, stringSet.filterNotToConstSetConsistent { it.length in 3..5 })

    val errorSet: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e2)
    assertEquals(errorExpected, errorSet.filterNotToConstSetConsistent { it is ClassCastException })

    // modified
    intSet = constMultiSetOf(1, 1, 3, 2, 14, 14)
    val intOptions = listOf(constMultiSetOf(1, 1), constMultiSetOf(3))
    var previousOdd = false
    val intPredicate: (Int) -> Boolean = {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }
    assertContains(intOptions, intSet.filterNotToConstSetConsistent(intPredicate))

    val listSet: ConstMultiSet<IntList> = constMultiSetOf(listOf(1, 2, 3), listOf(0, 5, 7), listOf(1, 2, 3))

    var previous12 = false
    val listPredicate: (IntList) -> Boolean = {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            false
        } else {
            previous12 = true
            true
        }
    }
    val listExpected: ConstMultiSet<IntList> = emptyConstMultiSet()
    assertEquals(listExpected, listSet.filterNotToConstSetConsistent(listPredicate))
}
