package xyz.lbres.kotlinutils.general.labelled

import xyz.lbres.kotlinutils.internal.constants.Suppressions

/**
 * Class to represent a value with an assigned label
 *
 * @param value T
 * @param label [String]
 */
@Suppress(Suppressions.UNUSED)
data class Labelled<T>(
    val value: T,
    val label: String
)
