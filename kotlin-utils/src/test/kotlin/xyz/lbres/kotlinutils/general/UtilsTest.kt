package xyz.lbres.kotlinutils.general

import xyz.lbres.kotlinutils.int.ext.isNegative
import kotlin.math.sqrt
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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

    @Test
    fun testTryDefault() {
        // without exceptions
        var expectedInt = 0
        var resultInt = tryDefault(0) { 10 / 0 }
        assertEquals(expectedInt, resultInt)

        expectedInt = 10
        resultInt = tryDefault(0) { 10 / 1 }
        assertEquals(expectedInt, resultInt)

        expectedInt = -1
        resultInt = tryDefault(-1) { emptyList<Int>().maxOrNull()!! }
        assertEquals(expectedInt, resultInt)

        expectedInt = 7
        resultInt = tryDefault(-1) { listOf(1, 3, 5, 7).maxOrNull()!! }
        assertEquals(expectedInt, resultInt)

        var expectedChar = '-'
        var resultChar = tryDefault('-') { "1234"[4] }
        assertEquals(expectedChar, resultChar)

        expectedChar = '4'
        resultChar = tryDefault('-') { "1234"[3] }
        assertEquals(expectedChar, resultChar)

        // with exceptions list
        var exceptions: List<KClass<out Exception>> = listOf(ArithmeticException::class)
        expectedInt = 0
        resultInt = tryDefault(0, exceptions) { 10 / 0 }
        assertEquals(expectedInt, resultInt)

        exceptions = listOf(NullPointerException::class, NumberFormatException::class)
        assertFailsWith<ArithmeticException> { tryDefault(0, exceptions) { 10 / 0 } }
        assertFailsWith<ArithmeticException> { tryDefault(0, emptyList()) { 10 / 0 } }

        expectedInt = 10
        resultInt = tryDefault(0, exceptions) { 10 / 1 }
        assertEquals(expectedInt, resultInt)

        exceptions = listOf(NullPointerException::class, IndexOutOfBoundsException::class)
        expectedChar = '-'
        resultChar = tryDefault('-', exceptions) { "1234"[4] }
        assertEquals(expectedChar, resultChar)

        exceptions = listOf(NullPointerException::class, ClassCastException::class)
        assertFailsWith<IndexOutOfBoundsException> { tryDefault('-', exceptions) { "1234"[4] } }

        expectedChar = '4'
        resultChar = tryDefault('-') { "1234"[3] }
        assertEquals(expectedChar, resultChar)

        class CustomException : Exception()
        exceptions = listOf(CustomException::class)
        expectedChar = 'X'
        resultChar = tryDefault('X') { throw CustomException() }
        assertEquals(expectedChar, resultChar)

        exceptions = listOf(NullPointerException::class, ClassCastException::class)
        assertFailsWith<CustomException> { tryDefault('X', exceptions) { throw CustomException() } }
    }
}
