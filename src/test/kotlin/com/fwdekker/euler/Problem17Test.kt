package com.fwdekker.euler


class Problem17Test : ProblemTest<Int>(
    ::Problem17,
    String::toInt,
    listOf(
        case(sample = 1),
        case(),
    )
)
