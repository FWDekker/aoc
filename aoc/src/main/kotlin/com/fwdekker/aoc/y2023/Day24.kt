package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asTriple
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.cartesian
import com.fwdekker.std.read
import com.fwdekker.std.toLongs
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.roundToLong


private typealias LVec = List<Long>
private typealias LMatrix = List<LVec>
private typealias BDMatrix = List<List<BigDecimal>>


class Day24(
    resource: String = resource(2023, 24),
    private val coordinateRange: LongRange = 200000000000000L..400000000000000L,
) : Day() {
    private val stones =
        read(resource).linesNotBlank()
            .map { line -> line.split(Regex("\\s+@\\s+")).map { coords -> coords.toLongs(Regex(",\\s+")) } }


    override fun part1(): Int =
        stones
            .map { Line2D(it[0], it[1]) }
            .let { it.cartesian(it) }
            .filter { (a, b) -> a != b }
            .count { (a, b) -> a.collidesWith(b, coordinateRange) } / 2

    override fun part2(): Long {
        val (p0, v0) = stones[0]
        val (p1, v1) = stones[1]
        val (p2, v2) = stones[2]

        return (v0.cross().matPlus(v1.cross().matNeg())).vertCat(v0.cross().matPlus(v2.cross().matNeg()))
            .horzCat((p1.cross().matPlus(p0.cross().matNeg())).vertCat(p2.cross().matPlus(p0.cross().matNeg())))
            .horzCat((p0.cross(v0).vecNeg().vecPlus(p1.cross(v1))).cat(p2.cross(v2).vecPlus(p0.cross(v0).vecNeg())).T)
            .toBigDecimals()
            .rref()
            .take(3)
            .sumOf { it.last().toDouble().roundToLong() }
    }


    private data class Line2D(val position: Triple<Long, Long, Long>, val velocity: Triple<Long, Long, Long>) {
        private val slope = velocity.second.toDouble() / velocity.first
        private val yIntercept = position.second - position.first * slope


        constructor(position: LVec, velocity: LVec) : this(position.asTriple(), velocity.asTriple())


        fun collidesWith(that: Line2D, within: LongRange): Boolean {
            if (this.slope == that.slope)
                return this.yIntercept == that.yIntercept

            val x = (that.yIntercept - this.yIntercept) / (this.slope - that.slope)
            val y = this.slope * x + this.yIntercept

            return x in within &&
                y in within &&
                x.compareTo(this.position.first) == this.velocity.first.compareTo(0) &&
                x.compareTo(that.position.first) == that.velocity.first.compareTo(0)
        }


        private operator fun LongRange.contains(value: Double): Boolean = value >= first && value <= last
    }


    private fun LVec.vecNeg(): LVec = map { -it }

    private fun LVec.vecPlus(that: LVec): LVec = (this zip that).map { (a, b) -> a + b }

    private fun LVec.cross(): LMatrix =
        listOf(listOf(0, -this[2], this[1]), listOf(this[2], 0, -this[0]), listOf(-this[1], this[0], 0))

    private fun LVec.cross(that: LVec): LVec = cross().dot(that)

    private fun LVec.cat(that: LVec): LVec = this + that

    @Suppress("PrivatePropertyName")
    private val LVec.T: LMatrix get() = map { listOf(it) }


    private fun LMatrix.matNeg(): LMatrix = map { it.vecNeg() }

    private fun LMatrix.matPlus(that: LMatrix): LMatrix = (this zip that).map { (a, b) -> a.vecPlus(b) }

    private fun LMatrix.dot(that: LVec): LVec = map { row -> (row zip that).sumOf { (a, b) -> a * b } }

    private fun LMatrix.horzCat(that: LMatrix): LMatrix = (this zip that).map { (a, b) -> a + b }

    private fun LMatrix.vertCat(that: LMatrix): LMatrix = this + that

    private fun LMatrix.toBigDecimals(): List<List<BigDecimal>> = map { row -> row.map { it.toBigDecimal() } }


    private fun BDMatrix.copy() = map { it.toList() }

    private fun BDMatrix.swapRows(rowIdx1: Int, rowIdx2: Int): BDMatrix =
        mapIndexed { idx, row ->
            when (idx) {
                rowIdx1 -> this[rowIdx2]
                rowIdx2 -> this[rowIdx1]
                else -> row
            }
        }.copy()

    private fun BDMatrix.divideRow(rowIdx: Int, factor: BigDecimal): BDMatrix =
        mapIndexed { idx, row ->
            if (idx == rowIdx) row.map { it.divide(factor, MathContext.DECIMAL128) }
            else row
        }.copy()

    private fun BDMatrix.addRowMultiple(rowIdx1: Int, rowIdx2: Int, factor: BigDecimal): BDMatrix =
        mapIndexed { idx, row ->
            if (idx == rowIdx1) (row zip this[rowIdx2]).map { (a, b) -> a.add(b.multiply(factor)) }
            else row
        }.copy()

    private fun BDMatrix.rref(): BDMatrix {
        var output = this

        val rowIndices = this.indices
        val colIndices = this[0].indices

        var pivotRow = 0
        var pivotCol = 0
        while (pivotRow in rowIndices && pivotCol in colIndices) {
            val candidate = rowIndices.drop(pivotRow).maxBy { output[it][pivotCol].abs() }
            if (output[candidate][pivotCol].compareTo(BigDecimal.ZERO) == 0) {
                pivotCol++
                continue
            }

            output = output.swapRows(pivotRow, candidate)

            if (output[pivotRow][pivotCol].compareTo(BigDecimal.ZERO) != 0)
                output = output.divideRow(pivotRow, output[pivotRow][pivotCol])

            output = rowIndices.minus(pivotRow)
                .fold(output) { acc, it -> acc.addRowMultiple(it, pivotRow, -acc[it][pivotCol]) }

            pivotRow++
            pivotCol++
        }

        return output
    }
}


fun main() = Day24().run()
