package com.fwdekker.aoc


/**
 * Convenience class for invoking the code for any particular day and part with a given resource.
 */
abstract class Day(val resource: String) {
    /**
     * Runs and prints both parts.
     */
    fun run() {
        println("Part one: ${part1()}")
        println("Part two: ${part2()}")
    }

    /**
     * Returns the output for the first part of this day.
     */
    abstract fun part1(): Any

    /**
     * Returns the output for the second part of this day.
     */
    abstract fun part2(): Any


    /**
     * Holds constants.
     */
    companion object {
        /**
         * Shorthand for returning the standard resource for the given [year] and [day].
         */
        fun resource(year: Int, day: Int, sample: Int = 0) =
            if (sample == 0) "/y$year/Day$day.txt"
            else "/y${year}/Day${day}Sample${sample}.txt"
    }
}
