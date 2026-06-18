package xyz.lbres.kotlinutils.testutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.runWithFailMessage
import kotlin.test.assertEquals

val byteNotZeroValues: List<Pair<List<Byte>, List<Byte>>> = listOf(
    listOf(0, 0, 0).map(Int::toByte) to emptyList(),
    listOf(1, 2, 0, 4, 0, 0, 5).map(Int::toByte) to listOf(1, 2, 4, 5),
    listOf(-1, 1, 0).map(Int::toByte) to listOf(-1, 1),
    listOf(1, 4, 100, 19, 5).map(Int::toByte) to listOf(1, 4, 100, 19, 5)
)
val charNotZeroValues: List<Pair<List<Char>, List<Char>>> = listOf(
    listOf(0, 0, 0).map(Int::toChar) to emptyList(),
    listOf(1, 2, 0, 4, 0, 0, 5).map(Int::toChar) to listOf(1, 2, 4, 5).map(Int::toChar),
    listOf(-1, 1, 0).map(Int::toChar) to listOf(-1, 1).map(Int::toChar),
    listOf(1, 4, 1000, 19, 5).map(Int::toChar) to listOf(1, 4, 1000, 19, 5).map(Int::toChar),
)
val doubleNotZeroValues: List<Pair<List<Double>, List<Double>>> = listOf(
    listOf(0.0, -0.0, 0.0) to emptyList(),
    listOf(1.0, 0.2, 0.0, 4.0, 0.0, 0.0, 5.63) to listOf(1.0, 0.2, 4.0, 5.63),
    listOf(-1.0, 1.0, 0.0, -0.0) to listOf(-1.0, 1.0),
    listOf(1.0, 4.0, 1000.0, 19.0, 5.0) to listOf(1.0, 4.0, 1000.0, 19.0, 5.0),
)
val floatNotZeroValues: List<Pair<List<Float>, List<Float>>> = listOf(
    listOf(0f, -0f, 0f) to emptyList(),
    listOf(1f, 0.2f, 0f, 4f, 0f, 0f, 5.63f) to listOf(1f, 0.2f, 4f, 5.63f),
    listOf(-1f, 1f, 0f, -0f) to listOf(-1f, 1f),
    listOf(1f, 4f, 1000f, 19f, 5f) to listOf(1f, 4f, 1000f, 19f, 5f),
)
val intNotZeroValues: List<Pair<List<Int>, List<Int>>> = listOf(
    listOf(0, 0, 0) to emptyList(),
    listOf(1, 2, 0, 4, 0, 0, 5) to listOf(1, 2, 4, 5),
    listOf(-1, 1, 0) to listOf(-1, 1),
    listOf(1, 4, 1000, 19, 5) to listOf(1, 4, 1000, 19, 5),
)
val longNotZeroValues: List<Pair<List<Long>, List<Long>>> = listOf(
    listOf(0L, 0L, 0L) to emptyList(),
    listOf(1L, 2L, 0L, 4L, 0L, 0L, 5L) to listOf(1, 2, 4, 5),
    listOf(-1L, 1L, 0L) to listOf(-1, 1),
    listOf(1L, 4L, 1000L, 19L, 5L) to listOf(1, 4, 1000, 19, 5),
)
val shortNotZeroValues: List<Pair<List<Short>, List<Short>>> = listOf(
    listOf(0, 0, 0).map(Int::toShort) to emptyList(),
    listOf(1, 2, 0, 4, 0, 0, 5).map(Int::toShort) to listOf(1, 2, 4, 5),
    listOf(-1, 1, 0).map(Int::toShort) to listOf(-1, 1),
    listOf(1, 4, 1000, 19, 5).map(Int::toShort) to listOf(1, 4, 1000, 19, 5),
)

@Suppress(Suppressions.UNCHECKED_CAST)
inline fun <reified S, T> testFilterNotZeroGeneric(createValues: (List<S>) -> T, crossinline filterNotZero: (T) -> List<S>) {
    var notZeroValues: List<Pair<List<S>, List<S>>> = listOf(emptyList<S>() to emptyList())
    notZeroValues = notZeroValues + when (S::class) {
        Byte::class -> byteNotZeroValues
        Char::class -> charNotZeroValues
        Double::class -> doubleNotZeroValues
        Float::class -> floatNotZeroValues
        Int::class -> intNotZeroValues
        Long::class -> longNotZeroValues
        Short::class -> shortNotZeroValues
        else -> emptyList()
    } as List<Pair<List<S>, List<S>>>

    for (pair in notZeroValues) {
        val values: T = createValues(pair.first)
        val expected: List<S> = pair.second
        runWithFailMessage("Checking $values with expected result $expected") {
            assertEquals(expected, filterNotZero(values))
        }
    }
}
