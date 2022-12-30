package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.assertEqualsAny
import xyz.lbres.kotlinutils.runTestWithRetry
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal fun runMinByTests() {
    val intSet = multiSetOf(-1, 0, -10, 3, 1, 2, 3, 4, 4, 4)
    val stringSet = multiSetOf("hello", "world", "hi", "bye", "welcome", "planet")
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))

    assertNull(emptyMultiSet<Int>().minByOrNull { it })

    val expectedInt = -10
    var actualInt = intSet.minByOrNull { it }
    assertEquals(expectedInt, actualInt)

    actualInt = intSet.minByOrNull { intSet.getCountOf(it) }
    assertTrue { actualInt in setOf(-10, -1, 0, 1, 2) }

    var expectedString = "hi"
    var actualString = stringSet.minByOrNull { it.length }
    assertEquals(expectedString, actualString)

    expectedString = "bye"
    actualString = stringSet.minByOrNull { it[0] }
    assertEquals(expectedString, actualString)

    val expectedList = listOf(1, 2, 3)
    val actualList = listSet.minByOrNull {
        if (it.isEmpty()) {
            1000
        } else {
            it.last()
        }
    }
    assertEquals(expectedList, actualList)

    // modified
    val modSet = multiSetOf(1, 1, 4, 4, 6, 6, 6, 8, 8, 8)
    runTestWithRetry {
        var pastOdd = false
        val modActual = modSet.minByOrNull {
            when {
                it % 2 == 1 && !pastOdd -> {
                    pastOdd = true
                    1000
                }
                it % 2 == 1 -> -1000
                else -> 0
            }
        }
        assertEquals(1, modActual)
    }
}

internal fun runMaxByTests() {
    val intSet = multiSetOf(-1, 0, -10, 3, 1, 2, 3, 3, 4, 4, 4)
    val stringSet = multiSetOf("hello", "world", "hi", "bye", "welcome", "planet")
    val listSet = multiSetOf(listOf(), listOf(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))

    assertNull(emptyMultiSet<Int>().maxByOrNull { it })

    val expectedInt = 4
    var actualInt = intSet.maxByOrNull { it }
    assertEquals(expectedInt, actualInt)

    actualInt = intSet.maxByOrNull { intSet.getCountOf(it) }
    assertTrue { actualInt in setOf(3, 4) }

    var expectedString = "welcome"
    var actualString = stringSet.maxByOrNull { it.length }
    assertEquals(expectedString, actualString)

    expectedString = "bye"
    actualString = stringSet.maxByOrNull { it[1] }
    assertEquals(expectedString, actualString)

    val expectedList = listOf(5)
    val actualList = listSet.maxByOrNull {
        if (it.isEmpty()) {
            -1000
        } else {
            it.last()
        }
    }
    assertEquals(expectedList, actualList)

    val modSet = multiSetOf(1, 1, 4, 4, 6, 6, 6, 8, 8, 8)
    runTestWithRetry {
        var pastOdd = false
        val modActual = modSet.maxByOrNull {
            when {
                it % 2 == 1 && !pastOdd -> {
                    pastOdd = true
                    -1000
                }
                it % 2 == 1 -> 1000
                else -> 0
            }
        }
        println(modActual)
        assertEquals(1, modActual)
    }
}
