package com.ahmadalfan.movieapp.ui.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmadalfan.movieapp.data.repositories.TvRepositories

class TvViewModelFactory(
    private val repositories: TvRepositories
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TvViewModel(repositories) as T
    }
}