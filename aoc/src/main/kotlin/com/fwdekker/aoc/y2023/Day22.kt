package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.toInts
import com.fwdekker.std.overlaps
import com.fwdekker.std.read


class Day22(resource: String = resource(2023, 22)) : Day() {
    private val lines = read(resource).linesNotBlank()
    private val stack = lines.asSequence()
        .map { line -> line.split('~').map { coords -> coords.toInts(',') } }
        .map { (from, to) -> (from zip to).map { it.first..it.second } }
        .map { (xs, ys, zs) -> Brick(xs, ys, zs) }
        .sortedBy { it.zs.first }
        .fold(Stack()) { caught, brick -> Stack(caught + caught.catch(brick)) }


    override fun part1(): Int = stack.filter { stack.canDisintegrate(it) }.size

    override fun part2(): Int = stack.sumOf { stack.size - stack.disintegrate(setOf(it)).size - 1 }


    private class Brick(val xs: IntRange, val ys: IntRange, val zs: IntRange) {
        fun fall(): Brick = Brick(xs, ys, zs.first - 1..<zs.last)

        fun onGround(): Boolean = 1 in zs

        fun supports(that: Brick): Boolean =
            that.zs.first - 1 in this.zs && this.xs.overlaps(that.xs) && this.ys.overlaps(that.ys)
    }

    private class Stack(val bricks: Collection<Brick> = emptyList()) : Collection<Brick> by bricks {
        fun catch(brick: Brick): Brick {
            var dropped = brick
            while (!dropped.onGround() && !any { it.supports(dropped) })
                dropped = dropped.fall()
            return dropped
        }

        fun supportOf(brick: Brick): Set<Brick> = filter { it.supports(brick) }.toSet()

        fun supportedBy(brick: Brick): Set<Brick> = filter { brick.supports(it) }.toSet()

        fun canDisintegrate(brick: Brick): Boolean = supportedBy(brick).all { supportOf(it).size > 1 }

        fun disintegrate(bricks: Set<Brick>): Stack =
            if (bricks.isEmpty()) this
            else Stack(this - bricks)
                .disintegrate(bricks.flatMap { supportedBy(it) }.filter { (supportOf(it) - bricks).isEmpty() }.toSet())
    }
}


fun main() = Day22().run()
