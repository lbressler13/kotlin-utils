package xyz.lbres.kotlinutils.set.multiset.const

import org.junit.Test
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals

class ConstMultiSetInlineMethodsTest {
    @Test
    fun testMapToConstSet() {
        val mapFn: GenericMapFn<*, *> = { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.mapToConstSet(fn as (Any?) -> Any)
        }
        runCommonMapToSetTests(::ConstMultiSetImpl, mapFn, true)
    }

    @Test
    fun testFilterToConstSet() {
        val filterFn: GenericFilterFn<*> = { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.filterToConstSet(fn as (Any?) -> Boolean)
        }
        runCommonFilterToSetTests(::ConstMultiSetImpl, filterFn, true)
    }

    @Test fun testFilterNotToConstSet() = runFilterNotToConstSetTests()

    @Test
    fun testMapToConstSetConsistent() {
        val mapFn: GenericMapFn<*, *> = { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.mapToConstSetConsistent(fn as (Any?) -> Any)
        }
        runCommonMapToSetConsistentTests(::ConstMultiSetImpl, mapFn, true)
    }

    @Test
    fun testFilterToConstSetConsistent() {
        val filterFn: GenericFilterFn<*> = { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.filterToConstSetConsistent(fn as (Any?) -> Boolean)
        }
        runCommonFilterToSetTests(::ConstMultiSetImpl, filterFn, true)

        val intSet = constMultiSetOf(1, 1, 2, 14, 14)
        val intExpected = constMultiSetOf(1, 2, 14, 14)
        var previousOdd = false
        val intActual = intSet.filterToConstSet {
            when {
                it % 2 == 0 -> true
                previousOdd -> false
                else -> {
                    previousOdd = true
                    true
                }
            }
        }
        assertEquals(intExpected, intActual)
    }

    @Test fun testFilterNotToConstSetConsistent() = runFilterNotToConstSetConsistentTests()
}
