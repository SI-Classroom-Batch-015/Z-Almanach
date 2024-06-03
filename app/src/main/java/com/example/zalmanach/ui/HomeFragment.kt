package com.example.zalmanach.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.zalmanach.MainViewModel
import com.example.zalmanach.R
import com.example.zalmanach.databinding.FragmentHomeBinding
import com.example.zalmanach.utils.AnimationUtils

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.loadTransformations()
        viewModel.loadPlanets()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Löst EsterEgg aus
        binding.ivZ.setOnClickListener { viewModel.triggerAnimation() }
        viewModel.startAnimation.observe(viewLifecycleOwner) { isRotate ->
            if (isRotate) {
                AnimationUtils.startZAnimation(binding.ivZ)
            }
        }

        // VideoView konfi und starten
        val videoUri: Uri =
            Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.dbzopeningintrochalahead)
        val mediaController = MediaController(requireContext())   // Player für Start/Pause
        binding.vVHome.setVideoURI(videoUri)
        binding.vVHome.setMediaController(mediaController)
        mediaController.setAnchorView(binding.vVHome)
        binding.vVHome.start()

        // Setze den OnClickListener für den Logout-Button
        binding.ivLogout.setOnClickListener {      // Zugriff auf die Anmeldedaten
            val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().apply {
                remove("email")
                remove("password")
                putBoolean("is_logged_in", false)
                apply()
            }
            Toast.makeText(requireContext(), "Benutzer abgemeldet", Toast.LENGTH_SHORT).show()
        }
    }
}