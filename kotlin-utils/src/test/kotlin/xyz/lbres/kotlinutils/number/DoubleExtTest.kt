package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress(Suppressions.CONSTANT_CONDITIONS)
class DoubleExtTest {
    @Test
    fun testIfZero() {
        val getValue = { 2.0 }

        var double = 0.0
        var expected = 2.0
        assertEquals(expected, double.ifZero(getValue))

        double = -0.0
        assertEquals(expected, double.ifZero(getValue))

        double = 2.0
        expected = 2.0
        assertEquals(expected, double.ifZero(getValue))

        double = 15.0
        expected = 15.0
        assertEquals(expected, double.ifZero(getValue))

        double = -0.1230
        expected = -0.123
        assertEquals(expected, double.ifZero(getValue))
    }

    @Test
    fun testIsNegative() {
        var double = 0.0
        assertFalse(double.isNegative())

        double = -0.0
        assertFalse(double.isNegative())

        double = 0.000000000001
        assertFalse(double.isNegative())

        double = 100.0
        assertFalse(double.isNegative())

        double = -0.000000000001
        assertTrue(double.isNegative())

        double = -100.0
        assertTrue(double.isNegative())
    }

    @Test
    fun testIsZero() {
        var double = 0.0
        assertTrue(double.isZero())

        double = -0.0
        assertTrue(double.isZero())

        double = 0.000000000001
        assertFalse(double.isZero())

        double = -0.000000000001
        assertFalse(double.isZero())

        double = 100.0
        assertFalse(double.isZero())

        double = -100.0
        assertFalse(double.isZero())
    }

    @Test
    fun testIsNullOrZero() {
        var double: Double? = null
        assertTrue(double.isNullOrZero())

        double = 0.0
        assertTrue(double.isNullOrZero())

        double = -0.0
        assertTrue(double.isNullOrZero())

        double = 0.000000000001
        assertFalse(double.isNullOrZero())

        double = -0.000000000001
        assertFalse(double.isNullOrZero())

        double = 100.0
        assertFalse(double.isNullOrZero())

        double = -100.0
        assertFalse(double.isNullOrZero())
    }
}
