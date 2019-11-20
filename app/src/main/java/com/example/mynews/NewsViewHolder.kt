package com.example.mynews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.extensions.LayoutContainer


class NewsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    val section: TextView = itemView.findViewById(R.id.newSection)
    val title: TextView = itemView.findViewById(R.id.newTitle)
    val date: TextView = itemView.findViewById(R.id.newDate)
    val image: SimpleDraweeView = itemView.findViewById(R.id.newImage)
    val cardView: ConstraintLayout = itemView.findViewById(R.id.cardView)

}