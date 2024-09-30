package com.fwdekker.std


/**
 * Splits this string into lines, removing blank lines.
 */
fun String.linesNotBlank(): List<String> = lines().filterNotBlank()

/**
 * Splits this string into sections (as separated by [separator]), and each section into lines.
 */
fun String.sections(separator: String = "\n\n"): List<List<String>> = split(separator).map { it.lines() }

/**
 * Returns the non-blank lines in this collection.
 */
fun Collection<String>.filterNotBlank(): List<String> = filterNot(String::isBlank)


/**
 * Splits by [separator] and converts each entry to an [Int].
 */
fun String.toInts(separator: Char): List<Int> = toInts(separator.toString())

/**
 * Splits by [separator] and converts each entry to an [Int].
 */
fun String.toInts(separator: String): List<Int> = split(separator).filterNotBlank().map(String::toInt)

/**
 * Splits by [separator] and converts each entry to an [Int].
 */
fun String.toInts(separator: Regex): List<Int> = split(separator).filterNotBlank().map(String::toInt)

/**
 * Splits by [separator] and converts each entry to a [Long].
 */
fun String.toLongs(separator: Char): List<Long> = toLongs(separator.toString())

/**
 * Splits by [separator] and converts each entry to a [Long].
 */
fun String.toLongs(separator: String): List<Long> = split(separator).filterNotBlank().map(String::toLong)

/**
 * Splits by [separator] and converts each entry to a [Long].
 */
fun String.toLongs(separator: Regex): List<Long> = split(separator).filterNotBlank().map(String::toLong)


/**
 * Sorts the characters in the string.
 */
fun String.sorted(): String =
    toCharArray().sortedArray().concatToString()

/**
 * Sorts the characters in the string in descending order.
 */
fun String.sortedDescending(): String =
    toCharArray().sortedArrayDescending().concatToString()

/**
 * Repeats this string [amount] times and joins the repetitions with [separator].
 */
fun String.repeat(amount: Int, separator: String = ""): String =
    listOf(this).repeat(amount).joinToString(separator = separator)
