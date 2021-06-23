package com.example.jetpack2.retrofit

import com.example.jetpack2.data.model.FilmList
import com.example.jetpack2.data.model.FilmResponse
import com.example.jetpack2.data.model.TvShowDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiConfig {
    @GET("movie/popular")
    fun getMovies(
        @Query("api_key") apiKey: String
    ): Call<FilmResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: String?,
        @Query("api_key") apiKey: String?
    ) : Call<FilmList>

    @GET("tv/popular")
    fun getTvShows(
        @Query("api_key") apiKey: String?
    ) : Call<FilmResponse>


    @GET("tv/{tv_id}")
    fun getTvShowDetails(
        @Path("tv_id") tvId: String?,
        @Query("api_key") apiKey: String?
    ) : Call<TvShowDetail>
}