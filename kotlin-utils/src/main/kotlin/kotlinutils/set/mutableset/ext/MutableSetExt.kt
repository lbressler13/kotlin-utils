package kotlinutils.set.mutableset.ext

/**
 * Remove a random element from set and return it
 *
 * @return [T]: an element from the set, or null if the set is empty
 */
fun <T> MutableSet<T>.popRandom(): T? {
    if (this.isEmpty()) {
        return null
    }

    val p = this.random()
    remove(p)
    return p
}
