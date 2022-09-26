package xyz.lbres.kotlinutils.list.mutablelist.ext

import xyz.lbres.kotlinutils.runRandomTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class MutableListExtTest {
    @Test
    internal fun testPopRandom() {
        var listString = mutableListOf<String>()
        assertNull(listString.popRandom())

        listString = mutableListOf("123")
        assertEquals("123", listString.popRandom())
        assertEquals(mutableListOf(), listString)

        listString = mutableListOf("123", "456")
        runSinglePopRandomTest(listString)

        listString = mutableListOf("1", "1", "1", "2")
        runSinglePopRandomTest(listString)

        val listInt = mutableListOf(23, 89, 0, -104, 44, 2)
        runSinglePopRandomTest(listInt)

        val listException = mutableListOf(IndexOutOfBoundsException(), ArithmeticException(), ClassCastException())
        runSinglePopRandomTest(listException)
    }

    /**
     * Run popRandom test on a single list.
     * Assumes the list has size >= 2 and has at least 2 distinct values
     *
     * @param list [MutableList]: the value to test
     */
    private fun <T> runSinglePopRandomTest(list: MutableList<T>) {
        val createResultList = {
            val listCopy = mutableListOf<T>()
            listCopy.addAll(list)
            val resultList = mutableListOf<T>()
            repeat(list.size) {
                val result = listCopy.popRandom()
                assertNotNull(result)
                resultList.add(result)
            }

            resultList
        }

        // same elements, different order
        val checkResult = { it: MutableList<T> ->
            it != list && it.size == list.size && it.toSet() == list.toSet()
        }

        runRandomTest(createResultList, checkResult)
    }
}
