package com.example.gbmaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbmaterial.databinding.FragmentRecyclerViewBinding

class RecyclerViewFragment : Fragment() {

    private var _ui: FragmentRecyclerViewBinding? = null
    private val ui get() = _ui!!
    private val model: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java] }

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
        model.loadNasaApodList()
        model.apodLiveList.observe(viewLifecycleOwner) { list ->
            val recyclerAdapter = RecyclerAdapter(list)
            ui.recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = recyclerAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }

}