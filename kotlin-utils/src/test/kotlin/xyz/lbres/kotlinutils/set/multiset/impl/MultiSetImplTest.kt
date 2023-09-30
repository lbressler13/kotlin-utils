package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.immutable.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test
import kotlin.test.assertEquals

class MultiSetImplTest {
    private fun <T> createSet(): (Collection<T>) -> MultiSetImpl<T> = { MultiSetImpl(it) }

    @Test fun testConstructor() = runImmutableConstructorTests()
    @Test fun testEquals() = runImmutableEqualsTests()

    @Test
    fun testContains() {
        runMultiSetContainsTests(createSet(), createSet(), createSet())
        runMultiSetMutableElementContainsTests(createSet())
    }

    @Test
    fun testContainsAll() {
        runMultiSetContainsAllTests(createSet())
        runMultiSetMutableElementContainsAllTests(createSet())
    }

    @Test
    fun testMinus() {
        runMultiSetMinusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMutableMultiSet(it) }
        runMultiSetMutableElementMinusTests(createSet())
    }

    @Test
    fun testPlus() {
        runMultiSetPlusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMutableMultiSet(it) }
        runMultiSetMutableElementPlusTests(createSet())
    }

    @Test
    fun testIntersect() {
        runMultiSetIntersectTests(createSet(), createSet(), createSet()) { ConstMutableMultiSet(it) }
        runMultiSetMutableElementIntersectTests(createSet())
    }

    @Test fun testIsEmpty() = runMultiSetIsEmptyTests(createSet(), createSet())

    @Test
    fun testGetCountOf() {
        runMultiSetGetCountOfTests(createSet(), createSet())
        runMultiSetMutableElementGetCountOfTests(createSet())
    }

    @Test
    fun testIterator() {
        runMultiSetIteratorTests({ MultiSetImpl(it) }, { MultiSetImpl(it) })

        // mutable elements
        val mutableList1 = mutableListOf(1, 2, 3)
        val mutableList2 = mutableListOf(0, 5, 7)
        val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)

        var listIter = listSet.iterator()
        var listExpected = listOf(listOf(1, 2, 3), listOf(0, 5, 7))
        var listValues: MutableList<IntList> = mutableListOf()
        while (listIter.hasNext()) {
            listValues.add(listIter.next())
        }
        assertEquals(listExpected, listValues)

        mutableList2.clear()
        listIter = listSet.iterator()
        listExpected = listOf(listOf(1, 2, 3), emptyList())
        listValues = mutableListOf()
        while (listIter.hasNext()) {
            listValues.add(listIter.next())
        }
        assertEquals(listExpected, listValues)
    }

    @Test
    fun testToString() {
        var set: MultiSet<Int> = emptyMultiSet()
        var expected = "[]"
        assertEquals(expected, set.toString())

        set = multiSetOf(100, -200, 400, 800)
        expected = "[100, -200, 400, 800]"
        assertEquals(expected, set.toString())

        set = multiSetOf(1, 1, 1, 1)
        expected = "[1, 1, 1, 1]"
        assertEquals(expected, set.toString())

        set = multiSetOf(2, 4, 2, 1)
        expected = "[2, 4, 2, 1]"
        assertEquals(expected, set.toString())

        val mutableList1 = mutableListOf(1, 2, 3)
        val mutableList2 = mutableListOf(0, 5, 7)
        val listSet: MultiSet<IntList> = multiSetOf(mutableList1, mutableList2)

        expected = "[[1, 2, 3], [0, 5, 7]]"
        assertEquals(expected, listSet.toString())

        mutableList2.clear()
        expected = "[[1, 2, 3], []]"
        assertEquals(expected, listSet.toString())
    }
}
