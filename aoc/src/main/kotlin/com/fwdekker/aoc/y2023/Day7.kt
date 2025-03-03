package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.mapSeconds
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.sumOfIndexed
import kotlin.math.min


// See https://adventofcode.com/2023/day/7
class Day7(sample: Int? = null) : Day(year = 2023, day = 7, sample = sample) {
    private val hands = input.linesNotBlank().map { it.split(' ').asPair() }.mapSeconds { it.toInt() }


    override fun part1(): Int = winnings(jIsJoker = false)

    override fun part2(): Int = winnings(jIsJoker = true)

    private fun winnings(jIsJoker: Boolean): Int =
        hands
            .sortedWith { (hand1, _), (hand2, _) -> compareHands(hand1, hand2, jIsJoker) }
            .sumOfIndexed { index, (_, bid) -> (index + 1) * bid }


    private fun String.strengths(jIsJoker: Boolean): List<Int> =
        if (jIsJoker) map { "J23456789TQKA".indexOf(it) }
        else map { "23456789TJQKA".indexOf(it) }

    private fun String.groups(jIsJoker: Boolean): List<Int> =
        groupBy { it }
            .mapValues { (_, it) -> it.size }
            .toList()
            .sortedByDescending { (_, count) -> count }
            .let { if (jIsJoker) it.distributeJokers() else it }
            .map { (_, count) -> count }

    private fun compareLists(l1: List<Int>, l2: List<Int>): Int? =
        ((l1 + l1.size) zip (l2 + l2.size))
            .firstOrNull { it.first != it.second }
            ?.let { it.first.compareTo(it.second) }

    private fun compareHands(hand1: String, hand2: String, jIsJoker: Boolean): Int =
        compareLists(hand1.groups(jIsJoker), hand2.groups(jIsJoker))
            ?: compareLists(hand1.strengths(jIsJoker), hand2.strengths(jIsJoker))
            ?: 0

    private fun List<Pair<Char, Int>>.distributeJokers(): List<Pair<Char, Int>> =
        filter { (card, _) -> card != 'J' }
            .fold(Pair(toMap().getOrDefault('J', 0), emptyList<Pair<Char, Int>>())) { (jokers, pairs), (card, count) ->
                val change = min(5 - count, jokers)
                Pair(jokers - change, pairs + Pair(card, count + change))
            }
            .second
            .ifEmpty { listOf('J' to 5) }

}


fun main() = Day7().run()
