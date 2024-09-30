package com.fwdekker.euler


class Problem6Test : ProblemTest<Long>(
    ::Problem6,
    String::toLong,
    listOf(case())
)
