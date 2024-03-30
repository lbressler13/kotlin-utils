package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.ext.copyWithoutLast
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

// TODO test is const set
fun runMapToConstSetTests() {
    var intSet = constMultiSetOf<Int>()
    var expectedInt = emptyConstMultiSet<Int>()
    assertEquals(expectedInt, intSet.mapToConstSet { it * 2 })

    intSet = constMultiSetOf(1, 1, 5, 7, -3, 0, 2, 5)
    expectedInt = constMultiSetOf(-3, 0, 1, 1, 2, 5, 5, 7)
    assertEquals(expectedInt, intSet.mapToConstSet { it })

    expectedInt = constMultiSetOf(-6, 0, 2, 2, 4, 10, 10, 14)
    assertEquals(expectedInt, intSet.mapToConstSet { it * 2 })

    var expectedString = constMultiSetOf("-4", "-1", "0", "0", "1", "4", "4", "6")
    assertEquals(expectedString, intSet.mapToConstSet { (it - 1).toString() })

    var stringSet = constMultiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    val helloWorldMap: (String) -> String = {
        when (it) {
            "hello", "hi" -> "greetings"
            "world" -> "planet"
            "goodbye" -> "farewell"
            else -> "leave this planet"
        }
    }
    expectedString = constMultiSetOf("greetings", "planet", "farewell", "planet", "greetings", "greetings", "planet", "leave this planet")
    assertEquals(expectedString, stringSet.mapToConstSet { helloWorldMap(it) })

    var helperString = "1"
    stringSet = constMultiSetOf("1", "2", "3", "4", "5")
    expectedString = constMultiSetOf("11", "112", "1113", "11114", "111115")
    val addingMap: (String) -> String = {
        val result = "$helperString$it"
        helperString += "1"
        result
    }
    assertEquals(expectedString, stringSet.mapToConstSet { addingMap(it) })

    expectedString = constMultiSetOf("", "", "", "", "")
    assertEquals(expectedString, stringSet.mapToConstSet { "" })

    stringSet = constMultiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    expectedInt = constMultiSetOf(1, 1, 1, 2, 2, 3, 3, 3)
    assertEquals(expectedInt, stringSet.mapToConstSet { stringSet.getCountOf(it) })

    val errorSet = constMultiSetOf(e1, e2, e3)
    val expectedStringNull = constMultiSetOf("Cannot cast Int to List", "Cannot invoke method on null value", null)
    assertEquals(expectedStringNull, errorSet.mapToConstSet { it.message })

    val listSet = constMultiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), emptyList(), listOf(7), listOf(7), listOf(7))
    val expectedList = constMultiSetOf(emptyList(), listOf(1, 2), listOf(4, 5), listOf(7), listOf(7), listOf(7))
    assertEquals(expectedList, listSet.mapToConstSet(shortenListMap))

    // modified
    var modString = ""
    intSet = constMultiSetOf(1, 1, 2, 1, 3)
    val modMap: (Int) -> String = {
        modString += "1"
        modString
    }
    expectedString = constMultiSetOf("1", "11", "111", "1111", "11111")
    assertEquals(expectedString, intSet.mapToConstSet(modMap))
}

fun runMapToConstSetConsistent() {
    var intSet = constMultiSetOf<Int>()
    var expectedInt = emptyConstMultiSet<Int>()
    assertEquals(expectedInt, intSet.mapToConstSetConsistent { it * 2 })

    intSet = constMultiSetOf(1, 1, 5, 7, -3, 0, 2, 5)
    expectedInt = constMultiSetOf(-3, 0, 1, 1, 2, 5, 5, 7)
    assertEquals(expectedInt, intSet.mapToConstSetConsistent { it })

    expectedInt = constMultiSetOf(-6, 0, 2, 2, 4, 10, 10, 14)
    assertEquals(expectedInt, intSet.mapToConstSetConsistent { it * 2 })

    var expectedString = constMultiSetOf("-4", "-1", "0", "0", "1", "4", "4", "6")
    assertEquals(expectedString, intSet.mapToConstSetConsistent { (it - 1).toString() })

    var stringSet = constMultiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    expectedString = constMultiSetOf("greetings", "planet", "farewell", "planet", "greetings", "greetings", "planet", "leave this planet")
    assertEquals(expectedString, stringSet.mapToConstSetConsistent { helloWorldMap(it) })

    var helperString = "1"
    stringSet = constMultiSetOf("1", "2", "3", "4", "5")
    expectedString = constMultiSetOf("11", "112", "1113", "11114", "111115")
    val addingMap: (String) -> String = {
        val result = "$helperString$it"
        helperString += "1"
        result
    }
    assertEquals(expectedString, stringSet.mapToConstSetConsistent { addingMap(it) })

    expectedString = constMultiSetOf("", "", "", "", "")
    assertEquals(expectedString, stringSet.mapToConstSetConsistent { "" })

    stringSet = constMultiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    expectedInt = constMultiSetOf(1, 1, 1, 2, 2, 3, 3, 3)
    assertEquals(expectedInt, stringSet.mapToConstSetConsistent { stringSet.getCountOf(it) })

    val errorSet = constMultiSetOf(e1, e2, e3)
    val expectedStringNull = constMultiSetOf("Cannot cast Int to List", "Cannot invoke method on null value", null)
    assertEquals(expectedStringNull, errorSet.mapToConstSetConsistent { it.message })

    val listSet = constMultiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), emptyList(), listOf(7), listOf(7), listOf(7))
    val expectedList = constMultiSetOf(emptyList(), listOf(1, 2), listOf(4, 5), listOf(7), listOf(7), listOf(7))
    assertEquals(expectedList, listSet.mapToConstSetConsistent(shortenListMap))

    // modified
    var modString = ""
    intSet = constMultiSetOf(1, 1, 2, 1, 3)
    val modMap: (Int) -> String = {
        modString += "1"
        modString
    }
    val resultOptions = listOf(
        constMultiSetOf("1", "1", "1", "11", "111"),
        constMultiSetOf("1", "11", "11", "11", "111"),
        constMultiSetOf("1", "11", "111", "111", "111")
    )
    assertContains(resultOptions, intSet.mapToConstSetConsistent(modMap))
}
