package com.fwdekker.aoc.y2023

import com.fwdekker.std.Coords
import com.fwdekker.aoc.Day
import com.fwdekker.std.allCoordsOf
import com.fwdekker.std.cartesian
import com.fwdekker.std.col
import com.fwdekker.std.cols
import com.fwdekker.std.longs
import com.fwdekker.std.readLines
import com.fwdekker.std.row
import com.fwdekker.std.rows
import kotlin.math.max
import kotlin.math.min


class Day11(resource: String = resource(2023, 11)) : Day(resource) {
    private val chart = readLines(resource)

    private val expandX = chart.rows.filter { x -> chart[x].all { it == '.' } }.longs()
    private val expandY = chart.cols.filter { y -> chart.all { it[y] == '.' } }.longs()
    private val galaxies = chart.allCoordsOf('#')
    private val pairs = galaxies.cartesian(galaxies)


    override fun part1(): Long = pairs.sumOf { (a, b) -> a.distance(b, expandX, expandY, 2) } / 2

    override fun part2(): Long = pairs.sumOf { (a, b) -> a.distance(b, expandX, expandY, 1000000) } / 2


    private fun Long.distance(that: Long, expanded: List<Long>, weight: Long): Long =
        (min(this, that)..<max(this, that)).sumOf { if (it in expanded) weight else 1L }

    private fun Coords.distance(that: Coords, expandX: List<Long>, expandY: List<Long>, weight: Long): Long =
        this.row.distance(that.row, expandX, weight) + this.col.distance(that.col, expandY, weight)
}


fun main() = Day11().run()
