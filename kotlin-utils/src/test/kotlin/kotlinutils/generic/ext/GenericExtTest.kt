package kotlinutils.generic.ext

import kotlin.test.Test
import kotlin.test.assertEquals

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
}
