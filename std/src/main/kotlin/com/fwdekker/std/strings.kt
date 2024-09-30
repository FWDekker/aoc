package com.fwdekker.std

import com.fwdekker.std.collections.repeat


/**
 * The alphabet.
 */
const val alphabet = "abcdefghijklmnopqrstuvwxyz"


/**
 * Returns the non-blank lines in this collection.
 */
private fun Collection<String>.filterNotBlank(): List<String> = filterNot(String::isBlank)

/**
 * Splits this string into lines, removing blank lines.
 */
fun String.linesNotBlank(): List<String> = lines().filterNotBlank()

/**
 * Splits this string into sections (as separated by [separator]), and each section into lines.
 */
fun String.sections(separator: String = "\n\n"): List<List<String>> = split(separator).map { it.lines() }


/**
 * Splits by [separator] and converts each entry to an [Int].
 */
fun String.toInts(separator: Char): List<Int> = toInts(separator.toString())

fun String.toInts(separator: String): List<Int> = split(separator).filterNotBlank().map { it.toInt() }

fun String.toInts(separator: Regex): List<Int> = split(separator).filterNotBlank().map { it.toInt() }

fun Collection<String>.toInts(separator: Char): List<List<Int>> = map { it.toInts(separator) }

fun Collection<String>.toInts(separator: String): List<List<Int>> = map { it.toInts(separator) }

fun Collection<String>.toInts(separator: Regex): List<List<Int>> = map { it.toInts(separator) }

/**
 * Splits by [separator] and converts each entry to a [Long].
 */
fun String.toLongs(separator: Char): List<Long> = toLongs(separator.toString())

fun String.toLongs(separator: String): List<Long> = split(separator).filterNotBlank().map { it.toLong() }

fun String.toLongs(separator: Regex): List<Long> = split(separator).filterNotBlank().map { it.toLong() }

fun Collection<String>.toLongs(separator: Char): List<List<Long>> = map { it.toLongs(separator) }

fun Collection<String>.toLongs(separator: String): List<List<Long>> = map { it.toLongs(separator) }

fun Collection<String>.toLongs(separator: Regex): List<List<Long>> = map { it.toLongs(separator) }


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
