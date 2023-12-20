package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Heading
import com.fwdekker.aoc.std.cell
import com.fwdekker.aoc.std.has
import com.fwdekker.aoc.std.readLines


fun main() {
    val lines = readLines("/y2023/Day16.txt")

    // Part 1
    println("Part one: ${traverse(lines, Heading(0, 0, 'E'))}")

    // Part 2
    val beams = emptyList<Heading>() +
        lines.indices.flatMap { listOf(Heading(it, 0, 'E'), Heading(it, lines[0].lastIndex, 'W')) } +
        lines[0].indices.flatMap { listOf(Heading(0, it, 'S'), Heading(lines.lastIndex, it, 'N')) }
    println("Part two: ${beams.maxOf { traverse(lines, it) }}")
}


private fun Char.traverse(from: Heading): Collection<Heading> =
    when (this) {
        '.' -> listOf(from.go { ahead })
        '/' -> listOf(from.go { flipNE })
        '\\' -> listOf(from.go { flipNW })
        '|' -> if (from.direction.vertical) listOf(from.go { ahead }) else listOf(from.go { left }, from.go { right })
        '-' -> if (from.direction.horizontal) listOf(from.go { ahead }) else listOf(from.go { left }, from.go { right })
        else -> emptyList()
    }

private fun traverse(map: List<String>, start: Heading): Int {
    var heads = listOf(start)
    val history = heads.toMutableSet()

    while (heads.isNotEmpty()) {
        heads = heads
            .flatMap { map.cell(it.coords).traverse(it) }
            .filter { map.has(it.coords) && it !in history }
        history.addAll(heads)
    }

    return history.distinctBy { it.coords }.size
}
