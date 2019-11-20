package com.example.mynews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.repository.roomdata.SharedArticle
import com.example.mynews.repository.roomdata.TopArticles
import com.example.mynews.utils.*
import android.net.Uri



class NewsAdapter(private var items: List<Any> = listOf(),
                  val onItemClickListener: OnItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return NewsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]
        when(item){
            is TopArticles -> {
                holder.section.text = item.section
                holder.title.text = item.title
                holder.date.text = item.published_date.formatDate()
                item.multimedia?.let {
                    var uri: Uri = Uri.parse(DEFAULT_IMAGE_LINK)
                    if(item.multimedia.isNotEmpty()) uri = Uri.parse(item.multimedia.first().url)
                    holder.image.setImageURI (uri)
                }
            }

            is SharedArticle -> {
                holder.section.text = item.section
                holder.title.text = item.title
                holder.date.text = item.published_date.formatDate()
                item.medias?.let {
                    var uri: Uri = Uri.parse(DEFAULT_IMAGE_LINK)
                    if(item.medias.isNotEmpty()) uri = Uri.parse(item.medias.first().mediaMetadata.first().url)
                    holder.image.setImageURI (uri)                }
            }
        }


        holder.cardView.setOnClickListener{
            onItemClickListener.onItemClick(item)
        }

    }



    override fun getItemCount(): Int {
        return items.size
    }


    interface OnItemClicked{
        fun onItemClick(item: Any)
    }

}