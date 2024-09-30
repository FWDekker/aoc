package com.fwdekker.euler


class Problem18Test : ProblemTest<Int>(
    ::Problem18,
    String::toInt,
    listOf(case())
)
