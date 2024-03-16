package xyz.lbres.kotlinutils.set.multiset.utils

@JvmInline
value class CountsMap<E>(val counts: Map<E, Int>) {
    fun toList(): List<E> {
        val list: MutableList<E> = mutableListOf()
        counts.forEach { (element, count) ->
            repeat(count) { list.add(element) }
        }

        return list
    }

    override fun toString(): String {
        if (counts.isEmpty()) {
            return "[]"
        }

        var elementsString = ""
        counts.forEach { (element, count) ->
            elementsString += "$element, ".repeat(count)
        }
        elementsString = elementsString.substring(0 until elementsString.lastIndex - 1) // remove trailing ", "

        return "[$elementsString]"
    }

    fun getCountOf(element: E): Int = counts.getOrDefault(element, 0)

    companion object {
        fun <E> from(elements: Collection<E>): CountsMap<E> {
            val counts: MutableMap<E, Int> = mutableMapOf()
            elements.forEach {
                counts[it] = counts.getOrDefault(it, 0) + 1
            }

            return CountsMap(counts)
        }
    }
}
