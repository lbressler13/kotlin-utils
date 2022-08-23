package kotlinutils.collection.ext

import kotlinutils.classes.multiset.MultiSet
import kotlinutils.classes.multiset.MutableMultiSet
import kotlinutils.classes.multiset.multiSetOf
import kotlinutils.classes.multiset.mutableMultiSetOf
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CollectionExtTest {
    @Test
    internal fun testToMultiSet() {
        var collection: Collection<Int> = listOf()
        var expected: MultiSet<Int> = multiSetOf()
        assertEquals(expected, collection.toMultiSet())

        expected = multiSetOf(1, 2, 3)

        collection = listOf(1, 2, 3)
        assertEquals(expected, collection.toMultiSet())

        collection = mutableSetOf(1, 2, 3)
        assertEquals(expected, collection.toMultiSet())

        collection = mutableListOf(1, 2, 3)
        assertEquals(expected, collection.toMultiSet())

        collection = listOf(1, 1, 2, 4)
        expected = multiSetOf(1, 2, 1, 4)
        assertEquals(expected, collection.toMultiSet())

        val listCollection = listOf(listOf(), listOf(1, 2), listOf(10000))
        val listExpected = multiSetOf(listOf(), listOf(1, 2), listOf(10000))
        assertEquals(listExpected, listCollection.toMultiSet())
    }

    @Test
    internal fun testToMutableMultiSet() {
        var collection: Collection<Int> = listOf()
        var expected: MutableMultiSet<Int> = mutableMultiSetOf()
        assertEquals(expected, collection.toMutableMultiSet())

        expected = mutableMultiSetOf(1, 2, 3)

        collection = listOf(1, 2, 3)
        assertEquals(expected, collection.toMutableMultiSet())

        collection = mutableSetOf(1, 2, 3)
        assertEquals(expected, collection.toMutableMultiSet())

        collection = mutableListOf(1, 2, 3)
        assertEquals(expected, collection.toMutableMultiSet())

        collection = listOf(1, 1, 2, 4)
        expected = mutableMultiSetOf(1, 2, 1, 4)
        assertEquals(expected, collection.toMutableMultiSet())

        val listCollection = listOf(listOf(), listOf(1, 2), listOf(10000))
        val listExpected = mutableMultiSetOf(listOf(), listOf(1, 2), listOf(10000))
        assertEquals(listExpected, listCollection.toMutableMultiSet())
    }

    @Test
    internal fun testFilterNotZero() {
        var list: List<Int> = listOf()
        var expected: List<Int> = listOf()
        assertEquals(expected, list.filterNotZero())

        list = listOf(0, 0, 0)
        expected = listOf()
        assertEquals(expected, list.filterNotZero())

        list = listOf(1, 2, 0, 4, 0, 0, 5)
        expected = listOf(1, 2, 4, 5)
        assertEquals(expected, list.filterNotZero())

        list = listOf(-1, 1, 0)
        expected = listOf(-1, 1)
        assertEquals(expected, list.filterNotZero())

        list = listOf(1, 4, 1000, 19, 5)
        expected = listOf(1, 4, 1000, 19, 5)
        assertEquals(expected, list.filterNotZero())
    }
}
