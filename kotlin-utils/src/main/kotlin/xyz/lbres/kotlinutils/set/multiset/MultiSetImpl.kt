package xyz.lbres.kotlinutils.set.multiset

import kotlin.math.max
import kotlin.math.min

/**
 * Set implementation that allows multiple occurrences of the same value.
 */
internal class MultiSetImpl<E> : MultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int

    /**
     * All distinct values contained in the MultiSet, without any counts
     */
    override val distinctValues: Set<E>

    /**
     * Store the number of occurrences of each element in set.
     * Counts are guaranteed to be greater than 0.
     */
    private val countsMap: Map<E, Int>

    /**
     * The initial elements that were passed to the constructor.
     */
    private val initialElements: Collection<E>

    /**
     * String representation of the set.
     */
    private val string: String

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        size = elements.size
        initialElements = elements
        string = createString()

        // init counts
        val mutableMap: MutableMap<E, Int> = mutableMapOf()
        val mutableValues: MutableSet<E> = mutableSetOf()

        for (element in elements) {
            val currentCount = mutableMap[element] ?: 0
            mutableMap[element] = currentCount + 1
            mutableValues.add(element)
        }

        // cast to simpler data structures
        countsMap = mutableMap.toMap()
        distinctValues = mutableValues.toSet()
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: Map<E, Int>) {
        countsMap = counts
        size = counts.values.fold(0, Int::plus)
        distinctValues = counts.keys

        initialElements = counts.flatMap {
            val element = it.key
            val count = it.value
            List(count) { element }
        }

        string = createString()
    }

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: true if [element] is in the set, false otherwise
     */
    override fun contains(element: E): Boolean = countsMap.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current set.
     * If [elements] contains multiple occurrences of the same value, the function will check if this set contains at least as many occurrences as [elements].
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: true if the current set contains at least as many occurrences of each value as [elements], false otherwise
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val newSet = MultiSetImpl(elements)
        return newSet.countsMap.all { countsMap.contains(it.key) && it.value <= getCountOf(it.key) }
    }

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this MultiSet.
     *
     * @param other [MultiSet]<[E]>: MultiSet to subtract from current
     * @return [MultiSet]<[E]>: MultiSet containing the items in this MultiSet but not the other
     */
    override operator fun minus(other: MultiSet<E>): MultiSet<E> {
        val newCounts = countsMap.keys.associateWith {
            val count = getCountOf(it)
            val otherCount = other.getCountOf(it)
            max(count - otherCount, 0)
        }.filter { it.value > 0 }.toMap()

        return MultiSetImpl(newCounts)
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<[E]>: MultiSet to add to current
     * @return [MultiSet]<[E]>: MultiSet containing all values from both MultiSets
     */
    override operator fun plus(other: MultiSet<E>): MultiSet<E> {
        val allValues = distinctValues + other.distinctValues

        val newCounts = allValues.associateWith {
            getCountOf(it) + other.getCountOf(it)
        }

        return MultiSetImpl(newCounts)
    }

    /**
     * Create a new MultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     *
     * @param other [MultiSet]<[E]>: MultiSet to intersect with current
     * @return [MultiSet]<[E]>: MultiSet containing only values that are in both MultiSets
     */
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> {
        val allValues = distinctValues + other.distinctValues

        val newCounts = allValues.associateWith {
            val count = getCountOf(it)
            val otherCount = other.getCountOf(it)
            min(count, otherCount)
        }.filter { it.value > 0 }.toMap()

        return MultiSetImpl(newCounts)
    }

    override fun <T> map(mapFunction: (E) -> T): MultiSet<T> {
        val newCounts: MutableMap<T, Int> = mutableMapOf()

        countsMap.forEach {
            val mappedValue = mapFunction(it.key)
            val count = it.value
            newCounts[mappedValue] = count + newCounts.getOrDefault(mappedValue, 0)
        }

        return MultiSetImpl(newCounts)
    }

    override fun filter(filterFunction: (E) -> Boolean): MultiSet<E> {
        val newCounts = countsMap.filterKeys(filterFunction)
        return MultiSetImpl(newCounts)
    }

    override fun filterNot(filterFunction: (E) -> Boolean): MultiSet<E> {
        val newCounts = countsMap.filterKeys { !filterFunction(it) }
        return MultiSetImpl(newCounts)
    }

    override fun <T> fold(initialValue: T, foldFunction: (T, E) -> T): T {
        var acc = initialValue

        countsMap.forEach {
            val value = it.key
            val count = it.value
            repeat(count) { acc = foldFunction(acc, value) }
        }

        return acc
    }

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    override fun isEmpty(): Boolean = countsMap.isEmpty()

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int = countsMap.getOrDefault(element, 0)

    /**
     * If two ImmutableMultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any?]
     * @return [Boolean]: true if [other] is a non-null ImmutableMultiSet which contains the same values as the current set, false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        return try {
            other as MultiSet<E>
            return minus(other).distinctValues.isEmpty() && other.minus(this).distinctValues.isEmpty()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Create the static string representation of the set.
     * Stored in a helper so it can be reused in both constructors.
     */
    private fun createString(): String {
        if (initialElements.isEmpty()) {
            return "[]"
        }

        val elementsString = initialElements.joinToString(", ")
        return "[$elementsString]"
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator<E>]
     */
    override fun iterator(): Iterator<E> = initialElements.iterator()

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String = string

    override fun hashCode(): Int = listOf(javaClass.name, countsMap).hashCode()
}
