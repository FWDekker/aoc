package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.firsts
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.powerSet
import com.fwdekker.std.maths.sum
import com.fwdekker.std.read
import com.fwdekker.std.toBigIntegers


class Day7(resource: String = resource(2024, 7)) : Day() {
    private val equations =
        read(resource).linesNotBlank().map { it.split(':') }.map { it[0].toBigInteger() to it[1].toBigIntegers(' ') }


    override fun part1() =
        equations.filter { (total, operands) ->
            (0..(operands.size - 2)).toList().powerSet().any { additions ->
                operands.reduceIndexed { idx, acc, operand ->
                    if ((idx - 1) in additions) acc + operand
                    else acc * operand
                } == total
            }
        }.firsts().sum()

    override fun part2() =
        equations.filter { (total, operands) ->
            (0..(operands.size - 2)).toList().powerSet().any { notConcatenation ->
                notConcatenation.powerSet().any { multiplications ->
                    operands.reduceIndexed { idx, acc, operand ->
                        if ((idx - 1) in multiplications) acc * operand
                        else if ((idx - 1) in notConcatenation) acc + operand
                        else "$acc$operand".toBigInteger()
                    } == total
                }
            }
        }.firsts().sum()
}


fun main() = Day7().run()
