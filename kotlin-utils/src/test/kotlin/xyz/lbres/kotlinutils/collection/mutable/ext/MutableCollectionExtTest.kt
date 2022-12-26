package xyz.lbres.kotlinutils.collection.mutable.ext

import xyz.lbres.kotlinutils.list.IntList
import xyz.lbres.kotlinutils.list.mutablelist.ext.popRandom
import xyz.lbres.kotlinutils.runRandomTest
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf
import xyz.lbres.kotlinutils.set.mutableset.ext.popRandom
import kotlin.test.*

internal class MutableCollectionExtTest {
    @Test
    fun testPopRandom() {
        assertNull(mutableListOf<Int>().popRandom())

        var multiSet = mutableMultiSetOf(1)
        assertEquals(1, multiSet.popRandom())
        assertNull(multiSet.popRandom())
        assertTrue(multiSet.isEmpty())

        multiSet = mutableMultiSetOf(-1, 1, 2, 2, 2, 3, 3)
        val multiSetCopy = mutableMultiSetOf(-1, 1, 2, 2, 2, 3, 3)
        val resultMultiSet = mutableMultiSetOf<Int>()
        repeat(multiSet.size) {
            val result = multiSetCopy.popRandom()
            assertNotNull(result)
            resultMultiSet.add(result)
        }
        assertEquals(multiSet, resultMultiSet)

        val listMultiSet: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf("7"), listOf("7"), listOf("7"))
        val listMultiSetCopy: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(), listOf("7"), listOf("7"), listOf("7"))
        val resultListMultiSet: MutableMultiSet<List<Comparable<*>>> = mutableMultiSetOf()
        repeat(listMultiSet.size) {
            val result = listMultiSetCopy.popRandom()
            assertNotNull(result)
            resultListMultiSet.add(result)
        }
        assertEquals(listMultiSet, resultListMultiSet)

        var set = mutableSetOf<String>()
        assertNull(set.popRandom())

        set = mutableSetOf("123")
        assertEquals("123", set.popRandom())
        assertNull(set.popRandom())

        set = mutableSetOf("123", "456", "789")
        val setCopy = mutableSetOf("123", "456", "789")
        val resultSet = mutableSetOf<String>()
        repeat(set.size) {
            val result = setCopy.popRandom()
            assertNotNull(result)
            resultSet.add(result)
        }
        assertEquals(set, resultSet)

        val e1 = IndexOutOfBoundsException()
        val e2 = ArithmeticException()
        val e3 = ClassCastException()
        val exceptionSet = mutableSetOf(e1, e2, e3)
        val exceptionSetCopy = mutableSetOf(e1, e2, e3)
        val resultExceptionSet = mutableSetOf<RuntimeException>()
        repeat(exceptionSet.size) {
            val result = exceptionSetCopy.popRandom()
            assertNotNull(result)
            resultExceptionSet.add(result)
        }
        assertEquals(exceptionSet, resultExceptionSet)

        var list = mutableListOf<String>()
        assertNull(list.popRandom())

        list = mutableListOf("123", "456")
        var listCopy = mutableListOf("123", "456")
        var resultList = mutableListOf<String>()
        repeat(list.size) {
            val result = listCopy.popRandom()
            assertNotNull(result)
            resultList.add(result)
        }
        assertEquals(list.sorted(), resultList.sorted())

        // TODO write specific test for this
        list = mutableListOf("1", "1", "1", "2")
        listCopy = mutableListOf("1", "1", "1", "2")
        resultList = mutableListOf()
        repeat(list.size) {
            val result = listCopy.popRandom()
            assertNotNull(result)
            resultList.add(result)
        }
        assertEquals(list.sorted(), resultList.sorted())
    }

    /**
     * Run popRandom test on a single set.
     * Assumes the set has size >= 2 and has at least 2 distinct values
     *
     * @param values [MutableMultiSet]: the value to test
     */
    private fun <T> runSingleSetTest(values: MutableMultiSet<T>, createEmptySet: () -> MutableMultiSet<T>) {
        val createResultSet = {
            val copy = createEmptySet()
            copy.addAll(values)
            val resultSet = createEmptySet()
            repeat(values.size) {
                val result = copy.popRandom()
                assertNotNull(result)
                resultSet.add(result)
            }

            println(resultSet)
            resultSet
        }
        val checkResult: (MutableMultiSet<T>) -> Boolean = { result: MutableMultiSet<T> -> result == values }
        println(values)

        runRandomTest(createResultSet, checkResult)
        println()
    }

    private fun <E, T: MutableCollection<E>> runTest(values: T, equals: (T, T) -> Boolean, createEmpty: () -> T) {
        val createResultSet = {
            val copy = createEmpty()
            copy.addAll(values)
            val resultSet = createEmpty()
            repeat(values.size) {
                val result = copy.popRandom()
                assertNotNull(result)
                resultSet.add(result)
            }

            println(listOf(values, resultSet, values == resultSet))
            resultSet
        }
        val checkResult: (T) -> Boolean = { result: T -> equals(result, values) }
        println(values)

        runRandomTest(createResultSet, checkResult)
        println()
    }
}

