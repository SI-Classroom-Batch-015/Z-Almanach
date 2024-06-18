package com.example.zalmanach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.zalmanach.MainViewModel
import com.example.zalmanach.adapter.FavoriteAdapter
import com.example.zalmanach.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteAdapter { favorite ->
      //      viewModel.removeFromFavorite(favorite.favoriteId, favorite.favoriteType)
            Toast.makeText(requireContext(), "Aus Favoriten Entfernt", Toast.LENGTH_SHORT).show()
        }
        binding.rvFavorite.adapter = adapter
        viewModel.favorite.observe(viewLifecycleOwner, Observer { favorites ->
            adapter.submitList(favorites)
        })
    }
}