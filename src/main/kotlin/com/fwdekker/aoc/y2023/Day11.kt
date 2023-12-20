package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.readLines
import kotlin.math.max
import kotlin.math.min


fun main() {
    val lines = readLines("/y2023/Day11.txt")

    val expandX = lines.indices.filter { x -> lines[x].all { it == '.' } }
    val expandY = lines[0].indices.filter { y -> lines.all { it[y] == '.' } }
    val galaxies = lines.flatMapIndexed { idx, line -> line.indices.filter { line[it] == '#' }.map { Pair(idx, it) } }
    val pairs = galaxies.flatMap { a -> galaxies.map { b -> Pair(a, b) } }

    // Part 1
    println("Part one: ${pairs.sumOf { (a, b) -> a.distance(b, expandX, expandY, 2) } / 2}")

    // Part 2
    println("Part one: ${pairs.sumOf { (a, b) -> a.distance(b, expandX, expandY, 1000000) } / 2}")
}


private fun Int.distance(that: Int, expanded: List<Int>, weight: Long): Long =
    (min(this, that)..<max(this, that)).sumOf { if (it in expanded) weight else 1L }

private fun Pair<Int, Int>.distance(that: Pair<Int, Int>, expandX: List<Int>, expandY: List<Int>, weight: Long): Long =
    this.first.distance(that.first, expandX, weight) + this.second.distance(that.second, expandY, weight)
