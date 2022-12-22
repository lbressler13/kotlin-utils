// ktlint-disable filename

package xyz.lbres.kotlinutils.classes.labelled

import xyz.lbres.kotlinutils.general.labelled.Labelled

/**
 * Code for the Labelled class has been moved to the general/labelled package.
 * This file exists only to avoid breaking existing functionality that uses the class at this path.
 */

private const val packageName = "xyz.lbres.kotlinutils.general.labelled"

@Deprecated("Class moved to package $packageName.", ReplaceWith("$packageName.Labelled", "xyz"), DeprecationLevel.WARNING)
typealias Labelled<T> = Labelled<T>
