package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest
import java.math.BigInteger


/**
 * Tests for [Day7].
 */
object Day7Test : DayTest(
    ::Day7,
    listOf(
        Triple(Day.resource(2024, 7, sample = 1), Day::part1, BigInteger("3749")),
        Triple(Day.resource(2024, 7, sample = 1), Day::part2, BigInteger("11387")),
        Triple(Day.resource(2024, 7), Day::part1, BigInteger("2501605301465")),
        Triple(Day.resource(2024, 7), Day::part2, BigInteger("44841372855953")),
    )
)
