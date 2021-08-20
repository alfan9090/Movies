package com.ahmadalfan.movieapp.data.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ahmadalfan.movieapp.data.model.ResultMovie
import com.ahmadalfan.movieapp.data.network.ApiService
import com.ahmadalfan.movieapp.data.network.NetworkState
import com.ahmadalfan.movieapp.utils.PAGE
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopularMovieDatasource(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
) : PageKeyedDataSource<Int, ResultMovie>() {
    private var page = PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultMovie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getMoviePopupar(
                "6e63c2317fbe963d76c3bdc2b785f6d1",
                1
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        val resp = it.results
                        callback.onResult(resp!!, null, page + 1)
                        if (resp.isNotEmpty()) {
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.NODATA)
                        }
                    },
                    {
                        when (it.message!!.trim()) {
                            "Make sure you have an active data connection" -> {
                                networkState.postValue(NetworkState.NOINTERNET)
                            }
                            else -> {
                                networkState.postValue(NetworkState.ERROR)
                            }
                        }
                        Log.e("FoodMeterDataSource", it.message!!)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getMoviePopupar(
                "6e63c2317fbe963d76c3bdc2b785f6d1",
                params.key
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.total_pages!! >= params.key) {
                            val resp = it.results
                            callback.onResult(resp!!, params.key + 1)
                            if (resp.isNotEmpty()) {
                                networkState.postValue(NetworkState.LOADED)
                            } else {
                                networkState.postValue(NetworkState.NODATA)
                            }
                        } else {
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        when (it.message!!.trim()) {
                            "Make sure you have an active data connection" -> {
                                networkState.postValue(NetworkState.NOINTERNET)
                            }
                            else -> {
                                networkState.postValue(NetworkState.ERROR)
                            }
                        }
                        Log.e("Upcaming DataSource", it.message!!)
                    }
                )
        )
    }
}