package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.Chart
import com.fwdekker.std.Coords
import com.fwdekker.std.Direction
import com.fwdekker.std.Heading
import com.fwdekker.std.cardinals
import com.fwdekker.std.cell
import com.fwdekker.std.contains
import com.fwdekker.std.coordsOf
import com.fwdekker.std.move
import com.fwdekker.std.readChart


class Day10(resource: String = resource(2023, 10)) : Day(resource) {
    private val maze = readChart(resource)

    private val start = maze.coordsOf('S')
    private val cycle = Direction.entries.asSequence()
        .filter { maze.contains(start.move(it)) }
        .map { Raccoon(maze, Heading(start, it)) }
        .onEach { it.scurry() }
        .first { it.isCycle() }


    override fun part1(): Int = cycle.distance / 2

    override fun part2(): Int = cycle.getEnclosedSpace()
}

private class Raccoon(val maze: Chart, heading: Heading) {
    private val path: MutableSet<Coords> = mutableSetOf(heading.coords)
    private var heading: Heading? = heading

    private val leftPipes: MutableSet<Coords> = mutableSetOf()
    private val rightPipes: MutableSet<Coords> = mutableSetOf()

    val distance: Int get() = path.size


    fun isCycle(): Boolean = heading?.coords == path.first() && distance > 1

    fun isDone(): Boolean = isCycle() || heading == null


    fun scurry() {
        while (!isDone())
            step()
    }

    private fun step() {
        val oldHeading = heading!!
        val newHeading = maze.cell(oldHeading.coords).traverse(oldHeading)
            .also { heading = it }
            ?: return

        path.add(newHeading.coords)

        when (newHeading.direction) {
            oldHeading.direction.left -> {
                rightPipes.add(oldHeading.go { right }.coords)
                rightPipes.add(oldHeading.go { ahead }.coords)
            }

            oldHeading.direction.right -> {
                leftPipes.add(oldHeading.go { left }.coords)
                leftPipes.add(oldHeading.go { ahead }.coords)
            }

            oldHeading.direction.ahead -> {
                leftPipes.add(oldHeading.go { left }.coords)
                rightPipes.add(oldHeading.go { right }.coords)
            }

            else -> {}
        }
    }


    fun getEnclosedSpace(): Int {
        require(isDone()) { "Cannot find enclosed space before scurrying." }
        require(isCycle()) { "Cannot find enclosed space if no cycle was scurried around." }

        val encloses = (if (rightPipes.size > leftPipes.size) leftPipes else rightPipes).toMutableSet()

        encloses.removeAll(path)
        encloses.retainAll { maze.contains(it) }

        var enclosed = 0
        while (encloses.size > enclosed) {
            enclosed = encloses.size
            encloses.addAll(encloses.flatMap { it.cardinals }.toSet().subtract(path))
            encloses.retainAll { maze.contains(it) }
        }

        return encloses.size
    }


    private fun Char.traverse(from: Heading): Heading? {
        val table: Map<Char, Direction.() -> Direction> =
            when (from.direction) {
                Direction.NORTH -> mapOf('S' to { ahead }, '|' to { ahead }, '7' to { left }, 'F' to { right })
                Direction.EAST -> mapOf('S' to { ahead }, '-' to { ahead }, 'J' to { left }, '7' to { right })
                Direction.SOUTH -> mapOf('S' to { ahead }, '|' to { ahead }, 'J' to { right }, 'L' to { left })
                Direction.WEST -> mapOf('S' to { ahead }, '-' to { ahead }, 'L' to { right }, 'F' to { left })
            }

        return table[this]?.invoke(from.direction)?.let(from::go)
    }
}


fun main() = Day10().run()
