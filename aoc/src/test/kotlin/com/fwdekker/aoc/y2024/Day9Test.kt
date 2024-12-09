package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest
import io.kotest.core.annotation.Tags


/**
 * Tests for [Day9].
 */
@Tags("XX")
object Day9Test : DayTest(
    ::Day9,
    listOf(
        Triple(Day.resource(2024, 9, sample = 1), Day::part1, 1928L),
        Triple(Day.resource(2024, 9, sample = 2), Day::part1, 60L),
        Triple(Day.resource(2024, 9, sample = 1), Day::part2, 2858L),
        Triple(Day.resource(2024, 9), Day::part1, 6461289671426L),
        Triple(Day.resource(2024, 9), Day::part2, 6488291456470L),
    )
)
