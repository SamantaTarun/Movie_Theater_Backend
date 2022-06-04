package com.demo.book.movie.service

import com.demo.book.movie.entity.Movie
import com.demo.book.movie.entity.Show
import com.demo.book.movie.repository.ShowRepository
import com.demo.book.movie.request.BookRequest
import com.demo.book.movie.request.ShowRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.collections.shouldBeSortedWith
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import java.time.ZoneId
import java.time.ZonedDateTime

class ShowServiceTest : StringSpec() {

    private val mockShowRepository = mockk<ShowRepository>(relaxUnitFun = true)
    private val mockMovieService = mockk<MovieService>()

    override fun afterEach(testCase: TestCase, result: TestResult) {
        super.afterEach(testCase, result)
        clearAllMocks()
    }

    init {

        "should be able to add the first show" {
            val referenceDate = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val showRequest = getDummyShowRequest(referenceDate)
            val expected = getDummyShow(1, referenceDate)
            every { mockMovieService.findMovieById(1) } returns getDummyMovie(40)
            every { mockShowRepository.getConflictingShows(showRequest.startTime, showRequest.startTime + 40 * 60000) } returns 0
            every { mockShowRepository.save(showRequest) } returns expected
            every { mockShowRepository.findAll() } returns listOf()
            val actual = ShowService(mockShowRepository, mockMovieService).save(showRequest)
            actual shouldBe expected
        }

        "Given the theatre has no shows an empty list should be returned" {
            every { mockShowRepository.findAll() } returns listOf<Show>()
            val actual = ShowService(mockShowRepository, mockMovieService).allShows()
            actual shouldBe emptyList()
        }

        "Given the theatre has two shows should return them in reverse chronological order" {

            val referenceDate = ZonedDateTime.of(2021, 5, 21, 1, 15, 0, 0, ZoneId.systemDefault())
            val expected = getDummyShow(1, referenceDate)

            val referenceDateTwo = ZonedDateTime.of(2021, 5, 21, 3, 15, 0, 0, ZoneId.systemDefault())
            val expectedTwo = getDummyShow(2, referenceDateTwo)

            // returns list in increasing orders
            every { mockShowRepository.findAll() } returns listOf(expected, expectedTwo)

            val actual = ShowService(mockShowRepository, mockMovieService).allShows()
            println(actual)

            actual shouldBeSortedWith (compareByDescending { it.startTime })
        }

        "Adding an show overlapping with an existing show should throw an error" {
            val referenceDate = ZonedDateTime.of(2021, 5, 21, 1, 15, 0, 0, ZoneId.systemDefault())
            val existingShow = getDummyShow(1, referenceDate)

            val referenceDateTwo = ZonedDateTime.of(2021, 5, 21, 1, 30, 0, 0, ZoneId.systemDefault())
            val newShow = getDummyShowRequest(referenceDateTwo)

            every { mockShowRepository.getConflictingShows(newShow.startTime, newShow.startTime + 30 * 60000) } returns 1
            every { mockShowRepository.findAll() } returns listOf(existingShow)
            every { mockMovieService.findMovieById(1) } returns Movie(1, "test", 30)
            every { mockShowRepository.save(newShow) } returns getDummyShow(1, referenceDate)

            shouldThrow<UnsupportedOperationException> {
                ShowService(mockShowRepository, mockMovieService).save(newShow)
            }
        }
    }

    private fun getDummyShowRequest(startTime: ZonedDateTime): ShowRequest {
        return ShowRequest(startTime.toInstant().toEpochMilli(), 1)
    }

    private fun getDummyShow(id: Int, startTime: ZonedDateTime): Show {
        return Show(id, startTime.toLocalDateTime(), 1, 120)
    }
    private fun getDummyBookRequest(tickets: Int): BookRequest {
        return BookRequest(1, tickets, listOf(1, 2, 3))
    }

    private fun getDummyMovie(duration: Int): Movie {
        return Movie(
            1, "test",
            duration
        )
    }
}
