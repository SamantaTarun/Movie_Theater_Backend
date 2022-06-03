package com.demo.book.movie.service

import com.demo.book.movie.entity.Movie
import com.demo.book.movie.repository.MovieRepository
import com.demo.book.movie.request.MovieRequest
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows

class MovieServiceTest : StringSpec() {
    private val mockMovieRepository = mockk<MovieRepository>()

    override fun afterEach(testCase: TestCase, result: TestResult) {
        super.afterEach(testCase, result)
        clearAllMocks()
    }

    init {

        "Adding a movie with length 5 minutes" {
            val movieRequest = getDummyMovieRequest(5)
            val expected = getDummyMovie(5)
            every { mockMovieRepository.save(movieRequest) } returns expected
            val actual = MovieService(mockMovieRepository).save(movieRequest)
            actual shouldBe expected
        }

        "Adding a movie with length 6 hours" {
            val movieRequest = getDummyMovieRequest(360)
            val expected = getDummyMovie(360)
            every { mockMovieRepository.save(movieRequest) } returns expected
            val actual = MovieService(mockMovieRepository).save(movieRequest)
            actual shouldBe expected
        }

        "Adding a movie with length 3 hours" {
            val movieRequest = getDummyMovieRequest(180)
            val expected = getDummyMovie(180)
            every { mockMovieRepository.save(movieRequest) } returns expected
            val actual = MovieService(mockMovieRepository).save(movieRequest)
            actual shouldBe expected
        }

        "Adding a movie with length less than 4 minutes should throw an error" {
            assertThrows<InvalidDurationException> {
                MovieService(mockMovieRepository).save(
                    getDummyMovieRequest(4)
                )
            }
        }

        "Adding a movie with length less than 0 minutes should throw an error" {
            assertThrows<InvalidDurationException> {
                MovieService(mockMovieRepository).save(
                    getDummyMovieRequest(0)
                )
            }
        }

        "Adding a movie with length 6 hours and 1 minute should throw error" {
            assertThrows<InvalidDurationException> {
                MovieService(mockMovieRepository).save(
                    getDummyMovieRequest(361)
                )
            }
        }
    }

    private fun getDummyMovieRequest(duration: Int): MovieRequest {
        return MovieRequest("test", duration)
    }

    private fun getDummyMovie(duration: Int): Movie {
        return Movie(
            1, "test",
            duration
        )
    }
}
