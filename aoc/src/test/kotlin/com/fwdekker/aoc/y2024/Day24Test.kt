package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import com.fwdekker.std.case
import io.kotest.core.annotation.Ignored
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day24Test : ChallengeTest(
    { sample ->
        if (sample == 3) Day24(sample, swapCount = 2)
        else Day24(sample)
    },
    listOf(
        case(part = 1, sample = 1) to 4L,
        case(part = 1, sample = 2) to 2024L,
        case(part = 2, sample = 3) to "z00,z01,z02,z05",
        case(part = 1) to 53258032898766L,
//        case(part = 2) to TODO(),
    )
)
