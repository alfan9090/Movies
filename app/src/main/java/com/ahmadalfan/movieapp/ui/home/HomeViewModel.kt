package com.ahmadalfan.movieapp.ui.home

import androidx.lifecycle.ViewModel
import com.ahmadalfan.movieapp.data.repositories.HomeRepositories

class HomeViewModel(
    private val repositories: HomeRepositories
) : ViewModel() {

    suspend fun getTvToday(
        page: Int?,
    ) = repositories.getTvToday(page)

    suspend fun getMoviePopularAtHome(
        page: Int?,
    ) = repositories.getMoviePopularAtHome(page)

    suspend fun getMovieUpcamingAtHome(
        page: Int?,
    ) = repositories.getMovieUpcamingAtHome(page)


    suspend fun getTvPopulaAtHome(
        page: Int?,
    ) = repositories.getTvPopulaAtHome(page)
}