package com.example.gbmaterial.ui

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.gbmaterial.R
import com.example.gbmaterial.databinding.FragmentNasaApodBinding
import com.example.gbmaterial.ui.recyclerview.RecyclerViewFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NasaApodFragment : Fragment() {

    private var _ui: FragmentNasaApodBinding? = null
    private val ui get() = _ui!!
    private val model: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java] }

    companion object { fun newInstance() = NasaApodFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _ui = FragmentNasaApodBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        model.loadNasaApod(model.currentDate())

        model.apodLive.observe(viewLifecycleOwner) {
            with (ui) {
                if (it.title.isNullOrEmpty()) {
                    apodTitle.visibility = View.GONE
                } else {
                    apodTitle.visibility = View.VISIBLE
                    apodTitle.text = it.title
                }
                if (it.copyright.isNullOrEmpty()) {
                    apodCopyright.visibility = View.GONE
                } else {
                    apodCopyright.visibility = View.VISIBLE
                    apodCopyright.text = it.copyright
                }
                if (it.date.isNullOrEmpty()) {
                    apodDate.visibility = View.GONE
                } else {
                    apodDate.visibility = View.VISIBLE
                    apodDate.text = it.date
                }
                if (it.explanation.isNullOrEmpty()) {
                    apodExplanation.visibility = View.GONE
                } else {
                    apodExplanation.visibility = View.VISIBLE
                    applyMultiStyleText(it.explanation, apodExplanation)
                }
                apodImage.loadPicture(it.url)
            }
        }

        model.responseCodeLive.observe(viewLifecycleOwner) {
            view?.snackMessage(it)
        }

        pictureByDate()

        ui.apodImage.setOnClickListener {
            openFragment(AnimationFragment.newInstance())
        }

        ui.daysListFab.setOnClickListener {
            openFragment(RecyclerViewFragment.newInstance())
        }
    }

    private fun applyMultiStyleText(text: String, textView: TextView) {
        try {
            val spannable = SpannableString(text)
            spannable.setSpan(
                TextAppearanceSpan(requireContext(), R.style.modifiedTextStyle),
                0, firstWhitespaceIndex(text),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannable
        } catch (_: Exception) { }
    }

    private fun firstWhitespaceIndex(text: String): Int {
        for (i in text.indices) {
            if (text[i] == ' ') return i
        }
        return text.length - 1
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

    private fun View.snackMessage(text: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(this, text, length).show()
    }

    private fun pictureByDate() {
        ui.changeDateFab.setOnClickListener {
            val datePicker = MaterialDatePicker
                .Builder
                .datePicker()
                .setTitleText(getString(R.string.select_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.show(parentFragmentManager, "tag")
            datePicker.addOnPositiveButtonClickListener {
                model.loadNasaApod(
                    Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                )
            }
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
        _ui = null
        super.onDestroyView()
    }

}