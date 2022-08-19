package kotlinutils.classes.multiset

class MultiSet<T> internal constructor(elements: Collection<T>) : Collection<T> {
    override val size: Int
    private val valuesMap: Map<T, Int>
    private var iter: Iterator<T>? = null

    // O(n) init
    init {
        size = elements.size

        val mutableMap: MutableMap<T, Int> = mutableMapOf()

        for (value in elements) {
            val currentCount = mutableMap[value] ?: 0
            mutableMap[value] = currentCount + 1
        }

        valuesMap = mutableMap.toMap()
    }

    constructor(size: Int, init: (Int) -> T) : this((0 until size).map(init))

    // O(1)
    override fun contains(element: T): Boolean = valuesMap.contains(element)

    // O(|e|)
    override fun containsAll(elements: Collection<T>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val newSet = MultiSet(elements)
        return newSet.valuesMap.all { valuesMap.contains(it.key) && it.value <= getCountOf(it.key) }
    }

    // O(1)
    override fun isEmpty(): Boolean = valuesMap.isEmpty()

    // O(1)
    fun getCountOf(element: T): Int = valuesMap[element] ?: 0

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        return valuesMap == other.valuesMap
    }

    // O(n)
    override fun iterator(): Iterator<T> {
        if (iter == null) {
            val list: MutableList<T> = mutableListOf()
            valuesMap.forEach {
                val element = it.key
                val count = it.value
                repeat(count) { list.add(element) }
            }

            iter = list.iterator()
        }
        return iter!!
    }

    override fun hashCode(): Int = Pair("MultiSet", valuesMap).hashCode()
}
