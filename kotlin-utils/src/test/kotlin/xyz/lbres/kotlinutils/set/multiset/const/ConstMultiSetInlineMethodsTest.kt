package xyz.lbres.kotlinutils.set.multiset.const

import org.junit.Test
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.testutils.* // ktlint-disable no-wildcard-imports no-unused-imports

class ConstMultiSetInlineMethodsTest {
    @Test
    fun testMapToConstSet() {
        runCommonMapToSetTests(::ConstMultiSetImpl, true) { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.mapToConstSet(fn as (Any?) -> Any)
        }
    }

    @Test
    fun testFilterToConstSet() {
        runCommonFilterToSetTests(::ConstMultiSetImpl, true) { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.filterToConstSet(fn as (Any?) -> Boolean)
        }
    }

    @Test
    fun testFilterNotToConstSet() {
        runCommonFilterNotToSetTests(::ConstMultiSetImpl, true) { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.filterNotToConstSet(fn as (Any?) -> Boolean)
        }
    }

    @Test
    fun testMapToConstSetConsistent() {
        runCommonMapToSetConsistentTests(::ConstMultiSetImpl, true) { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.mapToConstSetConsistent(fn as (Any?) -> Any)
        }
    }

    @Test
    fun testFilterToConstSetConsistent() {
        runCommonFilterToSetConsistentTests(::ConstMultiSetImpl, true) { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.filterToConstSetConsistent(fn as (Any?) -> Boolean)
        }
    }

    @Test
    fun testFilterNotToConstSetConsistent() {
        runCommonFilterNotToSetConsistentTests(::ConstMultiSetImpl, true) { set, fn ->
            set as ConstMultiSet
            @Suppress(Suppressions.UNCHECKED_CAST)
            set.filterNotToConstSetConsistent(fn as (Any?) -> Boolean)
        }
    }
}
