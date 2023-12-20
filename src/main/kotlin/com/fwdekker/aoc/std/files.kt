package com.fwdekker.aoc.std


/**
 * Returns the contents of resource [path], trimming whitespace if [trim] is `true`.
 */
fun readResource(path: String, trim: Boolean = true): String =
    {}.javaClass.getResource(path)!!.readText()
        .let { if (trim) it.trim() else it }

/**
 * Reads all lines in resource [path], trimming whitespace and removing blank lines.
 *
 * @see linesNotBlank
 */
fun readLines(path: String): List<String> = readResource(path, trim = true).linesNotBlank()

/**
 * Reads resource [path], trimming whitespace and trimming into sections.
 *
 * @see sections
 */
fun readSections(path: String): List<List<String>> = readResource(path, trim = true).sections()
