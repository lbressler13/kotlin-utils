package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.internal.constants.Suppressions
import xyz.lbres.kotlinutils.iterable.ext.countElement
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.AbstractConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.combineCounts
import kotlin.math.min

/**
 * Partial [MultiSet] implementation which supports modifications to values of elements (i.e. adding elements to a mutable list).
 */
internal abstract class AbstractMultiSetImpl<E>(initialElements: Collection<E>) : MultiSet<E> {
    protected open val elements: Collection<E> = initialElements

    override val distinctValues: Set<E>
        get() = elements.toSet()

    override val size: Int
        get() = elements.size

    override fun getCountOf(element: E): Int = elements.countElement(element)

    override fun contains(element: E): Boolean = elements.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean {
        return elements.isEmpty() || CountsMap.from(this.elements).containsAll(elements)
    }

    override fun equals(other: Any?): Boolean {
        @Suppress(Suppressions.UNCHECKED_CAST)
        return tryOrDefault(false, listOf(ClassCastException::class)) {
            val counts = CountsMap.from(this.elements)
            when (other) {
                is AbstractMultiSetImpl<*> -> counts == CountsMap.from(other)
                is AbstractConstMultiSetImpl<*> -> counts == other.counts
                is MultiSet<*> -> {
                    other as MultiSet<E>
                    size == other.size && counts.distinct.all { counts.getCountOf(it) == other.getCountOf(it) }
                }
                else -> false
            }
        }
    }

    override fun plus(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(CountsMap.from(elements), other, Int::plus, useAllValues = true)
    }
    override fun minus(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(CountsMap.from(elements), other, Int::minus, useAllValues = false)
    }
    override fun intersect(other: MultiSet<E>): MultiSet<E> {
        return combineCounts(CountsMap.from(elements), other, ::min, useAllValues = false)
    }

    override fun isEmpty(): Boolean = elements.isEmpty()
    override fun iterator(): Iterator<E> = elements.toList().iterator()

    override fun toString(): String {
        val elementsString = elements.joinToString(", ")
        return "[$elementsString]"
    }

    override fun hashCode(): Int {
        val hashCode = CountsMap.from(elements).hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }
}
