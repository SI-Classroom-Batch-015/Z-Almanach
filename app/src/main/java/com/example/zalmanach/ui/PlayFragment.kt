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
import com.example.zalmanach.adapter.VillainsAdapter
import com.example.zalmanach.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bottom Nav ausblenden, Fun. in der Main
        (activity as MainActivity).showBottomNav(false)

        // Daten beobachten und an die UI zu binden
        viewModel.selectedCharacterImage.observe(viewLifecycleOwner) { characterImage ->
            binding.ivPlayCharacter.load(characterImage)
        }

        viewModel.selectedCharacterName.observe(viewLifecycleOwner) { characterName ->
            binding.tvPlayName.text = characterName
        }

        val villainsAdapter = VillainsAdapter()
        binding.rvPlayVillains.adapter = villainsAdapter

        // Kombinierte Gegner-Liste aktualisieren und Query-Filter mitgeben
        viewModel.getCombinedVillains("male")
        viewModel.villains.observe(viewLifecycleOwner) { villains ->
            villainsAdapter.submitList(villains)
        }
    }

    // Bottom Nav wieder einblenden
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomNav(true)
    }
}