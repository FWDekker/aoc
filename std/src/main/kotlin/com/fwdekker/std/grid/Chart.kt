package com.fwdekker.std.grid

import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.sections


/**
 * A 2-dimensional chart (or map), represented by a list of strings, all with the same length.
 *
 * @see Grid
 */
typealias Chart = Grid<Char>

/**
 * Converts this (multi-line) string into a [Chart].
 */
fun String.toChart() = linesNotBlank().map { it.toList() }

/**
 * Converts each of the [sections] of this multi-line string into a [Chart].
 */
fun String.toCharts() = sections().map { section -> section.map { it.toList() } }
