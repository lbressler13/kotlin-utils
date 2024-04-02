package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.*
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
    val filterFn: GenericFilterFn<*> = { set, fn ->
        @Suppress(Suppressions.UNCHECKED_CAST)
        set.filterToSet(fn as (Any?) -> Boolean)
    }
    runCommonFilterToSetTests(::MultiSetImpl, filterFn, false)

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
    val filterFn: GenericFilterFn<*> = { set, fn ->
        @Suppress(Suppressions.UNCHECKED_CAST)
        set.filterNotToSet(fn as (Any?) -> Boolean)
    }
    runCommonFilterNotToSetTests(::MultiSetImpl, filterFn, false)

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)
    var listExpected = multiSetOf(listOf(1, 2, 3))
    assertEquals(listExpected, listSet.filterNotToSet { it.contains(0) })

    mutableList2.add(2)
    listExpected = emptyMultiSet()
    assertEquals(listExpected, listSet.filterNotToSet { it.contains(2) })
}
