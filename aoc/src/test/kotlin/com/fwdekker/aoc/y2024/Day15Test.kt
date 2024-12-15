package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day15].
 */
object Day15Test : DayTest(
    ::Day15,
    listOf(
        Triple(Day.resource(2024, 15, sample = 1), Day::part1, 2028L),
        Triple(Day.resource(2024, 15, sample = 2), Day::part1, 10092L),
        Triple(Day.resource(2024, 15, sample = 2), Day::part2, 9021L),
        Triple(Day.resource(2024, 15), Day::part1, 1471826L),
        Triple(Day.resource(2024, 15), Day::part2, 1457703L),
    )
)
