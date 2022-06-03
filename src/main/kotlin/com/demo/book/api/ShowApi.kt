package com.demo.book.api

import com.demo.book.movie.entity.Show
import com.demo.book.movie.request.BookRequest
import com.demo.book.movie.request.ShowRequest
import com.demo.book.movie.service.ShowService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller
class ShowApi(@Inject val showService: ShowService) {

    @Get("/shows")
    fun allShows(): HttpResponse<List<Show>> {
        return HttpResponse.ok(showService.allShows())
    }

    @Post("/shows")
    fun saveShow(@Body showRequest: ShowRequest): MutableHttpResponse<Int> {
        return try {
            HttpResponse.ok(showService.save(showRequest).id)
        } catch (e: UnsupportedOperationException) {
            HttpResponse.status(HttpStatus.CONFLICT, e.message)
        }
    }

    @Post("/shows/book")
    fun bookShow(@Body bookRequest: BookRequest): HttpResponse<Int> {
        return try {
            HttpResponse.ok(showService.bookSeats(bookRequest))
        } catch (e: UnsupportedOperationException) {
            HttpResponse.status(HttpStatus.CONFLICT, e.message)
        }
    }

    @Get("/shows/{showId}")
    fun getAvailableSeats(@PathVariable showId: Int): HttpResponse<List<Int>> {
        return HttpResponse.ok(showService.getAvailableSeats(showId))
    }
}
