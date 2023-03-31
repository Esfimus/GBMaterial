package com.example.gbmaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gbmaterial.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _ui: FragmentMainBinding? = null
    private val ui get() = _ui!!
    private val model: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java] }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _ui = FragmentMainBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        model.loadPicture(model.currentDate())
        model.pictureLive.observe(viewLifecycleOwner) {
            ui.testText.text = it.explanation
        }
    }

    override fun onDestroyView() {
        _ui = null
        super.onDestroyView()
    }

}