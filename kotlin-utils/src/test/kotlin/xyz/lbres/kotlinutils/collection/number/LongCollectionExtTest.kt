package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class LongCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric({ it }, Collection<Long>::filterNotZero)
    @Test fun testSum() = testSumGeneric({ it }, Collection<Long>::sum)
    @Test fun testProduct() = testProductGeneric({ it }, Collection<Long>::product)
}
