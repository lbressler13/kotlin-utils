package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.assertEqualsAnyOf
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

fun runMinByConsistentTests() {
    val intSet = multiSetOf(-1, 0, -10, 3, 1, 2, 3, 4, 4, 4)
    val stringSet = multiSetOf("hello", "world", "hi", "bye", "welcome", "planet")
    var listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))

    assertNull(emptyMultiSet<Int>().minByOrNullConsistent { it })

    val expectedInt = -10
    var actualInt = intSet.minByOrNullConsistent { it }
    assertEquals(expectedInt, actualInt)

    actualInt = intSet.minByOrNullConsistent { intSet.getCountOf(it) }
    assertTrue { actualInt in setOf(-10, -1, 0, 1, 2) }

    var expectedString = "hi"
    var actualString = stringSet.minByOrNullConsistent { it.length }
    assertEquals(expectedString, actualString)

    expectedString = "bye"
    actualString = stringSet.minByOrNullConsistent { it[0] }
    assertEquals(expectedString, actualString)

    val expectedList = listOf(1, 2, 3)
    val actualList = listSet.minByOrNullConsistent {
        if (it.isEmpty()) {
            1000
        } else {
            it.last()
        }
    }
    assertEquals(expectedList, actualList)

    // modified
    val modSet = multiSetOf(1, 1, 4, 4, 6, 6, 6, 8, 8, 8)
    var pastOdd = false
    val modActual = modSet.minByOrNullConsistent {
        when {
            it % 2 == 1 && !pastOdd -> {
                pastOdd = true
                1000
            }
            it % 2 == 1 -> -1000
            else -> 0
        }
    }
    assertEqualsAnyOf(listOf(4, 6, 8), modActual)

    // mutable list
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var past12 = false
    var listActual = listSet.minByOrNullConsistent {
        when {
            it.containsAll(listOf(1, 2)) && !past12 -> {
                past12 = true
                1000
            }
            it.containsAll(listOf(1, 2)) -> -1000
            else -> 0
        }
    }
    assertEquals(listOf(0, 5, 7), listActual)

    mutableList1.add(0)
    past12 = false
    listActual = listSet.minByOrNullConsistent {
        when {
            it.containsAll(listOf(1, 2)) && !past12 -> {
                past12 = true
                1000
            }
            it.containsAll(listOf(1, 2)) -> -1000
            else -> 0
        }
    }
    assertEqualsAnyOf(listOf(listOf(1, 2, 3), listOf(1, 2, 3, 0)), listActual)
}

fun runMaxByConsistentTests() {
    val intSet = multiSetOf(-1, 0, -10, 3, 1, 2, 3, 3, 4, 4, 4)
    val stringSet = multiSetOf("hello", "world", "hi", "bye", "welcome", "planet")
    var listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))

    assertNull(emptyMultiSet<Int>().maxByOrNullConsistent { it })

    val expectedInt = 4
    var actualInt = intSet.maxByOrNullConsistent { it }
    assertEquals(expectedInt, actualInt)

    actualInt = intSet.maxByOrNullConsistent { intSet.getCountOf(it) }
    assertTrue { actualInt in setOf(3, 4) }

    var expectedString = "welcome"
    var actualString = stringSet.maxByOrNullConsistent { it.length }
    assertEquals(expectedString, actualString)

    expectedString = "bye"
    actualString = stringSet.maxByOrNullConsistent { it[1] }
    assertEquals(expectedString, actualString)

    val expectedList = listOf(5)
    val actualList = listSet.maxByOrNullConsistent {
        if (it.isEmpty()) {
            -1000
        } else {
            it.last()
        }
    }
    assertEquals(expectedList, actualList)

    // modified
    val modSet = multiSetOf(1, 1, 4, 4, 6, 6, 6, 8, 8, 8)
    var pastOdd = false
    val modActual = modSet.maxByOrNullConsistent {
        when {
            it % 2 == 1 && !pastOdd -> {
                pastOdd = true
                -1000
            }
            it % 2 == 1 -> 1000
            else -> 0
        }
    }
    assertEqualsAnyOf(listOf(4, 6, 8), modActual)

    // mutable list
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var past12 = false
    var listActual = listSet.maxByOrNullConsistent {
        when {
            it.containsAll(listOf(1, 2)) && !past12 -> {
                past12 = true
                -1000
            }
            it.containsAll(listOf(1, 2)) -> 1000
            else -> 0
        }
    }
    assertEquals(listOf(0, 5, 7), listActual)

    mutableList1.add(0)
    past12 = false
    listActual = listSet.maxByOrNullConsistent {
        when {
            it.containsAll(listOf(1, 2)) && !past12 -> {
                past12 = true
                -1000
            }
            it.containsAll(listOf(1, 2)) -> 1000
            else -> 0
        }
    }
    assertEqualsAnyOf(listOf(listOf(1, 2, 3), listOf(1, 2, 3, 0)), listActual)
}
