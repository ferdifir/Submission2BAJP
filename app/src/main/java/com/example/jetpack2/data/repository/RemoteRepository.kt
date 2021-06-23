package com.example.jetpack2.data.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.jetpack2.BuildConfig.API_KEY
import com.example.jetpack2.data.model.FilmList
import com.example.jetpack2.data.model.FilmResponse
import com.example.jetpack2.data.model.TvShowDetail
import com.example.jetpack2.retrofit.ApiService
import com.example.jetpack2.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository(retrofitConfig: ApiService) {

    private val retrofitConfig = ApiService
    private var apiKey = API_KEY

    fun getMovie(getMovieCallback: GetMovieCallback) {
        EspressoIdlingResource.increment()
        Handler(Looper.getMainLooper()).postDelayed({
            retrofitConfig.create().getMovies(apiKey).enqueue(object:
                Callback<FilmResponse> {
                override fun onResponse(
                    call: Call<FilmResponse>,
                    response: Response<FilmResponse>
                ) {
                    response.body()?.results?.let { getMovieCallback.onResponse(it) }
                    EspressoIdlingResource.decrement()
                }
                override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

            })
        }, 1500)
    }

    interface GetMovieCallback {
        fun onResponse(movieResponse: List<FilmList>)
    }

    fun getMovieDetail(movieId: String, getMovieDetailCallback: GetMovieDetailCallback){
        EspressoIdlingResource.increment()
        Handler(Looper.getMainLooper()).postDelayed({
            retrofitConfig.create().getMovieDetails(movieId, apiKey).enqueue(object: Callback<FilmList> {
                override fun onResponse(call: Call<FilmList>, response: Response<FilmList>) {
                    getMovieDetailCallback.onResponse(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<FilmList>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

            })
        }, 1500)
    }

    interface GetMovieDetailCallback {
        fun onResponse(movieDetailResponse: FilmList)
    }

    fun getTvShow(getTvShowCallback: GetTvShowCallback){
        EspressoIdlingResource.increment()
        Handler(Looper.getMainLooper()).postDelayed({
            retrofitConfig.create().getTvShows(apiKey).enqueue(object:
                Callback<FilmResponse> {
                override fun onResponse(
                    call: Call<FilmResponse>,
                    response: Response<FilmResponse>
                ) {
                    response.body()?.results?.let { getTvShowCallback.onResponse(it) }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }


            })
        }, 1500)
    }

    interface GetTvShowCallback {
        fun onResponse(tvShowResponse: List<FilmList>)
    }

    fun getTvShowDetail(tvShowId: String, getTvShowDetailCallback: GetTvShowDetailCallback){
        EspressoIdlingResource.increment()
        Handler(Looper.getMainLooper()).postDelayed({
            retrofitConfig.create().getTvShowDetails(tvShowId, apiKey).enqueue(object:
                Callback<TvShowDetail> {
                override fun onResponse(
                    call: Call<TvShowDetail>,
                    response: Response<TvShowDetail>
                ) {
                    getTvShowDetailCallback.onResponse(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvShowDetail>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

            })
        }, 1500)
    }

    interface GetTvShowDetailCallback {
        fun onResponse(tvShowDetailResponse: TvShowDetail)
    }

    companion object{
        private var INSTANCE: RemoteRepository? = null
        private val TAG = RemoteRepository::class.java.toString()

        fun getInstance(retrofitConfig: ApiService): RemoteRepository{
            if (INSTANCE == null)
                INSTANCE = RemoteRepository(retrofitConfig)
            return INSTANCE as RemoteRepository
        }
    }

}