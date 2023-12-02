package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day2.txt").lines().filter(String::isNotBlank)

    // Part 1
    val limits = mapOf("red" to 12, "green" to 13, "blue" to 14);
    lines
        .map { it.split(": ") }
        .associate { (id, draws) -> Pair(id.drop(5).toInt(), draws.parseDraws()) }
        .filterValues { draws -> draws.all { draw -> limits.all { (color, amount) -> (draw[color] ?: 0) <= amount } } }
        .also { println("Part one: ${it.keys.sum()}") }

    // Part 2
    val colors = limits.keys
    lines
        .map { it.split(": ")[1].parseDraws() }
        .sumOf { draws -> colors.map { color -> draws.maxOf { it[color] ?: 0 } }.fold(1, Int::times) }
        .also { println("Part two: $it") }
}


private fun String.parseDraws() =
    this.split("; ").map { draw ->
        draw.split(", ")
            .map { it.split(' ') }
            .associate { (amount, colour) -> Pair(colour, amount.toInt()) }
    }
