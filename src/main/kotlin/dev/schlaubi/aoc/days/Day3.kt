package dev.schlaubi.aoc.days

import dev.schlaubi.aoc.SeparateDay
import kotlin.io.path.readLines

object Day3 : SeparateDay() {
    override val number: Int = 3

    override fun task1(): Any {
        return input.readLines()
                .asSequence()
                .map { rucksack ->
                    val (compartment1, compartment2) = rucksack.chunked(rucksack.length / 2)

                    compartment1.first { it in compartment2 }
                }.sumOf { it.toScore() }
    }

    override fun task2(): Any {
        return input.readLines()
                .asSequence()
                .chunked(3)
                .map { rucksack ->
                    rucksack.first().first { probableItem ->
                        rucksack.all {
                            probableItem in it
                        }
                    }
                }
                .sumOf { it.toScore() }
    }

    // UpperCase ASCII char starts at 65 and lower case ASCII starts at 97
    private fun Char.toScore() = if (isUpperCase()) code - 64 + 26 else code - 96
}
