package com.example.mynews

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mynews.utils.*
import android.net.Uri
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mynews.repository.roomdata.TopArticlesAndMultimediaX


class NewsAdapter(val onItemClickListener: (item: TopArticlesAndMultimediaX) -> Unit) : PagedListAdapter<TopArticlesAndMultimediaX, NewsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return NewsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val item: TopArticlesAndMultimediaX? = getItem(position)
        item?.let {
            holder.section.text = item.article?.section
            holder.title.text = item.article?.title
            holder.date.text = item.article?.published_date?.formatDate()
            item.multimedia?.let {
                var uri: Uri = Uri.parse(DEFAULT_IMAGE_LINK)
                if(item.multimedia.isNotEmpty()) uri = Uri.parse(item.multimedia.first().url)
                holder.image.setImageURI (uri)
            }
        }

        holder.cardView.setOnClickListener{
            onItemClickListener(item!!)
        }

    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<TopArticlesAndMultimediaX>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldItem: TopArticlesAndMultimediaX,
                                         newItem: TopArticlesAndMultimediaX) = oldItem == newItem

            override fun areContentsTheSame(oldItem: TopArticlesAndMultimediaX,
                                            newItem: TopArticlesAndMultimediaX) = oldItem.equals(newItem)
        }
    }


}