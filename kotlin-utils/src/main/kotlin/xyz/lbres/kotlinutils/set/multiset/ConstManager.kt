package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.list.listOfValue
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import kotlin.math.min

// TODO cleanup unit tests

@Suppress("EqualsOrHashCode")
internal class ConstManager<E> {

    private val mutable: Boolean

    private val initialElements: Collection<E>

    private val mutableElements: MutableList<E> = mutableListOf()
    private var mutableElementsUpdated: Boolean = false

    private val immutableDistinctValues: Set<E>
    private val immutableString: String

    private val counts: MutableMap<E, Int> = mutableMapOf()

    val distinctValues: Set<E>
        get() = simpleIf(mutable, { counts.keys }, { immutableDistinctValues })

    private var _size: Int
    val size: Int
        get() = _size

    constructor(initialElements: Collection<E>, mutable: Boolean) {
        _size = initialElements.size
        initialElements.forEach {
            counts[it] = counts.getOrDefault(it, 0) + 1
        }

        this.initialElements = initialElements
        immutableDistinctValues = counts.keys
        immutableString = createString(counts)

        this.mutable = mutable
    }

    constructor(counts: Map<E, Int>, mutable: Boolean) {
        _size = counts.values.fold(0, Int::plus)
        immutableDistinctValues = counts.keys
        counts.forEach { this.counts[it.key] = it.value }

        this.initialElements = counts.keys.fold(emptyList()) { acc, element ->
            acc + listOfValue(counts[element]!!, element)
        }
        immutableString = createString(counts)
        this.mutable = mutable
    }

    fun getCountOf(element: E): Int = counts.getOrDefault(element, 0)

    fun contains(element: E): Boolean = counts.contains(element)

    fun containsAll(elements: Collection<E>): Boolean {
        val otherCounts = createCounts(elements)

        return otherCounts.all {
            it.value <= getCountOf(it.key)
        }
    }

    fun add(element: E): Boolean {
        validateMutate("add")
        counts[element] = getCountOf(element) + 1
        _size++
        mutableElementsUpdated = false
        return true
    }

    fun addAll(elements: Collection<E>): Boolean {
        validateMutate("addAll")
        elements.forEach(this::add)
        mutableElementsUpdated = false
        return true
    }

    fun clear() {
        validateMutate("clear")
        counts.clear()
        _size = 0
        mutableElementsUpdated = false
    }

    fun remove(element: E): Boolean {
        validateMutate("remove")
        mutableElementsUpdated = false
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

    fun removeAll(elements: Collection<E>): Boolean {
        validateMutate("removeAll")
        if (elements.isEmpty()) {
            return true
        }

        var anySucceeded = false
        elements.forEach { anySucceeded = remove(it) || anySucceeded }

        mutableElementsUpdated = false
        return anySucceeded
    }

    fun retainAll(elements: Collection<E>): Boolean {
        validateMutate("retainAll")
        mutableElementsUpdated = false
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

    operator fun plus(other: MultiSet<E>): MultiSet<E> {
        val values = distinctValues + other.distinctValues

        val newCounts = values.associateWith {
            getCountOf(it) + other.getCountOf(it)
        }

        return simpleIf(mutable, { MultiSetImpl(newCounts) }, { MultiSetImpl(newCounts) })
    }

    operator fun minus(other: MultiSet<E>): MultiSet<E> {
        val values = distinctValues + other.distinctValues

        val newCounts = values.associateWith {
            getCountOf(it) - other.getCountOf(it)
        }.filter { it.value > 0 }
        return simpleIf(mutable, { MultiSetImpl(newCounts) }, { MultiSetImpl(newCounts) })
    }

    infix fun intersect(other: MultiSet<E>): MultiSet<E> {
        val values = distinctValues intersect other.distinctValues

        val newCounts = values.associateWith {
            min(getCountOf(it), other.getCountOf(it))
        }
        return simpleIf(mutable, { MultiSetImpl(newCounts) }, { MultiSetImpl(newCounts) })
    }

    fun iterator(): MutableIterator<E> {
        val list: MutableList<E> = mutableListOf() // TODO store this
        counts.forEach {
            repeat(it.value) { _ -> list.add(it.key) }
        }
        return list.iterator()
    }

    fun isEmpty(): Boolean = size == 0

    fun eq(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        return tryOrDefault(false) {
            @Suppress("UNCHECKED_CAST")
            other as MultiSet<E>

            distinctValues == other.distinctValues && distinctValues.all { getCountOf(it) == other.getCountOf(it) }
        }
    }

    private fun createCounts(values: Collection<E>): Map<E, Int> {
        val newCounts: MutableMap<E, Int> = mutableMapOf()

        values.forEach {
            newCounts[it] = newCounts.getOrDefault(it, 0) + 1
        }

        return newCounts
    }

    fun getImmutableIterator(): Iterator<E> = initialElements.iterator()

    fun getMutableIterator(): MutableIterator<E> {
        if (!mutableElementsUpdated) {
            mutableElements.clear()
            counts.forEach {
                repeat(it.value) { _ -> mutableElements.add(it.key) }
            }
            mutableElementsUpdated = true
        }
        return mutableElements.iterator()
    }

    override fun hashCode(): Int {
        val hashCode = counts.hashCode()
        return 31 * hashCode + MultiSet::class.java.name.hashCode()
    }

    private fun createString(values: Map<E, Int>): String {
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

    private fun validateMutate(mutation: String) {
        if (!mutable) {
            throw UnsupportedOperationException("Mutations cannot be performed on this object. Invalid operation: $mutation.")
        }
    }

    override fun toString(): String = simpleIf(mutable, { createString(counts) }, { immutableString })
}
