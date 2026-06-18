package xyz.lbres.kotlinutils.testutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.runWithFailMessage
import kotlin.test.assertEquals

val byteNotZeroValues: List<Pair<List<Byte>, List<Byte>>> = listOf(
    emptyList<Byte>() to emptyList(),
    listOf(0, 0, 0).map(Int::toByte) to emptyList(),
    listOf(1, 2, 0, 4, 0, 0, 5).map(Int::toByte) to listOf(1, 2, 4, 5),
    listOf(-1, 1, 0).map(Int::toByte) to listOf(-1, 1),
    listOf(1, 4, 100, 19, 5).map(Int::toByte) to listOf(1, 4, 100, 19, 5)
)

@Suppress(Suppressions.UNCHECKED_CAST)
inline fun <reified S, T> testFilterNotZeroGeneric(createValues: (List<S>) -> T, crossinline filterNotZero: (T) -> List<S>) {
    val notZeroValues: List<Pair<List<S>, List<S>>> = when (S::class) {
        Byte::class -> byteNotZeroValues
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

fun <T> testSumByte(createValues: (List<Byte>) -> T, sum: (T) -> Byte) {
    var values = createValues(emptyList())
    var expected: Byte = 0
    assertEquals(expected, sum(values))

    values = createValues(listOf(33))
    expected = 33
    assertEquals(expected, sum(values))

    values = createValues(listOf(-33))
    expected = -33
    assertEquals(expected, sum(values))

    values = createValues(listOf(5, -5))
    expected = 0
    assertEquals(expected, sum(values))

    values = createValues(listOf(-100, 45, -10, 67, -99))
    expected = -97
    assertEquals(expected, sum(values))

    values = createValues(listOf(-100, 45, -10, -67, 99))
    expected = -33
    assertEquals(expected, sum(values))
}

fun <T> testProductByte(createValues: (List<Byte>) -> T, product: (T) -> Byte) {
    var values = createValues(emptyList())
    var expected: Byte = 0
    assertEquals(expected, product(values))

    values = createValues(listOf(0))
    expected = 0
    assertEquals(expected, product(values))

    values = createValues(listOf(1))
    expected = 1
    assertEquals(expected, product(values))

    values = createValues(listOf(-1))
    expected = -1
    assertEquals(expected, product(values))

    values = createValues(listOf(5, 5, -2, 0))
    expected = 0
    assertEquals(expected, product(values))

    values = createValues(listOf(-3, -7, 2, 2))
    expected = 84
    assertEquals(expected, product(values))

    values = createValues(listOf(-3, -7, -2, 2))
    expected = -84
    assertEquals(expected, product(values))
}
