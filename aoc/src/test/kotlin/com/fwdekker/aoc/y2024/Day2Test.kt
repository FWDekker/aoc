package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest
import io.kotest.core.annotation.Tags


/**
 * Tests for [Day2].
 */
@Tags("XX")
object Day2Test : DayTest(
    ::Day2,
    listOf(
        Triple(Day.resource(2024, 2, sample = 1), Day::part1, 2),
        Triple(Day.resource(2024, 2, sample = 1), Day::part2, 4),
        Triple(Day.resource(2024, 2), Day::part1, 334),
        Triple(Day.resource(2024, 2), Day::part2, 400),
    )
)
