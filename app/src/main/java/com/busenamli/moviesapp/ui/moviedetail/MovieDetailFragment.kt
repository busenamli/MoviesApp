package com.busenamli.moviesapp.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.busenamli.moviesapp.databinding.FragmentMovieDetailBinding
import com.busenamli.moviesapp.util.changeVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding?= null
    private val binding get() = _binding!!
    val movieDetailArg: MovieDetailFragmentArgs by navArgs()
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = movieDetailArg.movieId
        observeData(movieId)
    }

    private fun observeData(movieId: Int){

        viewLifecycleOwner.lifecycleScope.launch {
            movieDetailViewModel.fetchMovieDetail(movieId)
            movieDetailViewModel.uiState.collectLatest { movieDetailUiState ->

                if (movieDetailUiState.isLoading == true) {
                    binding.movieDetailErrorText.changeVisibility(false)
                    binding.movieDetailLinear.changeVisibility(false)
                    binding.movieDetailProgressBar.changeVisibility(true)

                } else if (movieDetailUiState.movie != null) {
                    binding.movieDetailProgressBar.changeVisibility(false)
                    binding.movieDetailErrorText.changeVisibility(false)
                    binding.movieDetailLinear.changeVisibility(true)
                    println(movieDetailUiState.movie)
                    val model = movieDetailUiState.movie
                    binding.movieDetail = model
                    //Genre basıldığında liste açılacak
                    val genreList = arrayListOf<String>()
                    for (i in 0 until model.genres.size - 1) {
                        genreList.add(model.genres.get(i).name)
                    }
                    binding.movieDetailGenre.text = genreList.joinToString(", ")

                } else {
                    binding.movieDetailProgressBar.changeVisibility(false)
                    binding.movieDetailLinear.changeVisibility(false)
                    binding.movieDetailErrorText.changeVisibility(true)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}