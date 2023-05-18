package com.example.gbmaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gbmaterial.databinding.FragmentRecyclerViewBinding

class RecyclerViewFragment : Fragment() {

    private var _ui: FragmentRecyclerViewBinding? = null
    private val ui get() = _ui!!

    companion object { fun newInstance() = RecyclerViewFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _ui = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }

}