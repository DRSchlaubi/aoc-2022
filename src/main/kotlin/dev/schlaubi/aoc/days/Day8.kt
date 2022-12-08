package dev.schlaubi.aoc.days

import dev.schlaubi.aoc.Day
import kotlin.io.path.readLines

object Day8 : Day<List<List<Int>>>() {
    override val number: Int = 8

    override fun prepare(): List<List<Int>> = input.readLines().map {
        it.toCharArray().map(Char::digitToInt)
    }.reversed()

    override fun task1(prepared: List<List<Int>>): Any {
        return prepared.map(List<Int>::withIndex).withIndex().sumOf { (x, rows) ->
            rows.count { (y, _) ->
                val height = prepared.getAt(x, y) ?: 0
                !prepared.neighbourHigher(x, y, height, increaseYBy = 1) ||
                        !prepared.neighbourHigher(x, y, height, increaseYBy = -1) ||
                        !prepared.neighbourHigher(x, y, height, increaseXBy = 1) ||
                        !prepared.neighbourHigher(x, y, height, increaseXBy = -1)
            }
        }
    }

    override fun task2(prepared: List<List<Int>>): Any {
        return prepared.map(List<Int>::withIndex).withIndex().flatMap { (x, rows) ->
            rows.map { (y, _) ->
                val height = prepared.getAt(x, y) ?: 0
                prepared.neighbourSight(x, y, height, increaseYBy = 1) *
                        prepared.neighbourSight(x, y, height, increaseYBy = -1) *
                        prepared.neighbourSight(x, y, height, increaseXBy = 1) *
                        prepared.neighbourSight(x, y, height, increaseXBy = -1)
            }
        }.max()
    }
}

private fun List<List<Int>>.getAt(x: Int, y: Int): Int? = getOrNull(y)?.getOrNull(x)
private fun List<List<Int>>.neighbourHigher(x: Int, y: Int, minHeight: Int, increaseXBy: Int = 0, increaseYBy: Int = 0): Boolean {
    repeat(size) {
        val currentX = x + increaseXBy * (it + 1)
        val currentY = y + increaseYBy * (it + 1)
        val result = getAt(currentX, currentY) ?: return false
        if (result >= minHeight) {
            return true
        }
    }
    return false
}

private fun List<List<Int>>.neighbourSight(x: Int, y: Int, minHeight: Int, increaseXBy: Int = 0, increaseYBy: Int = 0): Int {
    repeat(size) {
        val currentX = x + increaseXBy * (it + 1)
        val currentY = y + increaseYBy * (it + 1)
        val result = getAt(currentX, currentY) ?: return it
        if (result >= minHeight) {
            return it + 1
        }
    }
    return size
}