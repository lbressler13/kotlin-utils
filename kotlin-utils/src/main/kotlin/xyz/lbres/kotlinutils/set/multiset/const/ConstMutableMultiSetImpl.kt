package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.countsToList
import xyz.lbres.kotlinutils.set.multiset.utils.countsToString
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap
import kotlin.math.min

// final implementation of ConstMutableMultiSet
internal class ConstMutableMultiSetImpl<E>(initialElements: Collection<E>) : ConstMutableMultiSet<E>(initialElements), AbstractConstMultiSetImpl<E> {
    /**
     * If all properties are up-to-date with the most recent changes to the counts map
     */
    private var allPropertiesUpdated: Boolean = false

    private val _counts: MutableMap<E, Int>
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
        _counts = createCountsMap(initialElements).toMutableMap()
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
        val elementsCounts = createCountsMap(elements)

        _size = 0
        distinctValues.forEach {
            val newCount = min(getCountOf(it), elementsCounts.getOrDefault(it, 0))
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

    override fun getCountOf(element: E): Int = counts.getCountOf(element)
    override fun contains(element: E): Boolean = counts.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = counts.containsAll(elements)

    override fun plus(other: MultiSet<E>): MultiSet<E> = super<AbstractConstMultiSetImpl>.plus(other)
    override fun minus(other: MultiSet<E>): MultiSet<E> = super<AbstractConstMultiSetImpl>.minus(other)
    override fun intersect(other: MultiSet<E>): MultiSet<E> = super<AbstractConstMultiSetImpl>.intersect(other)

    override fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> = super.plusC(other)
    override fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> = super.minusC(other)
    override fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> = super.intersectC(other)

    override fun isEmpty(): Boolean = counts.isEmpty()
    override fun equals(other: Any?): Boolean = other is MultiSet<*> && equalsSet(other)
    override fun hashCode(): Int = getHashCode()

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
            elements = countsToList(_counts)
            string = countsToString(_counts)

            allPropertiesUpdated = true
        }
    }
}
