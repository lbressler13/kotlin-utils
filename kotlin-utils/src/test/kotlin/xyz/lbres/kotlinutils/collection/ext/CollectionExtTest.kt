package xyz.lbres.kotlinutils.collection.ext

import xyz.lbres.kotlinutils.assertEmpty
import xyz.lbres.kotlinutils.int.ext.isZero
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.StringList
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.constMutableMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf
import kotlin.math.max
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class CollectionExtTest {
    @Test
    fun testToMultiSet() {
        var collection: Collection<Int> = emptyList()
        var expected: MultiSet<Int> = emptyMultiSet()
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

        val listCollection = listOf(emptyList(), listOf(1, 2), listOf(10000))
        val listExpected = multiSetOf(emptyList(), listOf(1, 2), listOf(10000))
        assertEquals(listExpected, listCollection.toMultiSet())
    }

    @Test
    fun testToMutableMultiSet() {
        var collection: Collection<Int> = emptyList()
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

        val listCollection = listOf(emptyList(), listOf(1, 2), listOf(10000))
        val listExpected = mutableMultiSetOf(emptyList(), listOf(1, 2), listOf(10000))
        assertEquals(listExpected, listCollection.toMutableMultiSet())
    }

    @Test
    fun testToConstMultiSet() {
        var collection: Collection<Int> = emptyList()
        var expected: ConstMultiSet<Int> = emptyConstMultiSet()
        assertEquals(expected, collection.toConstMultiSet())

        expected = constMultiSetOf(1, 2, 3)

        collection = listOf(1, 2, 3)
        assertEquals(expected, collection.toConstMultiSet())

        collection = mutableSetOf(1, 2, 3)
        assertEquals(expected, collection.toConstMultiSet())

        collection = mutableListOf(1, 2, 3)
        assertEquals(expected, collection.toConstMultiSet())

        collection = listOf(1, 1, 2, 4)
        expected = constMultiSetOf(1, 2, 1, 4)
        assertEquals(expected, collection.toConstMultiSet())

        val listCollection = listOf(emptyList(), listOf(1, 2), listOf(10000))
        val listExpected = constMultiSetOf(emptyList(), listOf(1, 2), listOf(10000))
        assertEquals(listExpected, listCollection.toConstMultiSet())
    }

    @Test
    fun testToMutableConstMultiSet() {
        var collection: Collection<Int> = emptyList()
        var expected: ConstMutableMultiSet<Int> = constMutableMultiSetOf()
        assertEquals(expected, collection.toMutableConstMultiSet())

        expected = constMutableMultiSetOf(1, 2, 3)

        collection = listOf(1, 2, 3)
        assertEquals(expected, collection.toMutableConstMultiSet())

        collection = mutableSetOf(1, 2, 3)
        assertEquals(expected, collection.toMutableConstMultiSet())

        collection = mutableListOf(1, 2, 3)
        assertEquals(expected, collection.toMutableConstMultiSet())

        collection = listOf(1, 1, 2, 4)
        expected = constMutableMultiSetOf(1, 2, 1, 4)
        assertEquals(expected, collection.toMutableConstMultiSet())

        val listCollection = listOf(emptyList(), listOf(1, 2), listOf(10000))
        val listExpected = constMutableMultiSetOf(emptyList(), listOf(1, 2), listOf(10000))
        assertEquals(listExpected, listCollection.toMutableConstMultiSet())
    }

    @Test
    fun testCountNull() {
        var collection: Collection<Int?> = emptyList()
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
        var collection: Collection<Int?> = emptyList()
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

        collection = emptySet()
        assertFalse(collection.isNotNullOrEmpty())

        collection = listOf(1)
        assertTrue(collection.isNotNullOrEmpty())

        collection = listOf(null)
        assertTrue(collection.isNotNullOrEmpty())

        collection = listOf(1, 2, 3)
        assertTrue(collection.isNotNullOrEmpty())
    }

    @Test
    fun testMapFilter() {
        val multiSet1 = multiSetOf(1, 2, 3, 4, 7, 2, 4, 1)
        val multiSet2 = multiSetOf(-1, 3, 1, 1, 1, 5, 7, 1, 8, 2)
        val multiSetMin: (Int) -> Int = { min(multiSet1.getCountOf(it), multiSet2.getCountOf(it)) }
        val multiSetMax: (Int) -> Int = { max(multiSet1.getCountOf(it), multiSet2.getCountOf(it)) }

        // empty
        var intCollection: Collection<Int> = emptySet()
        var intResult = intCollection.mapFilter({ it }, { it > 0 })
        assertEmpty(intResult)

        // none filtered
        intCollection = listOf(2, 3, 5, 7, 8)
        var intExpected = listOf(1, 2, 4, 6, 7)
        intResult = intCollection.mapFilter({ it - 1 }) {
            val div = it / 5
            div * 5 != it
        }
        assertEquals(intExpected, intResult)

        var listCollection = listOf(listOf("hello", "world"), listOf("planet", "farewell"), listOf("goodbye", "earth"))
        val stringExpected = listOf("hello", "planet", "goodbye")
        var stringResult = listCollection.mapFilter({ it.first() }) { it.length >= 5 }
        assertEquals(stringExpected, stringResult)

        intCollection = listOf(1, 3, 2)
        intExpected = listOf(2, 1, 1)
        intResult = intCollection.mapFilter(multiSetMin) { it > 0 }
        assertEquals(intExpected, intResult)

        // all filtered
        intCollection = listOf(2, 3, 5, 7, 8)
        intResult = intCollection.mapFilter({ it - 1 }) {
            val div = it / 5
            div * 5 == it
        }
        assertEmpty(intResult)

        listCollection = listOf(listOf("greetings", "world"), listOf("planet", "farewell"), listOf("goodbye", "earth"))
        stringResult = listCollection.mapFilter({ it.first() }) { it.length <= 5 }
        assertEmpty(stringResult)

        intCollection = listOf(1, 3, 2, 4, -1, 1)
        intResult = intCollection.mapFilter(multiSetMax, Int::isZero)
        assertEmpty(intResult)

        // some filtered
        intCollection = listOf(1, 3, 4, 2, 1)
        intExpected = listOf(2, 1, 1, 2)
        intResult = intCollection.mapFilter(multiSetMin) { it > 0 }
        assertEquals(intExpected, intResult)

        listCollection = listOf(emptyList(), listOf("123", "abc"), listOf("hello", "12"), listOf("-4"))
        intExpected = listOf(123, -4)
        val getIntValue: (StringList) -> Int? = { it.firstOrNull()?.toIntOrNull() }
        @Suppress(Suppressions.UNCHECKED_CAST)
        intResult = listCollection.mapFilter(getIntValue) { it != null } as IntList
        assertEquals(intExpected, intResult)
    }
}
