package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.East
import com.fwdekker.std.grid.Grid
import com.fwdekker.std.grid.Heading
import com.fwdekker.std.grid.allCoords
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.coordsOf
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.maths.Graph


// See https://adventofcode.com/2024/day/16
class Day16(sample: Int? = null) : Day(year = 2024, day = 16, sample = sample) {
    private val chart = input.toChart()
    private val start = Heading(chart.coordsOf('S'), East)
    private val ends = chart.coordsOf('E').let { end -> Cardinal.entries.map { Heading(end, it) } }


    override fun part1(): Long =
        DeerGraph(chart)
            .apply { distances(start) }
            .let { graph -> ends.minOf { graph.distance(start, it)!! } }

    override fun part2(): Int {
        val graph = DeerGraph(chart)
            .apply {
                distances(start)
                ends.forEach { distances(it) }
            }

        val minDistance = ends.minOf { graph.distance(start, it)!! }
        return graph.nodes
            .filter { node ->
                ends.any { end ->
                    graph.distance(start, node)!! + graph.distance(end, node.face { behind })!! == minDistance
                }
            }
            .distinctBy { it.coords }
            .size
    }


    private class DeerGraph(private val chart: Grid<Char>) : Graph<Heading>() {
        override val nodes: Collection<Heading> =
            chart.allCoords
                .filter { chart[it] != '#' }
                .flatMap { coords -> Cardinal.entries.map { Heading(coords, it) } }
                .toList()

        override fun getWeight(start: Heading, end: Heading): Long =
            if (start.coords == end.coords) 1000L
            else 1L

        override fun getNeighbours(node: Heading): Collection<Heading> =
            listOf(node.go(), node.face(node.direction.left), node.face(node.direction.right))
                .filter { it.coords in chart && chart[it.coords] != '#' }
    }
}


fun main() = Day16().run()
