package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day6.txt").lines().filter(String::isNotBlank)

    // Part 1
    lines.map(String::parseInts).let { it[0].zip(it[1]) }
        .map { (time, distance) -> (0..<time).count { (time - it) * it > distance } }
        .also { println("Part one: ${it.fold(1, Int::times)}") }

    // Part 2
    lines.map(String::parseBadlyKernedInts).let { it[0].zip(it[1]) }
        .map { (time, distance) -> (0..<time).count { (time - it) * it > distance } }
        .also { println("Part two: ${it.fold(1, Int::times)}") }
}


private fun String.parseInts(): List<Int> =
    this.split(": ")[1].split(Regex("\\s+")).filter { it.isNotBlank() }.map { it.toInt() }

private fun String.parseBadlyKernedInts(): List<Long> =
    listOf(this.split(": ")[1].filter { it.isDigit() }.toLong())
