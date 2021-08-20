package com.ahmadalfan.movieapp.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ahmadalfan.movieapp.data.model.TvResult
import com.ahmadalfan.movieapp.data.network.ApiService
import io.reactivex.disposables.CompositeDisposable

class TvairingTodayDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
) : DataSource.Factory<Int, TvResult>() {
    val tvairingTodayLiveDataSource = MutableLiveData<TvairingTodayDataSource>()

    override fun create(): DataSource<Int, TvResult> {
        val tvairingDatasource = TvairingTodayDataSource(
            apiService,
            compositeDisposable
        )
        tvairingTodayLiveDataSource.postValue(tvairingDatasource)
        return tvairingDatasource
    }
}