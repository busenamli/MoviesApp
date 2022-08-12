package com.busenamli.moviesapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.busenamli.moviesapp.ui.adapter.MovieListRecyclerViewAdapter
import com.busenamli.moviesapp.databinding.FragmentMovieListByGenreBinding
import com.busenamli.moviesapp.ui.viewmodel.MovieListByGenreViewModel
import com.busenamli.moviesapp.ui.uistate.Action
import com.busenamli.moviesapp.util.changeVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListByGenreFragment : Fragment() {

    private var _binding: FragmentMovieListByGenreBinding?= null
    private val binding get() = _binding!!
    private val genreArgs: MovieListByGenreFragmentArgs by navArgs()
    private val genreViewModel: MovieListByGenreViewModel by viewModels()
    private lateinit var pagingMovieListAdapter: MovieListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListByGenreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genreId = genreArgs.genreId
        pagingMovieListAdapter = MovieListRecyclerViewAdapter(Action.FromGenreList(true))
        val screenDp = activity!!.resources.configuration.screenWidthDp
        binding.movieListByGenreRecylerview.layoutManager = when{
            screenDp <= 600f -> GridLayoutManager(context,2)
            screenDp <= 840f -> GridLayoutManager(context,4)
            else -> GridLayoutManager(context,6)
        }
        binding.movieListByGenreRecylerview.adapter = pagingMovieListAdapter

        observeRefresh()
        observeData(genreId)
    }

    private fun observeRefresh(){
        binding.movieListByGenreSwipeRefreshLayout.setOnRefreshListener {
            binding.movieListByGenreRecylerview.changeVisibility(false)
            binding.movieListByGenreProgressBar.changeVisibility(true)
            genreViewModel.refreshList()
            binding.movieListByGenreSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeData(genreId: Int) {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                genreViewModel.fetchMoviesByGenre(genreId)

                genreViewModel.uiState.collectLatest { movieUiState ->

                    movieUiState.errorMessage?.let { messages ->
                        if(messages.isNotEmpty()){
                            println(messages.size)
                            val message = messages.get(messages.size-1)
                            Toast.makeText(context, message.message, Toast.LENGTH_SHORT).show()
                            genreViewModel.errorMessageShown()
                        }
                    }

                    if (movieUiState.isLoading == true) {
                        binding.movieListByGenreProgressBar.changeVisibility(true)
                        binding.movieListByGenreRecylerview.changeVisibility(false)

                    } else if (movieUiState.movieList != null) {
                        binding.movieListByGenreProgressBar.changeVisibility(false)
                        binding.movieListByGenreRecylerview.changeVisibility(true)
                        pagingMovieListAdapter.submitData(movieUiState.movieList)

                    } else if (movieUiState.isError == true){
                        binding.movieListByGenreRecylerview.changeVisibility(false)
                        binding.movieListByGenreProgressBar.changeVisibility(false)
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