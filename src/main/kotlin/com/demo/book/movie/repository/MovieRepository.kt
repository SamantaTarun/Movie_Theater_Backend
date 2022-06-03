package com.demo.book.movie.repository

import com.demo.book.movie.entity.Movie
import com.demo.book.movie.request.MovieRequest
import movie.GetAllMoviesParams
import movie.GetAllMoviesQuery
import movie.MovieByIdParams
import movie.MovieByIdQuery
import movie.SaveMovieParams
import movie.SaveMovieQuery
import norm.query
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource

@Singleton
class MovieRepository(@Inject private val datasource: DataSource) {

    fun save(movieToSave: MovieRequest): Movie = datasource.connection.use { connection ->
        SaveMovieQuery().query(
            connection,
            SaveMovieParams(
                movieToSave.title,
                movieToSave.duration,
                movieToSave.language,
                movieToSave.price.toBigDecimal()
            )
        )
    }.map {
        Movie(
            it.id,
            it.title,
            it.duration!!,
            it.language!!,
            it.price!!.toDouble(),
        )
    }.first()

    fun findAll(): List<Movie> = datasource.connection.use { connection ->
        GetAllMoviesQuery().query(
            connection,
            GetAllMoviesParams()
        )
    }.map {
        Movie(
            it.id,
            it.title,
            it.duration!!,
            it.language!!,
            it.price!!.toDouble(),
        )
    }

    fun findOne(id: Int): Movie = datasource.connection.use {
        connection ->
        MovieByIdQuery().query(
            connection,
            MovieByIdParams(
                id
            )
        )
    }.map {
        Movie(
            it.id,
            it.title,
            it.duration!!,
            it.language!!,
            it.price!!.toDouble(),
        )
    }.first()
}
