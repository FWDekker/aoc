package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.ints
import com.fwdekker.aoc.std.readLines


fun main() {
    val lines = readLines("/y2023/Day9.txt")

    // Part 1
    println("Part one: ${lines.sumOf { it.ints(' ').next() }}")

    // Part 2
    println("Part two: ${lines.sumOf { it.ints(' ').reversed().next() }}")
}


private tailrec fun List<Int>.next(plus: Int = 0): Int =
    if (all { it == 0 }) plus
    else zipWithNext().map { it.second - it.first }.next(plus + last())
