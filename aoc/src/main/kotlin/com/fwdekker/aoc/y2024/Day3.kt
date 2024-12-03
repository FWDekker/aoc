package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.read
import com.fwdekker.std.toInts


class Day3(resource: String = resource(2024, 3)) : Day() {
    private val instructions = read(resource)


    override fun part1(): Int =
        Regex("""mul\(\d+,\d+\)""").findAll(instructions).sumOf { it.value.mult() }

    override fun part2(): Int =
        Regex("""(mul\(\d+,\d+\)|do\(\)|don't\(\))""").findAll(instructions)
            .fold(Pair(0, true)) { (sum, enabled), instr ->
                when (instr.value) {
                    "don't()" -> sum to false
                    "do()" -> sum to true
                    else -> (if (enabled) sum + instr.value.mult() else sum) to enabled
                }
            }
            .first


    private fun String.mult(): Int = removeSurrounding("mul(", ")").toInts(',').let { it[0] * it[1] }
}


fun main() = Day3().run()
