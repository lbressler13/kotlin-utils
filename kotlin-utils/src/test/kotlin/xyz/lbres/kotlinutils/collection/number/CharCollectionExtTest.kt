package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

class CharCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric<Char, Collection<Char>>({ it }) { it.filterNotZero() }
    @Test fun testSum() = testSumGeneric<Char, Collection<Char>>({ it }) { it.sum() }
    @Test fun testProduct() = testProductGeneric<Char, Collection<Char>>({ it }) { it.product() }
}
