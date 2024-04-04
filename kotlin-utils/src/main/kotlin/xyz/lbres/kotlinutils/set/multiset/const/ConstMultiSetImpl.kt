package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.generic.ext.ifNull
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap

// final implementation of ConstMultiSet
internal class ConstMultiSetImpl<E>(private val elements: Collection<E>, initialCounts: CountsMap<E>? = null) : ConstMultiSet<E>() {
    override val size: Int = elements.size
    override val distinctValues: Set<E>
    private val string: String
    override val counts: CountsMap<E>

    init {
        counts = initialCounts.ifNull { CountsMap.from(elements) }
        distinctValues = counts.distinct
        string = counts.toString()
    }

    override fun iterator(): Iterator<E> = elements.iterator()
    override fun toString(): String = string
}
