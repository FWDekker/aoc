package com.fwdekker.euler


class Problem16Test : ProblemTest<Long>(
    ::Problem16,
    String::toLong,
    listOf(
        case(sample = 1),
        case(),
    )
)
