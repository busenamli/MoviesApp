package com.busenamli.moviesapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.busenamli.moviesapp.data.model.Genre
import com.busenamli.moviesapp.databinding.GenreListItemBinding
import com.busenamli.moviesapp.ui.view.MovieListFragmentDirections
import com.busenamli.moviesapp.util.ItemOnClickListener

class GenreListRecyclerViewAdapter(private val genreList: List<Genre>) :
    RecyclerView.Adapter<GenreListRecyclerViewAdapter.GenreListViewHolder>() {

    class GenreListViewHolder(private val binding: GenreListItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemOnClickListener {
        fun bind(item: Genre) {
            binding.genreModel = item
            binding.listener = this
            binding.executePendingBindings()
        }

        override fun itemOnClicked(view: View) {
            val genreId = binding.genreModel?.id
            val genreName = binding.genreModel?.name
            genreId?.let { id ->
                genreName?.let { name ->
                    val action =
                        MovieListFragmentDirections.actionMovieListFragmentToMovieListByGenreFragment(
                            id,
                            name
                        )
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
        val binding =
            GenreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        val item = genreList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }
}