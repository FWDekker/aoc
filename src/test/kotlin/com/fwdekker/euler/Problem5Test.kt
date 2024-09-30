package com.fwdekker.euler


class Problem5Test : ProblemTest<Long>(
    ::Problem5,
    String::toLong,
    listOf(
        case(sample = 1),
        case(),
    )
)
