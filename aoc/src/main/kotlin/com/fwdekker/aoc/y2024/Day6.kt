package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.isDistinct
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.Heading
import com.fwdekker.std.grid.North
import com.fwdekker.std.grid.canMove
import com.fwdekker.std.grid.coordsOf
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.read


class Day6(resource: String = resource(2024, 6)) : Day() {
    private val chart = read(resource).toChart()
    private val start = chart.coordsOf('^')


    override fun part1() = patrol(Heading(start, North)).distinctBy { it.coords }.count()

    override fun part2(): Int {
        val route = patrol(Heading(start, North)).toList()
        val coords = route.map { it.coords }

        return coords.withIndex()
            .filter { (_, it) -> it != start }
            .filter { (idx, it) -> it !in coords.take(idx) }
            .count { (idx, it) -> patrol(route[idx - 1], it).isDistinct() }
    }


    private fun patrol(start: Heading, obstacle: Coords? = null): Sequence<Heading> =
        generateSequence(start) { heading ->
            val ahead = heading.go()

            if (!chart.canMove(heading)) null
            else if (chart[ahead.coords] == '#' || ahead.coords == obstacle) heading.face { right }
            else ahead
        }
}


fun main() = Day6().run()
