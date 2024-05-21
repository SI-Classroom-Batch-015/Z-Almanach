package com.example.mojopediadbzedition.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mojopediadbzedition.MainViewModel
import com.example.mojopediadbzedition.adapter.DbzAdapter
import com.example.mojopediadbzedition.databinding.FragmentDbzBinding

class DbzFragment : Fragment() {

    private lateinit var binding: FragmentDbzBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDbzBinding.inflate(inflater, container, false)
        viewModel.loadCharacters()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // LinearLayoutManager erstellen und der RecyclerView zuweisen
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvDbzFragment.layoutManager = layoutManager

        // Adapter Init und Konfiguriert samt Safe Args
        val adapter = DbzAdapter { character ->
            findNavController()
                .navigate(
                    DbzFragmentDirections.actionDbzFragmentToDbzDetailFragment(
                        character.characterImage,
                        character.characterName,
                        character.ki,
                        character.maxKi,
                        character.race,
                        character.descriptionSpain,
                    )
                )
        }
        binding.rvDbzFragment.adapter = adapter
        viewModel.characters.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}