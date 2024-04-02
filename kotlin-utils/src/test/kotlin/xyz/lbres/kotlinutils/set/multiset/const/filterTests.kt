package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.list.IntList
import kotlin.test.assertContains
import kotlin.test.assertEquals

// TODO check is const set

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")
private val e4 = ClassCastException("other message")

fun runFilterNotToConstSetTests() {
    var intSet = emptyConstMultiSet<Int>()
    var intExpected = emptyConstMultiSet<Int>()
    assertEquals(intExpected, intSet.filterNotToConstSet { true })

    intSet = constMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    intExpected = emptyConstMultiSet()
    assertEquals(intExpected, intSet.filterNotToConstSet { true })

    intExpected = constMultiSetOf(1, 1, 1, 2, 3, 4, 5, 5, 6, -1, 0, 0)
    assertEquals(intExpected, intSet.filterNotToConstSet { false })

    intExpected = constMultiSetOf(2, 3, 4, 6, -1)
    assertEquals(intExpected, intSet.filterNotToConstSet { intSet.getCountOf(it) > 1 })

    intExpected = constMultiSetOf(2, 4, 5, 5, 0, 0)
    val testInts = setOf(-1, 1, 11, 12, 10, 3, 6, -2)
    assertEquals(intExpected, intSet.filterNotToConstSet { it in testInts })

    val stringSet = constMultiSetOf("abc", "abc", "hello", "world", "goodbye", "world", "hi", "world")
    val stringExpected = constMultiSetOf("goodbye", "hi")
    assertEquals(stringExpected, stringSet.filterNotToConstSet { it.length in 3..5 })

    val errorSet: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e2, e3, e4, e4)
    val errorExpected: ConstMultiSet<Exception> = constMultiSetOf(e1, e1, e2)
    assertEquals(errorExpected, errorSet.filterNotToConstSet { it is ClassCastException })

    // modified
    intSet = constMultiSetOf(1, 1, 3, 2, 14, 14)
    val intOptions = listOf(constMultiSetOf(1, 1), constMultiSetOf(1, 3))
    var previousOdd = false
    val intActual = intSet.filterNotToConstSet {
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

    val listSet: ConstMultiSet<IntList> = constMultiSetOf(listOf(1, 2, 3), listOf(0, 5, 6))
    val listExpected = constMultiSetOf(listOf(1, 2, 3))
    assertEquals(listExpected, listSet.filterNotToConstSet { it.contains(0) })
}
