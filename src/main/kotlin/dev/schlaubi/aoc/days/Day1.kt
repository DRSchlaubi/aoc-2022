package dev.schlaubi.aoc.days

import dev.schlaubi.aoc.Day
import dev.schlaubi.aoc.splitByBlank
import kotlin.io.path.readLines

object Day1 : Day<List<Int>>() {
    override val number: Int = 1

    override fun prepare(): List<Int> = input1
            .readLines()
            .splitByBlank()
            .map { it.sumOf(String::toInt) }

    override fun task1(prepared: List<Int>): Int = prepared.max()

    override fun task2(prepared: List<Int>): Int = prepared.sortedDescending().take(3).sum()
}
