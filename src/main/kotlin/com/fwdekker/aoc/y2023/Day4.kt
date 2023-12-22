package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.ints
import com.fwdekker.aoc.std.readLines
import kotlin.math.pow


class Day4(resource: String = resource(2023, 4)) : Day(resource) {
    private val lines = readLines(resource)
    private val winsByLine =
        lines
            .map { line -> line.substringAfter(": ").trim().split('|').map { it.ints(Regex("\\s+")).toSet() } }
            .map { (drawn, winning) -> drawn.intersect(winning).size }


    override fun part1(): Int = winsByLine.sumOf { 2.0.pow(it - 1).toInt() }

    override fun part2(): Int =
        ArrayDeque(lines.indices.toList())
            .let { cards -> cards.onEach { cards.addAll((it + 1)..(it + winsByLine[it])) }.size }
}


fun main() = Day4().run()
