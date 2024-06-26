package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import kotlin.math.min

// final implementation of ConstMutableMultiSet
internal class ConstMutableMultiSetImpl<E>(initialElements: Collection<E>) : ConstMutableMultiSet<E>() {
    /**
     * If all properties are up-to-date with the most recent changes to the counts map
     */
    private var allPropertiesUpdated: Boolean = false

    private val _counts: MutableMap<E, Int> = mutableMapOf() // values added in init
    override val counts: CountsMap<E>

    override val distinctValues: Set<E>
        get() = _counts.keys

    // elements list is up-to-date only when allPropertiesUpdated == true
    private var elements: List<E> = initialElements.toList()

    private var _size: Int = elements.size
    override val size: Int
        get() = _size

    // string is up-to-date only when allPropertiesUpdated == true
    private var string: String = ""

    init {
        initialElements.forEach {
            _counts[it] = _counts.getOrDefault(it, 0) + 1
        }

        counts = CountsMap(_counts)
    }

    override fun add(element: E): Boolean {
        allPropertiesUpdated = false
        _counts[element] = getCountOf(element) + 1
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
            1 -> _counts.remove(element)
            else -> _counts[element] = getCountOf(element) - 1
        }

        allPropertiesUpdated = false
        _size--
        return true
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val anySucceeded = elements.fold(false) { succeeded, element -> remove(element) || succeeded }
        allPropertiesUpdated = allPropertiesUpdated && !anySucceeded

        return anySucceeded
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        val initialSize = size
        val elementsCounts = CountsMap.from(elements)

        _size = 0
        distinctValues.forEach {
            val newCount = min(getCountOf(it), elementsCounts.getCountOf(it))
            if (newCount > 0) {
                _counts[it] = newCount
                _size += newCount
            } else {
                _counts.remove(it)
            }
        }

        allPropertiesUpdated = allPropertiesUpdated && size == initialSize

        return true
    }

    override fun clear() {
        _counts.clear()
        _size = 0
        allPropertiesUpdated = false
    }

    fun toCountsMap(): CountsMap<E> = counts

    override fun iterator(): MutableIterator<E> {
        updateMutableValues()
        return elements.toMutableList().iterator()
    }

    override fun toString(): String {
        updateMutableValues() // update string before returning value
        return string
    }

    /**
     * Update the elements list and string based on the current values in the counts map
     */
    private fun updateMutableValues() {
        if (!allPropertiesUpdated) {
            elements = counts.toList()
            string = counts.toString()

            allPropertiesUpdated = true
        }
    }
}
