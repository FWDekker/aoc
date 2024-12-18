package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.cardinals
import com.fwdekker.std.grid.col
import com.fwdekker.std.grid.row
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.Graph
import com.fwdekker.std.toInts


// See https://adventofcode.com/2024/day/18
class Day18(
    sample: Int? = null,
    private val gridSize: Int = 70,
    private val bytesMax: Int = 1024,
) : Day(year = 2024, day = 18, sample = sample) {
    private val bytes = input.linesNotBlank().map { it.toInts(',').asPair() }


    override fun part1(): Long = AutomatonGraph(bytes.take(bytesMax).toSet()).distance(0 to 0, gridSize to gridSize)!!

    override fun part2(): String {
        val bytesSet = mutableSetOf<Coords>()

        var path: Set<Coords>
        while (true) {
            path = (AutomatonGraph(bytesSet).shortestPath(0 to 0, gridSize to gridSize) ?: break).toSet()
            bytesSet += bytes.drop(bytesSet.size).takeWhile { it !in path }
            bytesSet += bytes[bytesSet.size]
        }

        return bytes[bytesSet.size - 1].let { "${it.first},${it.second}" }
    }


    inner class AutomatonGraph(private val bytes: Set<Coords>) : Graph<Coords>() {
        override val nodes: Collection<Coords> get() = throw NotImplementedError("")

        override fun getWeight(start: Coords, end: Coords): Long = 1L

        override fun getNeighbours(node: Coords): Collection<Coords> =
            node.cardinals.filter { it.row in 0..gridSize && it.col in 0..gridSize && it !in bytes }
    }
}


fun main() = Day18().run()
