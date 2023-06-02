package com.example.gbmaterial.ui

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.gbmaterial.R
import com.example.gbmaterial.databinding.FragmentAnimationBinding

class AnimationFragment : ViewBindingFragment<FragmentAnimationBinding>(FragmentAnimationBinding::inflate) {

    private val model: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java] }
    private var isClicked = false
    private val second = 1000L

    companion object { fun newInstance() = AnimationFragment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        model.apodLive.observe(viewLifecycleOwner) {
            with (ui) {
                backgroundImage.loadPicture(it.url)
                titleText.text = it.title
                dateText.text = it.date
                explanationText.text = it.explanation
            }
        }

        ui.backgroundImage.setOnClickListener {
            isClicked = !isClicked
            if (isClicked) {
                animationActivation(R.layout.fragment_animation_end)
            } else {
                animationActivation(R.layout.fragment_animation)
            }
        }
    }

    private fun animationActivation(layout: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), layout)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = second
        TransitionManager.beginDelayedTransition(ui.animationContainer, transition)
        constraintSet.applyTo(ui.animationContainer)
    }

    private fun ImageView.loadPicture(url: String) {
        val imageLoader = ImageLoader.Builder(this.context).build()
        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()
        imageLoader.enqueue(request)
    }
}