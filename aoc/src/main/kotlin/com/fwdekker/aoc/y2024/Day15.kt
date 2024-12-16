package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.East
import com.fwdekker.std.grid.MutableGrid
import com.fwdekker.std.grid.North
import com.fwdekker.std.grid.South
import com.fwdekker.std.grid.West
import com.fwdekker.std.grid.allCoords
import com.fwdekker.std.grid.allCoordsOf
import com.fwdekker.std.grid.col
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.coordsOf
import com.fwdekker.std.grid.east
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.move
import com.fwdekker.std.grid.row
import com.fwdekker.std.grid.set
import com.fwdekker.std.grid.west
import com.fwdekker.std.maths.Graph
import com.fwdekker.std.sections


// See https://adventofcode.com/2024/day/15
class Day15(sample: Int? = null) : Day(year = 2024, day = 15, sample = sample) {
    private val sections = input.sections()
    private val chart = sections[0].map { it.toList() }
    private val moves = sections[1].joinToString("")


    override fun part1(): Long = chart.map { it.toMutableList() }.releaseFish()

    override fun part2(): Long =
        chart.map { row ->
            row.joinToString("") {
                when (it) {
                    '#' -> "##"
                    'O' -> "[]"
                    '.' -> ".."
                    '@' -> "@."
                    else -> error("Invalid character '$it'.")
                }
            }.toMutableList()
        }.releaseFish()


    private fun MutableGrid<Char>.releaseFish(): Long {
        moves.forEach { move(coordsOf('@'), it.toCardinal()) }
        return allCoordsOf('O').sumOf { 100L * it.row + it.col } + allCoordsOf('[').sumOf { 100L * it.row + it.col }
    }

    private fun Char.toCardinal(): Cardinal =
        when (this) {
            '^' -> North; '>' -> East; 'v' -> South; '<' -> West; else -> error("")
        }


    private fun MutableGrid<Char>.move(start: Coords, direction: Cardinal) {
        if (MoveGraph(this, direction).breadthFirst(start.move(direction)).any { this[it] == '#' })
            return

        val end = start.move(direction)
        when (this[end]) {
            'O' -> {
                move(end, direction)
            }

            '[' -> {
                move(end, direction)
                if (direction.vertical) move(end.east(), direction)
            }

            ']' -> {
                move(end, direction)
                if (direction.vertical) move(end.west(), direction)
            }
        }

        this[start] = this[end].also { this[end] = this[start] }
    }


    private class MoveGraph(private val chart: MutableGrid<Char>, private val direction: Cardinal) : Graph<Coords>() {
        override val nodes: Collection<Coords> get() = chart.allCoords.toList()

        override fun getWeight(start: Coords, end: Coords): Long = 1L

        override fun getNeighbours(node: Coords): Collection<Coords> =
            when (chart[node]) {
                'O' -> listOf(node.move(direction))
                '[' -> listOf(node.east(), node.move(direction))
                ']' -> listOf(node.west(), node.move(direction))
                else -> emptyList()
            }.filter { it in chart }
    }
}


fun main() = Day15().run()
