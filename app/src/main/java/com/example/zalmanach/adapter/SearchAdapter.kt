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
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation
import com.example.zalmanach.databinding.ListItemSearchBinding

class SearchAdapter(
    private var dataset: List<Any>,
    private val onItemSelected: (Any) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class CharacterViewHolder(val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class TransformationViewHolder(val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class PlanetViewHolder(val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Any>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return when (viewType) {
            0 -> CharacterViewHolder(binding)
            1 -> TransformationViewHolder(binding)
            2 -> PlanetViewHolder(binding)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataset[position]) {
            is Character -> 0
            is Transformation -> 1
            is Planet -> 2
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataset[position]
        when (holder) {
            is CharacterViewHolder -> {
                val character = item as Character
                holder.binding.ivSearchResponse.load(character.characterImage) {
                    error(R.drawable.error404)
                    transformations(CircleCropTransformation())
                }
                holder.binding.tvSearchResponse.text = character.characterName
                holder.itemView.setOnClickListener {
                    onItemSelected(character)
                }
            }
            is TransformationViewHolder -> {
                val transformation = item as Transformation
                holder.binding.ivSearchResponse.load(transformation.transformationImage) {
                    error(R.drawable.error404)
                    transformations(CircleCropTransformation())
                }
                holder.binding.tvSearchResponse.text = transformation.transformationName
                holder.itemView.setOnClickListener {
                    onItemSelected(transformation)
                }
            }
            is PlanetViewHolder -> {
                val planet = item as Planet
                holder.binding.ivSearchResponse.load(planet.planetImage) {
                    error(R.drawable.error404)
                    transformations(CircleCropTransformation())
                }
                holder.binding.tvSearchResponse.text = planet.planetName
                holder.itemView.setOnClickListener {
                    onItemSelected(planet)
                }
            }
        }
    }
}