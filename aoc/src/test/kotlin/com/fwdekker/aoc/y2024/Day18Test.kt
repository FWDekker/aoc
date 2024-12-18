package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import com.fwdekker.std.case
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day18Test : ChallengeTest(
    { sample ->
        if (sample == 1) Day18(sample, gridSize = 6, bytesMax = 12)
        else Day18(sample)
    },
    listOf(
        case(part = 1, sample = 1) to 22,
        case(part = 2, sample = 1) to "6,1",
        case(part = 1) to 314,
        case(part = 2) to "15,20",
    )
)
