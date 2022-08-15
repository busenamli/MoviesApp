package com.busenamli.moviesapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.busenamli.moviesapp.databinding.MovieListItemBinding
import com.busenamli.moviesapp.data.model.MovieModel
import com.busenamli.moviesapp.ui.uistate.Action
import com.busenamli.moviesapp.ui.view.MovieListByGenreFragmentDirections
import com.busenamli.moviesapp.ui.view.MovieListFragmentDirections
import com.busenamli.moviesapp.util.ItemOnClickListener

class MovieListRecyclerViewAdapter(private val action: Action): PagingDataAdapter<MovieModel, MovieListRecyclerViewAdapter.MoviesViewHolder>(MoviesComparator) {

    class MoviesViewHolder(private val binding: MovieListItemBinding, private val action: Action):RecyclerView.ViewHolder(binding.root), ItemOnClickListener {
        fun bind(item:MovieModel){
            binding.movieModel = item
            binding.listener = this
            binding.executePendingBindings()
        }

        override fun itemOnClicked(view: View) {
            val movieId = binding.movieModel?.id
            val movieName = binding.movieModel?.title
            movieId?.let { id ->
                movieName?.let { name ->
                    when (action) {
                        is Action.FromMovieList -> {

                            val action =
                                MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                                    id,
                                    name
                                )
                            Navigation.findNavController(view).navigate(action)
                        }
                        is Action.FromGenreList -> {
                            val action =
                                MovieListByGenreFragmentDirections.actionMovieListByGenreFragmentToMovieDetailFragment(
                                    id,
                                    name
                                )
                            Navigation.findNavController(view).navigate(action)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MoviesViewHolder(binding, action)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {movieModel ->
            holder.bind(movieModel)
        }
    }

    object MoviesComparator: DiffUtil.ItemCallback<MovieModel>(){

        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }
}