package com.busenamli.moviesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.busenamli.moviesapp.databinding.MovieListItemBinding
import com.busenamli.moviesapp.data.model.MovieModel
import com.busenamli.moviesapp.ui.movielist.MovieListFragmentDirections
import com.busenamli.moviesapp.util.MovieItemOnClickListener

class MovieListRecyclerViewAdapter(diffCallback: DiffUtil.ItemCallback<MovieModel>): PagingDataAdapter<MovieModel, MovieListRecyclerViewAdapter.MoviesViewHolder>(diffCallback) {

    class MoviesViewHolder(private val binding: MovieListItemBinding):RecyclerView.ViewHolder(binding.root), MovieItemOnClickListener {
        fun bind(item:MovieModel){
            binding.movieModel = item
            binding.listener = this
            binding.executePendingBindings()
        }

        override fun movieItemOnClicked(view: View) {
            val movieId = binding.movieModel?.id
            movieId?.let {
                val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieId)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(item)
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

