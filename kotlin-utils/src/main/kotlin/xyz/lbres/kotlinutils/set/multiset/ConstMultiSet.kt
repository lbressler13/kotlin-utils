package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.manager.ConstMultiSetManager
import xyz.lbres.kotlinutils.set.multiset.manager.MultiSetManager

/**
 * Multi set implementation where values are assumed to be constant.
 * Behavior is not defined if mutable values are changed.
 */
abstract class ConstMultiSet<E> protected constructor() : MultiSet<E> {
    private val constManager: ConstMultiSetManager<E>
        get() = manager as ConstMultiSetManager<E>

    override val size: Int
        get() = constManager.size

    override val distinctValues: Set<E>
        get() = constManager.distinctValues

    abstract val manager: MultiSetManager<E>

    override fun getCountOf(element: E): Int = constManager.getCountOf(element)
    override fun contains(element: E): Boolean = constManager.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = constManager.containsAll(elements)

    override operator fun plus(other: MultiSet<E>): MultiSet<E> = constManager.plus(other)
    override operator fun minus(other: MultiSet<E>): MultiSet<E> = constManager.minus(other)
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> = constManager.intersect(other)

    override fun isEmpty(): Boolean = constManager.isEmpty()

    override fun equals(other: Any?): Boolean = constManager.eq(other)
    override fun hashCode(): Int = constManager.hashCode()
    override fun toString(): String = constManager.toString()

    override fun iterator(): Iterator<E> = constManager.getImmutableIterator()
}
