package com.fwdekker.euler


class Problem14Test : ProblemTest<Int>(
    ::Problem14,
    String::toInt,
    listOf(case())
)
