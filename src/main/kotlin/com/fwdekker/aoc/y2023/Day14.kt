package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.foldSelf
import com.fwdekker.std.readLines
import com.fwdekker.std.rotateCCW
import com.fwdekker.std.rotateCW
import com.fwdekker.std.sortedDescending
import com.fwdekker.std.sumOfIndexed


class Day14(resource: String = resource(2023, 14)) : Day(resource) {
    private val lines = readLines(resource)


    override fun part1(): Int = lines.rotateCCW().tiltWest().rotateCW().calcLoad()

    override fun part2(): Int = lines.rotateCCW().cycle(1_000_000_000).rotateCW().calcLoad()


    private fun List<String>.tiltWest(): List<String> =
        map { line -> line.split('#').joinToString("#") { it.sortedDescending() } }

    private fun List<String>.cycle(repetitions: Int = 1): List<String> {
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

    private fun List<String>.calcLoad(): Int = sumOfIndexed { idx, line -> (size - idx) * line.count { it == 'O' } }
}


fun main() = Day14().run()
