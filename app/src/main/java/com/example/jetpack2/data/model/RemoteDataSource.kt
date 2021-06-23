package com.example.jetpack2.data.model

import androidx.lifecycle.LiveData

interface RemoteDataSource {
    fun getListMovie(): LiveData<List<FilmList>>
    fun getDetailMovie(movieId: String): LiveData<FilmList>
    fun getListTvShow(): LiveData<List<FilmList>>
    fun getDetailTvShow(tvshowId: String): LiveData<TvShowDetail>
}