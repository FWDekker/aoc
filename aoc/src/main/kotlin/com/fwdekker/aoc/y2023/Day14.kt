package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.foldSelf
import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.rotateCCW
import com.fwdekker.std.grid.rotateCW
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.maths.sumOfIndexed
import com.fwdekker.std.sortedDescending


// See https://adventofcode.com/2023/day/14
class Day14(sample: Int? = null) : Day(year = 2023, day = 14, sample = sample) {
    private val chart = input.toChart()


    override fun part1(): Int = chart.rotateCCW().tiltWest().rotateCW().calcLoad()

    override fun part2(): Int = chart.rotateCCW().cycle(1_000_000_000).rotateCW().calcLoad()


    private fun Chart.tiltWest(): Chart =
        map { row -> row.joinToString("").split("#").joinToString("#") { it.sortedDescending() }.toList() }

    private fun Chart.cycle(repetitions: Int = 1): Chart {
        val history = mutableMapOf(this to 0)

        var platform = this
        for (repetition in 1..repetitions) {
            platform = platform.foldSelf(repeats = 4) { it.tiltWest().rotateCW() }

            if (platform in history)
                return platform.cycle((repetitions - repetition) % (repetition - history.getValue(platform)))

            history[platform] = repetition
        }

        return platform
    }

    private fun Chart.calcLoad(): Int = sumOfIndexed { idx, line -> (size - idx) * line.count { it == 'O' } }
}


fun main() = Day14().run()
