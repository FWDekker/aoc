package com.fwdekker.euler


class Problem7Test : ProblemTest<Long>(
    ::Problem7,
    String::toLong,
    listOf(
        case(sample = 1),
        case(),
    )
)
