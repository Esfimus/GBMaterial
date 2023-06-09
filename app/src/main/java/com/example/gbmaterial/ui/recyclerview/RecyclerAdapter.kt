package com.example.gbmaterial.ui.recyclerview

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

class RecyclerAdapter(private val itemsList: MutableList <Apod>) :
    RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>(), ItemTouchHelperAdapter {

    private var itemClickListener: OnListItemClick? = null

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

        init {
            ui.recyclerCard.setOnClickListener {
                itemClickListener?.onClick(absoluteAdapterPosition)
            }
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

    fun setClickListener(clickListener: OnListItemClick) {
        itemClickListener = clickListener
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        itemsList.removeAt(fromPosition).apply {
            itemsList.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemSwipe(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
    }
}

interface OnListItemClick {
    fun onClick(position: Int)
}

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemSwipe(position: Int)
}