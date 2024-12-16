package com.fwdekker.std

import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.Direction
import com.fwdekker.std.grid.Ordinal
import kotlin.time.TimeSource
import kotlin.time.measureTimedValue


/**
 * Convenience class for invoking the code for any particular challenge with a file resource.
 */
abstract class Challenge(private val partCount: Int) {
    /**
     * A reliable time source.
     */
    private val time = TimeSource.Monotonic

    /**
     * The moment in time at which the superclass' fields were first instantiated, marking a point in time before the
     * subclass' fields are instantiated.
     */
    private val mark = TimeSource.Monotonic.markNow()

    /**
     * The path to the resource from which the input is read, which is made available as [input].
     */
    protected abstract val resource: String

    /**
     * The input to the challenge.
     *
     * Lazily evaluated, in case there is no input.
     */
    val input: String by lazy { read(resource) }


    /**
     * Runs each part, and prints its output and the elapsed time.
     */
    fun run() {
        println("Instantiation: Complete. (${(time.markNow() - mark).inWholeMilliseconds} ms)")

        (0..<partCount).forEach { partNumber ->
            val partName = if (partCount == 1) "Solution" else "Part $partNumber"

            measureTimedValue { runPart(partNumber) }
                .also { println("$partName: ${it.value} (in ${it.duration.inWholeMilliseconds} ms)") }
        }
    }

    /**
     * Runs the part with given [number].
     */
    abstract fun runPart(number: Int): Any


    /**
     * Holds constants.
     */
    companion object {
        /**
         * Ignore this field.
         *
         * TODO: Remove workaround for KT-59723
         */
        @Suppress("unused")
        private val ignoreMe = Triple(Cardinal, Ordinal, Direction)
    }
}
