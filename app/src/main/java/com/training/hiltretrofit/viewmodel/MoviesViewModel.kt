package com.training.hiltretrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.training.hiltretrofit.paging.MoviesPagingSource
import com.training.hiltretrofit.repository.movieDetailsRepository
import com.training.hiltretrofit.repository.moviesRepository
import com.training.hiltretrofit.response.MovieDetailsResponse
import com.training.hiltretrofit.utils.Constants
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: moviesRepository,
    private val movieDetailsRepository: movieDetailsRepository
):
    ViewModel(){

        private var loading = MutableLiveData<Boolean>()
        val _loading = loading

        var moviesPager = Pager(PagingConfig(10)){
            MoviesPagingSource(moviesRepository)
        }.flow.cachedIn(viewModelScope)


    //Api
    val detailsMovie = MutableLiveData<MovieDetailsResponse>()
    fun loadDetailsMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = movieDetailsRepository.getMovieDetails(id, Constants.api_key)
        if (response.isSuccessful) {
            detailsMovie.postValue(response.body())
            loading.postValue(false)
        }
    }
}