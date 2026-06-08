package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class ByteExtTest {
    @Test
    fun testIfZero() {
        val getValue = { 2.toByte() }

        var byte: Byte = 0
        var expected: Byte = 2
        assertEquals(expected, byte.ifZero(getValue))

        byte = 2
        expected = 2
        assertEquals(expected, byte.ifZero(getValue))

        byte = 15
        expected = 15
        assertEquals(expected, byte.ifZero(getValue))

        byte = -100
        expected = -100
        assertEquals(expected, byte.ifZero(getValue))
    }

    @Test
    fun testIsNegative() {
        val trueValues: List<Byte> = listOf(-1, -100)
        val falseValues: List<Byte> = listOf(0, 1, 100)
        checkTrueFalse(trueValues, falseValues, { "$it.isNegative()" }, Byte::isNegative)
    }

    @Test
    fun testIsZero() {
        val trueValues: List<Byte> = listOf(0)
        val falseValues: List<Byte> = listOf(1, -1, 100, -100)
        checkTrueFalse(trueValues, falseValues, { "$it.isZero()" }, Byte::isZero)
    }

    @Test
    fun testIsNullOrZero() {
        val trueValues: List<Byte?> = listOf(null, 0)
        val falseValues: List<Byte?> = listOf(1, -1, 100, -100)
        checkTrueFalse(trueValues, falseValues, { "$it.isNullOrZero()" }, { it.isNullOrZero() })
    }
}
