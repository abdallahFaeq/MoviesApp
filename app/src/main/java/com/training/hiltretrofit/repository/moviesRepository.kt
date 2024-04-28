package com.training.hiltretrofit.repository

import com.training.hiltretrofit.api.ApiService
import javax.inject.Inject

class moviesRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getMovies(apiKey:String,page:Int) = apiService.getMoviesList(apiKey,page)
}