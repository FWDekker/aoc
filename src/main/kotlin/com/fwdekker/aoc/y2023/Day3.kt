package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.Chart
import com.fwdekker.std.Coords
import com.fwdekker.std.cellOrNull
import com.fwdekker.std.col
import com.fwdekker.std.east
import com.fwdekker.std.north
import com.fwdekker.std.principals
import com.fwdekker.std.product
import com.fwdekker.std.readChart
import com.fwdekker.std.rowOf
import com.fwdekker.std.south
import com.fwdekker.std.toRaw
import com.fwdekker.std.west
import kotlin.math.max


class Day3(resource: String = resource(2023, 3)) : Day(resource) {
    private val chart = readChart(resource)


    override fun part1(): Int =
        chart
            .flatMapIndexed { rowIndex, row ->
                row.skipFoldIndexed(emptyList<Int>()) { index, acc, _ ->
                    row.drop(index)
                        .takeWhile { it.isDigit() }
                        .let { partialRow ->
                            Pair(
                                max(1, partialRow.size),
                                if (partialRow.indices.none { chart.isNextToSymbol(rowIndex, index + it) }) acc
                                else acc + partialRow.toRaw().toInt()
                            )
                        }
                }
            }
            .sum()

    override fun part2(): Long =
        chart
            .flatMapIndexed { rowIndex, row ->
                row.asSequence()
                    .withIndex()
                    .filter { (_, char) -> char == '*' }
                    .map { (index, _) -> chart.getSurroundingNumbers(Coords(rowIndex, index)) }
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

    private fun Chart.isNextToSymbol(rowIdx: Int, colIdx: Int): Boolean =
        Coords(rowIdx, colIdx).principals.asSequence().mapNotNull(::cellOrNull).any { it != '.' && !it.isDigit() }

    private fun Chart.getNumber(coords: Coords): Int? {
        if (cellOrNull(coords)?.isDigit() == false) return null

        val row = rowOf(coords)
        return row.reversed().drop(row.size - 1 - coords.col.toInt()).takeWhile { it.isDigit() }.drop(1).reversed()
            .plus(row.drop(coords.col.toInt()).takeWhile { it.isDigit() })
            .toRaw().toInt()
    }

    private fun Chart.getSurroundingNumbers(coords: Coords): List<Int> =
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
