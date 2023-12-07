package com.fwdekker.aoc.y2023

import kotlin.math.min


fun main() {
    val lines = readResource("/y2023/Day7.txt").lines().filter(String::isNotBlank)

    val hands = lines.map { it.split(' ') }.map { Pair(it[0], it[1].toInt()) }

    // Part 1
    hands
        .sortedWith { (hand1, _), (hand2, _) -> compareHands(hand1, hand2, jIsJoker = false) }
        .mapIndexed { index, (_, bid) -> (index + 1) * bid }
        .also { println("Part one: ${it.sum()}") }

    // Part 2
    hands
        .sortedWith { (hand1, _), (hand2, _) -> compareHands(hand1, hand2, jIsJoker = true) }
        .mapIndexed { index, (_, bid) -> (index + 1) * bid }
        .also { println("Part two: ${it.sum()}") }
}


private fun String.strengths(jIsJoker: Boolean): List<Int> =
    this.map { (if (jIsJoker) "J23456789TQKA" else "23456789TJQKA").indexOf(it) }

private fun String.groups(jIsJoker: Boolean): List<Int> =
    this.groupBy { it }
        .map { (card, occurrences) -> Pair(card, occurrences.size) }
        .sortedByDescending { (_, count) -> count }
        .let { if (jIsJoker) it.distributeJokers() else it }
        .map { (_, count) -> count }

private fun compareInts(i1: Int, i2: Int): Int? =
    if (i1 < i2) -1 else if (i1 == i2) null else 1

private fun compareLists(l1: List<Int>, l2: List<Int>): Int? =
    l1.zip(l2).firstNotNullOfOrNull { (e1, e2) -> compareInts(e1, e2) }
        ?: compareInts(l1.size, l2.size)

private fun compareHands(hand1: String, hand2: String, jIsJoker: Boolean): Int =
    compareLists(hand1.groups(jIsJoker), hand2.groups(jIsJoker))
        ?: compareLists(hand1.strengths(jIsJoker), hand2.strengths(jIsJoker))
        ?: 0

private fun List<Pair<Char, Int>>.distributeJokers(): List<Pair<Char, Int>> =
    this.filter { (card, _) -> card != 'J' }
        .fold(Pair(this.toMap().getOrDefault('J', 0), emptyList<Pair<Char, Int>>())) { (jokers, pairs), (card, count) ->
            val change = min(5 - count, jokers)
            Pair(jokers - change, pairs + Pair(card, count + change))
        }
        .second
        .ifEmpty { listOf('J' to 5) }
