package com.fwdekker.euler


class Problem12Test : ProblemTest<Long>(
    ::Problem12,
    String::toLong,
    listOf(
        case(sample = 1),
        case(),
    )
)
