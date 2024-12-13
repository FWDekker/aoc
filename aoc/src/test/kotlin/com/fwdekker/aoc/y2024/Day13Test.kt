package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest
import io.kotest.core.annotation.Ignored
import java.math.BigInteger


/**
 * Tests for [Day13].
 */
object Day13Test : DayTest(
    ::Day13,
    listOf(
        Triple(Day.resource(2024, 13, sample = 1), Day::part1, BigInteger("480")),
        Triple(Day.resource(2024, 13), Day::part1, BigInteger("29517")),
        Triple(Day.resource(2024, 13), Day::part2, BigInteger("103570327981381")),
    )
)
