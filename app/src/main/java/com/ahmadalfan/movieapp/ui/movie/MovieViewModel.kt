package com.ahmadalfan.movieapp.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ahmadalfan.movieapp.data.model.ResultMovie
import com.ahmadalfan.movieapp.data.network.NetworkState
import com.ahmadalfan.movieapp.data.repositories.MovieRepositories
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(
    private val repositories: MovieRepositories
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun getUpComingPageList(): LiveData<PagedList<ResultMovie>> {
        return repositories.fetchupComingList(
            compositeDisposable
        )
    }

    fun getMoviePopularPageList(): LiveData<PagedList<ResultMovie>> {
        return repositories.fetchpopularMovieList(
            compositeDisposable
        )
    }


    fun networkStateTransaction(): LiveData<NetworkState> {
        return repositories.getNetworkState()
    }

    fun networkStateTransaction01(): LiveData<NetworkState> {
        return repositories.getNetworkState1()
    }

}