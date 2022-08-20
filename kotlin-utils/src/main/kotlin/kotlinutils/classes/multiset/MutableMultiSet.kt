package kotlinutils.classes.multiset

import kotlinutils.int.ext.isZero
import java.lang.Integer.min

class MutableMultiSet<T> internal constructor(elements: Collection<T>) : MutableSet<T> {
    /**
     * Number of elements in set
     */
    override val size: Int

    /**
     * Store the number of occurrences of each element in set.
     * Because set is not mutable, counts are guaranteed to be greater than 0.
     */
    private val countsMap: MutableMap<T, Int>

    /**
     * Iterator for the set, is created and stored the first time it's requested
     */
    private var iter: MutableIterator<T>? = null

    /**
     * If the iterator is up-to-date with the most recent changes
     */
    private var iterUpdated: Boolean = false

    init {
        size = elements.size

        countsMap = mutableMapOf()

        for (value in elements) {
            countsMap[value] = getCountOf(value) + 1
        }
    }

    fun getCountOf(element: T): Int = countsMap[element] ?: 0

    /**
     * Adds the specified element to the set.
     *
     * @param element [T]
     * @return [Boolean]: `true` if the element has been added successfully, `false` otherwise
     */
    override fun add(element: T): Boolean {
        countsMap[element] = getCountOf(element) + 1
        iterUpdated = false
        return true
    }

    /**
     * Adds all specified elements to the set.
     *
     * @param elements [Collection<T>]
     * @return [Boolean]: `true` if all elements have been added successfully, `false` otherwise
     */
    override fun addAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            if (!add(element)) {
                return false
            }
        }
        return true
    }

    /**
     * Remove all elements from the set
     */
    override fun clear() {
        iterUpdated = false
        countsMap.clear()
    }

    override fun iterator(): MutableIterator<T> {
        if (!iterUpdated) {
            // store iterator to be used for future calls
            iter = toList().iterator()
        }

        return iter!!
    }

    override fun remove(element: T): Boolean {
        val currentCount = getCountOf(element)
        return when (currentCount) {
            0 -> false
            1 -> {
                countsMap.remove(element)
                iterUpdated = false
                true
            }
            else -> {
                countsMap[element] = currentCount - 1
                iterUpdated = false
                true
            }
        }
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            if (!remove(element)) {
                return false
            }
        }

        return true
    }

    // O(max(n, e))
    override fun retainAll(elements: Collection<T>): Boolean {
        val other = MutableMultiSet(elements) // O(e)
        val keys = countsMap.keys.intersect(other.countsMap.keys) // O(max(n, e))

        val newValues = keys.map { it to min(countsMap[it]!!, other.countsMap[it]!!) } // O(n)
        clear()
        countsMap.putAll(newValues) // O(n)
        iterUpdated = false

        return true
    }

    /**
     * Determine if an element is contained in the current MultiSet.
     * Runs in O(1).
     *
     * @param element [T]
     * @return [Boolean]: true if [element] is in the MultiSet, false otherwise
     */
    override fun contains(element: T): Boolean = countsMap.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current MultiSet.
     * If [elements] has repeats of a single element,
     * this function checks if the MultiSet has at least as many occurrence as the input collection.
     * Runs in O(e), where e is the size of [elements].
     *
     * @param elements [Collection<T>]
     * @return [Boolean]: true if the current MultiSet contains at least as many occurrences of each value as [elements]
     */
    override fun containsAll(elements: Collection<T>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val newSet = MutableMultiSet(elements)
        return newSet.countsMap.all { countsMap.contains(it.key) && it.value <= getCountOf(it.key) }
    }

    // O(n)
    private fun toList(): MutableList<T> {
        val list: MutableList<T> = mutableListOf()

        countsMap.forEach {
            val element = it.key
            val count = it.value
            repeat(count) { list.add(element) }
        }

        return list
    }

    override fun isEmpty(): Boolean {
        // remove zeros
        countsMap.keys.removeAll { countsMap[it]?.isZero() ?: true }
        return countsMap.isEmpty()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MutableMultiSet<*>) {
            return false
        }

        val simplifiedCounts = countsMap.filterNot { it.value.isZero() }
        val otherSimplifiedCounts = other.countsMap.filterNot { it.value.isZero() }
        return simplifiedCounts == otherSimplifiedCounts
    }

    override fun hashCode(): Int = listOf("MutableMultiSet", countsMap).hashCode()
}
