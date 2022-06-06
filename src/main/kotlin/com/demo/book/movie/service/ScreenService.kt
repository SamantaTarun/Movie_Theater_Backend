package com.demo.book.movie.service

import com.demo.book.movie.entity.Screen
import com.demo.book.movie.repository.ScreenRepository
import com.demo.book.movie.request.ScreenRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScreenService(@Inject val screenRepository: ScreenRepository) {

    fun save(screenRequest: ScreenRequest): Screen {
        val screens = screenRepository.findAll()
        screens.forEach {
            if (it.title == screenRequest.title) {
                throw Exception("Screen already exists")
            }
        }
        return screenRepository.save(screenRequest)
    }

    fun allScreens(): List<Screen> {
        return screenRepository.findAll()
    }
}
