package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.map
import com.fwdekker.std.collections.mapFirst
import com.fwdekker.std.collections.mapSecond
import com.fwdekker.std.collections.repeat
import com.fwdekker.std.read
import com.fwdekker.std.toInts


class Day9(resource: String = resource(2024, 9)) : Day() {
    private val disk = read(resource).toInts("")


    override fun part1(): Long {
        val (files, frees) = disk.withIndex().partition { (idx, _) -> idx % 2 == 0 }
            .map { list -> list.map { it.value } }
            .mapFirst { files -> files.mapIndexed { idx, it -> listOf(idx).repeat(it) }.toMutableList() }
            .mapSecond { free -> free.map { listOf(-1).repeat(it) }.toMutableList() }

        frees.indices.forEach { freeIdx ->
            while (true) {
                if (freeIdx >= files.lastIndex)
                    break

                val free = frees[freeIdx]
                val freeUsed = free.takeWhile { it != -1 }
                val freeAvailable = free.size - freeUsed.size
                val file = files.last()

                if (file.size == freeAvailable) {
                    files.removeLast()
                    frees[freeIdx] = freeUsed + file
                    break
                } else if (file.size < freeAvailable) {
                    files.removeLast()
                    frees[freeIdx] = freeUsed + file + free.drop(freeUsed.size + file.size)
                } else {
                    files[files.lastIndex] = file.drop(freeAvailable)
                    frees[freeIdx] = freeUsed + file.take(freeAvailable)
                    break
                }
            }
        }

        val all = files.zip(frees.take(files.size)).flatMap { it.toList() }.flatten().dropLastWhile { it == -1 }
        return all.withIndex().sumOf { (idx, value) -> idx * value.toLong() }
        // sample2 should give 60
    }

    override fun part2(): Long {
        val (files, frees) = disk.withIndex().partition { (idx, _) -> idx % 2 == 0 }
            .map { list -> list.map { it.value } }
            .mapFirst { files -> files.mapIndexed { idx, it -> listOf(idx).repeat(it) }.toMutableList() }
            .mapSecond { free -> free.map { listOf(-1).repeat(it) }.toMutableList() }

        files.indices.reversed().forEach { fileIdx ->
            val file = files[fileIdx]
            val freeIdx = frees.indexOfFirst { free -> free.size >= file.size && -1 in free && free.count { it == -1 } >= file.size }
            if (freeIdx < 0) return@forEach
            if (freeIdx >= fileIdx) return@forEach

            files[fileIdx] = file.map { -1 }
            frees[freeIdx] = frees[freeIdx].takeWhile { it != -1 } + file + frees[freeIdx].drop(frees[freeIdx].takeWhile { it != -1 }.size + file.size)
        }

        val all = files.zip(frees.take(files.size)).flatMap { it.toList() }.flatten()
        return all.withIndex().filter { it.value != -1 }.sumOf { (idx, value) -> idx * value.toLong() }
    }
}


fun main() = Day9().run()
