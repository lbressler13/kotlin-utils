package xyz.lbres.kotlinutils.collection.mutable.ext

import xyz.lbres.kotlinutils.collection.ext.toMutableMultiSet
import xyz.lbres.kotlinutils.list.mutablelist.ext.popRandom
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf
import xyz.lbres.kotlinutils.set.mutableset.ext.popRandom
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class MutableCollectionExtTest {
    @Test
    fun testPopRandom() {
        assertNull(mutableListOf<Int>().popRandom())

        var multiSet = mutableMultiSetOf(1)
        assertEquals(1, multiSet.popRandom())
        assertNull(multiSet.popRandom())
        assertTrue(multiSet.isEmpty())

        multiSet = mutableMultiSetOf(-1, 1, 2, 2, 2, 3, 3)
        runSinglePopRandomTest(multiSet, multiSet.toMutableMultiSet(), mutableMultiSetOf())

        val listMultiSet: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf("7"), listOf("7"), listOf("7"))
        runSinglePopRandomTest(listMultiSet, listMultiSet.toMutableMultiSet(), mutableMultiSetOf())

        var set = mutableSetOf<String>()
        assertNull(set.popRandom())

        set = mutableSetOf("123")
        assertEquals("123", set.popRandom())
        assertNull(set.popRandom())

        set = mutableSetOf("123", "456", "789")
        runSinglePopRandomTest(set, set.toMutableSet(), mutableSetOf()) { coll1, coll2 -> assertEquals(coll1, coll2) }

        val exceptionSet = mutableSetOf(IndexOutOfBoundsException(), ArithmeticException(), ClassCastException())
        runSinglePopRandomTest(exceptionSet, exceptionSet.toMutableSet(), mutableSetOf())

        var list = mutableListOf<String>()
        assertNull(list.popRandom())

        list = mutableListOf("123", "456")
        runSinglePopRandomTest(list, list.toMutableList(), mutableListOf()) { coll1, coll2 -> assertEquals(coll1.sorted(), coll2.sorted()) }

        list = mutableListOf("1", "1", "1", "2")
        runSinglePopRandomTest(list, list.toMutableList(), mutableListOf()) { coll1, coll2 -> assertEquals(coll1.sorted(), coll2.sorted()) }
    }
}

/**
 * Run a single pop random test
 *
 * @param values [MutableCollection]<T>: values in collection
 * @param copy [MutableCollection]<T>: copy of [values]
 * @param results [MutableCollection]<T>: collection to store popped values. Expected to be empty
 * @param equalityAssertion (MutableCollection<T>, MutableCollection<T>) -> Unit: assertion to validate that values and results are equal.
 * Defaults to [assertEquals].
 */
private fun <T> runSinglePopRandomTest(
    values: MutableCollection<T>,
    copy: MutableCollection<T>,
    results: MutableCollection<T>,
    equalityAssertion: (MutableCollection<T>, MutableCollection<T>) -> Unit = { coll1, coll2 -> assertEquals(coll1, coll2) }
) {
    repeat(values.size) {
        val result = copy.popRandom()
        assertNotNull(result)
        results.add(result)
    }

    equalityAssertion(values, results)
    assertNull(copy.popRandom())
}
