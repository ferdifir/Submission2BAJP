package com.example.jetpack2.data.model

data class TvShowDetail (
    val id: Int,
    val poster_path: String?,
    val backdrop_path: String?,
    val name: String?,
    val original_name: String?,
    val first_air_date: String?,
    val overview: String?,
    val vote_average: Double?
    )