package com.training.hiltretrofit.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.training.hiltretrofit.R
import com.training.hiltretrofit.databinding.FragmentMovieDetailsBinding
import com.training.hiltretrofit.repository.movieDetailsRepository
import com.training.hiltretrofit.response.MovieDetailsResponse
import com.training.hiltretrofit.utils.Constants
import com.training.hiltretrofit.utils.Constants.POSTER_BASE_URL
import com.training.hiltretrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding

    @Inject
    lateinit var movieDetailsRepository: movieDetailsRepository

    val detailViewModel by viewModels<MoviesViewModel>()

    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.id != null) {
            val id = args.id

            detailViewModel.loadDetailsMovie(id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.detailsMovie.observe(viewLifecycleOwner){
            updateData(it)
        }

        detailViewModel._loading.observe(viewLifecycleOwner){
            if (it == true){
                binding.prgBarMovies.visibility = View.VISIBLE
            }else{
                binding.prgBarMovies.visibility = View.GONE
            }
        }
    }

    private fun updateData(itBody: MovieDetailsResponse) {
        val moviePosterURL = POSTER_BASE_URL + itBody.posterPath

        binding.apply {

            imgMovie.load(moviePosterURL) {
                crossfade(true)
                placeholder(R.drawable.poster_placeholder)
                scale(Scale.FILL)

                imgMovieBack.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)

                    tvMovieTitle.setText(itBody.title)
                    tvMovieTagLine.setText(itBody.tagline)
                    tvMovieDateRelease.text = itBody.releaseDate
                    tvMovieRating.text = itBody.voteAverage.toString()
                    tvMovieRuntime.text = itBody.runtime.toString()
                    tvMovieBudget.text = itBody.budget.toString()
                    tvMovieRevenue.text = itBody.revenue.toString()
                    tvMovieOverview.text = itBody.overview
                }
            }
        }
    }
}