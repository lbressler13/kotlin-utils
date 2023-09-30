package xyz.lbres.kotlinutils.set.multiset.impl.mutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMutableGetCountOfTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMutableIsEmptyTests
import kotlin.test.assertEquals

fun runMutableIsEmptyTests() {
    runMultiSetMutableIsEmptyTests({ MutableMultiSetImpl(it) }, { MutableMultiSetImpl(it) } )
}

fun runMutableGetCountOfTests() {
    runMultiSetMutableGetCountOfTests({ MutableMultiSetImpl(it) }, { MutableMultiSetImpl(it) } )

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MutableMultiSet<IntList> = mutableMultiSetOf(mutableList1, mutableList2)

    var listValue = listOf(1, 2, 3)
    var expected = 2
    assertEquals(expected, listSet.getCountOf(listValue))

    listValue = listOf(1, 2)
    expected = 0
    assertEquals(expected, listSet.getCountOf(listValue))

    mutableList1.remove(3)

    listValue = listOf(1, 2, 3)
    expected = 1
    assertEquals(expected, listSet.getCountOf(listValue))

    listValue = listOf(1, 2)
    expected = 1
    assertEquals(expected, listSet.getCountOf(listValue))
}
