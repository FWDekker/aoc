package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest
import io.kotest.core.annotation.Ignored
import java.math.BigInteger


/**
 * Tests for [Day14].
 */
object Day14Test : DayTest(
    ::Day14,
    listOf(
        Triple(Day.resource(2024, 14), Day::part1, 217328832),
        Triple(Day.resource(2024, 14), Day::part2, 7412),
    )
)
