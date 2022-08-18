package com.busenamli.moviesapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.busenamli.moviesapp.data.model.MovieDetail
import com.busenamli.moviesapp.databinding.FragmentMovieDetailBinding
import com.busenamli.moviesapp.ui.adapter.MovieDetailCreditRecyclerViewAdapter
import com.busenamli.moviesapp.ui.uistate.ScrollState
import com.busenamli.moviesapp.ui.viewmodel.MovieDetailViewModel
import com.busenamli.moviesapp.util.changeVisibility
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val movieDetailArg: MovieDetailFragmentArgs by navArgs()
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var creditAdapter: MovieDetailCreditRecyclerViewAdapter
    private var indexItem = 0
    private var topView = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = movieDetailArg.movieId
        binding.movieDetailCastRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        observeData(movieId)
    }

    private fun observeData(movieId: Int) {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewModel.fetchMovieDetail(movieId)

                movieDetailViewModel.uiState.collectLatest { movieDetailUiState ->

                    movieDetailUiState.errorMessage?.let { messages ->
                        if (messages.isNotEmpty()) {
                            val message = messages[messages.size - 1]
                            Toast.makeText(context, message.message, Toast.LENGTH_SHORT).show()
                            movieDetailViewModel.errorMessageShown(message)
                        }
                    }

                    if (movieDetailUiState.isLoading == true) {
                        binding.movieDetailProgressBar.changeVisibility(true)
                        binding.movieLinear.changeVisibility(false)

                    } else if (movieDetailUiState.movie != null && movieDetailUiState.cast != null) {
                        binding.movieDetailProgressBar.changeVisibility(false)
                        binding.movieLinear.changeVisibility(true)
                        val model = movieDetailUiState.movie
                        binding.movieDetail = model
                        setChips(model)
                        binding.movieDetailCastRecyclerview.changeVisibility(true)
                        creditAdapter =
                            MovieDetailCreditRecyclerViewAdapter(movieDetailUiState.cast)
                        binding.movieDetailCastRecyclerview.adapter = creditAdapter

                        movieDetailViewModel.uiState.value.pageScrollState?.let { position ->
                            position.positionX?.let {
                                binding.movieDetailNestedScrollView.scrollX = it
                            }
                            position.positionY?.let {
                                binding.movieDetailNestedScrollView.scrollY = it
                            }
                        }

                        movieDetailViewModel.uiState.value.creditListScrollState?.let { position ->
                            position?.let { scrollToPosition(it) }
                        }

                    } else if (movieDetailUiState.isError == true) {
                        binding.movieDetailProgressBar.changeVisibility(false)
                        binding.movieLinear.changeVisibility(false)
                    }
                }
            }
        }
    }

    private fun setChips(model: MovieDetail) {
        binding.movieDetailChipGroup.removeAllViews()
        for (i in 0 until model.genres.size - 1) {
            val chip = Chip(context)
            chip.id = model.genres[i].id
            chip.text = model.genres[i].name

            chip.setOnClickListener {
                val genreText = chip.text
                val genreId = chip.id

                movieDetailViewModel.genreSelected(genreId)
                val action =
                    MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieListByGenreFragment(
                        genreId,
                        genreText.toString()
                    )
                Navigation.findNavController(view!!).navigate(action)
            }
            binding.movieDetailChipGroup.addView(chip)
        }
    }

    private fun setScrollState() {
        val pageScrollState = ScrollState(
            binding.movieDetailNestedScrollView.scrollX,
            binding.movieDetailNestedScrollView.scrollY
        )
        movieDetailViewModel.savePageScrollState(pageScrollState)

        val lastFirstVisiblePosition =
            (binding.movieDetailCastRecyclerview.layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()
        lastFirstVisiblePosition?.let { movieDetailViewModel.saveCreditListScrollState(it) }
    }

    private fun scrollToPosition(positon: Int) {
        (binding.movieDetailCastRecyclerview.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
            positon,
            0
        )
    }

    override fun onStop() {
        super.onStop()
        setScrollState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setScrollState()
        _binding = null
    }
}