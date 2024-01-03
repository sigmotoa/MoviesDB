package com.sigmotoa.reto1.model

import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * Created by sigmotoa on 21/12/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */
interface TheMovieDbService {

    @GET("movie/popular")
    suspend fun listPopularMovies(@Query("api_key") apiKey: String,
                                  @Query("region") region: String): MoviePopularResult
}