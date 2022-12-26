package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.utils.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

internal class MultiSetUtilsTest {
    @Test fun testMultiSetOf() = runMultiSetOfTests()
    @Test fun testMutableMultiSetOf() = runMutableMultiSetOfTests()
    @Test fun testEmptyMultiSet() = runEmptyMultiSetTests()
    @Test fun testMultiSet() = runMultiSetTests()
    @Test fun testMutableMultiSet() = runMutableMultiSetTests()

    @Test fun testDefaultPlus() = runDefaultPlusTests()
    @Test fun testDefaultMinus() = runDefaultMinusTests()
    @Test fun testDefaultIntersect() = runDefaultIntersectTests()
}
