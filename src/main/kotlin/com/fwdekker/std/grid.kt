package com.fwdekker.std


/**
 * A 2-dimensional grid, represented by a list of lists, all with the same length.
 */
typealias Grid<T> = List<List<T>>


/**
 * The number of rows.
 */
val <T> Grid<T>.height: Int get() = size

/**
 * The number of columns.
 */
val <T> Grid<T>.width: Int get() = this[0].size

/**
 * The range of row indices.
 */
val <T> Grid<T>.rows: IntRange get() = indices

/**
 * The range of column indices.
 */
val <T> Grid<T>.cols: IntRange get() = this[0].indices

/**
 * The index of the last row.
 */
val <T> Grid<T>.lastRowIndex: Int get() = lastIndex

/**
 * The index of the last column.
 */
val <T> Grid<T>.lastColIndex: Int get() = this[0].lastIndex


/**
 * Returns the first row.
 */
val <T> Grid<T>.firstRow: List<T> get() = this[0]

/**
 * Returns the first column.
 */
val <T> Grid<T>.firstCol: List<T> get() = col(0)

/**
 * Returns the last row.
 */
val <T> Grid<T>.lastRow: List<T> get() = last()

/**
 * Returns the last column.
 */
val <T> Grid<T>.lastCol: List<T> get() = col(lastColIndex)

/**
 * Returns the [row]th row.
 */
fun <T> Grid<T>.row(row: Int): List<T> = this[row]

/**
 * Returns the [col]th column.
 */
fun <T> Grid<T>.col(col: Int): List<T> = map { it[col] }

/**
 * Returns the column in which [coords] is located.
 */
fun <T> Grid<T>.colOf(coords: Coords): List<T> = col(coords.col.toIntExact())

/**
 * Returns the row in which [coords] is located.
 */
fun <T> Grid<T>.rowOf(coords: Coords): List<T> = row(coords.row.toIntExact())


/**
 * Returns `true` if and only if the coordinates [row], [col] fall within bounds.
 */
fun <T> Grid<T>.contains(row: Int, col: Int): Boolean = row in rows && col in cols

/**
 * Returns `true` if and only if [coords] fall within bounds.
 */
operator fun <T> Grid<T>.contains(coords: Coords): Boolean = contains(coords.row.toIntExact(), coords.col.toIntExact())

/**
 * Returns the cell at coordinates [row], [col].
 */
fun <T> Grid<T>.cell(row: Int, col: Int): T = this[row][col]

/**
 * Returns the cell at [coords].
 */
fun <T> Grid<T>.cell(coords: Coords): T = cell(coords.row.toIntExact(), coords.col.toIntExact())

/**
 * Returns the cell at coordinates [row], [col], or `null` if there is no cell at those coordinates.
 */
fun <T> Grid<T>.cellOrNull(row: Int, col: Int): T? = getOrNull(row)?.getOrNull(col)

/**
 * Returns the cell at [coords], or `null` if there is no cell at those coordinates.
 */
fun <T> Grid<T>.cellOrNull(coords: Coords): T? = cellOrNull(coords.row.toIntExact(), coords.col.toIntExact())

/**
 * Returns the cell at wrapped coordinates [row], [col], so `cellMod(-1, -1)` returns the bottom-right cell.
 */
fun <T> Grid<T>.cellMod(row: Number, col: Number): T = getMod(row).getMod(col)

/**
 * Returns the cell at wrapped [coords], so `cellMod(Coords(-1, -1))` returns the bottom-right cell.
 */
fun <T> Grid<T>.cellMod(coords: Coords): T = cellMod(coords.row, coords.col)

/**
 * Returns the first [Coords] at which [cell] is found.
 */
fun <T> Grid<T>.coordsOf(cell: T): Coords =
    withIndex().asSequence().map { Coords(it.index, it.value.indexOf(cell)) }.first { it.col >= 0 }

/**
 * Returns all [Coords] at which [cell] is found.
 */
fun <T> Grid<T>.allCoordsOf(cell: T): List<Coords> =
    rows.flatMap { row -> this[row].withIndex().filter { (_, it) -> it == cell }.map { (col, _) -> Coords(row, col) } }


/**
 * Transposes, flipping semantics of rows and columns.
 */
fun <T> Grid<T>.transpose(): Grid<T> = cols.map(::col)

/**
 * Reverses the order of rows.
 */
fun <T> Grid<T>.flipUD(): Grid<T> = reversed()

/**
 * Reverses the order of columns.
 */
fun <T> Grid<T>.flipLR(): Grid<T> = map { it.reversed() }

/**
 * Rotates clockwise.
 */
fun <T> Grid<T>.rotateCW(): Grid<T> = transpose().flipLR()

/**
 * Rotates counter-clockwise.
 */
fun <T> Grid<T>.rotateCCW(): Grid<T> = transpose().flipUD()

/**
 * Rotates halfway around the clock.
 */
fun <T> Grid<T>.rotateHalf(): Grid<T> = flipLR().flipUD()



/**
 * A 2-dimensional chart (or map), represented by a list of strings, all with the same length.
 */
typealias Chart = Grid<Char>

/**
 * Returns the raw [String] representation of this row.
 */
fun List<Char>.toRaw(): String = this.joinToString("")

/**
 * Returns the [Chart] row representation of this raw [String].
 */
fun String.toRow(): List<Char> = this.toList()


/**
 * One of four cardinal directions.
 */
enum class Direction {
    /**
     * North.
     */
    NORTH,

    /**
     * East.
     */
    EAST,

    /**
     * South.
     */
    SOUTH,

    /**
     * West.
     */
    WEST;


    /**
     * `true` if and only if this direction is east or west.
     */
    val horizontal: Boolean
        get() = this == EAST || this == WEST

    /**
     * `true` if and only if this direction is north or south.
     */
    val vertical: Boolean
        get() = this == NORTH || this == SOUTH


    /**
     * Left relative to the current direction.
     */
    val left: Direction get() = LEFT.getValue(this)

    /**
     * Right relative to the current direction.
     */
    val right: Direction get() = RIGHT.getValue(this)

    /**
     * The current direction.
     */
    val ahead: Direction get() = this

    /**
     * Opposite the current direction.
     */
    val behind: Direction get() = BEHIND.getValue(this)


    /**
     * Mirrors north to south and vice versa, but not changing east and west.
     */
    val flipNS: Direction get() = FLIP_NS.getValue(this)

    /**
     * Mirrors east to west and vice versa, but not changing north and south.
     */
    val flipEW: Direction get() = FLIP_EW.getValue(this)

    /**
     * Mirrors diagonally, so north becomes west and east becomes south, and vice versa.
     */
    val flipNW: Direction get() = FLIP_NW.getValue(this)

    /**
     * Mirrors anti-diagonally, so north becomes east and south becomes west, and vice versa.
     */
    val flipNE: Direction get() = FLIP_NE.getValue(this)


    /**
     * Holds constants.
     */
    companion object {
        /**
         * Lookup table for [left].
         */
        private val LEFT = mapOf(NORTH to WEST, WEST to SOUTH, SOUTH to EAST, EAST to NORTH)

        /**
         * Lookup table for [right].
         */
        private val RIGHT = mapOf(NORTH to EAST, EAST to SOUTH, SOUTH to WEST, WEST to NORTH)

        /**
         * Lookup table for [behind].
         */
        private val BEHIND = mapOf(NORTH to SOUTH, SOUTH to NORTH, EAST to WEST, WEST to EAST)

        /**
         * Lookup table for [flipNS].
         */
        private val FLIP_NS = mapOf(NORTH to SOUTH, SOUTH to NORTH, EAST to EAST, WEST to WEST)

        /**
         * Lookup table for [flipEW].
         */
        private val FLIP_EW = mapOf(NORTH to NORTH, SOUTH to SOUTH, EAST to WEST, WEST to EAST)

        /**
         * Lookup table for [flipNW].
         */
        private val FLIP_NW = mapOf(NORTH to WEST, WEST to NORTH, EAST to SOUTH, SOUTH to EAST)

        /**
         * Lookup table for [flipNE].
         */
        private val FLIP_NE = mapOf(NORTH to EAST, EAST to NORTH, SOUTH to WEST, WEST to SOUTH)


        /**
         * Converts [item] to a [Direction] by matching the first entry in [converter] with [NORTH], the second with
         * [EAST], the third with [SOUTH], and the fourth with [WEST].
         */
        fun <T> from(item: T, converter: List<T>): Direction = entries[converter.indexOf(item)]

        /**
         * Converts [int] to a [Direction] by matching the first entry in [converter] with [NORTH], the second with
         * [EAST], the third with [SOUTH], and the fourth with [WEST].
         */
        fun fromInt(int: Int, converter: IntRange = 0..3): Direction = entries[converter.indexOf(int)]

        /**
         * Converts [char] to a [Direction] by matching the first entry in [converter] with [NORTH], the second with
         * [EAST], the third with [SOUTH], and the fourth with [WEST].
         */
        fun fromChar(char: Char, converter: CharRange): Direction = entries[converter.indexOf(char)]

        /**
         * Converts [char] to a [Direction] by matching the first entry in [converter] with [NORTH], the second with
         * [EAST], the third with [SOUTH], and the fourth with [WEST].
         */
        fun fromChar(char: Char, converter: String = "NESW"): Direction = entries[converter.indexOf(char)]
    }
}


/**
 * Two-dimensional coordinates.
 */
typealias Coords = Pair<Long,Long>

/**
 * Utility "constructor" for creating [Coords] from integers.
 */
fun Coords(row: Int, col: Int): Coords = Pair(row.toLong(), col.toLong())

/**
 * The vertical coordinate.
 */
val Coords.row: Long get() = first

/**
 * The horizontal coordinate.
 */
val Coords.col: Long get() = second

/**
 * Returns the coordinates directly [Direction.NORTH] of `this`.
 */
val Coords.north: Coords get() = move(rows = -1L)

/**
 * Returns the coordinates directly [Direction.NORTH] and then [Direction.EAST] of `this`.
 */
val Coords.northEast: Coords get() = move(rows = -1L, cols = 1L)

/**
 * Returns the coordinates directly [Direction.SOUTH] of `this`.
 */
val Coords.east: Coords get() = move(cols = 1L)

/**
 * Returns the coordinates directly [Direction.SOUTH] and then [Direction.EAST] of `this`.
 */
val Coords.southEast: Coords get() = move(rows = 1L, cols = 1L)

/**
 * Returns the coordinates directly [Direction.EAST] of `this`.
 */
val Coords.south: Coords get() = move(rows = 1L)

/**
 * Returns the coordinates directly [Direction.SOUTH] and then [Direction.WEST] of `this`.
 */
val Coords.southWest: Coords get() = move(rows = 1L, cols = -1L)

/**
 * Returns the coordinates directly [Direction.WEST] of `this`.
 */
val Coords.west: Coords get() = move(cols = -1L)

/**
 * Returns the coordinates directly [Direction.NORTH] and then [Direction.WEST] of `this`.
 */
val Coords.northWest: Coords get() = move(rows = -1L, cols = -1L)

/**
 * Returns the neighboring coordinates in all four cardinal [Direction]s.
 */
val Coords.cardinals: List<Coords> get() = listOf(north, east, south, west)

/**
 * Returns the neighboring coordinates in all four ordinal directions.
 */
val Coords.ordinals: List<Coords> get() = listOf(northEast, southEast, southWest, northWest)

/**
 * Returns the neighboring coordinates in all four cardinal and ordinal directions.
 */
val Coords.principals: List<Coords> get() = cardinals + ordinals

/**
 * Returns the coordinates in position [direction] relative to `this`.
 */
fun Coords.move(direction: Direction, distance: Long = 1L): Coords =
    when (direction) {
        Direction.NORTH -> Coords(row - distance, col)
        Direction.EAST -> Coords(row, col + distance)
        Direction.SOUTH -> Coords(row + distance, col)
        Direction.WEST -> Coords(row, col - distance)
    }

/**
 * Returns the coordinates located [rows] to the south and [cols] to the east.
 */
fun Coords.move(rows: Long = 0L, cols: Long = 0L): Coords = Coords(row + rows, col + cols)


/**
 * A location and a direction.
 */
data class Heading(val coords: Coords, val direction: Direction) {
    /**
     * Utility constructor for by-passing the [Coords] constructor.
     */
    constructor(row: Int, col: Int, direction: Direction) :
        this(Coords(row, col), direction)

    /**
     * Utility constructor for by-passing [Direction.fromChar].
     */
    constructor(coords: Coords, direction: Char, converter: (Char) -> Direction = { Direction.fromChar(it) }) :
        this(coords, converter(direction))

    /**
     * Utility constructor for by-passing both the [Coords] constructor and [Direction.fromChar].
     */
    constructor(row: Int, col: Int, direction: Char, converter: (Char) -> Direction = { Direction.fromChar(it) }) :
        this(Coords(row, col), converter(direction))


    /**
     * Turns and moves to [direction].
     */
    fun go(direction: Direction): Heading = Heading(coords.move(direction), direction)

    /**
     * Turns and moves to the result of applying [transform] to [direction].
     */
    fun go(transform: Direction.() -> Direction): Heading = go(transform(direction))

    /**
     * Turns and moves to each of the [directions].
     */
    fun go(vararg directions: Direction): List<Heading> = directions.map { go(it) }

    /**
     * Turns and moves to each result of applying the [transforms] to [direction].
     */
    fun go(vararg transforms: Direction.() -> Direction): List<Heading> = transforms.map { go(it) }
}
