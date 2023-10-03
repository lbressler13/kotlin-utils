package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import kotlin.test.assertEquals

private val e1 = ArithmeticException()
private val e2 = NullPointerException()
private val e3 = ArithmeticException()

val multiSetConstructorIntTestValues = listOf(
    mapOf("values" to emptyList<Int>(), "size" to 0, "distinct" to emptySet<Int>()),
    mapOf("values" to setOf(-12), "size" to 1, "distinct" to setOf(-12)),
    mapOf("values" to mutableListOf(10, 10, 10, 10), "size" to 4, "distinct" to setOf(10)),
    mapOf("values" to listOf(-12, 18, 4, 10000, 25, 25, -1, 0, 5, 25), "size" to 10, "distinct" to setOf(-12, 18, 4, 10000, 25, -1, 0, 5)),
)

val multiSetConstructorExceptionTestValues = listOf(
    mapOf("values" to mutableListOf(e1, e2, e1, e3), "size" to 4, "distinct" to setOf(e1, e2, e3)),
)

val multiSetConstructorIntListTestValues = listOf(
    mapOf(
        "values" to mutableSetOf(listOf(1, 3, 4), listOf(55, 66, 77)),
        "size" to 2,
        "distinct" to setOf(listOf(1, 3, 4), listOf(55, 66, 77))
    )
)

val multiSetConstructorCompListTestValues = listOf(
    mapOf(
        "values" to listOf(listOf(1, 2, 3), listOf("abc", "def"), listOf("abc", "def")),
        "size" to 3,
        "distinct" to setOf(listOf(1, 2, 3), listOf("abc", "def"))
    )
)

@Suppress(Suppressions.UNCHECKED_CAST)
fun <T> testConstructedMultiSet(constructedSet: MultiSet<T>, map: Map<String, Any>) {
    val expectedSize: Int = map["size"] as Int
    val expectedDistinct: Set<T> = map["distinct"] as Set<T>

    assertEquals(expectedSize, constructedSet.size)
    assertEquals(expectedDistinct, constructedSet.distinctValues)
}

fun testConstructorWithMutableElements(testConstructor: (Map<String, Any>) -> Unit) {
    val mutableList1 = mutableListOf(1, 2, 3)
    val mutableList2 = mutableListOf(1, 2, 3)
    val values = listOf(mutableList1, mutableList2, listOf(1, 2, 3))

    var map = mapOf("values" to values, "size" to 3, "distinct" to setOf(listOf(1, 2, 3)))
    testConstructor(map)

    mutableList1.clear()
    mutableList2.add(4)
    map = mapOf("values" to values, "size" to 3, "distinct" to setOf(emptyList(), listOf(1, 2, 3), listOf(1, 2, 3, 4)))
    testConstructor(map)
}
