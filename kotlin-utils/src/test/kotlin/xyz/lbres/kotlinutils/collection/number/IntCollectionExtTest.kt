package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class IntCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric<Int, Collection<Int>>({ it }) { it.filterNotZero() }
    @Test fun testSum() = testSumGeneric<Int, Collection<Int>>({ it }) { it.sum() }
    @Test fun testProduct() = testProductGeneric<Int, Collection<Int>>({ it }) { it.product() }
}
