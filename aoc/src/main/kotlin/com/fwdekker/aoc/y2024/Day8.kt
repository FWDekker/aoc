package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.allCoords
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.maths.combinations
import com.fwdekker.std.maths.gcd
import com.fwdekker.std.maths.minus
import com.fwdekker.std.maths.plus
import com.fwdekker.std.read


class Day8(resource: String = resource(2024, 8)) : Day() {
    private val chart = read(resource).toChart()


    override fun part1() =
        chart.allCoords
            .filter { chart[it] != '.' }
            .associateWith { chart[it] }
            .toList().combinations(2)
            .filter { (a, b) -> a.first != b.first && a.second == b.second }
            .flatMap { (a, b) ->
                a.first.minus(b.first).let { listOf(a.first.plus(it), b.first.minus(it)) }.filter { it in chart }
            }
            .toSet().size

    override fun part2() =
        chart.allCoords
            .filter { chart[it] != '.' }
            .associateWith { chart[it] }
            .toList().combinations(2)
            .filter { (a, b) -> a.first != b.first && a.second == b.second }
            .flatMap { (a, b) ->
                val diff = a.first.minus(b.first)
                val diffg = gcd(diff.first, diff.second)
                val diff2 = Pair(diff.first / diffg, diff.second / diffg)

                (0..100).asSequence()
                    .map { a.first.plus(Pair(diff2.first * it, diff2.second * it)) }
                    .takeWhile { it in chart } +
                (0..100).asSequence()
                    .map { b.first.minus(Pair(diff2.first * it, diff2.second * it)) }
                    .takeWhile { it in chart }
            }
            .toSet().size
}


fun main() = Day8().run()
