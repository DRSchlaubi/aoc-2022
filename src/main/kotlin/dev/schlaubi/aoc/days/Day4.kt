package dev.schlaubi.aoc.days

import dev.schlaubi.aoc.Day
import kotlin.io.path.readLines
import kotlin.ranges.IntRange

object Day4 : Day<List<List<IntRange>>>() {
    override val number: Int = 4

    override fun prepare(): List<List<IntRange>> {
        return input.readLines()
                .map { it.split(",").map(String::toAssignment) }
    }

    override fun task1(prepared: List<List<IntRange>>) = prepared
            .count { (first, second) -> first in second || second in first }

    override fun task2(prepared: List<List<IntRange>>) = prepared
            .count { (first, second) -> first.overlapsWith(second) }
}

private fun String.toAssignment(): IntRange {
    val (start, end) = split("-")
    return start.toInt()..end.toInt()
}

private operator fun IntRange.contains(other: IntRange) = other.first <= first && other.last >= last

private fun IntRange.overlapsWith(other: IntRange) = other.last >= first && other.first <= last
