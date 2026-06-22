package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class LongCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric<Long, Collection<Long>>({ it }) { it.filterNotZero() }
    @Test fun testSum() = testSumGeneric<Long, Collection<Long>>({ it }) { it.sum() }
    @Test fun testProduct() = testProductGeneric<Long, Collection<Long>>({ it }) { it.product() }
}
