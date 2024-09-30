package com.fwdekker.euler


class Problem1Test : ProblemTest<Int>(
    { Problem1(it) },
    String::toInt,
    listOf(
        case(sample = 1),
        case(),
    )
)
