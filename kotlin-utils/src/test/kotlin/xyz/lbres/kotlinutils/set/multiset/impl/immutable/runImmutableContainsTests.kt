package xyz.lbres.kotlinutils.set.multiset.impl.immutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetContainsAllTests
import xyz.lbres.kotlinutils.set.multiset.testutils.runMultiSetContainsTests
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runImmutableContainsTests() {
    runMultiSetContainsTests({ MultiSetImpl(it) }, { MultiSetImpl(it) }, { MultiSetImpl(it) })

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet = multiSetOf(mutableList1, mutableList2)
    assertFalse(listSet.contains(listOf(1, 2)))

    mutableList1.remove(3)
    assertTrue(listSet.contains(listOf(1, 2)))
    assertTrue(listSet.contains(listOf(1, 2, 3)))

    mutableList2.add(0)
    assertFalse(listSet.contains(listOf(1, 2, 3)))
}

fun runImmutableContainsAllTests() {
    runMultiSetContainsAllTests { MultiSetImpl(it) }

    // mutable elements
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)
    assertFalse(listSet.containsAll(listOf(listOf(1, 2))))

    mutableList1.remove(3)
    assertTrue(listSet.containsAll(listOf(listOf(1, 2), listOf(1, 2, 3))))

    mutableList2.add(0)
    assertFalse(listSet.containsAll(listOf(listOf(1, 2, 3))))
}
