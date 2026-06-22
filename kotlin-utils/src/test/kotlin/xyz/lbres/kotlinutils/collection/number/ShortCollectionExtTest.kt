package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class ShortCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric({ it }, Collection<Short>::filterNotZero)
    @Test fun testSum() = testSumGeneric({ it }, Collection<Short>::sum)
    @Test fun testProduct() = testProductGeneric({ it }, Collection<Short>::product)
}
