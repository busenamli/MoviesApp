package com.busenamli.moviesapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.busenamli.moviesapp.ui.adapter.GenreListRecyclerViewAdapter
import com.busenamli.moviesapp.databinding.FragmentMovieListBinding
import com.busenamli.moviesapp.ui.adapter.MovieListRecyclerViewAdapter
import com.busenamli.moviesapp.ui.viewmodel.MovieListViewModel
import com.busenamli.moviesapp.ui.uistate.Action
import com.busenamli.moviesapp.util.changeVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding ?= null
    private val binding get() = _binding!!
    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var pagingMovieListAdapter: MovieListRecyclerViewAdapter
    private lateinit var genreListAdapter: GenreListRecyclerViewAdapter

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

        pagingMovieListAdapter = MovieListRecyclerViewAdapter(Action.FromMovieList(true))
        binding.genresRecyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val screenDp = activity!!.resources.configuration.screenWidthDp
        binding.moviesRecyclerview.layoutManager = when{
            screenDp <= 600f -> GridLayoutManager(context,2)
            screenDp <= 840f -> GridLayoutManager(context,4)
            else -> GridLayoutManager(context,6)
        }
        binding.moviesRecyclerview.adapter = pagingMovieListAdapter

        observeRefresh()
        observeData()
    }

    private fun observeRefresh(){
        binding.movieListSwipeRefreshLayout.setOnRefreshListener {
            binding.moviesRecyclerview.changeVisibility(false)
            binding.genresRecyclerview.changeVisibility(false)
            binding.moviesProgressBar.changeVisibility(true)
            movieListViewModel.refreshList()
            binding.movieListSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeData(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                movieListViewModel.uiState.collectLatest { movieUiState ->

                    movieUiState.errorMessage?.let { messages ->
                        if(messages.isNotEmpty()){
                            println(messages.size)
                            val message = messages.get(messages.size-1)
                            Toast.makeText(context, message.message, Toast.LENGTH_SHORT).show()
                            movieListViewModel.errorMessageShown()
                        }
                    }

                    if (movieUiState.genreList != null) {
                        genreListAdapter = GenreListRecyclerViewAdapter(movieUiState.genreList)
                        binding.genresRecyclerview.adapter = genreListAdapter
                    }

                    if (movieUiState.isLoading == true) {
                        binding.moviesProgressBar.changeVisibility(true)
                        binding.moviesRecyclerview.changeVisibility(false)
                        binding.genresRecyclerview.changeVisibility(false)

                    } else if (movieUiState.movieList != null ) {
                        binding.moviesProgressBar.changeVisibility(false)
                        binding.moviesRecyclerview.changeVisibility(true)
                        binding.genresRecyclerview.changeVisibility(true)
                        pagingMovieListAdapter.submitData(movieUiState.movieList)

                    } else if (movieUiState.isError == true){
                        binding.moviesRecyclerview.changeVisibility(false)
                        binding.moviesProgressBar.changeVisibility(false)
                        binding.genresRecyclerview.changeVisibility(false)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}