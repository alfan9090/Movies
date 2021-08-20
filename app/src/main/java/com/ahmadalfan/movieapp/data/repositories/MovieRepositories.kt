package com.ahmadalfan.movieapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ahmadalfan.movieapp.data.datasource.PopularDataSourceFactory
import com.ahmadalfan.movieapp.data.datasource.PopularMovieDatasource
import com.ahmadalfan.movieapp.data.datasource.UpcamingDatasource
import com.ahmadalfan.movieapp.data.datasource.UpcominDataSourceFactory
import com.ahmadalfan.movieapp.data.model.ResultMovie
import com.ahmadalfan.movieapp.data.network.ApiService
import com.ahmadalfan.movieapp.data.network.NetworkState
import com.ahmadalfan.movieapp.data.network.SafeApiRequest
import com.ahmadalfan.movieapp.data.responses.TvResponse
import com.ahmadalfan.movieapp.utils.total_pages
import io.reactivex.disposables.CompositeDisposable

class MovieRepositories(
    private val apiService: ApiService,
) : SafeApiRequest() {
    private lateinit var upcomingPagedList: LiveData<PagedList<ResultMovie>>
    private lateinit var upComingDataSourceFactory: UpcominDataSourceFactory

    private lateinit var popularMoviePagedList: LiveData<PagedList<ResultMovie>>
    private lateinit var popularMovieDataSourceFactory: PopularDataSourceFactory


    fun fetchupComingList(
        compositeDisposable: CompositeDisposable,
    ): LiveData<PagedList<ResultMovie>> {
        upComingDataSourceFactory = UpcominDataSourceFactory(
            apiService,
            compositeDisposable,

            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(total_pages)
            .build()

        upcomingPagedList =
            LivePagedListBuilder(upComingDataSourceFactory, config).build()
        return upcomingPagedList
    }

    fun fetchpopularMovieList(
        compositeDisposable: CompositeDisposable,
    ): LiveData<PagedList<ResultMovie>> {
        popularMovieDataSourceFactory = PopularDataSourceFactory(
            apiService,
            compositeDisposable,
        )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(total_pages)
            .build()

        popularMoviePagedList =
            LivePagedListBuilder(popularMovieDataSourceFactory, config).build()
        return popularMoviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            upComingDataSourceFactory.upcamingLiveDataSource,
            UpcamingDatasource::networkState

        )
    }

    fun getNetworkState1(): LiveData<NetworkState> {
        return Transformations.switchMap(
            popularMovieDataSourceFactory.popularMovieLiveDataSource,
            PopularMovieDatasource::networkState

        )
    }



}
