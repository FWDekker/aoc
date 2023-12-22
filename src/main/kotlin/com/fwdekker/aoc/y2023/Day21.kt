package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Chart
import com.fwdekker.aoc.std.Coords
import com.fwdekker.aoc.std.cardinals
import com.fwdekker.aoc.std.cell
import com.fwdekker.aoc.std.cellMod
import com.fwdekker.aoc.std.coordsOf
import com.fwdekker.aoc.std.foldSelf
import com.fwdekker.aoc.std.has
import com.fwdekker.aoc.std.readLines
import com.fwdekker.aoc.std.width


fun main() {
    val chart = readLines("/y2023/Day21.txt")

    val start = setOf(chart.coordsOf('S'))

    // Part 1
    println("Part one: ${chart.flood(start, steps = 64) { has(it) && cell(it) != '#' }.size}")

    // Part 2
    val targetDistance = 26501365
    val jumps = (targetDistance / chart.width).toLong()
    val diffs = listOf(targetDistance % chart.width, chart.width, chart.width)
        .runningFold(start) { coords, steps -> chart.flood(coords, steps) { cellMod(it) != '#' } }.map { it.size }
        .zipWithNext().map { (a, b) -> b - a }
    println("Part two: ${1L + diffs[0] + jumps * (diffs[1] + (jumps - 1) * (diffs[2] - diffs[1]) / 2)}")
}


fun Chart.flood(start: Set<Coords>, steps: Int, filter: Chart.(Coords) -> Boolean): Set<Coords> =
    start.foldSelf(steps) { places -> places.flatMap { place -> place.cardinals.filter { this.filter(it) } }.toSet() }
