package xyz.lbres.kotlinutils.collection.multiset.impl

import xyz.lbres.kotlinutils.collection.multiset.IConstMultiSet
import xyz.lbres.kotlinutils.collection.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.generic.ifNull

// final implementation of ConstMultiSet
internal class ConstMultiSetImpl<E>(private val elements: Collection<E>, initialCounts: CountsMap<E>? = null) : IConstMultiSet<E>() {
    override val size: Int = elements.size
    override val distinctValues: Set<E>
    private val string: String
    override val counts: CountsMap<E>

    init {
        counts = initialCounts.ifNull { CountsMap.from(elements) }
        distinctValues = counts.distinct
        string = "[${elements.joinToString()}]"
    }

    fun toCountsMap(): CountsMap<E> = counts
    override fun iterator(): Iterator<E> = elements.iterator()
    override fun toString(): String = string
}
