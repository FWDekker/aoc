package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.maths.naturalNumbersLong
import com.fwdekker.std.maths.pow
import com.fwdekker.std.maths.toIntExact
import com.fwdekker.std.sections
import com.fwdekker.std.toInts


// See https://adventofcode.com/2024/day/17
class Day17(sample: Int? = null) : Day(year = 2024, day = 17, sample = sample) {
    private val sections = input.sections()
    private val registers = sections[0].map { it.drop("Register A: ".length).toLong() }
    private val program = sections[1].single().drop("Program: ".length).toInts(',')


    override fun part1(): String = run(registers, program).joinToString(",")

    override fun part2(): Long =
        // TODO: Implement
        naturalNumbersLong()
            .first {
                val out = run(listOf(it, registers[1], registers[2]), program).iterator()

                for (idx in program.indices)
                    if (!out.hasNext() || out.next() != program[idx]) return@first false

                return@first !out.hasNext()
            }


    private fun run(registers: List<Long>, program: List<Int>): Sequence<Int> =
        sequence {
            val registers = registers.toMutableList()
            var programPtr = 0

            while (programPtr in program.indices) {
                val operator = program[programPtr]
                val operand = program[programPtr + 1]

                when (operator) {
                    0 -> registers[0] = registers[0] / 2.pow(combo(registers, operand).toIntExact()).toLong()
                    1 -> registers[1] = registers[1].xor(operand.toLong())
                    2 -> registers[1] = combo(registers, operand) % 8.toLong()

                    3 ->
                        if (registers[0] != 0L) {
                            programPtr = operand
                            continue
                        }

                    4 -> registers[1] = registers[1].xor(registers[2])
                    5 -> yield((combo(registers, operand) % 8L).toIntExact())
                    6 -> registers[1] = registers[0] / 2.pow(combo(registers, operand).toIntExact()).toLong()
                    7 -> registers[2] = registers[0] / 2.pow(combo(registers, operand).toIntExact()).toLong()
                    else -> error("Invalid opcode $operator.")
                }

                programPtr += 2
            }

            return@sequence
        }


    private fun combo(registers: MutableList<Long>, operand: Int): Long =
        when (operand) {
            0, 1, 2, 3 -> operand.toLong()
            4, 5, 6 -> registers[operand - 4]
            else -> error("Invalid combo operator $operand.")
        }
}


fun main() = Day17().run()
