package com.fwdekker.aoc.y2023


fun main() {
    val patterns = readResource("/y2023/Day13.txt").split("\n\n").map { it.lines().filter(String::isNotBlank) }

    // Part 1
    println("Part one: ${patterns.sumOf { it.value(targetDiff = 0) }}")

    // Part 2
    println("Part two: ${patterns.sumOf { it.value(targetDiff = 1) }}")
}


private fun List<String>.transpose(): List<String> =
    this[0].indices.map { col -> joinToString(separator = "") { "${it[col]}" } }

private fun List<String>.diff(a: Int, b: Int): Int =
    if (a !in indices || b !in indices) 0
    else this[a].zip(this[b]).count { it.first != it.second }

private fun List<String>.mirrors(targetDiff: Int = 0): List<Int> =
    indices.filter { row -> indices.sumOf { offset -> diff(row - offset - 1, row + offset) } == targetDiff }

private fun List<String>.value(targetDiff: Int = 0): Int =
    mirrors(targetDiff).sumOf { it * 100 } + transpose().mirrors(targetDiff).sum()
