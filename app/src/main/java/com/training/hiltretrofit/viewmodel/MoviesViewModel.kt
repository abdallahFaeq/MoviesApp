package com.training.hiltretrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.training.hiltretrofit.paging.MoviesPagingSource
import com.training.hiltretrofit.repository.moviesRepository
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: moviesRepository
):
    ViewModel(){

        private var loading = MutableLiveData<Boolean>()
        val _loading = loading

        var moviesPager = Pager(PagingConfig(10)){
            MoviesPagingSource(moviesRepository)
        }.flow.cachedIn(viewModelScope)
}