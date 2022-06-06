package com.demo.book.respository

import com.demo.book.BaseIntegrationSpec
import com.demo.book.movie.entity.Ticket
import com.demo.book.movie.repository.MovieRepository
import com.demo.book.movie.repository.ShowRepository
import com.demo.book.utils.getDummyBookRequest
import com.demo.book.utils.getDummyMovieRequest
import com.demo.book.utils.getDummyShow
import com.demo.book.utils.getDummyShowRequest
import io.kotest.matchers.shouldBe
import java.time.ZoneId
import java.time.ZonedDateTime

class ShowRepositoryTest() : BaseIntegrationSpec() {

    init {
        "should be able to save a show in the database" {
            val startTime = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val showRequest = getDummyShowRequest(startTime)
            MovieRepository(super.dataSource).save(getDummyMovieRequest(30))
            ShowRepository(super.dataSource).save(showRequest) shouldBe getDummyShow(1, startTime)
        }

        "should return an overlapping show" {
            val startTime = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val showRequest = getDummyShowRequest(startTime)
            MovieRepository(super.dataSource).save(getDummyMovieRequest(30))
            ShowRepository(super.dataSource).save(showRequest)
            ShowRepository(super.dataSource).getConflictingShows(
                showRequest.startTime - 30 * 60000,
                showRequest.startTime + 10 * 60000
            ) shouldBe 1
        }

        "should return the show when searched by it's id" {
            val startTime = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val showRequest = getDummyShowRequest(startTime)
            MovieRepository(super.dataSource).save(getDummyMovieRequest(30))
            ShowRepository(super.dataSource).save(showRequest)
            ShowRepository(super.dataSource).findOne(1) shouldBe getDummyShow(1, startTime)
        }

        "should be able to book seats" {
            val startTime = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val showRequest = getDummyShowRequest(startTime)
            MovieRepository(super.dataSource).save(getDummyMovieRequest(30))
            ShowRepository(super.dataSource).save(showRequest)
            val bookRequest = getDummyBookRequest()
            ShowRepository(super.dataSource).bookSeats(bookRequest) shouldBe 1
        }

        "should return all the shows present" {
            val startTime = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val startTimeTwo = ZonedDateTime.of(2021, 5, 23, 11, 15, 0, 0, ZoneId.systemDefault())
            val showRequest = getDummyShowRequest(startTime)
            val showRequestTwo = getDummyShowRequest(startTimeTwo)
            MovieRepository(super.dataSource).save(getDummyMovieRequest(30))
            ShowRepository(super.dataSource).save(showRequest)
            ShowRepository(super.dataSource).save(showRequestTwo)
            ShowRepository(super.dataSource).findAll() shouldBe listOf(getDummyShow(1, startTime), getDummyShow(2, startTimeTwo))
        }

        "should generate ticket" {
            val startTime = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val showRequest = getDummyShowRequest(startTime)
            MovieRepository(super.dataSource).save(getDummyMovieRequest(30))
            ShowRepository(super.dataSource).save(showRequest)
            ShowRepository(super.dataSource).generateTicket("test@gmail.com", 1, 1) shouldBe Ticket(1, "test@gmail.com", 1, 1)
        }
    }
}
