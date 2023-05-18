package com.example.gbmaterial.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gbmaterial.R
import com.example.gbmaterial.data.api.apod.Apod
import com.example.gbmaterial.databinding.RecyclerviewItemBinding

class RecyclerAdapter(private val itemsList: List <Apod>) :
    RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ui = RecyclerviewItemBinding.bind(itemView)

        fun bind(apod: Apod) {
            with (ui) {
                recyclerTitle.text = apod.title
                recyclerDate.text = apod.date
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
}