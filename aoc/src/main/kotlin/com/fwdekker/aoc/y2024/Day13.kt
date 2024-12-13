package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.map
import com.fwdekker.std.maths.plus
import com.fwdekker.std.maths.sum
import com.fwdekker.std.maths.toBigDecimals
import com.fwdekker.std.read
import com.fwdekker.std.sections
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.roundToLong


// See https://adventofcode.com/2024/day/13
class Day13(resource: String = resource(2024, 13)) : Day() {
    private val machines = read(resource).sections()
        .map { section ->
            val partsA = section[0].split('+')
            val buttonA = partsA.drop(1).map { part -> part.takeWhile { it.isDigit() }.toLong() }.asPair()
            val partsB = section[1].split('+')
            val buttonB = partsB.drop(1).map { part -> part.takeWhile { it.isDigit() }.toLong() }.asPair()
            val partsPrize = section[2].split('=')
            val prize = partsPrize.drop(1).map { part -> part.takeWhile { it.isDigit() }.toLong() }.asPair()
            Triple(buttonA, buttonB, prize)
        }


    private fun part1sub() =
        machines.map { machine ->
            val w =
                ((machine.third.second - (machine.first.second.toDouble() / machine.first.first) * machine.third.first) / (machine.second.second - (machine.first.second.toDouble() / machine.first.first) * machine.second.first)).roundToLong()
            val v = (machine.third.first - w * machine.second.first) / machine.first.first

            if (v > 100 || w > 100) 0L
            else if (machine.first.map { it * v } + machine.second.map { it * w } == machine.third) 3L * v + w
            else 0L
        }


    override fun part1() = part1sub().sum()

    override fun part2() =
        machines
            .map { machine ->
                Triple(
                    machine.first.toBigDecimals(),
                    machine.second.toBigDecimals(),
//                    machine.third.toBigDecimals()
                    machine.third.toBigDecimals().map { BigDecimal("10000000000000") + it }
                )
            }
            .zip(part1sub())
            .map { (machine, predicted) ->
                val mc = MathContext(512, RoundingMode.HALF_EVEN)
//                val mc = MathContext.DECIMAL128
                val w =
                    (
                        (machine.third.second - (machine.first.second.divide(
                            machine.first.first,
                            mc
                        )) * machine.third.first).divide(
                            (machine.second.second - (machine.first.second.divide(
                                machine.first.first,
                                mc
                            )) * machine.second.first),
                            mc
                        )).round(mc).toBigInteger()
                val v = ((machine.third.first - w.toBigDecimal() * machine.second.first).divide(
                    machine.first.first,
                    mc
                )).round(mc).toBigInteger()

                val out =
                    //if (v > BigInteger.valueOf(100L) || w > BigInteger.valueOf(100L)) BigInteger.ZERO
                    //else
                        if (machine.first.map { it * v.toBigDecimal() } + machine.second.map { it * w.toBigDecimal() } == machine.third) BigInteger.valueOf(3L) * v + w
                    else BigInteger.ZERO

                out
            }
            .sum()

    // 1545093008511 is too low
    // 163458403948627 is too high
    // 103570327981381
}


fun main() = Day13().run()
