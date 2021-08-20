package com.ahmadalfan.movieapp.data.network

import androidx.databinding.library.BuildConfig
import com.ahmadalfan.movieapp.data.responses.MovieResponse
import com.ahmadalfan.movieapp.data.responses.TvResponse
import com.ahmadalfan.movieapp.utils.BASE_URL
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("movie/upcoming/")
    fun getMoviUpcoming(
        @Query("api_key") api_key: String?,
        @Query("page") page: Int?,
    ): Single<MovieResponse>

    @GET("movie/popular/")
    fun getMoviePopupar(
        @Query("api_key") api_key: String?,
        @Query("page") page: Int?,
    ): Single<MovieResponse>

    @GET("tv/airing_today/")
    fun getTvairingToday(
        @Query("api_key") api_key: String?,
        @Query("page") page: Int?,
    ): Single<TvResponse>

    @GET("tv/popular/")
    fun getTvPopular(
        @Query("api_key") api_key: String?,
        @Query("page") page: Int?,
    ): Single<TvResponse>


    @GET("tv/airing_today/")
    suspend fun getHomeTvairingToday(
        @Query("api_key") api_key: String?,
        @Query("page") page: Int?,
    ): Response<TvResponse>


    @GET("movie/popular/")
    suspend fun getMoviePopularAtHome(
        @Query("api_key") api_key: String?,
        @Query("page") page: Int?,
    ): Response<MovieResponse>

    @GET("movie/upcoming/")
    suspend fun getMovieUpcomingAtHome(
        @Query("api_key") api_key: String?,
        @Query("page") page: Int?,
    ): Response<MovieResponse>

    @GET("tv/popular/")
    suspend fun getTvPopularAthome(
        @Query("api_key") api_key: String?,
        @Query("page") page: Int?,
    ): Response<TvResponse>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): ApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BODY
                })
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}