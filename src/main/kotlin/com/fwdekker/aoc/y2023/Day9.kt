package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day9.txt").lines().filter(String::isNotBlank)

    // Part 1
    println("Part one: ${lines.sumOf { it.split(' ').map(String::toInt).next() }}")

    // Part 2
    println("Part two: ${lines.sumOf { it.split(' ').map(String::toInt).reversed().next() }}")
}


private tailrec fun List<Int>.next(plus: Int = 0): Int =
    if (all { it == 0 }) plus
    else zipWithNext().map { it.second - it.first }.next(plus + last())
