package com.fwdekker.std.grid


/**
 * One of the eight principal directions.
 */
sealed class Direction {
    /**
     * Ninety degrees left relative to the current direction.
     */
    abstract val left: Direction

    /**
     * Ninety degrees right relative to the current direction.
     */
    abstract val ahead: Direction

    /**
     * The current direction.
     */
    abstract val right: Direction

    /**
     * Opposite the current direction.
     */
    abstract val behind: Direction


    /**
     * Mirrors north to south and vice versa, but not changing east and west.
     */
    abstract val flipNS: Direction

    /**
     * Mirrors east to west and vice versa, but not changing north and south.
     */
    abstract val flipEW: Direction

    /**
     * Mirrors diagonally, so north becomes west and east becomes south, and vice versa.
     */
    abstract val flipNW: Direction

    /**
     * Mirrors anti-diagonally, so north becomes east and south becomes west, and vice versa.
     */
    abstract val flipNE: Direction


    /**
     * `true` if and only if this direction is east or west.
     */
    abstract val horizontal: Boolean

    /**
     * `true` if and only if this direction is north or south.
     */
    abstract val vertical: Boolean

    /**
     * `true` if and only if this direction is north-west or south-east.
     */
    abstract val diagonal: Boolean

    /**
     * `true` if and only if this direction is north-east or south-west.
     */
    abstract val antiDiagonal: Boolean


    /**
     * Holds constants.
     */
    companion object {
        /**
         * The eight cardinal directions.
         */
        val entries = listOf(North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest)
    }
}


/**
 * One of the four cardinal directions.
 */
sealed class Cardinal : Direction() {
    override val left: Cardinal get() = LEFT.getValue(this)
    override val right: Cardinal get() = RIGHT.getValue(this)
    override val ahead: Cardinal get() = this
    override val behind: Cardinal get() = BEHIND.getValue(this)

    override val flipNS: Cardinal get() = FLIP_NS.getValue(this)
    override val flipEW: Cardinal get() = FLIP_EW.getValue(this)
    override val flipNW: Cardinal get() = FLIP_NW.getValue(this)
    override val flipNE: Cardinal get() = FLIP_NE.getValue(this)

    override val horizontal: Boolean get() = this == East || this == West
    override val vertical: Boolean get() = this == North || this == South
    override val diagonal: Boolean get() = false
    override val antiDiagonal: Boolean get() = false


    /**
     * Holds constants.
     */
    companion object {
        /**
         * The four cardinal directions.
         */
        val entries = listOf(North, East, South, West)


        /**
         * Lookup table for [left].
         */
        private val LEFT = mapOf(North to West, West to South, South to East, East to North)

        /**
         * Lookup table for [right].
         */
        private val RIGHT = mapOf(North to East, East to South, South to West, West to North)

        /**
         * Lookup table for [behind].
         */
        private val BEHIND = mapOf(North to South, South to North, East to West, West to East)

        /**
         * Lookup table for [flipNS].
         */
        private val FLIP_NS = mapOf(North to South, South to North, East to East, West to West)

        /**
         * Lookup table for [flipEW].
         */
        private val FLIP_EW = mapOf(North to North, South to South, East to West, West to East)

        /**
         * Lookup table for [flipNW].
         */
        private val FLIP_NW = mapOf(North to West, West to North, East to South, South to East)

        /**
         * Lookup table for [flipNE].
         */
        private val FLIP_NE = mapOf(North to East, East to North, South to West, West to South)


        /**
         * Converts [int] to a [Direction] by matching the first entry in [converter] with [North], the second with
         * [East], the third with [South], and the fourth with [West].
         */
        fun fromInt(int: Int, converter: IntRange = 0..3): Cardinal = entries[converter.indexOf(int)]

        fun fromChar(char: Char, converter: String = "NESW"): Cardinal = entries[converter.indexOf(char)]
    }
}

data object North : Cardinal()
data object East : Cardinal()
data object South : Cardinal()
data object West : Cardinal()


/**
 * One of the four ordinal directions.
 */
sealed class Ordinal : Direction() {
    override val left: Ordinal get() = LEFT.getValue(this)
    override val right: Ordinal get() = RIGHT.getValue(this)
    override val ahead: Ordinal get() = this
    override val behind: Ordinal get() = BEHIND.getValue(this)

    override val flipNS: Ordinal get() = FLIP_NS.getValue(this)
    override val flipEW: Ordinal get() = FLIP_EW.getValue(this)
    override val flipNW: Ordinal get() = FLIP_NW.getValue(this)
    override val flipNE: Ordinal get() = FLIP_NE.getValue(this)

    override val horizontal: Boolean get() = false
    override val vertical: Boolean get() = false
    override val diagonal: Boolean get() = this == NorthWest || this == SouthEast
    override val antiDiagonal: Boolean get() = this == NorthEast || this == SouthWest


    companion object {
        /**
         * The four ordinal directions.
         */
        val entries = listOf(NorthEast, SouthEast, SouthWest, NorthWest)


        /**
         * Lookup table for [left].
         */
        private val LEFT =
            mapOf(NorthEast to NorthWest, NorthWest to SouthWest, SouthWest to SouthEast, SouthEast to NorthEast)

        /**
         * Lookup table for [right].
         */
        private val RIGHT =
            mapOf(NorthEast to SouthEast, SouthEast to SouthWest, SouthWest to NorthWest, NorthWest to NorthEast)

        /**
         * Lookup table for [behind].
         */
        private val BEHIND =
            mapOf(NorthEast to SouthWest, SouthWest to NorthEast, SouthEast to NorthWest, NorthWest to SouthEast)

        /**
         * Lookup table for [flipNS].
         */
        private val FLIP_NS =
            mapOf(NorthEast to SouthEast, SouthEast to NorthEast, SouthWest to NorthWest, NorthWest to SouthWest)

        /**
         * Lookup table for [flipEW].
         */
        private val FLIP_EW =
            mapOf(NorthEast to NorthWest, NorthWest to NorthEast, SouthEast to SouthWest, SouthWest to SouthEast)

        /**
         * Lookup table for [flipNW].
         */
        private val FLIP_NW =
            mapOf(NorthWest to NorthWest, SouthEast to SouthEast, NorthEast to SouthWest, SouthWest to NorthEast)

        /**
         * Lookup table for [flipNE].
         */
        private val FLIP_NE =
            mapOf(NorthEast to NorthEast, SouthWest to SouthWest, NorthWest to SouthEast, SouthEast to NorthWest)
    }
}

data object NorthEast : Ordinal()
data object SouthEast : Ordinal()
data object SouthWest : Ordinal()
data object NorthWest : Ordinal()
