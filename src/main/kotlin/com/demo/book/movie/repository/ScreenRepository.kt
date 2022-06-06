package com.demo.book.movie.repository
import com.demo.book.movie.entity.Screen
import com.demo.book.movie.request.ScreenRequest
import movie.GetAllScreensParams
import movie.GetAllScreensQuery
import movie.SaveScreenParams
import movie.SaveScreenQuery
import norm.query
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource

@Singleton
class ScreenRepository(@Inject private val datasource: DataSource) {

    fun save(screenToSave: ScreenRequest): Screen = datasource.connection.use { connection ->
        SaveScreenQuery().query(
            connection,
            SaveScreenParams(
                screenToSave.title,
                screenToSave.capacity,
            )
        )
    }.map {
        Screen(
            it.id,
            it.title,
            it.capacity
        )
    }.first()

    fun findAll(): List<Screen> = datasource.connection.use { connection ->
        GetAllScreensQuery().query(
            connection,
            GetAllScreensParams()
        )
    }.map {
        Screen(
            it.id,
            it.title,
            it.capacity
        )
    }
}
