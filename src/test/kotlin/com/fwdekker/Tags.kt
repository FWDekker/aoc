package com.fwdekker

import io.kotest.core.NamedTag


/**
 * Tags for Kotest-based tags for filtering tests.
 */
object Tags {
    /**
     * Tests related to the shared library.
     */
    val STD = NamedTag("STD")

    /**
     * Tests related to Advent of Code.
     */
    val AOC = NamedTag("AoC")

    /**
     * Tests related to Euler Project.
     */
    val EULER = NamedTag("Euler")
}
