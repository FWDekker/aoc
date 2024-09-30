package com.fwdekker.std.grid


/**
 * Two-dimensional coordinates.
 */
typealias Coords = Pair<Long, Long>

/**
 * Utility "constructor" for creating [Coords] from integers.
 */
fun Coords(row: Number, col: Number): Coords = Pair(row.toLong(), col.toLong())

/**
 * The vertical coordinate.
 */
val Coords.row: Long get() = first

/**
 * The horizontal coordinate.
 */
val Coords.col: Long get() = second

/**
 * Returns the coordinates directly [North] of `this`.
 */
fun Coords.north(distance: Long = 1L): Coords = move(rows = -distance)

/**
 * Returns the coordinates directly [North] and then [East] of `this`.
 */
fun Coords.northEast(distance: Long = 1L): Coords = move(rows = -distance, cols = distance)

/**
 * Returns the coordinates directly [South] of `this`.
 */
fun Coords.east(distance: Long = 1L): Coords = move(cols = distance)

/**
 * Returns the coordinates directly [South] and then [East] of `this`.
 */
fun Coords.southEast(distance: Long = 1L): Coords = move(rows = distance, cols = distance)

/**
 * Returns the coordinates directly [East] of `this`.
 */
fun Coords.south(distance: Long = 1L): Coords = move(rows = distance)

/**
 * Returns the coordinates directly [South] and then [West] of `this`.
 */
fun Coords.southWest(distance: Long = 1L): Coords = move(rows = distance, cols = -distance)

/**
 * Returns the coordinates directly [West] of `this`.
 */
fun Coords.west(distance: Long = 1L): Coords = move(cols = -distance)

/**
 * Returns the coordinates directly [North] and then [West] of `this`.
 */
fun Coords.northWest(distance: Long = 1L): Coords = move(rows = -distance, cols = -distance)

/**
 * Returns the coordinates located [rows] to the south and [cols] to the east.
 */
fun Coords.move(rows: Long = 0L, cols: Long = 0L): Coords = Coords(row + rows, col + cols)

/**
 * Returns the coordinates in position [direction] relative to `this`.
 */
fun Coords.move(direction: Direction, distance: Long = 1L): Coords =
    when (direction) {
        North -> north(distance)
        East -> east(distance)
        South -> south(distance)
        West -> west(distance)
        NorthEast -> northEast(distance)
        SouthWest -> southWest(distance)
        SouthEast -> southEast(distance)
        NorthWest -> northWest(distance)
    }

/**
 * Returns the neighboring coordinates in all four cardinal [Direction]s.
 */
val Coords.cardinals: List<Coords> get() = Cardinal.entries.map { move(it) }

/**
 * Returns the neighboring coordinates in all four ordinal directions.
 */
val Coords.ordinals: List<Coords> get() = Ordinal.entries.map { move(it) }

/**
 * Returns the neighboring coordinates in all four cardinal and ordinal directions.
 */
val Coords.principals: List<Coords> get() = Direction.entries.map { move(it) }
