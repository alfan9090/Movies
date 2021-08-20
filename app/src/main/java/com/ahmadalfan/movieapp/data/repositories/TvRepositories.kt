package com.ahmadalfan.movieapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ahmadalfan.movieapp.data.datasource.*
import com.ahmadalfan.movieapp.data.model.TvResult
import com.ahmadalfan.movieapp.data.network.ApiService
import com.ahmadalfan.movieapp.data.network.NetworkState
import com.ahmadalfan.movieapp.data.network.SafeApiRequest
import com.ahmadalfan.movieapp.data.responses.TvResponse
import com.ahmadalfan.movieapp.utils.total_pages
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Response

class TvRepositories(
    private val apiService: ApiService,
) : SafeApiRequest() {
    private lateinit var
            tvairingMoviePagedList: LiveData<PagedList<TvResult>>
    private lateinit var tvairingTodayDataSourceFactory: TvairingTodayDataSourceFactory


    private lateinit var
            tvPopularPagedList: LiveData<PagedList<TvResult>>
    private lateinit var tvPopularDataSourceFactory: TvPopularDataSourceFactory


    fun fetchtvArivingTodayList(
        compositeDisposable: CompositeDisposable,
    ): LiveData<PagedList<TvResult>> {
        tvairingTodayDataSourceFactory = TvairingTodayDataSourceFactory(
            apiService,
            compositeDisposable,

            )
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(total_pages)
            .build()

        tvairingMoviePagedList =
            LivePagedListBuilder(tvairingTodayDataSourceFactory, config).build()
        return tvairingMoviePagedList
    }

    fun fetchTvPopularList(
        compositeDisposable: CompositeDisposable,
    ): LiveData<PagedList<TvResult>> {
        tvPopularDataSourceFactory = TvPopularDataSourceFactory(
            apiService,
            compositeDisposable,

            )
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(total_pages)
            .build()

        tvPopularPagedList =
            LivePagedListBuilder(tvPopularDataSourceFactory, config).build()
        return tvPopularPagedList
    }


    fun getNetworkState1(): LiveData<NetworkState> {
        return Transformations.switchMap(
            tvairingTodayDataSourceFactory.tvairingTodayLiveDataSource,
            TvairingTodayDataSource::networkState

        )
    }

    fun getNetworkState2(): LiveData<NetworkState> {
        return Transformations.switchMap(
            tvPopularDataSourceFactory.tvPopularLiveDataSource,
            TvPopularDataSource::networkState

        )
    }

}