package com.demo.book.api

import auth.token.verifier.annotation.ClaimsAllowed
import com.demo.book.movie.entity.Movie
import com.demo.book.movie.request.MovieRequest
import com.demo.book.movie.service.InvalidDurationException
import com.demo.book.movie.service.MovieService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller
class MovieApi(@Inject val movieService: MovieService) {

    @Get("/movies")
    fun allMovies(): HttpResponse<List<Movie>> {
        return HttpResponse.ok(movieService.allMovies())
    }

    @Post("/movies")
    @ClaimsAllowed(claimKey = "adminRights", claimValues = ["write"])
    fun saveMovie(@Body movieRequest: MovieRequest): MutableHttpResponse<Int> {
        return try {
            HttpResponse.ok(movieService.save(movieRequest).id)
        } catch (e: InvalidDurationException) {
            HttpResponse.status(HttpStatus.UNPROCESSABLE_ENTITY, e.message)
        }
    }
}
