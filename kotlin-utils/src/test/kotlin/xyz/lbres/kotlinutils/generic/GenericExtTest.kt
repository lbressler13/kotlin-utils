package xyz.lbres.kotlinutils.generic

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class GenericExtTest {
    @Test
    fun testIfNull() {
        var string: String? = "abc"
        var expectedStr = "abc"
        var resultStr = string.ifNull { "xyz" }
        assertEquals(expectedStr, resultStr)

        string = "abc"
        expectedStr = "abc"
        resultStr = string.ifNull { "abc" }
        assertEquals(expectedStr, resultStr)

        string = ""
        expectedStr = ""
        resultStr = string.ifNull { "xyz" }
        assertEquals(expectedStr, resultStr)

        string = null
        expectedStr = "xyz"
        resultStr = string.ifNull { "xyz" }
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
        val stringFalseValues: List<String?> = listOf("", "null", "abc 123")
        checkTrueFalse(listOf(null), stringFalseValues, { "$it.isNull()" }) { it.isNull() }

        val intFalseValues = listOf(-100, 0)
        checkTrueFalse(listOf(null), intFalseValues, { "$it.isNull()" }) { it.isNull() }

        val nullList: List<Int?> = listOf(null, null)
        checkTrueFalse(listOf(null), listOf(nullList), { "$it.isNull()" }) { it.isNull() }
    }

    @Test
    fun testIsNotNull() {
        val stringTrueValues: List<String?> = listOf("", "null", "abc 123")
        checkTrueFalse(stringTrueValues, listOf(null), { "$it.isNotNull()" }) { it.isNotNull() }

        val intTrueValues = listOf(-100, 0)
        checkTrueFalse(intTrueValues, listOf(null), { "$it.isNotNull()" }) { it.isNotNull() }

        val nullList: List<Int?> = listOf(null, null)
        checkTrueFalse(listOf(nullList), listOf(null), { "$it.isNotNull()" }) { it.isNotNull() }
    }

    @Test
    fun testIfNotNull() {
        // null
        var i: Int? = null
        assertNull(i.ifNotNull { it + 5 })
        assertNull(i.ifNotNull { it.toString().padStart(4, '0') })

        var l: List<Int>? = null
        assertNull(l.ifNotNull { it.size })

        val mapStr: (String) -> List<Char> = {
            it.map { c -> c.uppercaseChar().minus(3) }
        }
        var s: String? = null
        assertNull(s.ifNotNull(mapStr))

        // not null
        i = 0
        assertEquals(5, i.ifNotNull { it + 5 })

        i = 6
        assertEquals(11, i.ifNotNull { it + 5 })
        assertEquals("0006", i.ifNotNull { it.toString().padStart(4, '0') })

        l = listOf(1, 4, 5, 6)
        assertEquals(4, l.ifNotNull { it.size })

        l = emptyList()
        assertEquals(0, l.ifNotNull { it.size })

        s = "hello"
        assertEquals("EBIIL".toList(), s.ifNotNull(mapStr))
    }
}
