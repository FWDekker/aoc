package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Coords
import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.cardinals
import com.fwdekker.aoc.std.cell
import com.fwdekker.aoc.std.cellMod
import com.fwdekker.aoc.std.coordsOf
import com.fwdekker.aoc.std.foldSelf
import com.fwdekker.aoc.std.has
import com.fwdekker.aoc.std.readLines
import com.fwdekker.aoc.std.width


class Day21(resource: String = resource(2023, 21)) : Day(resource) {
    private val chart = readLines(resource)
    private val start = setOf(chart.coordsOf('S'))


    override fun part1(): Int = flood(start, steps = 64) { chart.has(it) && chart.cell(it) != '#' }.size

    override fun part2(): Long {
        val targetDistance = 26501365L
        val jumps = targetDistance / chart.width
        val diffs =
            listOf(targetDistance % chart.width, chart.width, chart.width)
                .runningFold(start) { coords, steps -> flood(coords, steps) { chart.cellMod(it) != '#' } }
                .zipWithNext()
                .map { (a, b) -> b.size - a.size }
        return 1L + diffs[0] + jumps * (diffs[1] + (jumps - 1) * (diffs[2] - diffs[1]) / 2)
    }


    private fun flood(start: Set<Coords>, steps: Number, filter: (Coords) -> Boolean): Set<Coords> =
        start.foldSelf(steps.toInt()) { places -> places.flatMap { it.cardinals.filter(filter) }.toSet() }
}


fun main() = Day21().run()
