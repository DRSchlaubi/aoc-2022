import dev.schlaubi.aoc.Day
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertEquals

// This way inheritors don't need to specify T
typealias DayTest = DayTestImpl<*>

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class DayTestImpl<T>(val day: Day<T>) {
    abstract val test1Result: Any
    abstract val test2Result: Any

    var prepared: T? = null

    @BeforeAll
    fun beforeAll() {
        prepared = day.prepare()
    }

    @Test
    fun `test task 1`() {
        assertEquals(test1Result, day.task1(prepared!!))
    }

    @Test
    fun `test task 2`() {
        assertEquals(test2Result, day.task2(prepared!!))
    }
}