package kotlinutil.set.mutableset.ext

// TODO unit tests

fun <T> MutableSet<T>.popRandom(): T? {
    if (this.isEmpty()) {
        return null
    }

    val p = this.random()
    remove(p)
    return p
}