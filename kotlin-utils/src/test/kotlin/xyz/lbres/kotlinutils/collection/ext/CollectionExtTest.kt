package xyz.lbres.kotlinutils.collection.ext

import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
    internal fun testCountNull() {
        var collection: Collection<Int?> = listOf()
        var expected = 0
        assertEquals(expected, collection.countNull())

        collection = listOf(1, 2, 3, 4, 5)
        expected = 0
        assertEquals(expected, collection.countNull())

        collection = setOf(1, null, 2, 3, 4)
        expected = 1
        assertEquals(expected, collection.countNull())

        collection = listOf(null, null, null, 4, 5, 6, null)
        expected = 4
        assertEquals(expected, collection.countNull())

        collection = listOf(null, null)
        expected = 2
        assertEquals(expected, collection.countNull())
    }

    @Test
    internal fun testCountNotNull() {
        var collection: Collection<Int?> = listOf()
        var expected = 0
        assertEquals(expected, collection.countNotNull())

        collection = listOf(null, null)
        expected = 0
        assertEquals(expected, collection.countNotNull())

        collection = setOf(1, null, 2, 3, 4)
        expected = 4
        assertEquals(expected, collection.countNotNull())

        collection = listOf(null, null, null, 4, 5, 6, null)
        expected = 3
        assertEquals(expected, collection.countNotNull())

        collection = listOf(1, 2, 3, 4, 5)
        expected = 5
        assertEquals(expected, collection.countNotNull())
    }

    @Test
    internal fun testIsNotNullOrEmpty() {
        var collection: Collection<Int?>? = null
        assertFalse(collection.isNotNullOrEmpty())

        collection = setOf()
        assertFalse(collection.isNotNullOrEmpty())

        collection = listOf(1)
        assertTrue(collection.isNotNullOrEmpty())

        collection = listOf(null)
        assertTrue(collection.isNotNullOrEmpty())

        collection = listOf(1, 2, 3)
        assertTrue(collection.isNotNullOrEmpty())
    }
}
