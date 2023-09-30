package xyz.lbres.kotlinutils.set.multiset.impl.mutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImplTest
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMutableContainsAllTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetMutableContainsTests
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runMutableContainsTests() {
    runMultiSetMutableContainsTests({ MutableMultiSetImpl(it) }, { MutableMultiSetImpl(it) }, { MutableMultiSetImpl(it) })

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet = mutableMultiSetOf(mutableList1, mutableList2)
    assertFalse(listSet.contains(listOf(1, 2)))

    mutableList1.remove(3)
    assertTrue(listSet.contains(listOf(1, 2)))
    assertTrue(listSet.contains(listOf(1, 2, 3)))

    mutableList2.add(0)
    assertFalse(listSet.contains(listOf(1, 2, 3)))
}

fun runMutableContainsAllTests() {
    runMultiSetMutableContainsAllTests { MutableMultiSetImpl(it) }

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MutableMultiSet<IntList> = mutableMultiSetOf(mutableList1, mutableList2)
    assertFalse(listSet.containsAll(listOf(listOf(1, 2))))

    mutableList1.remove(3)
    assertTrue(listSet.containsAll(listOf(listOf(1, 2))))
    assertTrue(listSet.containsAll(listOf(listOf(1, 2, 3))))

    mutableList2.add(0)
    assertFalse(listSet.containsAll(listOf(listOf(1, 2, 3))))
}
