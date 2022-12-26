package xyz.lbres.kotlinutils.set.multiset.methods

import xyz.lbres.kotlinutils.int.ext.isNegative
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal fun runAnyTests() {
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))
    val intSet = multiSetOf(1, 2, 3, 4, 4, 4)

    // none
    assertFalse { emptyMultiSet<Int>().any { true } }

    assertFalse { intSet.any(Int::isNegative) }
    assertFalse { intSet.any { intSet.getCountOf(it) == 2 } }

    assertFalse { listSet.any { it.contains(6) } }

    // all
    assertTrue { intSet.any { true } }
    assertTrue { intSet.any { it in 1..7 } }

    assertTrue { listSet.any { it.size < 5 } }

    // some
    assertTrue { intSet.any { it == 3 } }
    assertTrue { intSet.any { intSet.getCountOf(it) > 1 } }

    assertTrue { listSet.any { it.isEmpty() } }
}

internal fun runAllTests() {
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))
    val intSet = multiSetOf(1, 2, 3, 4, 4, 4)

    // none
    assertTrue { emptyMultiSet<Int>().all { true } }

    assertFalse { intSet.all(Int::isNegative) }
    assertFalse { intSet.all { intSet.getCountOf(it) == 2 } }

    assertFalse { listSet.all { it.contains(6) } }

    // all
    assertTrue { intSet.all { true } }
    assertTrue { intSet.all { it in 1..7 } }

    assertTrue { listSet.all { it.size < 5 } }

    // some
    assertFalse { intSet.all { it == 3 } }
    assertFalse { intSet.all { intSet.getCountOf(it) > 1 } }

    assertFalse { listSet.all { it.isEmpty() } }
}

internal fun runNoneTests() {
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))
    val intSet = multiSetOf(1, 2, 3, 4, 4, 4)

    // none
    assertTrue { emptyMultiSet<Int>().none { true } }

    assertTrue { intSet.none(Int::isNegative) }
    assertTrue { intSet.none { intSet.getCountOf(it) == 2 } }

    assertTrue { listSet.none { it.contains(6) } }

    // none
    assertFalse { intSet.none { true } }
    assertFalse { intSet.none { it in 1..7 } }

    assertFalse { listSet.none { it.size < 5 } }

    // some
    assertFalse { intSet.none { it == 3 } }
    assertFalse { intSet.none { intSet.getCountOf(it) > 1 } }

    assertFalse { listSet.none { it.isEmpty() } }
}
