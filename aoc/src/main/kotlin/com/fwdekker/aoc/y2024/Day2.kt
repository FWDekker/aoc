package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.without
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.toInts
import kotlin.math.absoluteValue
import kotlin.math.sign


// See https://adventofcode.com/2024/day/2
class Day2(sample: Int? = null) : Day(year = 2024, day = 2, sample = sample) {
    private val reports = input.linesNotBlank().toInts(' ')


    override fun part1(): Int = reports.count { it.isSafe() }

    override fun part2(): Int =
        reports.count { report -> report.isSafe() || report.indices.any { idx -> report.without(idx).isSafe() } }


    private fun List<Int>.isSafe(): Boolean {
        val diffs = windowed(2).map { it[0] - it[1] }
        return diffs.all { it.absoluteValue in 1..3 } && diffs.map { it.sign }.toSet().size == 1
    }
}


fun main() = Day2().run()
