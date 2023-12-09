package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day9.txt").lines().filter(String::isNotBlank)

    // Part 1
    lines.map { it.split(' ').map(String::toInt).next() }
        .also { println("Part one: ${it.sum()}") }

    // Part 2
    lines.map { it.split(' ').map(String::toInt).reversed().next() }
        .also { println("Part two: ${it.sum()}") }
}


private tailrec fun List<Int>.next(plus: Int = 0): Int =
    if (all { it == 0 }) plus
    else zipWithNext().map { it.second - it.first }.next(plus + last())
