package xyz.lbres.kotlinutils.set.multiset.impl.immutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetGetCountOfTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetIsEmptyTests
import kotlin.test.assertEquals

fun runImmutableIsEmptyTests() {
    runMultiSetIsEmptyTests({ MultiSetImpl(it) }, { MultiSetImpl(it)} )
}

fun runImmutableGetCountOfTests() {
    runMultiSetGetCountOfTests({ MultiSetImpl(it) }, { MultiSetImpl(it)} )

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)

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
