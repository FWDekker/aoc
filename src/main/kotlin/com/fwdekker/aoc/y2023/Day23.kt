package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.Chart
import com.fwdekker.std.Coords
import com.fwdekker.std.cardinals
import com.fwdekker.std.cartesian
import com.fwdekker.std.cell
import com.fwdekker.std.cols
import com.fwdekker.std.contains
import com.fwdekker.std.east
import com.fwdekker.std.firstRow
import com.fwdekker.std.lastRow
import com.fwdekker.std.lastRowIndex
import com.fwdekker.std.north
import com.fwdekker.std.readChart
import com.fwdekker.std.rows
import com.fwdekker.std.south
import com.fwdekker.std.west
import java.util.PriorityQueue


class Day23(resource: String = resource(2023, 23)) : Day() {
    private val chart = readChart(resource)

    private val start = Coords(0, chart.firstRow.indexOf('.'))
    private val end = Coords(chart.lastRowIndex, chart.lastRow.indexOf('.'))
    private val junctions =
        chart.rows.cartesian(chart.cols)
            .map { (row, col) -> Coords(row, col) }
            .filter { chart.contains(it) && chart.cell(it) != '#' }
            .filter { chart.dryNeighborsOf(it).size > 2 }
            .let { it + listOf(start, end) }


    override fun part1(): Int = chart.longestDistance(start, end) { chart.slipperyNeighborsOf(it) }

    override fun part2(): Int = chart.longestDistance(start, end) { chart.dryNeighborsOf(it) }


    private fun Chart.slipperyNeighborsOf(coords: Coords): List<Coords> =
        when (cell(coords)) {
            '#' -> emptyList()
            '^' -> listOf(coords.north)
            '>' -> listOf(coords.east)
            'v' -> listOf(coords.south)
            '<' -> listOf(coords.west)
            else -> coords.cardinals
        }.filter { contains(it) && cell(it) != '#' }

    private fun Chart.dryNeighborsOf(coords: Coords): List<Coords> =
        when (cell(coords)) {
            '#' -> emptyList()
            else -> coords.cardinals
        }.filter { contains(it) && cell(it) != '#' }

    private fun Chart.longestDistance(from: Coords, to: Coords, getNeighbors: (Coords) -> Iterable<Coords>): Int =
        longestDistance(from, to, junctions.associateWith { findShortestDistances(it, junctions, getNeighbors) })

    private fun Chart.longestDistance(
        from: Coords,
        to: Coords,
        edges: Map<Coords, Map<Coords, Int>>,
        history: Set<Coords> = setOf(from),
    ): Int =
        edges.getValue(from)
            .filter { (it, _) -> it !in history }
            .maxOfOrNull { (it, dist) -> if (it == to) dist else dist + longestDistance(it, to, edges, history + it) }
            ?: Integer.MIN_VALUE

    private fun findShortestDistances(
        from: Coords,
        to: Iterable<Coords>,
        getNeighbors: (Coords) -> Iterable<Coords>,
    ): Map<Coords, Int> {
        val distances = mutableMapOf<Coords, Int>().withDefault { Integer.MAX_VALUE }
        distances[from] = 0

        val queue = PriorityQueue<Coords> { o1, o2 -> distances.getValue(o1) - distances.getValue(o2) }
        queue.add(from)

        while (queue.isNotEmpty()) {
            val current = queue.remove()
            if (current != from && current in to) continue

            val distance = distances.getValue(current)
            getNeighbors(current)
                .filter { it !in distances }
                .forEach { nextStep ->
                    if (distance + 1 < distances.getValue(nextStep)) {
                        distances[nextStep] = distance + 1
                        queue.add(nextStep)
                    }
                }
        }

        return distances.filterKeys { it in to }
    }
}


fun main() = Day23().run()
