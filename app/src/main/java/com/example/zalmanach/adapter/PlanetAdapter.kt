package com.example.zalmanach.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.zalmanach.R
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.databinding.ListItemHomeBinding

class PlanetAdapter(val onPlanetSelected: (Planet) -> Unit) :
    RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder>() {

    private var dataset: List<Planet> = emptyList()

    inner class PlanetViewHolder(val binding: ListItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Planet>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val binding = ListItemHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlanetViewHolder(binding)
    }

    override fun getItemCount(): Int { return  dataset.size }

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        val planet = dataset[position]

        holder.binding.ivPlanetAndTransformation.load(planet.planetImage) {
            error(R.drawable.error404)
            transformations(CircleCropTransformation())
        }
        holder.itemView.setOnClickListener {
            onPlanetSelected(planet)
        }
    }
}