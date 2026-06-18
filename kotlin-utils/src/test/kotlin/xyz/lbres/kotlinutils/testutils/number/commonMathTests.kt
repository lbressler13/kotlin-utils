package xyz.lbres.kotlinutils.testutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.runWithFailMessage
import kotlin.test.assertEquals

inline fun <reified S, T> testSumGeneric(createValues: (List<S>) -> T, crossinline sum: (T) -> S) {
    for (pair in getSumValues<S>()) {
        val values = createValues(pair.first)
        val expected: S = pair.second
        runWithFailMessage("Checking $values with expected result $expected") {
            assertEquals(expected, sum(values))
        }
    }
}

inline fun <reified S, T> testProductGeneric(createValues: (List<S>) -> T, crossinline product: (T) -> S) {
    for (pair in getProductValues<S>()) {
        val values = createValues(pair.first)
        val expected: S = pair.second
        runWithFailMessage("Checking $values with expected result $expected") {
            assertEquals(expected, product(values))
        }
    }
}

@Suppress(Suppressions.UNCHECKED_CAST)
inline fun <reified S> getSumValues(): List<Pair<List<S>, S>> {
    val byteValues: List<Pair<List<Byte>, Byte>> = listOf(
        emptyList<Byte>() to 0,
        listOf(33).map(Int::toByte) to 33,
        listOf(-33).map(Int::toByte) to -33,
        listOf(5, -5).map(Int::toByte) to 0,
        listOf(-100, 45, -10, 67, -99).map(Int::toByte) to -97,
        listOf(-100, 45, -10, -67, 99).map(Int::toByte) to -33,
    )
    val charValues: List<Pair<List<Char>, Char>> = listOf(
        emptyList<Char>() to Char(0),
        listOf(Char(33)) to Char(33),
        listOf(100, 45, 10, 67, 99).map(Int::toChar) to Char(321),
    )
    val doubleValues: List<Pair<List<Double>, Double>> = listOf()
    val floatValues: List<Pair<List<Float>, Float>> = listOf()
    val intValues: List<Pair<List<Int>, Int>> = listOf()
    val longValues: List<Pair<List<Long>, Long>> = listOf()
    val shortValues: List<Pair<List<Short>, Short>> = listOf()
    return when (S::class) {
        Byte::class -> byteValues
        Char::class -> charValues
        Double::class -> doubleValues
        Float::class -> floatValues
        Int::class -> intValues
        Long::class -> longValues
        Short::class -> shortValues
        else -> emptyList()
    } as List<Pair<List<S>, S>>
}

@Suppress(Suppressions.UNCHECKED_CAST)
inline fun <reified S> getProductValues(): List<Pair<List<S>, S>> {
    val byteValues: List<Pair<List<Byte>, Byte>> = listOf(
        emptyList<Byte>() to 0,
        listOf(1).map(Int::toByte) to 1,
        listOf(-1).map(Int::toByte) to -1,
        listOf(5, 5, -2, 0).map(Int::toByte) to 0,
        listOf(-3, -7, 2, 2).map(Int::toByte) to 84,
        listOf(-3, -7, -2, 2).map(Int::toByte) to -84,
    )
    val charValues: List<Pair<List<Char>, Char>> = listOf(
        emptyList<Char>() to Char(0),
        listOf(Char(0)) to Char(0),
        listOf(Char(1)) to Char(1),
        listOf(4, 4, 0).map(Int::toChar) to Char(0),
        listOf(15, 23, 4, 4).map(Int::toChar) to Char(5520)
    )
    val doubleValues: List<Pair<List<Double>, Double>> = listOf()
    val floatValues: List<Pair<List<Float>, Float>> = listOf()
    val intValues: List<Pair<List<Int>, Int>> = listOf()
    val longValues: List<Pair<List<Long>, Long>> = listOf()
    val shortValues: List<Pair<List<Short>, Short>> = listOf()
    return when (S::class) {
        Byte::class -> byteValues
        Char::class -> charValues
        Double::class -> doubleValues
        Float::class -> floatValues
        Int::class -> intValues
        Long::class -> longValues
        Short::class -> shortValues
        else -> emptyList()
    } as List<Pair<List<S>, S>>
}
