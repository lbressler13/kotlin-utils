package kotlinutils.classes.multiset

import kotlinutils.int.ext.isZero
import java.lang.Integer.min

class MutableMultiSet<E> internal constructor(elements: Collection<E>) : MutableSet<E> {
    /**
     * Number of elements in set
     */
    override val size: Int
        get() = storedSize

    /**
     * Store the number of occurrences of each element in set.
     * Because set is not mutable, counts are guaranteed to be greater than 0.
     */
    private val countsMap: MutableMap<E, Int>

    /**
     * Iterator for the set, is created and stored the first time it's requested
     */
    private var iter: MutableIterator<E>? = null

    /**
     * If the iterator is up-to-date with the most recent changes
     */
    private var iterUpdated: Boolean = false

    private var storedSize: Int = 0

    init {
        storedSize = elements.size

        countsMap = mutableMapOf()

        for (value in elements) {
            countsMap[value] = getCountOf(value) + 1
        }
    }

    /**
     * Constructor that creates a MutableMultiSet of a given size, using the init function to generate each element
     * Runs in O(n), in addition to the runtime of class init function
     *
     * @param size [Int]: size of MultiSet to create
     * @param initializeElement [(Int) -> E]: initialization function, used to create each element based on its index
     */
    constructor(size: Int, initializeElement: (Int) -> E) : this((0 until size).map(initializeElement))

    fun getCountOf(element: E): Int = countsMap[element] ?: 0

    /**
     * Adds the specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if the element has been added successfully, `false` otherwise
     */
    override fun add(element: E): Boolean {
        countsMap[element] = getCountOf(element) + 1
        iterUpdated = false
        storedSize++
        println(Pair(storedSize, countsMap))
        return true
    }

    /**
     * Adds all specified elements to the set.
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: `true` if all elements have been added successfully, `false` otherwise
     */
    override fun addAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var someSucceeded = false

        for (element in elements) {
            someSucceeded = add(element) || someSucceeded
        }

        return someSucceeded
    }

    /**
     * Remove all elements from the set
     */
    override fun clear() {
        countsMap.clear()
        iterUpdated = false
        storedSize = 0
    }

    override fun iterator(): MutableIterator<E> {
        if (!iterUpdated) {
            // store iterator to be used for future calls
            iter = toList().iterator()
        }

        return iter!!
    }

    override fun remove(element: E): Boolean {
        val currentCount = getCountOf(element)

        when (currentCount) {
            0 -> return false
            1 -> countsMap.remove(element)
            else -> countsMap[element] = currentCount - 1
        }

        iterUpdated = false
        storedSize--
        return true
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var someSucceeded = false

        for (element in elements) {
            someSucceeded = remove(element) || someSucceeded
        }

        return someSucceeded
    }

    // O(max(n, e))
    override fun retainAll(elements: Collection<E>): Boolean {
        val other = MutableMultiSet(elements) // O(e)
        val keys = countsMap.keys.intersect(other.countsMap.keys) // O(max(n, e))

        val newValues = keys.map { it to min(countsMap[it]!!, other.countsMap[it]!!) } // O(n)
        clear()
        countsMap.putAll(newValues) // O(n)

        iterUpdated = false
        storedSize = countsMap.values.fold(0, Int::plus) // O(n)

        return true
    }

    /**
     * Determine if an element is contained in the current MultiSet.
     * Runs in O(1).
     *
     * @param element [E]
     * @return [Boolean]: true if [element] is in the MultiSet, false otherwise
     */
    override fun contains(element: E): Boolean = countsMap.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current MultiSet.
     * If [elements] has repeats of a single element,
     * this function checks if the MultiSet has at least as many occurrence as the input collection.
     * Runs in O(e), where e is the size of [elements].
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: true if the current MultiSet contains at least as many occurrences of each value as [elements]
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val newSet = MutableMultiSet(elements)
        return newSet.countsMap.all { countsMap.contains(it.key) && it.value <= getCountOf(it.key) }
    }

    // O(n)
    private fun toList(): MutableList<E> {
        val list: MutableList<E> = mutableListOf()

        countsMap.forEach {
            val element = it.key
            val count = it.value
            repeat(count) { list.add(element) }
        }

        return list
    }

    override fun isEmpty(): Boolean = storedSize.isZero()

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
