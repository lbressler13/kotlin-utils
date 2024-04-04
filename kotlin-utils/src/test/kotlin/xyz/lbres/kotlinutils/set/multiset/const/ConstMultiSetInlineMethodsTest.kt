package xyz.lbres.kotlinutils.set.multiset.const

import org.junit.Test
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports

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

    @Test
    fun testFilterNotToConstSet() {
        val filterFn: GenericFilterFn<*> = { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.filterNotToConstSet(fn as (Any?) -> Boolean)
        }
        runCommonFilterNotToSetTests(::ConstMultiSetImpl, filterFn, true)
    }

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
        runCommonFilterToSetConsistentTests(::ConstMultiSetImpl, filterFn, true)
    }

    @Test
    fun testFilterNotToConstSetConsistent() {
        val filterFn: GenericFilterFn<*> = { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.filterNotToConstSetConsistent(fn as (Any?) -> Boolean)
        }
        runCommonFilterNotToSetConsistentTests(::ConstMultiSetImpl, filterFn, true)
    }
}
