package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.ext.copyWithoutLast
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.GenericMapFn
import xyz.lbres.kotlinutils.set.multiset.testutils.runCommonMapToSetTests
import kotlin.test.assertContains
import kotlin.test.assertEquals

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")
private val e4 = ClassCastException("other message")

fun runMapToSetTests() {
    val mapFn: GenericMapFn<*, *> = { set, fn ->
        @Suppress(Suppressions.UNCHECKED_CAST)
        set.mapToSet(fn as (Any?) -> Any)
    }
    runCommonMapToSetTests(::MultiSetImpl, mapFn, false)

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet = multiSetOf(mutableList1, mutableList2)
    var expectedInt = multiSetOf(3, 3)
    assertEquals(expectedInt, listSet.mapToSet { it.size })

    mutableList1.remove(2)
    expectedInt = multiSetOf(3, 2)
    assertEquals(expectedInt, listSet.mapToSet { it.size })
}

fun runFilterToSetTests() {
    var intSet = emptyMultiSet<Int>()
    var intExpected = emptyMultiSet<Int>()
    assertEquals(intExpected, intSet.filterToSet { true })

    intSet = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected, intSet.filterToSet { true })

    intExpected = emptyMultiSet()
    assertEquals(intExpected, intSet.filterToSet { false })

    intExpected = multiSetOf(1, 1, 1, 5, 5, 0, 0)
    assertEquals(intExpected, intSet.filterToSet { intSet.getCountOf(it) > 1 })

    intExpected = multiSetOf(1, 1, 1, 3, -1, 6)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected, intSet.filterToSet { it in testInts })

    val stringSet = multiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = multiSetOf("abc", "abc", "hello", "world", "world", "world")
    assertEquals(stringExpected, stringSet.filterToSet { it.length in 3..5 })

    val errorSet: MultiSet<Exception> = multiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: MultiSet<Exception> = multiSetOf(e3, e4, e4)
    assertEquals(errorExpected, errorSet.filterToSet { it is ClassCastException })

    // modified
    intSet = multiSetOf(1, 1, 2, 14, 14)
    intExpected = multiSetOf(1, 2, 14, 14)
    var previousOdd = false
    val intActual = intSet.filterToSet {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }
    assertEquals(intExpected, intActual)

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)
    var listExpected = multiSetOf(listOf(1, 2, 3))
    assertEquals(listExpected, listSet.filterToSet { it.contains(2) })

    mutableList1.remove(2)
    listExpected = emptyMultiSet()
    assertEquals(listExpected, listSet.filterToSet { it.contains(2) })
}

fun runFilterNotToSetTests() {
    var intSet = emptyMultiSet<Int>()
    var intExpected = emptyMultiSet<Int>()
    assertEquals(intExpected, intSet.filterNotToSet { true })

    intSet = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = emptyMultiSet()
    assertEquals(intExpected, intSet.filterNotToSet { true })

    intExpected = multiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected, intSet.filterNotToSet { false })

    intExpected = multiSetOf(2, 3, 4, 6, -1)
    assertEquals(intExpected, intSet.filterNotToSet { intSet.getCountOf(it) > 1 })

    intExpected = multiSetOf(2, 4, 5, 5, 0, 0)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected, intSet.filterNotToSet { it in testInts })

    val stringSet = multiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = multiSetOf("goodbye", "hi")
    assertEquals(stringExpected, stringSet.filterNotToSet { it.length in 3..5 })

    val errorSet: MultiSet<Exception> = multiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: MultiSet<Exception> = multiSetOf(e1, e1, e2)
    assertEquals(errorExpected, errorSet.filterNotToSet { it is ClassCastException })

    // modified
    intSet = multiSetOf(1, 1, 3, 2, 14, 14)
    val intOptions = listOf(multiSetOf(1, 1), multiSetOf(1, 3))
    var previousOdd = false
    val intActual = intSet.filterNotToSet {
        when {
            it % 2 == 0 -> true
            previousOdd -> false
            else -> {
                previousOdd = true
                true
            }
        }
    }
    assertContains(intOptions, intActual)

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)
    var listExpected = multiSetOf(listOf(1, 2, 3))
    assertEquals(listExpected, listSet.filterNotToSet { it.contains(0) })

    mutableList2.add(2)
    listExpected = emptyMultiSet()
    assertEquals(listExpected, listSet.filterNotToSet { it.contains(2) })
}
