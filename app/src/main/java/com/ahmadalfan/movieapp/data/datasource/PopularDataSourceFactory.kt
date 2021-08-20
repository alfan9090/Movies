package com.ahmadalfan.movieapp.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ahmadalfan.movieapp.data.model.ResultMovie
import com.ahmadalfan.movieapp.data.network.ApiService
import io.reactivex.disposables.CompositeDisposable

class PopularDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
) : DataSource.Factory<Int, ResultMovie>() {
    val popularMovieLiveDataSource = MutableLiveData<PopularMovieDatasource>()

    override fun create(): DataSource<Int, ResultMovie> {
        val popularMovieDatasource = PopularMovieDatasource(
            apiService,
            compositeDisposable
        )

        popularMovieLiveDataSource.postValue(popularMovieDatasource)
        return popularMovieDatasource
    }
}