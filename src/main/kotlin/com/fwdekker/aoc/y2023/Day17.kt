package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.readLines
import java.util.PriorityQueue


fun main() {
    val lines = readLines("/y2023/Day17.txt")

    // Part 1
    println("Part one: ${lines.smallestLoss(EdsgerStep::neighbors)}")

    // Part 2
    println("Part one: ${lines.smallestLoss(EdsgerStep::ultraNeighbors)}")
}


private fun Char?.opposite(): Char? = mapOf('N' to 'S', 'S' to 'N', 'W' to 'E', 'E' to 'W')[this]

private data class EdsgerStep(val coords: Pair<Int, Int>, val direction: Char, val times: Int) {
    private fun move(newDirection: Char): EdsgerStep =
        EdsgerStep(
            coords = when (newDirection) {
                'N' -> Pair(coords.first - 1, coords.second)
                'E' -> Pair(coords.first, coords.second + 1)
                'S' -> Pair(coords.first + 1, coords.second)
                'W' -> Pair(coords.first, coords.second - 1)
                else -> error("Unknown direction '$newDirection'.")
            },
            direction = newDirection,
            times = if (newDirection == direction) times + 1 else 1,
        )

    fun neighbors(): Collection<EdsgerStep> =
        "NESW"
            .filter { it != direction && it != direction.opposite() || it == direction && times < 3 }
            .map { move(it) }

    fun ultraNeighbors(): Collection<EdsgerStep> =
        "NESW"
            .filter { it != direction && it != direction.opposite() && times >= 4 || it == direction && times < 10 }
            .map { move(it) }
}

private fun List<String>.getLoss(coords: Pair<Int, Int>): Int = this[coords.first][coords.second].digitToInt()

private fun List<String>.smallestLoss(getNeighbors: (EdsgerStep) -> Collection<EdsgerStep>): Int {
    val distances = mutableMapOf<EdsgerStep, Int>().withDefault { Integer.MAX_VALUE }
    val queue = PriorityQueue<EdsgerStep> { o1, o2 -> distances.getValue(o1) - distances.getValue(o2) }

    val end = Pair(this.lastIndex, this[0].lastIndex)

    "SE".forEach {
        val start = EdsgerStep(Pair(0, 0), it, 0)
        distances[start] = 0
        queue.add(start)
    }

    while (queue.isNotEmpty()) {
        val current = queue.remove()
        if (current.coords == end) break

        val distance = distances.getValue(current)
        getNeighbors(current)
            .filter { it.coords.first in this.indices && it.coords.second in this[0].indices && it !in distances }
            .forEach { nextStep ->
                val nextDistance = distance + getLoss(nextStep.coords)
                if (nextDistance < distances.getValue(nextStep)) {
                    distances[nextStep] = nextDistance
                    queue.add(nextStep)
                }
            }
    }

    return distances.filter { it.key.coords == end }.values.min()
}
