package com.demo.book.movie.service

import com.demo.book.movie.entity.Show
import com.demo.book.movie.entity.Ticket
import com.demo.book.movie.repository.ShowRepository
import com.demo.book.movie.request.BookRequest
import com.demo.book.movie.request.ShowRequest
import javax.inject.Inject
import javax.inject.Singleton

const val MILI_SECOND_CONVERTER = 60000

@Singleton
class ShowService(@Inject val showRepository: ShowRepository, @Inject val movieService: MovieService) {
    fun save(showRequest: ShowRequest): Show {
        val showEndTime = showRequest.startTime + movieService.findMovieById(showRequest.movieId).duration * MILI_SECOND_CONVERTER
        if (showRepository.getConflictingShows(
                showRequest.startTime,
                showEndTime
            ) > 0
        ) throw UnsupportedOperationException("A show is already running at this time")
        val show = showRepository.save(showRequest)
        for (i in 1..120) {
            showRepository.insertSeat(show.id, i)
        }
        return show
    }

    fun allShows(): List<Show> {
        return showRepository.findAll().sortedByDescending { it.startTime }
    }

    fun bookSeats(bookRequest: BookRequest, name: String): List<Ticket> {
        val show = showRepository.findOne(bookRequest.showId)
        if (show.seats!! < bookRequest.seats)throw UnsupportedOperationException("Required seats exceeds available seats")
        val available = showRepository.availableSeats(bookRequest.showId)
        if (bookRequest.seatList.any { it !in available }) throw UnsupportedOperationException("Requested seats are not available")
        val rec = showRepository.bookSeats(bookRequest)
        val ticket = mutableListOf<Ticket>()
        for (i in bookRequest.seatList) {
            showRepository.updateStatus(bookRequest.showId, i)
            ticket.add(showRepository.generateTicket(name, i, bookRequest.showId))
        }
        return ticket
    }

    fun getAvailableSeats(showId: Int): List<Int> {
        return showRepository.availableSeats(showId)
    }
}
