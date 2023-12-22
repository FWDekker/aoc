package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.readLines
import kotlin.math.max
import kotlin.math.min


fun main() {
    val lines = readLines("/y2023/Day22.txt")
    val bricks = lines
        .map { line -> line.split('~').map { coords -> coords.split(',') } }
        .map { coords -> coords[0].zip(coords[1]).map { it.first.toInt()..it.second.toInt() } }
        .map { Brick(it[0], it[1], it[2]) }
        .sortedBy { it.third.first }

    val caught = bricks.fold(emptyList<Brick>()) { caught, brick -> caught + caught.catch(brick) }

    // Part 1
    println("Part one: ${caught.filter { caught.canDisintegrate(it) }.size}")

    // Part 2
    println("Part one: ${caught.sumOf { caught.size - caught.disintegrate(setOf(it)).size - 1 }}")
}


private typealias Brick = Triple<IntRange, IntRange, IntRange>

private fun Brick.fall(): Brick = Brick(first, second, third.first - 1..<third.last)

private fun Brick.onGround(): Boolean = 1 in third

private fun Brick.supports(that: Brick): Boolean =
    that.third.first - 1 in this.third && this.first.intersects(that.first) && this.second.intersects(that.second)

private fun Collection<Brick>.supportOf(brick: Brick): List<Brick> = filter { it.supports(brick) }

private fun Collection<Brick>.supportBy(brick: Brick): List<Brick> = filter { brick.supports(it) }

private fun Collection<Brick>.canDisintegrate(brick: Brick): Boolean = supportBy(brick).all { supportOf(it).size > 1 }

private fun Collection<Brick>.disintegrate(bricks: Set<Brick>): List<Brick> {
    val candidates = bricks.flatMap { supportBy(it) }.filter { (supportOf(it).toSet() - bricks).isEmpty() }.toSet()
    return (this - bricks).let { if (candidates.isEmpty()) it else it.disintegrate(candidates) }
}

private fun Collection<Brick>.catch(brick: Brick): Brick {
    var dropped = brick
    while (!dropped.onGround() && !any { it.supports(dropped) })
        dropped = dropped.fall()
    return dropped
}

private fun IntRange.intersects(that: IntRange): Boolean =
    !(max(this.first, that.first)..min(this.last, that.last)).isEmpty()
