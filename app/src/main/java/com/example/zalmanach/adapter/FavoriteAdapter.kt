package com.example.zalmanach.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.zalmanach.R
import com.example.zalmanach.data.model.Favorite
import com.example.zalmanach.databinding.ListItemFavoriteBinding

class FavoriteAdapter(
    private val onItemSelected: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var dataset: List<Favorite> = emptyList()

    inner class FavoriteViewHolder(val binding: ListItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Favorite>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ListItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding)

    }

    override fun getItemCount(): Int { return dataset.size }


    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = dataset[position]

        // Abhängig vom Typ, Bild und Namen setzen
        holder.binding.ivFavorite.load(favorite.favoriteImage) {
            error(R.drawable.error404)
            transformations(CircleCropTransformation())
        }
        holder.binding.tvFavoriteName.text = favorite.favoriteName

        // Klicklistener zum Entfernen eines Favoriten
        holder.binding.root.setOnClickListener {
            onItemSelected(favorite)
        }
    }
}