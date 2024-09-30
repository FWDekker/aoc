package com.fwdekker.std.maths

import com.fwdekker.std.collections.map


/**
 * Converts [this] to an [Int], or throws an exception if over- or underflow would occur.
 */
fun Long.toIntExact(): Int = Math.toIntExact(this)


/**
 * Converts all numbers to [Int]s.
 */
fun Pair<Number, Number>.toInts(): Pair<Int, Int> = map(Number::toInt)

fun Triple<Number, Number, Number>.toInts(): Triple<Int, Int, Int> = map(Number::toInt)

@JvmName("numbersToInts")
fun Iterable<Number>.toInts(): List<Int> = map(Number::toInt)

@JvmName("numberPairsToInts")
fun Iterable<Pair<Number, Number>>.toInts(): List<Pair<Int, Int>> = map { it.toInts() }


/**
 * Converts all numbers to [Long]s.
 */
fun Pair<Number, Number>.toLongs(): Pair<Long, Long> = map(Number::toLong)

fun Triple<Number, Number, Number>.toLongs(): Triple<Long, Long, Long> = map(Number::toLong)

@JvmName("numbersToLongs")
fun Iterable<Number>.toLongs(): List<Long> = map(Number::toLong)

@JvmName("numberPairsToLongs")
fun Iterable<Pair<Number, Number>>.toLongs(): List<Pair<Long, Long>> = map { it.toLongs() }
