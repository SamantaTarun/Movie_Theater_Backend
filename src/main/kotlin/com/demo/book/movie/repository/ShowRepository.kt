package com.demo.book.movie.repository

import com.demo.book.movie.entity.Show
import com.demo.book.movie.entity.Ticket
import com.demo.book.movie.request.BookRequest
import com.demo.book.movie.request.ShowRequest
import liquibase.pro.packaged.it
import movie.GetAllShowsParams
import movie.GetAllShowsQuery
import movie.GetNotBookedSeatsParams
import movie.GetNotBookedSeatsQuery
import movie.GetOverlappingShowsParams
import movie.GetOverlappingShowsQuery
import movie.InsertSeatCommand
import movie.InsertSeatParams
import movie.SaveShowParams
import movie.SaveShowQuery
import movie.SaveTicketParams
import movie.SaveTicketQuery
import movie.ShowByIdParams
import movie.ShowByIdQuery
import movie.UpdateSeatStatusCommand
import movie.UpdateSeatStatusParams
import movie.UpdateSeatsCommand
import movie.UpdateSeatsParams
import norm.command
import norm.query
import java.sql.Timestamp
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource

@Singleton
class ShowRepository(@Inject private val datasource: DataSource) {

    fun save(showToSave: ShowRequest): Show = datasource.connection.use { connection ->
        SaveShowQuery().query(
            connection,
            SaveShowParams(
                Timestamp.from(Instant.ofEpochMilli(showToSave.startTime)),
                showToSave.movieId,
                showToSave.price.toBigDecimal(),
                showToSave.movieLanguage,
                showToSave.movieType
            )
        )
    }.map {
        Show(
            it.id,
            it.startTime.toLocalDateTime(),
            it.movieId,
            it.price.toDouble(),
            it.movielanguage,
            it.movietype,
            it.seats
        )
    }.first()

    fun findAll(): List<Show> = datasource.connection.use { connection ->
        GetAllShowsQuery().query(
            connection,
            GetAllShowsParams()
        )
    }.map {
        Show(
            it.id,
            it.startTime.toLocalDateTime(),
            it.movieId,
            it.price.toDouble(),
            it.movielanguage,
            it.movietype,
            it.seats
        )
    }

    fun bookSeats(bookRequest: BookRequest): Int = datasource.connection.use { connection ->
        UpdateSeatsCommand().command(
            connection,
            UpdateSeatsParams(
                bookRequest.seats,
                bookRequest.showId
            )
        ).updatedRecordsCount
    }

    fun findOne(id: Int): Show = datasource.connection.use { connection ->
        ShowByIdQuery().query(
            connection,
            ShowByIdParams(
                id
            )
        )
    }.map {
        Show(
            it.id,
            it.startTime.toLocalDateTime(),
            it.movieId,
            it.price.toDouble(),
            it.movielanguage,
            it.movietype,
            it.seats
        )
    }.first()

    fun insertSeat(showId: Int, seat: Int): Unit = datasource.connection.use { connection ->
        InsertSeatCommand().command(
            connection,
            InsertSeatParams(
                showId,
                seat
            )
        )
    }

    fun updateStatus(showId: Int, seat_no: Int): Unit = datasource.connection.use { connection ->
        UpdateSeatStatusCommand().command(
            connection,
            UpdateSeatStatusParams(
                showId,
                seat_no
            )
        )
    }

    fun availableSeats(showId: Int): List<Int> = datasource.connection.use { connection ->
        GetNotBookedSeatsQuery().query(
            connection,
            GetNotBookedSeatsParams(
                showId
            )
        )
    }.map {
        it.seatNo
    }

    fun generateTicket(userEmail: String, seatNo: Int, showId: Int): Ticket = datasource.connection.use {
        connection ->
        SaveTicketQuery().query(
            connection,
            SaveTicketParams(
                userEmail,
                seatNo,
                showId
            )
        )
    }.map {
        Ticket(
            it.id,
            it.userEmail,
            it.seatNumber,
            it.showId
        )
    }.first()

    fun getConflictingShows(startTime: Long, showEndTime: Long): Long = datasource.connection.use {
        connection ->
        GetOverlappingShowsQuery().query(
            connection,
            GetOverlappingShowsParams(
                show_start_time = Timestamp(startTime),
                show_end_time = Timestamp(showEndTime),
            )
        )
    }.map {
        it.count!!
    }.first()
}
