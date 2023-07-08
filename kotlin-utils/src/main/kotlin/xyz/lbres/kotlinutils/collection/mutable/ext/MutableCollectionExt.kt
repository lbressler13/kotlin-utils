package xyz.lbres.kotlinutils.collection.mutable.ext

import kotlin.random.Random

/**
 * Remove a random element from the collection and return it
 *
 * @return [T]?: an element from the collection, or `null` if the collection is empty
 */
fun <T> MutableCollection<T>.popRandom(): T? {
    if (isEmpty()) {
        return null
    }

    val element = random()
    remove(element)
    return element
}

/**
 * Remove a random element from the collection and return it
 *
 * @param seed [Long]: random seed
 * @return [T]?: an element from the collection, or `null` if the collection is empty
 */
fun <T> MutableCollection<T>.popRandom(seed: Long): T? {
    if (isEmpty()) {
        return null
    }

    val element = random(Random(seed))
    remove(element)
    return element
}

/**
 * Remove a random element from the collection and return it
 *
 * @param seed [Int]: random seed
 * @return [T]?: an element from the collection, or `null` if the collection is empty
 */
fun <T> MutableCollection<T>.popRandom(seed: Int): T? = popRandom(seed.toLong())
