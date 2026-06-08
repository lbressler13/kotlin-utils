package xyz.lbres.kotlinutils.testutils

// https://en.wikipedia.org/wiki/ANSI_escape_code#Colors
private const val yellow = "\u001b[33m"
private const val red = "\u001b[31m"
private const val nc = "\u001b[0m" // no color

/**
 * Print a warning message
 */
fun printWarn(message: Any?) = println("$yellow$message$nc")

/**
 * Print an error message
 */
fun printErr(message: Any?) = println("$red$message$nc")
