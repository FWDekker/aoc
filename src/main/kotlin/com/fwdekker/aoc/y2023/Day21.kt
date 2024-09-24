package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.Coords
import com.fwdekker.std.cardinals
import com.fwdekker.std.cell
import com.fwdekker.std.cellMod
import com.fwdekker.std.contains
import com.fwdekker.std.coordsOf
import com.fwdekker.std.foldSelf
import com.fwdekker.std.readChart
import com.fwdekker.std.width


class Day21(resource: String = resource(2023, 21)) : Day(resource) {
    private val chart = readChart(resource)
    private val start = setOf(chart.coordsOf('S'))


    override fun part1(): Int = flood(start, steps = 64) { chart.contains(it) && chart.cell(it) != '#' }.size

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
