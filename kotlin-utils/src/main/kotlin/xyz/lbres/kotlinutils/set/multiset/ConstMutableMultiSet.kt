package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import kotlin.math.min

/**
 * [MutableMultiSet] implementation where values of elements are assumed to be constant.
 * Behavior is not defined if values of elements are changed.
 */
class ConstMutableMultiSet<E> internal constructor(initialElements: Collection<E>) : MutableMultiSet<E>, ConstMultiSet<E>(initialElements) {
    /**
     * If all properties are updated with the recent changes to the counts map
     */
    private var allPropertiesUpdated: Boolean = false

    // elements list is up-to-date only when allPropertiesUpdated == true
    private var _elements: MutableList<E> = initialElements.toMutableList()
    override val elements: Collection<E>
        get() {
            updateMutableValues() // update elements before returning value
            return _elements
        }

    // string is up-to-date only when allPropertiesUpdated == true
    private var _string: String = ""
    override val string: String
        get() {
            updateMutableValues() // update string before returning value
            return _string
        }

    // override property to get values from mutable map
    override val distinctValues: Set<E>
        get() = counts.keys

    // override property to allow changing keys and values
    override val counts: MutableMap<E, Int> = createCounts(initialElements).toMutableMap()

    /**
     * Create a new MutableMultiSet with all values from both sets.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be added to the number in this set.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: values to add to this set
     * @return [MutableMultiSet]<E>: MutableMultiSet containing all values from both sets
     */
    override operator fun plus(other: MultiSet<E>): MultiSet<E> = super<ConstMultiSet>.plus(other)

    /**
     * Create a new MutableMultiSet with values that are in this set but not the other set.
     * If there are multiple occurrences of a value, the number of occurrences in the other set will be subtracted from the number in this set.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MutableMultiSet]<E>: values to subtract from this set
     * @return [MultiSet]<E>: MultiSet containing the items in this set but not the other
     */
    override operator fun minus(other: MultiSet<E>): MultiSet<E> = super<ConstMultiSet>.minus(other)

    /**
     * Create a new MutableMultiSet with values that are shared between the sets.
     * If there are multiple occurrences of a value, the smaller number of occurrences will be used.
     * The returned set is **not** a ConstMultiSet.
     *
     * @param other [MultiSet]<E>: MultiSet to intersect with current
     * @return [MutableMultiSet]<E>: MultiSet containing only values that are in both set
     */
    override infix fun intersect(other: MultiSet<E>): MultiSet<E> = super<ConstMultiSet>.intersect(other)

    /**
     * Add one occurrence of the specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if the element has been added successfully, `false` otherwise
     */
    override fun add(element: E): Boolean {
        counts[element] = getCountOf(element) + 1
        _size++
        allPropertiesUpdated = false
        return true
    }

    /**
     * Add all specified elements to the set.
     * If [elements] contains multiple occurrences of the same value, the value will be added multiple times.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: `true` if any elements have been added successfully, `false` otherwise
     */
    override fun addAll(elements: Collection<E>): Boolean {
        elements.forEach(this::add)
        allPropertiesUpdated = false
        return true
    }

    /**
     * Remove one occurrence of the specified element from the set, if the element exists.
     *
     * @param element [E]
     * @return [Boolean]: true if the element has been removed successfully, false otherwise
     */
    override fun remove(element: E): Boolean {
        allPropertiesUpdated = false
        return when (getCountOf(element)) {
            0 -> false
            1 -> {
                counts.remove(element)
                _size--
                true
            }
            else -> {
                counts[element] = getCountOf(element) - 1
                _size--
                true
            }
        }
    }

    /**
     * Remove all specified elements from the set.
     * If [elements] contains a single occurrence of a value, only one occurrence of the value will be removed from the set.
     * If there are multiple occurrences, the value will be removed multiple times.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: true if any elements have been removed successfully, false otherwise
     */
    override fun removeAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var anySucceeded = false
        elements.forEach { anySucceeded = remove(it) || anySucceeded }

        allPropertiesUpdated = false
        return anySucceeded
    }

    /**
     * Retain only elements that are present in [elements].
     * If [elements] contains multiple occurrences of the same value, the value will retain up to that number of occurrences.
     *
     * @param elements [Collection]<E>
     * @return [Boolean]: true if elements have been retained successfully, false otherwise
     */
    override fun retainAll(elements: Collection<E>): Boolean {
        allPropertiesUpdated = false
        val elementsSet = MultiSetImpl(elements)

        val newCounts: MutableMap<E, Int> = mutableMapOf()

        distinctValues.forEach {
            val newCount = min(getCountOf(it), elementsSet.getCountOf(it))
            if (newCount != 0) {
                newCounts[it] = newCount
            }
        }

        counts.clear()
        _size = 0

        newCounts.forEach {
            counts[it.key] = it.value
            _size += it.value
        }

        return true
    }

    /**
     * Remove all elements from the set.
     */
    override fun clear() {
        counts.clear()
        _size = 0
        allPropertiesUpdated = false
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [MutableIterator]<E>
     */
    override fun iterator(): MutableIterator<E> {
        updateMutableValues()
        return _elements.iterator()
    }

    /**
     * Update the elements list and string based on the current values in the counts map
     */
    private fun updateMutableValues() {
        if (!allPropertiesUpdated) {
            _elements.clear()
            counts.forEach {
                repeat(it.value) { _ -> _elements.add(it.key) }
            }
            _string = createString(counts)

            allPropertiesUpdated = true
        }
    }
}
