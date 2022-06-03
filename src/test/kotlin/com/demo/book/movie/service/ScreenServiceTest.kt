package com.demo.book.movie.service

import com.demo.book.movie.entity.Screen
import com.demo.book.movie.repository.ScreenRepository
import com.demo.book.movie.request.ScreenRequest
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk

class ScreenServiceTest : StringSpec() {
    init {
        "should able to call save method on ScreenService" {
            val mockScreenRepository = mockk<ScreenRepository>()
            val screenRequest = ScreenRequest("Screen 1", 100)
            val expected = Screen(1, "Screen 1", 100)
            every { mockScreenRepository.save(screenRequest) } returns expected
            val screenService = ScreenService(mockScreenRepository)
            val result = screenService.save(screenRequest)
            assert(result == expected)
        }

        "should able to call allMovies method on ScreenService" {
            val mockScreenRepository = mockk<ScreenRepository>()
            every { mockScreenRepository.findAll() } returns listOf(Screen(1, "Screen 1", 100))
            val screenService = ScreenService(mockScreenRepository)
            val result = screenService.allScreens()
            val excepted = listOf(Screen(1, "Screen 1", 100))
            assert(result == excepted)
        }
    }
}
