package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day3.txt").lines().filter(String::isNotBlank)

    // Part 1
    lines
        .flatMapIndexed { lineIndex, line ->
            val partNumbers = mutableListOf<Int>()

            var remaining = line.mapIndexed { index, char -> Pair(index, char) }
            while (remaining.isNotEmpty()) {
                remaining = remaining.dropWhile { (_, char) -> !char.isDigit() }
                val number = remaining.takeWhile { (_, char) -> char.isDigit() }
                if (number.isEmpty())
                    continue

                remaining = remaining.drop(number.size)
                val numberIndices = number.first().first..number.last().first
                val isPartNumber = numberIndices.any { lines.isNextToSymbol(lineIndex, it) }
                if (isPartNumber)
                    partNumbers += number.map { it.second }.joinToString(separator = "").toInt()
            }

            partNumbers
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
                .map { it.reduce(Int::times) }
        }
        .also { println("Part two: ${it.sum()}") }
}


fun Char.isSymbol(): Boolean = this != '.' && !this.isDigit()

fun List<String>.isNextToSymbol(row: Int, col: Int): Boolean =
    listOf(-1, 0, 1).fold(false) { rowAcc, rowDiff ->
        rowAcc || listOf(-1, 0, 1).fold(false) { colAcc, colDiff ->
            colAcc || this.getOrNull(row + rowDiff)?.getOrNull(col + colDiff)?.isSymbol() ?: false
        }
    }

fun String.getNumberAt(col: Int): Int? {
    if (col !in this.indices || !this[col].isDigit()) return null

    return this.reversed().drop(this.length - 1 - col).takeWhile { it.isDigit() }.drop(1).reversed()
        .plus(this.drop(col).takeWhile { it.isDigit() })
        .toInt()
}

fun List<String>.getSurroundingNumbers(row: Int, col: Int): List<Int> {
    val surroundingNumbers = mutableListOf<Int?>()

    if (this[row][col].isDigit()) {
        surroundingNumbers += this[row].getNumberAt(col)
    } else {
        surroundingNumbers += this[row].getNumberAt(col - 1)
        surroundingNumbers += this[row].getNumberAt(col + 1)
    }

    if (row > 0) {
        if (this[row - 1][col].isDigit()) {
            surroundingNumbers += this[row - 1].getNumberAt(col)
        } else {
            surroundingNumbers += this[row - 1].getNumberAt(col - 1)
            surroundingNumbers += this[row - 1].getNumberAt(col + 1)
        }
    }
    if (row < this.lastIndex) {
        if (this[row + 1][col].isDigit()) {
            surroundingNumbers += this[row + 1].getNumberAt(col)
        } else {
            surroundingNumbers += this[row + 1].getNumberAt(col - 1)
            surroundingNumbers += this[row + 1].getNumberAt(col + 1)
        }
    }

    return surroundingNumbers.filterNotNull()
}
