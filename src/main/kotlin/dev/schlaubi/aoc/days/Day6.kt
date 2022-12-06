package dev.schlaubi.aoc.days

import dev.schlaubi.aoc.SeparateDay
import kotlin.io.path.readText

object Day6 : SeparateDay() {
    override val number: Int = 6

    override fun task1(): Any = performTask(4)

    override fun task2(): Any = performTask(14)

    private fun performTask(windowSize: Int) = input.readText()
            .asIterable()
            .windowed(windowSize)
            .withIndex()
            .first { (_, value) -> value.distinct() == value }
            .index + windowSize// windows size
}
