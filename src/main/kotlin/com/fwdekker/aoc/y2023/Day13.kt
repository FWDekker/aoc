package com.fwdekker.aoc.y2023

import kotlin.math.min


fun main() {
    val patterns = readResource("/y2023/Day13.txt").split("\n\n").map { it.lines().filter(String::isNotBlank) }

    // Part 1
    println("Part one: ${patterns.sumOf { it.value() }}")

    // Part 2 (does not work because the puzzle is badly designed)
    println("Part two: ${patterns.sumOf { it.fixSmudge().value() }}")
}


private fun List<String>.rowLines(): List<Int> =
    (1..<this.size)
        .filter { separator ->
            val offsets = 0..<min(separator, this.size - separator)

            offsets.all { offset -> this[separator - offset - 1] == this[separator + offset] }
        }

private fun List<String>.colLines(): List<Int> =
    (1..<this[0].length)
        .filter { separator ->
            val offsets = 0..<min(separator, this[0].length - separator)

            offsets.all { offset -> this.map { it[separator - offset - 1] } == this.map { it[separator + offset] } }
        }

private fun List<String>.value(): Int = rowLines().sumOf { it * 100 } + colLines().sum()

private fun List<String>.fixSmudge(): List<String> {
    val oldRowLines = rowLines()
    val oldColLines = colLines()

    for (row in this.indices) {
        for (col in this[0].indices) {
            val fixed = this.toMutableList()
            fixed[row] = fixed[row].replaceRange(col..col, if (fixed[row][col] == '.') "#" else ".")

            val newRowLines = fixed.rowLines()
            if (newRowLines.any { it !in oldRowLines })
                return fixed

            val newColLines = fixed.colLines()
            if (newColLines.any { it !in oldColLines })
                return fixed
        }
    }

    error("Could not find a smudge to fix.")
}
