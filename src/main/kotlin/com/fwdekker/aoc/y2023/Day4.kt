package com.fwdekker.aoc.y2023

import kotlin.math.pow


fun main() {
    val lines = readResource("/y2023/Day4.txt").lines().filter(String::isNotBlank)

    // Part 1
    val winsByLine = lines
        .map { line -> line.split(": ")[1].split('|').map { it.split(' ').mapNotNull(String::toIntOrNull).toSet() } }
        .map { (drawn, winning) -> drawn.intersect(winning).size }
    println("Part one: ${winsByLine.sumOf { 2.0.pow(it - 1).toInt() }}")

    // Part 2
    val cards = ArrayDeque(lines.indices.toList())
    println("Part two: ${cards.map { cards.addAll((it + 1)..(it + winsByLine[it])) }.size}")
}
