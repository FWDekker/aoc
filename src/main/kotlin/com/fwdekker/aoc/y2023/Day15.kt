package com.fwdekker.aoc.y2023


fun main() {
    val instructions = readResource("/y2023/Day15.txt").trim().split(',')

    // Part 1
    println("Part one: ${instructions.sumOf { Label(it).hashCode() }}")

    // Part 2
    val boxes = mutableMapOf<Label, MutableMap<String, Int>>()  // Kotlin maps preserve insertion order
    instructions.forEach { boxes.perform(it) }
    println("Part two: ${boxes.power()}")
}


private class Label(private val label: String) {
    override fun hashCode(): Int = label.fold(0) { hash, char -> ((hash + char.code) * 17) % 256 }

    override fun equals(other: Any?) = other is Label && this.hashCode() == other.hashCode()
}

private fun MutableMap<Label, MutableMap<String, Int>>.perform(instruction: String) {
    val (label, focal) = instruction.split('-', '=')

    getOrPut(Label(label)) { mutableMapOf() }
        .also { if ('-' in instruction) it.remove(label) else it[label] = focal.toInt() }
}

private fun Map<Label, MutableMap<String, Int>>.power(): Int =
    entries.sumOf { (label, box) ->
        (label.hashCode() + 1) * box.values.mapIndexed { slot, focal -> (slot + 1) * focal }.sum()
    }
