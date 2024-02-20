package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.utils.countsToList
import xyz.lbres.kotlinutils.set.multiset.utils.countsToString
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap
import kotlin.math.min

// final implementation of ConstMutableMultiSet
internal class ConstMutableMultiSetImpl<E>(initialElements: Collection<E>) : ConstMutableMultiSet<E>(initialElements) {
    /**
     * If all properties are up-to-date with the most recent changes to the counts map
     */
    private var allPropertiesUpdated: Boolean = false

    private val manager: ConstMultiSetManager<E>
    override val counts: MutableMap<E, Int>

    override val distinctValues: Set<E>
        get() = counts.keys

    // elements list is up-to-date only when allPropertiesUpdated == true
    private var _elements: List<E> = initialElements.toList()
    override val elements: Collection<E>
        get() {
            updateMutableValues()
            return _elements
        }

    // string is up-to-date only when allPropertiesUpdated == true
    private var _string: String = ""
    override val string: String
        get() {
            updateMutableValues() // update string before returning value
            return _string
        }

    init {
        counts = createCountsMap(initialElements).toMutableMap()
        manager = ConstMultiSetManager(elements, counts)
    }

    override fun add(element: E): Boolean {
        allPropertiesUpdated = false
        counts[element] = getCountOf(element) + 1
        _size++
        return true
    }

    override fun addAll(elements: Collection<E>): Boolean {
        allPropertiesUpdated = false
        elements.forEach(this::add)
        return true
    }

    override fun remove(element: E): Boolean {
        when (getCountOf(element)) {
            0 -> return false
            1 -> counts.remove(element)
            else -> counts[element] = getCountOf(element) - 1
        }

        allPropertiesUpdated = false
        _size--
        return true
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var anySucceeded = false
        elements.forEach { anySucceeded = remove(it) || anySucceeded }
        allPropertiesUpdated = allPropertiesUpdated && !anySucceeded

        return anySucceeded
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        val initialSize = size
        val elementsCounts = createCountsMap(elements)

        val newCounts = distinctValues.associateWith {
            min(getCountOf(it), elementsCounts.getOrDefault(it, 0))
        }

        counts.clear()
        _size = 0

        newCounts.forEach { (element, count) ->
            if (count > 0) {
                counts[element] = count
                _size += count
            }
        }

        allPropertiesUpdated = allPropertiesUpdated && size == initialSize

        return true
    }

    override fun clear() {
        counts.clear()
        _size = 0
        allPropertiesUpdated = false
    }

    override fun iterator(): MutableIterator<E> {
        updateMutableValues()
        return _elements.toMutableList().iterator()
    }

    /**
     * Update the elements list and string based on the current values in the counts map
     */
    private fun updateMutableValues() {
        if (!allPropertiesUpdated) {
            _elements = countsToList(counts)
            _string = countsToString(counts)

            allPropertiesUpdated = true
        }
    }
}
