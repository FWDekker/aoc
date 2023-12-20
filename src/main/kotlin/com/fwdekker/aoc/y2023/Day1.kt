package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.readLines


fun main() {
    val lines = readLines("/y2023/Day1.txt")

    // Part 1
    lines
        .map { it.toCharArray().filter(Char::isDigit) }
        .map { "" + (it.firstOrNull() ?: "") + (it.lastOrNull() ?: "") }
        .also { println("Part one: ${it.sumOf(String::toInt)}") }

    // Part 2
    lines
        .map { it.digits() }
        .map { "" + (it.firstOrNull() ?: "") + (it.lastOrNull() ?: "") }
        .also { println("Part two: ${it.sumOf(String::toInt)}") }
}


private val digits = mapOf(
    "one" to '1', "two" to '2', "three" to '3', "four" to '4', "five" to '5', "six" to '6', "seven" to '7',
    "eight" to '8', "nine" to '9',
)

private fun String.digits(): List<Char> =
    this.indices.mapNotNull { idx ->
        if (this[idx].isDigit()) this[idx]
        else digits.keys.firstOrNull { this.drop(idx).startsWith(it) }?.let { digits[it] }
    }
