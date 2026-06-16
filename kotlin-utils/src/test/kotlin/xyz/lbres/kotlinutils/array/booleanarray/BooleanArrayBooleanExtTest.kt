package xyz.lbres.kotlinutils.array.booleanarray

import xyz.lbres.kotlinutils.array.setAllValues
import xyz.lbres.kotlinutils.testutils.checkTrueFalse
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BooleanArrayBooleanExtTest {
    private val empty = listOf(booleanArrayOf())
    private val trueValues = listOf(booleanArrayOf(true), booleanArrayOf(true, true, true, true))
    private val falseValues = listOf(booleanArrayOf(false), booleanArrayOf(false, false, false, false))
    private val mixedValues = listOf(
        booleanArrayOf(true, false),
        booleanArrayOf(false, true),
        booleanArrayOf(true, true, true, false, true),
        booleanArrayOf(true, false, true, false, true, false, false),
    )

    @Test
    fun testAll() {
        checkTrueFalse(empty + trueValues, falseValues + mixedValues, { "$it.all()" }, BooleanArray::all)

        // changing
        var array = booleanArrayOf(true)
        assertTrue(array.all())

        array[0] = false
        assertFalse(array.all())

        array = booleanArrayOf(false, true, false)
        assertFalse(array.all())

        array[1] = false
        assertFalse(array.all())

        array.setAllValues(true)
        assertTrue(array.all())
    }

    @Test
    fun testNone() {
        checkTrueFalse(empty + falseValues, trueValues + mixedValues, { "$it.none()" }, BooleanArray::none)

        // changing
        var array = booleanArrayOf(true)
        assertFalse(array.none())

        array[0] = false
        assertTrue(array.none())

        array = booleanArrayOf(false, true, false)
        assertFalse(array.none())

        array[1] = false
        assertTrue(array.none())

        array.setAllValues(true)
        assertFalse(array.none())
    }

    @Test
    fun testAny() {
        checkTrueFalse(trueValues + mixedValues, empty + falseValues, { "$it.any()" }, BooleanArray::any)

        // changing
        var array = booleanArrayOf(true)
        assertTrue(array.any())

        array[0] = false
        assertFalse(array.any())

        array = booleanArrayOf(false, true, false)
        assertTrue(array.any())

        array[1] = false
        assertFalse(array.any())

        array.setAllValues(true)
        assertTrue(array.any())
    }
}
