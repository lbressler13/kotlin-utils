package xyz.lbres.kotlinutils.set.multiset.immutable

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.ext.copyWithoutLast
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import java.lang.NullPointerException
import kotlin.math.pow
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")

internal fun runImmutableMapTests() {
    var intSet = multiSetOf<Int>()
    var expectedInt = emptyList<Int>()
    assertEquals(expectedInt, intSet.map { it * 2 }.sorted())

    intSet = multiSetOf(1, 1, 5, 7, -3, 0, 2, 5)
    expectedInt = listOf(-3, 0, 1, 1, 2, 5, 5, 7)
    assertEquals(expectedInt, intSet.map { it }.sorted())

    expectedInt = listOf(-6, 0, 2, 2, 4, 10, 10, 14)
    assertEquals(expectedInt, intSet.map { it * 2 }.sorted())

    var expectedString = listOf("-4", "-1", "0", "0", "1", "4", "4", "6")
    assertEquals(expectedString, intSet.map { (it - 1).toString() }.sortedBy { it.toInt() })

    var stringSet = multiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    val helloWorldMap: (String) -> String = {
        when (it) {
            "hello", "hi" -> "greetings"
            "world" -> "planet"
            "goodbye" -> "farewell"
            else -> "leave this planet"
        }
    }
    expectedString = listOf("greetings", "planet", "farewell", "planet", "greetings", "greetings", "planet", "leave this planet")
    assertEquals(expectedString.sorted(), stringSet.map { helloWorldMap(it) }.sorted())

    var helperString = "1"
    stringSet = multiSetOf("1", "2", "3", "4", "5")
    expectedString = listOf("11", "112", "1113", "11114", "111115")
    val addingMap: (String) -> String = {
        val result = "$helperString$it"
        helperString += "1"
        result
    }
    assertEquals(expectedString.sorted(), stringSet.map { addingMap(it) }.sorted())

    expectedString = listOf("", "", "", "", "")
    assertEquals(expectedString, stringSet.map { "" })

    stringSet = multiSetOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong")
    expectedInt = listOf(1, 1, 1, 2, 2, 3, 3, 3)
    assertEquals(expectedInt, stringSet.map { stringSet.getCountOf(it) }.sorted())

    val errorSet = multiSetOf(e1, e2, e3)
    val expectedStringNull = listOf("Cannot cast Int to List", "Cannot invoke method on null value", null)
    assertEquals(expectedStringNull, errorSet.map { it.message }.sortedBy { it ?: "null" })

    val listSet = multiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf(7), listOf(7), listOf(7))
    val expectedList = listOf(listOf(), listOf(1, 2), listOf(4, 5), listOf(7), listOf(7), listOf(7))
    val listMap: (IntList) -> IntList = {
        if (it.size > 1) {
            it.copyWithoutLast()
        } else {
            it
        }
    }
    assertEquals(expectedList, listSet.map(listMap).sortedBy { if (it.isEmpty()) 0 else it.first() })
}

internal fun runImmutableFoldTests() {
    assertFailsWith<ArithmeticException> { multiSetOf(1, 2, 0).fold(1, Int::div) }

    var intSet = emptyMultiSet<Int>()
    var intExpected = 0
    assertEquals(intExpected, intSet.fold(0, Int::plus))

    intExpected = 10
    assertEquals(intExpected, intSet.fold(10, Int::plus))

    intSet = multiSetOf(1, 1, 1, 2, 2, 3, 4, 5)
    intExpected = 0
    assertEquals(intExpected, intSet.fold(0, Int::times))

    intExpected = 720
    assertEquals(intExpected, intSet.fold(3, Int::times))

    var stringExpected = "011122345"
    var foldedString = intSet.fold("0") { acc, int -> acc + int.toString() }
    foldedString = foldedString.toCharArray().sorted().joinToString("")
    assertEquals(stringExpected, foldedString)

    var stringSet = multiSetOf("abc", "ab", "nop", "def", "hijk", "lm", "lm", "lm", "nop")
    stringExpected = "aadhlllnn"
    foldedString = stringSet.fold("") { acc, string -> string[0] + acc }
    foldedString = foldedString.toCharArray().sorted().joinToString("")
    assertEquals(stringExpected, foldedString)

    stringSet = multiSetOf("abc", "abc", "de")
    intExpected = 262144
    assertEquals(intExpected, stringSet.fold(2) { acc, string -> acc.toDouble().pow(string.length).toInt() })

    stringExpected = "123123123"
    assertEquals(stringExpected, stringSet.fold("") { acc, _ -> acc + "123" })

    val msSet = multiSetOf(multiSetOf(1, 2, 3), multiSetOf(1, 2, 3), emptyMultiSet(), multiSetOf(9), multiSetOf(3, 3, 9, 4), emptyMultiSet())
    val msExpected = multiSetOf(1, 2, 3, 1, 2, 3, 9, 3, 3, 9, 4)
    assertEquals(msExpected, msSet.fold(emptyMultiSet()) { acc, set -> acc + set })
}
