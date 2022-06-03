package com.demo.book.movie.service

import com.demo.book.movie.entity.Show
import com.demo.book.movie.repository.ShowRepository
import com.demo.book.movie.request.ShowRequest
import java.time.ZoneOffset
import javax.inject.Inject
import javax.inject.Singleton

const val MILI_SECOND_CONVERTER = 1000
const val GMT_HOUR = 5
const val GMT_MINUTE = 30

@Singleton
class ShowService(@Inject val showRepository: ShowRepository, @Inject val movieService: MovieService) {
    @kotlin.jvm.Throws(UnsupportedOperationException::class)
    fun save(showRequest: ShowRequest): Show {
        val shows = allShows()
        shows.forEach {
            val durationInMilliSeconds: Int = movieService.findMovieById(it.movieId).duration * 60 * MILI_SECOND_CONVERTER
            if (
                it.startTime!!.toEpochSecond(ZoneOffset.ofHoursMinutes(GMT_HOUR, GMT_MINUTE)) * MILI_SECOND_CONVERTER <= showRequest.startTime &&
                it.startTime.toEpochSecond(ZoneOffset.ofHoursMinutes(GMT_HOUR, GMT_MINUTE)) * MILI_SECOND_CONVERTER + durationInMilliSeconds >= showRequest.startTime
            ) throw UnsupportedOperationException("A show is already running at this time")
        }
        return showRepository.save(showRequest)
    }

    fun allShows(): List<Show> {
        return showRepository.findAll().sortedByDescending { it.startTime }
    }
}
