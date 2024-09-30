package com.fwdekker.euler


class Problem15Test : ProblemTest<Long>(
    ::Problem15,
    String::toLong,
    listOf(case())
)
