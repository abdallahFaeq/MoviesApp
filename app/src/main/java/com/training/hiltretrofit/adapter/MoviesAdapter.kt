package com.training.hiltretrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import com.training.hiltretrofit.R
import com.training.hiltretrofit.databinding.ItemMoviesBinding
import com.training.hiltretrofit.response.MoviesListResponse
import com.training.hiltretrofit.utils.Constants.POSTER_BASE_URL
import javax.inject.Inject

class MoviesAdapter @Inject constructor()
    :PagingDataAdapter<MoviesListResponse.ResultsItem,MoviesAdapter.MoviesHolder>(diffUtil) {

    inner class MoviesHolder(private var binding: ItemMoviesBinding) : ViewHolder(binding.root) {
        fun bind(item: MoviesListResponse.ResultsItem) {
            binding.apply {
                tvMovieName.text = item.title
                tvMovieDateRelease.text = item.releaseDate
                tvRate.text = item.voteAverage.toString()
                val moviePosterURL = POSTER_BASE_URL + item?.posterPath
                imgMovie.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                tvLang.text = item.originalLanguage

                root.setOnClickListener{
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

        private var context: Context? = null

        companion object{
            private var diffUtil = object : DiffUtil.ItemCallback<MoviesListResponse.ResultsItem>() {
                override fun areItemsTheSame(
                    oldItem: MoviesListResponse.ResultsItem,
                    newItem: MoviesListResponse.ResultsItem
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: MoviesListResponse.ResultsItem,
                    newItem: MoviesListResponse.ResultsItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
            context = parent.context
            return MoviesHolder(
                ItemMoviesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
            holder.bind(getItem(position)!!)
            holder.setIsRecyclable(false)
        }


    private var onItemClickListener: ((MoviesListResponse.ResultsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (MoviesListResponse.ResultsItem) -> Unit) {
        onItemClickListener = listener
    }

}