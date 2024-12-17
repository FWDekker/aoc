package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.allCoords
import com.fwdekker.std.grid.cardinals
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.move
import com.fwdekker.std.grid.rows
import com.fwdekker.std.grid.toChart


// See https://adventofcode.com/2024/day/12
// TODO: Clean up code
class Day12(sample: Int? = null) : Day(year = 2024, day = 12, sample = sample) {
    private val garden = input.toChart()


    override fun part1() =
        garden.allCoords
            .map { spot ->
                val seen = mutableSetOf<Coords>()
                val queue = ArrayDeque(listOf(spot))
                while (queue.isNotEmpty()) {
                    val next = queue.removeFirst()
                    if (next in seen) continue

                    seen += next
                    queue += next.cardinals.filter { it in garden && garden[it] == garden[next] }
                }
                seen
            }
            .distinct()
            .sumOf { region ->
                region.size * region.sumOf { spot ->
                    spot.cardinals.count { it !in garden || garden[it] != garden[spot] }
                }
            }

    override fun part2() =
        garden.allCoords
            .map { spot ->
                val seen = mutableSetOf<Coords>()
                val queue = ArrayDeque(listOf(spot))
                while (queue.isNotEmpty()) {
                    val next = queue.removeFirst()
                    if (next in seen) continue

                    seen += next
                    queue += next.cardinals.filter { it in garden && garden[it] == garden[next] }
                }
                seen
            }
            .distinct()
            .sumOf { region ->
                region.size * region.flatMap { spot ->
                    Cardinal.entries.filter { spot.move(it) !in region }.map { direction ->
                        val outside = spot.move(direction);
                        setOf(outside) +
                            garden.rows.asSequence().map { outside.move(direction.left, it) }
                                .takeWhile { it !in region && it.move(direction.behind) in region } +
                            garden.rows.asSequence().map { outside.move(direction.right, it) }
                                .takeWhile { it !in region && it.move(direction.behind) in region } to direction
                    }
                }.distinct().size
            }
}


fun main() = Day12().run()
