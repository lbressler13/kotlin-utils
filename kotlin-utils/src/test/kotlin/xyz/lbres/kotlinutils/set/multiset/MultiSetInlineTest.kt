package xyz.lbres.kotlinutils.set.multiset

import org.junit.Test
import xyz.lbres.kotlinutils.set.multiset.inline.* // ktlint-disable no-wildcard-imports no-unused-imports

internal class MultiSetInlineTest {
    @Test fun testMap() = runMapTests()
    @Test fun testFilter() = runFilterTests()
    @Test fun testFilterNot() = runFilterNotTests()
    @Test fun testFold() = runFoldTests()

    @Test fun testMapToSet() = runMapToSetTests()
    @Test fun testFilterToSet() = runFilterToSetTests()
    @Test fun testFilterNotToSet() = runFilterNotToSetTests()
}
