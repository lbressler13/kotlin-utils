package xyz.lbres.kotlinutils.collection.ext

import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CollectionExtTest {
    @Test
    fun testToMultiSet() {
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
    fun testToMutableMultiSet() {
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
    fun testCountNull() {
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
    fun testCountNotNull() {
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
    fun testIsNotNullOrEmpty() {
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

    @Test
    fun testCountElements() {
        var intCollection: Collection<Int> = emptyList()
        assertEquals(0, intCollection.countElement(0))
        assertEquals(0, intCollection.countElement(175))

        intCollection = listOf(7)
        assertEquals(1, intCollection.countElement(7))
        assertEquals(0, intCollection.countElement(8))

        intCollection = listOf(7, 7, 2, 2, 7, 8, 5, 7)
        assertEquals(4, intCollection.countElement(7))
        assertEquals(1, intCollection.countElement(8))

        val mutableList = mutableListOf(7, 8, 9)
        assertEquals(1, mutableList.countElement(7))

        mutableList.addAll(listOf(7, 7))
        assertEquals(3, mutableList.countElement(7))

        mutableList.remove(7)
        assertEquals(2, mutableList.countElement(7))

        val mlist1 = mutableListOf(1, 2, 3)
        val mlist2 = mutableListOf(1, 2, 3)
        val mutablesCollection = listOf(mlist1, mlist2)
        assertEquals(2, mutablesCollection.countElement(listOf(1, 2, 3)))
        assertEquals(0, mutablesCollection.countElement(emptyList()))

        mlist1.clear()
        assertEquals(1, mutablesCollection.countElement(listOf(1, 2, 3)))
        assertEquals(1, mutablesCollection.countElement(emptyList()))

        var nullableList = listOf(1, 2, null, 3, null)
        assertEquals(2, nullableList.countElement(null))

        nullableList = listOf(null, null, null, null)
        assertEquals(4, nullableList.countElement(null))
    }
}
