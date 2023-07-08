package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.assertEqualsAnyOf
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

fun runMinByConsistentTests() {
    val intSet = multiSetOf(-1, 0, -10, 3, 1, 2, 3, 4, 4, 4)
    val stringSet = multiSetOf("hello", "world", "hi", "bye", "welcome", "planet")
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))

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
}

fun runMaxByConsistentTests() {
    val intSet = multiSetOf(-1, 0, -10, 3, 1, 2, 3, 3, 4, 4, 4)
    val stringSet = multiSetOf("hello", "world", "hi", "bye", "welcome", "planet")
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))

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
}
