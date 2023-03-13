package xyz.lbres.kotlinutils.set.multiset.inline

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.ext.copyWithoutLast
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import java.lang.NullPointerException
import kotlin.test.assertEquals

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")

internal fun runMapToSetTests() {
    var intSet = multiSetOf<Int>()
    var expectedInt = emptyMultiSet<Int>()
    assertEquals(expectedInt, intSet.mapToSet { it * 2 })

    intSet = multiSetOf(1, 1, 5, 7, -3, 0, 2, 5)
    expectedInt = multiSetOf(-3, 0, 1, 1, 2, 5, 5, 7)
    assertEquals(expectedInt, intSet.mapToSet { it })

    expectedInt = multiSetOf(-6, 0, 2, 2, 4, 10, 10, 14)
    assertEquals(expectedInt, intSet.mapToSet { it * 2 })

    var expectedString = multiSetOf("-4", "-1", "0", "0", "1", "4", "4", "6")
    assertEquals(expectedString, intSet.mapToSet { (it - 1).toString() })

    var stringSet = multiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    val helloWorldMap: (String) -> String = {
        when (it) {
            "hello", "hi" -> "greetings"
            "world" -> "planet"
            "goodbye" -> "farewell"
            else -> "leave this planet"
        }
    }
    expectedString = multiSetOf("greetings", "planet", "farewell", "planet", "greetings", "greetings", "planet", "leave this planet")
    assertEquals(expectedString, stringSet.mapToSet { helloWorldMap(it) })

    var helperString = "1"
    stringSet = multiSetOf("1", "2", "3", "4", "5")
    expectedString = multiSetOf("11", "112", "1113", "11114", "111115")
    val addingMap: (String) -> String = {
        val result = "$helperString$it"
        helperString += "1"
        result
    }
    assertEquals(expectedString, stringSet.mapToSet { addingMap(it) })

    expectedString = multiSetOf("", "", "", "", "")
    assertEquals(expectedString, stringSet.mapToSet { "" })

    stringSet = multiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    expectedInt = multiSetOf(1, 1, 1, 2, 2, 3, 3, 3)
    assertEquals(expectedInt, stringSet.mapToSet { stringSet.getCountOf(it) })

    val errorSet = multiSetOf(e1, e2, e3)
    val expectedStringNull = multiSetOf("Cannot cast Int to List", "Cannot invoke method on null value", null)
    assertEquals(expectedStringNull, errorSet.mapToSet { it.message })

    val listSet = multiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf(7), listOf(7), listOf(7))
    val expectedList = multiSetOf(listOf(), listOf(1, 2), listOf(4, 5), listOf(7), listOf(7), listOf(7))
    val listMap: (IntList) -> IntList = {
        if (it.size > 1) {
            it.copyWithoutLast()
        } else {
            it
        }
    }
    assertEquals(expectedList, listSet.mapToSet(listMap))

    // modified
    var modString = ""
    intSet = multiSetOf(1, 1, 2, 1, 3)
    val modMap: (Int) -> String = {
        modString += "1"
        modString
    }
    expectedString = multiSetOf("1", "11", "111", "1111", "11111")
    assertEquals(expectedString, intSet.mapToSet(modMap))
}
