package com.example.jetpack2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpack2.data.repository.FilmRepository
import com.example.jetpack2.utils.Injection

class ViewModelFactory(private val filmRepository: FilmRepository) : ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory? {
            if (INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    if (INSTANCE == null)
                        INSTANCE = Injection.provideRepository()?.let { ViewModelFactory(it) }
                }
            }
            return INSTANCE
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(model: Class<T>): T {
        return when{
            model.isAssignableFrom(FilmViewModel::class.java) -> FilmViewModel(filmRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: " + model.name)
        }
    }

}