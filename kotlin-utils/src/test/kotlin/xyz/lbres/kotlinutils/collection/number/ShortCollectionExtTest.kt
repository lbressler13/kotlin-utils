package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class ShortCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric<Short, Collection<Short>>({ it }) { it.filterNotZero() }
    @Test fun testSum() = testSumGeneric<Short, Collection<Short>>({ it }) { it.sum() }
    @Test fun testProduct() = testProductGeneric<Short, Collection<Short>>({ it }) { it.product() }
}
