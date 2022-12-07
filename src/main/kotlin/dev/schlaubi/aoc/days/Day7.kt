package dev.schlaubi.aoc.days

import dev.schlaubi.aoc.Day
import kotlin.io.path.readLines

private val cdPattern = """\$ cd (.*)""".toRegex()
private val lsPattern = """\$ ls""".toRegex()
private val filePattern = """(\d+) ((.*)(?:\.(.*))?)""".toRegex()

private data class SizedDirectory(val size: Int, val directory: FileSystemEntity.Directory)

private const val INDENT_STEP = 2

sealed interface FileSystemEntity {
    val name: String

    fun calculateSize(): Int

    data class File(val size: Int, override val name: String) : FileSystemEntity {
        override fun toString(): String = "$size $name"
        override fun calculateSize(): Int = size
    }

    data class Directory(override val name: String, val children: List<FileSystemEntity>) : FileSystemEntity {
        override fun toString(): String = toString(0)
        private fun toString(indent: Int): String = buildString {
            fun indent(actual: Int = indent) = if (actual > 0) append(" ".repeat(actual)) else this
            indent().append("dir $name")
            if (children.isNotEmpty()) {
                appendLine()
                children.forEach {
                    if (it is Directory) {
                        append(it.toString(indent + INDENT_STEP))
                    } else {
                        indent(indent + INDENT_STEP).appendLine(it)
                    }
                }
            }
        }

        override fun calculateSize(): Int = children.sumOf {
            when (it) {
                is Directory -> it.calculateSize()
                is File -> it.size
            }
        }
    }
}


private const val STORAGE_SPACE_90001 = 70000000
private const val OS_UPDATE_9001 = 30000000

object Day7 : Day<FileSystemEntity.Directory>() {
    override val number: Int = 7

    override fun prepare(): FileSystemEntity.Directory {
        val lines = input.readLines().iterator()
        fun doDirectory(name: String): FileSystemEntity.Directory {
            val children = buildList {
                while (lines.hasNext()) {
                    val line = lines.next()

                    val cdMatch = cdPattern.matchEntire(line)
                    when {
                        cdMatch != null -> {
                            val (destination) = cdMatch.destructured
                            if (destination == "..") break // no further lines will be for this directory
                            add(doDirectory(destination))
                        }

                        lsPattern.matches(line) -> Unit // be happy about next incoming lines
                        else -> {
                            filePattern.matchEntire(line)?.let {
                                val (size, fileName) = it.destructured
                                add(FileSystemEntity.File(size.toInt(), fileName))
                            }
                        }
                    }
                }
            }

            return FileSystemEntity.Directory(name, children)
        }

        val firstLine = lines.next()
        val (destination) = cdPattern.matchEntire(firstLine)?.destructured
                ?: error("Could not match first line: $firstLine")
        return doDirectory(destination)
    }


    override fun task1(prepared: FileSystemEntity.Directory): Any =
            prepared.calculateSubdirectorySizes()
                    // o begin, find all of the directories with a total size of at most 100000
                    .filter { (size) -> size <= 100000 }
                    .sumOf(SizedDirectory::size)

    override fun task2(prepared: FileSystemEntity.Directory): Any {
        val rootSize = prepared.calculateSize()
        val totalSpace = STORAGE_SPACE_90001
        val free = totalSpace - rootSize
        val remaining = OS_UPDATE_9001 - free

        return prepared.calculateSubdirectorySizes()
                .asSequence()
                .filter { it.size >= remaining }
                .sortedBy { it.size }
                .first()
                .size
    }
}


private fun FileSystemEntity.Directory.calculateSubdirectorySizes(): List<SizedDirectory> = children
        .asSequence()
        .filterIsInstance<FileSystemEntity.Directory>()
        .flatMap {
            (it.calculateSubdirectorySizes() + SizedDirectory(it.calculateSize(), it)).asSequence()
        }
        .toList()
