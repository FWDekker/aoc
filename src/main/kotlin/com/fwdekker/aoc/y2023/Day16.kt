package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.Heading
import com.fwdekker.aoc.std.cell
import com.fwdekker.aoc.std.cols
import com.fwdekker.aoc.std.contains
import com.fwdekker.aoc.std.height
import com.fwdekker.aoc.std.readLines
import com.fwdekker.aoc.std.rows
import com.fwdekker.aoc.std.width


class Day16(resource: String = resource(2023, 16)) : Day(resource) {
    private val lines = readLines(resource)


    override fun part1(): Int = traverse(lines, Heading(0, 0, 'E'))

    override fun part2(): Int =
        (lines.rows.flatMap { listOf(Heading(it, 0, 'E'), Heading(it, lines.width - 1, 'W')) } +
            lines.cols.flatMap { listOf(Heading(0, it, 'S'), Heading(lines.height - 1, it, 'N')) })
            .maxOf { traverse(lines, it) }


    private fun Char.traverse(from: Heading): List<Heading> =
        when (this) {
            '.' -> listOf(from.go { ahead })
            '/' -> listOf(from.go { flipNE })
            '\\' -> listOf(from.go { flipNW })
            '|' -> if (from.direction.vertical) listOf(from.go { ahead }) else from.go({ left }, { right })
            '-' -> if (from.direction.horizontal) listOf(from.go { ahead }) else from.go({ left }, { right })
            else -> emptyList()
        }

    private fun traverse(map: List<String>, start: Heading): Int {
        var heads = listOf(start)
        val history = heads.toMutableSet()

        while (heads.isNotEmpty()) {
            heads = heads
                .flatMap { map.cell(it.coords).traverse(it) }
                .filter { map.contains(it.coords) && it !in history }
                .also { history.addAll(it) }
        }

        return history.distinctBy { it.coords }.size
    }
}


fun main() = Day16().run()
