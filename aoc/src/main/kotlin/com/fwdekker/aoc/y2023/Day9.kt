package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.read
import com.fwdekker.std.toInts


class Day9(resource: String = resource(2023, 9)) : Day() {
    private val lines = read(resource).linesNotBlank()


    override fun part1(): Int = lines.sumOf { it.toInts(' ').next() }

    override fun part2(): Int = lines.sumOf { it.toInts(' ').reversed().next() }


    private tailrec fun List<Int>.next(plus: Int = 0): Int =
        if (all { it == 0 }) plus
        else zipWithNext().map { it.second - it.first }.next(plus + last())
}


fun main() = Day9().run()
