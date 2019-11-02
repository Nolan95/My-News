package com.example.mynews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer


class NewsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

//    val title: TextView = itemView.findViewById(R.id.title)
    val description: TextView = itemView.findViewById(R.id.description)
//    val date: TextView = itemView.findViewById(R.id.date)

}