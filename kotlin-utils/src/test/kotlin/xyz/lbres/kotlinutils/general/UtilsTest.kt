package xyz.lbres.kotlinutils.general

import xyz.lbres.kotlinutils.int.ext.isNegative
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {
    @Test
    fun testSimpleIf() {
        // value
        var expected = true
        var result = simpleIf(true, true, false)
        assertEquals(expected, result)

        expected = false
        result = simpleIf(false, true, false)
        assertEquals(expected, result)

        expected = false
        result = simpleIf(true, false, true)
        assertEquals(expected, result)

        var testInt = 12
        var expectedInt = 1
        var resultInt = simpleIf(testInt.isNegative(), -1, 1)
        assertEquals(expectedInt, resultInt)

        testInt = -12
        expectedInt = -1
        resultInt = simpleIf(testInt.isNegative(), -1, 1)
        assertEquals(expectedInt, resultInt)

        val isPerfectSquare: (Int) -> Boolean = {
            val float = it.toFloat()
            val sqrt = sqrt(float).toInt()
            sqrt * sqrt == it
        }

        testInt = 49
        expectedInt = 49
        resultInt = simpleIf(isPerfectSquare(testInt), testInt, -1)
        assertEquals(expectedInt, resultInt)

        testInt = 50
        expectedInt = -1
        resultInt = simpleIf(isPerfectSquare(testInt), testInt, -1)
        assertEquals(expectedInt, resultInt)

        // function
        expected = false
        result = simpleIf(false, { true }, { false })
        assertEquals(expected, result)

        val expectedString = ""
        val string = "a"
        val resultString = simpleIf(string.length > 1, { string[1] }, "")
        assertEquals(expectedString, resultString)

        val list = listOf(1, 2, 3)

        expectedInt = 2
        resultInt = simpleIf(3 in list, list.indexOf(3), 0)
        assertEquals(expectedInt, resultInt)

        val numerator = 4
        var denominator = 0
        expectedInt = 0
        resultInt = simpleIf(denominator == 0, { 0 }, { numerator / denominator })
        assertEquals(expectedInt, resultInt)

        denominator = 2
        expectedInt = 2
        resultInt = simpleIf(denominator == 0, { 0 }, { numerator / denominator })
        assertEquals(expectedInt, resultInt)

        // no return
        var count = 0
        simpleIf(false, { count += 1 }, { count += 2 })
        assertEquals(2, count)
    }
}
