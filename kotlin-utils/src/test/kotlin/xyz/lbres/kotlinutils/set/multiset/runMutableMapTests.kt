package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.ext.copyWithoutLast
import java.lang.NullPointerException
import kotlin.test.assertEquals

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")

internal fun runMutableMapTests() {
    var intSet = mutableMultiSetOf<Int>()
    var expectedInt = mutableMultiSetOf<Int>()
    assertEquals(expectedInt, intSet.map { it * 2 })

    intSet = mutableMultiSetOf(1, 1, 5, 7, -3, 0, 2, 5)
    assertEquals(intSet, intSet.map { it })

    expectedInt = mutableMultiSetOf(2, 2, 10, 14, -6, 0, 4, 10)
    assertEquals(expectedInt, intSet.map { it * 2 })

    var expectedString = mutableMultiSetOf("0", "0", "4", "6", "-4", "-1", "1", "4")
    assertEquals(expectedString, intSet.map { (it - 1).toString() })

    var stringSet = mutableMultiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    val helloWorldMap: (String) -> String = {
        when (it) {
            "hello", "hi" -> "greetings"
            "world" -> "planet"
            "goodbye" -> "farewell"
            else -> "leave this planet"
        }
    }
    expectedString = mutableMultiSetOf("greetings", "planet", "farewell", "planet", "greetings", "greetings", "planet", "leave this planet")
    assertEquals(expectedString, stringSet.map { helloWorldMap(it) })

    var helperString = "1"
    stringSet = mutableMultiSetOf("1", "2", "3", "4", "5")
    expectedString = mutableMultiSetOf("11", "112", "1113", "11114", "111115")
    val addingMap: (String) -> String = {
        val result = "$helperString$it"
        helperString += "1"
        result
    }
    assertEquals(expectedString, stringSet.map { addingMap(it) })

    expectedString = mutableMultiSetOf("", "", "", "", "")
    assertEquals(expectedString, stringSet.map { "" })

    stringSet = mutableMultiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    expectedInt = mutableMultiSetOf(2, 2, 3, 3, 3, 1, 1, 1)
    assertEquals(expectedInt, stringSet.map { stringSet.getCountOf(it) })

    val errorSet = mutableMultiSetOf(e1, e2, e3)
    val expectedStringNull = mutableMultiSetOf("Cannot invoke method on null value", null, "Cannot cast Int to List")
    assertEquals(expectedStringNull, errorSet.map { it.message })

    val listSet = mutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf(7), listOf(7), listOf(7))
    val expectedList = mutableMultiSetOf(listOf(1, 2), listOf(4, 5), listOf(), listOf(7), listOf(7), listOf(7))
    val listMap: (IntList) -> IntList = {
        if (it.size > 1) {
            it.copyWithoutLast()
        } else {
            it
        }
    }
    assertEquals(expectedList, listSet.map(listMap))
}
