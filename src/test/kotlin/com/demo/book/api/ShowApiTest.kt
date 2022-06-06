

package com.demo.book.api

import com.demo.book.BaseIntegrationSpec
import com.demo.book.movie.entity.Show
import com.demo.book.movie.request.MovieRequest
import com.demo.book.movie.request.ShowRequest
import com.demo.book.utils.get
import com.demo.book.utils.post
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import java.time.ZoneId
import java.time.ZonedDateTime

class ShowApiTest : BaseIntegrationSpec() {

    init {
        "should save Show" {
            // Given
            val referenceDate = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val avengersShow = newShowRequest(
                referenceDate.toInstant().toEpochMilli()
            )

            createNewMovie(
                newMovieRequest(
                    120
                )
            )
            // When
            val response = createNewShow(avengersShow)

            // Then
            response.status shouldBe HttpStatus.OK
            response.body.get() shouldBe 1
        }

        "should get all saved Shows" {
            // Given
            val referenceDate = ZonedDateTime.of(2021, 6, 1, 9, 15, 0, 0, ZoneId.systemDefault())

            createNewMovie(
                newMovieRequest(
                    120
                )
            )
            createNewShow(
                newShowRequest(
                    referenceDate.toInstant().toEpochMilli()
                )
            )

            // When
            val response = httpClient.get<List<Show>>("/shows")

            // Then
            response.status shouldBe HttpStatus.OK
            val savedShows = response.body.get()
            savedShows.size shouldBe 1
            jsonString(savedShows[0]) shouldBe """
                |{
                |  "id" : 1,
                |  "startTime" : "2021-06-01 09:15:00.000",
                |  "movieId" : 1,
                |  "price" : 100.0,
                |  "movieLanguage" : "English",
                |  "movieType" : "2D",
                |  "seats" : 120
                |}
            """.trimMargin().trimIndent()
        }

        "should return error" {
            // Given
            val referenceDate = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            createNewMovie(
                newMovieRequest(
                    120
                )
            )
            createNewShow(
                newShowRequest(
                    referenceDate.toInstant().toEpochMilli()
                )
            )

            // When
            try {
                val response = createNewShow(
                    newShowRequest(referenceDate.toInstant().toEpochMilli())
                )
            } catch (e: HttpClientResponseException) {
                e.status shouldBe HttpStatus.CONFLICT
            }
        }
    }

    private fun createNewShow(avengersShow: ShowRequest): HttpResponse<Any> {
        return httpClient.post(
            url = "/shows",
            body = jsonMapper.writeValueAsString(avengersShow)
        )
    }

    private fun newShowRequest(start_time: Long): ShowRequest {
        return ShowRequest(
            startTime = start_time,
            1,
            100.00,
            "English",
            "2D"
        )
    }

    private fun createNewMovie(avengersMovie: MovieRequest): HttpResponse<Any> {
        return httpClient.post(
            url = "/movies",
            body = jsonMapper.writeValueAsString(avengersMovie)
        )
    }

    private fun newMovieRequest(duration: Int): MovieRequest {
        return MovieRequest(
            "Avengers",
            duration
        )
    }
}
