package com.fwdekker.aoc

import com.fwdekker.std.Challenge


/**
 * Convenience class for invoking the code for any particular day and part with a given resource.
 *
 * @param year the year of advent of code this day belongs to
 * @param day the day of advent of code this object corresponds to
 * @param sample the sample number to solve for, or `null` to use the full problem
 */
abstract class Day(val year: Int? = null, val day: Int? = null, val sample: Int? = null) : Challenge(2) {
    override val resource: String get() = resource(year!!, day!!, sample)


    override fun runPart(number: Int): Any =
        when (number) {
            1 -> part1()
            2 -> part2()
            else -> error("Invalid part number $number.")
        }

    abstract fun part1(): Any

    abstract fun part2(): Any


    companion object {
        @Deprecated("Use non-static zero-parameter call instead.", replaceWith = ReplaceWith("resource()"))
        // TODO: Once no classes use this method anymore, make class fields `year` and `day` non-nullable
        fun resource(year: Int, day: Int, sample: Int? = null) =
            "/y$year/Day${day}"
                .let { if (sample != null) "${it}Sample${sample}" else it }
                .let { "${it}.txt" }
    }
}
