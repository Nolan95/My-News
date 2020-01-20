package com.example.mynews

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mynews.utils.*
import android.net.Uri
import android.util.Log
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.repository.roomdata.TopArticles
import com.example.mynews.repository.roomdata.TopArticlesAndMultimediaX


class NewsAdapter(val items: List<TopArticlesAndMultimediaX>, val onItemClickListener: (item: TopArticles) -> Unit) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return NewsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val item: TopArticlesAndMultimediaX? = items[position]
        item?.let {
            holder.section.text = item.article?.section
            holder.title.text = item.article?.title
            holder.date.text = item.article?.published_date?.formatDate()
            Log.w("Item id", "${item.article?.articleId}")
            item.multimedia?.let {
                var uri: Uri = Uri.parse(DEFAULT_IMAGE_LINK)
                if(item.multimedia.isNotEmpty()) uri = Uri.parse(item.multimedia.first().url)
                holder.image.setImageURI (uri)
            }

            holder.cardView.setOnClickListener{
                onItemClickListener(item.article!!)
            }
        }



    }


    override fun getItemCount(): Int {
        return items.size
    }



    /*companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<TopArticles>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldItem: TopArticles,
                                         newItem: TopArticles): Boolean {
                //Log.i("OldItem", "Old: ${oldItem.article?.id} ET New: ${newItem.article?.id}")
                //Log.i("NewItem", "${newItem.article?.id}")

                if(oldItem.id == newItem.id){
                    //Log.i("OldItem", "Old: ${oldItem.article?.id} ET New: ${newItem.article?.id}")
                    return true
                }

                return false
            }

            override fun areContentsTheSame(oldItem: TopArticles,
                                            newItem: TopArticles): Boolean{
                if(oldItem.title == newItem.title && oldItem.views == newItem.views && oldItem.published_date == newItem.published_date){
                    Log.i("OldItem", "Old: ${oldItem.id} ET New: ${newItem.id}")
                    return true
                }
                return false
            }
        }
    }*/


}