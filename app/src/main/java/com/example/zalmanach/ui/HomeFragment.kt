package com.example.zalmanach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.zalmanach.MainViewModel
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Löst EsterEgg aus
        binding.ivZ.setOnClickListener { viewModel.triggerAnimation() }
        viewModel.startAnimation.observe(viewLifecycleOwner, Observer { isRotate ->
        if (isRotate) {
            AnimationUtils.startZAnimation(binding.ivZ)
        }
        })

    }
}