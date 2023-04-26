package com.example.gbmaterial.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.gbmaterial.databinding.FragmentNasaEpicBinding

class NasaEpicFragment : Fragment() {

    private var _ui: FragmentNasaEpicBinding? = null
    private val ui get() = _ui!!
    private val model: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java] }
    private var isExpanded = false
    private val second = 1000L

    companion object { fun newInstance() = NasaEpicFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _ui = FragmentNasaEpicBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        searchDefinition()
        animateImage()
    }

    private fun animateImage() {
        with (ui) {
            imageView.setOnClickListener {
                isExpanded = !isExpanded
                if (isExpanded) {
                    imageView.animate()
                        .scaleX(2f)
                        .scaleY(2f)
                        .translationY(400f)
                        .setDuration(second)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                dictionaryButton.isClickable = false
                            }
                        })
                    coordinatesView.animate()
                        .alpha(0f)
                        .duration
                    dateView.animate()
                        .alpha(0f)
                        .duration
                } else {
                    imageView.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .translationY(0f)
                        .setDuration(second)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                dictionaryButton.isClickable = true
                            }
                        })
                    coordinatesView.animate()
                        .alpha(1f)
                        .duration
                    dateView.animate()
                        .alpha(1f)
                        .duration
                }
            }
        }
    }

    private fun initView() {
        model.loadNasaEpic()
        model.epicLive.observe(viewLifecycleOwner) {
            val date = it[0].date
            val imageId = it[0].image
            val coordinates = """lat: ${it[0].centroidCoordinates.lat}
                |lon: ${it[0].centroidCoordinates.lon}""".trimMargin()
            imageBuilder(date, imageId)
            with (ui) {
                dateView.text = it[0].date
                coordinatesView.text = coordinates
            }
        }
    }

    private fun searchDefinition() {
        ui.searchLayout.setEndIconOnClickListener {
            if (!"""\s*""".toRegex().matches(ui.searchText.text.toString())) {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    val filteredInput = model.filterInputText(ui.searchText.text.toString())
                    data = Uri.parse("https://www.merriam-webster.com/dictionary/$filteredInput")
                })
            }
        }
    }

    private fun imageBuilder(date: String, imageId: String) {
        try {
            val dateList = date.split("""[-\s]""".toRegex())
            val url = "https://epic.gsfc.nasa.gov/archive/natural/${dateList[0]}/${dateList[1]}/${dateList[2]}/jpg/$imageId.jpg"
            ui.imageView.loadPicture(url)
        } catch (_: java.lang.Exception) { }
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

    override fun onDestroyView() {
        _ui = null
        super.onDestroyView()
    }

}