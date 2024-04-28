package com.training.hiltretrofit.repository

import com.training.hiltretrofit.api.ApiService
import javax.inject.Inject


class movieDetailsRepository @Inject constructor(
    private val apiService: ApiService
){
    fun getMovieDetails(id:Int,apiKey:String) = apiService.getMovieDetails(id,apiKey)
}