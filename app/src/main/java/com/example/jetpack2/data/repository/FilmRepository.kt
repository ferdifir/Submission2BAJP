package com.example.jetpack2.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpack2.data.model.RemoteDataSource
import com.example.jetpack2.data.model.FilmList
import com.example.jetpack2.data.model.TvShowDetail

class FilmRepository(localRepository: LocalRepository, private val remoteRepository: RemoteRepository):
    RemoteDataSource {

    override fun getListMovie(): LiveData<List<FilmList>> {
        val listMovies = MutableLiveData<List<FilmList>>()
        remoteRepository.getMovie(object: RemoteRepository.GetMovieCallback{
            override fun onResponse(movieResponse: List<FilmList>) {
                listMovies.postValue(movieResponse)
            }
        })
        return listMovies
    }

    override fun getDetailMovie(movieId: String): LiveData<FilmList> {
        val detailMovie = MutableLiveData<FilmList>()
        remoteRepository.getMovieDetail(movieId, object: RemoteRepository.GetMovieDetailCallback{
            override fun onResponse(movieDetailResponse: FilmList) {
                detailMovie.postValue(movieDetailResponse)
            }

        })
        return detailMovie
    }

    override fun getListTvShow(): LiveData<List<FilmList>> {
        val listTvShows = MutableLiveData<List<FilmList>>()
        remoteRepository.getTvShow(object: RemoteRepository.GetTvShowCallback{
            override fun onResponse(tvShowResponse: List<FilmList>) {
                listTvShows.postValue(tvShowResponse)
            }
        })
        return listTvShows
    }

    override fun getDetailTvShow(tvshowId: String): LiveData<TvShowDetail> {
        val detailTvShow = MutableLiveData<TvShowDetail>()
        remoteRepository.getTvShowDetail(tvshowId, object: RemoteRepository.GetTvShowDetailCallback{
            override fun onResponse(tvShowDetailResponse: TvShowDetail) {
                detailTvShow.postValue(tvShowDetailResponse)
            }

        })
        return detailTvShow
    }

    companion object{
        @Volatile
        private var INSTANCE: FilmRepository? = null

        fun getInstance(localRepository: LocalRepository, remoteRepository: RemoteRepository): FilmRepository? {
            if (INSTANCE == null){
                synchronized(FilmRepository::class.java){
                    if (INSTANCE == null)
                        INSTANCE = FilmRepository(localRepository, remoteRepository)
                }
            }
            return INSTANCE
        }
    }
}