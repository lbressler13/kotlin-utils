// ktlint-disable filename

package xyz.lbres.kotlinutils.classes.labelled

/**
 * Code for the Labelled class has been moved to the general/labelled package.
 * This file exists only to avoid breaking existing functionality that uses the class at this path.
 */

private const val packageName = "xyz.lbres.kotlinutils.general.labelled"

@Deprecated("Class moved to package $packageName.", ReplaceWith("$packageName.Labelled", "$packageName.Labelled"), DeprecationLevel.ERROR)
data class Labelled<T>(val value: T, val label: String)
