package com.fwdekker.aoc.y2023


fun main() {
    val lines = readResource("/y2023/Day14.txt").lines().filter(String::isNotBlank)

    // Part 1
    println("Part one: ${lines.rotateCCW().tiltWest().rotateCW().calcLoad()}")

    // Part 2
    println("Part two: ${lines.rotateCCW().cycle(1_000_000_000).rotateCW().calcLoad()}")
}


private fun String.sorted(): String = toCharArray().sortedArray().concatToString()

private fun List<String>.transpose(): List<String> =
    this[0].indices.map { col -> joinToString(separator = "") { "${it[col]}" } }

private fun List<String>.rotateCW(): List<String> = transpose().map { it.reversed() }

private fun List<String>.rotateCCW(): List<String> = transpose().reversed()


private fun List<String>.tiltWest(): List<String> =
    map { line -> line.split('#').joinToString("#") { it.sorted().reversed() } }

private fun List<String>.cycle(repetitions: Int = 1): List<String> {
    val history = mutableMapOf(this to 0)

    var platform = this
    for (repetition in 1..repetitions) {
        platform = (1..4).fold(platform) { it, _ -> it.tiltWest().rotateCW() }

        if (platform in history)
            return platform.cycle((repetitions - repetition) % (repetition - history.getValue(platform)))

        history[platform] = repetition
    }

    return platform
}

private fun List<String>.calcLoad(): Int =
    mapIndexed { idx, line -> (size - idx) * line.count { it == 'O' } }.sum()
