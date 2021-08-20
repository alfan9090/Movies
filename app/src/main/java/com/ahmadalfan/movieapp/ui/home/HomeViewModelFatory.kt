package com.ahmadalfan.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmadalfan.movieapp.data.repositories.HomeRepositories

class HomeViewModelFatory(
    private val repositories: HomeRepositories
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repositories) as T
    }
}