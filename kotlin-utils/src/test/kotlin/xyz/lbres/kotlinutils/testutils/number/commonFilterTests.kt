package xyz.lbres.kotlinutils.testutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.runWithFailMessage
import kotlin.test.assertEquals

inline fun <reified S, T> testFilterNotZeroGeneric(createValues: (List<S>) -> T, crossinline filterNotZero: (T) -> List<S>) {
    for (pair in getFilterNotZeroValues<S>()) {
        val values: T = createValues(pair.first)
        val expected: List<S> = pair.second
        runWithFailMessage("Checking ${pair.first} with expected result $expected") {
            assertEquals(expected, filterNotZero(values))
        }
    }
}

@Suppress(Suppressions.UNCHECKED_CAST)
inline fun <reified S> getFilterNotZeroValues(): List<Pair<List<S>, List<S>>> {
    val byteValues = listOf(
        listOf(0, 0, 0) to emptyList(),
        listOf(1, 2, 0, 4, 0, 0, 5) to listOf(1, 2, 4, 5),
        listOf(-1, 1, 0) to listOf(-1, 1),
        listOf(1, 4, 100, 19, 5) to listOf(1, 4, 100, 19, 5)
    )
    val charValues = listOf(
        listOf(0, 0, 0) to emptyList(),
        listOf(1, 2, 0, 4, 0, 0, 5) to listOf(1, 2, 4, 5),
        listOf(-1, 1, 0) to listOf(-1, 1),
        listOf(1, 4, 1000, 19, 5) to listOf(1, 4, 1000, 19, 5),
    )
    val doubleValues = listOf(
        listOf(0.0, -0.0, 0.0) to emptyList(),
        listOf(1.0, 0.2, 0.0, 4.0, 0.0, 0.0, 5.63) to listOf(1.0, 0.2, 4.0, 5.63),
        listOf(-1.0, 1.0, 0.0, -0.0) to listOf(-1.0, 1.0),
        listOf(1.0, 4.0, 1000.0, 19.0, 5.0) to listOf(1.0, 4.0, 1000.0, 19.0, 5.0),
    )
    val floatValues = listOf(
        listOf(0f, -0f, 0f) to emptyList(),
        listOf(1f, 0.2f, 0f, 4f, 0f, 0f, 5.63f) to listOf(1f, 0.2f, 4f, 5.63f),
        listOf(-1f, 1f, 0f, -0f) to listOf(-1f, 1f),
        listOf(1f, 4f, 1000f, 19f, 5f) to listOf(1f, 4f, 1000f, 19f, 5f),
    )
    val intValues = listOf(
        listOf(0, 0, 0) to emptyList(),
        listOf(1, 2, 0, 4, 0, 0, 5) to listOf(1, 2, 4, 5),
        listOf(-1, 1, 0) to listOf(-1, 1),
        listOf(1, 4, 1000, 19, 5) to listOf(1, 4, 1000, 19, 5),
    )
    val longValues = listOf(
        listOf(0, 0, 0) to emptyList(),
        listOf(1, 2, 0, 4, 0, 0, 5) to listOf(1, 2, 4, 5),
        listOf(-1, 1, 0) to listOf(-1, 1),
        listOf(1, 4, 1000, 19, 5) to listOf(1, 4, 1000, 19, 5),
    )
    val shortValues = listOf(
        listOf(0, 0, 0) to emptyList(),
        listOf(1, 2, 0, 4, 0, 0, 5) to listOf(1, 2, 4, 5),
        listOf(-1, 1, 0) to listOf(-1, 1),
        listOf(1, 4, 1000, 19, 5) to listOf(1, 4, 1000, 19, 5),
    )

    val empty: List<Pair<List<S>, List<S>>> = listOf(emptyList<S>() to emptyList())
    return empty + when (S::class) {
        Byte::class -> mapFilterResults(byteValues, Int::toByte)
        Char::class -> mapFilterResults(charValues, Int::toChar)
        Double::class -> doubleValues
        Float::class -> floatValues
        Int::class -> intValues
        Long::class -> mapFilterResults(longValues, Int::toLong)
        Short::class -> mapFilterResults(shortValues, Int::toShort)
        else -> emptyList()
    } as List<Pair<List<S>, List<S>>>
}

fun <T> mapFilterResults(intValues: List<Pair<List<Int>, List<Int>>>, fromInt: (Int) -> T): List<Pair<List<T>, List<T>>> {
    return intValues.map { it.first.map(fromInt) to it.second.map(fromInt) }
}
