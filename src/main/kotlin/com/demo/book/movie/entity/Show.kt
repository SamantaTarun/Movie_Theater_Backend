package com.demo.book.movie.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Show(
    val id: Int,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    val startTime: LocalDateTime?,
    val movieId: Int,
    val seats: Int?
)
