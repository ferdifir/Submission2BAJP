package com.example.jetpack2.mainview.content

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jetpack2.R
import com.example.jetpack2.data.model.FilmList
import com.example.jetpack2.data.model.TvShowDetail
import com.example.jetpack2.utils.Helper.TYPE_MOVIE
import com.example.jetpack2.utils.Helper.TYPE_TVSHOW
import com.example.jetpack2.viewmodel.FilmViewModel
import com.example.jetpack2.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Detail Film"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setDetailFilm()
        showLoading(true)
        setFilmFav()
    }

    private fun setFilmFav() {
        var statusFav = false
        toggle_favorite.setOnClickListener {
            statusFav = !statusFav
            if (statusFav) {
                Toast.makeText(this,
                    "this film has been added to film's favorite list",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,
                    "this film has been removed from film's favorite list",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbDetail.visibility = View.VISIBLE
        } else {
            pbDetail.visibility = View.GONE
        }
    }

    private fun setDetailFilm() {
        val movie = intent.getStringExtra(TYPE_MOVIE)
        val tvshow = intent.getStringExtra(TYPE_TVSHOW)

        if (movie != null) {
            val movieDetailViewModel by lazy {
                val viewModelFactory = ViewModelFactory.getInstance()
                viewModelFactory?.let { ViewModelProvider(this, it).get(FilmViewModel::class.java) }
            }
            movieDetailViewModel?.getMovieDetail(movie)?.observe(this, {
                showLoading(false)
                loadDataMovie(it)
            })
        } else {
            val tvShowDetailViewModel by lazy{
                val viewModelFactory = ViewModelFactory.getInstance()
                viewModelFactory?.let { ViewModelProvider(this, it).get(FilmViewModel::class.java) }
            }
            if (tvshow != null) {
                tvShowDetailViewModel?.getTvShowDetail(tvshow)?.observe(this, {
                    showLoading(false)
                    loadDataTvShow(it)
                })
            }
        }
    }

    private fun loadDataTvShow(tvshow: TvShowDetail?) {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${tvshow?.poster_path}")
            .into(img_item_photo)
        tv_title.text = tvshow?.name
        tv_desc.text = tvshow?.overview
        tv_realase_date.text = tvshow?.first_air_date
        tv_orgin.text = tvshow?.original_name
    }

    private fun loadDataMovie(movie: FilmList?) {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie?.poster_path}")
            .into(img_item_photo)
        tv_title.text = movie?.title
        tv_desc.text = movie?.overview
        tv_realase_date.text = movie?.release_date
        if (movie?.tagLine == null)
            tv_orgin.text = movie?.title
        else
            tv_orgin.text = movie.tagLine
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}