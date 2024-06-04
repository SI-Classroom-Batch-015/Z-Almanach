package com.example.zalmanach.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.zalmanach.R
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.databinding.ListItemSearchBinding

class SearchAdapter(
    private var dataset: List<Character>,
    private val onCharacterSelcted: (Character) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(val binding: ListItemSearchBinding) :
            RecyclerView.ViewHolder(binding.root)

    // Aktualisiert die Liste bei Änderungen
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Character>) {
        dataset = list
        notifyDataSetChanged()
        Log.d("SearchAdapter", "Daten aktualisiert. Anzahl der Elemente: ${dataset.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ListItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int { return dataset.size }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val character = dataset[position]

        holder.binding.ivSearchResponse.load(character.characterImage) {
            error(R.drawable.error404)
            transformations(CircleCropTransformation())
        }
        holder.binding.tvSearchResponse.text = character.characterName
        holder.itemView.setOnClickListener {
            onCharacterSelcted(character)
        }
    }
}