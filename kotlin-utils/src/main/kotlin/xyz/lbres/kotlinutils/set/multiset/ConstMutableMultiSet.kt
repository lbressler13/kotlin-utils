package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.collection.ext.toMutableMultiSet

class ConstMutableMultiSet<E> : MutableMultiSet<E>, ConstMultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int
        get() = manager.size

    override val distinctValues: Set<E>
        get() = manager.distinctValues

    private val manager: ConstManager<E>

    /**
     * Initialize set from a collection of values.
     */
    internal constructor(elements: Collection<E>) : super(elements) {
        manager = ConstManager(elements, true)
    }

    /**
     * Initialize set from existing counts.
     */
    constructor(counts: Map<E, Int>) : super(counts) {
        manager = ConstManager(counts, true)
    }

    override fun getCountOf(element: E): Int = manager.getCountOf(element)

    override fun contains(element: E): Boolean = manager.contains(element)

    override fun containsAll(elements: Collection<E>): Boolean = manager.containsAll(elements)

    override fun add(element: E): Boolean = manager.add(element)
    override fun addAll(elements: Collection<E>): Boolean = manager.addAll(elements)
    override fun remove(element: E): Boolean = manager.remove(element)
    override fun removeAll(elements: Collection<E>): Boolean = manager.removeAll(elements)
    override fun retainAll(elements: Collection<E>): Boolean = manager.retainAll(elements)

    override fun clear() {
        manager.clear()
    }

    override operator fun minus(other: MultiSet<E>): MutableMultiSet<E> {
        return manager.minus(other).toMutableMultiSet()
    }

    override operator fun plus(other: MultiSet<E>): MutableMultiSet<E> {
        return manager.plus(other).toMutableMultiSet()
    }

    override infix fun intersect(other: MultiSet<E>): MutableMultiSet<E> {
        return manager.intersect(other).toMutableMultiSet()
    }

    override fun isEmpty(): Boolean = manager.isEmpty()

    override fun equals(other: Any?): Boolean = manager.eq(other)

    override fun hashCode(): Int = manager.hashCode()
    override fun toString(): String = manager.toString()

    override fun iterator(): MutableIterator<E> = manager.getMutableIterator()
}
