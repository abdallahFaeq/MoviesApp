package com.training.hiltretrofit.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.training.hiltretrofit.R
import com.training.hiltretrofit.databinding.FragmentMovieDetailsBinding
import com.training.hiltretrofit.repository.movieDetailsRepository
import com.training.hiltretrofit.response.MovieDetailsResponse
import com.training.hiltretrofit.utils.Constants
import com.training.hiltretrofit.utils.Constants.POSTER_BASE_URL
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

    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.id != null) {
            val id = args.id

            movieDetailsRepository.getMovieDetails(id, Constants.api_key)
                .enqueue(object : Callback<MovieDetailsResponse> {
                    override fun onResponse(
                        p0: Call<MovieDetailsResponse>,
                        response: Response<MovieDetailsResponse>
                    ) {
                        binding.prgBarMovies.visibility = View.VISIBLE
                        if (response.isSuccessful) {
                            when (response.code()) {
                                in 200..299 -> {
                                    binding.prgBarMovies.visibility = View.GONE
                                    response.body()?.let { itBody ->
                                        updateData(itBody)
                                    }
                                }
                                in 300..399 -> {
                                    Log.d(
                                        "Response Code",
                                        " Redirection messages : ${response.code()}"
                                    )
                                }
                                in 400..499 -> {
                                    Log.d(
                                        "Response Code",
                                        " Client error responses : ${response.code()}"
                                    )
                                }
                                in 500..599 -> {
                                    Log.d(
                                        "Response Code",
                                        " Server error responses : ${response.code()}"
                                    )
                                }
                            }
                        }
                    }

                    override fun onFailure(p0: Call<MovieDetailsResponse>, p1: Throwable) {

                    }

                })
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