package xyz.lbres.kotlinutils.set.multiset

/**
 * Multi set implementation where values are assumed to be constant.
 * Behavior is not defined if mutable values are changed.
 */
open class ConstMultiSet<E> : MultiSet<E> {
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
    internal constructor(elements: Collection<E>) {
        manager = ConstManager(elements, false)
    }

    /**
     * Initialize set from existing counts.
     */
    internal constructor(counts: Map<E, Int>) {
        manager = ConstManager(counts, false)
    }

    override fun getCountOf(element: E): Int = manager.getCountOf(element)

    override fun contains(element: E): Boolean = manager.contains(element)

    override fun containsAll(elements: Collection<E>): Boolean = manager.containsAll(elements)

    override operator fun plus(other: MultiSet<E>): MultiSet<E> = manager.plus(other)
    override operator fun minus(other: MultiSet<E>): MultiSet<E> = manager.minus(other)
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> = manager.intersect(other)

    override fun isEmpty(): Boolean = manager.isEmpty()

    override fun equals(other: Any?): Boolean = manager.eq(other)

    override fun iterator(): Iterator<E> = manager.getImmutableIterator()

    override fun hashCode(): Int = manager.hashCode()
    override fun toString(): String = manager.toString()
}
