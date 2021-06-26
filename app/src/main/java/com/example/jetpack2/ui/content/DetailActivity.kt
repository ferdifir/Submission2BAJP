package com.example.jetpack2.mainview.content

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jetpack2.R
import com.example.jetpack2.data.model.local.entity.MovieEntity
import com.example.jetpack2.data.model.local.entity.TvShowEntity
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
        val movie = intent.getIntExtra(TYPE_MOVIE, 0)
        val tvshow = intent.getIntExtra(TYPE_TVSHOW, 0)

        val movieDetailViewModel by lazy {
            val viewModelFactory = ViewModelFactory.getInstance(application)
            viewModelFactory?.let { ViewModelProvider(this, it).get(FilmViewModel::class.java) }
        }
        movieDetailViewModel?.getMovieDetail(movie)?.observe(this, {
            showLoading(false)
            loadDataMovie(it)
        })

        val tvShowDetailViewModel by lazy {
            val viewModelFactory = ViewModelFactory.getInstance(application)
            viewModelFactory?.let { ViewModelProvider(this, it).get(FilmViewModel::class.java) }
        }
        tvShowDetailViewModel?.getTvShowDetail(tvshow)?.observe(this, {
            showLoading(false)
            loadDataTvShow(it)
        })
    }

    private fun loadDataTvShow(tvshow: TvShowEntity?) {
        if (tvshow != null) {
            Glide.with(this)
                .load(applicationContext.resources
                    .getIdentifier(tvshow
                        .tv_show_poster,
                        "drawable",
                        applicationContext.packageName))
                .into(img_item_photo)
        }
        tv_title.text = tvshow?.tv_show_title
        tv_desc.text = tvshow?.tv_show_description
        tv_realase_date.text = tvshow?.tv_show_release
        tv_orgin.text = tvshow?.tv_show_genre
    }

    private fun loadDataMovie(movie: MovieEntity?) {
        if (movie != null) {
            Glide.with(this)
                .load(applicationContext.resources
                    .getIdentifier(movie
                        .movie_poster,
                        "drawable",
                        applicationContext.packageName))
                .into(img_item_photo)
        }
        tv_title.text = movie?.movie_title
        tv_desc.text = movie?.movie_description
        tv_realase_date.text = movie?.movie_release
        tv_orgin.text = movie?.movie_genre
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}