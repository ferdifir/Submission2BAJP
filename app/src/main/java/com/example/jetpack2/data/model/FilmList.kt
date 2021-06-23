package com.example.jetpack2.data.model

data class FilmList(
    val id: Int,
    val poster_path: String?,
    val backdrop_path: String?,
    val title: String?,
    val tagLine: String?,
    val overview: String?,
    val release_date: String?,
    val vote_average: Double?,
    val name: String?
)
