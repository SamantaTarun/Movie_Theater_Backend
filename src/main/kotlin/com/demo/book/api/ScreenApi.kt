package com.demo.book.api

import com.demo.book.movie.entity.Screen
import com.demo.book.movie.request.ScreenRequest
import com.demo.book.movie.service.ScreenService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller("/screens")
class ScreenApi(@Inject val screenService: ScreenService) {

    @Get
    fun allScreens(): HttpResponse<List<Screen>> {
        return HttpResponse.ok(screenService.allScreens())
    }

    @Post
    fun addScreen(@Body screeRequest: ScreenRequest): HttpResponse<Int> {
        return try {
            HttpResponse.ok(screenService.save(screeRequest).id)
        } catch (error: Exception) {
            HttpResponse.status(HttpStatus.BAD_REQUEST, error.message)
        }
    }
}
