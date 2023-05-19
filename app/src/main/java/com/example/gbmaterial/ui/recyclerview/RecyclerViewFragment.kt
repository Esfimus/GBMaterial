package com.example.gbmaterial.ui.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbmaterial.R
import com.example.gbmaterial.databinding.FragmentRecyclerViewBinding
import com.example.gbmaterial.ui.AnimationFragment
import com.example.gbmaterial.ui.SharedViewModel

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
            val newList = list.toMutableList()
            val recyclerAdapter = RecyclerAdapter(newList)
            ui.recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = recyclerAdapter
            }
//            ItemTouchHelper(ItemTouchHelperCallback(recyclerAdapter)).attachToRecyclerView(ui.recycler)

            recyclerAdapter.setClickListener(object : OnListItemClick {
                override fun onClick(position: Int) {
                    model.setApodLive(list[position])
                    openFragment(AnimationFragment.newInstance())
                }
            })
        }
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }

}