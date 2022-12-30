package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.inline.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

internal class MultiSetInlineMethodsTest {
    @Test fun testMap() = runMapTests()
    @Test fun testFilter() = runFilterTests()
    @Test fun testFilterNot() = runFilterNotTests()
    @Test fun testFold() = runFoldTests()

    @Test fun testMapToSet() = runMapToSetTests()
    @Test fun testFilterToSet() = runFilterToSetTests()
    @Test fun testFilterNotToSet() = runFilterNotToSetTests()

    @Test fun testAny() = runAnyTests()
    @Test fun testAll() = runAllTests()
    @Test fun testNone() = runNoneTests()

    @Test fun testMinBy() = runMinByTests()
    @Test fun testMaxBy() = runMaxByTests()
}
