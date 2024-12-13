package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.asTriple
import com.fwdekker.std.collections.firsts
import com.fwdekker.std.collections.map
import com.fwdekker.std.collections.mapThirds
import com.fwdekker.std.collections.seconds
import com.fwdekker.std.maths.plus
import com.fwdekker.std.read
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode


// See https://adventofcode.com/2024/day/13
class Day13(resource: String = resource(2024, 13)) : Day() {
    private val mc = MathContext.DECIMAL128
    private val machines = read(resource)
        .let { file -> Regex("\\d+").findAll(file).map { it.value.toBigInteger() } }
        .chunked(6)
        .map { machine -> machine.chunked(2).map { it.asPair() }.asTriple() }


    override fun part1() = machines.countTokens(maxPresses = BigInteger.valueOf(100))

    override fun part2() = machines.mapThirds { prize -> prize.map { it + BigInteger("10000000000000") } }.countTokens()


    private fun Sequence<Triple<BigCoords, BigCoords, BigCoords>>.countTokens(maxPresses: BigInteger? = null) =
        sumOf { machine ->
            val (eq1, eq2) = machine.toList().let { it.firsts() to it.seconds() }

            val rowFactor = eq2[0].toBigDecimal().divide(eq1[0].toBigDecimal(), mc)
            val eq2Reduced = eq2.indices
                .map { idx -> eq2[idx].toBigDecimal() - eq1[idx].toBigDecimal() * rowFactor }
                .let { row -> row.map { it.divide(row[1], mc) } }

            val pressesB = eq2Reduced[2].setScale(0, RoundingMode.HALF_UP).toBigInteger()
            val pressesA = (eq1[2] - eq1[1] * pressesB).divide(eq1[0])

            val solution = machine.first.map { it * pressesA } + machine.second.map { it * pressesB }

            when {
                maxPresses != null && (pressesA > maxPresses || pressesB > maxPresses) -> BigInteger.ZERO
                solution == machine.third -> BigInteger.valueOf(3L) * pressesA + pressesB
                else -> BigInteger.ZERO
            }
        }
}

private typealias BigCoords = Pair<BigInteger, BigInteger>


fun main() = Day13().run()
