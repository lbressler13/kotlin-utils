package xyz.lbres.kotlinutils.set.multiset.testutils

import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * Alternate MultiSet implementation to use in testing
 */
@Suppress("EqualsOrHashCode")
class TestMultiSet<E>(collection: Collection<E>) : MultiSet<E> {
    private val list: List<E> = collection.toList()
    override val size: Int = collection.size
    override val distinctValues: Set<E> = collection.toSet()

    override fun contains(element: E): Boolean = list.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = list.containsAll(elements)
    override fun isEmpty(): Boolean = list.isEmpty()
    override fun iterator(): Iterator<E> = list.iterator()
    override fun getCountOf(element: E): Int = list.count { it == element }
    override fun toString(): String = list.toString()

    override fun equals(other: Any?): Boolean {
        if (other !is MultiSet<*>) {
            return false
        }

        @Suppress("UNCHECKED_CAST")
        other as MultiSet<E>
        val counts = list.groupBy { it }.map { it.key to it.value.size }.toMap()
        val otherCounts = other.distinctValues.associateWith { other.getCountOf(it) }
        return counts == otherCounts
    }
}