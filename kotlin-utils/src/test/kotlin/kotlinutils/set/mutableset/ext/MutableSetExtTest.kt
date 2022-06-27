package kotlinutils.set.mutableset.ext

import kotlinutils.runRandomTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class MutableSetExtTest {
    @Test
    internal fun testPopRandom() {
        var set = mutableSetOf<String>()
        assertNull(set.popRandom())

        set = mutableSetOf("123")
        assertEquals("123", set.popRandom())
        assertNull(set.popRandom())

        set = mutableSetOf("123", "456", "abc", "def")
        runSinglePopRandomTest(set)

        val setInt = mutableSetOf(23, 89, 0, -104, 44, 2)
        runSinglePopRandomTest(setInt)

        val setException = mutableSetOf(IndexOutOfBoundsException(), ArithmeticException(), ClassCastException())
        runSinglePopRandomTest(setException)
    }

    /**
     * Run popRandom test on a single set.
     * Assumes the set has size >= 2 and has at least 2 distinct values
     *
     * @param set [MutableSet]: the value to test
     */
    private fun <T> runSinglePopRandomTest(set: MutableSet<T>) {
        val createResultSet = {
            val setCopy = mutableSetOf<T>()
            setCopy.addAll(set)
            val resultSet = mutableSetOf<T>()
            for (i in 0 until set.size) {
                val result = setCopy.popRandom()
                assertNotNull(result)
                resultSet.add(result)
            }

            resultSet
        }
        val checkResult: (MutableSet<T>) -> Boolean = { it: MutableSet<T> -> it == set }

        runRandomTest(createResultSet, checkResult)
    }
}
