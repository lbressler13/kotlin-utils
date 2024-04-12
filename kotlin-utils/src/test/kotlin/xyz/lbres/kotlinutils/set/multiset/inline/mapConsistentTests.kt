package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.ext.copyWithoutLast
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.runCommonMapToSetConsistentTests
import java.lang.NullPointerException
import kotlin.test.assertContains
import kotlin.test.assertEquals

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")

private val helloWorldMap: (String) -> String = {
    when (it) {
        "hello", "hi" -> "greetings"
        "world" -> "planet"
        "goodbye" -> "farewell"
        else -> "leave this planet"
    }
}
private val shortenListMap: (IntList) -> IntList = {
    simpleIf(it.size > 1, { it.copyWithoutLast() }, { it })
}

fun runMapConsistentTests() {
    var intSet = multiSetOf<Int>()
    var expectedInt = emptyList<Int>()
    assertEquals(expectedInt, intSet.mapConsistent { it * 2 }.sorted())

    intSet = multiSetOf(1, 1, 5, 7, -3, 0, 2, 5)
    expectedInt = listOf(-3, 0, 1, 1, 2, 5, 5, 7)
    assertEquals(expectedInt, intSet.mapConsistent { it }.sorted())

    expectedInt = listOf(-6, 0, 2, 2, 4, 10, 10, 14)
    assertEquals(expectedInt, intSet.mapConsistent { it * 2 }.sorted())

    var expectedString = listOf("-4", "-1", "0", "0", "1", "4", "4", "6")
    assertEquals(expectedString, intSet.mapConsistent { (it - 1).toString() }.sortedBy { it.toInt() })

    var stringSet = multiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    expectedString = listOf("greetings", "planet", "farewell", "planet", "greetings", "greetings", "planet", "leave this planet")
    assertEquals(expectedString.sorted(), stringSet.mapConsistent { helloWorldMap(it) }.sorted())

    var helperString = "1"
    stringSet = multiSetOf("1", "2", "3", "4", "5")
    expectedString = listOf("11", "112", "1113", "11114", "111115")
    val addingMap: (String) -> String = {
        val result = "$helperString$it"
        helperString += "1"
        result
    }
    assertEquals(expectedString.sorted(), stringSet.mapConsistent { addingMap(it) }.sorted())

    expectedString = listOf("", "", "", "", "")
    assertEquals(expectedString, stringSet.mapConsistent { "" })

    stringSet = multiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    expectedInt = listOf(1, 1, 1, 2, 2, 3, 3, 3)
    assertEquals(expectedInt, stringSet.mapConsistent { stringSet.getCountOf(it) }.sorted())

    val errorSet = multiSetOf(e1, e2, e3)
    val expectedStringNull = listOf("Cannot cast Int to List", "Cannot invoke method on null value", null)
    assertEquals(expectedStringNull, errorSet.mapConsistent { it.message }.sortedBy { it ?: "null" })

    var listSet = multiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), emptyList(), listOf(7), listOf(7), listOf(7))
    val expectedList = listOf(emptyList(), listOf(1, 2), listOf(4, 5), listOf(7), listOf(7), listOf(7))
    assertEquals(expectedList, listSet.mapConsistent(shortenListMap).sortedBy { if (it.isEmpty()) 0 else it.first() })

    // modified
    var modString = ""
    intSet = multiSetOf(1, 1, 2, 1, 3)
    val modMap: (Int) -> String = {
        modString += "1"
        modString
    }
    var resultOptions = listOf(
        listOf("1", "1", "1", "11", "111"),
        listOf("1", "11", "11", "11", "111"),
        listOf("1", "11", "111", "111", "111")
    )
    assertContains(resultOptions, intSet.mapConsistent(modMap))

    modString = ""
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    val modMapList: (IntList) -> String = {
        modString += "1"
        modString
    }

    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))
    resultOptions = listOf(
        listOf("1", "1", "11"),
        listOf("1", "11", "11")
    )
    assertContains(resultOptions, listSet.mapConsistent(modMapList))

    modString = ""
    mutableList1.clear()
    resultOptions = listOf(
        listOf("1", "11", "111"),
    )
    assertContains(resultOptions, listSet.mapConsistent(modMapList))

    modString = ""
    mutableList2.clear()
    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))
    resultOptions = listOf(
        listOf("1", "1", "11"),
        listOf("1", "11", "11")
    )
    assertContains(resultOptions, listSet.mapConsistent(modMapList))
}

fun runMapToSetConsistentTests() {
    runCommonMapToSetConsistentTests(::MultiSetImpl, false) { set, fn ->
        @Suppress(Suppressions.UNCHECKED_CAST)
        set.mapToSetConsistent(fn as (Any?) -> Any)
    }

    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(0, 5, 7)
    var modString = ""
    val modMapList: (IntList) -> String = {
        modString += "1"
        modString
    }

    var listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))
    var resultOptions = listOf(
        multiSetOf("1", "1", "11"),
        multiSetOf("1", "11", "11")
    )
    assertContains(resultOptions, listSet.mapToSetConsistent(modMapList))

    modString = ""
    mutableList1.clear()
    resultOptions = listOf(
        multiSetOf("1", "11", "111"),
    )
    assertContains(resultOptions, listSet.mapToSetConsistent(modMapList))

    modString = ""
    mutableList2.clear()
    listSet = multiSetOf(mutableList1, mutableList2, listOf(1, 2, 3))
    resultOptions = listOf(
        multiSetOf("1", "1", "11"),
        multiSetOf("1", "11", "11")
    )
    assertContains(resultOptions, listSet.mapToSetConsistent(modMapList))
}
