package com.example.mynews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mynews.repository.roomdata.SharedArticleAndMedia

class SharedArticleAdapter : PagedListAdapter<SharedArticleAndMedia, NewsViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return NewsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

    }


    companion object{
        private val DIFF_CALLBACK = object:
        DiffUtil.ItemCallback<SharedArticleAndMedia>(){
            override fun areItemsTheSame(
                oldItem: SharedArticleAndMedia,
                newItem: SharedArticleAndMedia
            ) = oldItem.sharedArticle?.sharedArticleId == newItem.sharedArticle?.sharedArticleId
            override fun areContentsTheSame(
                oldItem: SharedArticleAndMedia,
                newItem: SharedArticleAndMedia
            ) = oldItem.sharedArticle == newItem.sharedArticle
        }
    }
}