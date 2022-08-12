package com.busenamli.moviesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.busenamli.moviesapp.data.model.CastModel
import com.busenamli.moviesapp.databinding.CastListItemBinding

class MovieDetailCreditRecyclerViewAdapter(private val castList: List<CastModel>): RecyclerView.Adapter<MovieDetailCreditRecyclerViewAdapter.CreditListViewHolder>() {

    class CreditListViewHolder(private val binding: CastListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CastModel){
            binding.castModel = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditListViewHolder {
        val binding = CastListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CreditListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CreditListViewHolder, position: Int) {
        val item = castList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return castList.size
    }
}