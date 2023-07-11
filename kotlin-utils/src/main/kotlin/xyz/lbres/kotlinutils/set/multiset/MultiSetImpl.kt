package xyz.lbres.kotlinutils.set.multiset

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
     * Mutable parameter for all distinct values contained in the MultiSet, without any counts
     */
    private var _distinctValues: Set<E> = emptySet()

    /**
     * All distinct values contained in the MultiSet, without any counts
     */
    override val distinctValues: Set<E>
        get() {
            updateValues()
            return _distinctValues
        }

    /**
     * Store the number of occurrences of each element in set.
     * Counts are guaranteed to be greater than 0.
     */
    private var countsMap: Map<E, Int>

    /**
     * The initial elements that were passed to the constructor.
     */
    private val initialElements: Collection<E>

    /**
     * Store the hash codes for all the values in the set.
     * Used to determine if any mutable values have changed.
     */
    private var hashCodes: Map<Int, Int>

    /**
     * String representation of the set.
     */
    private var string: String

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        size = elements.size
        initialElements = elements
        string = createString()

        countsMap = elements.groupBy { it }.map { it.key to it.value.size }.toMap()
        hashCodes = getCurrentHashCodes()
        _distinctValues = countsMap.keys
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: Map<E, Int>) {
        countsMap = counts
        size = counts.values.fold(0, Int::plus)
        _distinctValues = counts.keys

        initialElements = counts.flatMap {
            val element = it.key
            val count = it.value
            List(count) { element }
        }

        string = createString()
        hashCodes = getCurrentHashCodes()
    }

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if [element] is in the set, `false` otherwise
     */
    override fun contains(element: E): Boolean {
        updateValues()
        return countsMap.contains(element)
    }

    /**
     * Determine if all elements in a collection are contained in the current set.
     * If [elements] contains multiple occurrences of the same value, the function will check if this set contains at least as many occurrences as [elements].
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if the current set contains at least as many occurrences of each value as [elements], `false` otherwise
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        updateValues()
        val newSet = MultiSetImpl(elements)
        // TODO wrapper to only call updateValues once
        return newSet.distinctValues.all {
            getCountOf(it) > 0 && newSet.getCountOf(it) <= getCountOf(it)
        }
    }

    /**
     * Create a new MultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: MultiSet to subtract from current
     * @return [MultiSet]<E>: MultiSet containing the items in this MultiSet but not the other
     */
    override operator fun minus(other: MultiSet<E>): MultiSet<E> {
        updateValues()

        val newCounts = distinctValues.associateWith {
            getCountOf(it) - other.getCountOf(it)
        }.filter { it.value > 0 }

        return MultiSetImpl(newCounts)
    }

    /**
     * Create a new MultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this MultiSet.
     *
     * @param other [MultiSet]<E>: MultiSet to add to current
     * @return [MultiSet]<E>: MultiSet containing all values from both MultiSets
     */
    override operator fun plus(other: MultiSet<E>): MultiSet<E> {
        updateValues()
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
     * @param other [MultiSet]<E>: MultiSet to intersect with current
     * @return [MultiSet]<E>: MultiSet containing only values that are in both MultiSets
     */
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> {
        updateValues()
        val allValues = distinctValues + other.distinctValues

        val newCounts = allValues.associateWith {
            val count = getCountOf(it)
            val otherCount = other.getCountOf(it)
            min(count, otherCount)
        }.filter { it.value > 0 }

        return MultiSetImpl(newCounts)
    }

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: `true` if the set contains 0 elements, `false` otherwise
     */
    override fun isEmpty(): Boolean = countsMap.isEmpty()

    /**
     * Get the number of occurrences of a given element.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int {
        updateValues()
        return countsMap.getOrDefault(element, 0)
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

        updateValues()
        return try {
            @Suppress("UNCHECKED_CAST")
            other as MultiSet<E>

            if (other is MultiSetImpl<*>) {
                other.updateValues()
                return countsMap == other.countsMap
            }

            // less efficient equality check
            val otherDistinct = other.distinctValues
            return distinctValues == otherDistinct && distinctValues.all { getCountOf(it) == other.getCountOf(it) }
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Create the static string representation of the set.
     * Stored in a helper so it can be reused in both constructors.
     *
     * @return [String]
     */
    private fun createString(): String {
        if (initialElements.isEmpty()) {
            return "[]"
        }

        val elementsString = initialElements.joinToString(", ")
        return "[$elementsString]"
    }

    /**
     * If the hash code values are changed, update all properties related to the values of the set
     */
    private fun updateValues() {
        val currentCodes = getCurrentHashCodes()
        if (currentCodes != hashCodes) {
            string = createString()

            countsMap = initialElements.groupBy { it }.map { it.key to it.value.size }.toMap()
            hashCodes = currentCodes
            _distinctValues = countsMap.keys
        }
    }

    /**
     * Get hash codes for the elements
     *
     * @return [Map]<Int, Int>: hash codes for all elements in the set
     */
    private fun getCurrentHashCodes(): Map<Int, Int> {
        val hashCodeCounts: MutableMap<Int, Int> = mutableMapOf()

        initialElements.forEach {
            val code = it.hashCode()
            hashCodeCounts[code] = (hashCodeCounts[code] ?: 0) + 1
        }

        return hashCodeCounts
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): Iterator<E> {
        // new iterator
        return initialElements.toList().iterator()
    }

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String {
        updateValues()
        return string
    }

    override fun hashCode(): Int {
        return listOf(javaClass.name, initialElements).hashCode()
    }
}
