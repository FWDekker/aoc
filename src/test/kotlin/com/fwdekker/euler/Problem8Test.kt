package com.fwdekker.euler


class Problem8Test : ProblemTest<Long>(
    ::Problem8,
    String::toLong,
    listOf(
        case(sample = 1),
        case(),
    )
)
