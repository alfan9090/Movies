package com.ahmadalfan.movieapp.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ahmadalfan.movieapp.data.model.ResultMovie
import com.ahmadalfan.movieapp.data.model.TvResult
import com.ahmadalfan.movieapp.data.network.NetworkState
import com.ahmadalfan.movieapp.data.repositories.MovieRepositories
import com.ahmadalfan.movieapp.data.repositories.TvRepositories
import io.reactivex.disposables.CompositeDisposable

class TvViewModel(
    private val repositories: TvRepositories

) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun getTvairivingTodayPageList(): LiveData<PagedList<TvResult>> {
        return repositories.fetchtvArivingTodayList(
            compositeDisposable
        )
    }

    fun networkStateTransaction(): LiveData<NetworkState> {
        return repositories.getNetworkState1()
    }

    fun getTvPopularPageList(): LiveData<PagedList<TvResult>> {
        return repositories.fetchTvPopularList(
            compositeDisposable
        )
    }

    fun networkStateTransaction2(): LiveData<NetworkState> {
        return repositories.getNetworkState2()
    }


}