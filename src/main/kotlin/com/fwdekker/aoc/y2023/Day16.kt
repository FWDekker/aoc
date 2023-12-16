package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day16.txt").lines().filter(String::isNotBlank)

    // Part 1
    println("Part one: ${Beam(0, 0, 'E').traverse(lines)}")

    // Part 2
    val beams = emptyList<Beam>() +
        lines.indices.flatMap { listOf(Beam(it, 0, 'E'), Beam(it, lines[0].lastIndex, 'W')) } +
        lines[0].indices.flatMap { listOf(Beam(0, it, 'S'), Beam(lines.lastIndex, it, 'N')) }
    println("Part two: ${beams.maxOf { it.traverse(lines) }}")
}


private data class Beam(val row: Int, val col: Int, val heading: Char) {
    fun traverse(map: List<String>): Int {
        var heads = listOf(this)
        val history = heads.toMutableSet()

        while (heads.isNotEmpty()) {
            heads = heads
                .flatMap { it.traverse(map[it.row][it.col]) }
                .filter { it.row in map.indices && it.col in map[0].indices && it !in history }
            history.addAll(heads)
        }

        return history.distinctBy { Pair(it.row, it.col) }.size
    }

    private fun traverse(cell: Char): Collection<Beam> =
        when (cell) {
            '.' -> listOf(move(heading))
            '/' -> listOf(move(mapOf('N' to 'E', 'E' to 'N', 'S' to 'W', 'W' to 'S')[heading]!!))
            '\\' -> listOf(move(mapOf('N' to 'W', 'E' to 'S', 'S' to 'E', 'W' to 'N')[heading]!!))
            '|' -> if (heading in "NS") listOf(move(heading)) else listOf(move('N'), move('S'))
            '-' -> if (heading in "EW") listOf(move(heading)) else listOf(move('E'), move('W'))
            else -> emptyList()
        }

    private fun move(direction: Char): Beam =
        when (direction) {
            'N' -> Beam(row - 1, col, 'N')
            'E' -> Beam(row, col + 1, 'E')
            'S' -> Beam(row + 1, col, 'S')
            'W' -> Beam(row, col - 1, 'W')
            else -> error("Unknown heading '$direction'.")
        }
}
