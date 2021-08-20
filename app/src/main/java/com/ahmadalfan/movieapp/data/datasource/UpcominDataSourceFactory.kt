package com.ahmadalfan.movieapp.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ahmadalfan.movieapp.data.model.ResultMovie
import com.ahmadalfan.movieapp.data.network.ApiService
import io.reactivex.disposables.CompositeDisposable

class UpcominDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,

    ) : DataSource.Factory<Int, ResultMovie>() {
    val upcamingLiveDataSource = MutableLiveData<UpcamingDatasource>()

    override fun create(): DataSource<Int, ResultMovie> {
        val upcamingDatasource = UpcamingDatasource(
            apiService,
            compositeDisposable
        )

        upcamingLiveDataSource.postValue(upcamingDatasource)
        return upcamingDatasource
    }
}