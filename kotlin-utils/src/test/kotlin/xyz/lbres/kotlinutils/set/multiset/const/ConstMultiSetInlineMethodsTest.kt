package xyz.lbres.kotlinutils.set.multiset.const

import org.junit.Test
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.testutils.GenericMapFn
import xyz.lbres.kotlinutils.set.multiset.testutils.runCommonMapToSetTests

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

    @Test fun testFilterToConstSet() = runFilterToConstSetTests()
    @Test fun testFilterNotToConstSet() = runFilterNotToConstSetTests()
    @Test fun testMapToConstSetConsistent() = runMapToConstSetConsistent()
    @Test fun testFilterToConstSetConsistent() = runFilterToConstSetConsistentTests()
    @Test fun testFilterNotToConstSetConsistent() = runFilterNotToConstSetConsistentTests()
}
