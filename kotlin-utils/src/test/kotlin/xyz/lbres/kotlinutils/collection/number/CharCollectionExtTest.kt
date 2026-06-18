package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.testutils.number.testFilterNotZeroGeneric
import xyz.lbres.kotlinutils.testutils.number.testProductGeneric
import xyz.lbres.kotlinutils.testutils.number.testSumGeneric
import kotlin.test.Test

typealias CharColl = Collection<Char>

class CharCollectionExtTest {
    @Test fun testFilterNotZero() = testFilterNotZeroGeneric<Char, CharColl>({ it }) { it.filterNotZero() }
    @Test fun testSum() = testSumGeneric<Char, CharColl>({ it }) { it.sum() }
    @Test fun testProduct() = testProductGeneric<Char, CharColl>({ it }) { it.product() }
}
