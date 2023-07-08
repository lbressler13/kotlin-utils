package xyz.lbres.kotlinutils.collection.mutable.ext

import xyz.lbres.kotlinutils.general.simpleIf
import kotlin.random.Random

/**
 * Remove a random element from the collection and return it
 *
 * @return [T]?: an element from the collection, or `null` if the collection is empty
 */
fun <T> MutableCollection<T>.popRandom(): T? = popRandomUtil(null)

/**
 * Remove a random element from the collection and return it
 *
 * @param seed [Long]: random seed
 * @return [T]?: an element from the collection, or `null` if the collection is empty
 */
fun <T> MutableCollection<T>.popRandom(seed: Long): T? = popRandomUtil(seed)

/**
 * Remove a random element from the collection and return it
 *
 * @param seed [Int]: random seed
 * @return [T]?: an element from the collection, or `null` if the collection is empty
 */
fun <T> MutableCollection<T>.popRandom(seed: Int): T? = popRandomUtil(seed.toLong())

/**
 * Common function to be used for all popRandom calls, with or without a seed
 *
 * @param seed [Long]?: random seed, can be `null`
 * @return [T]?: an element from the collection, or `null` if the collection is empty
 */
private fun <T> MutableCollection<T>.popRandomUtil(seed: Long?): T? {
    if (isEmpty()) {
        return null
    }

    val element = simpleIf(seed == null, { random() }, { random(Random(seed!!)) })
    remove(element)
    return element
}
