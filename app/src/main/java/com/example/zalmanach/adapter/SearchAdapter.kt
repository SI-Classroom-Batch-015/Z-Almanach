package com.example.zalmanach.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.zalmanach.R
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.DbzEntity
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation
import com.example.zalmanach.databinding.ListItemSearchBinding

class SearchAdapter(
    private var dataset: List<DbzEntity>,
    private val onItemSelected: (DbzEntity) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class CharacterViewHolder(val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.ivSearchResponse.load(character.characterImage) {
                error(R.drawable.error404)
                transformations(CircleCropTransformation())
            }
            binding.tvSearchResponse.text = character.characterName
            itemView.setOnClickListener {
                onItemSelected(character)
            }
        }
    }

    inner class TransformationViewHolder(val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transformation: Transformation) {
            binding.ivSearchResponse.load(transformation.transformationImage) {
                error(R.drawable.error404)
                transformations(CircleCropTransformation())
            }
            binding.tvSearchResponse.text = transformation.transformationName
            itemView.setOnClickListener {
                onItemSelected(transformation)
            }
        }
    }

    inner class PlanetViewHolder(val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(planet: Planet) {
            binding.ivSearchResponse.load(planet.planetImage) {
                error(R.drawable.error404)
                transformations(CircleCropTransformation())
            }
            binding.tvSearchResponse.text = planet.planetName
            itemView.setOnClickListener {
                onItemSelected(planet)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<DbzEntity>) {
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
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataset[position]
        when (holder) {
            is CharacterViewHolder -> holder.bind(item as Character)
            is TransformationViewHolder -> holder.bind(item as Transformation)
            is PlanetViewHolder -> holder.bind(item as Planet)
        }
    }
}