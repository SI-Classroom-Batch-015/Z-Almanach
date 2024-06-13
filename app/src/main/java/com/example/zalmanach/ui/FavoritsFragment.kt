package com.example.zalmanach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.zalmanach.MainActivity
import com.example.zalmanach.MainViewModel
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Transformation
import com.example.zalmanach.databinding.FragmentFavoritsBinding
import com.example.zalmanach.databinding.ListItemFavoritVilliansBinding

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

        // Daten beobachten und an die UI zu binden
        viewModel.selectedCharacterImage.observe(viewLifecycleOwner) { characterImage ->
            binding.ivFavoriteCharacter.load(characterImage)
        }

        viewModel.selectedCharacterName.observe(viewLifecycleOwner) { characterName ->
            binding.tvFavoriteName.text = characterName
        }

        // Kombinierte Gegner-Liste aktualisieren und Query-Filter mitgeben
        viewModel.getCombinedVillains("male")
        viewModel.villains.observe(viewLifecycleOwner) { villains ->
            updateVillainsTable(villains)
        }
    }

    private fun updateVillainsTable(villains: List<Any>) {

        // Zuerst alle Entfernen und wieder Neu setzen
        binding.tlFavoriteVillians.removeAllViews()

        villains.forEach { villain ->
            val tableRow = TableRow(context)
            val itemViewBinding = ListItemFavoritVilliansBinding.inflate(layoutInflater, tableRow, false)

            when (villain) {
                is Character -> {
                    itemViewBinding.ivFavoriteVillians.load(villain.characterImage) {
                        listener(
                            onError = { _, _ ->
                                itemViewBinding.tvFavoriteGoneName.visibility = View.VISIBLE
                            }
                        )
                    }
                    itemViewBinding.tvFavoriteGoneName.text = villain.characterName
                }
                is Transformation -> {
                    itemViewBinding.ivFavoriteVillians.load(villain.transformationImage) {
                        listener(
                            onError = { _, _ ->
                                itemViewBinding.tvFavoriteGoneName.visibility = View.VISIBLE
                            }
                        )
                    }
                    itemViewBinding.tvFavoriteGoneName.text = villain.transformationName
                }
            }

            // Instanz vom itemViewBinding zur TableRow; danach zum TableLayout hinzufügen
            tableRow.addView(itemViewBinding.root)
            binding.tlFavoriteVillians.addView(tableRow)
        }
    }

    // Bottom Nav wieder einblenden
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomNav(true)
    }
}