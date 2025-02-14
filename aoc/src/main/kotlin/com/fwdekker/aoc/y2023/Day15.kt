package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.maths.sumOfIndexed


// See https://adventofcode.com/2023/day/15
class Day15(sample: Int? = null) : Day(year = 2023, day = 15, sample = sample) {
    private val instructions = input.split(',')


    override fun part1(): Int =
        instructions.sumOf { Label(it).hashCode() }

    override fun part2(): Int =
        instructions.fold(emptyMap<Label, Map<String, Int>>()) { boxes, it -> boxes.perform(it) }.power()


    private class Label(private val label: String) {
        override fun hashCode(): Int = label.fold(0) { hash, char -> ((hash + char.code) * 17) % 256 }

        override fun equals(other: Any?) = other is Label && this.hashCode() == other.hashCode()
    }

    private fun Map<Label, Map<String, Int>>.perform(instruction: String): Map<Label, Map<String, Int>> {
        val (text, focal) = instruction.split('-', '=')

        val label = Label(text)
        val box = getOrDefault(label, emptyMap())

        return this + Pair(label, if ('-' in instruction) box - text else box + Pair(text, focal.toInt()))
    }

    private fun Map<Label, Map<String, Int>>.power(): Int =
        entries.sumOf { (label, box) ->
            (label.hashCode() + 1) * box.values.sumOfIndexed { slot, focal -> (slot + 1) * focal }
        }
}


fun main() = Day15().run()
