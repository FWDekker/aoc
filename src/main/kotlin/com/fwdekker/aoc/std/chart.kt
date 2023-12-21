package com.fwdekker.aoc.std


/**
 * A 2-dimensional chart (or map), represented by a list of strings all with the same length.
 */
typealias Chart = List<String>


/**
 * The number of rows in the chart.
 */
val Chart.height: Int get() = size

/**
 * The number of columns in the chart.
 */
val Chart.width: Int get() = this[0].length

/**
 * The range of row indices in the chart.
 */
val Chart.rows: IntRange get() = indices

/**
 * The range of column indices in the chart.
 */
val Chart.cols: IntRange get() = this[0].indices


/**
 * Returns `true` if and only if this chart contains a character at coordinates [row], [col].
 */
fun Chart.has(row: Int, col: Int): Boolean = row in rows && col in cols

/**
 * Returns `true` if and only if this chart contains a character at [coords].
 */
fun Chart.has(coords: Coords): Boolean = has(coords.row.toIntExact(), coords.col.toIntExact())

/**
 * Returns the cell at coordinates [row], [col].
 */
fun Chart.cell(row: Int, col: Int): Char = this[row][col]

/**
 * Returns the cell at [coords].
 */
fun Chart.cell(coords: Coords): Char = cell(coords.row.toIntExact(), coords.col.toIntExact())

/**
 * Returns the cell at coordinates [row], [col], or `null` if there is no cell at those coordinates.
 */
fun Chart.cellOrNull(row: Int, col: Int): Char? = getOrNull(row)?.getOrNull(col)

/**
 * Returns the cell at [coords], or `null` if there is no cell at those coordinates.
 */
fun Chart.cellOrNull(coords: Coords): Char? = cellOrNull(coords.row.toIntExact(), coords.col.toIntExact())

/**
 * Returns the cell at wrapped coordinates [row], [col], so `cellMod(-1, -1)` returns the bottom-right cell.
 */
fun Chart.cellMod(row: Number, col: Number): Char = getMod(row).getMod(col)

/**
 * Returns the cell at wrapped [coords], so `cellMod(Coords(-1, -1))` returns the bottom-right cell.
 */
fun Chart.cellMod(coords: Coords): Char = cellMod(coords.row, coords.col)

/**
 * Returns the first [Coords] at which [char] is found.
 */
fun Chart.coordsOf(char: Char): Coords =
    withIndex().asSequence().map { Coords(it.index, it.value.indexOf(char)) }.first { it.col >= 0 }


/**
 * Transposes, flipping semantics of rows and columns.
 */
fun Chart.transpose(): Chart = cols.map { col -> joinToString(separator = "") { line -> "${line[col]}" } }

/**
 * Reverses the order of rows.
 */
fun Chart.flipUD(): Chart = reversed()

/**
 * Reverses the order of columns.
 */
fun Chart.flipLR(): Chart = map { it.reversed() }

/**
 * Rotates clockwise.
 */
fun Chart.rotateCW(): Chart = transpose().flipLR()

/**
 * Rotates counter-clockwise.
 */
fun Chart.rotateCCW(): Chart = transpose().flipUD()

/**
 * Rotates halfway around the clock.
 */
fun Chart.rotateHalf(): Chart = flipLR().flipUD()


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
     * Absolute north.
     */
    val north: Direction get() = NORTH

    /**
     * Absolute east.
     */
    val east: Direction get() = EAST

    /**
     * Absolute south.
     */
    val south: Direction get() = SOUTH

    /**
     * Absolute west.
     */
    val west: Direction get() = WEST


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
         * Lookup table for [fromChar].
         */
        private val CONVERT = mapOf('N' to NORTH, 'E' to EAST, 'S' to SOUTH, 'W' to WEST)


        /**
         * Converts [char] to a [Direction] using the given [converter] as a lookup table.
         *
         * Using the default [converter], [char] must be one of `'N'`, `'E'`, `'S'`, and `'W'`, otherwise an exception
         * is thrown.
         */
        fun fromChar(char: Char, converter: Map<Char, Direction> = CONVERT): Direction = converter.getValue(char)

        /**
         * Does the same as [fromChar], except that this method returns `null` instead of throwing an exception.
         */
        fun fromCharOrNull(char: Char, converter: Map<Char, Direction> = CONVERT): Direction? = converter[char]
    }
}


/**
 * Two-dimensional coordinates.
 */
typealias Coords = Pair<Long,Long>

/**
 * Utility "constructor" for creating [Coords] from integers.
 */
@Suppress("FunctionName")
fun Coords(row: Int, col: Int) = Pair(row.toLong(), col.toLong())

/**
 * Utility "constructor" for creating [Coords] with named parameters.
 */
@Suppress("FunctionName")
fun Coords(row: Long, col: Int) = Pair(row, col)

/**
 * Utility method for copying [Coords] with named parameters.
 */
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun Coords.copy(row: Long = first, col: Long = second) = Pair(row, col)

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
val Coords.north: Coords get() = neighbor(Direction.NORTH)

/**
 * Returns the coordinates directly [Direction.SOUTH] of `this`.
 */
val Coords.east: Coords get() = neighbor(Direction.EAST)

/**
 * Returns the coordinates directly [Direction.EAST] of `this`.
 */
val Coords.south: Coords get() = neighbor(Direction.SOUTH)

/**
 * Returns the coordinates directly [Direction.WEST] of `this`.
 */
val Coords.west: Coords get() = neighbor(Direction.WEST)

/**
 * Returns the neighboring coordinates in all four [Direction]s.
 */
val Coords.neighbors: Collection<Coords> get() = listOf(north, east, south, west)

/**
 * Returns the coordinates in position [direction] relative to `this`.
 */
fun Coords.neighbor(direction: Direction): Coords =
    when (direction) {
        Direction.NORTH -> Coords(row - 1, col)
        Direction.EAST -> Coords(row, col + 1)
        Direction.SOUTH -> Coords(row + 1, col)
        Direction.WEST -> Coords(row, col - 1)
    }

/**
 * Returns the coordinates in position [direction] relative to `this`.
 *
 * @see Direction.fromChar
 */
fun Coords.neighbor(direction: Char, converter: (Char) -> Direction = { Direction.fromChar(it) }): Coords =
    neighbor(converter(direction))


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
    fun go(direction: Direction): Heading =
        Heading(coords.neighbor(direction), direction)

    /**
     * Turns and moves to the result of applying [transform] to [direction].
     */
    fun go(transform: Direction.() -> Direction): Heading = go(transform(direction))

    /**
     * Turns and moves to [direction].
     *
     * @see Direction.fromChar
     */
    fun go(direction: Char, converter: (Char) -> Direction = { Direction.fromChar(it) }): Heading =
        go(converter(direction))
}
