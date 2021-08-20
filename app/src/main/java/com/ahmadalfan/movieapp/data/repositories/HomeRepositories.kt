package com.ahmadalfan.movieapp.data.repositories

import com.ahmadalfan.movieapp.data.network.ApiService
import com.ahmadalfan.movieapp.data.network.SafeApiRequest
import com.ahmadalfan.movieapp.data.responses.MovieResponse
import com.ahmadalfan.movieapp.data.responses.TvResponse

class HomeRepositories(
    private val apiService: ApiService,
) : SafeApiRequest() {

    suspend fun getTvToday(page: Int?): retrofit2.Response<TvResponse> {
        return apiRequest {
            apiService.getHomeTvairingToday("6e63c2317fbe963d76c3bdc2b785f6d1", page)
        }
    }

    suspend fun getTvPopulaAtHome(page: Int?): retrofit2.Response<TvResponse> {
        return apiRequest {
            apiService.getTvPopularAthome("6e63c2317fbe963d76c3bdc2b785f6d1", page)
        }
    }

    suspend fun getMoviePopularAtHome(page: Int?): retrofit2.Response<MovieResponse> {
        return apiRequest {
            apiService.getMoviePopularAtHome("6e63c2317fbe963d76c3bdc2b785f6d1", page)
        }
    }
    suspend fun getMovieUpcamingAtHome(page: Int?): retrofit2.Response<MovieResponse> {
        return apiRequest {
            apiService.getMovieUpcomingAtHome("6e63c2317fbe963d76c3bdc2b785f6d1", page)
        }
    }


}