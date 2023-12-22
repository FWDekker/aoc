package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Coords
import com.fwdekker.aoc.std.Day
import com.fwdekker.aoc.std.cellOrNull
import com.fwdekker.aoc.std.col
import com.fwdekker.aoc.std.east
import com.fwdekker.aoc.std.north
import com.fwdekker.aoc.std.principals
import com.fwdekker.aoc.std.product
import com.fwdekker.aoc.std.readLines
import com.fwdekker.aoc.std.rowOf
import com.fwdekker.aoc.std.south
import com.fwdekker.aoc.std.west
import kotlin.math.max


class Day3(resource: String = resource(2023, 3)) : Day(resource) {
    private val lines = readLines(resource)


    override fun part1(): Int =
        lines
            .flatMapIndexed { lineIndex, line ->
                line.toList().skipFoldIndexed(emptyList<Int>()) { index, acc, _ ->
                    line.drop(index)
                        .takeWhile { it.isDigit() }
                        .let { number ->
                            Pair(
                                max(1, number.length),
                                if (number.indices.none { lines.isNextToSymbol(lineIndex, index + it) }) acc
                                else acc + number.toInt()
                            )
                        }
                }
            }
            .sum()

    override fun part2(): Long =
        lines
            .flatMapIndexed { lineIndex, line ->
                line.withIndex()
                    .filter { (_, char) -> char == '*' }
                    .map { (index, _) -> lines.getSurroundingNumbers(Coords(lineIndex, index)) }
                    .filter { it.size == 2 }
                    .map { it.product() }
            }
            .sum()


    private fun <T, R> List<T>.skipFoldIndexed(init: R, action: (Int, R, T) -> Pair<Int, R>): R {
        // Like `foldIndexed` except you can also return an `Int` saying how many subsequent iterations to skip

        val lastIndex = this.lastIndex

        var index = 0
        var accumulator = init
        while (index < lastIndex) {
            val out = action(index, accumulator, this[index])
            index += out.first
            accumulator = out.second
        }

        return accumulator
    }

    private fun List<String>.isNextToSymbol(rowIdx: Int, colIdx: Int): Boolean =
        Coords(rowIdx, colIdx).principals.asSequence().mapNotNull(::cellOrNull).any { it != '.' && !it.isDigit() }

    private fun List<String>.getNumber(coords: Coords): Int? {
        if (cellOrNull(coords)?.isDigit() == false) return null

        val row = rowOf(coords)
        return row.reversed().drop(row.length - 1 - coords.col.toInt()).takeWhile { it.isDigit() }.drop(1).reversed()
            .plus(row.drop(coords.col.toInt()).takeWhile { it.isDigit() })
            .toInt()
    }

    private fun List<String>.getSurroundingNumbers(coords: Coords): List<Int> =
        listOf(coords.north, coords, coords.south)
            .flatMap { other ->
                val char = cellOrNull(other)

                if (char == null) emptyList()
                else if (char.isDigit()) listOf(getNumber(other))
                else listOf(getNumber(other.west), getNumber(other.east))
            }
            .filterNotNull()
}


fun main() = Day3().run()
