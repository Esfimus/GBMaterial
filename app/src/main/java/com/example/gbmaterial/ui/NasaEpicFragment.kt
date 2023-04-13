package com.example.gbmaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gbmaterial.databinding.FragmentNasaEpicBinding

class NasaEpicFragment : Fragment() {

    private var _ui: FragmentNasaEpicBinding? = null
    private val ui get() = _ui!!

    companion object { fun newInstance() = NasaEpicFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _ui = FragmentNasaEpicBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

    }

    override fun onDestroyView() {
        _ui = null
        super.onDestroyView()
    }

}