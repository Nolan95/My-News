package com.example.mynews

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.R
import kotlinx.android.extensions.LayoutContainer

class SearchResultViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    val section: TextView = itemView.findViewById(R.id.newSection)
    val title: TextView = itemView.findViewById(R.id.newTitle)
    val date: TextView = itemView.findViewById(R.id.newDate)
    val image: ImageView = itemView.findViewById(R.id.newImage)
    val cardView: ConstraintLayout = itemView.findViewById(R.id.cardView)

}