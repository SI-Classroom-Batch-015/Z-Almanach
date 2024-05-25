package com.example.zalmanach.utils

import android.view.View

object AnimationUtils {

    fun startZAnimation(view: View) {
        view.animate().apply {
            duration = 1000
            rotationBy(360f) // Z-Achse/R
        }.withEndAction {
            view.animate().apply {
                duration = 1000
                rotationXBy(180f) // X-Achse/H
            }.withEndAction {
                view.animate().apply {
                    duration = 1000
                    rotationBy(45f)
                }.start()
            }
        }
    }
}