package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.asPair
import com.fwdekker.std.mapFirst
import com.fwdekker.std.mapSecond
import com.fwdekker.std.product
import com.fwdekker.std.readLines


class Day2(resource: String = resource(2023, 2)) : Day(resource) {
    private val lines = readLines(resource)
    private val limits = mapOf("red" to 12, "green" to 13, "blue" to 14)
    private val colors = limits.keys


    override fun part1(): Int = lines
        .associate { line -> line.split(": ").asPair().mapFirst { it.drop(5).toInt() }.mapSecond { it.parseDraws() } }
        .filterValues { draws -> draws.all { draw -> limits.all { (color, amount) -> (draw[color] ?: 0) <= amount } } }
        .keys.sum()

    override fun part2(): Long =
        lines
            .map { it.substringAfter(": ").parseDraws() }
            .sumOf { draws -> colors.map { color -> draws.maxOf { it[color] ?: 0 } }.product() }


    private fun String.parseDraws(): List<Map<String, Int>> =
        split("; ").map { draw -> draw.split(", ").associate { set -> set.split(' ').let { it[1] to it[0].toInt() } } }
}


fun main() = Day2().run()
