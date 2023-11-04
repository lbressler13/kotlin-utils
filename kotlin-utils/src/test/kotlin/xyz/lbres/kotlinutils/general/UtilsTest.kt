package xyz.lbres.kotlinutils.general

import xyz.lbres.kotlinutils.int.ext.isNegative
import xyz.lbres.kotlinutils.int.ext.isZero
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.list.IntList
import kotlin.math.sqrt
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress(Suppressions.BOOLEAN_LITERAL_ARG, Suppressions.CONSTANT_CONDITIONS)
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
        resultInt = simpleIf(denominator.isZero(), { 0 }, { numerator / denominator })
        assertEquals(expectedInt, resultInt)

        denominator = 2
        expectedInt = 2
        resultInt = simpleIf(denominator.isZero(), { 0 }, { numerator / denominator })
        assertEquals(expectedInt, resultInt)

        // no return
        var count = 0
        simpleIf(false, { count += 1 }, { count += 2 })
        assertEquals(2, count)
    }

    @Test
    fun testTryOrDefault() {
        val divFn: (Int) -> Int = { 10 / it }
        val listMaxFn: (IntList) -> Int = { it.maxOrNull()!! }
        val charIndexFn: (Int) -> Char = { "1234"[it] }

        // without exceptions param
        var resultInt = tryOrDefault(0) { divFn(0) }
        assertEquals(0, resultInt)

        resultInt = tryOrDefault(0) { divFn(1) }
        assertEquals(10, resultInt)

        resultInt = tryOrDefault(-1) { listMaxFn(emptyList()) }
        assertEquals(-1, resultInt)

        resultInt = tryOrDefault(-1) { listMaxFn(listOf(1, 3, 5, 7)) }
        assertEquals(7, resultInt)

        var resultChar = tryOrDefault('-') { charIndexFn(4) }
        assertEquals('-', resultChar)

        resultChar = tryOrDefault('-') { charIndexFn(3) }
        assertEquals('4', resultChar)

        // with exceptions param
        var exceptions: List<KClass<out Exception>> = listOf(ArithmeticException::class)
        resultInt = tryOrDefault(0, exceptions) { divFn(0) }
        assertEquals(0, resultInt)

        exceptions = listOf(NullPointerException::class, NumberFormatException::class)
        assertFailsWith<ArithmeticException> { tryOrDefault(0, exceptions) { divFn(0) } }
        assertFailsWith<ArithmeticException> { tryOrDefault(0, emptyList()) { divFn(0) } }

        resultInt = tryOrDefault(0, exceptions) { divFn(1) }
        assertEquals(10, resultInt)

        exceptions = listOf(NullPointerException::class, IndexOutOfBoundsException::class)
        resultChar = tryOrDefault('-', exceptions) { charIndexFn(4) }
        assertEquals('-', resultChar)

        exceptions = listOf(NullPointerException::class, ClassCastException::class)
        assertFailsWith<IndexOutOfBoundsException> { tryOrDefault('-', exceptions) { charIndexFn(4) } }

        resultChar = tryOrDefault('-') { charIndexFn(3) }
        assertEquals('4', resultChar)

        // custom exception
        class CustomException : Exception()
        exceptions = listOf(CustomException::class)

        resultChar = tryOrDefault('X') { throw CustomException() }
        assertEquals('X', resultChar)

        exceptions = listOf(NullPointerException::class, ClassCastException::class)
        assertFailsWith<CustomException> { tryOrDefault('X', exceptions) { throw CustomException() } }
    }

    @Test
    fun testSucceeds() {
        // succeeds
        assertTrue { succeeds { 1 / 2 } }
        assertTrue { succeeds { listOf(1, 2, 3)[2] } }
        assertTrue { succeeds { setOf("hello", "world").randomOrNull()!! } }

        // fails
        assertFalse { succeeds { 1 / 0 } }
        assertFalse { succeeds { listOf(1, 2, 3)[4] } }
        assertFalse { succeeds { emptySet<String>().randomOrNull()!! } }
        assertFalse { succeeds { throw NullPointerException() } }
    }
}
