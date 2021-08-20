package com.ahmadalfan.movieapp.data.responses

import com.ahmadalfan.movieapp.data.model.ResultMovie

data class MovieResponse(
    val page: Int?,
    val results: List<ResultMovie>?,
    val total_pages: Int?,
    val total_results: Int?,
)

data class Date(
    val maximum: String?,
    val minimum: String?
)