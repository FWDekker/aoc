package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest
import io.kotest.core.annotation.Ignored


/**
 * Tests for [Day999].
 */
@Ignored
object XDay999Test : DayTest(
    ::Day999,
    listOf(
        Triple(Day.resource(2024, 999, sample = 1), Day::part1, 0L),
        Triple(Day.resource(2024, 999, sample = 1), Day::part2, 0L),
        Triple(Day.resource(2024, 999), Day::part1, 0L),
        Triple(Day.resource(2024, 999), Day::part2, 0L),
    )
)
