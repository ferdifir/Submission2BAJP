package com.example.jetpack2.utils

import com.example.jetpack2.data.repository.FilmRepository
import com.example.jetpack2.data.repository.LocalRepository
import com.example.jetpack2.data.repository.RemoteRepository
import com.example.jetpack2.retrofit.ApiService

object Injection {
    fun provideRepository(): FilmRepository? {
        val localRepository = LocalRepository()
        val remoteRepository = RemoteRepository.getInstance(ApiService)
        return FilmRepository.getInstance(localRepository, remoteRepository)
    }
}