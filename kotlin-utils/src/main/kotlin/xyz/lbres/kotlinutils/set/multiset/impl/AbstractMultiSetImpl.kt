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
    private val immutableTypes = setOf(
        Byte::class,
        Char::class,
        Short::class,
        Int::class,
        Long::class,
        Float::class,
        Double::class,
        Boolean::class,
        String::class,
    )

    protected open val elements: Collection<E> = initialElements
    protected open val immutableElements: Collection<E> = initialElements.filter { isImmutable(it) }
    protected open val mutableElements: Collection<E> = initialElements.filterNot { isImmutable(it) }

    protected var immutableCounts: CountsMap<E> = CountsMap.from(initialElements.filter { isImmutable(it) })
    protected val mutableCounts: CountsMap<E>
        get() = CountsMap.from(mutableElements)

    override val distinctValues: Set<E>
        get() = elements.toSet()

    override val size: Int
        get() = elements.size

    protected val counts: CountsMap<E>
        get() = CountsMap.from(elements)

    override fun getCountOf(element: E): Int {
        if (isImmutable(element)) {
            return immutableCounts.getCountOf(element)
        }
        return mutableElements.countElement(element)
    }

    override fun contains(element: E): Boolean {
        return (isImmutable(element) && immutableCounts.contains(element)) || mutableElements.contains(element)
    }
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

    protected fun isImmutable(element: E) = element == null || element!!::class in immutableTypes

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
