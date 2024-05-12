package com.example.finalprojectmovie.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Float?,
    val genre: String,
)
