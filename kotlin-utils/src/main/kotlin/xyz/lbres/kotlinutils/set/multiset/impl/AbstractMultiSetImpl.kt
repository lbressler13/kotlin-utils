package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.iterable.ext.countElement
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.performIntersect
import xyz.lbres.kotlinutils.set.multiset.utils.performMinus
import xyz.lbres.kotlinutils.set.multiset.utils.performPlus

/**
 * Partial [MultiSet] implementation which supports modifications to values of elements (i.e. adding elements to a mutable list).
 */
internal abstract class AbstractMultiSetImpl<E>(initialElements: Collection<E>) : MultiSet<E> {
    protected open val elements: Collection<E> = initialElements

    override val distinctValues: Set<E>
        get() = elements.toSet()

    override val size: Int
        get() = elements.size

    protected val counts: CountsMap<E>
        get() = CountsMap.from(elements)

    override fun getCountOf(element: E): Int = elements.countElement(element)

    override fun contains(element: E): Boolean = elements.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean {
        // skip generating counts map if elements is empty
        return elements.isEmpty() || counts.containsAll(elements)
    }

    override fun equals(other: Any?): Boolean {
        return tryOrDefault(false, listOf(ClassCastException::class)) {
            when (other) {
                is AbstractMultiSetImpl<*> -> counts == other.counts
                is ConstMultiSetImpl<*> -> counts == other.toCountsMap()
                is ConstMutableMultiSetImpl<*> -> counts == other.toCountsMap()
                is MultiSet<*> -> {
                    @Suppress(Suppressions.UNCHECKED_CAST)
                    other as MultiSet<E>
                    size == other.size && counts.distinct.all { counts.getCountOf(it) == other.getCountOf(it) }
                }
                else -> false
            }
        }
    }

    override fun plus(other: MultiSet<E>): MultiSet<E> = performPlus(counts, other)
    override fun minus(other: MultiSet<E>): MultiSet<E> = performMinus(counts, other)
    override fun intersect(other: MultiSet<E>): MultiSet<E> = performIntersect(counts, other)

    override fun isEmpty(): Boolean = elements.isEmpty()
    override fun iterator(): Iterator<E> = elements.toList().iterator()

    override fun toString(): String {
        val elementsString = elements.joinToString(", ")
        return "[$elementsString]"
    }

    override fun hashCode(): Int {
        val hashCode = counts.hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }
}
