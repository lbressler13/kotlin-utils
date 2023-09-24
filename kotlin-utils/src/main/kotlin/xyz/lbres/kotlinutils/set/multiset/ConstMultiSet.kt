package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import kotlin.math.min

/**
 * [MultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed.
 */
abstract class ConstMultiSet<E>(initialElements: Collection<E>) : MultiSet<E> {
    /**
     * Number of elements in set.
     */
    protected var _size: Int = initialElements.size
    override val size: Int
        get() = _size
    // get() = constManager.size

    /**
     * All distinct values contained in the set.
     */
    override val distinctValues: Set<E> = createCounts(initialElements).keys
    // get() = constManager.distinctValues

    protected open val string: String = createString(createCounts(initialElements))

    protected open val elements: Collection<E> = initialElements

    protected open val counts: Map<E, Int> = createCounts(initialElements)

    /**
     * Manager for the set.
     */
    // abstract val manager: MultiSetManager<E>
    // private val constManager: ConstMultiSetManager<E>
    // get() = manager as ConstMultiSetManager<E>

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    // override fun getCountOf(element: E): Int = constManager.getCountOf(element)
    override fun getCountOf(element: E): Int = counts.getOrDefault(element, 0)

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if [element] is in the set, `false` otherwise
     */
    // override fun contains(element: E): Boolean = constManager.contains(element)
    override fun contains(element: E): Boolean = counts.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current set.
     * If the collection contains multiple occurrences of the same value, the function will check if this set contains at least as many occurrences as the collection.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if the current set contains at least as many occurrences of each value as the collection, `false` otherwise
     */
    // override fun containsAll(elements: Collection<E>): Boolean = constManager.containsAll(elements)
    override fun containsAll(elements: Collection<E>): Boolean {
        val otherCounts = createCounts(elements)

        return otherCounts.all {
            it.value <= getCountOf(it.key)
        }
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this set.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this set
     * @return [MultiSet]<E>: MultiSet containing all values from both sets
     */
    // override operator fun plus(other: MultiSet<E>): MultiSet<E> = constManager.plus(other)
    override operator fun plus(other: MultiSet<E>): MultiSet<E> {
        val values = distinctValues + other.distinctValues

        val newCounts = values.associateWith {
            getCountOf(it) + other.getCountOf(it)
        }

        return MultiSetImpl(newCounts)
    }

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this set.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: values to subtract from this set
     * @return [MultiSet]<E>: MultiSet containing the items in this set but not the other
     */
    // override operator fun minus(other: MultiSet<E>): MultiSet<E> = constManager.minus(other)
    override operator fun minus(other: MultiSet<E>): MultiSet<E> {
        val values = distinctValues + other.distinctValues

        val newCounts = values.associateWith {
            getCountOf(it) - other.getCountOf(it)
        }.filter { it.value > 0 }

        return MultiSetImpl(newCounts)
    }

    /**
     * Create a new MultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: MultiSet to intersect with current
     * @return [MultiSet]<E>: MultiSet containing only values that are in both set
     */
    // override infix fun intersect(other: MultiSet<E>): MultiSet<E> = constManager.intersect(other)
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> {
        val values = distinctValues intersect other.distinctValues

        val newCounts = values.associateWith {
            min(getCountOf(it), other.getCountOf(it))
        }

        return MultiSetImpl(newCounts)
    }

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    // override fun isEmpty(): Boolean = constManager.isEmpty()
    override fun isEmpty(): Boolean = size == 0

    /**
     * If the current set contains the same elements as another MultiSet, with the same number of occurrences per element.
     *
     * @param other [Any]?
     * @return [Boolean]: `true` if [other] is a non-null MultiSet which contains the same values as the current set, `false` otherwise
     */
    // override fun equals(other: Any?): Boolean = constManager.eq(other)
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

    /**
     * Get hash code for set.
     *
     * @return [Int]
     */
    // override fun hashCode(): Int = constManager.hashCode()

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    // override fun toString(): String = constManager.toString()
    override fun toString(): String = string

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    // override fun iterator(): Iterator<E> = constManager.iterator()
    override fun iterator(): Iterator<E> = elements.iterator()

    protected fun createCounts(values: Collection<E>): Map<E, Int> {
        val newCounts: MutableMap<E, Int> = mutableMapOf()

        values.forEach {
            newCounts[it] = newCounts.getOrDefault(it, 0) + 1
        }

        return newCounts
    }

    protected fun createString(values: Map<E, Int>): String {
        if (values.isEmpty() || values.values.all { it == 0 }) {
            return "[]"
        }

        var elementsString = ""
        values.forEach { (value, count) ->
            elementsString += "$value, ".repeat(count)
        }
        elementsString = elementsString.substring(0 until elementsString.lastIndex - 1)

        return "[$elementsString]"
    }

    override fun hashCode(): Int {
        val hashCode = counts.hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }
}
