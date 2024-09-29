package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.asPair
import com.fwdekker.std.toLongs
import com.fwdekker.std.map
import com.fwdekker.std.productOf
import com.fwdekker.std.readLines
import com.fwdekker.std.zipped


class Day6(resource: String = resource(2023, 6)) : Day() {
    private val lines = readLines(resource).asPair().map { it.substringAfter(": ") }


    override fun part1(): Long =
        lines.map { it.toLongs(Regex("\\s+")) }.zipped().productOf { it.countWays() }

    override fun part2(): Long =
        lines.map { it.filter(Char::isDigit).toLong() }.countWays()


    private fun Pair<Long, Long>.countWays(): Long =
        let { (time, distance) -> (0..<time).count { (time - it) * it > distance }.toLong() }
}


fun main() = Day6().run()
