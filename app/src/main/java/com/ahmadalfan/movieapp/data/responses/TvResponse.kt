package com.ahmadalfan.movieapp.data.responses

 import com.ahmadalfan.movieapp.data.model.TvResult

data class TvResponse(
    val dates: Date?,
    val page: Int?,
    val results: List<TvResult>?,
    val total_pages: Int?,
    val total_results: Int?,
)