package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class IntCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric({ it }, Collection<Int>::filterNotZero)
    @Test fun testSum() = testSumGeneric({ it }, Collection<Int>::sum)
    @Test fun testProduct() = testProductGeneric({ it }, Collection<Int>::product)
}
