package com.ahmadalfan.movieapp.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmadalfan.movieapp.data.repositories.MovieRepositories

class MovieViewModelFactory(
    private val repositories: MovieRepositories
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(repositories) as T
    }
}