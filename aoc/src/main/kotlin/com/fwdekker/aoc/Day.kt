package com.fwdekker.aoc

import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.Direction
import com.fwdekker.std.grid.Ordinal
import kotlin.time.measureTimedValue


/**
 * Convenience class for invoking the code for any particular day and part with a given resource.
 */
abstract class Day {
    /**
     * Runs and prints both parts.
     */
    fun run() {
        measureTimedValue { part1() }
            .also { println("Part one: ${it.value} (in ${it.duration.inWholeMilliseconds} ms)") }
        measureTimedValue { part2() }
            .also { println("Part two: ${it.value} (in ${it.duration.inWholeMilliseconds} ms)") }
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
        @Suppress("unused")
        private val ignoreMe = Triple(Cardinal, Ordinal, Direction)  // TODO: Remove workaround for KT-59723


        /**
         * Shorthand for returning the resource for the given [year] and [day].
         *
         * Setting [sample] to `null` returns the final difficult input, whereas all other values return easier sample
         * inputs.
         */
        fun resource(year: Int, day: Int, sample: Int? = null) =
            "/y$year/Day${day}"
                .let { if (sample != null) "${it}Sample${sample}" else it }
                .let { "${it}.txt" }
    }
}
