package com.example.mynews

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.data.Result
import com.example.mynews.utils.URL
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import com.example.mynews.utils.*


class NewsAdapter(private var items: List<Result> = listOf(),
                  val onItemClickListener: OnItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return NewsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]
        item.section?.let {
            holder.section.text = item.section
            holder.title.text = item.title
            holder.date.text = formatDate(item.published_date)
        }
        item.section_name?.let {
            holder.section.text = item.section_name
            holder.title.text = item.snippet
            holder.date.text = formatDate(item.pub_date)
        }

        item.multimedia?.let {
            if(item.multimedia.isNotEmpty()) Picasso.get().load(item.multimedia.first().url).into(holder.image)
        }
//        item.multimediaX?.let {
//            if(item.multimediaX.isNotEmpty()) Picasso.get().load(item.multimediaX.first().url).into(holder.image)
//        }
        item.media?.let {
            if(item.media.isNotEmpty()) Picasso.get().load(item.media.first().mediaMetadata.first().url).into(holder.image)
        }

        holder.cardView.setOnClickListener{
            onItemClickListener.onItemClick(item)
        }

    }



    override fun getItemCount(): Int {
        return items.size
    }


    interface OnItemClicked{
        fun onItemClick(item: Result)
    }

}