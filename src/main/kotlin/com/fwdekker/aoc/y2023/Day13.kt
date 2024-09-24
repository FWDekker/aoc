package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.readSections
import com.fwdekker.std.transpose


class Day13(resource: String = resource(2023, 13)) : Day(resource) {
    private val patterns = readSections(resource)


    override fun part1(): Int = patterns.sumOf { it.value(targetDiff = 0) }

    override fun part2(): Int = patterns.sumOf { it.value(targetDiff = 1) }


    private fun List<CharSequence>.diff(a: Int, b: Int): Int =
        if (a !in indices || b !in indices) 0
        else (this[a] zip this[b]).count { it.first != it.second }

    private fun List<CharSequence>.mirrors(targetDiff: Int = 0): List<Int> =
        indices.filter { row -> indices.sumOf { offset -> diff(row - offset - 1, row + offset) } == targetDiff }

    private fun List<String>.value(targetDiff: Int = 0): Int =
        mirrors(targetDiff).sumOf { it * 100 } + transpose().mirrors(targetDiff).sum()

}


fun main() = Day13().run()
