package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.internal.constants.Suppressions
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

/**
 * Convert generic create set function to create set function for a specific type
 *
 * @param genericCreateSet: generic function
 * @return type-specific function
 */
fun <T> getCreateSet(genericCreateSet: (Collection<*>) -> MultiSet<*>): (Collection<T>) -> MultiSet<T> {
    return {
        @Suppress(Suppressions.UNCHECKED_CAST)
        genericCreateSet(it) as MultiSet<T>
    }
}

/**
 * Convert generic create mutable set function to create mutable set function for a specific type
 *
 * @param genericCreateMutableSet: generic function
 * @return type-specific function
 */
fun <T> getCreateMutableSet(genericCreateMutableSet: (Collection<*>) -> MutableMultiSet<*>): (Collection<T>) -> MutableMultiSet<T> {
    return {
        @Suppress(Suppressions.UNCHECKED_CAST)
        genericCreateMutableSet(it) as MutableMultiSet<T>
    }
}
