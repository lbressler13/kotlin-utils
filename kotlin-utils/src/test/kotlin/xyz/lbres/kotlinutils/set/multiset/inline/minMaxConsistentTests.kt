package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.int.ext.isZero
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

fun runMinByConsistentTests() {
    val intSet = multiSetOf(-1, 0, -10, 3, 1, 2, 3, 4, 4, 4)
    val stringSet = multiSetOf("hello", "world", "hi", "bye", "welcome", "planet")
    var listSet = multiSetOf(emptyList(), emptyList(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))

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
    var listSelector: (IntList) -> Int = {
        if (it.isEmpty()) {
            1000
        } else {
            it.last()
        }
    }
    assertEquals(expectedList, listSet.minByOrNullConsistent(listSelector))

    // modified
    val modSet = multiSetOf(1, 1, 4, 4, 6, 6, 6, 8, 8, 8)
    var pastOdd = false
    val intSelector: (Int) -> Int = {
        when {
            it % 2 == 1 && !pastOdd -> {
                pastOdd = true
                1000
            }
            it % 2 == 1 -> -1000
            else -> 0
        }
    }
    assertContains(listOf(4, 6, 8), modSet.minByOrNullConsistent(intSelector))

    // mutable list
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var past12 = false
    listSelector = {
        when {
            it.containsAll(listOf(1, 2)) && !past12 -> {
                past12 = true
                1000
            }
            it.containsAll(listOf(1, 2)) -> -1000
            else -> 0
        }
    }
    assertEquals(listOf(0, 5, 7), listSet.minByOrNullConsistent(listSelector))

    mutableList1.add(0)
    past12 = false
    assertContains(listOf(listOf(1, 2, 3), listOf(1, 2, 3, 0)), listSet.minByOrNullConsistent(listSelector))
}

fun runMaxByConsistentTests() {
    val intSet = multiSetOf(-1, 0, -10, 3, 1, 2, 3, 3, 4, 4, 4)
    val stringSet = multiSetOf("hello", "world", "hi", "bye", "welcome", "planet")
    var listSet = multiSetOf(emptyList(), emptyList(), listOf(1, 2, 3, 4), listOf(1, 2, 3), listOf(5))

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
    var listSelector: (IntList) -> Int = {
        if (it.isEmpty()) {
            -1000
        } else {
            it.last()
        }
    }
    assertEquals(expectedList, listSet.maxByOrNullConsistent(listSelector))

    // modified
    val modSet = multiSetOf(1, 1, 4, 4, 6, 6, 6, 8, 8, 8)
    var pastOdd = false
    val intSelector: (Int) -> Int = {
        when {
            it % 2 == 1 && !pastOdd -> {
                pastOdd = true
                -1000
            }
            it % 2 == 1 -> 1000
            else -> 0
        }
    }
    assertContains(listOf(4, 6, 8), modSet.maxByOrNullConsistent(intSelector))

    // mutable list
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var past12 = false
    listSelector = {
        when {
            it.containsAll(listOf(1, 2)) && !past12 -> {
                past12 = true
                -1000
            }
            it.containsAll(listOf(1, 2)) -> 1000
            else -> 0
        }
    }
    assertEquals(listOf(0, 5, 7), listSet.maxByOrNullConsistent(listSelector))

    mutableList1.add(0)
    past12 = false
    assertContains(listOf(listOf(1, 2, 3), listOf(1, 2, 3, 0)), listSet.maxByOrNullConsistent(listSelector))
}

fun runCountConsistentTests() {
    // empty set
    var intSet: MultiSet<Int> = multiSetOf()
    assertEquals(0, intSet.countConsistent(Int::isZero))

    // none match
    intSet = multiSetOf(1, 2, -1)
    assertEquals(0, intSet.countConsistent(Int::isZero))

    var listSet = multiSetOf(listOf(1, 2), listOf("hello", 123), listOf(-.5))
    assertEquals(0, listSet.countConsistent(List<*>::isEmpty))

    // all match
    intSet = multiSetOf(0, 0, 0)
    assertEquals(3, intSet.countConsistent(Int::isZero))

    listSet = multiSetOf(listOf(1, 2), listOf("hello", 123), listOf(-.5))
    assertEquals(3, listSet.countConsistent(List<*>::isNotEmpty))

    // some match
    intSet = multiSetOf(-1, 0, 0)
    assertEquals(2, intSet.countConsistent(Int::isZero))

    intSet = multiSetOf(1, -1, 9, 8, 5, 5, 2, 3, 8, 5, -2)
    assertEquals(5, intSet.countConsistent { intSet.getCountOf(it) > 1 })

    listSet = multiSetOf(listOf(1, 2), emptyList(), listOf(-.5))
    assertEquals(1, listSet.countConsistent(List<*>::isEmpty))

    // modified
    var include = true
    intSet = multiSetOf(1, 1, 4, 5, 1, 4)
    val predicate: (Int) -> Boolean = {
        val result = include
        include = !include
        result
    }
    val resultOptions = listOf(3, 4, 5)
    assertContains(resultOptions, intSet.countConsistent(predicate))
}
