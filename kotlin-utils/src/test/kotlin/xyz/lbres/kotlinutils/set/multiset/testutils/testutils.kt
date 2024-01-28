package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.test.assertEquals

/**
 * Run single test to mutate set
 *
 * @param set [MutableMultiSet]<T>: set to be modified in operation
 * @param expected [MutableMultiSet]<T>: expected state of set after operation
 * @param success [Boolean]: if operation is expected to succeed
 * @param op () -> Boolean: operation to perform
 */
fun <T> runSingleMutateTest(
    set: MutableMultiSet<T>,
    expected: MultiSet<T>,
    success: Boolean = true,
    op: () -> Boolean
) {
    val result = op()
    assertEquals(success, result)
    assertEquals(expected, set)
    assertEquals(expected.size, set.size)
}
