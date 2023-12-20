package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.product
import com.fwdekker.aoc.std.readLines
import kotlin.math.max


fun main() {
    val lines = readLines("/y2023/Day3.txt")

    // Part 1
    lines
        .flatMapIndexed { lineIndex, line ->
            line.toList().skipFoldIndexed(emptyList<Int>()) { index, acc, _ ->
                line
                    .drop(index)
                    .takeWhile { it.isDigit() }
                    .let { number ->
                        Pair(
                            max(1, number.length),
                            if (number.indices.any { lines.isNextToSymbol(lineIndex, index + it) }) acc + number.toInt()
                            else acc
                        )
                    }
            }
        }
        .also { println("Part one: ${it.sum()}") }

    // Part 2
    lines
        .flatMapIndexed { lineIndex, line ->
            line
                .mapIndexed { index, char -> Pair(index, char) }
                .filter { (_, char) -> char == '*' }
                .map { (index, _) -> lines.getSurroundingNumbers(lineIndex, index) }
                .filter { it.size == 2 }
                .map { it.product() }
        }
        .also { println("Part two: ${it.sum()}") }
}


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

private fun List<String>.getChar(rowIdx: Int, colIdx: Int): Char? = this.getOrNull(rowIdx)?.getOrNull(colIdx)

private fun List<String>.isNextToSymbol(rowIdx: Int, colIdx: Int): Boolean =
    listOf(-1, 0, 1).any { rowDiff ->
        listOf(-1, 0, 1).any { colDiff ->
            this.getChar(rowIdx + rowDiff, colIdx + colDiff)?.let { it != '.' && !it.isDigit() } == true
        }
    }

private fun List<String>.getNumber(rowIdx: Int, colIdx: Int): Int? {
    if (this.getChar(rowIdx, colIdx)?.isDigit() == false) return null

    val row = this[rowIdx]
    return row.reversed().drop(row.length - 1 - colIdx).takeWhile { it.isDigit() }.drop(1).reversed()
        .plus(row.drop(colIdx).takeWhile { it.isDigit() })
        .toInt()
}

private fun List<String>.getSurroundingNumbers(row: Int, col: Int): List<Int> =
    listOf(-1, 0, 1)
        .flatMap { rowDiff ->
            val char = this.getChar(row + rowDiff, col)

            if (char == null) emptyList()
            else if (char.isDigit()) listOf(this.getNumber(row + rowDiff, col))
            else listOf(this.getNumber(row + rowDiff, col - 1), this.getNumber(row + rowDiff, col + 1))
        }
        .filterNotNull()
