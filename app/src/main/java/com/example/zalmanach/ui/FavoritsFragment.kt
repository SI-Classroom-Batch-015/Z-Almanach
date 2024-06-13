package com.example.zalmanach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.zalmanach.MainActivity
import com.example.zalmanach.MainViewModel
import com.example.zalmanach.databinding.FragmentFavoritsBinding

class FavoritsFragment : Fragment() {

    private lateinit var binding: FragmentFavoritsBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bottom Nav ausblenden, Fun in der Main
        (activity as MainActivity).showBottomNav(false)

        // Ausgewählten Character beobachten und die Daten an die UI zu binden
        viewModel.selectedCharacter.observe(viewLifecycleOwner) { character ->
            character?.let {
                binding.tvFavoriteName.text = character.characterName
                binding.ivFavoriteCharacter.load(character.characterImage)
            }
        }
    }

    // Bottom Nav wieder einblenden
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomNav(true)
    }
}