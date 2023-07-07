package xyz.lbres.kotlinutils.iterable.ext

fun <S, T> Iterable<S>.forEachWith(other: Iterable<T>, action: (S, T) -> Unit) {
    forEach { value ->
        other.forEach { action(value, it) }
    }
}
