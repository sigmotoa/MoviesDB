package com.sigmotoa.reto1.model

data class MoviePopularResult(
    val page: Int,
    val results: List<MovieDbResult>,
    val total_pages: Int,
    val total_results: Int
)