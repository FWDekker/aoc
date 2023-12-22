package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Coords
import com.fwdekker.aoc.std.Direction
import com.fwdekker.aoc.std.distance
import com.fwdekker.aoc.std.getMod
import com.fwdekker.aoc.std.move
import com.fwdekker.aoc.std.readLines
import kotlin.math.abs
import kotlin.math.roundToLong


@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val lines = readLines("/y2023/Day18.txt")

    // Part 1
    lines
        .map { line -> line.split(' ') }
        .map { Pair(Direction.fromChar(it[0][0], "URDL"), it[1].toLong()) }
        .runningFold(Coords(0L, 0L)) { previous, (direction, distance) -> previous.move(direction, distance) }
        .also { println("Part one: ${it.area()}") }

    // Part 2
    lines
        .map { line -> line.split(' ')[2].removeSurrounding("(#", ")") }
        .map { Pair(Direction.fromInt(it.drop(5).toInt()), it.take(5).hexToInt().toLong()) }
        .runningFold(Coords(0L, 0L)) { previous, (direction, distance) -> previous.move(direction, distance) }
        .also { println("Part two: ${it.area()}") }
}


private fun List<Pair<Long, Long>>.area(): Long {
    val area = indices
        .map { idx -> Triple(getMod(idx - 1), getMod(idx), getMod(idx + 1)) }
        .sumOf { (prev, cur, next) -> cur.second * (next.first - prev.first) }
        .let { abs(it).toDouble() / 2 }
    val boundaryLength = indices
        .map { idx -> Pair(getMod(idx - 1), getMod(idx)) }
        .sumOf { (prev, cur) -> prev.distance(cur) }
    val interiorPoints = (area + 1 - boundaryLength / 2).roundToLong()
    return interiorPoints + boundaryLength
}
