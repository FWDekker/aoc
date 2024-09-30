package com.fwdekker.euler


class Problem21Test : ProblemTest<Int>(
    ::Problem21,
    String::toInt,
    listOf(case())
)
