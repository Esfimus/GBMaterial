package com.example.gbmaterial.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.gbmaterial.R
import com.example.gbmaterial.data.api.apod.Apod
import com.example.gbmaterial.databinding.RecyclerviewItemBinding

class RecyclerAdapter(private val itemsList: List <Apod>) :
    RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ui = RecyclerviewItemBinding.bind(itemView)

        fun bind(apod: Apod) {
            with (ui) {
                recyclerTitle.apply {
                    text = apod.title
                    visibility = if (apod.title.isNullOrEmpty()) View.GONE else View.VISIBLE
                }
                recyclerCopyright.apply {
                    text = apod.copyright
                    visibility = if (apod.copyright.isNullOrEmpty()) View.GONE else View.VISIBLE
                }
                recyclerDate.apply {
                    text = apod.date
                    visibility = if (apod.date.isNullOrEmpty()) View.GONE else View.VISIBLE
                }
                recyclerImage.loadPicture(apod.url)
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }
}