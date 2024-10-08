package com.fwdekker.std

import com.fwdekker.std.collections.repeat
import java.math.BigInteger


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

fun Sequence<String>.toInts(separator: Char): Sequence<List<Int>> = map { it.toInts(separator) }

fun Sequence<String>.toInts(separator: String): Sequence<List<Int>> = map { it.toInts(separator) }

fun Sequence<String>.toInts(separator: Regex): Sequence<List<Int>> = map { it.toInts(separator) }

fun Iterable<String>.toInts(separator: Char): List<List<Int>> = map { it.toInts(separator) }

fun Iterable<String>.toInts(separator: String): List<List<Int>> = map { it.toInts(separator) }

fun Iterable<String>.toInts(separator: Regex): List<List<Int>> = map { it.toInts(separator) }

/**
 * Splits by [separator] and converts each entry to a [Long].
 */
fun String.toLongs(separator: Char): List<Long> = toLongs(separator.toString())

fun String.toLongs(separator: String): List<Long> = split(separator).filterNotBlank().map { it.toLong() }

fun String.toLongs(separator: Regex): List<Long> = split(separator).filterNotBlank().map { it.toLong() }

fun Sequence<String>.toLongs(separator: Char): Sequence<List<Long>> = map { it.toLongs(separator) }

fun Sequence<String>.toLongs(separator: String): Sequence<List<Long>> = map { it.toLongs(separator) }

fun Sequence<String>.toLongs(separator: Regex): Sequence<List<Long>> = map { it.toLongs(separator) }

fun Iterable<String>.toLongs(separator: Char): List<List<Long>> = map { it.toLongs(separator) }

fun Iterable<String>.toLongs(separator: String): List<List<Long>> = map { it.toLongs(separator) }

fun Iterable<String>.toLongs(separator: Regex): List<List<Long>> = map { it.toLongs(separator) }

/**
 * Splits by [separator] and converts each entry to a [BigInteger].
 */
fun String.toBigIntegers(separator: Char): List<BigInteger> = toBigIntegers(separator.toString())

fun String.toBigIntegers(separator: String): List<BigInteger> = split(separator).filterNotBlank().map { it.toBigInteger() }

fun String.toBigIntegers(separator: Regex): List<BigInteger> = split(separator).filterNotBlank().map { it.toBigInteger() }

fun Sequence<String>.toBigIntegers(separator: Char): Sequence<List<BigInteger>> = map { it.toBigIntegers(separator) }

fun Sequence<String>.toBigIntegers(separator: String): Sequence<List<BigInteger>> = map { it.toBigIntegers(separator) }

fun Sequence<String>.toBigIntegers(separator: Regex): Sequence<List<BigInteger>> = map { it.toBigIntegers(separator) }

fun Iterable<String>.toBigIntegers(separator: Char): List<List<BigInteger>> = map { it.toBigIntegers(separator) }

fun Iterable<String>.toBigIntegers(separator: String): List<List<BigInteger>> = map { it.toBigIntegers(separator) }

fun Iterable<String>.toBigIntegers(separator: Regex): List<List<BigInteger>> = map { it.toBigIntegers(separator) }


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
