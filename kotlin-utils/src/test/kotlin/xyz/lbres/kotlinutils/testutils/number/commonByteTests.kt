package xyz.lbres.kotlinutils.testutils.number

import kotlin.test.assertEquals

fun <T> testFilterNotZeroByte(createValues: (List<Byte>) -> T, filterNotZero: (T) -> List<Byte>) {
    var values = createValues(emptyList())
    var expected: List<Byte> = emptyList()
    assertEquals(expected, filterNotZero(values))

    values = createValues(listOf(0, 0, 0))
    expected = emptyList()
    assertEquals(expected, filterNotZero(values))

    values = createValues(listOf(1, 2, 0, 4, 0, 0, 5))
    expected = listOf(1, 2, 4, 5)
    assertEquals(expected, filterNotZero(values))

    values = createValues(listOf(-1, 1, 0))
    expected = listOf(-1, 1)
    assertEquals(expected, filterNotZero(values))

    values = createValues(listOf(1, 4, 100, 19, 5))
    expected = listOf(1, 4, 100, 19, 5)
    assertEquals(expected, filterNotZero(values))
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
