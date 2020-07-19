package com.gorauskas.util.structures

import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.shouldBe
import org.junit.jupiter.api.*
import java.net.URI
import java.net.URL

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class HistoryTests {

    private var hstr: History<String> = History()
    private var hurl: History<URL> = History()

    @BeforeAll
    fun init() {
        for (item in HistoryTestHelper.stringList()) {
            hstr.current = item
        }

        for (item in HistoryTestHelper.urlList()) {
            hurl.current = item
        }
    }

    @Test
    @Order(1)
    fun `can move forwards`() {
        hstr.canMoveForward.shouldBeFalse()
        hstr.previous().shouldBe(HistoryTestHelper.ONE_PREVIOUS_STR)
        hstr.canMoveForward.shouldBeTrue()

        hurl.canMoveForward.shouldBeFalse()
        hurl.previous().shouldBe(HistoryTestHelper.ONE_PREVIOUS_URL)
        hurl.canMoveForward.shouldBeTrue()
    }

    @Test
    @Order(2)
    fun `can move backwards`() {
        (0..9).forEach {
            hstr.previous()
            hurl.previous()
        }

        hstr.canMoveBackward.shouldBeFalse()
        hstr.next().shouldBe(HistoryTestHelper.ONE_NEXT_STR)
        hstr.canMoveBackward.shouldBeTrue()

        hurl.canMoveBackward.shouldBeFalse()
        hurl.next().shouldBe(HistoryTestHelper.ONE_NEXT_URL)
        hurl.canMoveBackward.shouldBeTrue()
    }

    @Test
    @Order(3)
    fun `current test`() {
        hstr.current.shouldBe(HistoryTestHelper.CURRENT_STR)
        hurl.current.shouldBe(HistoryTestHelper.CURRENT_URL)
    }

    @Test
    @Order(4)
    fun `next test`() {
        hstr.next()
        hstr.next()
        hstr.next()
        hstr.next().shouldBe(HistoryTestHelper.NEXT_STR)

        hurl.next()
        hurl.next()
        hurl.next()
        hurl.next().shouldBe(HistoryTestHelper.NEXT_URL)
    }

    @Test
    @Order(5)
    fun `previous test`() {
        hstr.previous()
        hstr.previous().shouldBe(HistoryTestHelper.PREV_STR)

        hurl.previous()
        hurl.previous().shouldBe(HistoryTestHelper.PREV_URL)
    }

    @Test
    @Order(6)
    fun `dump test`() {
        hstr.dump().shouldBe(HistoryTestHelper.stringList().reversed())
        hurl.dump().shouldBe(HistoryTestHelper.urlList().reversed())
    }

}

object HistoryTestHelper {

    val ONE_PREVIOUS_STR = "http://news.ycombinator.org/"
    val ONE_PREVIOUS_URL = URI(ONE_PREVIOUS_STR).toURL()

    val ONE_NEXT_STR = "http://en.wikipedia.org/wiki/History_(Unix)"
    val ONE_NEXT_URL = URI(ONE_NEXT_STR).toURL()

    val CURRENT_STR = "http://en.wikipedia.org/wiki/History_(Unix)"
    val CURRENT_URL = URI(CURRENT_STR).toURL()

    val NEXT_STR = "https://www.google.com/"
    val NEXT_URL = URI(NEXT_STR).toURL()

    val PREV_STR = "http://en.wikipedia.org/wiki/Priority_queue"
    val PREV_URL = URI(PREV_STR).toURL()

    fun stringList(): List<String> =
        mutableListOf<String>().also {
            it.add("http://en.wikipedia.org/")
            it.add("http://en.wikipedia.org/wiki/History_(Unix)")
            it.add("http://en.wikipedia.org/wiki/Graph_(data_structure)")
            it.add("http://en.wikipedia.org/wiki/Priority_queue")
            it.add("http://en.wikipedia.org/wiki/Gap_buffer")
            it.add("https://www.google.com/")
            it.add("http://www.microsoft.com/en-us/default.aspx")
            it.add("http://news.ycombinator.org/")
            it.add("http://en.wikipedia.org/wiki/Coin_problem")
        }.toList()

    fun urlList(): List<URL> =
        stringList().map {
            URI(it).toURL()
        }

}