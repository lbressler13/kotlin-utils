package xyz.lbres.kotlinutils.set.multiset.utils

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.AbstractMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import kotlin.math.min

internal fun <E> performPlus(counts: CountsMap<E>, other: MultiSet<E>, const: Boolean = false): MultiSet<E> {
    return combineCounts(counts, other, Int::plus, true, const)
}

internal fun <E> performMinus(counts: CountsMap<E>, other: MultiSet<E>, const: Boolean = false): MultiSet<E> {
    return combineCounts(counts, other, Int::minus, false, const)
}

internal fun <E> performIntersect(counts: CountsMap<E>, other: MultiSet<E>, const: Boolean = false): MultiSet<E> {
    return combineCounts(counts, other, ::min, false, const)
}

/**
 * Combine counts map and MultiSet, using the given operation
 *
 * @param counts [CountsMap]<E>: counts map
 * @param multiset [MultiSet]<E>: MultiSet to combine with
 * @param operation (Int, Int) -> Int: combination function
 * @param useAllValues [Boolean]: if all values from the map and the set should be used to generate the new set. If `false`, only the values from the counts map will be used.
 * @param const [Boolean]: if the returned MultiSet should be a ConstMultiSet. Defaults to `false`
 * @return [MultiSet]<E>: new set where each element has the number of occurrences specified by the operation. If [const] is `true`, the set will be a const multi set.
 */
private fun <E> combineCounts(counts: CountsMap<E>, multiset: MultiSet<E>, operation: (Int, Int) -> Int, useAllValues: Boolean, const: Boolean = false): MultiSet<E> {
    var getOtherCount = multiset::getCountOf
    var otherDistinct = multiset::distinctValues

    // increase efficiency for AbstractMultiSetImpl
    if (multiset is AbstractMultiSetImpl<E>) {
        val otherCounts = CountsMap.from(multiset)
        getOtherCount = otherCounts::getCountOf
        otherDistinct = otherCounts::distinct
    }

    val values: Set<E> = simpleIf(useAllValues, { counts.distinct + otherDistinct() }, { counts.distinct })
    val newCounts: MutableMap<E, Int> = mutableMapOf()
    val newElements: MutableList<E> = mutableListOf()

    values.forEach { value ->
        val count = operation(counts.getCountOf(value), getOtherCount(value))
        if (count > 0) {
            newCounts[value] = count
            repeat(count) { newElements.add(value) }
        }
    }

    return simpleIf(const, { ConstMultiSetImpl(newElements, CountsMap(newCounts)) }, { MultiSetImpl(newElements) })
}
