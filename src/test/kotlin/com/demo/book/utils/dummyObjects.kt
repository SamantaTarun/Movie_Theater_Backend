package com.demo.book.utils

import com.demo.book.movie.entity.Movie
import com.demo.book.movie.entity.Show
import com.demo.book.movie.entity.Ticket
import com.demo.book.movie.request.BookRequest
import com.demo.book.movie.request.MovieRequest
import com.demo.book.movie.request.ShowRequest
import java.time.ZonedDateTime

fun getDummyShowRequest(startTime: ZonedDateTime): ShowRequest {
    return ShowRequest(startTime.toInstant().toEpochMilli(), 1, 500.0, "English", "2D")
}

fun getDummyShow(id: Int, startTime: ZonedDateTime): Show {
    return Show(id, startTime.toLocalDateTime(), 1, 500.0, "English", "2D", 120)
}

fun getDummyMovieRequest(duration: Int): MovieRequest {
    return MovieRequest("test", duration)
}

fun getDummyBookRequest(): BookRequest {
    return BookRequest(1, 1, listOf(1))
}

fun getDummyMovie(duration: Int): Movie {
    return Movie(
        1, "test",
        duration
    )
}

fun getDummyTicket(): Ticket {
    return Ticket(
        1,
        "test@gmail.com",
        1,
        1
    )
}

