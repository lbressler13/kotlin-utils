package kotlinutils.generic.ext

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class GenericExtTest {
    @Test
    internal fun testIfNull() {
        var string: String? = "abc"
        var expectedStr = "abc"
        var resultStr = string.ifNull { "abcde" }
        assertEquals(expectedStr, resultStr)

        string = "abc"
        expectedStr = "abc"
        resultStr = string.ifNull { "abc" }
        assertEquals(expectedStr, resultStr)

        string = ""
        expectedStr = ""
        resultStr = string.ifNull { "abcde" }
        assertEquals(expectedStr, resultStr)

        string = null
        expectedStr = "abcde"
        resultStr = string.ifNull { "abcde" }
        assertEquals(expectedStr, resultStr)

        val nonnull = "123"
        expectedStr = "123"
        resultStr = nonnull.ifNull { "abc" }
        assertEquals(expectedStr, resultStr)

        var int: Int? = 0
        var expectedInt = 0
        var resultInt = int.ifNull { 1 }
        assertEquals(expectedInt, resultInt)

        int = -30
        expectedInt = -30
        resultInt = int.ifNull { 0 }
        assertEquals(expectedInt, resultInt)

        int = null
        expectedInt = 10
        resultInt = int.ifNull { 10 }
        assertEquals(expectedInt, resultInt)

        int = null
        expectedInt = 10
        resultInt = int.ifNull { 3 * 4 - 2 }
        assertEquals(expectedInt, resultInt)

        val error: Exception? = null
        val expectedErr = NullPointerException()
        val resultErr = error.ifNull { expectedErr }
        assertEquals(expectedErr, resultErr)
    }

    @Test
    fun testIsNull() {
        var string: String? = null
        assertTrue(string.isNull())

        string = ""
        assertFalse(string.isNull())

        string = "null"
        assertFalse(string.isNull())

        string = "abc 123"
        assertFalse(string.isNull())

        var int: Int? = null
        assertTrue(int.isNull())

        int = -100
        assertFalse(int.isNull())

        var list: List<Int?>? = null
        assertTrue(list.isNull())

        list = listOf(null, null)
        assertFalse(list.isNull())
    }

    @Test
    fun testIsNotNull() {
        var string: String? = null
        assertFalse(string.isNotNull())

        string = ""
        assertTrue(string.isNotNull())

        string = "null"
        assertTrue(string.isNotNull())

        string = "abc 123"
        assertTrue(string.isNotNull())

        var int: Int? = null
        assertFalse(int.isNotNull())

        int = -100
        assertTrue(int.isNotNull())

        var list: List<Int?>? = null
        assertFalse(list.isNotNull())

        list = listOf(null, null)
        assertTrue(list.isNotNull())
    }
}
