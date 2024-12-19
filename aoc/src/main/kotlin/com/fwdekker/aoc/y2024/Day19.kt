package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.sections


// See https://adventofcode.com/2024/day/19
class Day19(sample: Int? = null) : Day(year = 2024, day = 19, sample = sample) {
    private val sections = input.sections()
    private val towels = sections[0][0].split(", ").asSequence()
    private val patterns = sections[1]

    private val designWayCounts = mutableMapOf<String, Long>()


    override fun part1(): Int = patterns.count { countDesignWays(it) != 0L }

    override fun part2(): Long = patterns.sumOf { countDesignWays(it) }


    private fun countDesignWays(target: String): Long =
        designWayCounts.getOrPut(target) {
            if (target.isEmpty()) 1L
            else towels.filter { target.take(it.length) == it }.sumOf { countDesignWays(target.drop(it.length)) }
        }
}


fun main() = Day19().run()
