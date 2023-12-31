package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.ints
import com.fwdekker.aoc.std.readLines


class Day9(resource: String = resource(2023, 9)) : Day(resource) {
    private val lines = readLines(resource)


    override fun part1(): Int = lines.sumOf { it.ints(' ').next() }

    override fun part2(): Int = lines.sumOf { it.ints(' ').reversed().next() }


    private tailrec fun List<Int>.next(plus: Int = 0): Int =
        if (all { it == 0 }) plus
        else zipWithNext().map { it.second - it.first }.next(plus + last())
}


fun main() = Day9().run()
