package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager

/**
 * Multi set implementation where values are assumed to be constant.
 * Behavior is not defined if mutable values are changed.
 */
abstract class ConstMultiSet<E> protected constructor() : MultiSet<E> {
    override val size: Int
        get() = manager.size

    override val distinctValues: Set<E>
        get() = manager.distinctValues

    abstract val manager: ConstMultiSetManager<E>

    override fun getCountOf(element: E): Int = manager.getCountOf(element)
    override fun contains(element: E): Boolean = manager.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = manager.containsAll(elements)

    override operator fun plus(other: MultiSet<E>): MultiSet<E> = manager.plus(other)
    override operator fun minus(other: MultiSet<E>): MultiSet<E> = manager.minus(other)
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> = manager.intersect(other)

    override fun isEmpty(): Boolean = manager.isEmpty()

    override fun equals(other: Any?): Boolean = manager.eq(other)
    override fun hashCode(): Int = manager.hashCode()
    override fun toString(): String = manager.toString()

    override fun iterator(): Iterator<E> = manager.getImmutableIterator()
}
