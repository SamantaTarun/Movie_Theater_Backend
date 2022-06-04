package com.demo.book.movie.request

data class MovieRequest(
    val title: String,
    val duration: Int,
    val language: String,
    val price: Double
)
