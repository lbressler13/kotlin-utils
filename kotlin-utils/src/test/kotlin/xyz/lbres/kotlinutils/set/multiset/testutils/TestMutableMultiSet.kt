package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet

/**
 * Alternate MutableMultiSet implementation to use in testing
 */
@Suppress("EqualsOrHashCode")
class TestMutableMultiSet<E>(collection: Collection<E>) : MutableMultiSet<E> {
    private val list: MutableList<E> = collection.toMutableList()
    override val size: Int = collection.size
    override val distinctValues: MutableSet<E>
        get() = list.toMutableSet()

    override fun contains(element: E): Boolean = list.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = list.containsAll(elements)
    override fun isEmpty(): Boolean = list.isEmpty()
    override fun iterator(): MutableIterator<E> = list.iterator()
    override fun getCountOf(element: E): Int = list.count { it == element }
    override fun minus(other: MultiSet<E>): MultiSet<E> = MultiSet.genericMinus(this, other)
    override fun plus(other: MultiSet<E>): MultiSet<E> = MultiSet.genericPlus(this, other)
    override fun intersect(other: MultiSet<E>): MultiSet<E> = MultiSet.genericIntersect(this, other)
    override fun add(element: E): Boolean = list.add(element)
    override fun addAll(elements: Collection<E>): Boolean = list.addAll(elements)
    override fun clear() = list.clear()
    override fun remove(element: E): Boolean = list.remove(element)
    override fun removeAll(elements: Collection<E>): Boolean = list.removeAll(elements)
    override fun retainAll(elements: Collection<E>): Boolean = list.retainAll(elements)
    override fun toString(): String = list.toString()

    override fun equals(other: Any?): Boolean {
        if (other !is MultiSet<*>) {
            return false
        }

        @Suppress("UNCHECKED_CAST")
        other as MultiSet<E>
        val counts: Map<E, Int> = list.groupBy { it }.map { it.key to it.value.size }.toMap()
        val otherCounts: Map<E, Int> = other.distinctValues.associateWith { other.getCountOf(it) }
        return counts == otherCounts
    }
}
