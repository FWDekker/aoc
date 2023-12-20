package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.foldSelf
import com.fwdekker.aoc.std.readLines
import com.fwdekker.aoc.std.rotateCCW
import com.fwdekker.aoc.std.rotateCW
import com.fwdekker.aoc.std.sortedDescending


fun main() {
    val lines = readLines("/y2023/Day14.txt")

    // Part 1
    println("Part one: ${lines.rotateCCW().tiltWest().rotateCW().calcLoad()}")

    // Part 2
    println("Part two: ${lines.rotateCCW().cycle(1_000_000_000).rotateCW().calcLoad()}")
}


private fun List<String>.tiltWest(): List<String> =
    map { line -> line.split('#').joinToString("#") { it.sortedDescending() } }

private fun List<String>.cycle(repetitions: Int = 1): List<String> {
    val history = mutableMapOf(this to 0)

    var platform = this
    repeat(repetitions) { repetition ->
        platform = platform.foldSelf(repeats = 4) { it.tiltWest().rotateCW() }

        if (platform in history)
            return platform.cycle((repetitions - repetition) % (repetition - history.getValue(platform)))

        history[platform] = repetition
    }

    return platform
}

private fun List<String>.calcLoad(): Int = mapIndexed { idx, line -> (size - idx) * line.count { it == 'O' } }.sum()
