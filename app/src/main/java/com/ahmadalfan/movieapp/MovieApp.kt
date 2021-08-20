package com.ahmadalfan.movieapp

import android.app.Application
import com.ahmadalfan.movieapp.data.network.ApiService
import com.ahmadalfan.movieapp.data.network.NetworkConnectionInterceptor
import com.ahmadalfan.movieapp.data.repositories.HomeRepositories
import com.ahmadalfan.movieapp.data.repositories.MovieRepositories
import com.ahmadalfan.movieapp.data.repositories.TvRepositories
import com.ahmadalfan.movieapp.ui.home.HomeViewModelFatory
import com.ahmadalfan.movieapp.ui.tv.TvViewModelFactory
import com.ahmadalfan.movieapp.ui.movie.MovieViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MovieApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MovieApp))


        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiService(instance()) }
        bind() from singleton { MovieRepositories(instance()) }
        bind() from singleton { TvRepositories(instance()) }
        bind() from singleton { HomeRepositories(instance()) }

        bind() from provider { MovieViewModelFactory(instance()) }
        bind() from provider { TvViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFatory(instance()) }


    }
}