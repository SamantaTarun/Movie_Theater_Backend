package com.demo.book.movie.request

data class ShowRequest(
    val startTime: Long,
    val movieId: Int,
    val price: Double
)
