package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.map
import com.fwdekker.std.collections.zipped
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.productOf
import com.fwdekker.std.read
import com.fwdekker.std.toLongs


class Day6(resource: String = resource(2023, 6)) : Day() {
    private val lines = read(resource).linesNotBlank().asPair().map { it.substringAfter(": ") }


    override fun part1(): Long =
        lines.map { it.toLongs(Regex("\\s+")) }.zipped().productOf { it.countWays() }

    override fun part2(): Long =
        lines.map { it.filter(Char::isDigit).toLong() }.countWays()


    private fun Pair<Long, Long>.countWays(): Long =
        let { (time, distance) -> (0..<time).count { (time - it) * it > distance }.toLong() }
}


fun main() = Day6().run()
