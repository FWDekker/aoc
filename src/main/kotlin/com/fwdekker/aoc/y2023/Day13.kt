package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.readSections
import com.fwdekker.aoc.std.transpose


fun main() {
    val patterns = readSections("/y2023/Day13.txt")

    // Part 1
    println("Part one: ${patterns.sumOf { it.value(targetDiff = 0) }}")

    // Part 2
    println("Part two: ${patterns.sumOf { it.value(targetDiff = 1) }}")
}


private fun List<CharSequence>.diff(a: Int, b: Int): Int =
    if (a !in indices || b !in indices) 0
    else this[a].zip(this[b]).count { it.first != it.second }

private fun List<CharSequence>.mirrors(targetDiff: Int = 0): List<Int> =
    indices.filter { row ->  indices.sumOf { offset -> diff(row - offset - 1, row + offset) } == targetDiff }

private fun List<String>.value(targetDiff: Int = 0): Int =
    mirrors(targetDiff).sumOf { it * 100 } + transpose().mirrors(targetDiff).sum()
