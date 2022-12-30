package xyz.lbres.kotlinutils.set.multiset.consistent

import xyz.lbres.kotlinutils.int.ext.isNegative
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal fun runAnyConsistentTests() {
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))
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
}

internal fun runAllConsistentTests() {
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))
    val intSet = multiSetOf(1, 2, 3, 4, 4, 4)

    // empty
    assertTrue { emptyMultiSet<Int>().allConsistent { true } }

    // none
    assertFalse { intSet.allConsistent(Int::isNegative) }
    assertFalse { intSet.allConsistent { intSet.getCountOf(it) == 2 } }

    assertFalse { listSet.allConsistent { it.contains(6) } }

    // allConsistent
    assertTrue { intSet.allConsistent { true } }
    assertTrue { intSet.allConsistent { it in 1..7 } }

    assertTrue { listSet.allConsistent { it.size < 5 } }

    // some
    assertFalse { intSet.allConsistent { it == 3 } }
    assertFalse { intSet.allConsistent { intSet.getCountOf(it) > 1 } }

    assertFalse { listSet.allConsistent { it.isEmpty() } }
}

internal fun runNoneConsistentTests() {
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))
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
}
