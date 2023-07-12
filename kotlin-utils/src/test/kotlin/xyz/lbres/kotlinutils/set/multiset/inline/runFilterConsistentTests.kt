package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.assertEqualsAnyOf
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")
private val e4 = ClassCastException("other message")

fun runFilterConsistentTests() {
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
    assertEqualsAnyOf(intOptions, intActual)

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var previous12 = false
    var listActual = listSet.filterConsistent {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            true
        } else {
            previous12 = true
            false
        }
    }
    val listExpected: List<IntList> = emptyList()
    assertEquals(listExpected, listActual)

    mutableList1.add(0)
    previous12 = false
    listActual = listSet.filterConsistent {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            true
        } else {
            previous12 = true
            false
        }
    }
    val listOptions = listOf(listOf(listOf(1, 2, 3)), listOf(listOf(1, 2, 3, 0)))
    assertEqualsAnyOf(listOptions, listActual)
}

fun runFilterNotConsistentTests() {
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
    assertEqualsAnyOf(intOptions, intActual)

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var previous12 = false
    var listActual = listSet.filterNotConsistent {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            false
        } else {
            previous12 = true
            true
        }
    }
    val listExpected: List<IntList> = emptyList()
    assertEquals(listExpected, listActual)

    mutableList1.add(0)
    previous12 = false
    listActual = listSet.filterNotConsistent {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            false
        } else {
            previous12 = true
            true
        }
    }
    val listOptions = listOf(listOf(listOf(1, 2, 3)), listOf(listOf(1, 2, 3, 0)))
    assertEqualsAnyOf(listOptions, listActual)
}

fun runFilterToSetConsistentTests() {
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
    assertEqualsAnyOf(intOptions, intActual)

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var previous12 = false
    var listActual = listSet.filterToSetConsistent {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            true
        } else {
            previous12 = true
            false
        }
    }
    val listExpected: MultiSet<IntList> = emptyMultiSet()
    assertEquals(listExpected, listActual)

    mutableList1.add(0)
    previous12 = false
    listActual = listSet.filterToSetConsistent {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            true
        } else {
            previous12 = true
            false
        }
    }
    val listOptions = listOf(multiSetOf(listOf(1, 2, 3)), multiSetOf(listOf(1, 2, 3, 0)))
    assertEqualsAnyOf(listOptions, listActual)
}

fun runFilterNotToSetConsistentTests() {
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
    assertEqualsAnyOf(intOptions, intActual)

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var previous12 = false
    var listActual = listSet.filterNotToSetConsistent {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            false
        } else {
            previous12 = true
            true
        }
    }
    val listExpected: MultiSet<IntList> = emptyMultiSet()
    assertEquals(listExpected, listActual)

    mutableList1.add(0)
    previous12 = false
    listActual = listSet.filterNotToSetConsistent {
        if (it.containsAll(listOf(1, 2)) && previous12) {
            false
        } else {
            previous12 = true
            true
        }
    }
    val listOptions = listOf(multiSetOf(listOf(1, 2, 3)), multiSetOf(listOf(1, 2, 3, 0)))
    assertEqualsAnyOf(listOptions, listActual)
}
