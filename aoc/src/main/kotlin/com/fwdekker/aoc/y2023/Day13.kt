package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.Chart
import com.fwdekker.std.read
import com.fwdekker.std.toCharts
import com.fwdekker.std.transpose


class Day13(resource: String = resource(2023, 13)) : Day() {
    private val patterns = read(resource).toCharts()


    override fun part1(): Int = patterns.sumOf { it.value(targetDiff = 0) }

    override fun part2(): Int = patterns.sumOf { it.value(targetDiff = 1) }


    private fun Chart.diff(a: Int, b: Int): Int =
        if (a !in indices || b !in indices) 0
        else (this[a] zip this[b]).count { it.first != it.second }

    private fun Chart.mirrors(targetDiff: Int = 0): List<Int> =
        indices.filter { row -> indices.sumOf { offset -> diff(row - offset - 1, row + offset) } == targetDiff }

    private fun Chart.value(targetDiff: Int = 0): Int =
        mirrors(targetDiff).sumOf { it * 100 } + transpose().mirrors(targetDiff).sum()
}


fun main() = Day13().run()
