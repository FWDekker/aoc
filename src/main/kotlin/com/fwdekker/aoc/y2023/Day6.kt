package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.ints
import com.fwdekker.aoc.std.product
import com.fwdekker.aoc.std.readLines


fun main() {
    val lines = readLines("/y2023/Day6.txt")

    // Part 1
    lines.map(String::parseInts).let { it[0].zip(it[1]) }
        .map { (time, distance) -> (0..<time).count { (time - it) * it > distance } }
        .also { println("Part one: ${it.product()}") }

    // Part 2
    lines.map(String::parseBadlyKernedInts).let { it[0].zip(it[1]) }
        .map { (time, distance) -> (0..<time).count { (time - it) * it > distance } }
        .also { println("Part two: ${it.product()}") }
}


private fun String.parseInts(): List<Int> = split(": ")[1].ints(Regex("\\s+"))

private fun String.parseBadlyKernedInts(): List<Long> = listOf(split(": ")[1].filter { it.isDigit() }.toLong())
