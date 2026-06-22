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
    val byteValues = listOf(
        emptyList<Int>() to 0,
        listOf(33) to 33,
        listOf(-33) to -33,
        listOf(5, -5) to 0,
        listOf(-100, 45, -10, 67, -99) to -97,
        listOf(-100, 45, -10, -67, 99) to -33,
    )
    val charValues = listOf(
        emptyList<Int>() to 0,
        listOf(33) to 33,
        listOf(100, 45, 10, 67, 99) to 321,
    )
    val doubleValues: List<Pair<List<Double>, Double>> = listOf()
    val floatValues: List<Pair<List<Float>, Float>> = listOf()
    val intValues = listOf(
        emptyList<Int>() to 0,
        listOf(33) to 33,
        listOf(-33) to -33,
        listOf(5, -5) to 0,
        listOf(100, 45, -10, 67, 99) to 301,
        listOf(-100, 45, -10, -67, 99) to -33,
    )
    val longValues: List<Pair<List<Long>, Long>> = listOf()
    val shortValues = listOf(
        emptyList<Int>() to 0,
        listOf(33) to 33,
        listOf(-33) to -33,
        listOf(5, -5) to 0,
        listOf(100, 45, -10, 67, 99) to 301,
        listOf(-100, 45, -10, -67, 99) to -33,
    )
    return when (S::class) {
        Byte::class -> mapMathResults(byteValues, Int::toByte)
        Char::class -> mapMathResults(charValues, Int::toChar)
        Double::class -> doubleValues
        Float::class -> floatValues
        Int::class -> intValues
        Long::class -> longValues
        Short::class -> mapMathResults(shortValues, Int::toShort)
        else -> emptyList()
    } as List<Pair<List<S>, S>>
}

@Suppress(Suppressions.UNCHECKED_CAST)
inline fun <reified S> getProductValues(): List<Pair<List<S>, S>> {
    val byteValues = listOf(
        emptyList<Int>() to 0,
        listOf(1) to 1,
        listOf(-1) to -1,
        listOf(5, 5, -2, 0) to 0,
        listOf(-3, -7, 2, 2) to 84,
        listOf(-3, -7, -2, 2) to -84,
    )
    val charValues = listOf(
        emptyList<Int>() to 0,
        listOf(0) to 0,
        listOf(1) to 1,
        listOf(4, 4, 0) to 0,
        listOf(15, 23, 4, 4) to 5520,
    )
    val doubleValues: List<Pair<List<Double>, Double>> = listOf()
    val floatValues: List<Pair<List<Float>, Float>> = listOf()
    val intValues = listOf(
        emptyList<Int>() to 0,
        listOf(1) to 1,
        listOf(-1) to -1,
        listOf(5, 5, -2, 0) to 0,
        listOf(-15, 23, 17, 4, 4, -2, 3) to 563040,
        listOf(-15, 23, 17, 4, 4, -2, -3) to -563040,
    )
    val longValues: List<Pair<List<Long>, Long>> = listOf()
    val shortValues = listOf(
        emptyList<Int>() to 0,
        listOf(1) to 1,
        listOf(-1) to -1,
        listOf(5, 5, -2, 0) to 0,
        listOf(-15, 17, 4, 4, -2, 3) to 24480,
        listOf(-15, 17, 4, 4, -2, -3) to -24480,
    )
    return when (S::class) {
        Byte::class -> mapMathResults(byteValues, Int::toByte)
        Char::class -> mapMathResults(charValues, Int::toChar)
        Double::class -> doubleValues
        Float::class -> floatValues
        Int::class -> intValues
        Long::class -> longValues
        Short::class -> mapMathResults(shortValues, Int::toShort)
        else -> emptyList()
    } as List<Pair<List<S>, S>>
}

fun <T> mapMathResults(intValues: List<Pair<List<Int>, Int>>, fromInt: (Int) -> T): List<Pair<List<T>, T>> {
    return intValues.map { it.first.map(fromInt) to fromInt(it.second) }
}
