package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.set.multiset.ConstMutableMultiSetInt
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import kotlin.math.min

internal class MutableConstMultiSetImpl<E> : ConstMutableMultiSetInt<E>, AbstractConstMultiSet<E> {
    /**
     * Number of elements in set.
     */
    private var _size: Int = 0
    override val size: Int
        get() = _size

    private val _counts: MutableMap<E, Int> = mutableMapOf()
    override val counts: Map<E, Int>
        get() = _counts

    override val distinctValues: Set<E>
        get() = counts.keys

    /**
     * Initialize set from a collection of values.
     */
    internal constructor(elements: Collection<E>) {
        _size = elements.size

        elements.forEach {
            _counts[it] = _counts.getOrDefault(it, 0) + 1
        }
    }

    /**
     * Initialize set from existing counts.
     */
    private constructor(counts: Map<E, Int>) {
        counts.forEach {
            _counts[it.key] = it.value
        }
        _size = counts.values.fold(0, Int::plus)
    }

    override fun createFromCounts(counts: Map<E, Int>): MutableConstMultiSetImpl<E> {
        return MutableConstMultiSetImpl(counts)
    }

    override fun add(element: E): Boolean {
        _counts[element] = getCountOf(element) + 1
        _size++
        return true
    }

    override fun addAll(elements: Collection<E>): Boolean {
        elements.forEach(this::add)
        return true
    }

    override fun clear() {
        _counts.clear()
        _size = 0
    }

    override fun remove(element: E): Boolean {
        return when (getCountOf(element)) {
            0 -> false
            1 -> {
                _counts.remove(element)
                _size--
                true
            }
            else -> {
                _counts[element] = getCountOf(element) - 1
                _size--
                true
            }
        }
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var anySucceeded = false
        elements.forEach { anySucceeded = remove(it) || anySucceeded }

        return anySucceeded
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        val elementsSet = ConstMultiSetImpl(elements)

        val newCounts: MutableMap<E, Int> = mutableMapOf()

        distinctValues.forEach {
            val newCount = min(getCountOf(it), elementsSet.getCountOf(it))
            if (newCount != 0) {
                newCounts[it] = newCount
            }
        }

        _counts.clear()
        _size = 0

        newCounts.forEach {
            _counts[it.key] = it.value
            _size += it.value
        }

        return true
    }

    override operator fun minus(other: MultiSet<E>): MutableMultiSet<E> {
        return super<AbstractConstMultiSet>.minus(other) as MutableConstMultiSetImpl<E>
    }

    override operator fun plus(other: MultiSet<E>): MutableConstMultiSetImpl<E> {
        return super<AbstractConstMultiSet>.plus(other) as MutableConstMultiSetImpl<E>
    }

    override fun iterator(): MutableIterator<E> {
        val list: MutableList<E> = mutableListOf() // TODO store this
        counts.forEach {
            repeat(it.value) { _ -> list.add(it.key) }
        }
        return list.iterator()
    }
}
