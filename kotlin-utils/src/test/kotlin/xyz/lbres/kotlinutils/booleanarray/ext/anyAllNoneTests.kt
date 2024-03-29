package xyz.lbres.kotlinutils.booleanarray.ext

import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runAllTests() {
    // true
    var array = booleanArrayOf()
    assertTrue(array.all())

    array = booleanArrayOf(true)
    assertTrue(array.all())

    array = booleanArrayOf(true, true, true, true)
    assertTrue(array.all())

    // false
    array = booleanArrayOf(false)
    assertFalse(array.all())

    array = booleanArrayOf(false, false, false, false)
    assertFalse(array.all())

    // mixed
    array = booleanArrayOf(true, false)
    assertFalse(array.all())

    array = booleanArrayOf(false, true)
    assertFalse(array.all())

    array = booleanArrayOf(true, true, true, false, true)
    assertFalse(array.all())

    array = booleanArrayOf(true, false, true, false, true, false, false)
    assertFalse(array.all())

    // changing
    array = booleanArrayOf(true)
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

fun runNoneTests() {
    // true
    var array = booleanArrayOf()
    assertTrue(array.none())

    array = booleanArrayOf(false)
    assertTrue(array.none())

    array = booleanArrayOf(false, false, false, false)
    assertTrue(array.none())

    // false
    array = booleanArrayOf(true)
    assertFalse(array.none())

    array = booleanArrayOf(true, true, true, true)
    assertFalse(array.none())

    // mixed
    array = booleanArrayOf(true, false)
    assertFalse(array.none())

    array = booleanArrayOf(false, true)
    assertFalse(array.none())

    array = booleanArrayOf(true, true, true, false, true)
    assertFalse(array.none())

    array = booleanArrayOf(true, false, true, false, true, false, false)
    assertFalse(array.none())

    // changing
    array = booleanArrayOf(true)
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

fun runAnyTests() {
    // empty
    var array = booleanArrayOf()
    assertFalse(array.any())

    // true
    array = booleanArrayOf(true)
    assertTrue(array.any())

    array = booleanArrayOf(true, true, true, true)
    assertTrue(array.any())

    // false
    array = booleanArrayOf(false)
    assertFalse(array.any())

    array = booleanArrayOf(false, false, false, false)
    assertFalse(array.any())

    // mixed
    array = booleanArrayOf(true, false)
    assertTrue(array.any())

    array = booleanArrayOf(false, true)
    assertTrue(array.any())

    array = booleanArrayOf(true, true, true, false, true)
    assertTrue(array.any())

    array = booleanArrayOf(true, false, true, false, true, false, false)
    assertTrue(array.any())

    // changing
    array = booleanArrayOf(true)
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
