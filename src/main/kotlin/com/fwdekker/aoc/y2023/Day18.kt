package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.Coords
import com.fwdekker.std.Direction
import com.fwdekker.std.distance
import com.fwdekker.std.getMod
import com.fwdekker.std.move
import com.fwdekker.std.readLines
import kotlin.math.abs
import kotlin.math.roundToLong


@OptIn(ExperimentalStdlibApi::class)
class Day18(resource: String = resource(2023, 18)) : Day() {
    private val lines = readLines(resource)


    override fun part1(): Long =
        lines
            .map { line -> line.split(' ') }
            .map { Pair(Direction.fromChar(it[0][0], "URDL"), it[1].toLong()) }
            .runningFold(Coords(0L, 0L)) { previous, (direction, distance) -> previous.move(direction, distance) }
            .area()

    override fun part2(): Long =
        lines
            .map { line -> line.split(' ')[2].removeSurrounding("(#", ")") }
            .map { Pair(Direction.fromInt(it.drop(5).toInt()), it.take(5).hexToInt().toLong()) }
            .runningFold(Coords(0L, 0L)) { previous, (direction, distance) -> previous.move(direction, distance) }
            .area()


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

}


fun main() = Day18().run()
