package com.fwdekker.euler


class Problem3Test : ProblemTest<Long>(
    ::Problem3,
    String::toLong,
    listOf(
        case(sample = 1),
        case(),
    )
)
