package com.example.gbmaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gbmaterial.databinding.FragmentAnimationBinding

class AnimationFragment : Fragment() {

    private var _ui: FragmentAnimationBinding? = null
    private val ui get() = _ui!!

    companion object { fun newInstance() = AnimationFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _ui = FragmentAnimationBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onDestroyView() {
        _ui = null
        super.onDestroyView()
    }
}