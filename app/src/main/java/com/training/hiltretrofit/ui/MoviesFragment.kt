package com.training.hiltretrofit.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.hiltretrofit.R
import com.training.hiltretrofit.adapter.MoviesAdapter
import com.training.hiltretrofit.api.ApiService
import com.training.hiltretrofit.databinding.FragmentMoviesBinding
import com.training.hiltretrofit.repository.moviesRepository
import com.training.hiltretrofit.response.MoviesListResponse
import com.training.hiltretrofit.utils.Constants
import com.training.hiltretrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var binding:FragmentMoviesBinding


    val moviesViewModel by viewModels<MoviesViewModel>()

    @Inject
    lateinit var moviesAdapter : MoviesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMoviesRv()

        lifecycleScope.launchWhenStarted {
            moviesViewModel.moviesPager.collectLatest {
                moviesAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            moviesAdapter.loadStateFlow.collectLatest {
                val state = it.refresh
                binding.prgBarMovies.isVisible = state is LoadState.Loading
            }
        }
    }

    private fun initMoviesRv() {
        binding.rlMovies.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = moviesAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        moviesAdapter.setOnItemClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id!!)
            findNavController().navigate(action)
        }
    }
}