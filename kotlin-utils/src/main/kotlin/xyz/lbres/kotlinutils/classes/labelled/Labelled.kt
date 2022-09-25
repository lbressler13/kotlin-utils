package xyz.lbres.kotlinutils.classes.labelled

/**
 * Class to represent a value with an assigned label
 *
 * @param value [T]
 * @param label [String]
 */
data class Labelled<T>(
    val value: T,
    val label: String
)
