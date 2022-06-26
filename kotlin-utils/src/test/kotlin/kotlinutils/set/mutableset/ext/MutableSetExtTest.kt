package kotlinutils.set.mutableset.ext

import kotlinutils.runRandomTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class MutableSetExtTest {
    @Test
    internal fun testPopRandom() {
        var ms = mutableSetOf<String>()
        assertNull(ms.popRandom())

        ms = mutableSetOf("123")
        assertEquals("123", ms.popRandom())
        assertNull(ms.popRandom())

        ms = mutableSetOf("123", "456", "abc", "def")
        runSinglePopRandomTest(ms)

        val msInt = mutableSetOf(23, 89, 0, -104, 44, 2)
        runSinglePopRandomTest(msInt)

        val msException = mutableSetOf(IndexOutOfBoundsException(), ArithmeticException(), ClassCastException())
        runSinglePopRandomTest(msException)
    }

    // assumes ml has size >= 2
    private fun <T> runSinglePopRandomTest(ms: MutableSet<T>) {
        val createResultSet = {
            val set = mutableSetOf<T>()
            set.addAll(ms)
            val resultSet = mutableSetOf<T>()
            for (i in 0 until ms.size) {
                val result = set.popRandom()
                assertNotNull(result)
                resultSet.add(result)
            }

            resultSet
        }
        val checkResult: (MutableSet<T>) -> Boolean = { it: MutableSet<T> -> it == ms }

        runRandomTest(createResultSet, checkResult)
    }
}
