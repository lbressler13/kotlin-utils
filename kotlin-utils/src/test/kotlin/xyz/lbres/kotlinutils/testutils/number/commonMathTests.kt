package xyz.lbres.kotlinutils.testutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.runWithFailMessage
import kotlin.test.assertEquals

inline fun <reified S, T> testSumGeneric(createValues: (List<S>) -> T, crossinline sum: (T) -> S) {
    for (pair in getSumValues<S>()) {
        val values = createValues(pair.first)
        val expected: S = pair.second
        runWithFailMessage("Checking ${pair.first} with expected result $expected") {
            assertEquals(expected, sum(values))
        }
    }
}

inline fun <reified S, T> testProductGeneric(createValues: (List<S>) -> T, crossinline product: (T) -> S) {
    for (pair in getProductValues<S>()) {
        val values = createValues(pair.first)
        val expected: S = pair.second
        runWithFailMessage("Checking ${pair.first} with expected result $expected") {
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
    val doubleValues = listOf(
        emptyList<Double>() to 0.0,
        listOf(33.0) to 33.0,
        listOf(-33.3) to -33.3,
        listOf(5.0, -5.0) to 0.0,
        listOf(100.2, 0.45, -10.0, 67.0, 9.983) to 167.633,
        listOf(-100.2, -0.45, 10.0, -67.0, -9.983) to -167.633,
    )
    val floatValues = listOf(
        emptyList<Float>() to 0f,
        listOf(33f) to 33f,
        listOf(-33.3f) to -33.3f,
        listOf(5f, -5f) to 0f,
        listOf(100.2f, 0.45f, -10f, 67f, 9.983f) to 167.633f,
        listOf(-100.2f, -0.45f, 10f, -67f, -9.983f) to -167.633f,
    )
    val intValues = listOf(
        emptyList<Int>() to 0,
        listOf(33) to 33,
        listOf(-33) to -33,
        listOf(5, -5) to 0,
        listOf(100, 45, -10, 67, 99) to 301,
        listOf(-100, 45, -10, -67, 99) to -33,
    )
    val longValues = listOf(
        emptyList<Int>() to 0,
        listOf(33) to 33,
        listOf(-33) to -33,
        listOf(5, -5) to 0,
        listOf(100, 45, -10, 67, 99) to 301,
        listOf(-100, 45, -10, -67, 99) to -33,
    )
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
        Long::class -> mapMathResults(longValues, Int::toLong)
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
    val doubleValues = listOf(
        emptyList<Double>() to 0.0,
        listOf(0.0) to 0.0,
        listOf(-0.0) to -0.0,
        listOf(1.0) to 1.0,
        listOf(-1.0) to -1.0,
        listOf(5.3, 5.3, -0.6, 0.0) to -0.0,
        listOf(-5.3, 5.3, -0.6, 0.0) to 0.0,
        listOf(-10.5, 23.0, 17.8, 2.4, 2.4, -13.5, 3.0) to 1002800.736,
        listOf(10.5, 23.0, 17.8, 2.4, 2.4, -13.5, 3.0) to -1002800.736,
    )
    val floatValues = listOf(
        emptyList<Float>() to 0f,
        listOf(0f) to 0f,
        listOf(-0f) to -0f,
        listOf(1f) to 1f,
        listOf(-1f) to -1f,
        listOf(5.3f, 5.3f, -0.6f, 0f) to -0f,
        listOf(-5.3f, 5.3f, -0.6f, 0f) to 0f,
        listOf(10.5f, 23f, 17.8f, 2.4f, 2.4f, -13.5f, 3f) to -1002800.8f,
    )
    val intValues = listOf(
        emptyList<Int>() to 0,
        listOf(1) to 1,
        listOf(-1) to -1,
        listOf(5, 5, -2, 0) to 0,
        listOf(-15, 23, 17, 4, 4, -2, 3) to 563040,
        listOf(-15, 23, 17, 4, 4, -2, -3) to -563040,
    )
    val longValues = listOf(
        emptyList<Int>() to 0,
        listOf(1) to 1,
        listOf(-1) to -1,
        listOf(5, 5, -2, 0) to 0,
        listOf(-15, 23, 17, 4, 4, -2, 3) to 563040,
        listOf(-15, 23, 17, 4, 4, -2, -3) to -563040,
    )
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
        Long::class -> mapMathResults(longValues, Int::toLong)
        Short::class -> mapMathResults(shortValues, Int::toShort)
        else -> emptyList()
    } as List<Pair<List<S>, S>>
}

fun <T> mapMathResults(intValues: List<Pair<List<Int>, Int>>, fromInt: (Int) -> T): List<Pair<List<T>, T>> {
    return intValues.map { it.first.map(fromInt) to fromInt(it.second) }
}
