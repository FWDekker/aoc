package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.cyclic
import com.fwdekker.aoc.std.lcm
import com.fwdekker.aoc.std.readLines


fun main() {
    val lines = readLines("/y2023/Day8.txt")

    val steps = lines.first().toList()
    val map = lines.drop(1)
        .map { it.split(" = ") }
        .associate { Pair(it[0], it[1].removeSurrounding("(", ")").split(", ")) }
        .mapValues { (_, it) -> mapOf('L' to it[0], 'R' to it[1]) }

    // Part 1
    println("Part one: ${map.distance(steps, from = "AAA", to = { it == "ZZZ" })}")

    // Part 2
    map.keys.filter { it.endsWith('A') }
        .map { ghost -> map.distance(steps, from = ghost, to = { it.endsWith('Z') }) }
        .also { println("Part two: ${it.lcm()}") }
}


private fun <T, R> Sequence<T>.foldUntil(initial: R, condition: (R, T) -> Boolean, operation: (R, T) -> R): R {
    var current = initial

    val iterator = iterator()
    while (iterator.hasNext()) {
        val next = iterator.next()
        if (condition(current, next))
            break

        current = operation(current, next)
    }

    return current
}

private fun Map<String, Map<Char, String>>.distance(steps: List<Char>, from: String, to: (String) -> Boolean): Long =
    steps.cyclic()
        .foldUntil(Pair(0L, from), { it, _ -> to(it.second) }) { (steps, location), instruction ->
            Pair(steps + 1L, this[location]!![instruction]!!)
        }
        .first
