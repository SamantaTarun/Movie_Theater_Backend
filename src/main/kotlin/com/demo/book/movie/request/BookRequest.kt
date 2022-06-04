package com.demo.book.movie.request

data class BookRequest(
    val showId: Int,
    val seats: Int,
    val seatList: List<Int>
)
