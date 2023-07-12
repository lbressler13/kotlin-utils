package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.int.ext.isNegative
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runAnyConsistentTests() {
    var listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))
    val intSet = multiSetOf(1, 2, 3, 4, 4, 4)

    // empty
    assertFalse { emptyMultiSet<Int>().anyConsistent { true } }

    // none
    assertFalse { intSet.anyConsistent(Int::isNegative) }
    assertFalse { intSet.anyConsistent { intSet.getCountOf(it) == 2 } }

    assertFalse { listSet.anyConsistent { it.contains(6) } }

    // all
    assertTrue { intSet.anyConsistent { true } }
    assertTrue { intSet.anyConsistent { it in 1..7 } }

    assertTrue { listSet.anyConsistent { it.size < 5 } }

    // some
    assertTrue { intSet.anyConsistent { it == 3 } }
    assertTrue { intSet.anyConsistent { intSet.getCountOf(it) > 1 } }

    assertTrue { listSet.anyConsistent { it.isEmpty() } }

    // modified
    var previous4 = false
    val modifiedFn: (Int) -> Boolean = {
        when {
            it == 4 && !previous4 -> {
                previous4 = true
                false
            }
            it == 4 -> true
            else -> false
        }
    }
    assertFalse(intSet.anyConsistent(modifiedFn))
    previous4 = false
    assertTrue(intSet.any(modifiedFn))

    // mutable list
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))
    var previous12 = false
    val modifiedFnList: (IntList) -> Boolean = {
        when {
            it.containsAll(listOf(1, 2)) && !previous12 -> {
                previous12 = true
                false
            }
            it.containsAll(listOf(1, 2)) -> true
            else -> false
        }
    }
    assertFalse(listSet.anyConsistent(modifiedFnList))
    previous12 = false
    assertTrue(listSet.any(modifiedFnList))

    mutableList1.add(0)
    previous12 = false
    assertTrue(listSet.anyConsistent(modifiedFnList))
}

fun runAllConsistentTests() {
    var listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))
    val intSet = multiSetOf(1, 2, 3, 4, 4, 4)

    // empty
    assertTrue { emptyMultiSet<Int>().allConsistent { true } }

    // none
    assertFalse { intSet.allConsistent(Int::isNegative) }
    assertFalse { intSet.allConsistent { intSet.getCountOf(it) == 2 } }

    assertFalse { listSet.allConsistent { it.contains(6) } }

    // all
    assertTrue { intSet.allConsistent { true } }
    assertTrue { intSet.allConsistent { it in 1..7 } }

    assertTrue { listSet.allConsistent { it.size < 5 } }

    // some
    assertFalse { intSet.allConsistent { it == 3 } }
    assertFalse { intSet.allConsistent { intSet.getCountOf(it) > 1 } }

    assertFalse { listSet.allConsistent { it.isEmpty() } }

    // modified
    var no4 = true
    val modifiedFn: (Int) -> Boolean = {
        when {
            it == 4 && no4 -> {
                no4 = false
                true
            }
            it == 4 -> false
            else -> true
        }
    }
    assertTrue(intSet.allConsistent(modifiedFn))
    no4 = true
    assertFalse(intSet.all(modifiedFn))

    // mutable list
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))
    var no123 = true
    val modifiedFnList: (IntList) -> Boolean = {
        when {
            it == listOf(1, 2, 3) && no123 -> {
                no123 = false
                true
            }
            it == listOf(1, 2, 3) -> false
            else -> true
        }
    }
    assertTrue(listSet.allConsistent(modifiedFnList))
    no123 = true
    assertFalse(listSet.all(modifiedFnList))

    mutableList1.add(0)
    no123 = false
    assertFalse(listSet.allConsistent(modifiedFnList))
}

fun runNoneConsistentTests() {
    var listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))
    val intSet = multiSetOf(1, 2, 3, 4, 4, 4)

    // empty
    assertTrue { emptyMultiSet<Int>().noneConsistent { true } }

    // noneConsistent
    assertTrue { intSet.noneConsistent(Int::isNegative) }
    assertTrue { intSet.noneConsistent { intSet.getCountOf(it) == 2 } }

    assertTrue { listSet.noneConsistent { it.contains(6) } }

    // noneConsistent
    assertFalse { intSet.noneConsistent { true } }
    assertFalse { intSet.noneConsistent { it in 1..7 } }

    assertFalse { listSet.noneConsistent { it.size < 5 } }

    // some
    assertFalse { intSet.noneConsistent { it == 3 } }
    assertFalse { intSet.noneConsistent { intSet.getCountOf(it) > 1 } }

    assertFalse { listSet.noneConsistent { it.isEmpty() } }

    // modified
    var previous4 = false
    val modifiedFn: (Int) -> Boolean = {
        when {
            it == 4 && !previous4 -> {
                previous4 = true
                false
            }
            it == 4 -> true
            else -> false
        }
    }
    assertTrue(intSet.noneConsistent(modifiedFn))
    assertFalse(intSet.none(modifiedFn))

    // mutable list
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))
    var previous12 = false
    val listFn: (IntList) -> Boolean = {
        when {
            it.containsAll(listOf(1, 2)) && !previous12 -> {
                previous12 = true
                false
            }
            it.containsAll(listOf(1, 2)) -> true
            else -> false
        }
    }
    assertTrue(listSet.noneConsistent(listFn))
    previous12 = false
    assertFalse(listSet.none(listFn))

    mutableList1.add(0)
    previous12 = false
    assertFalse(listSet.noneConsistent(listFn))
}
