package com.busenamli.moviesapp.ui.movielist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.busenamli.moviesapp.databinding.FragmentMovieListBinding
import com.busenamli.moviesapp.adapter.MovieListRecyclerViewAdapter
import com.busenamli.moviesapp.util.changeVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding ?= null
    private val binding get() = _binding!!
    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var pagingAdapter: MovieListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagingAdapter = MovieListRecyclerViewAdapter(MovieListRecyclerViewAdapter.MoviesComparator)

        val screenDp = activity!!.resources.configuration.screenWidthDp
        binding.moviesRecyclerview.layoutManager = when{
            screenDp <= 600f -> GridLayoutManager(context,2)
            screenDp <= 840f -> GridLayoutManager(context,4)
            else -> GridLayoutManager(context,6)
        }
        binding.moviesRecyclerview.adapter = pagingAdapter
        observeData()
    }

    private fun observeData(){
        viewLifecycleOwner.lifecycleScope.launch {

            movieListViewModel.uiState.collectLatest { movieUiState->
                if (movieUiState.isLoading == true) {
                    binding.moviesErrorText.changeVisibility(false)
                    binding.moviesRecyclerview.changeVisibility(false)
                    binding.moviesProgressBar.changeVisibility(true)

                }else if(movieUiState.movieList != null) {
                    binding.moviesProgressBar.changeVisibility(false)
                    binding.moviesErrorText.changeVisibility(false)
                    binding.moviesRecyclerview.changeVisibility(true)
                    pagingAdapter.submitData(movieUiState.movieList)

                }else {
                    binding.moviesErrorText.changeVisibility(true)
                    binding.moviesRecyclerview.changeVisibility(false)
                    binding.moviesProgressBar.changeVisibility(false)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}