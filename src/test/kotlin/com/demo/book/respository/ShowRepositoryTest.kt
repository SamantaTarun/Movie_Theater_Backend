package com.demo.book.respository

import com.demo.book.BaseIntegrationSpec
import com.demo.book.movie.entity.Show
import com.demo.book.movie.repository.MovieRepository
import com.demo.book.movie.repository.ShowRepository
import com.demo.book.movie.request.MovieRequest
import com.demo.book.movie.request.ShowRequest
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
            ShowRepository(super.dataSource).getConflictingShows(showRequest.startTime - 30 * 60000, showRequest.startTime + 10 * 60000) shouldBe 1
        }
    }

    private fun getDummyShowRequest(startTime: ZonedDateTime): ShowRequest {
        return ShowRequest(startTime.toInstant().toEpochMilli(), 1)
    }

    private fun getDummyShow(id: Int, startTime: ZonedDateTime): Show {
        return Show(id, startTime.toLocalDateTime(), 1, 120)
    }

    private fun getDummyMovieRequest(duration: Int): MovieRequest {
        return MovieRequest("test", duration)
    }
}
