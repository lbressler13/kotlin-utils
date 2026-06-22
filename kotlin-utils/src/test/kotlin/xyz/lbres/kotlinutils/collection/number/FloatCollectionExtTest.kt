package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test
import kotlin.test.assertEquals

class FloatCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric<Float, Collection<Float>>({ it }) { it.filterNotZero() }
    @Test fun testSum() = testSumGeneric<Float, Collection<Float>>({ it }) { it.sum() }
    @Test fun testProduct() = testProductGeneric<Float, Collection<Float>>({ it }) { it.product() }
}
