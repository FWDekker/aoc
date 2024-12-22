package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day22Test : ChallengeTest(
    ::Day22,
    listOf(
        case(part = 1, sample = 1) to 37327623L,
        case(part = 2, sample = 2) to 23L,
        case(part = 1) to 19150344884L,
        case(part = 2) to 2121L,
    )
)
