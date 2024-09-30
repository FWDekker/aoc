package com.fwdekker.euler


class Problem4Test : ProblemTest<Long>(
    ::Problem4,
    String::toLong,
    listOf(
        case(sample = 1),
        case(),
    )
)
