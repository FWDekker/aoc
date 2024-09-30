package com.fwdekker.std.grid

import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.sections


/**
 * A 2-dimensional chart (or map), represented by a list of strings, all with the same length.
 */
typealias Chart = Grid<Char>

/**
 * Returns the raw [String] representation of this row.
 */
fun List<Char>.toRaw(): String = joinToString("")

/**
 * Returns the [Chart] row representation of this raw [String].
 */
fun String.toRow(): List<Char> = toList()

/**
 * Converts this (multi-line) string into a [Chart].
 */
fun String.toChart() = linesNotBlank().map { it.toRow() }

/**
 * Converts each of the [sections] of this multi-line string into a [Chart].
 */
fun String.toCharts() = sections().map { it.map(String::toRow) }
