package com.example.mojopediadbzedition.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mojopediadbzedition.databinding.FragmentFavoritsBinding

class FavoritsFragment : Fragment() {

    private lateinit var binding: FragmentFavoritsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritsBinding.inflate(inflater, container, false)
        return binding.root
    }
}