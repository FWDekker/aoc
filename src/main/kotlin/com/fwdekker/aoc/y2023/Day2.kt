package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.asPair
import com.fwdekker.aoc.std.product
import com.fwdekker.aoc.std.mapFirst
import com.fwdekker.aoc.std.mapSecond
import com.fwdekker.aoc.std.readLines
import com.fwdekker.aoc.std.swap


fun main() {
    val lines = readLines("/y2023/Day2.txt")

    // Part 1
    val limits = mapOf("red" to 12, "green" to 13, "blue" to 14);
    lines
        .associate { line -> line.split(": ").asPair().mapFirst { it.drop(5).toInt() }.mapSecond { it.parseDraws() } }
        .filterValues { draws -> draws.all { draw -> limits.all { (color, amount) -> (draw[color] ?: 0) <= amount } } }
        .also { println("Part one: ${it.keys.sum()}") }

    // Part 2
    val colors = limits.keys
    lines
        .map { it.split(": ")[1].parseDraws() }
        .sumOf { draws -> colors.map { color -> draws.maxOf { it[color] ?: 0 } }.product() }
        .also { println("Part two: $it") }
}


private fun String.parseDraws(): List<Map<String, Int>> =
    split("; ").map { draw -> draw.split(", ").associate { it.split(' ').asPair().swap().mapSecond(String::toInt) } }
