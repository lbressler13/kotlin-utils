package xyz.lbres.kotlinutils.iterable.ext

fun <S, T> Iterable<S>.forEachWith(other: Iterable<T>, action: (S, T) -> Unit) {
    forEach { value ->
        other.forEach { action(value, it) }
    }
}

fun <S, T> Iterable<S>.forEachWithIndexed(other: Iterable<T>, action: (IndexedValue<S>, IndexedValue<T>) -> Unit) {
    forEachIndexed { index, value ->
        val indexed: IndexedValue<S> = IndexedValue(index, value)

        other.forEachIndexed { otherIndex, otherValue ->
            val otherIndexed: IndexedValue<T> = IndexedValue(otherIndex, otherValue)
            action(indexed, otherIndexed)
        }
    }
}
