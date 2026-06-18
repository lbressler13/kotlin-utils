package xyz.lbres.kotlinutils.testutils.number

// inline fun <reified S, T> testOpGeneric(createValues: (List<S>) -> T, crossinline op: (T) -> S) {
//    for (pair in getSumValues<S>()) {
//        val values = createValues(pair.first)
//        val expected: S = pair.second
//        runWithFailMessage("Checking $values with expected result $expected") {
//            assertEquals(expected, op(values))
//        }
//    }
// }
//
// @Suppress(Suppressions.UNCHECKED_CAST)
// inline fun <reified S> getSumValues(): List<Pair<List<S>, S>> {
//    val byteValues: List<Pair<List<Byte>, Byte>> = listOf(
//
//    )
//    val charValues: List<Pair<List<Char>, Char>> = listOf(
//
//    )
//    val doubleValues: List<Pair<List<Double>, Double>> = listOf(
//
//    )
//    val floatValues: List<Pair<List<Float>, Float>> = listOf(
//
//    )
//    val intValues: List<Pair<List<Int>, Int>> = listOf(
//
//    )
//    val longValues: List<Pair<List<Long>, Long>> = listOf(
//
//    )
//    val shortValues: List<Pair<List<Short>, Short>> = listOf(
//
//    )
//    return when (S::class) {
//        Byte::class -> byteValues
//        Char::class -> charValues
//        Double::class -> doubleValues
//        Float::class -> floatValues
//        Int::class -> intValues
//        Long::class -> longValues
//        Short::class -> shortValues
//        else -> emptyList()
//    } as List<Pair<List<S>, S>>
// }
