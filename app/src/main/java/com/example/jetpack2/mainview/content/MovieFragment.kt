package com.example.jetpack2.mainview.content

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpack2.R
import com.example.jetpack2.adapter.MovieAdapter
import com.example.jetpack2.data.model.FilmList
import com.example.jetpack2.utils.Helper.TYPE_MOVIE
import com.example.jetpack2.viewmodel.FilmViewModel
import com.example.jetpack2.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), MovieAdapter.OnItemClickCallback {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)

        val movieAdapter =
            MovieAdapter(context)

        movieAdapter.setOnItemClickCallback(this)
        movieViewModel?.movie?.observe(viewLifecycleOwner, {
            showLoading(false)
            movieAdapter.addList(it)
        })

        rv_movie.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pbMovie.visibility = View.VISIBLE
        } else {
            pbMovie.visibility = View.GONE
        }
    }

    private val movieViewModel by lazy {
        val viewModelFactory = ViewModelFactory.getInstance()
        viewModelFactory?.let { ViewModelProvider(this, it).get(FilmViewModel::class.java) }
    }

    override fun onItemClicked(data: FilmList) {
        startActivity(
            Intent(context, DetailActivity::class.java)
                .putExtra(TYPE_MOVIE, data.id.toString())
        )
    }

}