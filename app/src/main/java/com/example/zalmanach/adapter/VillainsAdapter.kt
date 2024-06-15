package com.example.zalmanach.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.zalmanach.R
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Transformation
import com.example.zalmanach.databinding.ListItemVillainsBinding

class VillainsAdapter :
    RecyclerView.Adapter<VillainsAdapter.VillainViewHolder>() {

    private var dataset: List<Any> = emptyList()

    inner class VillainViewHolder(val binding: ListItemVillainsBinding) :
            RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Any>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VillainsAdapter.VillainViewHolder {
        val binding = ListItemVillainsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  VillainViewHolder(binding)
    }

    override fun getItemCount(): Int { return dataset.size }

    override fun onBindViewHolder(holder: VillainsAdapter.VillainViewHolder, position: Int) {
        val villain = dataset[position]

        when (villain) {
            is Character -> {
                holder.binding.ivFavoriteVillians.load(villain.characterImage) {
                    error(R.drawable.error404)
                }
                holder.binding.tvFavoriteGoneName.text = villain.characterName
            }
            is Transformation -> {
                holder.binding.ivFavoriteVillians.load(villain.transformationImage) {
                    error(R.drawable.error404)
                }
                holder.binding.tvFavoriteGoneName.text = villain.transformationName
            }
        }
    }
}