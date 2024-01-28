package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions

/**
 * Convert generic create set function to create set function for a specific type
 *
 * @param genericCreateSet: generic function
 * @return type-specific function
 */
fun <T> getCreateConstSet(genericCreateSet: (Collection<*>) -> ConstMultiSet<*>): (Collection<T>) -> ConstMultiSet<T> {
    return {
        @Suppress(Suppressions.UNCHECKED_CAST)
        genericCreateSet(it) as ConstMultiSet<T>
    }
}
