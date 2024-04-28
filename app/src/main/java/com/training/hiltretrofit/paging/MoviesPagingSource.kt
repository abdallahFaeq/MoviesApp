package com.training.hiltretrofit.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.training.hiltretrofit.repository.moviesRepository
import com.training.hiltretrofit.response.MoviesListResponse
import com.training.hiltretrofit.utils.Constants
import retrofit2.HttpException

class MoviesPagingSource(
    private val repository: moviesRepository
):PagingSource<Int,MoviesListResponse.ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesListResponse.ResultsItem> {
        return try {
            var currentPage = params.key ?: 1
            var response = repository.getMovies(Constants.api_key,currentPage)
            var data = response.body()!!.results as MutableList<MoviesListResponse.ResultsItem>

            var responseData = mutableListOf<MoviesListResponse.ResultsItem>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        }catch (e:Exception){
            LoadResult.Error(e)
        }catch (httpE:HttpException){
            LoadResult.Error(httpE)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoviesListResponse.ResultsItem>): Int? {
        return null
    }
}