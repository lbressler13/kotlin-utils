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
val intNotZeroValues: List<Pair<List<Int>, List<Int>>> = listOf(
    listOf(0, 0, 0) to emptyList(),
    listOf(1, 2, 0, 4, 0, 0, 5) to listOf(1, 2, 4, 5),
    listOf(-1, 1, 0) to listOf(-1, 1),
    listOf(1, 4, 1000, 19, 5) to listOf(1, 4, 1000, 19, 5),
)

@Suppress(Suppressions.UNCHECKED_CAST)
inline fun <reified S, T> testFilterNotZeroGeneric(createValues: (List<S>) -> T, crossinline filterNotZero: (T) -> List<S>) {
    var notZeroValues: List<Pair<List<S>, List<S>>> = listOf(emptyList<S>() to emptyList())
    notZeroValues = notZeroValues + when (S::class) {
        Byte::class -> byteNotZeroValues
        Int::class -> intNotZeroValues
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
