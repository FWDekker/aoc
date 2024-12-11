package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.fold
import com.fwdekker.std.collections.map
import com.fwdekker.std.foldSelf
import com.fwdekker.std.read
import com.fwdekker.std.sections
import com.fwdekker.std.splitAtIndex
import com.fwdekker.std.toLongs


class Day11(resource: String = resource(2024, 11)) : Day() {
    private val input = read(resource).sections()
    private val blinks = input[0][0].toInt()
    private val stones = input[1][0].toLongs(' ')


    override fun part1(): Long = blink(blinks, stones).values.sum()

    override fun part2(): Long = blink(blinks * 3, stones).values.sum()


    private fun blink(times: Int, stones: List<Long>): Map<Long, Long> =
        stones.associateWith { 1L }.foldSelf(times) { currentStones ->
            currentStones.fold(mutableMapOf<Long, Long>().withDefault { 0L }) { acc, (stone, count) ->
                val stoneStr = stone.toString()

                when {
                    stone == 0L -> listOf(1L)
                    stoneStr.length % 2 == 0 -> stoneStr.splitAtIndex(stoneStr.length / 2).map { it.toLong() }.toList()
                    else -> listOf(stone * 2024L)
                }.forEach { acc[it] = acc.getValue(it) + count }

                acc
            }
        }
}


fun main() = Day11().run()
