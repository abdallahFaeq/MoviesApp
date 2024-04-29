package com.training.hiltretrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.training.hiltretrofit.databinding.LoadMoreBinding

class LoadMovieAdapter(private val retry : ()->Unit) :LoadStateAdapter<LoadMovieAdapter.LoadMovieHolder>() {
    private lateinit var binding : LoadMoreBinding

    override fun onBindViewHolder(holder: LoadMovieHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadMovieHolder {
        binding = LoadMoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadMovieHolder(retry)
    }

    inner class LoadMovieHolder(retry: () -> Unit) : ViewHolder(binding.root){
        init {
            binding.btnLoadMoreRetry.setOnClickListener{
                retry()
            }
        }

        fun setData(state:LoadState){
            binding.apply {
                btnLoadMoreRetry.isVisible = state is LoadState.Error
                tvLoadMore.isVisible = state is LoadState.Error
                prgBarLoadMore.isVisible = state is LoadState.Loading
            }
        }
    }
}