package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.general.tryOrDefault
import kotlin.math.min

sealed class AbstractConstMultiSet<E> : MultiSet<E> {
    protected abstract val counts: Map<E, Int>

    override fun getCountOf(element: E): Int = counts.getOrDefault(element, 0)

    override fun contains(element: E): Boolean = counts.contains(element)

    override fun containsAll(elements: Collection<E>): Boolean {
        val otherSet = ConstMultiSet(elements) as AbstractConstMultiSet<E>
        val elementsCounts = otherSet.counts

        return elementsCounts.all {
            it.value <= getCountOf(it.key)
        }
    }

    /**
     * If two MultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any]?
     * @return [Boolean]: `true` if [other] is a non-null MultiSet which contains the same values as the current set, `false` otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        return tryOrDefault(false) {
            @Suppress("UNCHECKED_CAST")
            other as MultiSet<E>

            distinctValues == other.distinctValues && distinctValues.all { getCountOf(it) == other.getCountOf(it) }
        }
    }

    override operator fun plus(other: MultiSet<E>): MultiSet<E> {
        val values = distinctValues + other.distinctValues

        val newCounts = values.associateWith {
            getCountOf(it) + other.getCountOf(it)
        }
        return createFromCounts(newCounts)
    }

    override operator fun minus(other: MultiSet<E>): MultiSet<E> {
        val values = distinctValues + other.distinctValues

        val newCounts = values.associateWith {
            getCountOf(it) - other.getCountOf(it)
        }.filter { it.value > 0 }

        return createFromCounts(newCounts)
    }

    override infix fun intersect(other: MultiSet<E>): MultiSet<E> {
        val values = distinctValues intersect other.distinctValues

        val newCounts = values.associateWith {
            min(getCountOf(it), other.getCountOf(it))
        }
        return createFromCounts(newCounts)
    }

    /**
     * Initialize a new MultiSet from existing counts.
     */
    protected abstract fun createFromCounts(counts: Map<E, Int>): MultiSet<E>

    override fun isEmpty(): Boolean = size == 0

    override fun toString(): String {
        val elementsString = mapConsistent { it.toString() }.joinToString(", ")
        return "[$elementsString]"
    }

    override fun hashCode(): Int {
        val hashCode = counts.hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }
}
