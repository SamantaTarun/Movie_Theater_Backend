package com.demo.book.api

import com.demo.book.BaseIntegrationSpec
import com.demo.book.movie.entity.Movie
import com.demo.book.movie.request.MovieRequest
import com.demo.book.utils.get
import com.demo.book.utils.post
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import java.time.ZoneId
import java.time.ZonedDateTime

class MovieApiTest : BaseIntegrationSpec() {

    init {
        "should save movie" {
            // Given
            val referenceDate = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val avengersMovie = newMovieRequest(
                120
            )

            // When
            val response = createNewMovie(avengersMovie)

            // Then
            response.status shouldBe HttpStatus.OK
            response.body.get() shouldBe 1
        }

        "should get all saved movies" {
            // Given
            val referenceDate = ZonedDateTime.of(2021, 6, 1, 9, 15, 0, 0, ZoneId.systemDefault())
            createNewMovie(
                newMovieRequest(
                    120
                )
            )

            // When
            val response = httpClient.get<List<Movie>>("/movies")

            // Then
            response.status shouldBe HttpStatus.OK
            val savedMovies = response.body.get()
            savedMovies.size shouldBe 1
            jsonString(savedMovies[0]) shouldBe """
                |{
                |  "id" : 1,
                |  "title" : "Avengers",
                |  "duration" : 120,
                |  "language" : "English",
                |  "price" : 100.0
                |}
            """.trimMargin().trimIndent()
        }

        "should return error" {
            // Given
            val referenceDate = ZonedDateTime.of(2021, 5, 21, 11, 15, 0, 0, ZoneId.systemDefault())
            val avengersMovie = newMovieRequest(
                420
            )

            // When
            try {
                val response = createNewMovie(avengersMovie)
            } catch (e: HttpClientResponseException) {
                e.status shouldBe HttpStatus.UNPROCESSABLE_ENTITY
            }
        }
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
            duration,
            "English",
            100.0,

        )
    }
}
