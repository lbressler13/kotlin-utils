package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.AbstractMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.combineCounts
import kotlin.math.min

/**
 * Common functionality for ConstMultiSet implementations
 */
internal interface AbstractConstMultiSetImpl<E> {
    val counts: CountsMap<E>
    val size: Int

//    fun plus(other: MultiSet<E>): MultiSet<E> {
//        return combineCounts(counts, other, Int::plus, true, const = false)
//    }
//    fun minus(other: MultiSet<E>): MultiSet<E> {
//        return combineCounts(counts, other, Int::minus, false, const = false)
//    }
//    fun intersect(other: MultiSet<E>): MultiSet<E> {
//        return combineCounts(counts, other, ::min, false, const = false)
//    }
//
//    fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
//        return combineCounts(counts, other, Int::plus, true, const = true) as ConstMultiSet<E>
//    }
//    fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> {
//        return combineCounts(counts, other, Int::minus, false, const = true) as ConstMultiSet<E>
//    }
//    fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> {
//        return combineCounts(counts, other, ::min, false, const = true) as ConstMultiSet<E>
//    }
}
