package com.training.hiltretrofit.api

import com.training.hiltretrofit.response.MovieDetailsResponse
import com.training.hiltretrofit.response.MoviesListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMoviesList(@Query("api_key") apiKey :String,
        @Query("page") page:Int):Response<MoviesListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id:Int,
        @Query("api_key") apiKey:String):Response<MovieDetailsResponse>
}