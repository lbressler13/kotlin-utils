package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.ext.copyWithoutLast
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertContains
import kotlin.test.assertEquals

typealias GenericMapFn<S, T> = (MultiSet<S>, (S) -> T) -> MultiSet<T>

private val e1 = NullPointerException("Cannot invoke method on null value")
private val e2 = ArithmeticException()
private val e3 = ClassCastException("Cannot cast Int to List")
private val e4 = ClassCastException("other message")

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

private fun <S, T> runSingleTest(set: MultiSet<S>, expected: MultiSet<T>, const: Boolean, genericMap: GenericMapFn<*, *>, mapFn: (S) -> T) {
    val result = genericMap(set, mapFn)
    assertEquals(expected, result)
    assertEquals(const, result is ConstMultiSet<*>)
}

fun runCommonMapToSetTests(createSet: (Collection<*>) -> MultiSet<*>, genericMap: GenericMapFn<*, *>, const: Boolean) {
    val createIntSet = getCreateSet<Int>(createSet)
    val createStringSet = getCreateSet<String>(createSet)
    val createIntListSet = getCreateSet<IntList>(createSet)
    val createExceptionSet = getCreateSet<Exception>(createSet)

    var intSet = createIntSet(emptyList())
    var expectedInt = emptyMultiSet<Int>()
    runSingleTest(intSet, expectedInt, const, genericMap) { it * 2 }

    intSet = createIntSet(listOf(1, 1, 5, 7, -3, 0, 2, 5))
    expectedInt = multiSetOf(-3, 0, 1, 1, 2, 5, 5, 7)
    runSingleTest(intSet, expectedInt, const, genericMap) { it }

    expectedInt = multiSetOf(-6, 0, 2, 2, 4, 10, 10, 14)
    runSingleTest(intSet, expectedInt, const, genericMap) { it * 2 }

    var expectedString = multiSetOf("-4", "-1", "0", "0", "1", "4", "4", "6")
    runSingleTest(intSet, expectedString, const, genericMap) { (it - 1).toString() }

    var stringSet = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong"))
    expectedString = multiSetOf("greetings", "planet", "farewell", "planet", "greetings", "greetings", "planet", "leave this planet")
    runSingleTest(stringSet, expectedString, const, genericMap, helloWorldMap)

    var helperString = "1"
    stringSet = createStringSet(listOf("1", "2", "3", "4", "5"))
    expectedString = multiSetOf("11", "112", "1113", "11114", "111115")
    val addingMap: (String) -> String = {
        val result = "$helperString$it"
        helperString += "1"
        result
    }
    runSingleTest(stringSet, expectedString, const, genericMap, addingMap)

    expectedString = createStringSet(listOf("", "", "", "", ""))
    runSingleTest(stringSet, expectedString, const, genericMap) { "" }

    stringSet = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong"))
    expectedInt = multiSetOf(1, 1, 1, 2, 2, 3, 3, 3)
    runSingleTest(stringSet, expectedInt, const, genericMap) { stringSet.getCountOf(it) }

    val errorSet = createExceptionSet(listOf(e1, e2, e3))
    val expectedStringNull = multiSetOf("Cannot cast Int to List", "Cannot invoke method on null value", null)
    runSingleTest(errorSet, expectedStringNull, const, genericMap) { it.message }

    val listSet = createIntListSet(listOf(listOf(1, 2, 3), listOf(4, 5, 6), emptyList(), listOf(7), listOf(7), listOf(7)))
    val expectedList = multiSetOf(emptyList(), listOf(1, 2), listOf(4, 5), listOf(7), listOf(7), listOf(7))
    val listMap: (IntList) -> IntList = { simpleIf(it.size > 1, { it.copyWithoutLast() }, { it }) }
    runSingleTest(listSet, expectedList, const, genericMap, listMap)

    // modified
    var modString = ""
    intSet = createIntSet(listOf(1, 1, 2, 1, 3))
    val modMap: (Int) -> String = {
        modString += "1"
        modString
    }
    expectedString = multiSetOf("1", "11", "111", "1111", "11111")
    runSingleTest(intSet, expectedString, const, genericMap, modMap)
}

fun runCommonMapToSetConsistentTests(createSet: (Collection<*>) -> MultiSet<*>, genericMap: GenericMapFn<*, *>, const: Boolean) {
    val createIntSet = getCreateSet<Int>(createSet)
    val createStringSet = getCreateSet<String>(createSet)
    val createIntListSet = getCreateSet<IntList>(createSet)
    val createExceptionSet = getCreateSet<Exception>(createSet)

    var intSet = createIntSet(emptyList())
    var expectedInt = emptyMultiSet<Int>()
    runSingleTest(intSet, expectedInt, const, genericMap) { it * 2 }

    intSet = createIntSet(listOf(1, 1, 5, 7, -3, 0, 2, 5))
    expectedInt = multiSetOf(-3, 0, 1, 1, 2, 5, 5, 7)
    runSingleTest(intSet, expectedInt, const, genericMap) { it }

    expectedInt = multiSetOf(-6, 0, 2, 2, 4, 10, 10, 14)
    runSingleTest(intSet, expectedInt, const, genericMap) { it * 2 }

    var expectedString = multiSetOf("-4", "-1", "0", "0", "1", "4", "4", "6")
    runSingleTest(intSet, expectedString, const, genericMap) { (it - 1).toString() }

    var stringSet = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong"))
    expectedString = multiSetOf("greetings", "planet", "farewell", "planet", "greetings", "greetings", "planet", "leave this planet")
    runSingleTest(stringSet, expectedString, const, genericMap, helloWorldMap)

    var helperString = "1"
    stringSet = createStringSet(listOf("1", "2", "3", "4", "5"))
    expectedString = multiSetOf("11", "112", "1113", "11114", "111115")
    val addingMap: (String) -> String = {
        val result = "$helperString$it"
        helperString += "1"
        result
    }
    runSingleTest(stringSet, expectedString, const, genericMap, addingMap)

    expectedString = multiSetOf("", "", "", "", "")
    runSingleTest(stringSet, expectedString, const, genericMap) { "" }

    stringSet = createStringSet(listOf("hello", "world", "goodbye", "world", "hello", "hi", "world", "wrong"))
    expectedInt = multiSetOf(1, 1, 1, 2, 2, 3, 3, 3)
    runSingleTest(stringSet, expectedInt, const, genericMap) { stringSet.getCountOf(it) }

    val errorSet = createExceptionSet(listOf(e1, e2, e3))
    val expectedStringNull = multiSetOf("Cannot cast Int to List", "Cannot invoke method on null value", null)
    runSingleTest(errorSet, expectedStringNull, const, genericMap) { it.message }

    val listSet = createIntListSet(listOf(listOf(1, 2, 3), listOf(4, 5, 6), emptyList(), listOf(7), listOf(7), listOf(7)))
    val expectedList = multiSetOf(emptyList(), listOf(1, 2), listOf(4, 5), listOf(7), listOf(7), listOf(7))
    runSingleTest(listSet, expectedList, const, genericMap, shortenListMap)

    // modified
    var modString = ""
    intSet = createIntSet(listOf(1, 1, 2, 1, 3))
    val modMap: (Int) -> String = {
        modString += "1"
        modString
    }
    val resultOptions = listOf(
        multiSetOf("1", "1", "1", "11", "111"),
        multiSetOf("1", "11", "11", "11", "111"),
        multiSetOf("1", "11", "111", "111", "111")
    )
    val result = genericMap(intSet, modMap)
    assertContains(resultOptions, result)
    assertEquals(const, result is ConstMultiSet<*>)
}
