package kotlinutils.list.mutablelist.ext

import kotlinutils.runRandomTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class MutableListExtTest {
    @Test
    internal fun testPopRandom() {
        var ml = mutableListOf<String>()
        assertNull(ml.popRandom())

        ml = mutableListOf("123")
        assertEquals("123", ml.popRandom())
        assertEquals(mutableListOf(), ml)

        ml = mutableListOf("123", "456")
        runSinglePopRandomTest(ml)

        ml = mutableListOf("1", "1", "1", "2")
        runSinglePopRandomTest(ml)

        val mlInt = mutableListOf(23, 89, 0, -104, 44, 2)
        runSinglePopRandomTest(mlInt)

        val mlException = mutableListOf(IndexOutOfBoundsException(), ArithmeticException(), ClassCastException())
        runSinglePopRandomTest(mlException)
    }

    // assumes ml has size >= 2 and does not have all elements of same value
    private fun <T> runSinglePopRandomTest(ml: MutableList<T>) {
        val createResultList = {
            val list = mutableListOf<T>()
            list.addAll(ml)
            val resultList = mutableListOf<T>()
            for (i in ml.indices) {
                val result = list.popRandom()
                assertNotNull(result)
                resultList.add(result)
            }

            resultList
        }

        // same elements, different order
        val checkResult = { it: MutableList<T> -> it != ml && it.size == ml.size && it.toSet() == ml.toSet() }

        runRandomTest(createResultList, checkResult)
    }
}
