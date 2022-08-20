package kotlinutils.collection.ext

import kotlinutils.classes.multiset.MultiSet
import kotlinutils.classes.multiset.multiSetOf
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
    internal fun testFilterNotNull() {
        // TODO
    }
}
