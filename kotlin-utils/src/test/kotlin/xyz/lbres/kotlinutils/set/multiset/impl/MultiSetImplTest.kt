package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.set.multiset.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test
import kotlin.test.assertEquals

class MultiSetImplTest {
    private fun <T> createSet(): (Collection<T>) -> MultiSetImpl<T> = { MultiSetImpl(it) }

    @Test fun testConstructor() = runImmutableConstructorTests()

    @Test
    fun testEquals() {
        runMultiSetEqualsTests(createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMultiSetMutableElementsEqualsTests(createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
    }

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
        runMultiSetMinusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMultiSetMutableElementMinusTests(createSet())
    }

    @Test
    fun testPlus() {
        runMultiSetPlusTests(createSet(), createSet(), createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
        runMultiSetMutableElementPlusTests(createSet())
    }

    @Test
    fun testIntersect() {
        runMultiSetIntersectTests(createSet(), createSet(), createSet()) { ConstMutableMultiSetImpl(it) }
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
        runMultiSetToStringTests(createSet(), createSet())
        runMultiSetMutableElementToStringTests(createSet())
    }
}
