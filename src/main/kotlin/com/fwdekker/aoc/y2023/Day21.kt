package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.std.Coords
import com.fwdekker.aoc.std.cell
import com.fwdekker.aoc.std.cellMod
import com.fwdekker.aoc.std.col
import com.fwdekker.aoc.std.cols
import com.fwdekker.aoc.std.coordsOf
import com.fwdekker.aoc.std.copy
import com.fwdekker.aoc.std.foldSelf
import com.fwdekker.aoc.std.has
import com.fwdekker.aoc.std.height
import com.fwdekker.aoc.std.neighbors
import com.fwdekker.aoc.std.pow
import com.fwdekker.aoc.std.readLines
import com.fwdekker.aoc.std.row
import com.fwdekker.aoc.std.rows
import com.fwdekker.aoc.std.width
import java.util.PriorityQueue
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign


fun main() {
    val chart = readLines("/y2023/Day21.txt")

    // Part 1
    val start = chart.coordsOf('S')
    println("Part one: ${chart.flood(start, maxDepth = 64).size}")

    // Part 2
    val horDistance = chart.wrappedDistance(start, start.copy(row = start.row + chart.height))
    val verDistance = chart.wrappedDistance(start, start.copy(col = start.col + chart.width))
    val mapDiameter = max(horDistance, verDistance)

    val targetDistance = 26501365L
    val bigSteps = targetDistance / mapDiameter
    val actualBigStepsWeTake = max(0, bigSteps - 1)
    val smallSteps = targetDistance % mapDiameter
    val allDistances = chart.distances(start)
    val safeSquaresPerMap = allDistances.filter { it.value % 2 == 0L }.size
//    require(allDistances
//        .filter { it.key.row in listOf(0L, chart.lastIndex) || it.key.col in listOf(0L, chart[0].lastIndex) }
//        .filter { it.value < smallSteps }
//        .isEmpty())
//    require(safeSquaresPerMap == chart.flood(start, maxDepth = 300).size)

    val untranslatedSteps = chart.floodMod(start, maxDepth = min(targetDistance, smallSteps + mapDiameter).toInt())
    if (actualBigStepsWeTake == 0L) {
        println("Part two: ${untranslatedSteps.size}")
        return
    }

    val n = untranslatedSteps.filter { it.row < 0 && it.col in chart.cols }.toSet()
    val ne = untranslatedSteps.filter { it.row < 0 && it.col >= chart.width }.toSet()
    val e = untranslatedSteps.filter { it.row in chart.rows && it.col >= chart.width }.toSet()
    val se = untranslatedSteps.filter { it.row >= chart.height && it.col >= chart.width }.toSet()
    val s = untranslatedSteps.filter { it.row >= chart.height && it.col in chart.cols }.toSet()
    val sw = untranslatedSteps.filter { it.row >= chart.height && it.col < 0 }.toSet()
    val w = untranslatedSteps.filter { it.row in chart.rows && it.col < 0 }.toSet()
    val nw = untranslatedSteps.filter { it.row < 0 && it.col < 0 }.toSet()

    val easySquaresInMaps = manhattanCombos(actualBigStepsWeTake) * safeSquaresPerMap
    val directlyAtTheEnds = listOf(n, e, s, w).sumOf { it.size }
    val atOrdinalDirections = (actualBigStepsWeTake + 1) * listOf(ne, se, sw, nw).sumOf { it.size }
    val atJoins = actualBigStepsWeTake * listOf(n + e, e + s, s + w, w + n).sumOf { it.size }
    println("Part two: ${easySquaresInMaps + directlyAtTheEnds + atOrdinalDirections + atJoins}")
    // 1235927740785860 is too high
    // 1235436644588630 is too high
    // 1235444319003494 is also going to be too high
    // 617242158204165 is too low
}


fun List<String>.flood(start: Coords, maxDepth: Int): Set<Coords> =
    setOf(start).foldSelf(maxDepth) { places ->
        places.flatMap { place -> place.neighbors.filter { has(it) && cell(it) != '#' } }.toSet()
    }

fun List<String>.floodMod(start: Coords, maxDepth: Int): Set<Coords> =
    setOf(start).foldSelf(maxDepth) { places ->
        places.flatMap { place -> place.neighbors.filter { cellMod(it) != '#' } }.toSet()
    }

fun List<String>.distances(from: Coords): Map<Coords, Long> {
    val distances = mutableMapOf<Coords, Long>().withDefault { Long.MAX_VALUE }
    val queue = PriorityQueue<Coords> { o1, o2 -> distances.getValue(o1).compareTo(distances.getValue(o2)) }

    distances[from] = 0L
    queue.add(from)
    while (queue.isNotEmpty()) {
        val current = queue.remove()
        val distance = distances.getValue(current)

        current.neighbors
            .filter { has(it) && cell(it) != '#' }
            .forEach {
                val newDistance = distance + 1
                if (newDistance < distances.getValue(it)) {
                    distances[it] = newDistance
                    queue.add(it)
                }
            }
    }

    return distances.toMap()
}

fun List<String>.wrappedDistance(from: Coords, to: Coords): Long {
    val distances = mutableMapOf<Coords, Long>().withDefault { Long.MAX_VALUE }
    val queue = PriorityQueue<Coords> { o1, o2 -> distances.getValue(o1).compareTo(distances.getValue(o2)) }

    distances[from] = 0L
    queue.add(from)
    while (queue.isNotEmpty()) {
        val current = queue.remove()
        val distance = distances.getValue(current)

        if (current == to)
            return distance

        current.neighbors
            .filter { cellMod(it) != '#' }
            .forEach {
                val newDistance = distance + 1
                if (newDistance < distances.getValue(it)) {
                    distances[it] = newDistance
                    queue.add(it)
                }
            }
    }

    error("No path found.")
}

fun List<String>.getWrapCoords(coords: Coords): Coords =
    Coords((abs(coords.row) / height) * coords.row.sign, (abs(coords.col) / width) * coords.col.sign)

fun manhattanCombos(distance: Long): Long =
    if (distance <= 0) 0
    else (distance + 1).pow(2) + distance.pow(2)
