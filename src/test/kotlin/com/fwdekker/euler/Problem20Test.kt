package com.fwdekker.euler


class Problem20Test : ProblemTest<Int>(
    ::Problem20,
    String::toInt,
    listOf(
        case(sample = 1),
        case(),
    )
)
