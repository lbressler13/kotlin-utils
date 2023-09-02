package xyz.lbres.kotlinutils.general.labelled

/**
 * Class to represent a value with an assigned label
 *
 * @param value [T]
 * @param label [String]
 */
@Suppress("Unused")
data class Labelled<T>(
    val value: T,
    val label: String
)
