package com.example.mynews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.data.Quote
import kotlinx.android.extensions.LayoutContainer

class NewsAdapter(private var items: List<Quote> = listOf()) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return NewsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]
//        holder.title.text = item.title
        holder.description.text = item.content
//        holder.date.text = item.source
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun replaceItems(items: List<Quote>) {
        this.items = items
        notifyDataSetChanged()
    }

}