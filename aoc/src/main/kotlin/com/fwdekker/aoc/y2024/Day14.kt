package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.allInts
import com.fwdekker.std.collections.firsts
import com.fwdekker.std.collections.map
import com.fwdekker.std.collections.seconds
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.plus
import com.fwdekker.std.maths.product
import com.fwdekker.std.maths.wrapMod
import com.fwdekker.std.runningFoldSelf
import kotlin.math.pow


// See https://adventofcode.com/2024/day/14
class Day14(
    sample: Int? = null,
    private val width: Int = 101,
    private val height: Int = 103,
) : Day(year = 2024, day = 14, sample = sample) {
    private val robots = input.linesNotBlank().allInts().map { Pair(it[0], it[1]) to Pair(it[2], it[3]) }


    override fun part1(): Long {
        val width2 = (width - 1) / 2
        val height2 = (height - 1) / 2

        return robots.asSequence()
            .map { (begin, step) -> begin + step.map { 100 * it } }
            .map { (x, y) -> x.wrapMod(width) to y.wrapMod(height) }
            .filter { (x, y) -> x != width2 && y != height2 }
            .groupBy { (x, y) -> (if (x < width2) 0 else 1) + (if (y < height2) 0 else 2) }
            .map { (_, bots) -> bots.count() }
            .product()
    }

    override fun part2(): Int =
        robots.firsts()
            .runningFoldSelf(25_000) { positions ->
                positions
                    .mapIndexed { idx, pos -> pos + robots[idx].second }
                    .map { (x, y) -> x.wrapMod(width) to y.wrapMod(height) }
            }
            .map { it.firsts().variance() + it.seconds().variance() }
            .withIndex()
            .minBy { it.value }
            .index


    @JvmName("intVariance")
    private fun List<Int>.variance(): Double {
        val self = map { it.toDouble() }
        val mean = self.sum() / self.size
        return self.sumOf { (it - mean).pow(2) } / self.size
    }

}


fun main() = Day14().run()
