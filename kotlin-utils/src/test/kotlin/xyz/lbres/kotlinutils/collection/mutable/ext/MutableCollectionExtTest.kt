package xyz.lbres.kotlinutils.collection.mutable.ext

import xyz.lbres.kotlinutils.collection.ext.toMutableMultiSet
import xyz.lbres.kotlinutils.list.mutablelist.ext.popRandom
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf
import xyz.lbres.kotlinutils.set.mutableset.ext.popRandom
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MutableCollectionExtTest {
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
        runSinglePopRandomTest(set, set.toMutableSet(), mutableSetOf())

        val exceptionSet = mutableSetOf(IndexOutOfBoundsException(), ArithmeticException(), ClassCastException())
        runSinglePopRandomTest(exceptionSet, exceptionSet.toMutableSet(), mutableSetOf())

        var list = mutableListOf<String>()
        assertNull(list.popRandom())

        list = mutableListOf("123", "456")
        runSinglePopRandomTest(list, list.toMutableList(), mutableListOf()) { coll1, coll2 -> assertEquals(coll1.sorted(), coll2.sorted()) }

        list = mutableListOf("1", "1", "1", "2")
        runSinglePopRandomTest(list, list.toMutableList(), mutableListOf()) { coll1, coll2 -> assertEquals(coll1.sorted(), coll2.sorted()) }

        // long seed
        set = mutableSetOf("4", "5", "678")
        var copy = set.toMutableSet()
        var results: MutableSet<String> = mutableSetOf()
        repeat(set.size) {
            val result = copy.popRandom(10L)
            assertNotNull(result)
            results.add(result)
        }

        assertEquals(set, results)
        assertNull(copy.popRandom())

        // int seed
        set = mutableSetOf("4", "5", "678")
        copy = set.toMutableSet()
        results = mutableSetOf()
        repeat(set.size) {
            val result = copy.popRandom(10)
            assertNotNull(result)
            results.add(result)
        }

        assertEquals(set, results)
        assertNull(copy.popRandom())

        // check order
        set = mutableSetOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "17", "17", "20")
        copy = set.toMutableSet()
        val resultsList1: MutableList<String?> = mutableListOf()
        val resultsList2: MutableList<String?> = mutableListOf()
        repeat(set.size) {
            resultsList1.add(copy.popRandom())
        }
        copy = set.toMutableSet()
        repeat(set.size) {
            resultsList2.add(copy.popRandom())
        }
        assertEquals(set, resultsList1.toSet())
        assertEquals(set, resultsList2.toSet())
        assertNotEquals(resultsList1, resultsList2)
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
