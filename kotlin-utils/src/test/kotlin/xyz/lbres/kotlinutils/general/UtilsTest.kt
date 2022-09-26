package xyz.lbres.kotlinutils.general

import xyz.lbres.kotlinutils.int.ext.isNegative
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {
    @Test
    fun testTernaryIf() {
        var expected = true
        var result = ternaryIf(true, true, false)
        assertEquals(expected, result)

        expected = false
        result = ternaryIf(false, true, false)
        assertEquals(expected, result)

        expected = false
        result = ternaryIf(true, false, true)
        assertEquals(expected, result)

        var testInt = 12
        var expectedInt = 1
        var resultInt = ternaryIf(testInt.isNegative(), -1, 1)
        assertEquals(expectedInt, resultInt)

        testInt = -12
        expectedInt = -1
        resultInt = ternaryIf(testInt.isNegative(), -1, 1)
        assertEquals(expectedInt, resultInt)

        val isPerfectSquare: (Int) -> Boolean = {
            val float = it.toFloat()
            val sqrt = sqrt(float).toInt()
            sqrt * sqrt == it
        }

        testInt = 49
        expectedInt = 49
        resultInt = ternaryIf(isPerfectSquare(testInt), testInt, -1)
        assertEquals(expectedInt, resultInt)

        testInt = 50
        expectedInt = -1
        resultInt = ternaryIf(isPerfectSquare(testInt), testInt, -1)
        assertEquals(expectedInt, resultInt)
    }
}
