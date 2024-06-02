package com.example.zalmanach.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.zalmanach.R
import com.example.zalmanach.data.model.Transformation
import com.example.zalmanach.databinding.ListItemDbzBinding

class TransformationAdapter(val onTransformationSelected: (Transformation) -> Unit) :
    RecyclerView.Adapter<TransformationAdapter.TransformationViewHolder>() {

    private var dataset: List<Transformation> = emptyList()

    inner class TransformationViewHolder(val binding: ListItemDbzBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Transformation>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformationViewHolder {
        val binding = ListItemDbzBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransformationViewHolder(binding)
    }

    override fun getItemCount(): Int { return dataset.size }

    override fun onBindViewHolder(holder: TransformationViewHolder, position: Int) {
        val transformation = dataset[position]

        holder.binding.ivDbzImage.load(transformation.transformationImage) {
            error(R.drawable.error404)
            transformations(CircleCropTransformation())
        }
        holder.binding.tvDbzName.text = transformation.transformationName
        holder.itemView.setOnClickListener {
            onTransformationSelected(transformation)
        }
    }
}