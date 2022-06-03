package com.demo.book.movie.repository
import com.demo.book.movie.entity.Show
import com.demo.book.movie.request.BookRequest
import com.demo.book.movie.request.ShowRequest
import movie.GetAllShowsParams
import movie.GetAllShowsQuery
import movie.GetNotBookedSeatsParams
import movie.GetNotBookedSeatsQuery
import movie.InsertSeatCommand
import movie.InsertSeatParams
import movie.SaveShowParams
import movie.SaveShowQuery
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
                showToSave.movieId
            )
        )
    }.map {
        Show(
            it.id,
            it.startTime.toLocalDateTime(),
            it.movieId,
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

    fun findOne(id: Int): Show = datasource.connection.use {
        connection ->
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
}
