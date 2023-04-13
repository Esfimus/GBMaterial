package com.example.gbmaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gbmaterial.databinding.FragmentNasaEpicBinding

class NasaEpicFragment : Fragment() {

    private var _ui: FragmentNasaEpicBinding? = null
    private val ui get() = _ui!!
    private val model: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java] }

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
        model.loadNasaEpic()
        model.epicLive.observe(viewLifecycleOwner) {
            ui.epicText.text = it.toString()
        }
    }

    override fun onDestroyView() {
        _ui = null
        super.onDestroyView()
    }

}