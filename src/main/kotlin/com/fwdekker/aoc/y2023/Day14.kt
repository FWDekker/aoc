package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.Chart
import com.fwdekker.std.foldSelf
import com.fwdekker.std.readChart
import com.fwdekker.std.rotateCCW
import com.fwdekker.std.rotateCW
import com.fwdekker.std.sortedDescending
import com.fwdekker.std.sumOfIndexed
import com.fwdekker.std.toRaw
import com.fwdekker.std.toRow


class Day14(resource: String = resource(2023, 14)) : Day(resource) {
    private val chart = readChart(resource)


    override fun part1(): Int = chart.rotateCCW().tiltWest().rotateCW().calcLoad()

    override fun part2(): Int = chart.rotateCCW().cycle(1_000_000_000).rotateCW().calcLoad()


    private fun Chart.tiltWest(): Chart =
        map { row -> row.toRaw().split("#").joinToString("#") { it.sortedDescending() }.toRow() }

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
