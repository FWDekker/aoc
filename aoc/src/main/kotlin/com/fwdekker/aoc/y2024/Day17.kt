package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.maths.toIntExact
import com.fwdekker.std.sections
import com.fwdekker.std.toLongs


// See https://adventofcode.com/2024/day/17
class Day17(sample: Int? = null) : Day(year = 2024, day = 17, sample = sample) {
    private val sections = input.sections()
    private val registers = sections[0].map { it.drop("Register A: ".length).toLong() }.let { Registers.from(it) }
    private val program = sections[1].single().drop("Program: ".length).toLongs(',')


    override fun part1(): String = execute(registers.copy()).joinToString(",")

    override fun part2(): Long? = backtrace()


    private fun combo(registers: Registers, operand: Long): Long =
        when (operand) {
            0L, 1L, 2L, 3L -> operand
            4L -> registers.a
            5L -> registers.b
            6L -> registers.c
            else -> error("Invalid combo operator $operand.")
        }

    private fun execute(registers: Registers): Sequence<Long> =
        sequence {
            var programPtr = 0
            while (programPtr in program.indices) {
                val operator = program[programPtr]
                val operand = program[programPtr + 1]

                when (operator) {
                    0L -> registers.a = registers.a shr combo(registers, operand).toIntExact()
                    1L -> registers.b = registers.b xor operand
                    2L -> registers.b = combo(registers, operand) % 8L
                    3L -> if (registers.a != 0L) programPtr = operand.toIntExact() - 2
                    4L -> registers.b = registers.b xor registers.c
                    5L -> yield(combo(registers, operand) % 8L)
                    6L -> registers.b = registers.a shr combo(registers, operand).toIntExact()
                    7L -> registers.c = registers.a shr combo(registers, operand).toIntExact()
                    else -> error("Invalid opcode $operator.")
                }

                programPtr += 2
            }
        }

    // See also `Day17Notes.txt` (in `resources`)
    private fun backtrace(a: Long = 0, index: Int = program.size - 1): Long? =
        (0L..7L).firstNotNullOfOrNull { word ->
            val aNew = (a shl 3) or word
            val candidate = execute(registers.copy(a = aNew)).first()

            if (candidate != program[index]) null
            else if (index == 0) aNew
            else backtrace(aNew, index - 1)
        }


    private data class Registers(var a: Long, var b: Long, var c: Long) {
        companion object {
            fun from(registers: Iterable<Long>): Registers = registers.take(3).let { Registers(it[0], it[1], it[2]) }
        }
    }
}


fun main() = Day17().run()
