package com.example.jetpack2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jetpack2.data.model.FilmList
import com.example.jetpack2.data.model.TvShowDetail
import com.example.jetpack2.data.repository.FilmRepository

class FilmViewModel(private val filmRepository: FilmRepository): ViewModel() {
    val movie: LiveData<List<FilmList>> = filmRepository.getListMovie()
    fun getMovieDetail(movieId: String): LiveData<FilmList> = filmRepository.getDetailMovie(movieId)
    val tvShow: LiveData<List<FilmList>> = filmRepository.getListTvShow()
    fun getTvShowDetail(tvId: String): LiveData<TvShowDetail> = filmRepository.getDetailTvShow(tvId)
}