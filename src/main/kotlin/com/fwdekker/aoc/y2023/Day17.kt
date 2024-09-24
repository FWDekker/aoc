package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.Chart
import com.fwdekker.std.Coords
import com.fwdekker.std.Direction
import com.fwdekker.std.Heading
import com.fwdekker.std.cell
import com.fwdekker.std.contains
import com.fwdekker.std.lastColIndex
import com.fwdekker.std.lastRowIndex
import com.fwdekker.std.readChart
import java.util.PriorityQueue


class Day17(resource: String = resource(2023, 17)) : Day(resource) {
    private val chart = readChart(resource)


    override fun part1(): Int = chart.smallestLoss({ it.neighbors() }, { true })

    override fun part2(): Int = chart.smallestLoss({ it.ultraNeighbors() }, { it.times >= 4 })


    private data class Step(val heading: Heading, val times: Int) {
        val coords: Coords get() = heading.coords
        val direction: Direction get() = heading.direction

        private fun move(direction: Direction): Step =
            Step(heading.go { direction }, if (direction == this.direction) times + 1 else 1)

        fun neighbors(): List<Step> =
            Direction.entries
                .filter { (it != direction || times < 3) && it != direction.behind }
                .map { move(it) }

        fun ultraNeighbors(): List<Step> =
            Direction.entries
                .filter { (if (it == direction) times < 10 else times >= 4) && it != direction.behind }
                .map { move(it) }
    }

    private fun Chart.smallestLoss(getNeighbors: (Step) -> Iterable<Step>, validEnd: (Step) -> Boolean): Int {
        val distances = mutableMapOf<Step, Int>().withDefault { Integer.MAX_VALUE }
        val queue = PriorityQueue<Step> { o1, o2 -> distances.getValue(o1) - distances.getValue(o2) }

        val end = Coords(lastRowIndex, lastColIndex)

        listOf(Direction.SOUTH, Direction.EAST)
            .map { Step(Heading(0, 0, it), 0) }
            .forEach {
                distances[it] = 0
                queue.add(it)
            }

        while (queue.isNotEmpty()) {
            val current = queue.remove()
            if (current.coords == end) continue

            val distance = distances.getValue(current)
            getNeighbors(current)
                .filter { contains(it.coords) && it !in distances }
                .forEach { next ->
                    val weight = cell(next.coords).digitToInt()
                    if (distance + weight < distances.getValue(next)) {
                        distances[next] = distance + weight
                        queue.add(next)
                    }
                }
        }

        return distances.filter { it.key.coords == end && validEnd(it.key) }.values.min()
    }
}


fun main() = Day17().run()
