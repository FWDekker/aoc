package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.ChartGraph
import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.associateWithIndex
import com.fwdekker.std.collections.neverNull
import com.fwdekker.std.grid.coordsOf
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.maths.distance
import com.fwdekker.std.maths.plus
import kotlin.math.absoluteValue


// See https://adventofcode.com/2024/day/20
class Day20(sample: Int? = null, private val minSavings: Int = 100) : Day(year = 2024, day = 20, sample = sample) {
    private val chart = input.toChart()


    override fun part1() = race(cheats = 2)

    override fun part2() = race(cheats = 20)


    private fun race(cheats: Int): Int {
        val path = ChartGraph(chart).shortestPath(chart.coordsOf('E'), chart.coordsOf('S'))!!
        val pathLength = path.size - 1
        val distanceLeft = path.associateWithIndex().neverNull()

        return path.sumOf { node ->
            (-cheats..cheats).asSequence()
                .flatMap { x -> (-cheats + x.absoluteValue..cheats - x.absoluteValue).map { y -> node + (x to y) } }
                .filter { it in distanceLeft.keys }
                .map { (pathLength - distanceLeft[node]) + node.distance(it) + distanceLeft[it] }
                .count { it <= pathLength - minSavings }
        }
    }
}


fun main() = Day20().run()
