package com.dhandev.gamer.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhandev.gamer.core.R
import com.dhandev.gamer.core.databinding.ItemListGamesBinding
import com.dhandev.gamer.core.domain.model.Games

class GamesAdapter : ListAdapter<Games, GamesAdapter.ListViewHolder>(DiffCallback()) {

    var onItemClick: ((Games) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_games, parent, false))

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    private class DiffCallback : DiffUtil.ItemCallback<Games>() {

        override fun areItemsTheSame(oldItem: Games, newItem: Games) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Games, newItem: Games) =
            oldItem == newItem
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListGamesBinding.bind(itemView)
        fun bind(data: Games) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.backgroundImage)
                    .into(ivItemImage)
                tvItemTitle.text = data.name
                tvItemSubtitle.text = data.released
                tvRating.text = data.rating.toString()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(currentList[adapterPosition])
            }
        }
    }
}